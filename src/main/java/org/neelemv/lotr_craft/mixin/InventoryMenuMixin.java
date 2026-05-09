package org.neelemv.lotr_craft.mixin;

import org.neelemv.lotr_craft.item.RingWearable;
import org.neelemv.lotr_craft.ring.PlayerRingAccess;
import org.neelemv.lotr_craft.ring.RingSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

@Mixin(InventoryMenu.class)
public class InventoryMenuMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void lotr_craft$addRingSlot(Inventory inventory, boolean active, Player owner, CallbackInfo ci) {
        if (owner instanceof PlayerRingAccess ringAccess) {
            ((AbstractContainerMenuInvoker) this).lotr_craft$addSlot(new RingSlot(ringAccess.lotr_craft$getRingInventory(), 0, 77, 44));
        }
    }

    @Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true)
    private void lotr_craft$quickMoveRing(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
        InventoryMenu menu = (InventoryMenu) (Object) this;
        if (index < 0 || index >= menu.slots.size()) {
            return;
        }

        int ringSlotIndex = lotr_craft$getRingSlotIndex(menu);
        if (ringSlotIndex == -1 || index == ringSlotIndex) {
            return;
        }

        Slot clickedSlot = menu.slots.get(index);
        Slot ringSlot = menu.slots.get(ringSlotIndex);
        if (!clickedSlot.hasItem() || ringSlot.hasItem()) {
            return;
        }

        ItemStack clickedStack = clickedSlot.getItem();
        if (!(clickedStack.getItem() instanceof RingWearable)) {
            return;
        }

        ItemStack originalStack = clickedStack.copy();
        if (!((AbstractContainerMenuInvoker) this).lotr_craft$moveItemStackTo(clickedStack, ringSlotIndex, ringSlotIndex + 1, false)) {
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }

        if (clickedStack.isEmpty()) {
            clickedSlot.setByPlayer(ItemStack.EMPTY, originalStack);
        } else {
            clickedSlot.setChanged();
        }

        if (clickedStack.getCount() == originalStack.getCount()) {
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }

        clickedSlot.onTake(player, clickedStack);
        cir.setReturnValue(originalStack);
    }

    private static int lotr_craft$getRingSlotIndex(InventoryMenu menu) {
        for (int slotIndex = 0; slotIndex < menu.slots.size(); slotIndex++) {
            if (menu.slots.get(slotIndex) instanceof RingSlot) {
                return slotIndex;
            }
        }

        return -1;
    }
}
