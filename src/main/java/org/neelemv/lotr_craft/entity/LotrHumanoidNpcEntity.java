package org.neelemv.lotr_craft.entity;

import org.neelemv.lotr_craft.entity.goal.FactionAllyAssistGoal;
import org.neelemv.lotr_craft.entity.goal.FactionHurtByTargetGoal;
import org.neelemv.lotr_craft.entity.goal.FactionNearestAttackableTargetGoal;
import org.neelemv.lotr_craft.faction.LotrFaction;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class LotrHumanoidNpcEntity extends PathfinderMob implements LotrFactioned {
    private final HumanoidNpcKind kind;

    public LotrHumanoidNpcEntity(EntityType<? extends LotrHumanoidNpcEntity> entityType, Level level, HumanoidNpcKind kind) {
        super(entityType, level);
        this.kind = kind;
        this.xpReward = 1;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    public HumanoidNpcKind kind() {
        return kind;
    }

    @Override
    public LotrFaction lotrFaction() {
        return kind.faction();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.15, true));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F, 0.05F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new FactionHurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new FactionAllyAssistGoal(this));
        this.targetSelector.addGoal(3, new FactionNearestAttackableTargetGoal(this));
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (target instanceof LotrFactioned factionedTarget && LotrFactioned.areFriendly(this, factionedTarget)) {
            return false;
        }
        return super.canAttack(target);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }
}
