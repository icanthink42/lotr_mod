package org.neelemv.lotr_craft.item;

import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.entity.HumanoidNpcKind;
import org.neelemv.lotr_craft.faction.LotrFaction;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.component.BlocksAttacks;

public enum LotrShields {
    ALIGNMENT_BREE(LotrFaction.BREE),
    ALIGNMENT_RANGER(LotrFaction.RANGER_NORTH),
    ALIGNMENT_BLUE_MOUNTAINS(LotrFaction.BLUE_MOUNTAINS),
    ALIGNMENT_HIGH_ELF(LotrFaction.HIGH_ELF),
    ALIGNMENT_RIVENDELL(LotrFaction.HIGH_ELF),
    ALIGNMENT_GUNDABAD(LotrFaction.GUNDABAD),
    ALIGNMENT_ANGMAR(LotrFaction.ANGMAR),
    ALIGNMENT_WOOD_ELF(LotrFaction.WOOD_ELF),
    ALIGNMENT_DOL_GULDUR(LotrFaction.DOL_GULDUR),
    ALIGNMENT_DALE(LotrFaction.DALE),
    ALIGNMENT_ESGAROTH(LotrFaction.DALE),
    ALIGNMENT_DWARF(LotrFaction.DURINS_FOLK),
    ALIGNMENT_GALADHRIM(LotrFaction.LOTHLORIEN),
    ALIGNMENT_DUNLAND(LotrFaction.DUNLAND),
    ALIGNMENT_URUK_HAI(LotrFaction.ISENGARD, "uruk_hai_shield"),
    ALIGNMENT_ROHAN(LotrFaction.ROHAN),
    ALIGNMENT_GONDOR(LotrFaction.GONDOR),
    ALIGNMENT_DOL_AMROTH(LotrFaction.GONDOR),
    ALIGNMENT_LOSSARNACH(LotrFaction.GONDOR),
    ALIGNMENT_LEBENNIN(LotrFaction.GONDOR),
    ALIGNMENT_PELARGIR(LotrFaction.GONDOR),
    ALIGNMENT_BLACKROOT_VALE(LotrFaction.GONDOR),
    ALIGNMENT_PINNATH_GELIN(LotrFaction.GONDOR),
    ALIGNMENT_LAMEDON(LotrFaction.GONDOR),
    ALIGNMENT_MORDOR(LotrFaction.MORDOR),
    ALIGNMENT_MINAS_MORGUL(LotrFaction.MORDOR),
    ALIGNMENT_BLACK_URUK(LotrFaction.MORDOR),
    ALIGNMENT_DORWINION(LotrFaction.DORWINION),
    ALIGNMENT_DORWINION_ELF(LotrFaction.DORWINION),
    ALIGNMENT_RHUN(LotrFaction.RHUDEL),
    ALIGNMENT_HARNEDOR(LotrFaction.NEAR_HARAD),
    ALIGNMENT_NEAR_HARAD(LotrFaction.NEAR_HARAD),
    ALIGNMENT_UMBAR(LotrFaction.NEAR_HARAD),
    ALIGNMENT_CORSAIR(LotrFaction.NEAR_HARAD),
    ALIGNMENT_GULF(LotrFaction.NEAR_HARAD),
    ALIGNMENT_MOREDAIN(LotrFaction.MORWAITH),
    ALIGNMENT_TAUREDAIN(LotrFaction.TAURETHRIM),
    ALIGNMENT_HALF_TROLL(LotrFaction.HALF_TROLL);

    private static final Map<LotrShields, Item> ITEMS = new EnumMap<>(LotrShields.class);

    private final LotrFaction faction;
    private final String itemId;

    LotrShields(LotrFaction faction) {
        this(faction, null);
    }

    LotrShields(LotrFaction faction, String itemId) {
        this.faction = faction;
        this.itemId = itemId == null ? name().toLowerCase(Locale.ROOT) + "_shield" : itemId;
    }

    public LotrFaction faction() {
        return faction;
    }

    public String itemId() {
        return itemId;
    }

    public Item item() {
        return ITEMS.get(this);
    }

    public static List<Item> registerItems() {
        for (LotrShields shield : values()) {
            Item item = Lotr_craft.registerItem(shield.itemId, ShieldItem::new, shieldProperties());
            ITEMS.put(shield, item);
        }
        return List.copyOf(ITEMS.values());
    }

