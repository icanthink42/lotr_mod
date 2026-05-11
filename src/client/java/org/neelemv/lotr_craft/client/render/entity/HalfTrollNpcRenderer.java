package org.neelemv.lotr_craft.client.render.entity;

import org.neelemv.lotr_craft.entity.LotrHumanoidNpcEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class HalfTrollNpcRenderer extends HumanoidNpcRenderer {
    public HalfTrollNpcRenderer(EntityRendererProvider.Context context) {
        super(context, new HalfTrollNpcModel(context.bakeLayer(HalfTrollNpcModel.LAYER_LOCATION)));
    }

    @Override
    public void extractRenderState(LotrHumanoidNpcEntity entity, HumanoidNpcRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        long bits = entity.getUUID().getMostSignificantBits() ^ entity.getUUID().getLeastSignificantBits();
        state.halfTrollMohawk = (bits & 1L) == 0L;
        state.halfTrollHorns = (bits & 2L) == 0L;
        state.halfTrollFullHorns = state.halfTrollHorns && (bits & 4L) == 0L;
    }
}
