package org.neelemv.lotr_craft.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

final class HobbitOutfitLayer extends RenderLayer<HobbitRenderState, HumanoidModel<HobbitRenderState>> {
    HobbitOutfitLayer(RenderLayerParent<HobbitRenderState, HumanoidModel<HobbitRenderState>> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, HobbitRenderState state, float yRot, float xRot) {
        if (state.outfitTexture != null) {
            coloredCutoutModelCopyLayerRender(getParentModel(), state.outfitTexture, poseStack, submitNodeCollector, packedLight, state, -1, 0);
        }
    }
}
