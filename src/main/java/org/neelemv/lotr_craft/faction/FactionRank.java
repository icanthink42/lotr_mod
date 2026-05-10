package org.neelemv.lotr_craft.faction;

public record FactionRank(float alignment, String name, boolean gendered, boolean pledgeRank) implements Comparable<FactionRank> {
    @Override
    public int compareTo(FactionRank other) {
        return -Float.compare(alignment, other.alignment);
    }
}
