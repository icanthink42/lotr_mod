package org.neelemv.lotr_craft.client.gui;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.worldgen.MiddleEarthMapConstants;
import org.neelemv.lotr_craft.worldgen.SvgMiddleEarthMap;

import com.mojang.blaze3d.platform.NativeImage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;

final class MiddleEarthMapTexture {
    static final int WIDTH = MiddleEarthMapConstants.MAP_WIDTH;
    static final int HEIGHT = MiddleEarthMapConstants.MAP_HEIGHT;

    private static final Identifier TEXTURE_ID = Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "dynamic/middle_earth_map");
    private static boolean registered;

    private MiddleEarthMapTexture() {
    }

    static Identifier texture() {
        if (!registered) {
            Minecraft.getInstance().getTextureManager().register(TEXTURE_ID, new DynamicTexture(() -> "Middle-earth map", createImage()));
            registered = true;
        }
        return TEXTURE_ID;
    }

    private static NativeImage createImage() {
        SvgMiddleEarthMap map = SvgMiddleEarthMap.get();
        NativeImage image = new NativeImage(WIDTH, HEIGHT, false);
        for (int y = 0; y < HEIGHT; y++) {
            int mapY = y * MiddleEarthMapConstants.MAP_HEIGHT / HEIGHT;
            for (int x = 0; x < WIDTH; x++) {
                int mapX = x * MiddleEarthMapConstants.MAP_WIDTH / WIDTH;
                image.setPixelABGR(x, y, toAbgr(map.colorAtMapPixel(mapX, mapY)));
            }
        }
        return image;
    }

    private static int toAbgr(int rgb) {
        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb & 0xFF;
        return 0xFF000000 | b << 16 | g << 8 | r;
    }
}
