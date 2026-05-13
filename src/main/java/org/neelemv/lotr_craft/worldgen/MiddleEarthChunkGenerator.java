package org.neelemv.lotr_craft.worldgen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.neelemv.lotr_craft.block.LotrBlocks;

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
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MiddleEarthChunkGenerator extends ChunkGenerator {
    public static final MapCodec<MiddleEarthChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(MiddleEarthChunkGenerator::getBiomeSource))
            .apply(instance, MiddleEarthChunkGenerator::new));

    private static final BlockState AIR = Blocks.AIR.defaultBlockState();
    private static final BlockState BEDROCK = Blocks.BEDROCK.defaultBlockState();
    private static final BlockState STONE = Blocks.STONE.defaultBlockState();
    private static final BlockState WATER = Blocks.WATER.defaultBlockState();
    private static final BlockState DIRT_PATH = Blocks.DIRT_PATH.defaultBlockState();
    private static final BlockState COARSE_DIRT = Blocks.COARSE_DIRT.defaultBlockState();
    private static final BlockState GRAVEL = Blocks.GRAVEL.defaultBlockState();
    private static final BlockState COBBLESTONE = Blocks.COBBLESTONE.defaultBlockState();
    private static final BlockState SANDSTONE = Blocks.SANDSTONE.defaultBlockState();
    private static final BlockState OAK_PLANKS = Blocks.OAK_PLANKS.defaultBlockState();
    private static final BlockState OAK_LOG = Blocks.OAK_LOG.defaultBlockState();
    private static final BlockState OAK_FENCE = Blocks.OAK_FENCE.defaultBlockState();
    private static final BlockState SPRUCE_PLANKS = Blocks.SPRUCE_PLANKS.defaultBlockState();
    private static final BlockState SPRUCE_LOG = Blocks.SPRUCE_LOG.defaultBlockState();
    private static final BlockState SPRUCE_FENCE = Blocks.SPRUCE_FENCE.defaultBlockState();
    private static final BlockState DARK_OAK_PLANKS = Blocks.DARK_OAK_PLANKS.defaultBlockState();
    private static final BlockState DARK_OAK_LOG = Blocks.DARK_OAK_LOG.defaultBlockState();
    private static final BlockState DARK_OAK_FENCE = Blocks.DARK_OAK_FENCE.defaultBlockState();

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
                fillColumn(chunk, localX, localZ, blockX, blockZ, surfaceY, minY, maxY, terrain);
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
            states[i] = stateForY(x, z, y, minY, surfaceY, terrain);
        }
        return new NoiseColumn(minY, states);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState randomState, BlockPos pos) {
        MiddleEarthTerrainProfile profile = SvgMiddleEarthMap.get().terrainAtBlock(pos.getX(), pos.getZ());
        TerrainBlend terrain = SvgMiddleEarthMap.get().terrainBlendAtBlock(pos.getX(), pos.getZ());
        info.add("LOTR terrain: " + profile.debugName());
        info.add("LOTR expected height: " + getTerrainHeight(pos.getX(), pos.getZ(), terrain));
        info.add("LOTR height factor: " + String.format("%.1f", MiddleEarthHeight.heightAtBlock(pos.getX(), pos.getZ())));
        info.add("LOTR river strength: " + String.format("%.3f", terrain.riverStrength()));
        info.add("LOTR map scale: 1:" + MiddleEarthMapConstants.MAP_SCALE);
    }

    private static final double GLOBAL_VARIATION = 8.0;
    private static final double GLOBAL_ROUGHNESS = 6.0;

    private int getTerrainHeight(int blockX, int blockZ, TerrainBlend terrain) {
        double mapHeight = MiddleEarthHeight.heightAtBlock(blockX, blockZ);
        double broad = noise(blockX, blockZ, 0.0028, 1954L);
        double detail = noise(blockX, blockZ, 0.018, 1955L);
        double ridge = 1.0 - Math.abs(noise(blockX, blockZ, 0.006, 1956L));
        double shaped = broad * GLOBAL_VARIATION + detail * GLOBAL_ROUGHNESS + ridge * GLOBAL_ROUGHNESS * 0.75;
        double landHeight = Math.max(MiddleEarthMapConstants.SEA_LEVEL - 2, mapHeight + shaped);

        if (terrain.riverStrength() > 0.0) {
            double river = clamp(terrain.riverStrength(), 0.0, 1.0);
            double channel = smooth(clamp((river - 0.18) / 0.82, 0.0, 1.0));
            double bank = smooth(river);
            double riverBottom = MiddleEarthMapConstants.SEA_LEVEL - 4.0 - channel * 30.0 + noise(blockX, blockZ, 0.018, 1954L) * 2.0;
            double bankHeight = landHeight - bank * 10.0;
            landHeight = lerp(landHeight, Math.min(bankHeight, riverBottom), bank);
        }

        return (int) Math.round(landHeight);
    }

    private void fillColumn(ChunkAccess chunk, int localX, int localZ, int blockX, int blockZ, int surfaceY, int minY, int maxY, TerrainBlend terrain) {
        int bedrockTop = Math.min(minY + 4, maxY - 1);
        int top = Math.min(Math.max(surfaceY, MiddleEarthMapConstants.SEA_LEVEL), maxY - 1);
        MiddleEarthTerrainProfile profile = terrain.surfaceProfileAtBlock(blockX, blockZ);
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
            BlockState filler = profile.filler(blockX, blockZ);
            for (int y = fillerBottom; y <= fillerTop; y++) {
                setBlockState(chunk, localX, y, localZ, filler);
            }
            if (surfaceY >= minY && surfaceY < maxY) {
                setBlockState(chunk, localX, surfaceY, localZ, profile.top(blockX, blockZ, surfaceY, terrain.water()));
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

        applyRoad(chunk, localX, localZ, blockX, blockZ, surfaceY, minY, maxY, terrain);
    }

    private void applyRoad(ChunkAccess chunk, int localX, int localZ, int blockX, int blockZ, int surfaceY, int minY, int maxY, TerrainBlend terrain) {
        if (!MiddleEarthRoads.isRoadAt(blockX, blockZ)) {
            return;
        }

        boolean bridge = terrain.water() || surfaceY < MiddleEarthMapConstants.SEA_LEVEL;
        if (bridge) {
            int bridgeY = Math.min(Math.max(MiddleEarthMapConstants.SEA_LEVEL + 1, surfaceY + 2), maxY - 3);
            BridgeBlocks bridgeBlocks = bridgeBlocks(terrain.surfaceProfileAtBlock(blockX, blockZ));
            boolean edge = isRoadEdge(blockX, blockZ);
            boolean pillar = edge && isRoadPillar(blockX, blockZ);

            setBlockStateSafe(chunk, localX, bridgeY, localZ, edge ? bridgeBlocks.edge() : bridgeBlocks.deck(), minY, maxY);
            if (edge) {
                setBlockStateSafe(chunk, localX, bridgeY + 1, localZ, connectedFence(bridgeBlocks.fence(), blockX, blockZ), minY, maxY);
            }
            if (pillar) {
                for (int y = bridgeY - 1; y >= Math.max(surfaceY + 1, MiddleEarthMapConstants.SEA_LEVEL - 10); y--) {
                    setBlockStateSafe(chunk, localX, y, localZ, bridgeBlocks.edge(), minY, maxY);
                }
            }
            return;
        }

        int roadY = Math.min(surfaceY, maxY - 1);
        MiddleEarthTerrainProfile roadProfile = terrain.surfaceProfileAtBlock(blockX, blockZ);
        BlockState topState = roadState(roadProfile, blockX, blockZ);
        BlockState fillState = roadFillState(roadProfile, blockX, blockZ);
        setBlockStateSafe(chunk, localX, roadY, localZ, topState, minY, maxY);
        for (int y = roadY - 1; y >= Math.max(minY + 5, roadY - 3); y--) {
            setBlockStateSafe(chunk, localX, y, localZ, fillState, minY, maxY);
        }
    }

    private static boolean isRoadEdge(int blockX, int blockZ) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if ((dx != 0 || dz != 0) && !MiddleEarthRoads.isRoadAt(blockX + dx, blockZ + dz)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isRoadPillar(int blockX, int blockZ) {
        int range = 8;
        int xMod = Math.floorMod(blockX, range);
        return Math.floorMod(xMod + Math.floorMod(blockZ, range), range) == 0;
    }

    private BlockState connectedFence(BlockState fence, int blockX, int blockZ) {
        return fence
                .setValue(CrossCollisionBlock.NORTH, hasBridgeFenceAt(blockX, blockZ - 1))
                .setValue(CrossCollisionBlock.EAST, hasBridgeFenceAt(blockX + 1, blockZ))
                .setValue(CrossCollisionBlock.SOUTH, hasBridgeFenceAt(blockX, blockZ + 1))
                .setValue(CrossCollisionBlock.WEST, hasBridgeFenceAt(blockX - 1, blockZ));
    }

    private boolean hasBridgeFenceAt(int blockX, int blockZ) {
        if (!MiddleEarthRoads.isRoadAt(blockX, blockZ) || !isRoadEdge(blockX, blockZ)) {
            return false;
        }
        TerrainBlend terrain = SvgMiddleEarthMap.get().terrainBlendAtBlock(blockX, blockZ);
        return terrain.water() || getTerrainHeight(blockX, blockZ, terrain) < MiddleEarthMapConstants.SEA_LEVEL;
    }

    private static BlockState roadState(MiddleEarthTerrainProfile profile, int blockX, int blockZ) {
        String biome = profile.biomeName;
        int roll = coordinateHash(blockX, blockZ) & 7;
        if (biome.contains("mordor") || biome.contains("gorgoroth") || biome.contains("udun") || biome.contains("dagorlad") || biome.contains("nanUngol")) {
            return roll == 0 ? LotrBlocks.MORDOR_GRAVEL.defaultBlockState() : LotrBlocks.MORDOR_DIRT.defaultBlockState();
        }
        if (biome.contains("gondor") || biome.contains("ithilien") || biome.contains("lebennin") || biome.contains("lossarnach") || biome.contains("dorEnErnil") || biome.contains("dale") || biome.contains("dorwinion") || biome.contains("rhun")) {
            return roll <= 1 ? GRAVEL : COBBLESTONE;
        }
        if (biome.contains("harad") || biome.contains("umbar") || biome.contains("harnedor") || biome.contains("lostladen")) {
            return roll <= 1 ? Blocks.SAND.defaultBlockState() : SANDSTONE;
        }
        if (roll == 0) {
            return GRAVEL;
        }
        if (roll == 1) {
            return COARSE_DIRT;
        }
        return DIRT_PATH;
    }

    private static BlockState roadFillState(MiddleEarthTerrainProfile profile, int blockX, int blockZ) {
        BlockState state = roadState(profile, blockX, blockZ);
        return state == DIRT_PATH ? COARSE_DIRT : state;
    }

    private static BridgeBlocks bridgeBlocks(MiddleEarthTerrainProfile profile) {
        String biome = profile.biomeName;
        if (biome.contains("mordor") || biome.contains("gorgoroth") || biome.contains("udun") || biome.contains("nanUngol")) {
            return new BridgeBlocks(DARK_OAK_PLANKS, DARK_OAK_LOG, DARK_OAK_FENCE);
        }
        if (biome.contains("mirkwood") || biome.contains("woodlandRealm") || biome.contains("fangorn")) {
            return new BridgeBlocks(SPRUCE_PLANKS, SPRUCE_LOG, SPRUCE_FENCE);
        }
        return new BridgeBlocks(OAK_PLANKS, OAK_LOG, OAK_FENCE);
    }

    private static int coordinateHash(int blockX, int blockZ) {
        int value = blockX * 73428767 ^ blockZ * 912931;
        value ^= value >>> 13;
        value *= 1274126177;
        return value ^ value >>> 16;
    }

    private static void setBlockState(ChunkAccess chunk, int localX, int y, int localZ, BlockState state) {
        LevelChunkSection section = chunk.getSection(chunk.getSectionIndex(y));
        section.setBlockState(localX, y & 15, localZ, state, false);
    }

    private static void setBlockStateSafe(ChunkAccess chunk, int localX, int y, int localZ, BlockState state, int minY, int maxY) {
        if (y >= minY && y < maxY) {
            setBlockState(chunk, localX, y, localZ, state);
        }
    }

    private record BridgeBlocks(BlockState deck, BlockState edge, BlockState fence) {
    }

    private static BlockState stateForY(int blockX, int blockZ, int y, int minY, int surfaceY, TerrainBlend terrain) {
        MiddleEarthTerrainProfile profile = terrain.surfaceProfileAtBlock(blockX, blockZ);
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
