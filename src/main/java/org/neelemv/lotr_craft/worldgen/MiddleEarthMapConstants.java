package org.neelemv.lotr_craft.worldgen;

public final class MiddleEarthMapConstants {
    public static final int MAP_SCALE = 128;
    public static final int MAP_ORIGIN_X = 810;
    public static final int MAP_ORIGIN_Z = 730;
    public static final int MAP_WIDTH = 3200;
    public static final int MAP_HEIGHT = 4000;
    public static final int SEA_LEVEL = 62;
    public static final int WORLD_MIN_Y = -64;
    public static final int WORLD_HEIGHT = 2096;
    public static final int WORLD_MAX_Y = WORLD_MIN_Y + WORLD_HEIGHT - 1;

    private MiddleEarthMapConstants() {
    }

    public static double blockToMapX(int blockX) {
        return blockX / (double) MAP_SCALE + MAP_ORIGIN_X;
    }

    public static double blockToMapZ(int blockZ) {
        return blockZ / (double) MAP_SCALE + MAP_ORIGIN_Z;
    }

    public static int mapToBlockX(int mapX) {
        return Math.round((mapX - MAP_ORIGIN_X) * MAP_SCALE);
    }

    public static int mapToBlockZ(int mapZ) {
        return Math.round((mapZ - MAP_ORIGIN_Z) * MAP_SCALE);
    }
}
