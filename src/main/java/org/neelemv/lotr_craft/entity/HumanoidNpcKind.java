package org.neelemv.lotr_craft.entity;

import java.util.Locale;

public enum HumanoidNpcKind {
    BREE_MAN("bree_man", "Bree Man", SkinSet.BREE),
    BREE_GUARD("bree_guard", "Bree Guard", SkinSet.BREE),
    BREE_CAPTAIN("bree_captain", "Bree Captain", SkinSet.BREE),
    BREE_BAKER("bree_baker", "Bree Baker", SkinSet.BREE, "bree/outfit_baker.png"),
    BREE_BLACKSMITH("bree_blacksmith", "Bree Blacksmith", SkinSet.BREE, "bree/outfit_blacksmith.png"),
    BREE_BREWER("bree_brewer", "Bree Brewer", SkinSet.BREE, "bree/outfit_brewer.png"),
    BREE_BUTCHER("bree_butcher", "Bree Butcher", SkinSet.BREE, "bree/outfit_butcher.png"),
    BREE_FARMER("bree_farmer", "Bree Farmer", SkinSet.BREE),
    BREE_FARMHAND("bree_farmhand", "Bree Farmhand", SkinSet.BREE),
    BREE_FLORIST("bree_florist", "Bree Florist", SkinSet.BREE, "bree/outfit_florist.png"),
    BREE_INNKEEPER("bree_innkeeper", "Bree Innkeeper", SkinSet.BREE, "bree/outfit_innkeeper.png"),
    BREE_LUMBERMAN("bree_lumberman", "Bree Lumberman", SkinSet.BREE),
    BREE_MARKET_TRADER("bree_market_trader", "Bree Market Trader", SkinSet.BREE),
    BREE_MASON("bree_mason", "Bree Mason", SkinSet.BREE, "bree/outfit_mason.png"),
    BREE_RUFFIAN("bree_ruffian", "Bree Ruffian", SkinSet.BREE_RUFFIAN),
    RUFFIAN_BRUTE("ruffian_brute", "Ruffian Brute", SkinSet.BREE_RUFFIAN),
    RUFFIAN_SPY("ruffian_spy", "Ruffian Spy", SkinSet.BREE_RUFFIAN),

    GONDOR_MAN("gondor_man", "Gondor Man", SkinSet.GONDOR),
    GONDOR_SOLDIER("gondor_soldier", "Gondor Soldier", SkinSet.GONDOR_SOLDIER),
    GONDOR_ARCHER("gondor_archer", "Gondor Archer", SkinSet.GONDOR_SOLDIER),
    GONDOR_TOWER_GUARD("gondor_tower_guard", "Gondor Tower Guard", SkinSet.GONDOR_SOLDIER),
    GONDORIAN_CAPTAIN("gondorian_captain", "Gondorian Captain", SkinSet.GONDOR_SOLDIER),
    DOL_AMROTH_SOLDIER("dol_amroth_soldier", "Dol Amroth Soldier", SkinSet.GONDOR_SOLDIER),
    DOL_AMROTH_ARCHER("dol_amroth_archer", "Dol Amroth Archer", SkinSet.GONDOR_SOLDIER),
    DOL_AMROTH_CAPTAIN("dol_amroth_captain", "Dol Amroth Captain", SkinSet.GONDOR_SOLDIER),
    SWAN_KNIGHT("swan_knight", "Swan Knight", SkinSet.SWAN_KNIGHT),
    BLACKROOT_SOLDIER("blackroot_soldier", "Blackroot Soldier", SkinSet.RANGER),
    BLACKROOT_ARCHER("blackroot_archer", "Blackroot Archer", SkinSet.RANGER),
    LAMEDON_SOLDIER("lamedon_soldier", "Lamedon Soldier", SkinSet.RANGER),
    LAMEDON_ARCHER("lamedon_archer", "Lamedon Archer", SkinSet.RANGER),
    LEBENNIN_LEVYMAN("lebennin_levyman", "Lebennin Levyman", SkinSet.GONDOR),
    LOSSARNACH_AXEMAN("lossarnach_axeman", "Lossarnach Axeman", SkinSet.GONDOR_SOLDIER),
    PELARGIR_MARINE("pelargir_marine", "Pelargir Marine", SkinSet.GONDOR_SOLDIER),
    PINNATH_GELIN_SOLDIER("pinnath_gelin_soldier", "Pinnath Gelin Soldier", SkinSet.GONDOR_SOLDIER),
    GONDOR_BAKER("gondor_baker", "Gondor Baker", SkinSet.GONDOR, "gondor/outfit_baker.png"),
    GONDOR_BLACKSMITH("gondor_blacksmith", "Gondor Blacksmith", SkinSet.GONDOR, "gondor/outfit_blacksmith.png"),
    GONDOR_BREWER("gondor_brewer", "Gondor Brewer", SkinSet.GONDOR, "gondor/outfit_brewer.png"),
    GONDOR_BUTCHER("gondor_butcher", "Gondor Butcher", SkinSet.GONDOR, "gondor/outfit_butcher.png"),
    GONDOR_FARMER("gondor_farmer", "Gondor Farmer", SkinSet.GONDOR),
    GONDOR_FARMHAND("gondor_farmhand", "Gondor Farmhand", SkinSet.GONDOR),
    GONDOR_FISHMONGER("gondor_fishmonger", "Gondor Fishmonger", SkinSet.GONDOR),
    GONDOR_FLORIST("gondor_florist", "Gondor Florist", SkinSet.GONDOR, "gondor/outfit_florist.png"),
    GONDOR_GREENGROCER("gondor_greengrocer", "Gondor Greengrocer", SkinSet.GONDOR, "gondor/outfit_greengrocer.png"),
    GONDOR_LUMBERMAN("gondor_lumberman", "Gondor Lumberman", SkinSet.GONDOR),
    GONDOR_MARKET_TRADER("gondor_market_trader", "Gondor Market Trader", SkinSet.GONDOR),
    GONDOR_MASON("gondor_mason", "Gondor Mason", SkinSet.GONDOR, "gondor/outfit_mason.png"),

