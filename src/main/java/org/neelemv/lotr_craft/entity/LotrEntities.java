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

    private LotrEntities() {
    }

    public static void register() {
        for (EntityType<LotrHobbitEntity> type : HOBBIT_TYPES.values()) {
            FabricDefaultAttributeRegistry.register(type, LotrHobbitEntity.createAttributes());
        }
    }

    public static Iterable<EntityType<LotrHobbitEntity>> hobbitTypes() {
        return HOBBIT_TYPES.values();
    }

    public static EntityType<LotrHobbitEntity> hobbitType(HobbitKind kind) {
        return HOBBIT_TYPES.get(kind);
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
}
