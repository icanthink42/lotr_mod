package org.neelemv.lotr_craft.mixin;

import org.neelemv.lotr_craft.faction.LotrFaction;
import org.neelemv.lotr_craft.faction.PlayerAlignmentAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

@Mixin(Player.class)
public abstract class PlayerAlignmentMixin implements PlayerAlignmentAccess {
    @Unique
    private static final String LOTR_CRAFT_ALIGNMENTS_KEY = "lotr_craft_alignments";

    @Unique
    private final float[] lotr_craft$alignments = new float[LotrFaction.values().length];

    @Override
    public float lotr_craft$getAlignment(LotrFaction faction) {
        return faction.hasFixedAlignment() ? faction.fixedAlignment() : lotr_craft$alignments[faction.ordinal()];
    }

    @Override
    public void lotr_craft$setAlignment(LotrFaction faction, float alignment) {
        if (!faction.hasFixedAlignment()) {
            lotr_craft$alignments[faction.ordinal()] = alignment;
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void lotr_craft$saveAlignments(ValueOutput output, CallbackInfo ci) {
        int[] stored = new int[lotr_craft$alignments.length];
        for (int i = 0; i < lotr_craft$alignments.length; i++) {
            stored[i] = Math.round(lotr_craft$alignments[i] * 1000.0F);
        }
        output.putIntArray(LOTR_CRAFT_ALIGNMENTS_KEY, stored);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void lotr_craft$readAlignments(ValueInput input, CallbackInfo ci) {
        int[] stored = input.getIntArray(LOTR_CRAFT_ALIGNMENTS_KEY).orElse(null);
        if (stored == null) {
            return;
        }
        int length = Math.min(stored.length, lotr_craft$alignments.length);
        for (int i = 0; i < length; i++) {
            lotr_craft$alignments[i] = stored[i] / 1000.0F;
        }
    }
}
