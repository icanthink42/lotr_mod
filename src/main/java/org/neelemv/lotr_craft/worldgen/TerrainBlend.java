package org.neelemv.lotr_craft.worldgen;

record TerrainBlend(MiddleEarthTerrainProfile surfaceProfile, double baseHeight, double variation, double roughness, double waterWeight, double mountainPeakHeight, double mountainInterior) {
    private static final double WATER_THRESHOLD = 0.45;

    static TerrainBlend of(MiddleEarthTerrainProfile profile) {
        return new TerrainBlend(profile, profile.baseHeight, profile.variation, profile.roughness, profile.water ? 1.0 : 0.0, profile.mountainPeakHeight(), profile.mountainPeakHeight() > 0 ? 1.0 : 0.0);
    }

    boolean water() {
        return surfaceProfile.water || waterWeight >= WATER_THRESHOLD;
    }
}
