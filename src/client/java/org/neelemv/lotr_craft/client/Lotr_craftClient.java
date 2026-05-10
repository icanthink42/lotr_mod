package org.neelemv.lotr_craft.client;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.client.gui.MiddleEarthMapScreen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;

public class Lotr_craftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LotrKeyMappings.register();

        UseItemCallback.EVENT.register((player, level, hand) -> {
            if (!level.isClientSide() || player.getItemInHand(hand).getItem() != Lotr_craft.MIDDLE_EARTH_MAP) {
                return InteractionResult.PASS;
            }

            Minecraft.getInstance().setScreen(new MiddleEarthMapScreen());
            return InteractionResult.SUCCESS;
        });
    }
}
