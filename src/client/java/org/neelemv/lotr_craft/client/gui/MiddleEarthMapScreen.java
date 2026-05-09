package org.neelemv.lotr_craft.client.gui;

import org.neelemv.lotr_craft.client.LotrKeyMappings;
import org.neelemv.lotr_craft.network.MiddleEarthMapTeleportPayload;
import org.neelemv.lotr_craft.worldgen.MiddleEarthMapConstants;

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
    private static final int RIGHT_MOUSE_BUTTON = 1;
    private static final float MIN_ZOOM = 1.0F;
    private static final float MAX_ZOOM = 8.0F;
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