    private static Item.Properties shieldProperties() {
        return new Item.Properties()
                .durability(336)
                .repairable(ItemTags.WOODEN_TOOL_MATERIALS)
                .equippableUnswappable(EquipmentSlot.OFFHAND)
                .delayedComponent(DataComponents.BLOCKS_ATTACKS, registries -> new BlocksAttacks(
                        0.25F,
                        1.0F,
                        List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                        new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                        Optional.of(registries.getOrThrow(DamageTypeTags.BYPASSES_SHIELD)),
                        Optional.of(SoundEvents.SHIELD_BLOCK),
                        Optional.of(SoundEvents.SHIELD_BREAK)))
                .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK);
    }

    public static ItemStack stackFor(HumanoidNpcKind kind) {
        LotrShields shield = shieldFor(kind);
        return shield == null ? ItemStack.EMPTY : new ItemStack(shield.item());
    }

    public static LotrShields shieldFor(HumanoidNpcKind kind) {
        String id = kind.id();
        if (kind.faction() == LotrFaction.ISENGARD && id.startsWith("uruk_hai")) {
            return id.contains("crossbower") || id.contains("berserker") ? null : ALIGNMENT_URUK_HAI;
        }
        if (id.contains("archer") || id.contains("crossbower") || id.contains("blowgunner")) {
            return null;
        }
        if (id.contains("swan_knight")) {
            return ALIGNMENT_DOL_AMROTH;
        }
        if (id.contains("lossarnach")) {
            return ALIGNMENT_LOSSARNACH;
        }
        if (id.contains("lebennin")) {
            return ALIGNMENT_LEBENNIN;
        }
        if (id.contains("pelargir")) {
            return ALIGNMENT_PELARGIR;
        }
        if (id.contains("blackroot")) {
            return ALIGNMENT_BLACKROOT_VALE;
        }
        if (id.contains("pinnath_gelin")) {
            return ALIGNMENT_PINNATH_GELIN;
        }
        if (id.contains("lamedon")) {
            return ALIGNMENT_LAMEDON;
        }
        if (id.contains("black_uruk")) {
            return ALIGNMENT_BLACK_URUK;
        }
        if (id.contains("minas_morgul")) {
            return ALIGNMENT_MINAS_MORGUL;
        }
        if (id.contains("dol_guldur")) {
            return ALIGNMENT_DOL_GULDUR;
        }
        if (id.contains("gundabad")) {
            return ALIGNMENT_GUNDABAD;
        }
        if (id.contains("angmar")) {
            return ALIGNMENT_ANGMAR;
        }
        if (id.contains("rivendell")) {
            return ALIGNMENT_RIVENDELL;
        }
        if (id.contains("galadhrim")) {
            return ALIGNMENT_GALADHRIM;
        }
        if (id.contains("dorwinion_elf")) {
            return ALIGNMENT_DORWINION_ELF;
        }
        if (id.contains("umbar")) {
            return ALIGNMENT_UMBAR;
        }
        if (id.contains("corsair")) {
            return ALIGNMENT_CORSAIR;
        }
        if (id.contains("gulf_harad")) {
            return ALIGNMENT_GULF;
        }
        if (id.contains("harnedor")) {
            return ALIGNMENT_HARNEDOR;
        }
        return switch (kind.faction()) {
            case BREE -> ALIGNMENT_BREE;
            case RANGER_NORTH -> ALIGNMENT_RANGER;
            case BLUE_MOUNTAINS -> ALIGNMENT_BLUE_MOUNTAINS;
            case HIGH_ELF -> ALIGNMENT_HIGH_ELF;
            case WOOD_ELF -> ALIGNMENT_WOOD_ELF;
            case DALE -> ALIGNMENT_DALE;
            case DURINS_FOLK -> ALIGNMENT_DWARF;
            case DUNLAND -> ALIGNMENT_DUNLAND;
            case ROHAN -> ALIGNMENT_ROHAN;
            case GONDOR -> ALIGNMENT_GONDOR;
            case MORDOR -> ALIGNMENT_MORDOR;
            case DORWINION -> ALIGNMENT_DORWINION;
            case RHUDEL -> ALIGNMENT_RHUN;
            case NEAR_HARAD -> ALIGNMENT_NEAR_HARAD;
            case MORWAITH -> ALIGNMENT_MOREDAIN;
            case TAURETHRIM -> ALIGNMENT_TAUREDAIN;
            case HALF_TROLL -> ALIGNMENT_HALF_TROLL;
            default -> null;
        };
    }
}
