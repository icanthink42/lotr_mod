package org.neelemv.lotr_craft.faction;

public record FactionPair(LotrFaction first, LotrFaction second) {
    public FactionPair {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Faction pair cannot contain null factions");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof FactionPair other)) {
            return false;
        }
        return first == other.first && second == other.second || first == other.second && second == other.first;
    }

    @Override
    public int hashCode() {
        int firstId = first.ordinal();
        int secondId = second.ordinal();
        int lower = Math.min(firstId, secondId);
        int upper = Math.max(firstId, secondId);
        return upper << 16 | lower;
    }
}
