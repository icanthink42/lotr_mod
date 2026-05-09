package org.neelemv.lotr_craft.client.gui;

import org.neelemv.lotr_craft.client.LotrKeyMappings;
import org.neelemv.lotr_craft.network.MiddleEarthMapTeleportPayload;
import org.neelemv.lotr_craft.worldgen.MiddleEarthMapConstants;
import org.neelemv.lotr_craft.worldgen.MiddleEarthRoads;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public final class MiddleEarthMapScreen extends Screen {
    private static final int BACKGROUND_COLOR = 0xD0100D09;
    private static final int FRAME_COLOR = 0xFF25180D;
    private static final int INNER_FRAME_COLOR = 0xFF5B3F1D;
    private static final int MARKER_OUTLINE = 0xFF000000;
    private static final int MARKER_COLOR = 0xFFFFFFFF;
    private static final int LABEL_COLOR = 0xFFFFFF;
    private static final int LABEL_SHADOW_COLOR = 0x000000;
    private static final int ROAD_COLOR = 0xB0352414;
    private static final int ROAD_SHADOW_COLOR = 0x8030180A;
    private static final int RIGHT_MOUSE_BUTTON = 1;
    private static final float MIN_ZOOM = 1.0F;
    private static final float MAX_ZOOM = 16.0F;
    private static final int MAP_MARGIN = 18;
    private static final int MIN_VISIBLE_MAP_PIXELS = 48;

    private float zoom = 1.0F;
    private float panX;
    private float panY;
    private double mouseX;
    private double mouseY;
    private boolean panning;

    public MiddleEarthMapScreen() {
        super(Component.translatable("screen.lotr_craft.middle_earth_map"));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        graphics.fill(0, 0, width, height, BACKGROUND_COLOR);

        Identifier mapTexture = MiddleEarthMapTexture.texture();
        int textureWidth = MiddleEarthMapTexture.WIDTH;
        int textureHeight = MiddleEarthMapTexture.HEIGHT;
        float scale = currentScale();
        int drawWidth = Math.round(textureWidth * scale);
        int drawHeight = Math.round(textureHeight * scale);
        int x = Math.round(baseX(scale) + panX);
        int y = Math.round(baseY(scale) + panY);

        graphics.fill(x - 6, y - 6, x + drawWidth + 6, y + drawHeight + 6, FRAME_COLOR);
        graphics.fill(x - 3, y - 3, x + drawWidth + 3, y + drawHeight + 3, INNER_FRAME_COLOR);

        graphics.pose().pushMatrix();
        graphics.pose().translate(x, y);
        graphics.pose().scale(scale, scale);
        graphics.blit(RenderPipelines.GUI_TEXTURED, mapTexture, 0, 0, 0.0F, 0.0F, textureWidth, textureHeight, textureWidth, textureHeight);
        graphics.pose().popMatrix();

        drawRoads(graphics, x, y, drawWidth, drawHeight, scale);
        drawMapLabels(graphics, x, y, drawWidth, drawHeight, scale);
        drawPlayerMarker(graphics, x, y, drawWidth, drawHeight);
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == RIGHT_MOUSE_BUTTON) {
            panning = true;
            setDragging(true);
            return true;
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (event.button() == RIGHT_MOUSE_BUTTON) {
            panning = false;
            setDragging(false);
            return true;
        }
        return super.mouseReleased(event);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        mouseX = event.x();
        mouseY = event.y();
        if (panning && event.button() == RIGHT_MOUSE_BUTTON) {
            panX += (float) dragX;
            panY += (float) dragY;
            clampPan();
            return true;
        }
        return super.mouseDragged(event, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        if (scrollY == 0.0) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        float oldScale = currentScale();
        float oldX = baseX(oldScale) + panX;
        float oldY = baseY(oldScale) + panY;
        float textureX = (float) ((mouseX - oldX) / oldScale);
        float textureY = (float) ((mouseY - oldY) / oldScale);

        zoom = Mth.clamp(zoom * (scrollY > 0.0 ? 1.2F : 1.0F / 1.2F), MIN_ZOOM, MAX_ZOOM);

        float newScale = currentScale();
        panX = (float) mouseX - baseX(newScale) - textureX * newScale;
        panY = (float) mouseY - baseY(newScale) - textureY * newScale;
        clampPan();
        return true;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (LotrKeyMappings.MAP_TELEPORT.matches(event)) {
            sendTeleportAtMouse();
            return true;
        }
        return super.keyPressed(event);
    }

    private void sendTeleportAtMouse() {
        float scale = currentScale();
        int mapX = Mth.floor((mouseX - baseX(scale) - panX) / scale);
        int mapZ = Mth.floor((mouseY - baseY(scale) - panY) / scale);
        if (mapX < 0 || mapX >= MiddleEarthMapConstants.MAP_WIDTH || mapZ < 0 || mapZ >= MiddleEarthMapConstants.MAP_HEIGHT) {
            return;
        }

        ClientPlayNetworking.send(new MiddleEarthMapTeleportPayload(mapX, mapZ));
        onClose();
    }

    private float currentScale() {
        return fitScale() * zoom;
    }

    private float fitScale() {
        return Math.max(0.1F, Math.min((width - MAP_MARGIN * 2) / (float) MiddleEarthMapTexture.WIDTH, (height - MAP_MARGIN * 2) / (float) MiddleEarthMapTexture.HEIGHT));
    }

    private float baseX(float scale) {
        return (width - MiddleEarthMapTexture.WIDTH * scale) * 0.5F;
    }

    private float baseY(float scale) {
        return (height - MiddleEarthMapTexture.HEIGHT * scale) * 0.5F;
    }

    private void clampPan() {
        float scale = currentScale();
        panX = clampPanAxis(panX, baseX(scale), MiddleEarthMapTexture.WIDTH * scale, width);
        panY = clampPanAxis(panY, baseY(scale), MiddleEarthMapTexture.HEIGHT * scale, height);
    }

    private static float clampPanAxis(float pan, float base, float drawSize, int screenSize) {
        if (drawSize <= screenSize - MAP_MARGIN * 2) {
            return 0.0F;
        }

        float minPan = MIN_VISIBLE_MAP_PIXELS - base - drawSize;
        float maxPan = screenSize - MIN_VISIBLE_MAP_PIXELS - base;
        return Mth.clamp(pan, minPan, maxPan);
    }

    private void drawRoads(GuiGraphicsExtractor graphics, int mapX, int mapY, int mapWidth, int mapHeight, float scale) {
        float roadAlpha = Mth.clamp(((float) (Math.log(scale) / Math.log(2.0)) + 3.3F) / 2.2F, 0.0F, 1.0F);
        if (roadAlpha <= 0.0F) {
            return;
        }

        double minMapX = (Math.max(0, -mapX) / (double) scale) - 8.0D;
        double minMapZ = (Math.max(0, -mapY) / (double) scale) - 8.0D;
        double maxMapX = ((Math.min(width, mapX + mapWidth) - mapX) / (double) scale) + 8.0D;
        double maxMapZ = ((Math.min(height, mapY + mapHeight) - mapY) / (double) scale) + 8.0D;
        double minBlockX = (minMapX - MiddleEarthMapConstants.MAP_ORIGIN_X) * MiddleEarthMapConstants.MAP_SCALE;
        double minBlockZ = (minMapZ - MiddleEarthMapConstants.MAP_ORIGIN_Z) * MiddleEarthMapConstants.MAP_SCALE;
        double maxBlockX = (maxMapX - MiddleEarthMapConstants.MAP_ORIGIN_X) * MiddleEarthMapConstants.MAP_SCALE;
        double maxBlockZ = (maxMapZ - MiddleEarthMapConstants.MAP_ORIGIN_Z) * MiddleEarthMapConstants.MAP_SCALE;

        int pointStep = Math.max(1, Math.round(24.0F / Math.max(scale, 0.05F)));
        int roadHalfWidth = Math.max(1, Math.round(scale * 1.4F));
        int roadColor = ((int) (roadAlpha * 176.0F) << 24) | (ROAD_COLOR & 0xFFFFFF);
        int shadowColor = ((int) (roadAlpha * 128.0F) << 24) | (ROAD_SHADOW_COLOR & 0xFFFFFF);

        graphics.enableScissor(
                Math.max(0, mapX),
                Math.max(0, mapY),
                Math.min(width, mapX + mapWidth),
                Math.min(height, mapY + mapHeight));

        for (MiddleEarthRoads.Road road : MiddleEarthRoads.allRoadsForDisplay()) {
            if (!road.intersectsBlockBounds(minBlockX, minBlockZ, maxBlockX, maxBlockZ)) {
                continue;
            }
            MiddleEarthRoads.RoadPoint[] points = road.roadPoints();
            for (int i = 0; i < points.length; i += pointStep) {
                MiddleEarthRoads.RoadPoint point = points[i];
                int x = mapX + (int) Math.round(point.mapX() * scale);
                int y = mapY + (int) Math.round(point.mapZ() * scale);
                if (x < mapX || x >= mapX + mapWidth || y < mapY || y >= mapY + mapHeight) {
                    continue;
                }
                graphics.fill(x - roadHalfWidth, y - roadHalfWidth, x + roadHalfWidth + 1, y + roadHalfWidth + 1, shadowColor);
                graphics.fill(x - roadHalfWidth + 1, y - roadHalfWidth + 1, x + roadHalfWidth, y + roadHalfWidth, roadColor);
            }
        }

        graphics.disableScissor();
    }

    private void drawMapLabels(GuiGraphicsExtractor graphics, int mapX, int mapY, int mapWidth, int mapHeight, float scale) {
        float zoomExp = (float) (Math.log(scale) / Math.log(2.0));
        graphics.enableScissor(
                Math.max(0, mapX - 256),
                Math.max(0, mapY - 96),
                Math.min(width, mapX + mapWidth + 256),
                Math.min(height, mapY + mapHeight + 96));

        for (MiddleEarthMapLabels.Label label : MiddleEarthMapLabels.ALL) {
            float zoomLerp = (zoomExp - label.minZoom()) / (label.maxZoom() - label.minZoom());
            if (zoomLerp <= 0.0F || zoomLerp >= 1.0F) {
                continue;
            }

            float alpha = (0.5F - Math.abs(zoomLerp - 0.5F)) / 0.5F * 0.7F;
            int alphaInt = Mth.clamp((int) (alpha * 255.0F), 4, 255);
            int screenX = mapX + Math.round(label.x() * scale);
            int screenY = mapY + Math.round(label.y() * scale);
            if (screenX < mapX - 200 || screenX > mapX + mapWidth + 200 || screenY < mapY - 80 || screenY > mapY + mapHeight + 80) {
                continue;
            }

            var text = label.text();
            float labelScale = Math.max(0.35F, label.scale() * scale);
            int textX = -Math.round(font.width(text) * 0.5F);
            int textY = -Math.round(font.lineHeight * 0.5F);
            int color = alphaInt << 24 | LABEL_COLOR;
            int shadow = alphaInt << 24 | LABEL_SHADOW_COLOR;

            graphics.pose().pushMatrix();
            graphics.pose().translate(screenX, screenY);
            graphics.pose().rotate((float) Math.toRadians(label.angle()));
            graphics.pose().scale(labelScale, labelScale);
            graphics.text(font, text, textX + 1, textY + 1, shadow, false);
            graphics.text(font, text, textX, textY, color, false);
            graphics.pose().popMatrix();
        }

        graphics.disableScissor();
    }

    private static void drawPlayerMarker(GuiGraphicsExtractor graphics, int mapX, int mapY, int mapWidth, int mapHeight) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        double worldMapX = MiddleEarthMapConstants.blockToMapX(player.getBlockX());
        double worldMapZ = MiddleEarthMapConstants.blockToMapZ(player.getBlockZ());
        if (worldMapX < 0.0 || worldMapX >= MiddleEarthMapConstants.MAP_WIDTH || worldMapZ < 0.0 || worldMapZ >= MiddleEarthMapConstants.MAP_HEIGHT) {
            return;
        }

        int markerX = mapX + (int) Math.round(worldMapX / MiddleEarthMapConstants.MAP_WIDTH * mapWidth);
        int markerY = mapY + (int) Math.round(worldMapZ / MiddleEarthMapConstants.MAP_HEIGHT * mapHeight);
        int directionX = Math.round(-Mth.sin(player.getYRot() * Mth.DEG_TO_RAD) * 4.0F);
        int directionY = Math.round(Mth.cos(player.getYRot() * Mth.DEG_TO_RAD) * 4.0F);

        graphics.fill(markerX - 2, markerY - 2, markerX + 3, markerY + 3, MARKER_OUTLINE);
        graphics.fill(markerX - 1, markerY - 1, markerX + 2, markerY + 2, MARKER_COLOR);
        graphics.fill(markerX + directionX, markerY + directionY, markerX + directionX + 1, markerY + directionY + 1, MARKER_COLOR);
    }
}
