package org.neelemv.lotr_craft.item;

import org.neelemv.lotr_craft.network.LotrNetworking;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class FactionBookItem extends Item {
    public FactionBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            LotrNetworking.syncFactionAlignments(serverPlayer);
        }
        return InteractionResult.SUCCESS;
    }
}
