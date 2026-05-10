package org.neelemv.lotr_craft.faction;

public enum FactionRegion {
    WEST(FactionDimension.MIDDLE_EARTH),
    EAST(FactionDimension.MIDDLE_EARTH),
    SOUTH(FactionDimension.MIDDLE_EARTH),
    UTUMNO(FactionDimension.UTUMNO);

    private final FactionDimension dimension;

    FactionRegion(FactionDimension dimension) {
        this.dimension = dimension;
    }

    public FactionDimension dimension() {
        return dimension;
    }
}
