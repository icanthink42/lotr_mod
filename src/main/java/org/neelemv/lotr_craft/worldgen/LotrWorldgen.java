package org.neelemv.lotr_craft.worldgen;

import org.neelemv.lotr_craft.Lotr_craft;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.chunk.ChunkGenerator;

public final class LotrWorldgen {
    public static final MapCodec<MiddleEarthChunkGenerator> MIDDLE_EARTH_CHUNK_GENERATOR = MiddleEarthChunkGenerator.CODEC;

    private LotrWorldgen() {
    }

    public static void register() {
        Registry.register(
                BuiltInRegistries.CHUNK_GENERATOR,
                Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "middle_earth"),
                MIDDLE_EARTH_CHUNK_GENERATOR);
    }
}
