package org.neelemv.lotr_craft.client.render.entity;

import java.util.UUID;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.entity.LotrCommonNpcEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.Identifier;

public class CommonNpcRenderer extends HumanoidMobRenderer<LotrCommonNpcEntity, CommonNpcRenderState, HumanoidModel<CommonNpcRenderState>> {
    private static final Identifier FALLBACK_TEXTURE = texture("bree_male/0");

    public CommonNpcRenderer(EntityRendererProvider.Context context) {
        super(context, new CommonNpcModel(context.bakeLayer(CommonNpcModel.LAYER_LOCATION)), 0.5F);
        addLayer(new CommonNpcHeadwearLayer(this));
    }

    @Override
    public CommonNpcRenderState createRenderState() {
        return new CommonNpcRenderState();
    }

    @Override
    public void extractRenderState(LotrCommonNpcEntity entity, CommonNpcRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        UUID uuid = entity.getUUID();
        long bits = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
        boolean male = (bits & 1L) == 0L;
        int variants = male ? 30 : 9;
        int index = Math.floorMod(bits >> 1, variants);
        state.texture = texture((male ? "bree_male/" : "bree_female/") + index);
        state.headwearTexture = !male && Math.floorMod(bits >> 5, 4) == 0 ? texture("headwear_female/0") : null;
    }

    @Override
    public Identifier getTextureLocation(CommonNpcRenderState state) {
        return state.texture == null ? FALLBACK_TEXTURE : state.texture;
    }

    @Override
    protected void scale(CommonNpcRenderState state, PoseStack poseStack) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    private static Identifier texture(String path) {
        return Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "textures/entity/common_npc/" + path + ".png");
    }
}
