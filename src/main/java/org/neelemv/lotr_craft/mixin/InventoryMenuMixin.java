package org.neelemv.lotr_craft.mixin;

import org.neelemv.lotr_craft.ring.PlayerRingAccess;
import org.neelemv.lotr_craft.ring.RingSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;

@Mixin(InventoryMenu.class)
public class InventoryMenuMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void lotr_craft$addRingSlot(Inventory inventory, boolean active, Player owner, CallbackInfo ci) {
        if (owner instanceof PlayerRingAccess ringAccess) {
            ((AbstractContainerMenuInvoker) this).lotr_craft$addSlot(new RingSlot(ringAccess.lotr_craft$getRingInventory(), 0, 77, 44));
        }
    }
}
