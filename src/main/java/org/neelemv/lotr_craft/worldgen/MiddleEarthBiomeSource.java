package org.neelemv.lotr_craft.worldgen;

import java.util.Map;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

public class MiddleEarthBiomeSource extends BiomeSource {
    public static final MapCodec<MiddleEarthBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, Biome.CODEC).fieldOf("biomes").forGetter(source -> source.biomesByName))
            .apply(instance, MiddleEarthBiomeSource::new));

    private final Map<String, Holder<@NotNull Biome>> biomesByName;
    private final Holder<@NotNull Biome> fallbackBiome;

    private int cacheX = Integer.MIN_VALUE;
    private int cacheZ = Integer.MIN_VALUE;
    private Holder<@NotNull Biome> cacheBiome = null;

    public MiddleEarthBiomeSource(Map<String, Holder<Biome>> biomesByName) {
        if (biomesByName.isEmpty()) {
            throw new IllegalArgumentException("Middle-earth biome source requires at least one biome");
        }
        this.biomesByName = Map.copyOf(biomesByName);
        this.fallbackBiome = this.biomesByName.getOrDefault(MiddleEarthTerrainProfile.OCEAN.biomeName, this.biomesByName.values().iterator().next());
    }

    @Override
    protected @NotNull MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull Stream<Holder<@NotNull Biome>> collectPossibleBiomes() {
        return biomesByName.values().stream().distinct();
    }

    @Override
    public Holder<@NotNull Biome> getNoiseBiome(int quartX, int quartY, int quartZ, Climate.Sampler sampler) {
        int blockX = quartX << 2;
        int blockZ = quartZ << 2;
        if (blockX == cacheX && blockZ == cacheZ) {
            return cacheBiome;
        }
        MiddleEarthTerrainProfile profile = SvgMiddleEarthMap.get().terrainBlendAtBlock(blockX, blockZ).biomeProfileAtBlock(blockX, blockZ);
        Holder<@NotNull Biome> biome = biomesByName.getOrDefault(profile.biomeName, fallbackBiome);
        cacheX = blockX;
        cacheZ = blockZ;
        cacheBiome = biome;
        return biome;
    }
}
