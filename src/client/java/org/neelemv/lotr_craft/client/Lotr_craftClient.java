package org.neelemv.lotr_craft.client;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.client.gui.FactionsScreen;
import org.neelemv.lotr_craft.client.gui.MiddleEarthMapScreen;
import org.neelemv.lotr_craft.client.render.entity.HobbitModel;
import org.neelemv.lotr_craft.client.render.entity.HobbitRenderer;
import org.neelemv.lotr_craft.entity.LotrEntities;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;

public class Lotr_craftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LotrKeyMappings.register();
        ModelLayerRegistry.registerModelLayer(HobbitModel.LAYER_LOCATION, HobbitModel::createBodyLayer);
        for (var type : LotrEntities.hobbitTypes()) {
            EntityRendererRegistry.register(type, HobbitRenderer::new);
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
