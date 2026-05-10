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

public class MiddleEarthBiomeSource extends BiomeSource {
    public static final MapCodec<MiddleEarthBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, Biome.CODEC).fieldOf("biomes").forGetter(source -> source.biomesByName))
            .apply(instance, MiddleEarthBiomeSource::new));

    private final Map<String, Holder<Biome>> biomesByName;
    private final Holder<Biome> fallbackBiome;

    public MiddleEarthBiomeSource(Map<String, Holder<Biome>> biomesByName) {
        if (biomesByName.isEmpty()) {
            throw new IllegalArgumentException("Middle-earth biome source requires at least one biome");
        }
        this.biomesByName = Map.copyOf(biomesByName);
        this.fallbackBiome = this.biomesByName.getOrDefault(MiddleEarthTerrainProfile.OCEAN.biomeName, this.biomesByName.values().iterator().next());
    }

    @Override
    protected MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return biomesByName.values().stream().distinct();
    }

    @Override
    public Holder<Biome> getNoiseBiome(int quartX, int quartY, int quartZ, Climate.Sampler sampler) {
        int blockX = quartX << 2;
        int blockZ = quartZ << 2;
        MiddleEarthTerrainProfile profile = SvgMiddleEarthMap.get().terrainBlendAtBlock(blockX, blockZ).biomeProfileAtBlock(blockX, blockZ);
        return biomesByName.getOrDefault(profile.biomeName, fallbackBiome);
    }
}
