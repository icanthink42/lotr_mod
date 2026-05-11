package org.neelemv.lotr_craft.client;

import java.util.EnumMap;
import java.util.Map;

import org.neelemv.lotr_craft.faction.LotrFaction;
import org.neelemv.lotr_craft.faction.PlayerAlignments;

public final class ClientFactionAlignments {
    private static final EnumMap<LotrFaction, Float> ALIGNMENTS = new EnumMap<>(LotrFaction.class);

    private ClientFactionAlignments() {
    }

    public static void replaceFrom(String encoded) {
        ALIGNMENTS.clear();
        for (Map.Entry<LotrFaction, Float> entry : PlayerAlignments.decode(encoded).entrySet()) {
            ALIGNMENTS.put(entry.getKey(), entry.getValue());
        }
    }

    public static float get(LotrFaction faction) {
        if (faction.hasFixedAlignment()) {
            return faction.fixedAlignment();
        }
        return ALIGNMENTS.getOrDefault(faction, 0.0F);
    }
}