    ROHAN_MAN("rohan_man", "Rohan Man", SkinSet.ROHAN),
    ROHIRRIM_WARRIOR("rohirrim_warrior", "Rohirrim Warrior", SkinSet.ROHAN_WARRIOR),
    ROHIRRIM_ARCHER("rohirrim_archer", "Rohirrim Archer", SkinSet.ROHAN_WARRIOR),
    ROHIRRIM_MARSHAL("rohirrim_marshal", "Rohirrim Marshal", SkinSet.ROHAN_WARRIOR),
    ROHAN_SHIELDMAIDEN("rohan_shieldmaiden", "Rohan Shieldmaiden", SkinSet.ROHAN_SHIELDMAIDEN),
    ROHAN_BAKER("rohan_baker", "Rohan Baker", SkinSet.ROHAN),
    ROHAN_BLACKSMITH("rohan_blacksmith", "Rohan Blacksmith", SkinSet.ROHAN),
    ROHAN_BREWER("rohan_brewer", "Rohan Brewer", SkinSet.ROHAN),
    ROHAN_BUILDER("rohan_builder", "Rohan Builder", SkinSet.ROHAN),
    ROHAN_BUTCHER("rohan_butcher", "Rohan Butcher", SkinSet.ROHAN),
    ROHAN_FARMER("rohan_farmer", "Rohan Farmer", SkinSet.ROHAN),
    ROHAN_FARMHAND("rohan_farmhand", "Rohan Farmhand", SkinSet.ROHAN),
    ROHAN_FISHMONGER("rohan_fishmonger", "Rohan Fishmonger", SkinSet.ROHAN),
    ROHAN_LUMBERMAN("rohan_lumberman", "Rohan Lumberman", SkinSet.ROHAN),
    ROHAN_MARKET_TRADER("rohan_market_trader", "Rohan Market Trader", SkinSet.ROHAN),
    ROHAN_MEADHOST("rohan_meadhost", "Rohan Meadhost", SkinSet.ROHAN),
    ROHAN_ORCHARDER("rohan_orcharder", "Rohan Orcharder", SkinSet.ROHAN),
    ROHAN_STABLEMASTER("rohan_stablemaster", "Rohan Stablemaster", SkinSet.ROHAN),

    DALE_MAN("dale_man", "Dale Man", SkinSet.DALE),
    DALE_SOLDIER("dale_soldier", "Dale Soldier", SkinSet.DALE_SOLDIER),
    DALE_ARCHER("dale_archer", "Dale Archer", SkinSet.DALE_SOLDIER),
    DALE_CAPTAIN("dale_captain", "Dale Captain", SkinSet.DALE_SOLDIER),
    DALE_LEVYMAN("dale_levyman", "Dale Levyman", SkinSet.DALE),
    DALE_MERCHANT("dale_merchant", "Dale Merchant", SkinSet.DALE),
    DALE_BAKER("dale_baker", "Dale Baker", SkinSet.DALE, "dale/baker_apron.png"),
    DALE_BLACKSMITH("dale_blacksmith", "Dale Blacksmith", SkinSet.DALE, "dale/blacksmith_apron.png"),

