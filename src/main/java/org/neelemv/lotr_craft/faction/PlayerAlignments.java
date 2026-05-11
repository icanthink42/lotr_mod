package org.neelemv.lotr_craft.faction;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

import net.minecraft.world.entity.player.Player;

public final class PlayerAlignments {
    private PlayerAlignments() {
    }

    public static float get(Player player, LotrFaction faction) {
        if (faction.hasFixedAlignment()) {
            return faction.fixedAlignment();
        }
        if (player instanceof PlayerAlignmentAccess access) {
            return access.lotr_craft$getAlignment(faction);
        }
        return 0.0F;
    }

    public static void set(Player player, LotrFaction faction, float alignment) {
        if (!faction.hasFixedAlignment() && player instanceof PlayerAlignmentAccess access) {
            access.lotr_craft$setAlignment(faction, alignment);
        }
    }

    public static void add(Player player, LotrFaction faction, float alignment) {
        if (!faction.hasFixedAlignment() && player instanceof PlayerAlignmentAccess access) {
            access.lotr_craft$setAlignment(faction, access.lotr_craft$getAlignment(faction) + alignment);
        }
    }

    public static FactionRelation relationFor(float alignment) {
        if (alignment >= 100.0F) {
            return FactionRelation.ALLY;
        }
        if (alignment >= 10.0F) {
            return FactionRelation.FRIEND;
        }
        if (alignment >= 0.0F) {
            return FactionRelation.NEUTRAL;
        }
        if (alignment <= -100.0F) {
            return FactionRelation.MORTAL_ENEMY;
        }
        return FactionRelation.ENEMY;
    }

    public static FactionRank rankFor(LotrFaction faction, float alignment) {
        for (FactionRank rank : faction.ranks()) {
            if (alignment >= rank.alignment()) {
                return rank;
            }
        }
        return null;
    }

    public static boolean isHostileTo(Player player, LotrFaction faction) {
        float directAlignment = get(player, faction);
        if (directAlignment >= 100.0F) {
            return false;
        }
        if (directAlignment < hostilityThreshold(faction)) {
            return true;
        }
        for (LotrFaction otherFaction : LotrFaction.values()) {
            if (otherFaction == faction || !otherFaction.playerAllowed()) {
                continue;
            }
            FactionRelation defaultRelation = LotrFactionRelations.relationBetween(faction, otherFaction);
            if (isFriendlyDefaultRelation(defaultRelation) && isEnemyAlignment(get(player, otherFaction))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFriendlyDefaultRelation(FactionRelation relation) {
        return relation == FactionRelation.ALLY || relation == FactionRelation.FRIEND;
    }

    private static boolean isEnemyAlignment(float alignment) {
        FactionRelation relation = relationFor(alignment);
        return relation == FactionRelation.ENEMY || relation == FactionRelation.MORTAL_ENEMY;
    }

    private static float hostilityThreshold(LotrFaction faction) {
        if (faction == LotrFaction.HOSTILE || faction == LotrFaction.DARK_HUORN) {
            return Float.POSITIVE_INFINITY;
        }
        if (faction.isOfType(FactionType.ORC) || faction.isOfType(FactionType.TROLL)) {
            return 100.0F;
        }
        return 0.0F;
    }

    public static String encode(Player player) {
        StringBuilder encoded = new StringBuilder();
        for (LotrFaction faction : LotrFaction.values()) {
            if (!faction.playerAllowed() || faction.hasFixedAlignment()) {
                continue;
            }
            if (encoded.length() > 0) {
                encoded.append(';');
            }
            encoded.append(faction.name()).append('=').append(Float.toString(get(player, faction)));
        }
        return encoded.toString();
    }

    public static Map<LotrFaction, Float> decode(String encoded) {
        EnumMap<LotrFaction, Float> alignments = new EnumMap<>(LotrFaction.class);
        if (encoded == null || encoded.isBlank()) {
            return alignments;
        }
        String[] entries = encoded.split(";");
        for (String entry : entries) {
            int separator = entry.indexOf('=');
            if (separator <= 0 || separator == entry.length() - 1) {
                continue;
            }
            try {
                LotrFaction faction = LotrFaction.valueOf(entry.substring(0, separator).toUpperCase(Locale.ROOT));
                if (faction.playerAllowed() && !faction.hasFixedAlignment()) {
                    alignments.put(faction, Float.parseFloat(entry.substring(separator + 1)));
                }
            } catch (IllegalArgumentException ignored) {
            }
        }
        return alignments;
    }
}
