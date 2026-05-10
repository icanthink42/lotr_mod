package org.neelemv.lotr_craft.client.render.entity;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

public class HumanoidNpcRenderState extends HumanoidRenderState {
    public Identifier texture;
    public Identifier overlayTexture;
    public Identifier headwearTexture;
    public float scale = 0.9375F;
    public boolean orcModelFeatures;
}
