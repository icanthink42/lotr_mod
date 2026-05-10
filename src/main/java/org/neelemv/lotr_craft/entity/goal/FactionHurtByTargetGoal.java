package org.neelemv.lotr_craft.entity.goal;

import org.neelemv.lotr_craft.entity.LotrFactioned;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class FactionHurtByTargetGoal extends HurtByTargetGoal {
    private final PathfinderMob mob;

    public FactionHurtByTargetGoal(PathfinderMob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        LivingEntity attacker = mob.getLastHurtByMob();
        return attacker != null && attacker instanceof LotrFactioned target && mob instanceof LotrFactioned self && LotrFactioned.areEnemies(self, target) && super.canUse();
    }

    @Override
    protected void alertOther(Mob other, LivingEntity attacker) {
        if (attacker instanceof LotrFactioned target && other instanceof LotrFactioned ally && mob instanceof LotrFactioned self
                && LotrFactioned.areAllied(self, ally) && LotrFactioned.areEnemies(ally, target) && other.canAttack(attacker)) {
            other.setTarget(attacker);
        }
    }
}
