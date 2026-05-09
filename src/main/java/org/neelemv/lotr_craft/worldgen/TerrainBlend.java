package org.neelemv.lotr_craft.worldgen;

record TerrainBlend(MiddleEarthTerrainProfile surfaceProfile, double baseHeight, double variation, double roughness, double waterWeight, double riverStrength, double mountainPeakHeight, double mountainInterior) {
    private static final double WATER_THRESHOLD = 0.35;

    static TerrainBlend of(MiddleEarthTerrainProfile profile) {
        double water = profile.water ? 1.0 : 0.0;
        return new TerrainBlend(profile, profile.baseHeight, profile.variation, profile.roughness, water, water, profile.mountainPeakHeight(), profile.mountainPeakHeight() > 0 ? 1.0 : 0.0);
    }

    boolean water() {
        return surfaceProfile.water || waterWeight >= WATER_THRESHOLD || riverStrength >= WATER_THRESHOLD;
    }
}