    DORWINION_MAN("dorwinion_man", "Dorwinion Man", SkinSet.DORWINION),
    DORWINION_GUARD("dorwinion_guard", "Dorwinion Guard", SkinSet.DORWINION),
    DORWINION_CAPTAIN("dorwinion_captain", "Dorwinion Captain", SkinSet.DORWINION),
    DORWINION_VINEHAND("dorwinion_vinehand", "Dorwinion Vinehand", SkinSet.DORWINION, "dorwinion/outfit/0.png"),
    DORWINION_VINEKEEPER("dorwinion_vinekeeper", "Dorwinion Vinekeeper", SkinSet.DORWINION, "dorwinion/outfit/1.png"),
    DORWINION_ELF("dorwinion_elf", "Dorwinion Elf", SkinSet.DORWINION_ELF),
    DORWINION_ELF_WARRIOR("dorwinion_elf_warrior", "Dorwinion Elf Warrior", SkinSet.DORWINION_ELF),
    DORWINION_ELF_ARCHER("dorwinion_elf_archer", "Dorwinion Elf Archer", SkinSet.DORWINION_ELF),
    DORWINION_ELF_CAPTAIN("dorwinion_elf_captain", "Dorwinion Elf Captain", SkinSet.DORWINION_ELF),
    DORWINION_ELF_VINTNER("dorwinion_elf_vintner", "Dorwinion Elf Vintner", SkinSet.DORWINION_ELF),

    DUNEDAIN("dunedain", "Dunedain", SkinSet.RANGER),
    DUNEDAIN_BLACKSMITH("dunedain_blacksmith", "Dunedain Blacksmith", SkinSet.RANGER),
    RANGER_NORTH("ranger_north", "Ranger of the North", SkinSet.RANGER),
    RANGER_NORTH_CAPTAIN("ranger_north_captain", "Ranger Captain of the North", SkinSet.RANGER),
    RANGER_ITHILIEN("ranger_ithilien", "Ranger of Ithilien", SkinSet.RANGER),
    RANGER_ITHILIEN_CAPTAIN("ranger_ithilien_captain", "Ranger Captain of Ithilien", SkinSet.RANGER),

    DUNLENDING("dunlending", "Dunlending", SkinSet.DUNLAND),
    DUNLENDING_WARRIOR("dunlending_warrior", "Dunlending Warrior", SkinSet.DUNLAND),
    DUNLENDING_ARCHER("dunlending_archer", "Dunlending Archer", SkinSet.DUNLAND),
    DUNLENDING_AXE_THROWER("dunlending_axe_thrower", "Dunlending Axe Thrower", SkinSet.DUNLAND),
    DUNLENDING_BERSERKER("dunlending_berserker", "Dunlending Berserker", SkinSet.DUNLAND_BERSERKER),
    DUNLENDING_WARLORD("dunlending_warlord", "Dunlending Warlord", SkinSet.DUNLAND),
    DUNLENDING_BARTENDER("dunlending_bartender", "Dunlending Bartender", SkinSet.DUNLAND, "dunland/bartender_apron.png"),

    HILLMAN("hillman", "Hillman", SkinSet.HILLMAN),
    HILLMAN_WARRIOR("hillman_warrior", "Hillman Warrior", SkinSet.HILLMAN),
    HILLMAN_AXE_THROWER("hillman_axe_thrower", "Hillman Axe Thrower", SkinSet.HILLMAN),
    HILLMAN_CHIEFTAIN("hillman_chieftain", "Hillman Chieftain", SkinSet.HILLMAN),

    DWARF("dwarf", "Dwarf", SkinSet.DWARF, 0.75F),
    DWARF_WARRIOR("dwarf_warrior", "Dwarf Warrior", SkinSet.DWARF, 0.75F),
    DWARF_AXE_THROWER("dwarf_axe_thrower", "Dwarf Axe Thrower", SkinSet.DWARF, 0.75F),
    DWARF_COMMANDER("dwarf_commander", "Dwarf Commander", SkinSet.DWARF, 0.75F),
    DWARF_MINER("dwarf_miner", "Dwarf Miner", SkinSet.DWARF, 0.75F),
    DWARF_SMITH("dwarf_smith", "Dwarf Smith", SkinSet.DWARF, "dwarf/blacksmith_apron.png", 0.75F),
    BLUE_DWARF("blue_dwarf", "Blue Mountains Dwarf", SkinSet.BLUE_DWARF, 0.75F),
    BLUE_DWARF_WARRIOR("blue_dwarf_warrior", "Blue Mountains Dwarf Warrior", SkinSet.BLUE_DWARF, 0.75F),
    BLUE_DWARF_AXE_THROWER("blue_dwarf_axe_thrower", "Blue Mountains Dwarf Axe Thrower", SkinSet.BLUE_DWARF, 0.75F),
    BLUE_DWARF_COMMANDER("blue_dwarf_commander", "Blue Mountains Dwarf Commander", SkinSet.BLUE_DWARF, 0.75F),
    BLUE_DWARF_MERCHANT("blue_dwarf_merchant", "Blue Mountains Dwarf Merchant", SkinSet.BLUE_DWARF, 0.75F),
    BLUE_DWARF_MINER("blue_dwarf_miner", "Blue Mountains Dwarf Miner", SkinSet.BLUE_DWARF, 0.75F),
    BLUE_MOUNTAINS_SMITH("blue_mountains_smith", "Blue Mountains Smith", SkinSet.BLUE_DWARF, "dwarf/blacksmith_apron.png", 0.75F),
    WICKED_DWARF("wicked_dwarf", "Wicked Dwarf", SkinSet.WICKED_DWARF, 0.75F),
    IRON_HILLS_MERCHANT("iron_hills_merchant", "Iron Hills Merchant", SkinSet.DWARF, 0.75F),

