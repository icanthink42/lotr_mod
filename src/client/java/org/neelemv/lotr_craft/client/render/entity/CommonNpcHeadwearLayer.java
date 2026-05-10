package org.neelemv.lotr_craft.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

final class CommonNpcHeadwearLayer extends RenderLayer<CommonNpcRenderState, HumanoidModel<CommonNpcRenderState>> {
    CommonNpcHeadwearLayer(RenderLayerParent<CommonNpcRenderState, HumanoidModel<CommonNpcRenderState>> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, CommonNpcRenderState state, float yRot, float xRot) {
        if (state.headwearTexture != null) {
            coloredCutoutModelCopyLayerRender(getParentModel(), state.headwearTexture, poseStack, submitNodeCollector, packedLight, state, -1, 0);
        }
    }
}
