package org.neelemv.lotr_craft.entity.goal;

import java.util.EnumSet;

import org.neelemv.lotr_craft.entity.LotrFactioned;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class FactionAllyAssistGoal extends Goal {
    private final PathfinderMob mob;
    private int lastCheckedTick;

    public FactionAllyAssistGoal(PathfinderMob mob) {
        this.mob = mob;
        setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (!(mob instanceof LotrFactioned self) || mob.tickCount - lastCheckedTick < 20) {
            return false;
        }
        lastCheckedTick = mob.tickCount;
        for (LivingEntity nearby : mob.level().getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(16.0, 8.0, 16.0))) {
            if (nearby == mob || !(nearby instanceof LotrFactioned ally) || !LotrFactioned.areAllied(self, ally)) {
                continue;
            }
            LivingEntity target = nearby.getLastHurtByMob();
            if (target instanceof LotrFactioned factionedTarget && LotrFactioned.areEnemies(self, factionedTarget) && mob.canAttack(target)) {
                mob.setTarget(target);
                return true;
            }
        }
        return false;
    }
}