    HIGH_ELF("high_elf", "High Elf", SkinSet.HIGH_ELF),
    HIGH_ELF_WARRIOR("high_elf_warrior", "High Elf Warrior", SkinSet.HIGH_ELF),
    HIGH_ELF_LORD("high_elf_lord", "High Elf Lord", SkinSet.HIGH_ELF),
    HIGH_ELF_SMITH("high_elf_smith", "High Elf Smith", SkinSet.HIGH_ELF),
    RIVENDELL_ELF("rivendell_elf", "Rivendell Elf", SkinSet.HIGH_ELF),
    RIVENDELL_WARRIOR("rivendell_warrior", "Rivendell Warrior", SkinSet.HIGH_ELF),
    RIVENDELL_LORD("rivendell_lord", "Rivendell Lord", SkinSet.HIGH_ELF),
    RIVENDELL_SMITH("rivendell_smith", "Rivendell Smith", SkinSet.HIGH_ELF),
    RIVENDELL_TRADER("rivendell_trader", "Rivendell Trader", SkinSet.HIGH_ELF),
    GALADHRIM_ELF("galadhrim_elf", "Galadhrim Elf", SkinSet.GALADHRIM),
    GALADHRIM_WARRIOR("galadhrim_warrior", "Galadhrim Warrior", SkinSet.GALADHRIM),
    GALADHRIM_WARDEN("galadhrim_warden", "Galadhrim Warden", SkinSet.GALADHRIM),
    GALADHRIM_LORD("galadhrim_lord", "Galadhrim Lord", SkinSet.GALADHRIM),
    GALADHRIM_SMITH("galadhrim_smith", "Galadhrim Smith", SkinSet.GALADHRIM),
    GALADHRIM_TRADER("galadhrim_trader", "Galadhrim Trader", SkinSet.GALADHRIM),
    WOOD_ELF("wood_elf", "Wood-elf", SkinSet.WOOD_ELF),
    WOOD_ELF_WARRIOR("wood_elf_warrior", "Wood-elf Warrior", SkinSet.WOOD_ELF),
    WOOD_ELF_SCOUT("wood_elf_scout", "Wood-elf Scout", SkinSet.WOOD_ELF),
    WOOD_ELF_CAPTAIN("wood_elf_captain", "Wood-elf Captain", SkinSet.WOOD_ELF),
    WOOD_ELF_SMITH("wood_elf_smith", "Wood-elf Smith", SkinSet.WOOD_ELF),
    TORMENTED_ELF("tormented_elf", "Tormented Elf", SkinSet.TORMENTED_ELF),

