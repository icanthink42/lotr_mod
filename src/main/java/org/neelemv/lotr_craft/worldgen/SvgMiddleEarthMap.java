package org.neelemv.lotr_craft.worldgen;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SvgMiddleEarthMap {
    private static final Logger LOGGER = LoggerFactory.getLogger(SvgMiddleEarthMap.class);
    private static final String MAP_RESOURCE = "/assets/lotr_craft/map/map.png";
    private static final String HEIGHT_MAP_RESOURCE = "/assets/lotr_craft/map/height.png";
    private static final double TERRAIN_BLEND_RADIUS = 6.0;
    private static final int TERRAIN_BLEND_CELL_RADIUS = 6;
    private static final double RIVER_SEARCH_RADIUS = 3.0;
    private static final int RIVER_SEARCH_CELL_RADIUS = 3;
    private static final double HEIGHT_MAP_PEAK = 800.0;
    private final byte[] terrainProfiles;
    private final short[] heightMap;

    private SvgMiddleEarthMap(byte[] terrainProfiles, short[] heightMap) {
        this.terrainProfiles = terrainProfiles;
        this.heightMap = heightMap;
    }

    public static SvgMiddleEarthMap get() {
        return Holder.INSTANCE;
    }

    MiddleEarthTerrainProfile terrainAtBlock(int blockX, int blockZ) {
        double mapX = MiddleEarthMapConstants.blockToMapX(blockX);
        double mapZ = MiddleEarthMapConstants.blockToMapZ(blockZ);
        return terrainAtMapPixel(mapX, mapZ);
    }

    TerrainBlend terrainBlendAtBlock(int blockX, int blockZ) {
        double mapX = MiddleEarthMapConstants.blockToMapX(blockX);
        double mapZ = MiddleEarthMapConstants.blockToMapZ(blockZ);
        int centerX = fastFloor(mapX);
        int centerZ = fastFloor(mapZ);
        double totalWeight = 0.0;
        double water = 0.0;
        double[] profileWeights = new double[MiddleEarthTerrainProfile.count()];

        for (int dz = -TERRAIN_BLEND_CELL_RADIUS; dz <= TERRAIN_BLEND_CELL_RADIUS; dz++) {
            int sampleZ = centerZ + dz;
            double weightZ = blendWeight(mapZ, sampleZ);
            if (weightZ <= 0.0) {
                continue;
            }
            for (int dx = -TERRAIN_BLEND_CELL_RADIUS; dx <= TERRAIN_BLEND_CELL_RADIUS; dx++) {
                int sampleX = centerX + dx;
                double weight = weightZ * blendWeight(mapX, sampleX);
                if (weight <= 0.0) {
                    continue;
                }

                MiddleEarthTerrainProfile profile = MiddleEarthTerrainProfile.fromId(profileIdAtMapPixel(sampleX, sampleZ));
                totalWeight += weight;
                water += (profile.water ? 1.0 : 0.0) * weight;
                profileWeights[profile.id()] += weight;
            }
        }

        if (totalWeight <= 0.0) {
            return TerrainBlend.of(MiddleEarthTerrainProfile.OCEAN);
        }

        int dominantId = MiddleEarthTerrainProfile.OCEAN.id();
        double dominantWeight = -1.0;
        for (int i = 0; i < profileWeights.length; i++) {
            if (profileWeights[i] > dominantWeight) {
                dominantWeight = profileWeights[i];
                dominantId = i;
            }
        }

        double inverseWeight = 1.0 / totalWeight;
        double riverStrength = riverStrengthAtMapPosition(mapX, mapZ);
        water = Math.max(water * inverseWeight, riverStrength);
        return new TerrainBlend(
                MiddleEarthTerrainProfile.fromId(dominantId),
                water,
                riverStrength,
                TerrainBlend.normalizedWeights(profileWeights, totalWeight));
    }

    double heightAtBlock(int blockX, int blockZ) {
        double mapX = MiddleEarthMapConstants.blockToMapX(blockX);
        double mapZ = MiddleEarthMapConstants.blockToMapZ(blockZ);
        int centerX = fastFloor(mapX);
        int centerZ = fastFloor(mapZ);
        double totalWeight = 0.0;
        double height = 0.0;

        for (int dz = -TERRAIN_BLEND_CELL_RADIUS; dz <= TERRAIN_BLEND_CELL_RADIUS; dz++) {
            int sampleZ = centerZ + dz;
            double weightZ = blendWeight(mapZ, sampleZ);
            if (weightZ <= 0.0) {
                continue;
            }
            for (int dx = -TERRAIN_BLEND_CELL_RADIUS; dx <= TERRAIN_BLEND_CELL_RADIUS; dx++) {
                int sampleX = centerX + dx;
                double weight = weightZ * blendWeight(mapX, sampleX);
                if (weight <= 0.0) {
                    continue;
                }

                totalWeight += weight;
                height += heightAtMapPixel(sampleX, sampleZ) * weight;
            }
        }

        if (totalWeight <= 0.0) {
            return MiddleEarthMapConstants.SEA_LEVEL;
        }

        return height / totalWeight;
    }

    public int colorAtMapPixel(int x, int z) {
        return MiddleEarthTerrainProfile.colorForId(profileIdAtMapPixel(x, z));
    }

    private static double blendWeight(double coordinate, int sample) {
        double distance = Math.abs(coordinate - (sample + 0.5));
        if (distance >= TERRAIN_BLEND_RADIUS) {
            return 0.0;
        }
        double normalized = 1.0 - distance / TERRAIN_BLEND_RADIUS;
        return normalized * normalized * (3.0 - 2.0 * normalized);
    }

    private double riverStrengthAtMapPosition(double mapX, double mapZ) {
        int centerX = fastFloor(mapX);
        int centerZ = fastFloor(mapZ);
        double nearestWaterDistance = Double.POSITIVE_INFINITY;

        for (int dz = -RIVER_SEARCH_CELL_RADIUS; dz <= RIVER_SEARCH_CELL_RADIUS; dz++) {
            int sampleZ = centerZ + dz;
            for (int dx = -RIVER_SEARCH_CELL_RADIUS; dx <= RIVER_SEARCH_CELL_RADIUS; dx++) {
                int sampleX = centerX + dx;
                MiddleEarthTerrainProfile profile = MiddleEarthTerrainProfile.fromId(profileIdAtMapPixel(sampleX, sampleZ));
                if (!profile.water) {
                    continue;
                }
                double sampleCenterX = sampleX + 0.5;
                double sampleCenterZ = sampleZ + 0.5;
                double distanceX = mapX - sampleCenterX;
                double distanceZ = mapZ - sampleCenterZ;
                double distance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);
                nearestWaterDistance = Math.min(nearestWaterDistance, distance);
            }
        }

        if (nearestWaterDistance >= RIVER_SEARCH_RADIUS) {
            return 0.0;
        }
        double strength = 1.0 - nearestWaterDistance / RIVER_SEARCH_RADIUS;
        return strength * strength * (3.0 - 2.0 * strength);
    }

    private MiddleEarthTerrainProfile terrainAtMapPixel(double mapX, double mapZ) {
        int x = fastFloor(mapX);
        int z = fastFloor(mapZ);
        return MiddleEarthTerrainProfile.fromId(profileIdAtMapPixel(x, z));
    }

    private int profileIdAtMapPixel(int x, int z) {
        if (x < 0 || x >= MiddleEarthMapConstants.MAP_WIDTH || z < 0 || z >= MiddleEarthMapConstants.MAP_HEIGHT) {
            return MiddleEarthTerrainProfile.OCEAN.id();
        }
        return terrainProfiles[x + z * MiddleEarthMapConstants.MAP_WIDTH] & 0xFF;
    }

    private double heightAtMapPixel(int x, int z) {
        if (x < 0 || x >= MiddleEarthMapConstants.MAP_WIDTH || z < 0 || z >= MiddleEarthMapConstants.MAP_HEIGHT) {
            return MiddleEarthMapConstants.SEA_LEVEL;
        }
        double normalized = (heightMap[x + z * MiddleEarthMapConstants.MAP_WIDTH] & 0xFFFF) / 65535.0;
        return MiddleEarthMapConstants.SEA_LEVEL + normalized * (HEIGHT_MAP_PEAK - MiddleEarthMapConstants.SEA_LEVEL);
    }

    private static SvgMiddleEarthMap load() {
        try (InputStream stream = SvgMiddleEarthMap.class.getResourceAsStream(MAP_RESOURCE)) {
            if (stream == null) {
                LOGGER.warn("Missing {}, Middle-earth terrain will fall back to ocean", MAP_RESOURCE);
                return empty();
            }

            BufferedImage image = ImageIO.read(stream);
            if (image == null) {
                LOGGER.warn("Failed to decode {}, Middle-earth terrain will fall back to ocean", MAP_RESOURCE);
                return empty();
            }
            if (image.getWidth() != MiddleEarthMapConstants.MAP_WIDTH || image.getHeight() != MiddleEarthMapConstants.MAP_HEIGHT) {
                LOGGER.warn("Ignoring Middle-earth biome map with unexpected size {}x{}", image.getWidth(), image.getHeight());
                return empty();
            }

            byte[] profiles = new byte[MiddleEarthMapConstants.MAP_WIDTH * MiddleEarthMapConstants.MAP_HEIGHT];
            Set<Integer> unknownColors = new HashSet<>();
            int offset = 0;
            for (int z = 0; z < MiddleEarthMapConstants.MAP_HEIGHT; z++) {
                for (int x = 0; x < MiddleEarthMapConstants.MAP_WIDTH; x++) {
                    int argb = image.getRGB(x, z) | 0xFF000000;
                    MiddleEarthTerrainProfile profile = MiddleEarthTerrainProfile.fromMapColor(argb);
                    if (profile == MiddleEarthTerrainProfile.OCEAN && argb != (MiddleEarthTerrainProfile.OCEAN.mapColor() | 0xFF000000)) {
                        unknownColors.add(argb);
                    }
                    profiles[offset++] = (byte) profile.id();
                }
            }

            if (!unknownColors.isEmpty()) {
                LOGGER.warn("Found {} unknown colors in Middle-earth biome map; unknown pixels use ocean", unknownColors.size());
            }
            short[] heightMap = loadHeightMap();
            LOGGER.info("Loaded Middle-earth biome PNG: {} profile samples, {} biome definitions", profiles.length, MiddleEarthTerrainProfile.count());
            return new SvgMiddleEarthMap(profiles, heightMap);
        } catch (Exception exception) {
            LOGGER.error("Failed to load Middle-earth biome PNG", exception);
            return empty();
        }
    }

    private static SvgMiddleEarthMap empty() {
        byte[] profiles = new byte[MiddleEarthMapConstants.MAP_WIDTH * MiddleEarthMapConstants.MAP_HEIGHT];
        Arrays.fill(profiles, (byte) MiddleEarthTerrainProfile.OCEAN.id());
        short[] defaultHeight = new short[profiles.length];
        short seaLevelEncoded = 0;
        Arrays.fill(defaultHeight, seaLevelEncoded);
        return new SvgMiddleEarthMap(profiles, defaultHeight);
    }

    private static short[] loadHeightMap() {
        try (InputStream stream = SvgMiddleEarthMap.class.getResourceAsStream(HEIGHT_MAP_RESOURCE)) {
            if (stream == null) {
                LOGGER.warn("Missing {}, using flat sea-level height map", HEIGHT_MAP_RESOURCE);
                return defaultHeightMap();
            }

            BufferedImage image = ImageIO.read(stream);
            if (image == null) {
                LOGGER.warn("Failed to decode {}, using flat sea-level height map", HEIGHT_MAP_RESOURCE);
                return defaultHeightMap();
            }
            if (image.getWidth() != MiddleEarthMapConstants.MAP_WIDTH || image.getHeight() != MiddleEarthMapConstants.MAP_HEIGHT) {
                LOGGER.warn("Ignoring height map with unexpected size {}x{} (expected {}x{})", image.getWidth(), image.getHeight(), MiddleEarthMapConstants.MAP_WIDTH, MiddleEarthMapConstants.MAP_HEIGHT);
                return defaultHeightMap();
            }

            int pixelCount = MiddleEarthMapConstants.MAP_WIDTH * MiddleEarthMapConstants.MAP_HEIGHT;
            short[] heights = new short[pixelCount];
            boolean is16Bit = image.getColorModel().getComponentSize(0) > 8;
            int offset = 0;
            for (int z = 0; z < MiddleEarthMapConstants.MAP_HEIGHT; z++) {
                for (int x = 0; x < MiddleEarthMapConstants.MAP_WIDTH; x++) {
                    if (is16Bit) {
                        int sample = image.getRaster().getSample(x, z, 0);
                        heights[offset++] = (short) Math.min(sample, 65535);
                    } else {
                        int argb = image.getRGB(x, z);
                        int gray = (argb >> 16) & 0xFF;
                        heights[offset++] = (short) (gray * 257);
                    }
                }
            }

            LOGGER.info("Loaded Middle-earth height map PNG ({})", is16Bit ? "16-bit" : "8-bit");
            return heights;
        } catch (Exception exception) {
            LOGGER.error("Failed to load Middle-earth height map PNG", exception);
            return defaultHeightMap();
        }
    }

    private static short[] defaultHeightMap() {
        int pixelCount = MiddleEarthMapConstants.MAP_WIDTH * MiddleEarthMapConstants.MAP_HEIGHT;
        short[] heights = new short[pixelCount];
        short seaLevelEncoded = 0;
        Arrays.fill(heights, seaLevelEncoded);
        return heights;
    }

    private static int fastFloor(double value) {
        int i = (int) value;
        return value < i ? i - 1 : i;
    }

    private static final class Holder {
        private static final SvgMiddleEarthMap INSTANCE = load();
    }
}
