package org.neelemv.lotr_craft.faction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public enum LotrFaction {
    HOBBIT(0x59CE8E, FactionRegion.WEST, new FactionMapRegion(830, 745, 100), typeSet(FactionType.FREE, FactionType.MAN)),
    BREE(0xAD8F72, FactionRegion.WEST, new FactionMapRegion(925, 735, 50), typeSet(FactionType.FREE, FactionType.MAN)),
    RANGER_NORTH(0x3A5902, FactionRegion.WEST, new FactionMapRegion(1070, 760, 150), typeSet(FactionType.FREE, FactionType.MAN)),
    BLUE_MOUNTAINS(0x5D91CC, FactionRegion.WEST, new FactionMapRegion(650, 600, 125), typeSet(FactionType.FREE, FactionType.DWARF)),
    HIGH_ELF(0xC6E0FF, FactionRegion.WEST, new FactionMapRegion(570, 770, 200), typeSet(FactionType.FREE, FactionType.ELF)),
    GUNDABAD(0x966994, FactionRegion.WEST, new FactionMapRegion(1160, 670, 150), typeSet(FactionType.ORC)),
    ANGMAR(0x7799F7, FactionRegion.WEST, new FactionMapRegion(1080, 600, 125), typeSet(FactionType.ORC, FactionType.TROLL)),
    WOOD_ELF(0x39978E, FactionRegion.WEST, new FactionMapRegion(1400, 640, 75), typeSet(FactionType.FREE, FactionType.ELF)),
    DOL_GULDUR(0x353D04, FactionRegion.WEST, new FactionMapRegion(1380, 870, 100), typeSet(FactionType.ORC)),
    DALE(0xCE87DF, FactionRegion.WEST, new FactionMapRegion(1530, 670, 100), typeSet(FactionType.FREE, FactionType.MAN)),
    DURINS_FOLK(0x4B5E42, FactionRegion.WEST, new FactionMapRegion(1650, 650, 125), typeSet(FactionType.FREE, FactionType.DWARF)),
    LOTHLORIEN(0xEFC0D8, FactionRegion.WEST, new FactionMapRegion(1230, 900, 75), typeSet(FactionType.FREE, FactionType.ELF)),
    DUNLAND(0xA8978F, FactionRegion.WEST, new FactionMapRegion(1090, 1030, 125), typeSet(FactionType.MAN)),
    ISENGARD(0x333A33, FactionRegion.WEST, new FactionMapRegion(1110, 1070, 50), typeSet(FactionType.ORC)),
    FANGORN(0x49B912, FactionRegion.WEST, new FactionMapRegion(1200, 1000, 75), typeSet(FactionType.FREE, FactionType.TREE)),
    ROHAN(0x358727, FactionRegion.WEST, new FactionMapRegion(1230, 1090, 150), typeSet(FactionType.FREE, FactionType.MAN)),
    GONDOR(0xF9F8F9, FactionRegion.WEST, new FactionMapRegion(1170, 1300, 300), typeSet(FactionType.FREE, FactionType.MAN)),
    MORDOR(0x351F1F, FactionRegion.WEST, new FactionMapRegion(1620, 1290, 225), typeSet(FactionType.ORC)),
    DORWINION(0x6D3108, FactionRegion.EAST, new FactionMapRegion(1750, 900, 100), typeSet(FactionType.FREE, FactionType.MAN, FactionType.ELF)),
    RHUDEL(0xC49F27, FactionRegion.EAST, new FactionMapRegion(1890, 980, 200), typeSet(FactionType.MAN)),
    NEAR_HARAD(0xB51C9B, FactionRegion.SOUTH, new FactionMapRegion(1400, 1730, 375), typeSet(FactionType.MAN)),
    MORWAITH(0xD9B45A, FactionRegion.SOUTH, new FactionMapRegion(1400, 2360, 450), typeSet(FactionType.MAN)),
    TAURETHRIM(0x2E6402, FactionRegion.SOUTH, new FactionMapRegion(1250, 2870, 400), typeSet(FactionType.FREE, FactionType.MAN)),
    HALF_TROLL(0x9E8283, FactionRegion.SOUTH, new FactionMapRegion(1900, 2500, 200), typeSet(FactionType.MAN, FactionType.TROLL)),
    DARK_HUORN(0, null, null, false, true, -1, null, typeSet()),
    RUFFIAN(0, null, null, false, true, 0, null, typeSet()),
    UTUMNO(0x330640, FactionDimension.UTUMNO, FactionRegion.UTUMNO, true, true, -66666, null, typeSet(FactionType.ORC)),
    HOSTILE(0, null, null, false, true, -1, null, typeSet()),
    UNALIGNED(0, null, null, false, false, 0, null, typeSet());

    private final int color;
    private final FactionDimension dimension;
    private final FactionRegion region;
    private final boolean playerAllowed;
    private final boolean entityRegistryAllowed;
    private final Integer fixedAlignment;
    private final FactionMapRegion mapRegion;
    private final Set<FactionType> types;
    private final List<FactionRank> ranks = new ArrayList<>();
    private FactionRank pledgeRank;
    private String achievementCategory;
    private boolean isolationist;
    private boolean approvesWarCrimes = true;

    LotrFaction(int color, FactionRegion region, FactionMapRegion mapRegion, Set<FactionType> types) {
        this(color, FactionDimension.MIDDLE_EARTH, region, true, true, null, mapRegion, types);
    }

    LotrFaction(int color, FactionDimension dimension, FactionRegion region, boolean playerAllowed, boolean entityRegistryAllowed, Integer fixedAlignment, FactionMapRegion mapRegion, Set<FactionType> types) {
        this.color = color;
        this.dimension = dimension;
        this.region = region;
        this.playerAllowed = playerAllowed;
        this.entityRegistryAllowed = entityRegistryAllowed;
        this.fixedAlignment = fixedAlignment;
        this.mapRegion = mapRegion;
        this.types = Collections.unmodifiableSet(types.isEmpty() ? EnumSet.noneOf(FactionType.class) : EnumSet.copyOf(types));
    }

    static {
        HOBBIT.flags("SHIRE", true, false).rank(10, "guest").pledgeRank(100, "friend").rank(250, "hayward").rank(500, "bounder").rank(1000, "shirriff").rank(2000, "chief").rank(3000, "thain");
        BREE.flags("BREE_LAND", false, false).rank(10, "guest").rank(50, "friend").pledgeRank(100, "townsman").rank(200, "trustee").rank(500, "champion").rank(1000, "captain").rank(2000, "master");
        RANGER_NORTH.flags("ERIADOR", false, false).rank(10, "friend").rank(50, "warden").pledgeRank(100, "ranger").rank(200, "ohtar").rank(500, "roquen").rank(1000, "champion").rank(2000, "captain");
        BLUE_MOUNTAINS.flags("BLUE_MOUNTAINS", false, false).rank(10, "guest").rank(50, "friend").pledgeRank(100, "warden").rank(200, "axebearer").rank(500, "champion").rank(1000, "captain").rank(1500, "noble").rank(3000, "lord", true);
        HIGH_ELF.flags("LINDON", false, false).rank(10, "guest").rank(50, "friend").pledgeRank(100, "warrior").rank(200, "herald").rank(500, "captain").rank(1000, "noble").rank(2000, "commander").rank(3000, "lord", true);
        GUNDABAD.flags("ERIADOR", false, true).rank(10, "thrall").rank(50, "snaga").pledgeRank(100, "raider").rank(200, "ravager").rank(500, "scourge").rank(1000, "warlord").rank(2000, "chieftain");
        ANGMAR.flags("ANGMAR", false, true).rank(10, "thrall").rank(50, "servant").pledgeRank(100, "kinsman").rank(200, "warrior").rank(500, "champion").rank(1000, "warlord").rank(2000, "chieftain");
        WOOD_ELF.flags("MIRKWOOD", false, false).rank(50, "guest").pledgeRank(100, "friend").rank(200, "guard").rank(500, "herald").rank(1000, "captain").rank(2000, "noble").rank(3000, "lord", true);
        DOL_GULDUR.flags("MIRKWOOD", false, true).rank(10, "thrall").rank(50, "servant").pledgeRank(100, "brigand").rank(200, "torchbearer").rank(500, "despoiler").rank(1000, "captain").rank(2000, "lieutenant");
        DALE.flags("DALE", false, false).rank(10, "guest").rank(50, "friend").pledgeRank(100, "soldier").rank(200, "herald").rank(500, "captain").rank(1000, "marshal").rank(2000, "lord", true);
        DURINS_FOLK.flags("IRON_HILLS", false, false).rank(10, "guest").rank(50, "friend").pledgeRank(100, "oathfriend").rank(200, "axebearer").rank(500, "champion").rank(1000, "commander").rank(1500, "lord", true).rank(3000, "uzbad", true);
        LOTHLORIEN.flags("LOTHLORIEN", false, false).rank(10, "guest").rank(50, "friend").pledgeRank(100, "warden").rank(200, "warrior").rank(500, "herald", true).rank(1000, "captain").rank(2000, "noble").rank(3000, "lord", true);
        DUNLAND.flags("DUNLAND", false, true).rank(10, "guest").rank(50, "kinsman").pledgeRank(100, "warrior").rank(200, "bearer").rank(500, "avenger").rank(1000, "warlord").rank(2000, "chieftain");
        ISENGARD.flags("ROHAN", false, true).rank(10, "thrall").rank(50, "snaga").pledgeRank(100, "soldier").rank(200, "treefeller").rank(500, "berserker").rank(1000, "corporal").rank(1500, "hand").rank(3000, "captain");
        FANGORN.flags("FANGORN", true, false).rank(10, "newcomer").rank(50, "friend").pledgeRank(100, "treeherd").rank(250, "master").rank(500, "elder");
        ROHAN.flags("ROHAN", false, false).rank(10, "guest").rank(50, "footman").pledgeRank(100, "atarms").rank(250, "rider").rank(500, "esquire").rank(1000, "captain").rank(2000, "marshal");
        GONDOR.flags("GONDOR", false, false).rank(10, "guest").rank(50, "friend").pledgeRank(100, "atarms").rank(200, "soldier").rank(500, "knight").rank(1000, "champion").rank(1500, "captain").rank(3000, "lord", true);
        MORDOR.flags("MORDOR", false, true).rank(10, "thrall").rank(50, "snaga").pledgeRank(100, "brigand").rank(200, "slavedriver").rank(500, "despoiler").rank(1000, "captain").rank(1500, "lieutenant").rank(3000, "commander");
        DORWINION.flags("DORWINION", false, false).rank(10, "guest").rank(50, "vinehand").pledgeRank(100, "merchant").rank(200, "guard").rank(500, "captain").rank(1000, "master").rank(1500, "chief").rank(3000, "lord", true);
        RHUDEL.flags("RHUN", false, false).rank(10, "bondsman").rank(50, "levyman").pledgeRank(100, "clansman").rank(200, "warrior").rank(500, "champion").rank(1000, "golden").rank(1500, "warlord").rank(3000, "chieftain");
        NEAR_HARAD.flags("NEAR_HARAD", false, false).rank(10, "guest").rank(50, "friend").pledgeRank(100, "kinsman").rank(200, "warrior").rank(500, "champion").rank(1000, "serpentguard").rank(1500, "warlord").rank(3000, "prince", true);
        MORWAITH.flags("FAR_HARAD_SAVANNAH", false, true).rank(10, "guest").rank(50, "friend").pledgeRank(100, "kinsman").rank(250, "hunter").rank(500, "warrior").rank(1000, "chief").rank(3000, "greatchief");
        TAURETHRIM.flags("FAR_HARAD_JUNGLE", false, true).rank(10, "guest").rank(50, "friend").pledgeRank(100, "forestman").rank(200, "warrior").rank(500, "champion").rank(1000, "warlord").rank(3000, "splendour");
        HALF_TROLL.flags("PERDOROGWAITH", false, true).rank(10, "guest").rank(50, "scavenger").pledgeRank(100, "kin").rank(200, "warrior").rank(500, "raider").rank(1000, "warlord").rank(2000, "chieftain");
        UTUMNO.flags("UTUMNO", false, true);
    }

    public static LotrFaction forCodeName(String codeName) {
        for (LotrFaction faction : values()) {
            if (faction.codeName().equals(codeName)) {
                return faction;
            }
        }
        return null;
    }

    public String codeName() {
        return name();
    }

    public String translationKey() {
        return "faction.lotr_craft." + name().toLowerCase(Locale.ROOT);
    }

    public int color() {
        return color;
    }

    public FactionDimension dimension() {
        return dimension;
    }

    public FactionRegion region() {
        return region;
    }

    public boolean playerAllowed() {
        return playerAllowed;
    }

    public boolean entityRegistryAllowed() {
        return entityRegistryAllowed;
    }

    public Integer fixedAlignment() {
        return fixedAlignment;
    }

    public boolean hasFixedAlignment() {
        return fixedAlignment != null;
    }

    public FactionMapRegion mapRegion() {
        return mapRegion;
    }

    public Set<FactionType> types() {
        return types;
    }

    public List<FactionRank> ranks() {
        return Collections.unmodifiableList(ranks);
    }

    public FactionRank pledgeRank() {
        return pledgeRank;
    }

    public String achievementCategory() {
        return achievementCategory;
    }

    public boolean isolationist() {
        return isolationist;
    }

    public boolean approvesWarCrimes() {
        return approvesWarCrimes;
    }

    public boolean isOfType(FactionType type) {
        return types.contains(type);
    }

    public boolean isPlayableAlignmentFaction() {
        return playerAllowed && !hasFixedAlignment();
    }

    private LotrFaction flags(String achievementCategory, boolean isolationist, boolean approvesWarCrimes) {
        this.achievementCategory = achievementCategory;
        this.isolationist = isolationist;
        this.approvesWarCrimes = approvesWarCrimes;
        return this;
    }

    private LotrFaction rank(float alignment, String name) {
        return rank(alignment, name, false);
    }

    private LotrFaction rank(float alignment, String name, boolean gendered) {
        ranks.add(new FactionRank(alignment, name, gendered, false));
        Collections.sort(ranks);
        return this;
    }

    private LotrFaction pledgeRank(float alignment, String name) {
        return pledgeRank(alignment, name, false);
    }

    private LotrFaction pledgeRank(float alignment, String name, boolean gendered) {
        FactionRank rank = new FactionRank(alignment, name, gendered, true);
        pledgeRank = rank;
        ranks.add(rank);
        Collections.sort(ranks);
        return this;
    }

    private static Set<FactionType> typeSet(FactionType... types) {
        if (types.length == 0) {
            return EnumSet.noneOf(FactionType.class);
        }
        EnumSet<FactionType> set = EnumSet.noneOf(FactionType.class);
        Collections.addAll(set, types);
        return set;
    }
}
