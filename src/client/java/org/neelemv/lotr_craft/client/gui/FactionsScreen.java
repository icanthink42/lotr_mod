package org.neelemv.lotr_craft.client.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.faction.FactionMapRegion;
import org.neelemv.lotr_craft.faction.FactionRegion;
import org.neelemv.lotr_craft.faction.FactionRelation;
import org.neelemv.lotr_craft.faction.LotrFaction;
import org.neelemv.lotr_craft.faction.LotrFactionRelations;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public final class FactionsScreen extends Screen {
    private static final Identifier FACTIONS_TEXTURE = Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "textures/gui/factions.png");
    private static final int PAGE_WIDTH = 256;
    private static final int PAGE_HEIGHT = 128;
    private static final int PAGE_Y = 46;
    private static final int TEXT_COLOR = 0xFF7A5D43;
    private static final int TITLE_COLOR = 0xFFFFFFFF;
    private static final int FACTION_NAME_COLOR = 0xFFEAD8B5;
    private static final int BUTTON_COLOR = 0xFF4C3524;
    private static final int BUTTON_HOVER_COLOR = 0xFF6B4A31;
    private static final int SCROLL_BAR_WIDTH = 240;
    private static final int SCROLL_BAR_HEIGHT = 14;
    private static final int MAP_SIZE = 72;

    private static final FactionRegion[] REGIONS = { FactionRegion.WEST, FactionRegion.EAST, FactionRegion.SOUTH };

    private FactionRegion currentRegion = FactionRegion.WEST;
    private InfoPage currentInfoPage = InfoPage.FRIENDS;
    private int currentFactionIndex;

    public FactionsScreen() {
        super(Component.translatable("screen.lotr_craft.factions"));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, width, height, 0xD0100D09);

        int left = (width - PAGE_WIDTH) / 2;
        int top = (height - 210) / 2;
        int pageTop = top + PAGE_Y;
        List<LotrFaction> factions = currentFactions();
        LotrFaction faction = factions.get(currentFactionIndex);

        graphics.blit(RenderPipelines.GUI_TEXTURED, FACTIONS_TEXTURE, left, pageTop, 0.0F, 0.0F, PAGE_WIDTH, PAGE_HEIGHT, 256, 256);
        drawTitle(graphics, top, faction);
        drawRegionButton(graphics, left, top, mouseX, mouseY);
        drawInfoPage(graphics, left, pageTop, faction);
        drawMapPage(graphics, left, pageTop, faction);

        drawFactionScrollBar(graphics, left, top, factions);
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        int left = (width - PAGE_WIDTH) / 2;
        int top = (height - 210) / 2;
        int pageTop = top + PAGE_Y;
        double mouseX = event.x();
        double mouseY = event.y();

        if (inside(mouseX, mouseY, left + 82, top + 2, 92, 18)) {
            cycleRegion();
            return true;
        }

        if (inside(mouseX, mouseY, left + 16, pageTop + 105, 16, 14)) {
            flipInfoPage(-1);
            return true;
        }
        if (inside(mouseX, mouseY, left + 106, pageTop + 105, 16, 14)) {
            flipInfoPage(1);
            return true;
        }

        int scrollX = left + PAGE_WIDTH / 2 - SCROLL_BAR_WIDTH / 2;
        int scrollY = top + 180;
        if (inside(mouseX, mouseY, scrollX, scrollY, SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT)) {
            List<LotrFaction> factions = currentFactions();
            double normalized = Mth.clamp((mouseX - scrollX) / SCROLL_BAR_WIDTH, 0.0, 0.999);
            currentFactionIndex = Math.min(factions.size() - 1, (int) (normalized * factions.size()));
            return true;
        }

        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY == 0.0) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        List<LotrFaction> factions = currentFactions();
        currentFactionIndex = Mth.clamp(currentFactionIndex + (scrollY < 0.0 ? 1 : -1), 0, factions.size() - 1);
        return true;
    }

    private void drawTitle(GuiGraphicsExtractor graphics, int top, LotrFaction faction) {
        Component title = Component.translatable("screen.lotr_craft.factions");
        graphics.text(font, title, (width - font.width(title)) / 2, top - 18, TITLE_COLOR, false);
        Component name = Component.translatable(faction.translationKey());
        graphics.text(font, name, (width - font.width(name)) / 2, top + 24, FACTION_NAME_COLOR, false);
    }

    private void drawRegionButton(GuiGraphicsExtractor graphics, int left, int top, int mouseX, int mouseY) {
        int x = left + 82;
        int y = top + 2;
        boolean hover = inside(mouseX, mouseY, x, y, 92, 18);
        graphics.fill(x, y, x + 92, y + 18, hover ? BUTTON_HOVER_COLOR : BUTTON_COLOR);
        Component text = Component.translatable("faction_region.lotr_craft." + currentRegion.name().toLowerCase());
        graphics.text(font, text, x + 46 - font.width(text) / 2, y + 5, TITLE_COLOR, false);
    }

    private void drawInfoPage(GuiGraphicsExtractor graphics, int left, int top, LotrFaction faction) {
        int x = left + 18;
        int y = top + 14;
        Component heading = Component.translatable(currentInfoPage.translationKey).withStyle(Style.EMPTY.withBold(true));
        graphics.text(font, heading, x, y, 0xFF000000, false);
        y += 13;

        List<LotrFaction> relations = relatedFactions(faction, currentInfoPage);
        if (relations.isEmpty()) {
            graphics.text(font, Component.translatable("faction_detail.lotr_craft.none"), x, y, TEXT_COLOR, false);
        } else {
            int end = Math.min(relations.size(), 6);
            for (int i = 0; i < end; i++) {
                LotrFaction related = relations.get(i);
                Component name = Component.translatable(related.translationKey());
                graphics.text(font, name, x, y, 0xFF000000 | related.color(), false);
                y += 10;
            }
            if (relations.size() > end) {
                graphics.text(font, Component.literal("+" + (relations.size() - end)), x, y, TEXT_COLOR, false);
            }
        }

        drawPageFlip(graphics, left, top);
    }

    private void drawMapPage(GuiGraphicsExtractor graphics, int left, int top, LotrFaction faction) {
        FactionMapRegion map = faction.mapRegion();
        if (map != null) {
            int mapX = left + 164;
            int mapY = top + 28;
            int sampleSize = Mth.clamp(Math.round(map.radius() * 3.5F), 220, 900);
            sampleSize = Math.min(sampleSize, Math.min(MiddleEarthMapTexture.WIDTH, MiddleEarthMapTexture.HEIGHT));
            int srcX = Mth.clamp(map.mapX() - sampleSize / 2, 0, MiddleEarthMapTexture.WIDTH - sampleSize);
            int srcY = Mth.clamp(map.mapY() - sampleSize / 2, 0, MiddleEarthMapTexture.HEIGHT - sampleSize);
            graphics.fill(mapX - 1, mapY - 1, mapX + MAP_SIZE + 1, mapY + MAP_SIZE + 1, 0xFF000000);
            graphics.blit(RenderPipelines.GUI_TEXTURED, MiddleEarthMapTexture.texture(), mapX, mapY,
                    srcX, srcY, MAP_SIZE, MAP_SIZE, sampleSize, sampleSize, MiddleEarthMapTexture.WIDTH, MiddleEarthMapTexture.HEIGHT, -1);
        }
    }

    private void drawPageFlip(GuiGraphicsExtractor graphics, int left, int top) {
        int y = top + 108;
        graphics.text(font, Component.literal("<"), left + 20, y, TEXT_COLOR, false);
        graphics.text(font, Component.literal(">"), left + 112, y, TEXT_COLOR, false);
    }

    private void drawFactionScrollBar(GuiGraphicsExtractor graphics, int left, int top, List<LotrFaction> factions) {
        int x = left + PAGE_WIDTH / 2 - SCROLL_BAR_WIDTH / 2;
        int y = top + 180;
        graphics.blit(RenderPipelines.GUI_TEXTURED, FACTIONS_TEXTURE, x, y, 0.0F, 128.0F, SCROLL_BAR_WIDTH, SCROLL_BAR_HEIGHT, 256, 256);
        int innerWidth = SCROLL_BAR_WIDTH - 2;
        for (int i = 0; i < factions.size(); i++) {
            int minX = x + 1 + i * innerWidth / factions.size();
            int maxX = x + 1 + (i + 1) * innerWidth / factions.size();
            graphics.fill(minX, y + 1, Math.max(minX + 1, maxX), y + SCROLL_BAR_HEIGHT - 1, 0xFF000000 | factions.get(i).color());
        }
        int widgetRange = SCROLL_BAR_WIDTH - 2 - 17;
        int widgetX = x + 1 + Math.round((factions.size() <= 1 ? 0.0F : currentFactionIndex / (float) (factions.size() - 1)) * widgetRange);
        graphics.blit(RenderPipelines.GUI_TEXTURED, FACTIONS_TEXTURE, widgetX, y + 1, 0.0F, 142.0F, 17, 12, 256, 256);
    }

    private List<LotrFaction> currentFactions() {
        List<LotrFaction> factions = new ArrayList<>();
        for (LotrFaction faction : LotrFaction.values()) {
            if (faction.region() == currentRegion && faction.playerAllowed()) {
                factions.add(faction);
            }
        }
        factions.sort(Comparator.comparingInt(Enum::ordinal));
        currentFactionIndex = Mth.clamp(currentFactionIndex, 0, Math.max(0, factions.size() - 1));
        return factions;
    }

    private void cycleRegion() {
        int index = currentRegionIndex();
        currentRegion = REGIONS[(index + 1) % REGIONS.length];
        currentFactionIndex = 0;
        currentInfoPage = InfoPage.FRIENDS;
    }

    private int currentRegionIndex() {
        for (int i = 0; i < REGIONS.length; i++) {
            if (REGIONS[i] == currentRegion) {
                return i;
            }
        }
        return 0;
    }

    private static boolean inside(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    private void flipInfoPage(int direction) {
        InfoPage[] pages = InfoPage.values();
        currentInfoPage = pages[Math.floorMod(currentInfoPage.ordinal() + direction, pages.length)];
    }

    private static List<LotrFaction> relatedFactions(LotrFaction faction, InfoPage page) {
        List<LotrFaction> related = new ArrayList<>();
        for (LotrFaction other : LotrFaction.values()) {
            if (other == faction || !other.playerAllowed()) {
                continue;
            }
            FactionRelation relation = LotrFactionRelations.relationBetween(faction, other);
            if (page.matches(relation)) {
                related.add(other);
            }
        }
        return related;
    }

    private enum InfoPage {
        FRIENDS("faction_page.lotr_craft.friends") {
            @Override
            boolean matches(FactionRelation relation) {
                return relation == FactionRelation.FRIEND;
            }
        },
        ALLIES("faction_page.lotr_craft.allies") {
            @Override
            boolean matches(FactionRelation relation) {
                return relation == FactionRelation.ALLY;
            }
        },
        ENEMIES("faction_page.lotr_craft.enemies") {
            @Override
            boolean matches(FactionRelation relation) {
                return relation == FactionRelation.ENEMY || relation == FactionRelation.MORTAL_ENEMY;
            }
        };

        private final String translationKey;

        InfoPage(String translationKey) {
            this.translationKey = translationKey;
        }

        abstract boolean matches(FactionRelation relation);
    }
}