    MORDOR_ORC("mordor_orc", "Mordor Orc", SkinSet.ORC),
    MORDOR_ORC_ARCHER("mordor_orc_archer", "Mordor Orc Archer", SkinSet.ORC),
    MORDOR_ORC_BOMBARDIER("mordor_orc_bombardier", "Mordor Orc Bombardier", SkinSet.ORC),
    MORDOR_ORC_MERCENARY_CAPTAIN("mordor_orc_mercenary_captain", "Mordor Orc Mercenary Captain", SkinSet.ORC),
    MORDOR_ORC_SLAVER("mordor_orc_slaver", "Mordor Orc Slaver", SkinSet.ORC),
    MORDOR_ORC_SPIDER_KEEPER("mordor_orc_spider_keeper", "Mordor Orc Spider Keeper", SkinSet.ORC),
    MORDOR_ORC_TRADER("mordor_orc_trader", "Mordor Orc Trader", SkinSet.ORC),
    DOL_GULDUR_ORC("dol_guldur_orc", "Dol Guldur Orc", SkinSet.ORC),
    DOL_GULDUR_ORC_ARCHER("dol_guldur_orc_archer", "Dol Guldur Orc Archer", SkinSet.ORC),
    DOL_GULDUR_ORC_CHIEFTAIN("dol_guldur_orc_chieftain", "Dol Guldur Orc Chieftain", SkinSet.ORC),
    DOL_GULDUR_ORC_TRADER("dol_guldur_orc_trader", "Dol Guldur Orc Trader", SkinSet.ORC),
    ANGMAR_ORC("angmar_orc", "Angmar Orc", SkinSet.ORC),
    ANGMAR_ORC_ARCHER("angmar_orc_archer", "Angmar Orc Archer", SkinSet.ORC),
    ANGMAR_ORC_BOMBARDIER("angmar_orc_bombardier", "Angmar Orc Bombardier", SkinSet.ORC),
    ANGMAR_ORC_TRADER("angmar_orc_trader", "Angmar Orc Trader", SkinSet.ORC),
    GUNDABAD_ORC("gundabad_orc", "Gundabad Orc", SkinSet.ORC),
    GUNDABAD_ORC_ARCHER("gundabad_orc_archer", "Gundabad Orc Archer", SkinSet.ORC),
    GUNDABAD_ORC_TRADER("gundabad_orc_trader", "Gundabad Orc Trader", SkinSet.ORC),
    GUNDABAD_URUK("gundabad_uruk", "Gundabad Uruk", SkinSet.BLACK_URUK),
    GUNDABAD_URUK_ARCHER("gundabad_uruk_archer", "Gundabad Uruk Archer", SkinSet.BLACK_URUK),
    BLACK_URUK("black_uruk", "Black Uruk", SkinSet.BLACK_URUK),
    BLACK_URUK_ARCHER("black_uruk_archer", "Black Uruk Archer", SkinSet.BLACK_URUK),
    BLACK_URUK_CAPTAIN("black_uruk_captain", "Black Uruk Captain", SkinSet.BLACK_URUK),
    URUK_HAI("uruk_hai", "Uruk-hai", SkinSet.URUK_HAI),
    URUK_HAI_BERSERKER("uruk_hai_berserker", "Uruk-hai Berserker", SkinSet.URUK_HAI),
    URUK_HAI_CROSSBOWER("uruk_hai_crossbower", "Uruk-hai Crossbower", SkinSet.URUK_HAI),
    URUK_HAI_SAPPER("uruk_hai_sapper", "Uruk-hai Sapper", SkinSet.URUK_HAI),
    URUK_HAI_TRADER("uruk_hai_trader", "Uruk-hai Trader", SkinSet.URUK_HAI),
    ISENGARD_SNAGA("isengard_snaga", "Isengard Snaga", SkinSet.ORC),
    ISENGARD_SNAGA_ARCHER("isengard_snaga_archer", "Isengard Snaga Archer", SkinSet.ORC),
    UTUMNO_ORC("utumno_orc", "Utumno Orc", SkinSet.ORC),
    UTUMNO_ORC_ARCHER("utumno_orc_archer", "Utumno Orc Archer", SkinSet.ORC),

    EASTERLING("easterling", "Easterling", SkinSet.RHUN),
    EASTERLING_WARRIOR("easterling_warrior", "Easterling Warrior", SkinSet.RHUN),
    EASTERLING_ARCHER("easterling_archer", "Easterling Archer", SkinSet.RHUN),
    EASTERLING_FIRE_THROWER("easterling_fire_thrower", "Easterling Fire-thrower", SkinSet.RHUN),
    EASTERLING_GOLD_WARRIOR("easterling_gold_warrior", "Easterling Gold Warrior", SkinSet.RHUN),
    EASTERLING_WARLORD("easterling_warlord", "Easterling Warlord", SkinSet.RHUN),
    EASTERLING_BAKER("easterling_baker", "Easterling Baker", SkinSet.RHUN),
    EASTERLING_BLACKSMITH("easterling_blacksmith", "Easterling Blacksmith", SkinSet.RHUN),
    EASTERLING_BREWER("easterling_brewer", "Easterling Brewer", SkinSet.RHUN),
    EASTERLING_BUTCHER("easterling_butcher", "Easterling Butcher", SkinSet.RHUN),
    EASTERLING_FARMER("easterling_farmer", "Easterling Farmer", SkinSet.RHUN),
    EASTERLING_FARMHAND("easterling_farmhand", "Easterling Farmhand", SkinSet.RHUN),
    EASTERLING_FISHMONGER("easterling_fishmonger", "Easterling Fishmonger", SkinSet.RHUN),
    EASTERLING_GOLDSMITH("easterling_goldsmith", "Easterling Goldsmith", SkinSet.RHUN),
    EASTERLING_HUNTER("easterling_hunter", "Easterling Hunter", SkinSet.RHUN),
    EASTERLING_LEVYMAN("easterling_levyman", "Easterling Levyman", SkinSet.RHUN),
    EASTERLING_LUMBERMAN("easterling_lumberman", "Easterling Lumberman", SkinSet.RHUN),
    EASTERLING_MARKET_TRADER("easterling_market_trader", "Easterling Market Trader", SkinSet.RHUN),
    EASTERLING_MASON("easterling_mason", "Easterling Mason", SkinSet.RHUN),

