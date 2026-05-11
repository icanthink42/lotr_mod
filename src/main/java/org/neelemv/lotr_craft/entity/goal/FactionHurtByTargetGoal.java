package org.neelemv.lotr_craft.entity.goal;

import org.neelemv.lotr_craft.entity.LotrFactioned;
import org.neelemv.lotr_craft.faction.PlayerAlignments;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;

public class FactionHurtByTargetGoal extends HurtByTargetGoal {
    private final PathfinderMob mob;

    public FactionHurtByTargetGoal(PathfinderMob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        LivingEntity attacker = mob.getLastHurtByMob();
        return attacker != null && mob instanceof LotrFactioned self && isEnemy(self, attacker) && super.canUse();
    }

    @Override
    protected void alertOther(Mob other, LivingEntity attacker) {
        if (other instanceof LotrFactioned ally && mob instanceof LotrFactioned self
                && LotrFactioned.areAllied(self, ally) && isEnemy(ally, attacker) && other.canAttack(attacker)) {
            other.setTarget(attacker);
        }
    }

    private static boolean isEnemy(LotrFactioned self, LivingEntity target) {
        if (target instanceof Player player) {
            return !player.isCreative() && !player.isSpectator() && PlayerAlignments.isHostileTo(player, self.lotrFaction());
        }
        return target instanceof LotrFactioned factionedTarget && LotrFactioned.areEnemies(self, factionedTarget);
    }
}
