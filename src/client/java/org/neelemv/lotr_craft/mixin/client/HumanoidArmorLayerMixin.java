package org.neelemv.lotr_craft.mixin.client;

import org.neelemv.lotr_craft.client.render.entity.LotrCustomHelmetModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {
    @Inject(method = "renderArmorPiece", at = @At("HEAD"), cancellable = true)
    private void lotrCraft$skipDefaultCustomHelmet(PoseStack poseStack, SubmitNodeCollector collector, ItemStack stack, EquipmentSlot slot, int packedLight, HumanoidRenderState state, CallbackInfo ci) {
        if (slot == EquipmentSlot.HEAD && LotrCustomHelmetModel.variantFor(stack) != null) {
            ci.cancel();
        }
    }
}
