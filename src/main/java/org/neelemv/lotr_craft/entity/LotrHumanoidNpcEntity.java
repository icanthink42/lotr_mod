package org.neelemv.lotr_craft.entity;

import org.neelemv.lotr_craft.entity.goal.FactionAllyAssistGoal;
import org.neelemv.lotr_craft.entity.goal.FactionHurtByTargetGoal;
import org.neelemv.lotr_craft.entity.goal.FactionNearestAttackableTargetGoal;
import org.neelemv.lotr_craft.faction.LotrFaction;
import org.neelemv.lotr_craft.faction.PlayerAlignments;
import org.neelemv.lotr_craft.item.LotrEquipment;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class LotrHumanoidNpcEntity extends PathfinderMob implements LotrFactioned, RangedAttackMob {
    private final HumanoidNpcKind kind;

    public LotrHumanoidNpcEntity(EntityType<? extends LotrHumanoidNpcEntity> entityType, Level level, HumanoidNpcKind kind) {
        super(entityType, level);
        this.kind = kind;
        this.xpReward = 1;
        registerCombatGoal();
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
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F, 0.05F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new FactionHurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new FactionAllyAssistGoal(this));
        this.targetSelector.addGoal(3, new FactionNearestAttackableTargetGoal(this));
    }

    private void registerCombatGoal() {
        if (LotrEquipment.isRanged(kind)) {
            this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.05, 30, 18.0F));
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15, true));
        } else {
            this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.15, true));
        }
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (target instanceof Player player && !PlayerAlignments.isHostileTo(player, lotrFaction())) {
            return false;
        }
        if (target instanceof LotrFactioned factionedTarget && LotrFactioned.areFriendly(this, factionedTarget)) {
            return false;
        }
        return super.canAttack(target);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
        LotrEquipment.equipHumanoid(this, kind);
        return data;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        ItemStack weapon = getMainHandItem();
        AbstractArrow arrow = ProjectileUtil.getMobArrow(this, new ItemStack(Items.ARROW), velocity, weapon);
        double dx = target.getX() - getX();
        double dy = target.getY(0.3333333333333333D) - arrow.getY();
        double dz = target.getZ() - getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        arrow.shoot(dx, dy + horizontal * 0.2D, dz, 1.6F, 8.0F);
        serverLevel.addFreshEntity(arrow);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }
}
