package org.neelemv.lotr_craft.mixin;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.ring.PlayerRingAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

@Mixin(Player.class)
public class PlayerMixin implements PlayerRingAccess {
    @Unique
    private static final String LOTR_CRAFT_RING_SLOT_KEY = "lotr_craft:RingSlot";

    @Unique
    private final SimpleContainer lotr_craft$ringInventory = new SimpleContainer(1);

    @Override
    public Container lotr_craft$getRingInventory() {
        return lotr_craft$ringInventory;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void lotr_craft$tickRingEffects(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (player.level().isClientSide()) {
            return;
        }

        if (lotr_craft$ringInventory.getItem(0).getItem() == Lotr_craft.THE_ONE_RING) {
            MobEffectInstance invisibility = player.getEffect(MobEffects.INVISIBILITY);
            if (invisibility == null || invisibility.getDuration() <= 10 || invisibility.isVisible() || invisibility.showIcon()) {
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 20, 0, false, false, false));
            }
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void lotr_craft$readRingSlot(ValueInput input, CallbackInfo ci) {
        lotr_craft$ringInventory.fromItemList(input.listOrEmpty(LOTR_CRAFT_RING_SLOT_KEY, ItemStack.CODEC));
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void lotr_craft$writeRingSlot(ValueOutput output, CallbackInfo ci) {
        lotr_craft$ringInventory.storeAsItemList(output.list(LOTR_CRAFT_RING_SLOT_KEY, ItemStack.CODEC));
    }
}
