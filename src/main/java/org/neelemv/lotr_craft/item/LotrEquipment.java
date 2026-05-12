package org.neelemv.lotr_craft.item;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.entity.HumanoidNpcKind;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.ArmorType;

public final class LotrEquipment {
    private static final Map<Weapon, Item> WEAPONS = new EnumMap<>(Weapon.class);
    private static final Map<ArmorPiece, Item> ARMOR = new EnumMap<>(ArmorPiece.class);

    private LotrEquipment() {
    }

    public static List<Item> registerItems() {
        List<Item> items = new ArrayList<>();
        for (Weapon weapon : Weapon.values()) {
            Item item = Lotr_craft.registerItem(weapon.id(), weapon.isBow() ? BowItem::new : Item::new, weapon.properties());
            WEAPONS.put(weapon, item);
            items.add(item);
        }
        ArmorFamily.values();
        for (ArmorPiece armor : ArmorPiece.values()) {
            Item item = Lotr_craft.registerItem(armor.id(), Item::new, armor.properties());
            ARMOR.put(armor, item);
            items.add(item);
        }
        return List.copyOf(items);
    }

    private static ArmorMaterial armorMaterial(String assetName) {
        return new ArmorMaterial(
                15,
                Map.of(
                        ArmorType.BOOTS, 2,
                        ArmorType.LEGGINGS, 5,
                        ArmorType.CHESTPLATE, 6,
                        ArmorType.HELMET, 2,
                        ArmorType.BODY, 5),
                9,
                armorEquipSound(assetName),
                0.0F,
                0.0F,
                ItemTags.REPAIRS_IRON_ARMOR,
                ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, assetName)));
    }

    private static Holder<SoundEvent> armorEquipSound(String assetName) {
        if (assetName.contains("dwarven") || assetName.contains("gondor") || assetName.contains("uruk") || assetName.contains("utumno")) {
            return SoundEvents.ARMOR_EQUIP_IRON;
        }
        if (assetName.contains("hithlain") || assetName.contains("elven")) {
            return SoundEvents.ARMOR_EQUIP_CHAIN;
        }
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    public static boolean isRanged(HumanoidNpcKind kind) {
        String id = kind.id();
        return id.contains("archer") || id.contains("crossbower") || id.contains("blowgunner") || id.contains("ranger") || id.contains("dunedain");
    }

    public static ItemStack rangedWeaponFor(HumanoidNpcKind kind) {
        return new ItemStack(WEAPONS.get(switch (kind.faction()) {
            case GONDOR -> Weapon.GONDOR_BOW;
            case RANGER_NORTH -> Weapon.RANGER_BOW;
            case ROHAN -> Weapon.ROHAN_BOW;
            case DALE -> Weapon.DALE_BOW;
            case HIGH_ELF, LOTHLORIEN -> Weapon.ELVEN_BOW;
            case WOOD_ELF, DORWINION -> Weapon.WOOD_ELVEN_BOW;
            case DURINS_FOLK, BLUE_MOUNTAINS -> Weapon.DWARVEN_CROSSBOW;
            case MORDOR, ANGMAR, DOL_GULDUR, GUNDABAD -> Weapon.ORC_BOW;
            case ISENGARD -> Weapon.URUK_BOW;
            case RHUDEL -> Weapon.RHUN_BOW;
            case NEAR_HARAD, MORWAITH -> Weapon.HARAD_BOW;
            case TAURETHRIM -> Weapon.TAUREDAIN_BLOWGUN;
            default -> Weapon.IRON_BOW;
        }));
    }

    public static ItemStack meleeWeaponFor(HumanoidNpcKind kind) {
        if (kind.id().contains("axe_thrower") || kind.id().contains("axeman")) {
            return stack(weaponForFamily(kind, Weapon.BATTLEAXE_IRON));
        }
        if (kind.id().contains("berserker") || kind.id().contains("half_troll")) {
            return stack(weaponForFamily(kind, Weapon.BATTLEAXE_IRON));
        }
        return stack(weaponForFamily(kind, Weapon.SWORD_IRON));
    }

    public static int meleeAttackCooldownTicks(HumanoidNpcKind kind) {
        if (isRanged(kind)) {
            return 20;
        }
        Weapon weapon;
        if (kind.id().contains("axe_thrower") || kind.id().contains("axeman") || kind.id().contains("berserker") || kind.id().contains("half_troll")) {
            weapon = weaponForFamily(kind, Weapon.BATTLEAXE_IRON);
        } else {
            weapon = weaponForFamily(kind, Weapon.SWORD_IRON);
        }
        return weapon.attackCooldownTicks();
    }

    public static void equipHumanoid(net.minecraft.world.entity.Mob mob, HumanoidNpcKind kind) {
        mob.setItemSlot(EquipmentSlot.MAINHAND, isRanged(kind) ? rangedWeaponFor(kind) : meleeWeaponFor(kind));
        ItemStack shield = LotrShields.stackFor(kind);
        if (!shield.isEmpty()) {
            mob.setItemSlot(EquipmentSlot.OFFHAND, shield);
        }
        ArmorFamily armor = armorFamily(kind);
        if (armor != null) {
            mob.setItemSlot(EquipmentSlot.FEET, stack(armor.boots));
            mob.setItemSlot(EquipmentSlot.LEGS, stack(armor.legs));
            mob.setItemSlot(EquipmentSlot.CHEST, stack(armor.body));
            if (wearsHelmet(kind)) {
                mob.setItemSlot(EquipmentSlot.HEAD, stack(armor.helmet));
            }
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.isArmor() || slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
                mob.setDropChance(slot, 0.04F);
            }
        }
    }

    private static Weapon weaponForFamily(HumanoidNpcKind kind, Weapon fallback) {
        return switch (kind.faction()) {
            case GONDOR -> Weapon.SWORD_GONDOR;
            case BREE, RANGER_NORTH -> Weapon.SWORD_GONDOR;
            case ROHAN -> kind.id().contains("axe") ? Weapon.BATTLEAXE_ROHAN : Weapon.SWORD_ROHAN;
            case DALE -> Weapon.SWORD_DALE;
            case HIGH_ELF -> Weapon.SWORD_HIGH_ELVEN;
            case LOTHLORIEN -> Weapon.SWORD_ELVEN;
            case WOOD_ELF, DORWINION -> Weapon.SWORD_WOOD_ELVEN;
            case DURINS_FOLK -> kind.id().contains("axe") ? Weapon.BATTLEAXE_DWARVEN : Weapon.SWORD_DWARVEN;
            case BLUE_MOUNTAINS -> kind.id().contains("axe") ? Weapon.BATTLEAXE_BLUE_DWARVEN : Weapon.SWORD_BLUE_DWARVEN;
            case DUNLAND -> Weapon.BATTLEAXE_DUNLENDING;
            case MORDOR, ANGMAR, DOL_GULDUR -> kind.id().contains("dagger") ? Weapon.DAGGER_ORC : Weapon.SWORD_ORC;
            case GUNDABAD -> Weapon.SWORD_GUNDABAD_URUK;
            case ISENGARD -> Weapon.SWORD_URUK;
            case RHUDEL -> Weapon.SWORD_RHUN;
            case NEAR_HARAD -> Weapon.SWORD_NEAR_HARAD;
            case MORWAITH -> Weapon.SWORD_MOREDAN;
            case TAURETHRIM -> Weapon.SWORD_TAUREDAIN;
            case HALF_TROLL -> Weapon.BATTLEAXE_HALF_TROLL;
            case UTUMNO -> Weapon.SWORD_UTUMNO;
            case RUFFIAN -> Weapon.DAGGER_IRON;
            case HOSTILE -> Weapon.DAGGER_IRON;
            default -> fallback;
        };
    }

    private static ArmorFamily armorFamily(HumanoidNpcKind kind) {
        return switch (kind.faction()) {
            case GONDOR -> kind.id().contains("ranger") || kind.id().contains("blackroot") ? ArmorFamily.RANGER : ArmorFamily.GONDOR;
            case RANGER_NORTH -> ArmorFamily.RANGER;
            case ROHAN -> ArmorFamily.ROHAN;
            case DALE -> ArmorFamily.DALE;
            case DORWINION -> kind.id().contains("elf") ? ArmorFamily.DORWINION_ELF : ArmorFamily.DORWINION;
            case HIGH_ELF -> ArmorFamily.HIGH_ELVEN;
            case LOTHLORIEN -> ArmorFamily.HITHLAIN;
            case WOOD_ELF -> ArmorFamily.WOOD_ELVEN;
            case DURINS_FOLK -> ArmorFamily.DWARVEN;
            case BLUE_MOUNTAINS -> ArmorFamily.BLUE_DWARVEN;
            case DUNLAND -> ArmorFamily.DUNLENDING;
            case MORDOR -> kind.id().contains("black_uruk") ? ArmorFamily.BLACK_URUK : ArmorFamily.ORC;
            case ANGMAR -> ArmorFamily.ANGMAR;
            case DOL_GULDUR -> ArmorFamily.DOL_GULDUR;
            case GUNDABAD -> ArmorFamily.GUNDABAD_URUK;
            case ISENGARD -> ArmorFamily.URUK;
            case RHUDEL -> kind.id().contains("gold") ? ArmorFamily.RHUN_GOLD : ArmorFamily.RHUN;
            case NEAR_HARAD -> kind.id().contains("nomad") ? ArmorFamily.NOMAD : ArmorFamily.NEAR_HARAD;
            case MORWAITH -> ArmorFamily.MOREDAIN;
            case TAURETHRIM -> ArmorFamily.TAUREDAIN;
            case HALF_TROLL -> ArmorFamily.HALF_TROLL;
            case UTUMNO -> ArmorFamily.UTUMNO;
            default -> null;
        };
    }

    private static boolean wearsHelmet(HumanoidNpcKind kind) {
        String id = kind.id();
        return id.contains("archer")
                || id.contains("axeman")
                || id.contains("blowgunner")
                || id.contains("bombardier")
                || id.contains("captain")
                || id.contains("chieftain")
                || id.contains("commander")
                || id.contains("crossbower")
                || id.contains("guard")
                || id.contains("knight")
                || id.contains("marine")
                || id.contains("marshal")
                || id.contains("orc")
                || id.contains("ranger")
                || id.contains("soldier")
                || id.contains("uruk")
                || id.contains("warrior")
                || id.contains("warlord");
    }

    private static ItemStack stack(Weapon weapon) {
        return new ItemStack(WEAPONS.get(weapon));
    }

    private static ItemStack stack(ArmorPiece armor) {
        return new ItemStack(ARMOR.get(armor));
    }

    public enum Weapon {
        SWORD_IRON("swordBronze", WeaponClass.SWORD, ToolMaterial.COPPER, 2.5F, -2.4F),
        DAGGER_IRON("daggerIron", WeaponClass.DAGGER, ToolMaterial.IRON, 0.5F, -1.6F),
        BATTLEAXE_IRON("battleaxeIron", WeaponClass.BATTLEAXE, ToolMaterial.IRON, 5.0F, -3.2F),
        IRON_BOW("bronzeCrossbow", WeaponClass.BOW, ToolMaterial.COPPER, 0.0F, 0.0F),
        SWORD_GONDOR("swordGondor", WeaponClass.SWORD, ToolMaterial.IRON, 3.0F, -2.4F),
        SWORD_ROHAN("swordRohan", WeaponClass.SWORD, ToolMaterial.IRON, 2.8F, -2.35F),
        SWORD_DALE("swordDale", WeaponClass.SWORD, ToolMaterial.IRON, 3.0F, -2.4F),
        SWORD_DWARVEN("swordDwarven", WeaponClass.SWORD, ToolMaterial.DIAMOND, 2.8F, -2.55F),
        SWORD_BLUE_DWARVEN("swordBlueDwarven", WeaponClass.SWORD, ToolMaterial.DIAMOND, 2.8F, -2.55F),
        SWORD_HIGH_ELVEN("swordHighElven", WeaponClass.SWORD, ToolMaterial.DIAMOND, 2.4F, -2.25F),
        SWORD_ELVEN("swordElven", WeaponClass.SWORD, ToolMaterial.DIAMOND, 2.3F, -2.2F),
        SWORD_WOOD_ELVEN("swordWoodElven", WeaponClass.SWORD, ToolMaterial.DIAMOND, 2.2F, -2.15F),
        SWORD_ORC("scimitarOrc", WeaponClass.SWORD, ToolMaterial.COPPER, 2.5F, -2.35F),
        SWORD_URUK("scimitarUruk", WeaponClass.SWORD, ToolMaterial.IRON, 3.2F, -2.55F),
        SWORD_GUNDABAD_URUK("swordGundabadUruk", WeaponClass.SWORD, ToolMaterial.IRON, 3.4F, -2.6F),
        SWORD_RHUN("swordRhun", WeaponClass.SWORD, ToolMaterial.IRON, 3.0F, -2.4F),
        SWORD_NEAR_HARAD("scimitarNearHarad", WeaponClass.SWORD, ToolMaterial.IRON, 2.8F, -2.25F),
        SWORD_MOREDAN("swordMoredain", WeaponClass.SWORD, ToolMaterial.STONE, 3.0F, -2.5F),
        SWORD_TAUREDAIN("swordTauredain", WeaponClass.SWORD, ToolMaterial.DIAMOND, 2.6F, -2.45F),
        SWORD_UTUMNO("swordUtumno", WeaponClass.SWORD, ToolMaterial.NETHERITE, 3.6F, -2.65F),
        BATTLEAXE_ROHAN("battleaxeRohan", WeaponClass.BATTLEAXE, ToolMaterial.IRON, 5.2F, -3.25F),
        BATTLEAXE_DWARVEN("battleaxeDwarven", WeaponClass.BATTLEAXE, ToolMaterial.DIAMOND, 5.0F, -3.35F),
        BATTLEAXE_BLUE_DWARVEN("battleaxeBlueDwarven", WeaponClass.BATTLEAXE, ToolMaterial.DIAMOND, 5.0F, -3.35F),
        BATTLEAXE_DUNLENDING("battleaxeBronze", WeaponClass.BATTLEAXE, ToolMaterial.COPPER, 5.0F, -3.15F),
        BATTLEAXE_HALF_TROLL("battleaxeHalfTroll", WeaponClass.BATTLEAXE, ToolMaterial.STONE, 7.0F, -3.65F),
        DAGGER_ORC("daggerOrc", WeaponClass.DAGGER, ToolMaterial.COPPER, 0.7F, -1.55F),
        RANGER_BOW("rangerBow", WeaponClass.BOW, ToolMaterial.IRON, 0.0F, 0.0F),
        GONDOR_BOW("gondorBow", WeaponClass.BOW, ToolMaterial.IRON, 0.0F, 0.0F),
        ROHAN_BOW("rohanBow", WeaponClass.BOW, ToolMaterial.IRON, 0.0F, 0.0F),
        DALE_BOW("daleBow", WeaponClass.BOW, ToolMaterial.IRON, 0.0F, 0.0F),
        ELVEN_BOW("elvenBow", WeaponClass.BOW, ToolMaterial.DIAMOND, 0.0F, 0.0F),
        WOOD_ELVEN_BOW("mirkwoodBow", WeaponClass.BOW, ToolMaterial.DIAMOND, 0.0F, 0.0F),
        DWARVEN_CROSSBOW("ironCrossbow", WeaponClass.BOW, ToolMaterial.IRON, 0.0F, 0.0F),
        ORC_BOW("orcBow", WeaponClass.BOW, ToolMaterial.COPPER, 0.0F, 0.0F),
        URUK_BOW("urukCrossbow", WeaponClass.BOW, ToolMaterial.IRON, 0.0F, 0.0F),
        RHUN_BOW("rhunBow", WeaponClass.BOW, ToolMaterial.IRON, 0.0F, 0.0F),
        HARAD_BOW("nearHaradBow", WeaponClass.BOW, ToolMaterial.IRON, 0.0F, 0.0F),
        TAUREDAIN_BLOWGUN("tauredainBlowgun", WeaponClass.BOW, ToolMaterial.STONE, 0.0F, 0.0F);

        private final String oldTexture;
        private final WeaponClass weaponClass;
        private final ToolMaterial material;
        private final float damage;
        private final float attackSpeed;

        Weapon(String oldTexture, WeaponClass weaponClass, ToolMaterial material, float damage, float attackSpeed) {
            this.oldTexture = oldTexture;
            this.weaponClass = weaponClass;
            this.material = material;
            this.damage = damage;
            this.attackSpeed = attackSpeed;
        }

        public String id() {
            return toSnake(oldTexture);
        }

        public String oldTexture() {
            return oldTexture;
        }

        private boolean isBow() {
            return weaponClass == WeaponClass.BOW;
        }

        private int attackCooldownTicks() {
            float attacksPerSecond = Math.max(0.1F, 4.0F + attackSpeed);
            return Math.max(1, (int) Math.ceil(20.0F / attacksPerSecond));
        }

        private Item.Properties properties() {
            Item.Properties properties = new Item.Properties().stacksTo(1);
            return switch (weaponClass) {
                case BOW -> properties.durability(material.durability());
                case DAGGER, SWORD -> properties.sword(material, damage, attackSpeed);
                case BATTLEAXE -> properties.axe(material, damage, attackSpeed);
            };
        }
    }

    private enum WeaponClass {
        SWORD,
        DAGGER,
        BATTLEAXE,
        BOW
    }

    public enum ArmorFamily {
        GONDOR("Gondor"),
        RANGER("Ranger"),
        ROHAN("Rohan"),
        DALE("Dale"),
        DORWINION("Dorwinion"),
        DORWINION_ELF("DorwinionElf"),
        HIGH_ELVEN("HighElven"),
        HITHLAIN("Hithlain"),
        WOOD_ELVEN("WoodElven"),
        DWARVEN("Dwarven"),
        BLUE_DWARVEN("BlueDwarven"),
        DUNLENDING("Dunlending"),
        ORC("Orc"),
        BLACK_URUK("BlackUruk"),
        ANGMAR("Angmar"),
        DOL_GULDUR("DolGuldur"),
        GUNDABAD_URUK("GundabadUruk"),
        URUK("Uruk"),
        RHUN("Rhun"),
        RHUN_GOLD("RhunGold"),
        NEAR_HARAD("NearHarad"),
        NOMAD("Nomad"),
        MOREDAIN("Moredain"),
        TAUREDAIN("Tauredain"),
        HALF_TROLL("HalfTroll"),
        UTUMNO("Utumno");

        private final String assetName;
        private final ArmorMaterial material;
        private final ArmorPiece helmet;
        private final ArmorPiece body;
        private final ArmorPiece legs;
        private final ArmorPiece boots;

        ArmorFamily(String oldSuffix) {
            this.assetName = armorAssetName(oldSuffix);
            this.material = armorMaterial(assetName);
            this.helmet = ArmorPiece.valueOf(("HELMET_" + toSnake(oldSuffix)).toUpperCase(Locale.ROOT));
            this.body = ArmorPiece.valueOf(("BODY_" + toSnake(oldSuffix)).toUpperCase(Locale.ROOT));
            this.legs = ArmorPiece.valueOf(("LEGS_" + toSnake(oldSuffix)).toUpperCase(Locale.ROOT));
            this.boots = ArmorPiece.valueOf(("BOOTS_" + toSnake(oldSuffix)).toUpperCase(Locale.ROOT));
            this.helmet.setMaterial(material);
            this.body.setMaterial(material);
            this.legs.setMaterial(material);
            this.boots.setMaterial(material);
        }
    }

    public enum ArmorPiece {
        HELMET_GONDOR("helmetGondor", ArmorType.HELMET), BODY_GONDOR("bodyGondor", ArmorType.CHESTPLATE), LEGS_GONDOR("legsGondor", ArmorType.LEGGINGS), BOOTS_GONDOR("bootsGondor", ArmorType.BOOTS),
        HELMET_RANGER("helmetRanger", ArmorType.HELMET), BODY_RANGER("bodyRanger", ArmorType.CHESTPLATE), LEGS_RANGER("legsRanger", ArmorType.LEGGINGS), BOOTS_RANGER("bootsRanger", ArmorType.BOOTS),
        HELMET_ROHAN("helmetRohan", ArmorType.HELMET), BODY_ROHAN("bodyRohan", ArmorType.CHESTPLATE), LEGS_ROHAN("legsRohan", ArmorType.LEGGINGS), BOOTS_ROHAN("bootsRohan", ArmorType.BOOTS),
        HELMET_DALE("helmetDale", ArmorType.HELMET), BODY_DALE("bodyDale", ArmorType.CHESTPLATE), LEGS_DALE("legsDale", ArmorType.LEGGINGS), BOOTS_DALE("bootsDale", ArmorType.BOOTS),
        HELMET_DORWINION("helmetDorwinion", ArmorType.HELMET), BODY_DORWINION("bodyDorwinion", ArmorType.CHESTPLATE), LEGS_DORWINION("legsDorwinion", ArmorType.LEGGINGS), BOOTS_DORWINION("bootsDorwinion", ArmorType.BOOTS),
        HELMET_DORWINION_ELF("helmetDorwinionElf", ArmorType.HELMET), BODY_DORWINION_ELF("bodyDorwinionElf", ArmorType.CHESTPLATE), LEGS_DORWINION_ELF("legsDorwinionElf", ArmorType.LEGGINGS), BOOTS_DORWINION_ELF("bootsDorwinionElf", ArmorType.BOOTS),
        HELMET_HIGH_ELVEN("helmetHighElven", ArmorType.HELMET), BODY_HIGH_ELVEN("bodyHighElven", ArmorType.CHESTPLATE), LEGS_HIGH_ELVEN("legsHighElven", ArmorType.LEGGINGS), BOOTS_HIGH_ELVEN("bootsHighElven", ArmorType.BOOTS),
        HELMET_HITHLAIN("helmetHithlain", ArmorType.HELMET), BODY_HITHLAIN("bodyHithlain", ArmorType.CHESTPLATE), LEGS_HITHLAIN("legsHithlain", ArmorType.LEGGINGS), BOOTS_HITHLAIN("bootsHithlain", ArmorType.BOOTS),
        HELMET_WOOD_ELVEN("helmetWoodElven", ArmorType.HELMET), BODY_WOOD_ELVEN("bodyWoodElven", ArmorType.CHESTPLATE), LEGS_WOOD_ELVEN("legsWoodElven", ArmorType.LEGGINGS), BOOTS_WOOD_ELVEN("bootsWoodElven", ArmorType.BOOTS),
        HELMET_DWARVEN("helmetDwarven", ArmorType.HELMET), BODY_DWARVEN("bodyDwarven", ArmorType.CHESTPLATE), LEGS_DWARVEN("legsDwarven", ArmorType.LEGGINGS), BOOTS_DWARVEN("bootsDwarven", ArmorType.BOOTS),
        HELMET_BLUE_DWARVEN("helmetBlueDwarven", ArmorType.HELMET), BODY_BLUE_DWARVEN("bodyBlueDwarven", ArmorType.CHESTPLATE), LEGS_BLUE_DWARVEN("legsBlueDwarven", ArmorType.LEGGINGS), BOOTS_BLUE_DWARVEN("bootsBlueDwarven", ArmorType.BOOTS),
        HELMET_DUNLENDING("helmetDunlending", ArmorType.HELMET), BODY_DUNLENDING("bodyDunlending", ArmorType.CHESTPLATE), LEGS_DUNLENDING("legsDunlending", ArmorType.LEGGINGS), BOOTS_DUNLENDING("bootsDunlending", ArmorType.BOOTS),
        HELMET_ORC("helmetOrc", ArmorType.HELMET), BODY_ORC("bodyOrc", ArmorType.CHESTPLATE), LEGS_ORC("legsOrc", ArmorType.LEGGINGS), BOOTS_ORC("bootsOrc", ArmorType.BOOTS),
        HELMET_BLACK_URUK("helmetBlackUruk", ArmorType.HELMET), BODY_BLACK_URUK("bodyBlackUruk", ArmorType.CHESTPLATE), LEGS_BLACK_URUK("legsBlackUruk", ArmorType.LEGGINGS), BOOTS_BLACK_URUK("bootsBlackUruk", ArmorType.BOOTS),
        HELMET_ANGMAR("helmetAngmar", ArmorType.HELMET), BODY_ANGMAR("bodyAngmar", ArmorType.CHESTPLATE), LEGS_ANGMAR("legsAngmar", ArmorType.LEGGINGS), BOOTS_ANGMAR("bootsAngmar", ArmorType.BOOTS),
        HELMET_DOL_GULDUR("helmetDolGuldur", ArmorType.HELMET), BODY_DOL_GULDUR("bodyDolGuldur", ArmorType.CHESTPLATE), LEGS_DOL_GULDUR("legsDolGuldur", ArmorType.LEGGINGS), BOOTS_DOL_GULDUR("bootsDolGuldur", ArmorType.BOOTS),
        HELMET_GUNDABAD_URUK("helmetGundabadUruk", ArmorType.HELMET), BODY_GUNDABAD_URUK("bodyGundabadUruk", ArmorType.CHESTPLATE), LEGS_GUNDABAD_URUK("legsGundabadUruk", ArmorType.LEGGINGS), BOOTS_GUNDABAD_URUK("bootsGundabadUruk", ArmorType.BOOTS),
        HELMET_URUK("helmetUruk", ArmorType.HELMET), BODY_URUK("bodyUruk", ArmorType.CHESTPLATE), LEGS_URUK("legsUruk", ArmorType.LEGGINGS), BOOTS_URUK("bootsUruk", ArmorType.BOOTS),
        HELMET_RHUN("helmetRhun", ArmorType.HELMET), BODY_RHUN("bodyRhun", ArmorType.CHESTPLATE), LEGS_RHUN("legsRhun", ArmorType.LEGGINGS), BOOTS_RHUN("bootsRhun", ArmorType.BOOTS),
        HELMET_RHUN_GOLD("helmetRhunGold", ArmorType.HELMET), BODY_RHUN_GOLD("bodyRhunGold", ArmorType.CHESTPLATE), LEGS_RHUN_GOLD("legsRhunGold", ArmorType.LEGGINGS), BOOTS_RHUN_GOLD("bootsRhunGold", ArmorType.BOOTS),
        HELMET_NEAR_HARAD("helmetNearHarad", ArmorType.HELMET), BODY_NEAR_HARAD("bodyNearHarad", ArmorType.CHESTPLATE), LEGS_NEAR_HARAD("legsNearHarad", ArmorType.LEGGINGS), BOOTS_NEAR_HARAD("bootsNearHarad", ArmorType.BOOTS),
        HELMET_NOMAD("helmetHaradRobes", ArmorType.HELMET), BODY_NOMAD("bodyNomad", ArmorType.CHESTPLATE), LEGS_NOMAD("legsNomad", ArmorType.LEGGINGS), BOOTS_NOMAD("bootsNomad", ArmorType.BOOTS),
        HELMET_MOREDAIN("helmetMoredain", ArmorType.HELMET), BODY_MOREDAIN("bodyMoredain", ArmorType.CHESTPLATE), LEGS_MOREDAIN("legsMoredain", ArmorType.LEGGINGS), BOOTS_MOREDAIN("bootsMoredain", ArmorType.BOOTS),
        HELMET_TAUREDAIN("helmetTauredain", ArmorType.HELMET), BODY_TAUREDAIN("bodyTauredain", ArmorType.CHESTPLATE), LEGS_TAUREDAIN("legsTauredain", ArmorType.LEGGINGS), BOOTS_TAUREDAIN("bootsTauredain", ArmorType.BOOTS),
        HELMET_HALF_TROLL("helmetHalfTroll", ArmorType.HELMET), BODY_HALF_TROLL("bodyHalfTroll", ArmorType.CHESTPLATE), LEGS_HALF_TROLL("legsHalfTroll", ArmorType.LEGGINGS), BOOTS_HALF_TROLL("bootsHalfTroll", ArmorType.BOOTS),
        HELMET_UTUMNO("helmetUtumno", ArmorType.HELMET), BODY_UTUMNO("bodyUtumno", ArmorType.CHESTPLATE), LEGS_UTUMNO("legsUtumno", ArmorType.LEGGINGS), BOOTS_UTUMNO("bootsUtumno", ArmorType.BOOTS);

        private final String oldTexture;
        private final ArmorType type;
        private ArmorMaterial material;

        ArmorPiece(String oldTexture, ArmorType type) {
            this.oldTexture = oldTexture;
            this.type = type;
        }

        public String id() {
            return toSnake(oldTexture);
        }

        public String oldTexture() {
            return oldTexture;
        }

        private void setMaterial(ArmorMaterial material) {
            this.material = material;
        }

        private Item.Properties properties() {
            return new Item.Properties().stacksTo(1).humanoidArmor(material, type);
        }
    }

    public static String toSnake(String name) {
        return name.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase(Locale.ROOT);
    }

    private static String armorAssetName(String oldSuffix) {
        String name = toSnake(oldSuffix);
        return switch (name) {
            case "orc" -> "mordor";
            case "nomad" -> "harad_nomad";
            default -> name;
        };
    }
}
