package org.neelemv.lotr_craft.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class OrcHumanoidNpcRenderer extends HumanoidNpcRenderer {
    public OrcHumanoidNpcRenderer(EntityRendererProvider.Context context) {
        super(context, new OrcHumanoidNpcModel(context.bakeLayer(OrcHumanoidNpcModel.LAYER_LOCATION)));
    }
}
