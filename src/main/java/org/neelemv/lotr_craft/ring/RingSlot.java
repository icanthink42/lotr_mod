package org.neelemv.lotr_craft.ring;

import org.neelemv.lotr_craft.item.RingWearable;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class RingSlot extends Slot {
    public RingSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof RingWearable;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
