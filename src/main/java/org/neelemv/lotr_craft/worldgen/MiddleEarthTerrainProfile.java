package org.neelemv.lotr_craft.worldgen;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.neelemv.lotr_craft.block.LotrBlocks;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

enum MiddleEarthTerrainProfile {
    RIVER("river", 0x367CB5, 0.80F, 0.80F, 46, 1, 4, Surface.GRASS, true),
    ROHAN("rohan", 0x70AD45, 0.80F, 0.80F, 68, 4, 6, Surface.ROHAN_GRASS, false),
    MISTY_MOUNTAINS("mistyMountains", 0xE8E7E1, 0.20F, 0.50F, 126, 48, 24, Surface.SNOW_STONE, false),
    SHIRE("shire", 0x67AD35, 0.80F, 0.90F, 67, 7, 7, Surface.GRASS, false),
    SHIRE_WOODLANDS("shireWoodlands", 0x447736, 0.80F, 0.90F, 72, 12, 9, Surface.PODZOL, false),
    MORDOR("mordor", 0x11100E, 2.00F, 0.00F, 72, 12, 9, Surface.MORDOR_ROCK, false),
    MORDOR_MOUNTAINS("mordorMountains", 0x514D48, 2.00F, 0.00F, 126, 72, 32, Surface.MORDOR_ROCK, false),
    GONDOR("gondor", 0x88B445, 0.80F, 0.80F, 65, 4, 6, Surface.GONDOR_GRASS, false),
    WHITE_MOUNTAINS("whiteMountains", 0xE5E5E8, 0.60F, 0.80F, 110, 48, 24, Surface.SNOW_STONE, false),
    LOTHLORIEN("lothlorien", 0xFBD83F, 0.90F, 1.00F, 65, 7, 7, Surface.PODZOL, false),
    CELEBRANT("celebrant", 0x74AF46, 1.10F, 1.10F, 65, 1, 4, Surface.GRASS, false),
    IRON_HILLS("ironHills", 0x8B7F4D, 0.27F, 0.40F, 72, 34, 18, Surface.GRASS, false),
    DEAD_MARSHES("deadMarshes", 0x6F733F, 0.40F, 1.00F, 62, 2, 5, Surface.GRASS, true),
    TROLLSHAWS("trollshaws", 0x587C2F, 0.60F, 0.80F, 67, 24, 14, Surface.PODZOL, false),
    WOODLAND_REALM("woodlandRealm", 0x3E6526, 0.80F, 0.90F, 68, 7, 7, Surface.PODZOL, false),
    MIRKWOOD_CORRUPTED("mirkwoodCorrupted", 0x2E441B, 0.60F, 0.80F, 68, 10, 8, Surface.PODZOL, false),
    ROHAN_URUK_HIGHLANDS("rohanUrukHighlands", 0x7E935A, 0.70F, 0.40F, 88, 7, 7, Surface.GRASS, false),
    EMYN_MUIL("emynMuil", 0x968C72, 0.50F, 0.90F, 68, 19, 12, Surface.GRASS, false),
    ITHILIEN("ithilien", 0x75A734, 0.90F, 0.90F, 67, 12, 9, Surface.GONDOR_GRASS, false),
    PELARGIR("pelargir", 0xABC151, 1.00F, 1.00F, 65, 5, 6, Surface.GRASS, false),
    LONE_LANDS("loneLands", 0x82A84A, 0.60F, 0.50F, 67, 10, 8, Surface.GRASS, false),
    LONE_LANDS_HILLS("loneLandsHills", 0x848E4E, 0.60F, 0.50F, 81, 19, 12, Surface.GRASS, false),
    DUNLAND("dunland", 0x69994C, 0.40F, 0.70F, 72, 12, 9, Surface.GRASS, false),
    FANGORN("fangorn", 0x427519, 0.70F, 0.80F, 68, 10, 8, Surface.PODZOL, false),
    ANGLE("angle", 0x8FAF4F, 0.60F, 0.80F, 67, 7, 7, Surface.GRASS, false),
    ETTENMOORS("ettenmoors", 0x7C895A, 0.20F, 0.60F, 78, 14, 10, Surface.GRASS, false),
    OLD_FOREST("oldForest", 0x45753B, 0.50F, 1.00F, 68, 7, 7, Surface.PODZOL, false),
    HARONDOR("harondor", 0xA2B546, 1.00F, 0.60F, 68, 7, 7, Surface.GRASS, false),
    ERIADOR("eriador", 0x6BA644, 0.90F, 0.80F, 65, 10, 8, Surface.GRASS, false),
    ERIADOR_DOWNS("eriadorDowns", 0x748C47, 0.60F, 0.70F, 78, 12, 9, Surface.GRASS, false),
    ERYN_VORN("erynVorn", 0x427F4D, 0.80F, 0.90F, 65, 10, 8, Surface.PODZOL, false),
    GREY_MOUNTAINS("greyMountains", 0xCACCC1, 0.28F, 0.20F, 120, 48, 24, Surface.SNOW_STONE, false),
    MIDGEWATER("midgewater", 0x5B9357, 0.60F, 1.00F, 62, 2, 5, Surface.GRASS, true),
    BROWN_LANDS("brownLands", 0x827E50, 1.00F, 0.20F, 68, 5, 6, Surface.COARSE_DIRT, false),
    OCEAN("ocean", 0x02598D, 0.80F, 0.80F, 30, 7, 7, Surface.GRASS, true),
    ANDUIN_HILLS("anduinHills", 0x6BB25C, 0.70F, 0.70F, 81, 10, 8, Surface.GRASS, false),
    MENELTARMA("meneltarma", 0x91B75A, 0.90F, 0.80F, 65, 5, 6, Surface.STONE, false),
    GLADDEN_FIELDS("gladdenFields", 0x4C9B59, 0.60F, 1.20F, 62, 2, 5, Surface.GRASS, true),
    LOTHLORIEN_EDGE("lothlorienEdge", 0xD4C643, 0.90F, 1.00F, 65, 5, 6, Surface.PODZOL, false),
    FORODWAITH("forodwaith", 0xD8D8D2, 0.00F, 0.20F, 65, 2, 5, Surface.SNOW_GRASS, false),
    ENEDWAITH("enedwaith", 0x7AA84F, 0.60F, 0.80F, 68, 7, 7, Surface.GRASS, false),
    ANGMAR("angmar", 0x54472F, 0.20F, 0.20F, 68, 14, 10, Surface.GRASS, false),
    EREGION("eregion", 0x659048, 0.60F, 0.70F, 68, 7, 7, Surface.GRASS, false),
    LINDON("lindon", 0x74AD45, 0.90F, 0.90F, 67, 5, 6, Surface.GRASS, false),
    LINDON_WOODLANDS("lindonWoodlands", 0x1E772F, 0.90F, 1.00F, 68, 12, 9, Surface.PODZOL, false),
    EAST_BIGHT("eastBight", 0x8A955D, 0.80F, 0.30F, 67, 1, 4, Surface.GRASS, false),
    BLUE_MOUNTAINS("blueMountains", 0xC9DAE2, 0.22F, 0.80F, 94, 60, 29, Surface.SNOW_STONE, false),
    MIRKWOOD_MOUNTAINS("mirkwoodMountains", 0x282D1D, 0.28F, 0.90F, 100, 36, 19, Surface.SNOW_STONE, false),
    WILDERLAND("wilderland", 0x92AC50, 0.90F, 0.40F, 68, 10, 8, Surface.GRASS, false),
    DAGORLAD("dagorlad", 0x6B5F45, 1.00F, 0.20F, 65, 1, 4, Surface.MORDOR_DIRT, false),
    NURN("nurn", 0x28241B, 0.90F, 0.40F, 65, 5, 6, Surface.GRASS, false),
    NURNEN("nurnen", 0x0E3656, 0.90F, 0.40F, 30, 7, 7, Surface.GRASS, true),
    NURN_MARSHES("nurnMarshes", 0x3D3B2B, 0.90F, 0.40F, 62, 2, 5, Surface.GRASS, true),
    ADORNLAND("adornland", 0x779B4F, 0.70F, 0.60F, 68, 5, 6, Surface.ROHAN_GRASS, false),
    ANGMAR_MOUNTAINS("angmarMountains", 0xCFCFCB, 0.25F, 0.10F, 113, 36, 19, Surface.SNOW_STONE, false),
    ANDUIN_MOUTH("anduinMouth", 0x4DA853, 0.90F, 1.00F, 62, 2, 5, Surface.GRASS, true),
    ENTWASH_MOUTH("entwashMouth", 0x55A346, 0.50F, 1.00F, 62, 2, 5, Surface.GRASS, true),
    DOR_EN_ERNIL("dorEnErnil", 0x8EBF45, 0.90F, 0.90F, 64, 5, 6, Surface.GONDOR_GRASS, false),
    DOR_EN_ERNIL_HILLS("dorEnErnilHills", 0x82A043, 0.80F, 0.70F, 78, 12, 9, Surface.GONDOR_GRASS, false),
    FANGORN_WASTELAND("fangornWasteland", 0x677C4C, 0.70F, 0.40F, 68, 10, 8, Surface.COARSE_DIRT, false),
    ROHAN_WOODLANDS("rohanWoodlands", 0x578736, 0.90F, 0.90F, 68, 10, 8, Surface.ROHAN_PODZOL, false),
    GONDOR_WOODLANDS("gondorWoodlands", 0x59872B, 0.80F, 0.90F, 68, 5, 6, Surface.GONDOR_PODZOL, false),
    LAKE("lake", 0x34649E, 0.80F, 0.80F, 30, 7, 7, Surface.GRASS, true),
    LINDON_COAST("lindonCoast", 0x8D9596, 0.90F, 0.90F, 62, 12, 9, Surface.GRASS, false),
    BARROW_DOWNS("barrowDowns", 0x7B8E52, 0.60F, 0.70F, 72, 10, 8, Surface.GRASS, false),
    LONG_MARSHES("longMarshes", 0x6D8746, 0.60F, 0.90F, 62, 2, 5, Surface.GRASS, true),
    FANGORN_CLEARING("fangornClearing", 0x59AD3A, 0.70F, 0.80F, 68, 2, 5, Surface.PODZOL, false),
    ITHILIEN_HILLS("ithilienHills", 0x6A9840, 0.70F, 0.70F, 81, 14, 10, Surface.GONDOR_GRASS, false),
    ITHILIEN_WASTELAND("ithilienWasteland", 0x7A874F, 0.60F, 0.60F, 67, 5, 6, Surface.COARSE_DIRT, false),
    NINDALF("nindalf", 0x6C8446, 0.40F, 1.00F, 62, 2, 5, Surface.GRASS, true),
    COLDFELLS("coldfells", 0x7E9652, 0.25F, 0.80F, 75, 19, 12, Surface.SNOW_GRASS, false),
    NAN_CURUNIR("nanCurunir", 0x6C7C52, 0.60F, 0.40F, 68, 2, 5, Surface.GRASS, false),
    WHITE_DOWNS("whiteDowns", 0x9BCE79, 0.60F, 0.70F, 81, 14, 10, Surface.CHALK_GRASS, false),
    SWANFLEET("swanfleet", 0x5F9C59, 0.80F, 1.00F, 62, 2, 5, Surface.GRASS, true),
    PELENNOR("pelennor", 0xABCC4B, 0.90F, 0.90F, 65, 1, 4, Surface.GRASS, false),
    MINHIRIATH("minhiriath", 0x709E46, 0.70F, 0.40F, 65, 5, 6, Surface.GRASS, false),
    EREBOR("erebor", 0x726D55, 0.60F, 0.70F, 75, 14, 10, Surface.GRASS, false),
    MIRKWOOD_NORTH("mirkwoodNorth", 0x3A5223, 0.70F, 0.70F, 68, 10, 8, Surface.PODZOL, false),
    WOODLAND_REALM_HILLS("woodlandRealmHills", 0x37501F, 0.80F, 0.60F, 91, 17, 11, Surface.PODZOL, false),
    NAN_UNGOL("nanUngol", 0x0A0501, 2.00F, 0.00F, 65, 10, 8, Surface.MORDOR_ROCK, false),
    PINNATH_GELIN("pinnathGelin", 0x97C645, 0.80F, 0.80F, 78, 12, 9, Surface.GRASS, false),
    ISLAND("island", 0x9AB553, 0.90F, 0.80F, 62, 7, 7, Surface.GRASS, false),
    FORODWAITH_MOUNTAINS("forodwaithMountains", 0xEDEDEE, 0.00F, 0.20F, 126, 48, 24, Surface.SNOW_STONE, false),
    MISTY_MOUNTAINS_FOOTHILLS("mistyMountainsFoothills", 0xBEC1B6, 0.25F, 0.60F, 84, 22, 13, Surface.SNOW_STONE, false),
    GREY_MOUNTAINS_FOOTHILLS("greyMountainsFoothills", 0x8B9660, 0.50F, 0.70F, 78, 22, 13, Surface.SNOW_STONE, false),
    BLUE_MOUNTAINS_FOOTHILLS("blueMountainsFoothills", 0xABB5B2, 0.50F, 0.80F, 78, 22, 13, Surface.STONE, false),
    TUNDRA("tundra", 0xBCB296, 0.10F, 0.30F, 65, 5, 6, Surface.SNOW_GRASS, false),
    TAIGA("taiga", 0x63964F, 0.10F, 0.70F, 65, 12, 9, Surface.PODZOL, false),
    BREELAND("breeland", 0x68B339, 0.80F, 0.70F, 65, 5, 6, Surface.GRASS, false),
    CHETWOOD("chetwood", 0x43831D, 0.80F, 0.90F, 68, 10, 8, Surface.PODZOL, false),
    FORODWAITH_GLACIER("forodwaithGlacier", 0x8FCCE0, 0.00F, 0.10F, 94, 2, 5, Surface.SNOW_STONE, false),
    WHITE_MOUNTAINS_FOOTHILLS("whiteMountainsFoothills", 0xC0CDB7, 0.60F, 0.70F, 78, 22, 13, Surface.SNOW_STONE, false),
    BEACH("beach", 0xDBCA97, 0.80F, 0.80F, 65, 5, 6, Surface.SAND, false),
    BEACH_GRAVEL("beachGravel", 0x9695A0, 0.80F, 0.80F, 65, 5, 6, Surface.GRAVEL, false),
    NEAR_HARAD("nearHarad", 0xD8C377, 1.50F, 0.10F, 68, 2, 5, Surface.SAND, false),
    FAR_HARAD("farHarad", 0x94A041, 1.20F, 0.20F, 65, 2, 5, Surface.SAND, false),
    HARAD_MOUNTAINS("haradMountains", 0x969075, 0.90F, 0.50F, 120, 48, 24, Surface.SAND, false),
    UMBAR("umbar", 0x919C54, 0.90F, 0.60F, 65, 5, 6, Surface.GRASS, false),
    FAR_HARAD_JUNGLE("farHaradJungle", 0x4B7423, 1.20F, 0.90F, 68, 10, 8, Surface.MUD_GRASS, false),
    UMBAR_HILLS("umbarHills", 0x7D864A, 0.80F, 0.50F, 100, 19, 12, Surface.GRASS, false),
    NEAR_HARAD_HILLS("nearHaradHills", 0xB9A762, 1.20F, 0.30F, 78, 19, 12, Surface.SAND, false),
    FAR_HARAD_JUNGLE_LAKE("farHaradJungleLake", 0x22AACC, 1.20F, 0.90F, 46, 5, 6, Surface.SAND, true),
    LOSTLADEN("lostladen", 0xA2A365, 1.20F, 0.20F, 68, 2, 5, Surface.SAND, false),
    FAR_HARAD_FOREST("farHaradForest", 0x38821D, 1.00F, 1.00F, 72, 10, 8, Surface.SAND, false),
    NEAR_HARAD_FERTILE("nearHaradFertile", 0x9EAA4E, 1.20F, 0.70F, 68, 2, 5, Surface.SAND, false),
    PERTOROGWAITH("pertorogwaith", 0x877E5A, 0.70F, 0.10F, 68, 12, 9, Surface.SAND, false),
    UMBAR_FOREST("umbarForest", 0x6D873A, 0.80F, 0.80F, 68, 7, 7, Surface.PODZOL, false),
    FAR_HARAD_JUNGLE_EDGE("farHaradJungleEdge", 0x71882E, 1.20F, 0.80F, 68, 5, 6, Surface.MUD_GRASS, false),
    TAUREDAIN_CLEARING("tauredainClearing", 0xA4BC45, 1.20F, 0.80F, 68, 5, 6, Surface.GRASS, false),
    GULF_HARAD("gulfHarad", 0x8BA850, 1.00F, 0.50F, 67, 2, 5, Surface.SAND, false),
    DORWINION_HILLS("dorwinionHills", 0xCBD3A9, 0.90F, 0.80F, 88, 19, 12, Surface.CHALK_GRASS, false),
    TOLFALAS("tolfalas", 0x9BA06D, 0.80F, 0.40F, 72, 24, 14, Surface.GRASS, false),
    LEBENNIN("lebennin", 0x77B62A, 1.00F, 0.90F, 65, 7, 7, Surface.GRASS, false),
    RHUN("rhun", 0x9FB258, 0.90F, 0.30F, 72, 1, 4, Surface.GRASS, false),
    RHUN_FOREST("rhunForest", 0x72873B, 0.80F, 0.90F, 72, 12, 9, Surface.PODZOL, false),
    RED_MOUNTAINS("redMountains", 0x93714C, 0.30F, 0.40F, 110, 48, 24, Surface.SNOW_STONE, false),
    RED_MOUNTAINS_FOOTHILLS("redMountainsFoothills", 0x999452, 0.70F, 0.40F, 78, 22, 13, Surface.STONE, false),
    DOL_GULDUR("dolGuldur", 0x242F0F, 0.60F, 0.80F, 68, 12, 9, Surface.MORDOR_ROCK, false),
    NEAR_HARAD_SEMI_DESERT("nearHaradSemiDesert", 0xBDBB6A, 1.50F, 0.20F, 68, 2, 5, Surface.SAND, false),
    FAR_HARAD_ARID("farHaradArid", 0xAAAE55, 1.50F, 0.30F, 68, 4, 6, Surface.SAND, false),
    FAR_HARAD_ARID_HILLS("farHaradAridHills", 0x998D5B, 1.50F, 0.30F, 94, 14, 10, Surface.SAND, false),
    FAR_HARAD_SWAMP("farHaradSwamp", 0x55934B, 0.80F, 1.00F, 62, 2, 5, Surface.SAND, false),
    FAR_HARAD_CLOUD_FOREST("farHaradCloudForest", 0x2E7B40, 1.20F, 1.20F, 84, 10, 8, Surface.SAND, false),
    FAR_HARAD_BUSHLAND("farHaradBushland", 0x99913E, 1.00F, 0.40F, 68, 2, 5, Surface.SAND, false),
    FAR_HARAD_BUSHLAND_HILLS("farHaradBushlandHills", 0x7F7934, 0.80F, 0.40F, 88, 19, 12, Surface.SAND, false),
    FAR_HARAD_MANGROVE("farHaradMangrove", 0x878E4D, 1.00F, 0.90F, 60, 1, 4, Surface.SAND, true),
    NEAR_HARAD_FERTILE_FOREST("nearHaradFertileForest", 0x698432, 1.20F, 1.00F, 68, 10, 8, Surface.SAND, false),
    ANDUIN_VALE("anduinVale", 0x71A548, 0.90F, 1.10F, 64, 1, 4, Surface.GRASS, false),
    WOLD("wold", 0x90B54F, 0.90F, 0.10F, 75, 7, 7, Surface.GRASS, false),
    SHIRE_MOORS("shireMoors", 0x699B4C, 0.60F, 1.60F, 75, 14, 10, Surface.GRASS, false),
    SHIRE_MARSHES("shireMarshes", 0x3DA05F, 0.80F, 1.20F, 62, 2, 5, Surface.GRASS, true),
    NEAR_HARAD_RED_DESERT("nearHaradRedDesert", 0xC9934F, 1.50F, 0.10F, 68, 1, 4, Surface.RED_SAND, false),
    FAR_HARAD_VOLCANO("farHaradVolcano", 0x685734, 1.50F, 0.00F, 81, 29, 16, Surface.SAND, false),
    UDUN("udun", 0x010000, 1.50F, 0.00F, 68, 17, 11, Surface.MORDOR_ROCK, false),
    GORGOROTH("gorgoroth", 0x211D1D, 2.00F, 0.00F, 81, 5, 6, Surface.MORDOR_ROCK, false),
    MORGUL_VALE("morgulVale", 0x152D19, 1.00F, 0.00F, 68, 2, 5, Surface.MORDOR_DIRT, false),
    EASTERN_DESOLATION("easternDesolation", 0x5C5C47, 1.00F, 0.30F, 68, 5, 6, Surface.MORDOR_DIRT, false),
    DALE("dale", 0x7DA34F, 0.80F, 0.70F, 65, 5, 6, Surface.GRASS, false),
    DORWINION("dorwinion", 0x6CA545, 0.90F, 0.90F, 65, 7, 7, Surface.GRASS, false),
    TOWER_HILLS("towerHills", 0x689641, 0.80F, 0.80F, 78, 12, 9, Surface.GRASS, false),
    GULF_HARAD_FOREST("gulfHaradForest", 0x598C2E, 1.00F, 1.00F, 68, 10, 8, Surface.SAND, false),
    WILDERLAND_NORTH("wilderlandNorth", 0x93A66C, 0.60F, 0.60F, 68, 12, 9, Surface.GRASS, false),
    FORODWAITH_COAST("forodwaithCoast", 0x8C9AAD, 0.00F, 0.40F, 62, 12, 9, Surface.SNOW_GRASS, false),
    FAR_HARAD_COAST("farHaradCoast", 0x7F8278, 1.20F, 0.80F, 62, 12, 9, Surface.SAND, false),
    NEAR_HARAD_RIVERBANK("nearHaradRiverbank", 0x6D9E50, 1.20F, 0.80F, 65, 2, 5, Surface.SAND, true),
    LOSSARNACH("lossarnach", 0x80C52E, 1.00F, 1.00F, 65, 5, 6, Surface.GRASS, false),
    IMLOTH_MELUI("imlothMelui", 0xDD8568, 1.00F, 1.20F, 65, 5, 6, Surface.GRASS, false),
    NEAR_HARAD_OASIS("nearHaradOasis", 0x0CB500, 1.20F, 0.80F, 65, 2, 5, Surface.SAND, false),
    BEACH_WHITE("beachWhite", 0xEDEDED, 0.80F, 0.80F, 65, 5, 6, Surface.WHITE_SAND, false),
    HARNEDOR("harnedor", 0xAEB355, 1.00F, 0.30F, 65, 7, 7, Surface.GRASS, false),
    LAMEDON("lamedon", 0xA6BD64, 0.90F, 0.50F, 68, 5, 6, Surface.GRASS, false),
    LAMEDON_HILLS("lamedonHills", 0xCED6A9, 0.60F, 0.40F, 81, 22, 13, Surface.GRASS, false),
    BLACKROOT_VALE("blackrootVale", 0x6D9E31, 0.80F, 0.90F, 68, 3, 5, Surface.GRASS, false),
    ANDRAST("andrast", 0x879660, 0.80F, 0.80F, 68, 5, 6, Surface.GRASS, false),
    PUKEL("pukel", 0x567A42, 0.70F, 0.70F, 68, 10, 8, Surface.GRASS, false),
    RHUN_LAND("rhunLand", 0xADAB4F, 1.00F, 0.80F, 65, 7, 7, Surface.GRASS, false),
    RHUN_LAND_STEPPE("rhunLandSteppe", 0xB2B762, 1.00F, 0.30F, 68, 1, 4, Surface.GRASS, false),
    RHUN_LAND_HILLS("rhunLandHills", 0x8E8D4E, 1.00F, 0.50F, 81, 19, 12, Surface.GRASS, false),
    RHUN_RED_FOREST("rhunRedForest", 0x916C3E, 0.90F, 1.00F, 65, 7, 7, Surface.PODZOL, false),
    RHUN_ISLAND("rhunIsland", 0xA5B157, 1.00F, 0.80F, 65, 10, 8, Surface.GRASS, false),
    RHUN_ISLAND_FOREST("rhunIslandForest", 0x91793E, 0.90F, 1.00F, 65, 10, 8, Surface.PODZOL, false),
    LAST_DESERT("lastDesert", 0xD3C387, 0.70F, 0.00F, 68, 1, 4, Surface.SAND, false),
    WIND_MOUNTAINS("windMountains", 0xD3D3D3, 0.28F, 0.20F, 126, 48, 24, Surface.SNOW_STONE, false),
    WIND_MOUNTAINS_FOOTHILLS("windMountainsFoothills", 0x9A9F6A, 0.40F, 0.60F, 78, 14, 10, Surface.SNOW_STONE, false),
    RIVENDELL("rivendell", 0x86B72A, 0.90F, 1.00F, 67, 7, 7, Surface.GRASS, false),
    RIVENDELL_HILLS("rivendellHills", 0xD8D5B1, 0.70F, 0.80F, 126, 12, 9, Surface.CHALK_GRASS, false),
    FAR_HARAD_JUNGLE_MOUNTAINS("farHaradJungleMountains", 0x635A46, 1.00F, 1.00F, 120, 36, 19, Surface.MUD_GRASS, false),
    HALF_TROLL_FOREST("halfTrollForest", 0x5B7034, 0.80F, 0.20F, 72, 10, 8, Surface.PODZOL, false),
    FAR_HARAD_KANUKA("farHaradKanuka", 0x4E7818, 1.00F, 1.00F, 72, 12, 9, Surface.SAND, false),
    UTUMNO("utumno", 0x000000, 2.00F, 0.00F, 62, 1, 4, Surface.GRASS, false);

