package org.neelemv.lotr_craft.worldgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MiddleEarthRoads {
    private static final int LOOKUP_SIZE = 128;
    private static final int LOOKUP_OVERLAP = 1;
    private static final int ROAD_POINT_SPACING = 2;

    private static final List<Road> WORLD_ROADS = new ArrayList<>();
    private static final List<Road> DISPLAY_ROADS = new ArrayList<>();
    private static final List<Road> ALL_DISPLAY_ROADS = new ArrayList<>();
    private static final Map<Long, List<RoadPoint>> ROAD_POINTS = new HashMap<>();

    static {
        createRoads();
        ALL_DISPLAY_ROADS.addAll(WORLD_ROADS);
        ALL_DISPLAY_ROADS.addAll(DISPLAY_ROADS);
    }

    private MiddleEarthRoads() {
    }

    public static List<Road> allRoadsForDisplay() {
        return Collections.unmodifiableList(ALL_DISPLAY_ROADS);
    }

    public static boolean isRoadAt(int blockX, int blockZ) {
        return roadNear(blockX, blockZ, 5) >= 0.0F;
    }

    public static float roadNear(int blockX, int blockZ, int width) {
        double widthSq = width * width;
        float leastSqRatio = -1.0F;
        for (RoadPoint point : getPointsForCoords(blockX, blockZ)) {
            double dx = point.x - blockX;
            double dz = point.z - blockZ;
            double distSq = dx * dx + dz * dz;
            if (distSq >= widthSq) {
                continue;
            }
            float ratio = (float) (distSq / widthSq);
            if (leastSqRatio < 0.0F || ratio < leastSqRatio) {
                leastSqRatio = ratio;
            }
        }
        return leastSqRatio;
    }

    private static void createRoads() {
        registerRoad("EredLuin", p(626.000000, 636.000000), p(622.000000, 600.000000));
        registerRoad("NogrodForlond", p(626.000000, 636.000000), p(526.000000, 718.000000));
        registerRoad("NogrodMithlond", p(626.000000, 636.000000), p(654.000000, 650.000000), p(669.000000, 717.000000));
        registerRoad("Mithlond", p(605.000000, 783.000000), p(658.000000, 755.000000), p(679.000000, 729.000000), p(690.000000, 711.000000), p(681.000000, 705.000000), p(669.000000, 717.000000), p(644.000000, 733.000000), p(603.000000, 733.000000), p(554.000000, 715.000000), p(526.000000, 718.000000));
        registerRoad("WestEast", p(679.000000, 729.000000), p(710.000000, 742.000000), p(762.000000, 745.000000), p(796.000000, 739.000000), p(807.000000, 733.000000), p(820.000000, 730.000000), p(831.000000, 728.000000), p(843.000000, 727.000000), p(853.000000, 725.000000), p(870.000000, 718.000000), p(902.000000, 729.000000), p(915.000000, 734.000000));
        registerRoad("WestEast", p(915.000000, 734.000000), p(915.500000, 734.000000));
        registerRoad("WestEast", p(917.000000, 735.500000), p(924.000000, 739.000000), p(950.000000, 743.000000), p(998.000000, 725.000000), p(1088.000000, 714.000000), p(1132.000000, 723.000000), p(1178.000000, 704.000000), p(1222.000000, 706.000000), p(1284.000000, 702.000000), p(1474.000000, 696.000000), p(1567.000000, 680.000000), p(1651.000000, 690.000000), p(1785.000000, 775.000000), p(1942.000000, 811.000000), p(2045.000000, 815.000000), p(2228.000000, 835.000000), p(2326.000000, 800.000000));
        registerRoad("WestEast", p(914.625000, 731.524000), p(917.000000, 732.500000));
        registerDisplayOnlyRoad("WestEast", p(915.500000, 734.000000), p(917.000000, 734.000000));
        registerDisplayOnlyRoad("WestEast", p(917.000000, 732.500000), p(917.000000, 735.500000));
        registerRoad("BywaterRoad", p(820.000000, 730.000000), p(815.000000, 727.000000));
        registerRoad("Overhill", p(815.000000, 727.000000), p(817.000000, 720.000000));
        registerRoad("BucklandRoad", p(856.000000, 728.000000), p(857.000000, 734.000000), p(858.000000, 747.000000));
        registerRoad("Chetroad", p(924.000000, 739.000000), p(924.000000, 734.000000), p(927.000000, 731.000000), p(928.000000, 728.000000));
        registerRoad("Chetroad", p(924.000000, 734.000000), p(923.500000, 734.000000));
        registerRoad("Chetroad", p(927.000000, 731.000000), p(927.500000, 731.000000));
        registerRoad("Chetroad", p(928.000000, 728.000000), p(928.000000, 727.500000));
        registerRoad("ElfPath", p(1303.000000, 655.000000), p(1396.000000, 650.000000), p(1420.000000, 633.000000));
        registerRoad("EreborRoad", p(1461.000000, 632.000000), p(1464.000000, 615.000000), p(1463.000000, 609.000000));
        registerRoad("DalePortRoad", p(1464.000000, 615.000000), p(1567.000000, 680.000000), p(1657.000000, 768.000000));
        registerRoad("DaleSouthRoad", p(1354.000000, 966.000000), p(1524.000000, 870.000000), p(1534.000000, 749.000000), p(1567.000000, 680.000000), p(1588.000000, 608.000000));
        registerRoad("IronHills", p(1588.000000, 608.000000), p(1652.000000, 621.000000), p(1729.000000, 610.000000));
        registerRoad("DorwinionSouthRoad", p(1657.000000, 768.000000), p(1680.000000, 882.000000), p(1758.000000, 939.000000), p(1776.000000, 986.000000));
        registerRoad("DorwinionEastRoad", p(1524.000000, 870.000000), p(1680.000000, 882.000000), p(1784.000000, 863.000000));
        registerRoad("RhunRoad", p(1776.000000, 986.000000), p(1794.000000, 979.000000), p(1837.000000, 956.000000), p(1867.000000, 984.000000), p(1888.000000, 958.000000), p(1903.000000, 914.000000), p(1921.000000, 889.000000), p(2045.000000, 815.000000));
        registerRoad("RhunEastRoad", p(1903.000000, 914.000000), p(1983.000000, 936.000000), p(2010.000000, 962.000000));
        registerRoad("Nobottle", p(778.000000, 712.000000), p(785.000000, 718.000000), p(797.000000, 710.000000), p(806.000000, 708.000000));
        registerRoad("Oatbarton", p(822.000000, 701.000000), p(831.000000, 728.000000));
        registerRoad("Stock", p(815.000000, 741.000000), p(849.000000, 737.000000));
        registerRoad("Deephallow", p(840.000000, 713.000000), p(843.000000, 727.000000), p(849.000000, 737.000000), p(850.000000, 749.000000));
        registerRoad("Willowbottom", p(845.000000, 752.000000), p(850.000000, 749.000000));
        registerRoad("ArnorRoad", p(814.000000, 661.000000), p(897.000000, 652.000000));
        registerRoad("Greenway", p(897.000000, 652.000000), p(915.000000, 734.000000), p(920.000000, 810.000000));
        registerRoad("ElvenWay", p(1134.000000, 873.000000), p(1133.000000, 867.000000), p(1124.000000, 868.000000), p(1112.000000, 870.000000), p(1073.000000, 864.000000), p(1028.000000, 847.000000), p(1002.000000, 849.000000), p(992.000000, 860.000000), p(979.000000, 878.000000), p(959.000000, 889.000000), p(926.000000, 913.000000), p(902.000000, 942.000000), p(867.000000, 1004.000000));
        registerRoad("BruinenPath", p(1163.000000, 723.000000), p(1173.000000, 721.000000));
        registerRoad("NimrodelRoad", p(1177.000000, 864.000000), p(1198.000000, 894.000000));
        registerRoad("AnduinRoad", p(1470.000000, 1131.000000), p(1428.000000, 1066.000000), p(1354.000000, 966.000000), p(1285.000000, 905.000000), p(1325.000000, 820.000000), p(1318.000000, 735.000000), p(1303.000000, 655.000000));
        registerRoad("DolGuldurRoad", p(1285.000000, 905.000000), p(1339.000000, 894.000000));
        registerRoad("Framsburg", p(1303.000000, 655.000000), p(1278.000000, 605.000000), p(1251.000000, 590.000000), p(1260.000000, 565.000000), p(1262.000000, 554.000000));
        registerRoad("NorthSouth", p(785.000000, 718.000000), p(807.000000, 733.000000), p(820.000000, 765.000000), p(883.000000, 802.000000), p(920.000000, 810.000000), p(979.000000, 878.000000), p(1025.000000, 1050.000000), p(1102.000000, 1087.000000), p(1136.000000, 1108.000000), p(1153.000000, 1122.000000), p(1190.000000, 1148.000000), p(1223.000000, 1178.000000), p(1299.000000, 1202.000000), p(1416.000000, 1231.000000));
        registerRoad("TirithRoad", p(1416.000000, 1231.000000), p(1419.000000, 1247.000000));
        registerRoad("OsgiliathRoad", p(1419.000000, 1247.000000), p(1428.000000, 1246.000000));
        registerRoad("OsgiliathCrossing", p(1428.000000, 1246.000000), p(1435.000000, 1246.000000));
        registerRoad("OsgiliathMorgulRoad", p(1435.000000, 1246.000000), p(1450.000000, 1236.000000), p(1461.000000, 1239.000000));
        registerRoad("GondorSouthRoad", p(1419.000000, 1247.000000), p(1412.000000, 1272.000000), p(1408.000000, 1291.000000), p(1390.000000, 1348.000000), p(1292.000000, 1342.000000), p(1266.000000, 1301.000000), p(1256.000000, 1259.000000), p(1235.000000, 1248.000000), p(1205.000000, 1213.000000), p(1186.000000, 1205.000000));
        registerRoad("IsengardRoad", p(1102.000000, 1087.000000), p(1102.000000, 1061.500000));
        registerRoad("IsengardRoad", p(1102.000000, 1061.500000), p(1102.000000, 1058.000000));
        registerRoad("HelmRoad", p(1136.000000, 1108.000000), p(1128.000000, 1115.000000));
        registerRoad("WoldRoad", p(1190.000000, 1148.000000), p(1239.000000, 1104.000000), p(1260.000000, 1060.000000), p(1285.000000, 1015.000000));
        registerRoad("DolAmroth", p(1266.000000, 1301.000000), p(1241.000000, 1300.000000), p(1189.000000, 1293.000000), p(1185.000000, 1325.000000), p(1158.000000, 1333.000000));
        registerRoad("Pelargir", p(1390.000000, 1348.000000), p(1394.000000, 1352.000000));
        registerRoad("Poros", p(1397.000000, 1355.000000), p(1442.000000, 1370.000000));
        registerRoad("CairAndros", p(1416.000000, 1231.000000), p(1427.000000, 1207.000000), p(1447.000000, 1151.000000));
        registerRoad("SauronRoad", p(1461.000000, 1239.000000), p(1533.000000, 1204.000000), p(1573.000000, 1196.000000), p(1682.000000, 1214.000000), p(1742.000000, 1209.000000), p(1809.000000, 1172.000000), p(1840.000000, 1137.000000), p(1834.000000, 1112.000000), p(1869.000000, 1055.000000), p(1875.000000, 1003.000000), p(1867.000000, 996.000000), p(1867.000000, 984.000000));
        registerRoad("MorannonRoad", p(1470.000000, 1131.000000), p(1470.000000, 1145.000000));
        registerRoad("MorannonRhunRoad", p(1470.000000, 1131.000000), p(1520.000000, 1130.000000), p(1658.000000, 1140.000000), p(1780.000000, 1115.000000), p(1834.000000, 1112.000000), p(1900.000000, 1141.000000), p(1932.000000, 1331.000000), p(1778.000000, 1432.000000), p(1566.000000, 1482.000000), p(1539.000000, 1545.000000), p(1518.000000, 1563.000000), p(1447.000000, 1558.000000), p(1343.000000, 1561.000000), p(1245.000000, 1582.000000), p(1218.000000, 1631.000000), p(1214.000000, 1689.000000), p(1097.000000, 1721.000000));
        registerRoad("GorgorothRoad", p(1470.000000, 1145.000000), p(1493.000000, 1166.000000), p(1573.000000, 1196.000000), p(1643.000000, 1354.000000));
        registerRoad("HaradRoad", p(1470.000000, 1131.000000), p(1447.000000, 1151.000000), p(1450.000000, 1236.000000), p(1442.000000, 1370.000000), p(1429.000000, 1394.000000), p(1408.000000, 1432.000000), p(1428.000000, 1470.000000), p(1435.000000, 1526.000000), p(1503.000000, 1544.000000), p(1518.000000, 1563.000000), p(1563.000000, 1611.000000));
        registerRoad("UmbarRoad", p(1214.000000, 1689.000000), p(1252.000000, 1698.000000), p(1265.000000, 1737.000000), p(1245.000000, 1781.000000), p(1169.000000, 1821.000000), p(1141.000000, 1976.000000));
        registerRoad("GulfRoad", p(1832.000000, 2188.000000), p(1794.000000, 2110.000000), p(1686.000000, 2032.000000), p(1692.000000, 2001.000000), p(1640.000000, 1922.000000), p(1626.000000, 1874.000000), p(1702.000000, 1940.000000), p(1724.000000, 1982.000000), p(1775.000000, 2002.000000), p(1847.000000, 2049.000000));
        registerRoad("JungleNorthRoad", p(952.000000, 2656.000000), p(1084.000000, 2670.000000), p(1419.000000, 2604.000000));
        registerRoad("JungleMangroveRoad", p(1419.000000, 2604.000000), p(1594.000000, 2766.000000), p(1846.000000, 2838.000000));
        registerRoad("JungleDeepRoad", p(1419.000000, 2604.000000), p(1380.000000, 2861.000000), p(1257.000000, 3054.000000), p(1184.000000, 3237.000000));
        registerRoad("JungleWestEastRoad", p(1084.000000, 2670.000000), p(1236.000000, 2787.000000), p(1380.000000, 2861.000000), p(1550.000000, 2856.000000), p(1590.000000, 2940.000000));
        registerRoad("JungleLakeRoad", p(1550.000000, 2856.000000), p(1594.000000, 2766.000000), p(1621.000000, 2673.000000), p(1834.000000, 2523.000000));
    }

    private static RoadPoint p(double mapX, double mapZ) {
        return new RoadPoint(mapToBlockX(mapX), mapToBlockZ(mapZ));
    }

    private static double mapToBlockX(double mapX) {
        return (mapX - MiddleEarthMapConstants.MAP_ORIGIN_X + 0.5D) * MiddleEarthMapConstants.MAP_SCALE;
    }

    private static double mapToBlockZ(double mapZ) {
        return (mapZ - MiddleEarthMapConstants.MAP_ORIGIN_Z + 0.5D) * MiddleEarthMapConstants.MAP_SCALE;
    }

    private static void registerRoad(String name, RoadPoint... waypoints) {
        registerRoadToList(WORLD_ROADS, false, name, waypoints);
    }

    private static void registerDisplayOnlyRoad(String name, RoadPoint... waypoints) {
        registerRoadToList(DISPLAY_ROADS, true, name, waypoints);
    }

    private static void registerRoadToList(List<Road> targetList, boolean displayOnly, String name, RoadPoint... waypoints) {
        Road[] roads = BezierCurves.getSplines(name, displayOnly, waypoints);
        Collections.addAll(targetList, roads);
    }

    private static void addPoint(RoadPoint point) {
        int x = Math.floorDiv((int) Math.round(point.x), LOOKUP_SIZE);
        int z = Math.floorDiv((int) Math.round(point.z), LOOKUP_SIZE);
        for (int dx = -LOOKUP_OVERLAP; dx <= LOOKUP_OVERLAP; dx++) {
            for (int dz = -LOOKUP_OVERLAP; dz <= LOOKUP_OVERLAP; dz++) {
                ROAD_POINTS.computeIfAbsent(key(x + dx, z + dz), ignored -> new ArrayList<>()).add(point);
            }
        }
    }

    private static List<RoadPoint> getPointsForCoords(int blockX, int blockZ) {
        int x = Math.floorDiv(blockX, LOOKUP_SIZE);
        int z = Math.floorDiv(blockZ, LOOKUP_SIZE);
        return ROAD_POINTS.getOrDefault(key(x, z), Collections.emptyList());
    }

    private static long key(int x, int z) {
        return ((long) x << 32) ^ (z & 0xFFFFFFFFL);
    }

    public record Road(String name, boolean displayOnly, RoadPoint[] roadPoints, RoadPoint[] endpoints, double minX, double minZ, double maxX, double maxZ) {
        public boolean intersectsBlockBounds(double minBlockX, double minBlockZ, double maxBlockX, double maxBlockZ) {
            return maxX >= minBlockX && minX <= maxBlockX && maxZ >= minBlockZ && minZ <= maxBlockZ;
        }
    }

    public record RoadPoint(double x, double z) {
        public double mapX() {
            return MiddleEarthMapConstants.blockToMapX((int) Math.round(x));
        }

        public double mapZ() {
            return MiddleEarthMapConstants.blockToMapZ((int) Math.round(z));
        }
    }

    private static final class BezierCurves {
        private static RoadPoint bezier(RoadPoint a, RoadPoint b, RoadPoint c, RoadPoint d, double t) {
            RoadPoint ab = lerp(a, b, t);
            RoadPoint bc = lerp(b, c, t);
            RoadPoint cd = lerp(c, d, t);
            RoadPoint abbc = lerp(ab, bc, t);
            RoadPoint bccd = lerp(bc, cd, t);
            return lerp(abbc, bccd, t);
        }

        private static double[][] getControlPoints(double[] src) {
            int length = src.length - 1;
            double[] p1 = new double[length];
            double[] p2 = new double[length];
            double[] a = new double[length];
            double[] b = new double[length];
            double[] c = new double[length];
            double[] r = new double[length];
            a[0] = 0.0D;
            b[0] = 2.0D;
            c[0] = 1.0D;
            r[0] = src[0] + 2.0D * src[1];

            for (int i = 1; i < length - 1; i++) {
                a[i] = 1.0D;
                b[i] = 4.0D;
                c[i] = 1.0D;
                r[i] = 4.0D * src[i] + 2.0D * src[i + 1];
            }

            a[length - 1] = 2.0D;
            b[length - 1] = 7.0D;
            c[length - 1] = 0.0D;
            r[length - 1] = 8.0D * src[length - 1] + src[length];

            for (int i = 1; i < length; i++) {
                double p = a[i] / b[i - 1];
                b[i] -= p * c[i - 1];
                r[i] -= p * r[i - 1];
            }

            p1[length - 1] = r[length - 1] / b[length - 1];
            for (int i = length - 2; i >= 0; i--) {
                p1[i] = (r[i] - c[i] * p1[i + 1]) / b[i];
            }

            for (int i = 0; i < length - 1; i++) {
                p2[i] = 2.0D * src[i + 1] - p1[i + 1];
            }
            p2[length - 1] = 0.5D * (src[length] + p1[length - 1]);
            return new double[][] { p1, p2 };
        }

        static Road[] getSplines(String name, boolean displayOnly, RoadPoint[] waypoints) {
            if (waypoints.length == 2) {
                RoadPoint p1 = waypoints[0];
                RoadPoint p2 = waypoints[1];
                return new Road[] { createRoad(name, displayOnly, p1, p2, p1, p2) };
            }

            int length = waypoints.length;
            double[] x = new double[length];
            double[] z = new double[length];
            for (int i = 0; i < length; i++) {
                x[i] = waypoints[i].x;
                z[i] = waypoints[i].z;
            }
            double[][] controlX = getControlPoints(x);
            double[][] controlZ = getControlPoints(z);
            Road[] roads = new Road[length - 1];
            for (int i = 0; i < roads.length; i++) {
                RoadPoint p1 = waypoints[i];
                RoadPoint p2 = waypoints[i + 1];
                RoadPoint cp1 = new RoadPoint(controlX[0][i], controlZ[0][i]);
                RoadPoint cp2 = new RoadPoint(controlX[1][i], controlZ[1][i]);
                roads[i] = createRoad(name, displayOnly, p1, p2, cp1, cp2);
            }
            return roads;
        }

        private static Road createRoad(String name, boolean displayOnly, RoadPoint p1, RoadPoint p2, RoadPoint cp1, RoadPoint cp2) {
            double dx = p2.x - p1.x;
            double dz = p2.z - p1.z;
            int roadLength = (int) Math.round(Math.sqrt(dx * dx + dz * dz));
            int points = Math.max(2, roadLength / ROAD_POINT_SPACING);
            RoadPoint[] roadPoints = new RoadPoint[points];
            double minX = Math.min(p1.x, p2.x);
            double minZ = Math.min(p1.z, p2.z);
            double maxX = Math.max(p1.x, p2.x);
            double maxZ = Math.max(p1.z, p2.z);
            for (int i = 0; i < points; i++) {
                double t = (double) i / (points - 1);
                RoadPoint point = cp1 == p1 && cp2 == p2 ? lerp(p1, p2, t) : bezier(p1, cp1, cp2, p2, t);
                roadPoints[i] = point;
                minX = Math.min(minX, point.x);
                minZ = Math.min(minZ, point.z);
                maxX = Math.max(maxX, point.x);
                maxZ = Math.max(maxZ, point.z);
                if (!displayOnly) {
                    addPoint(point);
                }
            }
            return new Road(name, displayOnly, roadPoints, new RoadPoint[] { p1, p2 }, minX, minZ, maxX, maxZ);
        }

        private static RoadPoint lerp(RoadPoint a, RoadPoint b, double t) {
            return new RoadPoint(a.x + (b.x - a.x) * t, a.z + (b.z - a.z) * t);
        }
    }
}
