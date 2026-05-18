package org.neelemv.lotr_craft.worldgen;

import org.neelemv.lotr_craft.worldgen.noise.Fbm;
import org.neelemv.lotr_craft.worldgen.noise.RidgeFbm;
import org.neelemv.lotr_craft.worldgen.rng.Rng;

public final class MiddleEarthHeight {
    public static final double HEIGHT_MAP_PEAK = 800.0;

    private static final double GLOBAL_VARIATION = 8.0;
    private static final double GLOBAL_ROUGHNESS = 6.0;
    private static final double RIDGE_SCALE = 0.004;
    private static final double RIDGE_AMPLITUDE = 60.0;

    private final Fbm broadFbm;
    private final Fbm detailFbm;
    private final RidgeFbm ridgeFbm;

    public MiddleEarthHeight(Rng rng) {
        broadFbm = new Fbm(rng.fork("broad"), 3, 2.0, 0.5);
        detailFbm = new Fbm(rng.fork("detail"), 3, 2.0, 0.5);
        ridgeFbm = new RidgeFbm(rng.fork("ridge"), 6, 2.0, 0.5, 2.0);
    }

    public double heightAtBlock(int blockX, int blockZ) {
        double factor = SvgMiddleEarthMap.get().heightFactorAtBlock(blockX, blockZ);
        double mapHeight = MiddleEarthMapConstants.SEA_LEVEL + factor * (HEIGHT_MAP_PEAK - MiddleEarthMapConstants.SEA_LEVEL);
        double broad = broadFbm.sample(blockX * 0.0028, blockZ * 0.0028);
        double detail = detailFbm.sample(blockX * 0.018, blockZ * 0.018);
        double ridge = ridgeFbm.sample(blockX * RIDGE_SCALE, blockZ * RIDGE_SCALE) * RIDGE_AMPLITUDE * Math.clamp(factor, 0.0, 1.0);
        double shaped = broad * GLOBAL_VARIATION + detail * GLOBAL_ROUGHNESS + ridge;
        return Math.max(MiddleEarthMapConstants.SEA_LEVEL - 2, mapHeight + shaped);
    }
}
