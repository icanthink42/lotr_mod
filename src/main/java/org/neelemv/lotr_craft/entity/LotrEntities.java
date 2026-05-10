package org.neelemv.lotr_craft.entity;

import java.util.EnumMap;
import java.util.Map;

import org.neelemv.lotr_craft.Lotr_craft;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class LotrEntities {
    private static final Map<HobbitKind, EntityType<LotrHobbitEntity>> HOBBIT_TYPES = new EnumMap<>(HobbitKind.class);
    private static final Map<PassiveKind, EntityType<LotrPassiveEntity>> PASSIVE_TYPES = new EnumMap<>(PassiveKind.class);
    private static final Map<HumanoidNpcKind, EntityType<LotrHumanoidNpcEntity>> HUMANOID_NPC_TYPES = new EnumMap<>(HumanoidNpcKind.class);

    public static final EntityType<LotrCommonNpcEntity> COMMON_NPC = registerCommonNpc();

    public static final EntityType<LotrHobbitEntity> HOBBIT = registerHobbit(HobbitKind.HOBBIT);
    public static final EntityType<LotrHobbitEntity> HOBBIT_BARTENDER = registerHobbit(HobbitKind.HOBBIT_BARTENDER);
    public static final EntityType<LotrHobbitEntity> HOBBIT_BOUNDER = registerHobbit(HobbitKind.HOBBIT_BOUNDER);
    public static final EntityType<LotrHobbitEntity> HOBBIT_SHIRRIFF = registerHobbit(HobbitKind.HOBBIT_SHIRRIFF);
    public static final EntityType<LotrHobbitEntity> HOBBIT_ORCHARDER = registerHobbit(HobbitKind.HOBBIT_ORCHARDER);
    public static final EntityType<LotrHobbitEntity> HOBBIT_FARMER = registerHobbit(HobbitKind.HOBBIT_FARMER);
    public static final EntityType<LotrHobbitEntity> HOBBIT_FARMHAND = registerHobbit(HobbitKind.HOBBIT_FARMHAND);
    public static final EntityType<LotrHobbitEntity> BREE_HOBBIT = registerHobbit(HobbitKind.BREE_HOBBIT);
    public static final EntityType<LotrHobbitEntity> BREE_HOBBIT_INNKEEPER = registerHobbit(HobbitKind.BREE_HOBBIT_INNKEEPER);
    public static final EntityType<LotrHobbitEntity> BREE_HOBBIT_BAKER = registerHobbit(HobbitKind.BREE_HOBBIT_BAKER);
    public static final EntityType<LotrHobbitEntity> BREE_HOBBIT_BUTCHER = registerHobbit(HobbitKind.BREE_HOBBIT_BUTCHER);
    public static final EntityType<LotrHobbitEntity> BREE_HOBBIT_BREWER = registerHobbit(HobbitKind.BREE_HOBBIT_BREWER);
    public static final EntityType<LotrHobbitEntity> BREE_HOBBIT_FLORIST = registerHobbit(HobbitKind.BREE_HOBBIT_FLORIST);

    static {
        for (HumanoidNpcKind kind : HumanoidNpcKind.values()) {
            registerHumanoidNpc(kind);
        }
        for (PassiveKind kind : PassiveKind.values()) {
            registerPassive(kind);
        }
    }

    private LotrEntities() {
    }

    public static void register() {
        FabricDefaultAttributeRegistry.register(COMMON_NPC, LotrCommonNpcEntity.createAttributes());
        for (EntityType<LotrHumanoidNpcEntity> type : HUMANOID_NPC_TYPES.values()) {
            FabricDefaultAttributeRegistry.register(type, LotrHumanoidNpcEntity.createAttributes());
        }
        for (EntityType<LotrHobbitEntity> type : HOBBIT_TYPES.values()) {
            FabricDefaultAttributeRegistry.register(type, LotrHobbitEntity.createAttributes());
        }
        for (Map.Entry<PassiveKind, EntityType<LotrPassiveEntity>> entry : PASSIVE_TYPES.entrySet()) {
            FabricDefaultAttributeRegistry.register(entry.getValue(), LotrPassiveEntity.createAttributes(entry.getKey()));
        }
    }

    public static Iterable<EntityType<LotrHobbitEntity>> hobbitTypes() {
        return HOBBIT_TYPES.values();
    }

    public static EntityType<LotrHobbitEntity> hobbitType(HobbitKind kind) {
        return HOBBIT_TYPES.get(kind);
    }

    public static Iterable<EntityType<LotrHumanoidNpcEntity>> humanoidNpcTypes() {
        return HUMANOID_NPC_TYPES.values();
    }

    public static EntityType<LotrHumanoidNpcEntity> humanoidNpcType(HumanoidNpcKind kind) {
        return HUMANOID_NPC_TYPES.get(kind);
    }

    public static Iterable<EntityType<LotrPassiveEntity>> passiveTypes() {
        return PASSIVE_TYPES.values();
    }

    public static EntityType<LotrPassiveEntity> passiveType(PassiveKind kind) {
        return PASSIVE_TYPES.get(kind);
    }

    private static EntityType<LotrHobbitEntity> registerHobbit(HobbitKind kind) {
        Identifier id = Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, kind.id());
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);
        EntityType<LotrHobbitEntity> type = EntityType.Builder.<LotrHobbitEntity>of(
                        (entityType, level) -> new LotrHobbitEntity(entityType, level, kind), MobCategory.CREATURE)
                .sized(0.45F, 1.2F)
                .eyeHeight(1.02F)
                .clientTrackingRange(8)
                .build(key);
        EntityType<LotrHobbitEntity> registered = Registry.register(BuiltInRegistries.ENTITY_TYPE, key, type);
        HOBBIT_TYPES.put(kind, registered);
        return registered;
    }

    private static EntityType<LotrCommonNpcEntity> registerCommonNpc() {
        Identifier id = Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "common_npc");
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);
        EntityType<LotrCommonNpcEntity> type = EntityType.Builder.<LotrCommonNpcEntity>of(LotrCommonNpcEntity::new, MobCategory.CREATURE)
                .sized(0.6F, 1.8F)
                .eyeHeight(1.62F)
                .clientTrackingRange(8)
                .build(key);
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, type);
    }

    private static EntityType<LotrHumanoidNpcEntity> registerHumanoidNpc(HumanoidNpcKind kind) {
        Identifier id = Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, kind.id());
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);
        EntityType<LotrHumanoidNpcEntity> type = EntityType.Builder.<LotrHumanoidNpcEntity>of(
                        (entityType, level) -> new LotrHumanoidNpcEntity(entityType, level, kind), MobCategory.CREATURE)
                .sized(0.6F * kind.scale(), 1.8F * kind.scale())
                .eyeHeight(1.62F * kind.scale())
                .clientTrackingRange(8)
                .build(key);
        EntityType<LotrHumanoidNpcEntity> registered = Registry.register(BuiltInRegistries.ENTITY_TYPE, key, type);
        HUMANOID_NPC_TYPES.put(kind, registered);
        return registered;
    }

    private static EntityType<LotrPassiveEntity> registerPassive(PassiveKind kind) {
        Identifier id = Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, kind.id());
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);
        EntityType<LotrPassiveEntity> type = EntityType.Builder.<LotrPassiveEntity>of(
                        (entityType, level) -> new LotrPassiveEntity(entityType, level, kind), MobCategory.CREATURE)
                .sized(kind.width(), kind.height())
                .eyeHeight(kind.height() * 0.85F)
                .clientTrackingRange(8)
                .build(key);
        EntityType<LotrPassiveEntity> registered = Registry.register(BuiltInRegistries.ENTITY_TYPE, key, type);
        PASSIVE_TYPES.put(kind, registered);
        return registered;
    }
}
