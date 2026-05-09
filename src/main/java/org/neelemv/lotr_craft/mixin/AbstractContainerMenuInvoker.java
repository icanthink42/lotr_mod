package org.neelemv.lotr_craft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

@Mixin(AbstractContainerMenu.class)
public interface AbstractContainerMenuInvoker {
    @Invoker("addSlot")
    Slot lotr_craft$addSlot(Slot slot);

    @Invoker("moveItemStackTo")
    boolean lotr_craft$moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection);
}
