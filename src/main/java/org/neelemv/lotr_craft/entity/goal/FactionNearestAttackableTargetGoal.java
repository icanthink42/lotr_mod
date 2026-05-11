package org.neelemv.lotr_craft.entity.goal;

import org.neelemv.lotr_craft.entity.LotrFactioned;
import org.neelemv.lotr_craft.faction.PlayerAlignments;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class FactionNearestAttackableTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
    public FactionNearestAttackableTargetGoal(Mob mob) {
        super(mob, LivingEntity.class, 20, true, false, (target, level) -> {
            if (!(mob instanceof LotrFactioned self)) {
                return false;
            }
            if (target instanceof Player player) {
                return !player.isCreative() && !player.isSpectator() && PlayerAlignments.isHostileTo(player, self.lotrFaction());
            }
            return target instanceof LotrFactioned factionedTarget && LotrFactioned.areEnemies(self, factionedTarget);
        });
    }
}
