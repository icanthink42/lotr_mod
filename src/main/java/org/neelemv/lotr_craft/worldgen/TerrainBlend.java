package org.neelemv.lotr_craft.worldgen;

import java.util.Arrays;

record TerrainBlend(MiddleEarthTerrainProfile surfaceProfile, double waterWeight, double riverStrength, double[] profileWeights) {
    private static final double WATER_THRESHOLD = 0.35;

    static TerrainBlend of(MiddleEarthTerrainProfile profile) {
        double water = profile.water ? 1.0 : 0.0;
        double[] weights = new double[MiddleEarthTerrainProfile.count()];
        weights[profile.id()] = 1.0;
        return new TerrainBlend(profile, water, water, weights);
    }

    boolean water() {
        return surfaceProfile.water || waterWeight >= WATER_THRESHOLD || riverStrength >= WATER_THRESHOLD;
    }

    MiddleEarthTerrainProfile surfaceProfileAtBlock(int blockX, int blockZ) {
        return selectProfile(blockX, blockZ, 0x5F3759DFL, false);
    }

    MiddleEarthTerrainProfile biomeProfileAtBlock(int blockX, int blockZ) {
        return selectProfile(blockX, blockZ, 0x1D872B41L, true);
    }

    private MiddleEarthTerrainProfile selectProfile(int blockX, int blockZ, long salt, boolean includeWater) {
        double dominantWeight = weightOf(surfaceProfile);
        if (dominantWeight >= 0.985) {
            return surfaceProfile;
        }

        double total = 0.0;
        for (int i = 0; i < profileWeights.length; i++) {
            MiddleEarthTerrainProfile profile = MiddleEarthTerrainProfile.fromId(i);
            if (!includeWater && profile.water) {
                continue;
            }
            total += profileWeights[i];
        }
        if (total <= 0.0) {
            return surfaceProfile;
        }

        double target = randomUnit(blockX, blockZ, salt) * total;
        double accumulated = 0.0;
        for (int i = 0; i < profileWeights.length; i++) {
            MiddleEarthTerrainProfile profile = MiddleEarthTerrainProfile.fromId(i);
            if (!includeWater && profile.water) {
                continue;
            }
            accumulated += profileWeights[i];
            if (target <= accumulated) {
                return profile;
            }
        }
        return surfaceProfile;
    }

    private double weightOf(MiddleEarthTerrainProfile profile) {
        int id = profile.id();
        return id >= 0 && id < profileWeights.length ? profileWeights[id] : 0.0;
    }

    static double[] normalizedWeights(double[] weights, double totalWeight) {
        double[] normalized = Arrays.copyOf(weights, weights.length);
        if (totalWeight <= 0.0) {
            return normalized;
        }
        double inverse = 1.0 / totalWeight;
        for (int i = 0; i < normalized.length; i++) {
            normalized[i] *= inverse;
        }
        return normalized;
    }

    private static double randomUnit(int x, int z, long salt) {
        long value = x * 341873128712L + z * 132897987541L + salt;
        value = (value ^ (value >>> 30)) * 0xbf58476d1ce4e5b9L;
        value = (value ^ (value >>> 27)) * 0x94d049bb133111ebL;
        value = value ^ (value >>> 31);
        return (value >>> 11) * 0x1.0p-53;
    }
}
