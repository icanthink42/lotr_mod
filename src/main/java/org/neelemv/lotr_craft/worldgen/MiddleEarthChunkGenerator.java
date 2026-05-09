package org.neelemv.lotr_craft.worldgen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MiddleEarthChunkGenerator extends ChunkGenerator {
    public static final MapCodec<MiddleEarthChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(MiddleEarthChunkGenerator::getBiomeSource))
            .apply(instance, MiddleEarthChunkGenerator::new));

    private static final BlockState AIR = Blocks.AIR.defaultBlockState();
    private static final BlockState BEDROCK = Blocks.BEDROCK.defaultBlockState();
    private static final BlockState STONE = Blocks.STONE.defaultBlockState();
    private static final BlockState WATER = Blocks.WATER.defaultBlockState();

    public MiddleEarthChunkGenerator(BiomeSource biomeSource) {
        super(biomeSource);
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion region, long seed, RandomState randomState, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk) {
    }

    @Override
    public void buildSurface(WorldGenRegion region, StructureManager structureManager, RandomState randomState, ChunkAccess chunk) {
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion region) {
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunk) {
        int minY = chunk.getMinY();
        int maxY = minY + chunk.getHeight();
        int chunkBlockX = chunk.getPos().getMinBlockX();
        int chunkBlockZ = chunk.getPos().getMinBlockZ();

        for (int localX = 0; localX < 16; localX++) {
            int blockX = chunkBlockX + localX;
            for (int localZ = 0; localZ < 16; localZ++) {
                int blockZ = chunkBlockZ + localZ;
                TerrainBlend terrain = SvgMiddleEarthMap.get().terrainBlendAtBlock(blockX, blockZ);
                int surfaceY = getTerrainHeight(blockX, blockZ, terrain);
                fillColumn(chunk, localX, localZ, surfaceY, minY, maxY, terrain);
            }
        }

        Heightmap.primeHeightmaps(chunk, chunk.getPersistedStatus().heightmapsAfter());
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return MiddleEarthMapConstants.SEA_LEVEL;
    }

    @Override
    public int getMinY() {
        return MiddleEarthMapConstants.WORLD_MIN_Y;
    }

    @Override
    public int getGenDepth() {
        return MiddleEarthMapConstants.WORLD_HEIGHT;
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor heightAccessor, RandomState randomState) {
        TerrainBlend terrain = SvgMiddleEarthMap.get().terrainBlendAtBlock(x, z);
        int terrainHeight = getTerrainHeight(x, z, terrain);
        if (terrain.water()) {
            return Math.max(terrainHeight, getSeaLevel() + 1);
        }
        return terrainHeight + 1;
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor heightAccessor, RandomState randomState) {
        int minY = heightAccessor.getMinY();
        int height = heightAccessor.getHeight();
        BlockState[] states = new BlockState[height];
        TerrainBlend terrain = SvgMiddleEarthMap.get().terrainBlendAtBlock(x, z);
        int surfaceY = getTerrainHeight(x, z, terrain);
        for (int i = 0; i < states.length; i++) {
            int y = minY + i;
            states[i] = stateForY(y, minY, surfaceY, terrain);
        }
        return new NoiseColumn(minY, states);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState randomState, BlockPos pos) {
        MiddleEarthTerrainProfile profile = SvgMiddleEarthMap.get().terrainAtBlock(pos.getX(), pos.getZ());
        TerrainBlend terrain = SvgMiddleEarthMap.get().terrainBlendAtBlock(pos.getX(), pos.getZ());
        info.add("LOTR terrain: " + profile.debugName());
        info.add("LOTR expected height: " + getTerrainHeight(pos.getX(), pos.getZ(), terrain));
        info.add("LOTR mountain interior: " + String.format("%.3f", terrain.mountainInterior()));
        info.add("LOTR map scale: 1:" + MiddleEarthMapConstants.MAP_SCALE);
    }

    private int getTerrainHeight(int blockX, int blockZ, TerrainBlend terrain) {
        double broad = noise(blockX, blockZ, 0.0028, 1954L);
        double detail = noise(blockX, blockZ, 0.018, 1955L);
        double ridge = 1.0 - Math.abs(noise(blockX, blockZ, 0.006, 1956L));
        double interior = terrain.mountainPeakHeight() > 0.0 ? smooth(clamp(terrain.mountainInterior(), 0.0, 1.0)) : 0.0;
        double baseHeight = terrain.mountainPeakHeight() > 0.0 ? lerp(getSeaLevel() + 6.0, terrain.baseHeight(), interior) : terrain.baseHeight();
        double variation = terrain.mountainPeakHeight() > 0.0 ? lerp(8.0, terrain.variation(), interior) : terrain.variation();
        double roughness = terrain.mountainPeakHeight() > 0.0 ? lerp(6.0, terrain.roughness(), interior) : terrain.roughness();
        double shaped = broad * variation + detail * roughness + ridge * roughness * 0.75;
        double landHeight = Math.max(getSeaLevel() - 2, baseHeight + shaped);

        if (terrain.mountainPeakHeight() > 0.0) {
            double mountainTarget = Math.min(MiddleEarthMapConstants.WORLD_MAX_Y - 1.0, terrain.mountainPeakHeight());
            double raisedBase = baseHeight + (mountainTarget - baseHeight) * interior;
            double mountainVariationScale = 1.0 + interior * 3.5;
            double mountainRidge = (1.0 - Math.abs(noise(blockX, blockZ, 0.0052, 1982L))) * roughness * interior * 2.5;
            double mountainDetail = detail * roughness * mountainVariationScale;
            landHeight = Math.max(getSeaLevel() - 2, raisedBase + broad * variation * mountainVariationScale + mountainDetail + mountainRidge);
        }

        if (terrain.waterWeight() <= 0.0) {
            return (int) Math.round(landHeight);
        }

        double waterFloor = getSeaLevel() - 7 + noise(blockX, blockZ, 0.013, 1954L) * 4.0;
        double waterBlend = smooth(Math.min(1.0, terrain.waterWeight()));
        return (int) Math.round(lerp(landHeight, waterFloor, waterBlend));
    }

    private static void fillColumn(ChunkAccess chunk, int localX, int localZ, int surfaceY, int minY, int maxY, TerrainBlend terrain) {
        int bedrockTop = Math.min(minY + 4, maxY - 1);
        int top = Math.min(Math.max(surfaceY, MiddleEarthMapConstants.SEA_LEVEL), maxY - 1);
        MiddleEarthTerrainProfile profile = terrain.surfaceProfile();
        boolean water = terrain.water();

        for (int y = minY; y <= bedrockTop; y++) {
            setBlockState(chunk, localX, y, localZ, BEDROCK);
        }

        int stoneTop = Math.min(surfaceY - 5, maxY - 1);
        for (int y = bedrockTop + 1; y <= stoneTop; y++) {
            setBlockState(chunk, localX, y, localZ, STONE);
        }

        if (!water) {
            int fillerBottom = Math.max(bedrockTop + 1, surfaceY - 4);
            int fillerTop = Math.min(surfaceY - 1, maxY - 1);
            BlockState filler = profile.filler();
            for (int y = fillerBottom; y <= fillerTop; y++) {
                setBlockState(chunk, localX, y, localZ, filler);
            }
            if (surfaceY >= minY && surfaceY < maxY) {
                setBlockState(chunk, localX, surfaceY, localZ, profile.top());
            }
        } else {
            int oceanFloorTop = Math.min(surfaceY, maxY - 1);
            for (int y = bedrockTop + 1; y <= oceanFloorTop; y++) {
                setBlockState(chunk, localX, y, localZ, STONE);
            }
        }

        int waterBottom = Math.max(surfaceY + 1, minY);
        int waterTop = Math.min(MiddleEarthMapConstants.SEA_LEVEL, top);
        for (int y = waterBottom; y <= waterTop; y++) {
            setBlockState(chunk, localX, y, localZ, WATER);
        }
    }

    private static void setBlockState(ChunkAccess chunk, int localX, int y, int localZ, BlockState state) {
        LevelChunkSection section = chunk.getSection(chunk.getSectionIndex(y));
        section.setBlockState(localX, y & 15, localZ, state, false);
    }

    private static BlockState stateForY(int y, int minY, int surfaceY, TerrainBlend terrain) {
        MiddleEarthTerrainProfile profile = terrain.surfaceProfile();
        boolean water = terrain.water();
        if (y <= minY + 4) {
            return BEDROCK;
        }
        if (y > surfaceY) {
            return y <= MiddleEarthMapConstants.SEA_LEVEL ? WATER : AIR;
        }
        if (y == surfaceY && !water) {
            return profile.top();
        }
        if (y >= surfaceY - 4 && !water) {
            return profile.filler();
        }
        return STONE;
    }

    private static double noise(int x, int z, double scale, long salt) {
        double nx = x * scale;
        double nz = z * scale;
        double a = valueNoise(nx, nz, salt);
        double b = valueNoise(nx * 2.0 + 19.5, nz * 2.0 - 31.25, salt + 1L) * 0.5;
        double c = valueNoise(nx * 4.0 - 7.25, nz * 4.0 + 11.75, salt + 2L) * 0.25;
        return (a + b + c) / 1.75;
    }

    private static double valueNoise(double x, double z, long salt) {
        int x0 = fastFloor(x);
        int z0 = fastFloor(z);
        double tx = smooth(x - x0);
        double tz = smooth(z - z0);
        double a = randomValue(x0, z0, salt);
        double b = randomValue(x0 + 1, z0, salt);
        double c = randomValue(x0, z0 + 1, salt);
        double d = randomValue(x0 + 1, z0 + 1, salt);
        return lerp(lerp(a, b, tx), lerp(c, d, tx), tz);
    }

    private static double randomValue(int x, int z, long salt) {
        long value = x * 341873128712L + z * 132897987541L + salt * 42317861L;
        value ^= value >>> 13;
        value *= 1274126177L;
        value ^= value >>> 16;
        return ((value & 0xFFFFFF) / (double) 0x7FFFFF) - 1.0;
    }

    private static int fastFloor(double value) {
        int i = (int) value;
        return value < i ? i - 1 : i;
    }

    private static double smooth(double value) {
        return value * value * value * (value * (value * 6.0 - 15.0) + 10.0);
    }

    private static double lerp(double a, double b, double delta) {
        return a + (b - a) * delta;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
