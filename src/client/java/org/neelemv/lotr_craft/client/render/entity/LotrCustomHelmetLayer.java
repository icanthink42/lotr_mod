package org.neelemv.lotr_craft.client.render.entity;

import java.util.EnumMap;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class LotrCustomHelmetLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    private final Map<LotrCustomHelmetModel.Variant, LotrCustomHelmetModel<S>> models = new EnumMap<>(LotrCustomHelmetModel.Variant.class);

    public LotrCustomHelmetLayer(RenderLayerParent<S, M> parent, EntityRendererProvider.Context context) {
        super(parent);
        for (LotrCustomHelmetModel.Variant variant : LotrCustomHelmetModel.Variant.values()) {
            models.put(variant, new LotrCustomHelmetModel<>(context.bakeLayer(variant.layer())));
        }
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, S state, float yRot, float xRot) {
        LotrCustomHelmetModel.Variant variant = LotrCustomHelmetModel.variantFor(state.headEquipment);
        if (variant == null) {
            return;
        }
        coloredCutoutModelCopyLayerRender(models.get(variant), variant.texture(), poseStack, collector, packedLight, state, -1, 0);
    }
}
