package org.neelemv.lotr_craft.client.render.entity;

import java.util.Locale;
import java.util.UUID;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.entity.LotrHumanoidNpcEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;

public class HumanoidNpcRenderer extends HumanoidMobRenderer<LotrHumanoidNpcEntity, HumanoidNpcRenderState, HumanoidModel<HumanoidNpcRenderState>> {
    private static final Identifier FALLBACK_TEXTURE = texture("bree/bree_male/0.png");

    public HumanoidNpcRenderer(EntityRendererProvider.Context context) {
        this(context, new HumanoidNpcModel(context.bakeLayer(HumanoidNpcModel.LAYER_LOCATION)));
    }

    protected HumanoidNpcRenderer(EntityRendererProvider.Context context, HumanoidModel<HumanoidNpcRenderState> model) {
        super(context, model, 0.5F);
        addLayer(new HumanoidArmorLayer<>(
                this,
                ArmorModelSet.bake(ModelLayers.PLAYER_ARMOR, context.getModelSet(), HumanoidModel::new),
                context.getEquipmentRenderer()));
        addLayer(new HumanoidNpcOverlayLayer(this));
    }

    @Override
    public HumanoidNpcRenderState createRenderState() {
        return new HumanoidNpcRenderState();
    }

    @Override
    public void extractRenderState(LotrHumanoidNpcEntity entity, HumanoidNpcRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        UUID uuid = entity.getUUID();
        long bits = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
        state.texture = texture(entity.kind().skinSet().baseTexture(bits));
        state.overlayTexture = entity.kind().overlayTexture() == null ? null : texture(entity.kind().overlayTexture());
        state.headwearTexture = entity.kind().skinSet().headwearTexture(bits) == null ? null : texture(entity.kind().skinSet().headwearTexture(bits));
        state.scale = entity.kind().scale();
        state.orcModelFeatures = entity.kind().skinSet().isOrc();
        state.offhandShieldBlocking = state.isUsingItem
                && state.useItemHand == InteractionHand.OFF_HAND
                && entity.getOffhandItem().has(DataComponents.BLOCKS_ATTACKS);
        float attackTime = entity.lotrAttackAnimation(partialTick);
        if (attackTime > state.attackTime) {
            state.attackTime = attackTime;
            state.attackArm = entity.getMainArm();
            state.isUsingItem = false;
            state.offhandShieldBlocking = false;
        }
    }

    @Override
    public Identifier getTextureLocation(HumanoidNpcRenderState state) {
        return state.texture == null ? FALLBACK_TEXTURE : state.texture;
    }

    @Override
    protected void scale(HumanoidNpcRenderState state, PoseStack poseStack) {
        poseStack.scale(state.scale, state.scale, state.scale);
    }

    private static Identifier texture(String path) {
        return Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "textures/entity/npc/" + path.toLowerCase(Locale.ROOT));
    }
}
