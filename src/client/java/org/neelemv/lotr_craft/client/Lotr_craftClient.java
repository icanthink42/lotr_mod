package org.neelemv.lotr_craft.client;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.client.gui.FactionsScreen;
import org.neelemv.lotr_craft.client.gui.MiddleEarthMapScreen;
import org.neelemv.lotr_craft.client.render.entity.CommonNpcModel;
import org.neelemv.lotr_craft.client.render.entity.CommonNpcRenderer;
import org.neelemv.lotr_craft.client.render.entity.HalfTrollNpcModel;
import org.neelemv.lotr_craft.client.render.entity.HalfTrollNpcRenderer;
import org.neelemv.lotr_craft.client.render.entity.HobbitModel;
import org.neelemv.lotr_craft.client.render.entity.HobbitRenderer;
import org.neelemv.lotr_craft.client.render.entity.HumanoidNpcModel;
import org.neelemv.lotr_craft.client.render.entity.HumanoidNpcRenderer;
import org.neelemv.lotr_craft.client.render.entity.LotrCustomHelmetLayer;
import org.neelemv.lotr_craft.client.render.entity.LotrCustomHelmetModel;
import org.neelemv.lotr_craft.client.render.entity.OrcHumanoidNpcModel;
import org.neelemv.lotr_craft.client.render.entity.OrcHumanoidNpcRenderer;
import org.neelemv.lotr_craft.client.render.entity.PassiveModel;
import org.neelemv.lotr_craft.client.render.entity.PassiveRenderer;
import org.neelemv.lotr_craft.entity.HumanoidNpcKind;
import org.neelemv.lotr_craft.entity.LotrEntities;
import org.neelemv.lotr_craft.entity.PassiveKind;
import org.neelemv.lotr_craft.network.FactionAlignmentSyncPayload;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.InteractionResult;

public class Lotr_craftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LotrKeyMappings.register();
        ClientPlayNetworking.registerGlobalReceiver(FactionAlignmentSyncPayload.TYPE, (payload, context) -> ClientFactionAlignments.replaceFrom(payload.encodedAlignments()));
        ModelLayerRegistry.registerModelLayer(CommonNpcModel.LAYER_LOCATION, CommonNpcModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(HobbitModel.LAYER_LOCATION, HobbitModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(HumanoidNpcModel.LAYER_LOCATION, HumanoidNpcModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(HalfTrollNpcModel.LAYER_LOCATION, HalfTrollNpcModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(OrcHumanoidNpcModel.LAYER_LOCATION, OrcHumanoidNpcModel::createBodyLayer);
        for (LotrCustomHelmetModel.Variant variant : LotrCustomHelmetModel.Variant.values()) {
            ModelLayerRegistry.registerModelLayer(variant.layer(), () -> LotrCustomHelmetModel.createLayer(variant));
        }
        for (PassiveKind kind : PassiveKind.values()) {
            ModelLayerRegistry.registerModelLayer(PassiveModel.layerLocation(kind), () -> PassiveModel.createBodyLayer(kind));
        }
        LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType, renderer, helper, context) -> {
            if (renderer.getModel() instanceof HumanoidModel<?>) {
                @SuppressWarnings({ "rawtypes", "unchecked" })
                LotrCustomHelmetLayer<?, ?> layer = new LotrCustomHelmetLayer((RenderLayerParent<HumanoidRenderState, HumanoidModel<HumanoidRenderState>>) (RenderLayerParent) renderer, context);
                helper.register(layer);
            }
        });
        EntityRendererRegistry.register(LotrEntities.COMMON_NPC, CommonNpcRenderer::new);
        for (HumanoidNpcKind kind : HumanoidNpcKind.values()) {
            switch (kind) {
                case HALF_TROLL, HALF_TROLL_WARRIOR, HALF_TROLL_SCAVENGER, HALF_TROLL_WARLORD ->
                    EntityRendererRegistry.register(LotrEntities.humanoidNpcType(kind), HalfTrollNpcRenderer::new);
                default ->
                    EntityRendererRegistry.register(LotrEntities.humanoidNpcType(kind), kind.skinSet().isOrc() ? OrcHumanoidNpcRenderer::new : HumanoidNpcRenderer::new);
            }
        }
        for (var type : LotrEntities.hobbitTypes()) {
            EntityRendererRegistry.register(type, HobbitRenderer::new);
        }
        for (PassiveKind kind : PassiveKind.values()) {
            EntityRendererRegistry.register(LotrEntities.passiveType(kind), context -> new PassiveRenderer(context, kind));
        }

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (LotrKeyMappings.OPEN_MAP.consumeClick()) {
                if (client.player != null && (client.player.isCreative() || client.player.isSpectator())) {
                    client.setScreen(new MiddleEarthMapScreen());
                }
            }
        });

        UseItemCallback.EVENT.register((player, level, hand) -> {
            if (!level.isClientSide()) {
                return InteractionResult.PASS;
            }

            if (player.getItemInHand(hand).getItem() == Lotr_craft.MIDDLE_EARTH_MAP) {
                Minecraft.getInstance().setScreen(new MiddleEarthMapScreen());
                return InteractionResult.SUCCESS;
            }
            if (player.getItemInHand(hand).getItem() == Lotr_craft.FACTION_BOOK) {
                Minecraft.getInstance().setScreen(new FactionsScreen());
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
    }

}
