package org.neelemv.lotr_craft.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class LotrPassiveEntity extends PathfinderMob {
    private final PassiveKind kind;

    public LotrPassiveEntity(EntityType<? extends LotrPassiveEntity> entityType, Level level, PassiveKind kind) {
        super(entityType, level);
        this.kind = kind;
    }

    public static AttributeSupplier.Builder createAttributes(PassiveKind kind) {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, kind.health())
                .add(Attributes.MOVEMENT_SPEED, kind.speed());
    }

    public PassiveKind kind() {
        return kind;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0));
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }
}