    private static final MiddleEarthTerrainProfile[] BY_ID = values();
    private static final Map<Integer, MiddleEarthTerrainProfile> BY_MAP_COLOR = buildColorMap();

    final String biomeName;
    private final int mapColor;
    final float temperature;
    final float rainfall;
    final int baseHeight;
    final int variation;
    final int roughness;
    private final Surface surface;
    final boolean water;

    MiddleEarthTerrainProfile(String biomeName, int mapColor, float temperature, float rainfall, int baseHeight, int variation, int roughness, Surface surface, boolean water) {
        this.biomeName = biomeName;
        this.mapColor = mapColor;
        this.temperature = temperature;
        this.rainfall = rainfall;
        this.baseHeight = baseHeight;
        this.variation = variation;
        this.roughness = roughness;
        this.surface = surface;
        this.water = water;
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

    String debugName() {
        return biomeName.toLowerCase(Locale.ROOT);
    }

    static int count() {
        return BY_ID.length;
    }

    static MiddleEarthTerrainProfile fromId(int id) {
        if (id < 0 || id >= BY_ID.length) {
            return OCEAN;
        }
        return BY_ID[id];
    }

    static MiddleEarthTerrainProfile fromMapColor(int argb) {
        MiddleEarthTerrainProfile profile = BY_MAP_COLOR.get(argb | 0xFF000000);
        return profile == null ? OCEAN : profile;
    }

    static int colorForId(int id) {
        return fromId(id).mapColor();
    }

    int mountainPeakHeight() {
        return switch (this) {
            case MISTY_MOUNTAINS -> MiddleEarthMapConstants.WORLD_MAX_Y - 1;
            case MISTY_MOUNTAINS_FOOTHILLS -> 640;
            case WHITE_MOUNTAINS -> 1650;
            case WHITE_MOUNTAINS_FOOTHILLS -> 620;
            case GREY_MOUNTAINS, FORODWAITH_MOUNTAINS -> 1550;
            case GREY_MOUNTAINS_FOOTHILLS, BLUE_MOUNTAINS_FOOTHILLS, RED_MOUNTAINS_FOOTHILLS, WIND_MOUNTAINS_FOOTHILLS -> 560;
            case BLUE_MOUNTAINS -> 1350;
            case RED_MOUNTAINS, WIND_MOUNTAINS -> 1280;
            case MORDOR_MOUNTAINS -> 1500;
            case ANGMAR_MOUNTAINS -> 1200;
            case HARAD_MOUNTAINS, FAR_HARAD_JUNGLE_MOUNTAINS -> 1100;
            case MIRKWOOD_MOUNTAINS -> 950;
            case RIVENDELL_HILLS -> 900;
            case IRON_HILLS, LONE_LANDS_HILLS, EREBOR, PINNATH_GELIN, TOLFALAS, RHUN_LAND_HILLS, FAR_HARAD_ARID_HILLS, FAR_HARAD_BUSHLAND_HILLS, UMBAR_HILLS, LAMEDON_HILLS, WOODLAND_REALM_HILLS, ITHILIEN_HILLS, DOR_EN_ERNIL_HILLS, DORWINION_HILLS -> 520;
            default -> 0;
        };
    }

    private static Map<Integer, MiddleEarthTerrainProfile> buildColorMap() {
        Map<Integer, MiddleEarthTerrainProfile> profiles = new HashMap<>();
        for (MiddleEarthTerrainProfile profile : values()) {
            profiles.put(profile.mapColor | 0xFF000000, profile);
        }
        return profiles;
    }

    private enum Surface {
        GRASS,
        PODZOL,
        SAND,
        RED_SAND,
        GRAVEL,
        STONE,
        SNOW_STONE,
        SNOW_GRASS,
        COARSE_DIRT,
        MORDOR_ROCK,
        GONDOR_GRASS,
        GONDOR_PODZOL,
        ROHAN_GRASS,
        ROHAN_PODZOL,
        CHALK_GRASS,
        MORDOR_DIRT,
        MUD_GRASS,
        WHITE_SAND;

        BlockState top() {
            return switch (this) {
                case GRASS -> Blocks.GRASS_BLOCK.defaultBlockState();
                case PODZOL -> Blocks.PODZOL.defaultBlockState();
                case SAND -> Blocks.SAND.defaultBlockState();
                case RED_SAND -> Blocks.RED_SAND.defaultBlockState();
                case GRAVEL -> Blocks.GRAVEL.defaultBlockState();
                case STONE -> Blocks.STONE.defaultBlockState();
                case SNOW_STONE -> Blocks.SNOW_BLOCK.defaultBlockState();
                case SNOW_GRASS -> Blocks.SNOW_BLOCK.defaultBlockState();
                case COARSE_DIRT -> Blocks.COARSE_DIRT.defaultBlockState();
                case MORDOR_ROCK -> LotrBlocks.MORDOR_ROCK.defaultBlockState();
                case GONDOR_GRASS, ROHAN_GRASS, CHALK_GRASS -> Blocks.GRASS_BLOCK.defaultBlockState();
                case GONDOR_PODZOL, ROHAN_PODZOL -> Blocks.PODZOL.defaultBlockState();
                case MORDOR_DIRT -> LotrBlocks.MORDOR_DIRT.defaultBlockState();
                case MUD_GRASS -> LotrBlocks.MUD_GRASS.defaultBlockState();
                case WHITE_SAND -> LotrBlocks.WHITE_SAND.defaultBlockState();
            };
        }

        BlockState filler() {
            return switch (this) {
                case GRASS, PODZOL, SNOW_GRASS -> Blocks.DIRT.defaultBlockState();
                case SAND -> Blocks.SAND.defaultBlockState();
                case RED_SAND -> Blocks.RED_SAND.defaultBlockState();
                case GRAVEL -> Blocks.GRAVEL.defaultBlockState();
                case STONE, SNOW_STONE, COARSE_DIRT -> Blocks.STONE.defaultBlockState();
                case MORDOR_ROCK -> LotrBlocks.MORDOR_ROCK.defaultBlockState();
                case GONDOR_GRASS, GONDOR_PODZOL -> LotrBlocks.GONDOR_ROCK.defaultBlockState();
                case ROHAN_GRASS, ROHAN_PODZOL -> LotrBlocks.ROHAN_ROCK.defaultBlockState();
                case CHALK_GRASS -> LotrBlocks.CHALK_ROCK.defaultBlockState();
                case MORDOR_DIRT -> LotrBlocks.MORDOR_DIRT.defaultBlockState();
                case MUD_GRASS -> LotrBlocks.MUD.defaultBlockState();
                case WHITE_SAND -> LotrBlocks.WHITE_SAND.defaultBlockState();
            };
        }
    }
}
