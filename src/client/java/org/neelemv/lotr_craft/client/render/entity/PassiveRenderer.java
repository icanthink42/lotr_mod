package org.neelemv.lotr_craft.client.render.entity;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.entity.LotrPassiveEntity;
import org.neelemv.lotr_craft.entity.PassiveKind;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class PassiveRenderer extends MobRenderer<LotrPassiveEntity, PassiveRenderState, PassiveModel> {
    private static final Identifier FALLBACK_TEXTURE = texture("rabbit/0.png");

    public PassiveRenderer(EntityRendererProvider.Context context, PassiveKind kind) {
        super(context, new PassiveModel(context.bakeLayer(PassiveModel.layerLocation(kind))), 0.35F);
    }

    @Override
    public PassiveRenderState createRenderState() {
        return new PassiveRenderState();
    }

    @Override
    public void extractRenderState(LotrPassiveEntity entity, PassiveRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.texture = texture(entity.kind().texturePath());
        state.entityWidth = entity.kind().width();
        state.entityHeight = entity.kind().height();
    }

    @Override
    public Identifier getTextureLocation(PassiveRenderState state) {
        return state.texture == null ? FALLBACK_TEXTURE : state.texture;
    }

    @Override
    protected void scale(PassiveRenderState state, PoseStack poseStack) {
        poseStack.scale(state.entityWidth / 1.0F, state.entityHeight / 1.0F, state.entityWidth / 1.0F);
    }

    private static Identifier texture(String path) {
        return Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "textures/entity/passive/" + path);
    }
}
