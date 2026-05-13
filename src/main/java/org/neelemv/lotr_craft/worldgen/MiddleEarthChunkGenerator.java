package org.neelemv.lotr_craft.worldgen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.resources.Identifier;
import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.worldgen.rng.Rng;

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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.block.state.BlockState;

public class MiddleEarthChunkGenerator extends ChunkGenerator {
    public static final MapCodec<MiddleEarthChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(MiddleEarthChunkGenerator::getBiomeSource))
            .apply(instance, MiddleEarthChunkGenerator::new));

    private volatile MiddleEarthGenerator generator;

    public MiddleEarthChunkGenerator(BiomeSource biomeSource) {
        super(biomeSource);
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    private void init(RandomState randomState) {
        if (generator == null) {
            long seed = randomState.getOrCreateRandomFactory(Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "middle_earth")).fromSeed(0).nextLong();
            generator = new MiddleEarthGenerator(new Rng(seed));
        }
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
        init(randomState);
        generator.generate(chunk);
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
        init(randomState);

        TerrainBlend terrain = SvgMiddleEarthMap.get().terrainBlendAtBlock(x, z);
        int terrainHeight = generator.getTerrainHeight(x, z, terrain);
        if (terrain.water()) {
            return Math.max(terrainHeight, getSeaLevel() + 1);
        }
        return terrainHeight + 1;
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor heightAccessor, RandomState randomState) {
        init(randomState);

        return generator.generateColumn(x, z);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState randomState, BlockPos pos) {
        init(randomState);

        MiddleEarthTerrainProfile profile = SvgMiddleEarthMap.get().terrainAtBlock(pos.getX(), pos.getZ());
        TerrainBlend terrain = SvgMiddleEarthMap.get().terrainBlendAtBlock(pos.getX(), pos.getZ());
        info.add("LOTR terrain: " + profile.debugName());
        info.add("LOTR expected height: " + generator.getTerrainHeight(pos.getX(), pos.getZ(), terrain));
        info.add("LOTR height factor: " + String.format("%.1f", MiddleEarthHeight.heightAtBlock(pos.getX(), pos.getZ())));
        info.add("LOTR river strength: " + String.format("%.3f", terrain.riverStrength()));
        info.add("LOTR map scale: 1:" + MiddleEarthMapConstants.MAP_SCALE);
    }
}
