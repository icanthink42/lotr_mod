package org.neelemv.lotr_craft.worldgen;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

enum MiddleEarthTerrainProfile {
    OCEAN(0, 9, 3, Surface.SAND, true, 0x2F68A8),
    MARSH(61, 5, 2, Surface.GRASS, false, 0x556D3A),
    PLAINS(68, 10, 5, Surface.GRASS, false, 0x7E9B52),
    FOREST(74, 14, 6, Surface.GRASS, false, 0x2F5B2E),
    HILLS(86, 25, 9, Surface.GRASS, false, 0x9D9C68),
    MOUNTAINS(118, 58, 18, Surface.STONE, false, 0x777777),
    SNOW_MOUNTAINS(132, 58, 16, Surface.SNOW_STONE, false, 0xD8D8D8),
    DESERT(67, 9, 4, Surface.SAND, false, 0xD0B66E),
    WASTE(66, 9, 5, Surface.COARSE_DIRT, false, 0x8A7045),
    MORDOR(72, 17, 7, Surface.STONE, false, 0x473536);

    private static final MiddleEarthTerrainProfile[] BY_ID = values();

    final int baseHeight;
    final int variation;
    final int roughness;
    private final Surface surface;
    final boolean water;
    private final int mapColor;

    MiddleEarthTerrainProfile(int baseHeight, int variation, int roughness, Surface surface, boolean water, int mapColor) {
        this.baseHeight = baseHeight;
        this.variation = variation;
        this.roughness = roughness;
        this.surface = surface;
        this.water = water;
        this.mapColor = mapColor;
    }

    BlockState top() {
        return surface.top();
    }

    BlockState filler() {
        return surface.filler();
    }

    int id() {
        return ordinal();
    }

    int mapColor() {
        return mapColor;
    }

    static int count() {
        return BY_ID.length;
    }

    static MiddleEarthTerrainProfile fromColor(int rgb) {
        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb & 0xFF;
        int max = Math.max(r, Math.max(g, b));
        int min = Math.min(r, Math.min(g, b));
        int chroma = max - min;
        int brightness = (r * 299 + g * 587 + b * 114) / 1000;

        if (b > g + 18 && b > r + 12 || (b > 120 && g > 120 && r < 100)) {
            return OCEAN;
        }
        if (brightness > 205 && chroma < 38) {
            return SNOW_MOUNTAINS;
        }
        if (chroma < 26) {
            return brightness > 150 ? MOUNTAINS : MORDOR;
        }
        if (r > g + 25 && r > b + 20 && brightness < 120) {
            return MORDOR;
        }
        if (r > g + 20 && g > b + 10) {
            return brightness > 145 ? DESERT : WASTE;
        }
        if (r > 150 && g > 130 && b < 95) {
            return DESERT;
        }
        if (g > r + 24 && g > b + 16) {
            return brightness < 82 ? FOREST : PLAINS;
        }
        if (g >= r && brightness < 110) {
            return FOREST;
        }
        if (brightness > 160) {
            return HILLS;
        }
        return PLAINS;
    }

    static MiddleEarthTerrainProfile fromId(int id) {
        if (id < 0 || id >= BY_ID.length) {
            return OCEAN;
        }
        return BY_ID[id];
    }

    static int colorForId(int id) {
        return fromId(id).mapColor();
    }

    private enum Surface {
        GRASS,
        SAND,
        STONE,
        SNOW_STONE,
        COARSE_DIRT;

        BlockState top() {
            return switch (this) {
                case GRASS -> Blocks.GRASS_BLOCK.defaultBlockState();
                case SAND -> Blocks.SAND.defaultBlockState();
                case STONE -> Blocks.STONE.defaultBlockState();
                case SNOW_STONE -> Blocks.SNOW_BLOCK.defaultBlockState();
                case COARSE_DIRT -> Blocks.COARSE_DIRT.defaultBlockState();
            };
        }

        BlockState filler() {
            return switch (this) {
                case GRASS -> Blocks.DIRT.defaultBlockState();
                case SAND -> Blocks.SAND.defaultBlockState();
                case STONE, SNOW_STONE, COARSE_DIRT -> Blocks.STONE.defaultBlockState();
            };
        }
    }
}