    NEAR_HARADRIM("near_haradrim", "Near Haradrim", SkinSet.NEAR_HARAD),
    NEAR_HARADRIM_WARRIOR("near_haradrim_warrior", "Near Haradrim Warrior", SkinSet.NEAR_HARAD_WARRIOR),
    NEAR_HARADRIM_ARCHER("near_haradrim_archer", "Near Haradrim Archer", SkinSet.NEAR_HARAD_WARRIOR),
    NEAR_HARADRIM_WARLORD("near_haradrim_warlord", "Near Haradrim Warlord", SkinSet.NEAR_HARAD_WARRIOR),
    NEAR_HARAD_BLACKSMITH("near_harad_blacksmith", "Near Harad Blacksmith", SkinSet.NEAR_HARAD),
    NEAR_HARAD_MERCHANT("near_harad_merchant", "Near Harad Merchant", SkinSet.NEAR_HARAD),
    HARNEDHRIM("harnedhrim", "Harnedhrim", SkinSet.HARNEDOR),
    HARNEDOR_WARRIOR("harnedor_warrior", "Harnedor Warrior", SkinSet.HARNEDOR_WARRIOR),
    HARNEDOR_ARCHER("harnedor_archer", "Harnedor Archer", SkinSet.HARNEDOR_WARRIOR),
    HARNEDOR_WARLORD("harnedor_warlord", "Harnedor Warlord", SkinSet.HARNEDOR_WARRIOR),
    HARNEDOR_TRADER("harnedor_trader", "Harnedor Trader", SkinSet.HARNEDOR),
    GULF_HARADRIM("gulf_haradrim", "Gulf Haradrim", SkinSet.NEAR_HARAD),
    GULF_HARAD_WARRIOR("gulf_harad_warrior", "Gulf Harad Warrior", SkinSet.NEAR_HARAD_WARRIOR),
    GULF_HARAD_ARCHER("gulf_harad_archer", "Gulf Harad Archer", SkinSet.NEAR_HARAD_WARRIOR),
    GULF_HARAD_WARLORD("gulf_harad_warlord", "Gulf Harad Warlord", SkinSet.NEAR_HARAD_WARRIOR),
    UMBARIAN("umbarian", "Umbarian", SkinSet.NEAR_HARAD),
    UMBAR_WARRIOR("umbar_warrior", "Umbar Warrior", SkinSet.NEAR_HARAD_WARRIOR),
    UMBAR_ARCHER("umbar_archer", "Umbar Archer", SkinSet.NEAR_HARAD_WARRIOR),
    UMBAR_CAPTAIN("umbar_captain", "Umbar Captain", SkinSet.NEAR_HARAD_WARRIOR),
    CORSAIR("corsair", "Corsair", SkinSet.NEAR_HARAD_WARRIOR),
    CORSAIR_CAPTAIN("corsair_captain", "Corsair Captain", SkinSet.NEAR_HARAD_WARRIOR),
    CORSAIR_SLAVER("corsair_slaver", "Corsair Slaver", SkinSet.NEAR_HARAD_WARRIOR),
    NOMAD("nomad", "Nomad", SkinSet.NOMAD),
    NOMAD_WARRIOR("nomad_warrior", "Nomad Warrior", SkinSet.NOMAD),
    NOMAD_ARCHER("nomad_archer", "Nomad Archer", SkinSet.NOMAD),
    NOMAD_CHIEFTAIN("nomad_chieftain", "Nomad Chieftain", SkinSet.NOMAD),
    HARAD_SLAVE("harad_slave", "Harad Slave", SkinSet.NEAR_HARAD_SLAVE),
    NURN_SLAVE("nurn_slave", "Nurn Slave", SkinSet.NURN_SLAVE),

    TAUREDAIN("tauredain", "Tauredain", SkinSet.TAUREDAIN),
    TAUREDAIN_WARRIOR("tauredain_warrior", "Tauredain Warrior", SkinSet.TAUREDAIN, "tauredain/outfit/0.png"),
    TAUREDAIN_BLOWGUNNER("tauredain_blowgunner", "Tauredain Blowgunner", SkinSet.TAUREDAIN, "tauredain/outfit/0.png"),
    TAUREDAIN_CHIEFTAIN("tauredain_chieftain", "Tauredain Chieftain", SkinSet.TAUREDAIN, "tauredain/outfit/0.png"),
    TAUREDAIN_FARMER("tauredain_farmer", "Tauredain Farmer", SkinSet.TAUREDAIN),
    TAUREDAIN_FARMHAND("tauredain_farmhand", "Tauredain Farmhand", SkinSet.TAUREDAIN),
    TAUREDAIN_SHAMAN("tauredain_shaman", "Tauredain Shaman", SkinSet.TAUREDAIN, "tauredain/shaman_outfit/0.png"),
    TAUREDAIN_SMITH("tauredain_smith", "Tauredain Smith", SkinSet.TAUREDAIN),

