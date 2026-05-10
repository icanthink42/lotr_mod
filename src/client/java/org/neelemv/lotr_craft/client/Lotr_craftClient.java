package org.neelemv.lotr_craft.client;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.client.gui.FactionsScreen;
import org.neelemv.lotr_craft.client.gui.MiddleEarthMapScreen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;

public class Lotr_craftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LotrKeyMappings.register();

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
