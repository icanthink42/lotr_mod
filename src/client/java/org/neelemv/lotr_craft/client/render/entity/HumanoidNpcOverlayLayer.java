package org.neelemv.lotr_craft.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

final class HumanoidNpcOverlayLayer extends RenderLayer<HumanoidNpcRenderState, HumanoidModel<HumanoidNpcRenderState>> {
    HumanoidNpcOverlayLayer(RenderLayerParent<HumanoidNpcRenderState, HumanoidModel<HumanoidNpcRenderState>> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, HumanoidNpcRenderState state, float yRot, float xRot) {
        if (state.overlayTexture != null) {
            coloredCutoutModelCopyLayerRender(getParentModel(), state.overlayTexture, poseStack, submitNodeCollector, packedLight, state, -1, 0);
        }
        if (state.headwearTexture != null) {
            coloredCutoutModelCopyLayerRender(getParentModel(), state.headwearTexture, poseStack, submitNodeCollector, packedLight, state, -1, 0);
        }
    }
}
