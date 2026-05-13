package org.neelemv.lotr_craft.worldgen;

import org.neelemv.lotr_craft.worldgen.noise.Fbm;
import org.neelemv.lotr_craft.worldgen.noise.Fbm3d;
import org.neelemv.lotr_craft.worldgen.noise.RidgeFbm;
import org.neelemv.lotr_craft.worldgen.rng.Rng;

public final class MiddleEarthHeight {
    public static final double HEIGHT_MAP_PEAK = 800.0;
    public static final double CLIFF_AMPLITUDE = 100.0;

    private static final double GLOBAL_VARIATION = 8.0;
    private static final double GLOBAL_ROUGHNESS = 6.0;
    private static final double RIDGE_SCALE = 0.004;
    private static final double RIDGE_AMPLITUDE = 200.0;
    // Warp frequency is lower than RIDGE_SCALE so distortion features are
    // larger than individual ridges, creating sweeping curved ranges.
    private static final double WARP_FREQ = 0.002;
    // Amplitude in ridge-coordinate space: 0.6 ~= 60% of one ridge period.
    private static final double WARP_AMPLITUDE = 0.6;
    private static final double CLIFF_XZ_SCALE = 0.005;
    private static final double CLIFF_Y_SCALE = 0.010;

    private final Fbm broadFbm;
    private final Fbm detailFbm;
    private final RidgeFbm ridgeFbm;
    private final Fbm warpFbmX;
    private final Fbm warpFbmZ;
    private final Fbm3d cliffFbm;

    public MiddleEarthHeight(Rng rng) {
        broadFbm = new Fbm(rng.fork("broad"), 3, 2.0, 0.5);
        detailFbm = new Fbm(rng.fork("detail"), 3, 2.0, 0.5);
        ridgeFbm = new RidgeFbm(rng.fork("ridge"), 6, 2.0, 0.5, 2.0);
        warpFbmX = new Fbm(rng.fork("warpX"), 3, 2.0, 0.5);
        warpFbmZ = new Fbm(rng.fork("warpZ"), 3, 2.0, 0.5);
        cliffFbm = new Fbm3d(rng.fork("cliff"), 4, 2.0, 0.5);
    }

    public ColumnInfo column(int x, int z) {
        double factor = SvgMiddleEarthMap.get().heightFactorAtBlock(x, z);
        double surface = surfaceHeight(x, z, factor);
        return new ColumnInfo(x, z, factor, surface);
    }

    public final class ColumnInfo {
        private final int x;
        private final int z;
        private final double factor;
        private final double surface;

        private ColumnInfo(int x, int z, double factor, double surface) {
            this.x = x;
            this.z = z;
            this.factor = factor;
            this.surface = surface;
        }

        public double approximateHeight() {
            return surface;
        }

        public double minHeight() {
            return surface - CLIFF_AMPLITUDE;
        }

        public double maxHeight() {
            return surface + CLIFF_AMPLITUDE;
        }

        // Returns > 0 if solid, < 0 if air.
        public double density(int y) {
            double f = clamp(factor, 0.0, 1.0);
            double cliff = cliffFbm.sample(x * CLIFF_XZ_SCALE, y * CLIFF_Y_SCALE, z * CLIFF_XZ_SCALE) * CLIFF_AMPLITUDE * (f * f);
            return surface - y + cliff;
        }
    }

    public double approximateHeightAtBlock(int x, int z) {
        return column(x, z).approximateHeight();
    }

    public double minHeightAtBlock(int x, int z) {
        return column(x, z).minHeight();
    }

    public double maxHeightAtBlock(int x, int z) {
        return column(x, z).maxHeight();
    }

    // Returns > 0 if solid, < 0 if air.
    public double densityAtBlock(int x, int y, int z) {
        return column(x, z).density(y);
    }

    private double surfaceHeight(int x, int z, double factor) {
        double mapHeight = MiddleEarthMapConstants.SEA_LEVEL + factor * (HEIGHT_MAP_PEAK - MiddleEarthMapConstants.SEA_LEVEL);
        double broad = broadFbm.sample(x * 0.0028, z * 0.0028);
        double detail = detailFbm.sample(x * 0.018, z * 0.018);
        double f = clamp(factor, 0.0, 1.0);
        double wx = warpFbmX.sample(x * WARP_FREQ, z * WARP_FREQ);
        double wz = warpFbmZ.sample(x * WARP_FREQ, z * WARP_FREQ);
        double ridge = ridgeFbm.sample(
            x * RIDGE_SCALE + wx * WARP_AMPLITUDE,
            z * RIDGE_SCALE + wz * WARP_AMPLITUDE
        ) * RIDGE_AMPLITUDE * (f * f * f);
        double shaped = broad * GLOBAL_VARIATION + detail * GLOBAL_ROUGHNESS + ridge;
        return Math.max(MiddleEarthMapConstants.SEA_LEVEL - 2, mapHeight + shaped);
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
