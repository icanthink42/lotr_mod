package org.neelemv.lotr_craft.entity;

import org.neelemv.lotr_craft.entity.goal.FactionAllyAssistGoal;
import org.neelemv.lotr_craft.entity.goal.FactionHurtByTargetGoal;
import org.neelemv.lotr_craft.entity.goal.FactionNearestAttackableTargetGoal;
import org.neelemv.lotr_craft.faction.LotrFaction;
import org.neelemv.lotr_craft.faction.PlayerAlignments;
import org.neelemv.lotr_craft.item.LotrEquipment;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
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
    private static final EntityDataAccessor<Integer> ATTACK_ANIMATION_TICKS = SynchedEntityData.defineId(LotrHumanoidNpcEntity.class, EntityDataSerializers.INT);
    private static final int ATTACK_ANIMATION_DURATION = 10;

    private final HumanoidNpcKind kind;
    private int meleeAttackCooldown;

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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACK_ANIMATION_TICKS, 0);
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
            this.goalSelector.addGoal(2, new LotrMeleeAttackGoal(this, 1.15, true));
        } else {
            this.goalSelector.addGoal(1, new LotrMeleeAttackGoal(this, 1.15, true));
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
        stopShieldBlocking();
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
    public void aiStep() {
        super.aiStep();
        tickAttackAnimation();
        tickMeleeAttackCooldown();
        updateCombatSprinting();
        updateShieldBlocking();
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (hasUsableShield() && meleeAttackCooldown > 0) {
            if (!isUsingItem() || getUsedItemHand() != InteractionHand.OFF_HAND) {
                startUsingItem(InteractionHand.OFF_HAND);
            }
            return false;
        }
        stopShieldBlocking();
        triggerAttackAnimation();
        meleeAttackCooldown = LotrEquipment.meleeAttackCooldownTicks(kind);
        boolean attacked = super.doHurtTarget(level, target);
        if (attacked) {
            triggerAttackAnimation();
        }
        return attacked;
    }

    private void updateCombatSprinting() {
        LivingEntity target = getTarget();
        boolean shouldSprint = target != null
                && target.isAlive()
                && !LotrEquipment.isRanged(kind)
                && (hasUsableShield() || target.distanceToSqr(this) > 9.0D);
        setSprinting(shouldSprint);
    }

    private void updateShieldBlocking() {
        if (level().isClientSide()) {
            return;
        }
        if (shouldShieldBlock()) {
            if (!isUsingItem() || getUsedItemHand() != InteractionHand.OFF_HAND) {
                startUsingItem(InteractionHand.OFF_HAND);
            }
        } else {
            stopShieldBlocking();
        }
    }

    public float lotrAttackAnimation(float partialTick) {
        int ticks = entityData.get(ATTACK_ANIMATION_TICKS);
        if (ticks <= 0) {
            return 0.0F;
        }
        return 1.0F - Math.max(0.0F, ticks - partialTick) / (float) ATTACK_ANIMATION_DURATION;
    }

    private void tickAttackAnimation() {
        int ticks = entityData.get(ATTACK_ANIMATION_TICKS);
        if (ticks > 0) {
            entityData.set(ATTACK_ANIMATION_TICKS, ticks - 1);
        }
    }

    private void triggerAttackAnimation() {
        entityData.set(ATTACK_ANIMATION_TICKS, ATTACK_ANIMATION_DURATION);
        swing(InteractionHand.MAIN_HAND, true);
    }

    private void tickMeleeAttackCooldown() {
        if (meleeAttackCooldown > 0) {
            meleeAttackCooldown--;
        }
    }

    private boolean shouldShieldBlock() {
        if (LotrEquipment.isRanged(kind) || meleeAttackCooldown <= 0) {
            return false;
        }
        LivingEntity target = getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        return hasUsableShield();
    }

    private boolean hasUsableShield() {
        ItemStack shield = getOffhandItem();
        return !shield.isEmpty() && shield.has(DataComponents.BLOCKS_ATTACKS);
    }

    private void stopShieldBlocking() {
        if (isUsingItem() && getUsedItemHand() == InteractionHand.OFF_HAND) {
            stopUsingItem();
        }
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    private static final class LotrMeleeAttackGoal extends MeleeAttackGoal {
        private int ticksUntilWeaponReady;

        private LotrMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(mob, speedModifier, followingTargetEvenIfNotSeen);
        }

        @Override
        public void start() {
            super.start();
            ticksUntilWeaponReady = 0;
        }

        @Override
        public void tick() {
            ticksUntilWeaponReady = Math.max(0, ticksUntilWeaponReady - 1);
            super.tick();
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity target) {
            if (!canPerformAttack(target) || ticksUntilWeaponReady > 0) {
                return;
            }
            ticksUntilWeaponReady = weaponCooldown();
            mob.swing(InteractionHand.MAIN_HAND);
            mob.doHurtTarget(getServerLevel(mob), target);
        }

        private int weaponCooldown() {
            if (mob instanceof LotrHumanoidNpcEntity npc) {
                return LotrEquipment.meleeAttackCooldownTicks(npc.kind());
            }
            return 20;
        }
    }
}
