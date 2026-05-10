package org.neelemv.lotr_craft.faction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class LotrFactionRelations {
    private static final Map<FactionPair, FactionRelation> DEFAULT_RELATIONS = new HashMap<>();


    static {
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.BREE, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.RANGER_NORTH, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.BLUE_MOUNTAINS, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.HIGH_ELF, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.WOOD_ELF, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.DALE, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.DURINS_FOLK, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.LOTHLORIEN, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.ROHAN, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.GONDOR, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.RANGER_NORTH, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.BLUE_MOUNTAINS, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.HIGH_ELF, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.WOOD_ELF, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.DALE, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.DURINS_FOLK, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.LOTHLORIEN, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.HIGH_ELF, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.WOOD_ELF, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.LOTHLORIEN, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.ROHAN, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.GONDOR, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.BLUE_MOUNTAINS, LotrFaction.DURINS_FOLK, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.HIGH_ELF, LotrFaction.WOOD_ELF, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.HIGH_ELF, LotrFaction.LOTHLORIEN, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.HIGH_ELF, LotrFaction.FANGORN, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.HIGH_ELF, LotrFaction.GONDOR, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.GUNDABAD, LotrFaction.ANGMAR, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.GUNDABAD, LotrFaction.DOL_GULDUR, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.GUNDABAD, LotrFaction.MORDOR, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.ANGMAR, LotrFaction.DOL_GULDUR, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.ANGMAR, LotrFaction.MORDOR, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.WOOD_ELF, LotrFaction.LOTHLORIEN, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.WOOD_ELF, LotrFaction.FANGORN, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.WOOD_ELF, LotrFaction.DORWINION, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.DOL_GULDUR, LotrFaction.MORDOR, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.DALE, LotrFaction.DURINS_FOLK, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.DALE, LotrFaction.ROHAN, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.DALE, LotrFaction.GONDOR, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.DURINS_FOLK, LotrFaction.DUNLAND, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.LOTHLORIEN, LotrFaction.FANGORN, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.DUNLAND, LotrFaction.ISENGARD, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.ISENGARD, LotrFaction.HALF_TROLL, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.FANGORN, LotrFaction.TAURETHRIM, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.ROHAN, LotrFaction.GONDOR, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.MORDOR, LotrFaction.RHUDEL, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.MORDOR, LotrFaction.NEAR_HARAD, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.MORDOR, LotrFaction.MORWAITH, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.MORDOR, LotrFaction.HALF_TROLL, FactionRelation.ALLY);
        setDefaultRelation(LotrFaction.NEAR_HARAD, LotrFaction.MORWAITH, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.NEAR_HARAD, LotrFaction.HALF_TROLL, FactionRelation.FRIEND);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.GUNDABAD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.ANGMAR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.DOL_GULDUR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.ISENGARD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.MORDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.HOBBIT, LotrFaction.DARK_HUORN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.GUNDABAD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.ANGMAR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.DOL_GULDUR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.ISENGARD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.MORDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BREE, LotrFaction.DARK_HUORN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.GUNDABAD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.ANGMAR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.DOL_GULDUR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.DUNLAND, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.ISENGARD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.MORDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.RHUDEL, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.NEAR_HARAD, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.MORWAITH, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.RANGER_NORTH, LotrFaction.DARK_HUORN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BLUE_MOUNTAINS, LotrFaction.GUNDABAD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BLUE_MOUNTAINS, LotrFaction.ANGMAR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BLUE_MOUNTAINS, LotrFaction.DOL_GULDUR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BLUE_MOUNTAINS, LotrFaction.ISENGARD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BLUE_MOUNTAINS, LotrFaction.MORDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.BLUE_MOUNTAINS, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.HIGH_ELF, LotrFaction.GUNDABAD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.HIGH_ELF, LotrFaction.ANGMAR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.HIGH_ELF, LotrFaction.DOL_GULDUR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.HIGH_ELF, LotrFaction.ISENGARD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.HIGH_ELF, LotrFaction.MORDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.HIGH_ELF, LotrFaction.RHUDEL, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.HIGH_ELF, LotrFaction.NEAR_HARAD, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.HIGH_ELF, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.GUNDABAD, LotrFaction.WOOD_ELF, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.GUNDABAD, LotrFaction.DALE, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.GUNDABAD, LotrFaction.DURINS_FOLK, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.GUNDABAD, LotrFaction.LOTHLORIEN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.GUNDABAD, LotrFaction.FANGORN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.GUNDABAD, LotrFaction.ROHAN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.GUNDABAD, LotrFaction.GONDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.GUNDABAD, LotrFaction.DORWINION, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ANGMAR, LotrFaction.WOOD_ELF, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ANGMAR, LotrFaction.DALE, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ANGMAR, LotrFaction.DURINS_FOLK, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ANGMAR, LotrFaction.LOTHLORIEN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ANGMAR, LotrFaction.FANGORN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ANGMAR, LotrFaction.ROHAN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ANGMAR, LotrFaction.GONDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ANGMAR, LotrFaction.DORWINION, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.WOOD_ELF, LotrFaction.DOL_GULDUR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.WOOD_ELF, LotrFaction.ISENGARD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.WOOD_ELF, LotrFaction.MORDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.WOOD_ELF, LotrFaction.RHUDEL, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.WOOD_ELF, LotrFaction.NEAR_HARAD, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.WOOD_ELF, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DOL_GULDUR, LotrFaction.DALE, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DOL_GULDUR, LotrFaction.DURINS_FOLK, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DOL_GULDUR, LotrFaction.LOTHLORIEN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DOL_GULDUR, LotrFaction.FANGORN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DOL_GULDUR, LotrFaction.ROHAN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DOL_GULDUR, LotrFaction.GONDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DOL_GULDUR, LotrFaction.DORWINION, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DALE, LotrFaction.ISENGARD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DALE, LotrFaction.MORDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DALE, LotrFaction.RHUDEL, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.DALE, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DURINS_FOLK, LotrFaction.ISENGARD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DURINS_FOLK, LotrFaction.MORDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DURINS_FOLK, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.LOTHLORIEN, LotrFaction.ISENGARD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.LOTHLORIEN, LotrFaction.MORDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.LOTHLORIEN, LotrFaction.RHUDEL, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.LOTHLORIEN, LotrFaction.NEAR_HARAD, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.LOTHLORIEN, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DUNLAND, LotrFaction.ROHAN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DUNLAND, LotrFaction.GONDOR, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.ISENGARD, LotrFaction.FANGORN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ISENGARD, LotrFaction.ROHAN, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ISENGARD, LotrFaction.GONDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ISENGARD, LotrFaction.DORWINION, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.FANGORN, LotrFaction.MORDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.FANGORN, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ROHAN, LotrFaction.MORDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ROHAN, LotrFaction.RHUDEL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.ROHAN, LotrFaction.NEAR_HARAD, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.ROHAN, LotrFaction.MORWAITH, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.ROHAN, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.GONDOR, LotrFaction.MORDOR, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.GONDOR, LotrFaction.RHUDEL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.GONDOR, LotrFaction.NEAR_HARAD, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.GONDOR, LotrFaction.MORWAITH, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.GONDOR, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.MORDOR, LotrFaction.DORWINION, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.MORDOR, LotrFaction.TAURETHRIM, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.DORWINION, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.NEAR_HARAD, LotrFaction.TAURETHRIM, FactionRelation.ENEMY);
        setDefaultRelation(LotrFaction.MORWAITH, LotrFaction.TAURETHRIM, FactionRelation.MORTAL_ENEMY);
        setDefaultRelation(LotrFaction.TAURETHRIM, LotrFaction.HALF_TROLL, FactionRelation.MORTAL_ENEMY);
        for (LotrFaction faction : LotrFaction.values()) {
            if (faction.playerAllowed() && faction != LotrFaction.UTUMNO) {
                setDefaultRelation(faction, LotrFaction.UTUMNO, FactionRelation.MORTAL_ENEMY);
            }
        }
    }

    private LotrFactionRelations() {
    }

    public static FactionRelation relationBetween(LotrFaction first, LotrFaction second) {
        if (first == LotrFaction.UNALIGNED || second == LotrFaction.UNALIGNED) {
            return FactionRelation.NEUTRAL;
        }
        if (first == LotrFaction.HOSTILE || second == LotrFaction.HOSTILE) {
            return FactionRelation.MORTAL_ENEMY;
        }
        if (first == second) {
            return FactionRelation.ALLY;
        }
        return DEFAULT_RELATIONS.getOrDefault(new FactionPair(first, second), FactionRelation.NEUTRAL);
    }

    public static Map<FactionPair, FactionRelation> defaultRelations() {
        return Collections.unmodifiableMap(DEFAULT_RELATIONS);
    }

    static void setDefaultRelation(LotrFaction first, LotrFaction second, FactionRelation relation) {
        if (relation == FactionRelation.NEUTRAL) {
            DEFAULT_RELATIONS.remove(new FactionPair(first, second));
        } else {
            DEFAULT_RELATIONS.put(new FactionPair(first, second), relation);
        }
    }
}
