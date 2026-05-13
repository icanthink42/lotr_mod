package org.neelemv.lotr_craft.worldgen;

import org.neelemv.lotr_craft.worldgen.noise.RidgeFbm;
import org.neelemv.lotr_craft.worldgen.rng.Rng;

public final class MiddleEarthHeight {
    public static final double HEIGHT_MAP_PEAK = 800.0;

    private static final double GLOBAL_VARIATION = 8.0;
    private static final double GLOBAL_ROUGHNESS = 6.0;
    private static final double RIDGE_SCALE = 0.004;
    private static final double RIDGE_AMPLITUDE = 60.0;

    private final RidgeFbm ridgeFbm;

    public MiddleEarthHeight(Rng rng) {
        ridgeFbm = new RidgeFbm(rng.fork("ridge"), 6, 2.0, 0.5, 2.0);
    }

    public double heightAtBlock(int blockX, int blockZ) {
        double factor = SvgMiddleEarthMap.get().heightFactorAtBlock(blockX, blockZ);
        double mapHeight = MiddleEarthMapConstants.SEA_LEVEL + factor * (HEIGHT_MAP_PEAK - MiddleEarthMapConstants.SEA_LEVEL);
        double broad = noise(blockX, blockZ, 0.0028, 1954L);
        double detail = noise(blockX, blockZ, 0.018, 1955L);
        double ridge = ridgeFbm.sample(blockX * RIDGE_SCALE, blockZ * RIDGE_SCALE) * RIDGE_AMPLITUDE * clamp(factor, 0.0, 1.0);
        double shaped = broad * GLOBAL_VARIATION + detail * GLOBAL_ROUGHNESS + ridge;
        return Math.max(MiddleEarthMapConstants.SEA_LEVEL - 2, mapHeight + shaped);
    }

    private static double noise(int x, int z, double scale, long salt) {
        double nx = x * scale;
        double nz = z * scale;
        double a = valueNoise(nx, nz, salt);
        double b = valueNoise(nx * 2.0 + 19.5, nz * 2.0 - 31.25, salt + 1L) * 0.5;
        double c = valueNoise(nx * 4.0 - 7.25, nz * 4.0 + 11.75, salt + 2L) * 0.25;
        return (a + b + c) / 1.75;
    }

    private static double valueNoise(double x, double z, long salt) {
        int x0 = fastFloor(x);
        int z0 = fastFloor(z);
        double tx = smooth(x - x0);
        double tz = smooth(z - z0);
        double a = randomValue(x0, z0, salt);
        double b = randomValue(x0 + 1, z0, salt);
        double c = randomValue(x0, z0 + 1, salt);
        double d = randomValue(x0 + 1, z0 + 1, salt);
        return lerp(lerp(a, b, tx), lerp(c, d, tx), tz);
    }

    private static double randomValue(int x, int z, long salt) {
        long value = x * 341873128712L + z * 132897987541L + salt * 42317861L;
        value ^= value >>> 13;
        value *= 1274126177L;
        value ^= value >>> 16;
        return ((value & 0xFFFFFF) / (double) 0x7FFFFF) - 1.0;
    }

    private static int fastFloor(double value) {
        int i = (int) value;
        return value < i ? i - 1 : i;
    }

    private static double smooth(double value) {
        return value * value * value * (value * (value * 6.0 - 15.0) + 10.0);
    }

    private static double lerp(double a, double b, double delta) {
        return a + (b - a) * delta;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
