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
    private static final double TERRAIN_BLEND_RADIUS = 6.0;
    private static final int TERRAIN_BLEND_CELL_RADIUS = 6;
    private static final double RIVER_SEARCH_RADIUS = 3.0;
    private static final int RIVER_SEARCH_CELL_RADIUS = 3;
    private static final int MOUNTAIN_DISTANCE_SCALE = 10;
    private static final int MOUNTAIN_DISTANCE_CAP = 40 * MOUNTAIN_DISTANCE_SCALE;
    private static final int MOUNTAIN_DISTANCE_STRAIGHT = 10;
    private static final int MOUNTAIN_DISTANCE_DIAGONAL = 14;

    private final byte[] terrainProfiles;
    private final short[] mountainDistances;

    private SvgMiddleEarthMap(byte[] terrainProfiles, short[] mountainDistances) {
        this.terrainProfiles = terrainProfiles;
        this.mountainDistances = mountainDistances;
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
        double baseHeight = 0.0;
        double variation = 0.0;
        double roughness = 0.0;
        double water = 0.0;
        double mountainPeakHeight = 0.0;
        double mountainInterior = 0.0;
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
                baseHeight += profile.baseHeight * weight;
                variation += profile.variation * weight;
                roughness += profile.roughness * weight;
                water += (profile.water ? 1.0 : 0.0) * weight;
                mountainPeakHeight += profile.mountainPeakHeight() * weight;
                mountainInterior += mountainInteriorAtMapPixel(sampleX, sampleZ) * weight;
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
                baseHeight * inverseWeight,
                variation * inverseWeight,
                roughness * inverseWeight,
                water,
                riverStrength,
                mountainPeakHeight * inverseWeight,
                mountainInterior * inverseWeight);
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

    private double mountainInteriorAtMapPixel(int x, int z) {
        if (x < 0 || x >= MiddleEarthMapConstants.MAP_WIDTH || z < 0 || z >= MiddleEarthMapConstants.MAP_HEIGHT) {
            return 0.0;
        }
        return (mountainDistances[x + z * MiddleEarthMapConstants.MAP_WIDTH] & 0xFFFF) / (double) MOUNTAIN_DISTANCE_CAP;
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
            short[] mountainDistances = buildMountainDistances(profiles);
            LOGGER.info("Loaded Middle-earth biome PNG: {} profile samples, {} biome definitions", profiles.length, MiddleEarthTerrainProfile.count());
            return new SvgMiddleEarthMap(profiles, mountainDistances);
        } catch (Exception exception) {
            LOGGER.error("Failed to load Middle-earth biome PNG", exception);
            return empty();
        }
    }

    private static SvgMiddleEarthMap empty() {
        byte[] profiles = new byte[MiddleEarthMapConstants.MAP_WIDTH * MiddleEarthMapConstants.MAP_HEIGHT];
        Arrays.fill(profiles, (byte) MiddleEarthTerrainProfile.OCEAN.id());
        return new SvgMiddleEarthMap(profiles, new short[profiles.length]);
    }

    private static short[] buildMountainDistances(byte[] profiles) {
        int size = profiles.length;
        int width = MiddleEarthMapConstants.MAP_WIDTH;
        int height = MiddleEarthMapConstants.MAP_HEIGHT;
        int[] distances = new int[size];

        for (int i = 0; i < size; i++) {
            MiddleEarthTerrainProfile profile = MiddleEarthTerrainProfile.fromId(profiles[i] & 0xFF);
            distances[i] = profile.mountainPeakHeight() > 0 ? MOUNTAIN_DISTANCE_CAP : 0;
        }

        for (int z = 0; z < height; z++) {
            int row = z * width;
            for (int x = 0; x < width; x++) {
                int index = row + x;
                int distance = distances[index];
                if (x > 0) {
                    distance = Math.min(distance, distances[index - 1] + MOUNTAIN_DISTANCE_STRAIGHT);
                }
                if (z > 0) {
                    int previousRow = index - width;
                    distance = Math.min(distance, distances[previousRow] + MOUNTAIN_DISTANCE_STRAIGHT);
                    if (x > 0) {
                        distance = Math.min(distance, distances[previousRow - 1] + MOUNTAIN_DISTANCE_DIAGONAL);
                    }
                    if (x < width - 1) {
                        distance = Math.min(distance, distances[previousRow + 1] + MOUNTAIN_DISTANCE_DIAGONAL);
                    }
                }
                distances[index] = Math.min(distance, MOUNTAIN_DISTANCE_CAP);
            }
        }

        for (int z = height - 1; z >= 0; z--) {
            int row = z * width;
            for (int x = width - 1; x >= 0; x--) {
                int index = row + x;
                int distance = distances[index];
                if (x < width - 1) {
                    distance = Math.min(distance, distances[index + 1] + MOUNTAIN_DISTANCE_STRAIGHT);
                }
                if (z < height - 1) {
                    int nextRow = index + width;
                    distance = Math.min(distance, distances[nextRow] + MOUNTAIN_DISTANCE_STRAIGHT);
                    if (x > 0) {
                        distance = Math.min(distance, distances[nextRow - 1] + MOUNTAIN_DISTANCE_DIAGONAL);
                    }
                    if (x < width - 1) {
                        distance = Math.min(distance, distances[nextRow + 1] + MOUNTAIN_DISTANCE_DIAGONAL);
                    }
                }
                distances[index] = Math.min(distance, MOUNTAIN_DISTANCE_CAP);
            }
        }

        short[] packedDistances = new short[size];
        for (int i = 0; i < size; i++) {
            packedDistances[i] = (short) distances[i];
        }
        return packedDistances;
    }

    private static int fastFloor(double value) {
        int i = (int) value;
        return value < i ? i - 1 : i;
    }

    private static final class Holder {
        private static final SvgMiddleEarthMap INSTANCE = load();
    }
}