    MOREDAN("moredain", "Moredain", SkinSet.MOREDAIN),
    MOREDAN_WARRIOR("moredain_warrior", "Moredain Warrior", SkinSet.MOREDAIN, "moredain/outfit/0.png"),
    MOREDAN_HUNTSMAN("moredain_huntsman", "Moredain Huntsman", SkinSet.MOREDAIN, "moredain/outfit/1.png"),
    MOREDAN_HUTMAKER("moredain_hutmaker", "Moredain Hutmaker", SkinSet.MOREDAIN, "moredain/outfit/2.png"),
    MOREDAN_CHIEFTAIN("moredain_chieftain", "Moredain Chieftain", SkinSet.MOREDAIN, "moredain/outfit/0.png"),
    MOREDAN_MERCENARY("moredain_mercenary", "Moredain Mercenary", SkinSet.MOREDAIN, "moredain/outfit/1.png"),
    MOREDAN_VILLAGE_TRADER("moredain_village_trader", "Moredain Village Trader", SkinSet.MOREDAIN),

    HALF_TROLL("half_troll", "Half-troll", SkinSet.HALF_TROLL, 1.15F),
    HALF_TROLL_WARRIOR("half_troll_warrior", "Half-troll Warrior", SkinSet.HALF_TROLL, 1.15F),
    HALF_TROLL_SCAVENGER("half_troll_scavenger", "Half-troll Scavenger", SkinSet.HALF_TROLL, 1.15F),
    HALF_TROLL_WARLORD("half_troll_warlord", "Half-troll Warlord", SkinSet.HALF_TROLL, 1.15F),

    BANDIT("bandit", "Bandit", SkinSet.BANDIT),
    HARAD_BANDIT("harad_bandit", "Harad Bandit", SkinSet.HARAD_BANDIT),
    SCRAP_TRADER("scrap_trader", "Scrap Trader", SkinSet.SCRAP_TRADER),
    GONDOR_RENEGADE("gondor_renegade", "Gondor Renegade", SkinSet.GONDOR),
    MARSH_WRAITH("marsh_wraith", "Marsh Wraith", SkinSet.WRAITH),
    BARROW_WIGHT("barrow_wight", "Barrow-wight", SkinSet.BARROW_WIGHT),
    GONDOR_RUINS_WRAITH("gondor_ruins_wraith", "Gondor Ruins Wraith", SkinSet.WRAITH),
    SKELETAL_WRAITH("skeletal_wraith", "Skeletal Wraith", SkinSet.WRAITH),
    GANDALF("gandalf", "Gandalf", SkinSet.GANDALF),
    SARUMAN("saruman", "Saruman", SkinSet.SARUMAN);

    private final String id;
    private final String displayName;
    private final SkinSet skinSet;
    private final String overlayTexture;
    private final float scale;

    HumanoidNpcKind(String id, String displayName, SkinSet skinSet) {
        this(id, displayName, skinSet, null, 0.9375F);
    }

    HumanoidNpcKind(String id, String displayName, SkinSet skinSet, float scale) {
        this(id, displayName, skinSet, null, scale);
    }

    HumanoidNpcKind(String id, String displayName, SkinSet skinSet, String overlayTexture) {
        this(id, displayName, skinSet, overlayTexture, 0.9375F);
    }

    HumanoidNpcKind(String id, String displayName, SkinSet skinSet, String overlayTexture, float scale) {
        this.id = id;
        this.displayName = displayName;
        this.skinSet = skinSet;
        this.overlayTexture = overlayTexture;
        this.scale = scale;
    }

    public String id() {
        return id;
    }

    public String displayName() {
        return displayName;
    }

    public SkinSet skinSet() {
        return skinSet;
    }

    public String overlayTexture() {
        return overlayTexture;
    }

    public float scale() {
        return scale;
    }

