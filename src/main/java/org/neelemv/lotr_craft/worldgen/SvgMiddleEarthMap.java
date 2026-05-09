package org.neelemv.lotr_craft.worldgen;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class SvgMiddleEarthMap {
    private static final Logger LOGGER = LoggerFactory.getLogger(SvgMiddleEarthMap.class);
    private static final String MAP_RESOURCE = "/assets/map/lotr.svg";
    private static final double TERRAIN_BLEND_RADIUS = 1.35;
    private static final int TERRAIN_BLEND_CELL_RADIUS = 2;
    private static final SvgMiddleEarthMap INSTANCE = load();

    private final byte[] terrainProfiles;

    private SvgMiddleEarthMap(byte[] terrainProfiles) {
        this.terrainProfiles = terrainProfiles;
    }

    public static SvgMiddleEarthMap get() {
        return INSTANCE;
    }

    MiddleEarthTerrainProfile terrainAtBlock(int blockX, int blockZ) {
        double mapX = MiddleEarthMapConstants.blockToMapX(blockX);
        double mapZ = MiddleEarthMapConstants.blockToMapZ(blockZ);
        return terrainAtMapPixel(mapX, mapZ);
    }

    TerrainBlend terrainBlendAtBlock(int blockX, int blockZ) {
        double mapX = MiddleEarthMapConstants.blockToMapX(blockX);
        double mapZ = MiddleEarthMapConstants.blockToMapZ(blockZ);
        int centerX = fastFloor(mapX);
        int centerZ = fastFloor(mapZ);
        double totalWeight = 0.0;
        double baseHeight = 0.0;
        double variation = 0.0;
        double roughness = 0.0;
        double water = 0.0;
        double[] profileWeights = new double[MiddleEarthTerrainProfile.count()];

        for (int dz = -TERRAIN_BLEND_CELL_RADIUS; dz <= TERRAIN_BLEND_CELL_RADIUS; dz++) {
            int sampleZ = centerZ + dz;
            double weightZ = blendWeight(mapZ, sampleZ);
            if (weightZ <= 0.0) {
                continue;
            }
            for (int dx = -TERRAIN_BLEND_CELL_RADIUS; dx <= TERRAIN_BLEND_CELL_RADIUS; dx++) {
                int sampleX = centerX + dx;
                double weight = weightZ * blendWeight(mapX, sampleX);
                if (weight <= 0.0) {
                    continue;
                }

                MiddleEarthTerrainProfile profile = MiddleEarthTerrainProfile.fromId(profileIdAtMapPixel(sampleX, sampleZ));
                totalWeight += weight;
                baseHeight += profile.baseHeight * weight;
                variation += profile.variation * weight;
                roughness += profile.roughness * weight;
                water += (profile.water ? 1.0 : 0.0) * weight;
                profileWeights[profile.id()] += weight;
            }
        }

        if (totalWeight <= 0.0) {
            return TerrainBlend.of(MiddleEarthTerrainProfile.OCEAN);
        }

        int dominantId = MiddleEarthTerrainProfile.OCEAN.id();
        double dominantWeight = -1.0;
        for (int i = 0; i < profileWeights.length; i++) {
            if (profileWeights[i] > dominantWeight) {
                dominantWeight = profileWeights[i];
                dominantId = i;
            }
        }

        double inverseWeight = 1.0 / totalWeight;
        return new TerrainBlend(
                MiddleEarthTerrainProfile.fromId(dominantId),
                baseHeight * inverseWeight,
                variation * inverseWeight,
                roughness * inverseWeight,
                water * inverseWeight);
    }

    public int colorAtMapPixel(int x, int z) {
        return MiddleEarthTerrainProfile.colorForId(profileIdAtMapPixel(x, z));
    }

    private static double blendWeight(double coordinate, int sample) {
        double distance = Math.abs(coordinate - (sample + 0.5));
        if (distance >= TERRAIN_BLEND_RADIUS) {
            return 0.0;
        }
        double normalized = 1.0 - distance / TERRAIN_BLEND_RADIUS;
        return normalized * normalized * (3.0 - 2.0 * normalized);
    }

    private MiddleEarthTerrainProfile terrainAtMapPixel(double mapX, double mapZ) {
        int x = fastFloor(mapX);
        int z = fastFloor(mapZ);
        return MiddleEarthTerrainProfile.fromId(profileIdAtMapPixel(x, z));
    }

    private int profileIdAtMapPixel(int x, int z) {
        if (x < 0 || x >= MiddleEarthMapConstants.MAP_WIDTH || z < 0 || z >= MiddleEarthMapConstants.MAP_HEIGHT) {
            return MiddleEarthTerrainProfile.OCEAN.id();
        }
        return terrainProfiles[x + z * MiddleEarthMapConstants.MAP_WIDTH] & 0xFF;
    }

    private static byte[] rasterizeProfiles(List<ShapeEntry> shapes) {
        BufferedImage image = new BufferedImage(
                MiddleEarthMapConstants.MAP_WIDTH,
                MiddleEarthMapConstants.MAP_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        try {
            graphics.setComposite(AlphaComposite.Src);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            graphics.setColor(new Color(MiddleEarthTerrainProfile.OCEAN.id(), 0, 0));
            graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
            for (ShapeEntry shape : shapes) {
                graphics.setColor(new Color(shape.profile.id(), 0, 0));
                graphics.fill(shape.path);
            }
        } finally {
            graphics.dispose();
        }

        byte[] profiles = new byte[MiddleEarthMapConstants.MAP_WIDTH * MiddleEarthMapConstants.MAP_HEIGHT];
        int offset = 0;
        for (int z = 0; z < MiddleEarthMapConstants.MAP_HEIGHT; z++) {
            for (int x = 0; x < MiddleEarthMapConstants.MAP_WIDTH; x++) {
                profiles[offset++] = (byte) (image.getRGB(x, z) >> 16 & 0xFF);
            }
        }
        return profiles;
    }

    private static SvgMiddleEarthMap load() {
        try (InputStream stream = SvgMiddleEarthMap.class.getResourceAsStream(MAP_RESOURCE)) {
            if (stream == null) {
                LOGGER.warn("Missing {}, Middle-earth terrain will fall back to ocean", MAP_RESOURCE);
                return empty();
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setExpandEntityReferences(false);
            Document document = factory.newDocumentBuilder().parse(stream);
            NodeList paths = document.getElementsByTagName("path");
            List<ShapeEntry> shapes = new ArrayList<>(paths.getLength());

            for (int i = 0; i < paths.getLength(); i++) {
                Element path = (Element) paths.item(i);
                String fill = findFill(path);
                String d = path.getAttribute("d");
                if (fill == null || d == null || d.isBlank()) {
                    continue;
                }
                int color = Integer.parseInt(fill.substring(1), 16);
                shapes.add(new ShapeEntry(parsePath(d), MiddleEarthTerrainProfile.fromColor(color)));
            }

            long started = System.nanoTime();
            byte[] profiles = rasterizeProfiles(shapes);
            LOGGER.info("Loaded Middle-earth terrain map: {} SVG paths rasterized to {} profile samples in {} ms",
                    shapes.size(),
                    profiles.length,
                    (System.nanoTime() - started) / 1_000_000L);
            return new SvgMiddleEarthMap(profiles);
        } catch (Exception exception) {
            LOGGER.error("Failed to load Middle-earth SVG map", exception);
            return empty();
        }
    }

    private static SvgMiddleEarthMap empty() {
        byte[] profiles = new byte[MiddleEarthMapConstants.MAP_WIDTH * MiddleEarthMapConstants.MAP_HEIGHT];
        return new SvgMiddleEarthMap(profiles);
    }

    private static String findFill(Element path) {
        String fill = path.getAttribute("fill");
        if (isHexColor(fill)) {
            return fill;
        }
        String style = path.getAttribute("style");
        for (String part : style.split(";")) {
            String trimmed = part.trim();
            if (trimmed.startsWith("fill:")) {
                String value = trimmed.substring("fill:".length()).trim();
                return isHexColor(value) ? value : null;
            }
        }
        return null;
    }

    private static boolean isHexColor(String value) {
        return value != null && value.matches("#[0-9a-fA-F]{6}");
    }

    private static Path2D.Double parsePath(String d) {
        PathParser parser = new PathParser(d);
        return parser.parse();
    }

    private static int fastFloor(double value) {
        int i = (int) value;
        return value < i ? i - 1 : i;
    }

    private record ShapeEntry(Path2D.Double path, MiddleEarthTerrainProfile profile) {
    }

    private static final class PathParser {
        private final String input;
        private int index;
        private double x;
        private double y;
        private double subPathX;
        private double subPathY;

        private PathParser(String input) {
            this.input = input;
        }

        private Path2D.Double parse() {
            Path2D.Double path = new Path2D.Double(Path2D.WIND_NON_ZERO);
            char command = 0;
            while (skipSeparators()) {
                if (isCommand(peek())) {
                    command = input.charAt(index++);
                } else if (command == 0) {
                    throw new IllegalArgumentException("SVG path starts without command");
                }
                command = apply(path, command);
            }
            return path;
        }

        private char apply(Path2D.Double path, char command) {
            boolean relative = Character.isLowerCase(command);
            char upper = Character.toUpperCase(command);
            switch (upper) {
                case 'M' -> {
                    double nx = nextNumber();
                    double ny = nextNumber();
                    if (relative) {
                        nx += x;
                        ny += y;
                    }
                    path.moveTo(nx, ny);
                    x = subPathX = nx;
                    y = subPathY = ny;
                    while (hasNumberAhead()) {
                        lineTo(path, nextNumber(), nextNumber(), relative);
                    }
                    return relative ? 'l' : 'L';
                }
                case 'L' -> {
                    while (hasNumberAhead()) {
                        lineTo(path, nextNumber(), nextNumber(), relative);
                    }
                }
                case 'H' -> {
                    while (hasNumberAhead()) {
                        double nx = nextNumber();
                        if (relative) {
                            nx += x;
                        }
                        path.lineTo(nx, y);
                        x = nx;
                    }
                }
                case 'V' -> {
                    while (hasNumberAhead()) {
                        double ny = nextNumber();
                        if (relative) {
                            ny += y;
                        }
                        path.lineTo(x, ny);
                        y = ny;
                    }
                }
                case 'C' -> {
                    while (hasNumberAhead()) {
                        double x1 = adjustedX(nextNumber(), relative);
                        double y1 = adjustedY(nextNumber(), relative);
                        double x2 = adjustedX(nextNumber(), relative);
                        double y2 = adjustedY(nextNumber(), relative);
                        double nx = adjustedX(nextNumber(), relative);
                        double ny = adjustedY(nextNumber(), relative);
                        path.curveTo(x1, y1, x2, y2, nx, ny);
                        x = nx;
                        y = ny;
                    }
                }
                case 'S' -> {
                    while (hasNumberAhead()) {
                        double x2 = adjustedX(nextNumber(), relative);
                        double y2 = adjustedY(nextNumber(), relative);
                        double nx = adjustedX(nextNumber(), relative);
                        double ny = adjustedY(nextNumber(), relative);
                        path.curveTo(x, y, x2, y2, nx, ny);
                        x = nx;
                        y = ny;
                    }
                }
                case 'Q' -> {
                    while (hasNumberAhead()) {
                        double x1 = adjustedX(nextNumber(), relative);
                        double y1 = adjustedY(nextNumber(), relative);
                        double nx = adjustedX(nextNumber(), relative);
                        double ny = adjustedY(nextNumber(), relative);
                        path.quadTo(x1, y1, nx, ny);
                        x = nx;
                        y = ny;
                    }
                }
                case 'T' -> {
                    while (hasNumberAhead()) {
                        double nx = adjustedX(nextNumber(), relative);
                        double ny = adjustedY(nextNumber(), relative);
                        path.quadTo(x, y, nx, ny);
                        x = nx;
                        y = ny;
                    }
                }
                case 'A' -> {
                    while (hasNumberAhead()) {
                        nextNumber();
                        nextNumber();
                        nextNumber();
                        nextNumber();
                        nextNumber();
                        lineTo(path, nextNumber(), nextNumber(), relative);
                    }
                }
                case 'Z' -> {
                    path.closePath();
                    x = subPathX;
                    y = subPathY;
                }
                default -> throw new IllegalArgumentException("Unsupported SVG path command " + command);
            }
            return command;
        }

        private void lineTo(Path2D.Double path, double nx, double ny, boolean relative) {
            if (relative) {
                nx += x;
                ny += y;
            }
            path.lineTo(nx, ny);
            x = nx;
            y = ny;
        }

        private double adjustedX(double value, boolean relative) {
            return relative ? x + value : value;
        }

        private double adjustedY(double value, boolean relative) {
            return relative ? y + value : value;
        }

        private boolean hasNumberAhead() {
            return skipSeparators() && !isCommand(peek());
        }

        private double nextNumber() {
            skipSeparators();
            int start = index;
            if (peek() == '+' || peek() == '-') {
                index++;
            }
            while (index < input.length() && Character.isDigit(input.charAt(index))) {
                index++;
            }
            if (index < input.length() && input.charAt(index) == '.') {
                index++;
                while (index < input.length() && Character.isDigit(input.charAt(index))) {
                    index++;
                }
            }
            if (index < input.length() && (input.charAt(index) == 'e' || input.charAt(index) == 'E')) {
                index++;
                if (index < input.length() && (input.charAt(index) == '+' || input.charAt(index) == '-')) {
                    index++;
                }
                while (index < input.length() && Character.isDigit(input.charAt(index))) {
                    index++;
                }
            }
            return Double.parseDouble(input.substring(start, index).toLowerCase(Locale.ROOT));
        }

        private boolean skipSeparators() {
            while (index < input.length()) {
                char c = input.charAt(index);
                if (!Character.isWhitespace(c) && c != ',') {
                    return true;
                }
                index++;
            }
            return false;
        }

        private char peek() {
            return input.charAt(index);
        }

        private static boolean isCommand(char c) {
            return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
        }
    }
}
