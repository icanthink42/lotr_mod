package org.neelemv.lotr_craft.mixin.client;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import org.neelemv.lotr_craft.Lotr_craft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FogRenderer.class)
public class MirkwoodFogMixin {
    private static final ResourceKey<Biome> MIRKWOOD_CORRUPTED = biomeKey("mirkwood_corrupted");
    private static final ResourceKey<Biome> MIRKWOOD_MOUNTAINS = biomeKey("mirkwood_mountains");
    private static final ResourceKey<Biome> MIRKWOOD_NORTH = biomeKey("mirkwood_north");
    private static final ResourceKey<Biome> DOL_GULDUR = biomeKey("dol_guldur");

    @Inject(method = "setupFog", at = @At("RETURN"))
    private void lotr_craft$thickenMirkwoodFog(
            Camera camera,
            int renderDistance,
            DeltaTracker deltaTracker,
            float darkenWorldAmount,
            ClientLevel level,
            CallbackInfoReturnable<FogData> cir
    ) {
        if (camera.getFluidInCamera() != FogType.NONE) {
            return;
        }

        Holder<Biome> biome = level.getBiome(camera.blockPosition());
        FogData fog = cir.getReturnValue();
        if (biome.is(DOL_GULDUR) || biome.is(MIRKWOOD_CORRUPTED)) {
            applyMirkwoodFog(fog, 2.0F, 42.0F, 62.0F, 0.42F);
        } else if (biome.is(MIRKWOOD_MOUNTAINS)) {
            applyMirkwoodFog(fog, 2.0F, 50.0F, 76.0F, 0.48F);
        } else if (biome.is(MIRKWOOD_NORTH)) {
            applyMirkwoodFog(fog, 4.0F, 74.0F, 112.0F, 0.62F);
        }
    }

    private static void applyMirkwoodFog(FogData fog, float start, float environmentalEnd, float renderEnd, float colorScale) {
        fog.environmentalStart = Math.min(fog.environmentalStart, start);
        fog.environmentalEnd = Math.min(fog.environmentalEnd, environmentalEnd);
        fog.renderDistanceStart = Math.min(fog.renderDistanceStart, start + 12.0F);
        fog.renderDistanceEnd = Math.min(fog.renderDistanceEnd, renderEnd);
        fog.skyEnd = Math.min(fog.skyEnd, renderEnd * 0.85F);
        fog.cloudEnd = Math.min(fog.cloudEnd, renderEnd * 0.75F);
        fog.color.x *= colorScale;
        fog.color.y *= colorScale;
        fog.color.z *= colorScale;
    }

    private static ResourceKey<Biome> biomeKey(String path) {
        return ResourceKey.create(net.minecraft.core.registries.Registries.BIOME, Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, path));
    }
}