    public enum SkinSet {
        BREE("bree/bree_male", 20, "bree/bree_female", 9, "bree/headwear_female/0.png"),
        BREE_RUFFIAN("bree/ruffian", 5),
        GONDOR("gondor/gondor_male", 8, 2, "gondor/gondor_female", 14, 0, "gondor/headwear_female/0.png"),
        GONDOR_SOLDIER("gondor/gondorSoldier", 6),
        SWAN_KNIGHT("gondor/swanKnight", 3),
        ROHAN("rohan/rohan_male", 6, "rohan/rohan_female", 7, null),
        ROHAN_WARRIOR("rohan/warrior", 6),
        ROHAN_SHIELDMAIDEN("rohan/shieldmaiden", 3),
        DALE("dale/dale_male", 3, "dale/dale_female", 2, null),
        DALE_SOLDIER("dale/dale_soldier", 3),
        DORWINION("dorwinion/dorwinion_male", 4, "dorwinion/dorwinion_female", 4, null),
        DORWINION_ELF("elf/dorwinion_male", 3, "elf/dorwinion_female", 3, null),
        RANGER("ranger/ranger_male", 5, "ranger/ranger_female", 3, null),
        DUNLAND("dunland/dunlending_male", 4, "dunland/dunlending_female", 3, null),
        DUNLAND_BERSERKER("dunland/berserker", 1),
        HILLMAN("hillman/hillman_male", 3, "hillman/hillman_female", 4, null),
        DWARF("dwarf/dwarf_male", 3, "dwarf/dwarf_female", 3, null),
        BLUE_DWARF("dwarf/blueMountains_male", 3, "dwarf/blueMountains_female", 3, null),
        WICKED_DWARF("dwarf/wicked_male", 1),
        HIGH_ELF("elf/highElf_male", 18, "elf/highElf_female", 11, null),
        GALADHRIM("elf/galadhrim_male", 4, "elf/galadhrim_female", 3, null),
        WOOD_ELF("elf/woodElf_male", 4, "elf/woodElf_female", 3, null),
        TORMENTED_ELF("elf/tormented", 3),
        ORC("orc/orc", 8),
        BLACK_URUK("orc/blackUruk", 3),
        URUK_HAI("orc/urukHai", 3),
        RHUN("rhun/easterling_male", 5, "rhun/easterling_female", 5, null),
        NEAR_HARAD("nearHarad/haradrim_male", 5, "nearHarad/haradrim_female", 3, null),
        NEAR_HARAD_WARRIOR("nearHarad/warrior", 1),
        HARNEDOR("nearHarad/harnedor_male", 5, "nearHarad/harnedor_female", 3, null),
        HARNEDOR_WARRIOR("nearHarad/harnedorWarrior", 5),
        NOMAD("nearHarad/nomad_male", 5, "nearHarad/nomad_female", 3, "nearHarad/nomad_hat/0.png"),
        NEAR_HARAD_SLAVE("nearHarad/slave/nearHarad_male", 2),
        NURN_SLAVE("nurn/slave_male", 4, "nurn/slave_female", 3, null),
        TAUREDAIN("tauredain/tauredain_male", 4, "tauredain/tauredain_female", 3, null),
        MOREDAIN("moredain/moredain_male", 5, "moredain/moredain_female", 4, null),
        HALF_TROLL("halfTroll/halfTroll", 3),
        BANDIT("bandit/bandit", 6),
        HARAD_BANDIT("bandit/harad", 5),
        SCRAP_TRADER("scrapTrader", 2),
        BARROW_WIGHT("barrowWight/wight", 1),
        WRAITH("wraith/marshwraith.png", 0),
        GANDALF("char/gandalf.png", 0, null, 0, "char/gandalf_hat.png"),
        SARUMAN("char/saruman.png", 0);

        private final String malePath;
        private final int maleVariants;
        private final int maleStart;
        private final String femalePath;
        private final int femaleVariants;
        private final int femaleStart;
        private final String headwearPath;

        SkinSet(String fixedPath, int variants) {
            this(fixedPath, variants, 0, null, 0, 0, null);
        }

        SkinSet(String malePath, int maleVariants, String femalePath, int femaleVariants, String headwearPath) {
            this(malePath, maleVariants, 0, femalePath, femaleVariants, 0, headwearPath);
        }

        SkinSet(String malePath, int maleVariants, int maleStart, String femalePath, int femaleVariants, int femaleStart, String headwearPath) {
            this.malePath = malePath;
            this.maleVariants = maleVariants;
            this.maleStart = maleStart;
            this.femalePath = femalePath;
            this.femaleVariants = femaleVariants;
            this.femaleStart = femaleStart;
            this.headwearPath = headwearPath;
        }

        public String baseTexture(long bits) {
            boolean useFemale = femalePath != null && (bits & 1L) != 0L;
            String folder = useFemale ? femalePath : malePath;
            int variants = useFemale ? femaleVariants : maleVariants;
            int start = useFemale ? femaleStart : maleStart;
            if (variants <= 0) {
                return folder;
            }
            int index = start + Math.floorMod(bits >> 1, Math.max(variants, 1));
            return folder + "/" + index + ".png";
        }

        public String headwearTexture(long bits) {
            if (headwearPath == null || femalePath == null || (bits & 1L) == 0L || Math.floorMod(bits >> 5, 4) != 0) {
                return null;
            }
            return headwearPath;
        }

        public boolean isOrc() {
            return this == ORC || this == BLACK_URUK || this == URUK_HAI;
        }

        public static String enumNameFromId(String id) {
            return id.toUpperCase(Locale.ROOT).replace('-', '_');
        }
    }
}
