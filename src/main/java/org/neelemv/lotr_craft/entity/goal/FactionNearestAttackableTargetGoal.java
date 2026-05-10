package org.neelemv.lotr_craft.entity.goal;

import org.neelemv.lotr_craft.entity.LotrFactioned;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class FactionNearestAttackableTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
    public FactionNearestAttackableTargetGoal(Mob mob) {
        super(mob, LivingEntity.class, 20, true, false, (target, level) -> target instanceof LotrFactioned factionedTarget && LotrFactioned.areEnemies((LotrFactioned) mob, factionedTarget));
    }
}
