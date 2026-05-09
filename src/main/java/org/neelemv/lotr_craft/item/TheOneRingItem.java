package org.neelemv.lotr_craft.item;

import net.minecraft.world.item.ItemStack;

public class TheOneRingItem extends RingItem {
    public TheOneRingItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
