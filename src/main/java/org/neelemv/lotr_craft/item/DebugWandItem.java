package org.neelemv.lotr_craft.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DebugWandItem extends Item {
    private static final float VANILLA_FLY_SPEED = 0.05f;
    private static final float BOOSTED_FLY_SPEED = VANILLA_FLY_SPEED * 20f;

    public DebugWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        Abilities abilities = player.getAbilities();
        if (abilities.getFlyingSpeed() >= BOOSTED_FLY_SPEED) {
            abilities.setFlyingSpeed(VANILLA_FLY_SPEED);
        } else {
            abilities.setFlyingSpeed(BOOSTED_FLY_SPEED);
        }
        player.onUpdateAbilities();

        return InteractionResult.SUCCESS;
    }
}
