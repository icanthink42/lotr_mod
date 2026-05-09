package org.neelemv.lotr_craft.item;

import org.neelemv.lotr_craft.ring.PlayerRingAccess;

import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RingItem extends Item implements RingWearable {
    public RingItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && player instanceof PlayerRingAccess ringAccess) {
            Container ringInventory = ringAccess.lotr_craft$getRingInventory();
            ItemStack heldStack = player.getItemInHand(hand);
            ItemStack equippedStack = ringInventory.getItem(0);

            ringInventory.setItem(0, heldStack.copyWithCount(1));
            player.setItemInHand(hand, equippedStack);
            ringInventory.setChanged();
            player.containerMenu.broadcastChanges();
        }

        return InteractionResult.SUCCESS;
    }
}
