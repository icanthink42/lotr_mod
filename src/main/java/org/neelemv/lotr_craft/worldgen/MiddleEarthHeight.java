package org.neelemv.lotr_craft.worldgen;

public final class MiddleEarthHeight {
    public static final double HEIGHT_MAP_PEAK = 800.0;

    private MiddleEarthHeight() {
    }

    public static double heightAtBlock(int blockX, int blockZ) {
        double factor = SvgMiddleEarthMap.get().heightFactorAtBlock(blockX, blockZ);
        return MiddleEarthMapConstants.SEA_LEVEL + factor * (HEIGHT_MAP_PEAK - MiddleEarthMapConstants.SEA_LEVEL);
    }
}
