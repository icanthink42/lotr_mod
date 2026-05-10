package org.neelemv.lotr_craft.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.block.custom.LotrGroundPlantBlock;
import org.neelemv.lotr_craft.block.custom.LotrSaplingBlock;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;

public final class LotrBlocks {
    public static final List<Block> TREE_BLOCKS = new ArrayList<>();
    public static final List<Block> FOLIAGE_BLOCKS = new ArrayList<>();
    public static final List<Block> SURFACE_BLOCKS = new ArrayList<>();

    public static final Block MORDOR_ROCK = registerBlock("mordor_rock", rock(MapColor.COLOR_BLACK));
    public static final Block GONDOR_ROCK = registerBlock("gondor_rock", rock(MapColor.STONE));
    public static final Block ROHAN_ROCK = registerBlock("rohan_rock", rock(MapColor.COLOR_BROWN));
    public static final Block BLUE_ROCK = registerBlock("blue_rock", rock(MapColor.COLOR_LIGHT_BLUE));
    public static final Block RED_ROCK = registerBlock("red_rock", rock(MapColor.COLOR_RED));
    public static final Block CHALK_ROCK = registerBlock("chalk_rock", rock(MapColor.QUARTZ));
    public static final Block MORDOR_DIRT = registerBlock("mordor_dirt", BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)
            .mapColor(MapColor.COLOR_BLACK)
            .strength(0.5F)
            .sound(SoundType.GRAVEL));
    public static final Block MORDOR_GRAVEL = registerBlock("mordor_gravel", BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL)
            .mapColor(MapColor.COLOR_BLACK)
            .strength(0.6F)
            .sound(SoundType.GRAVEL));
    public static final Block QUAGMIRE = registerSurfaceBlock("quagmire", BlockBehaviour.Properties.ofFullCopy(Blocks.MUD)
            .mapColor(MapColor.COLOR_BROWN)
            .strength(0.8F)
            .sound(SoundType.MUD));
    public static final Block MUD = registerBlock("mud", BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)
            .mapColor(MapColor.COLOR_BROWN)
            .strength(0.5F)
            .sound(SoundType.GRAVEL));
    public static final Block MUD_GRASS = registerBlock("mud_grass", BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)
            .mapColor(MapColor.GRASS)
            .strength(0.6F)
            .sound(SoundType.GRASS));
    public static final Block WHITE_SAND = registerBlock("white_sand", BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
            .mapColor(MapColor.SNOW)
            .strength(0.5F)
            .sound(SoundType.SAND));
    public static final Block RED_CLAY = registerSurfaceBlock("red_clay", BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)
            .mapColor(MapColor.COLOR_RED)
            .strength(1.25F)
            .sound(SoundType.GRAVEL));
    public static final Block RED_SANDSTONE = registerSurfaceBlock("red_sandstone", BlockBehaviour.Properties.ofFullCopy(Blocks.RED_SANDSTONE)
            .mapColor(MapColor.COLOR_ORANGE)
            .strength(0.8F)
            .sound(SoundType.STONE));
    public static final Block WHITE_SANDSTONE = registerSurfaceBlock("white_sandstone", BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE)
            .mapColor(MapColor.SNOW)
            .strength(0.8F)
            .sound(SoundType.STONE));
    public static final Block DRIED_REEDS = registerSurfaceBlock("dried_reeds", BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)
            .mapColor(MapColor.COLOR_BROWN)
            .strength(0.5F)
            .sound(SoundType.GRASS));

    static {
        registerTreeFamily("mallorn", MapColor.GOLD, MapColor.COLOR_YELLOW);
        registerTreeFamily("mirk_oak", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("lebethron", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("beech", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("holly", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("banana", MapColor.COLOR_YELLOW, MapColor.PLANT);
        registerTreeFamily("maple", MapColor.WOOD, MapColor.COLOR_ORANGE);
        registerTreeFamily("larch", MapColor.PODZOL, MapColor.PLANT);
        registerTreeFamily("date_palm", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("mangrove", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("chestnut", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("baobab", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("cedar", MapColor.PODZOL, MapColor.PLANT);
        registerTreeFamily("fir", MapColor.PODZOL, MapColor.PLANT);
        registerTreeFamily("pine", MapColor.PODZOL, MapColor.PLANT);
        registerTreeFamily("mahogany", MapColor.COLOR_RED, MapColor.PLANT);
        registerTreeFamily("willow", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("cypress", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("olive", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("aspen", MapColor.WOOD, MapColor.COLOR_YELLOW);
        registerTreeFamily("green_oak", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("lairelosse", MapColor.SNOW, MapColor.SNOW);
        registerTreeFamily("almond", MapColor.WOOD, MapColor.COLOR_PINK);
        registerTreeFamily("plum", MapColor.WOOD, MapColor.COLOR_PURPLE);
        registerTreeFamily("redwood", MapColor.COLOR_RED, MapColor.PLANT);
        registerTreeFamily("pomegranate", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("palm", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("dragonblood", MapColor.COLOR_RED, MapColor.PLANT);
        registerTreeFamily("kanuka", MapColor.WOOD, MapColor.PLANT);
        registerTreeFamily("charred", MapColor.COLOR_BLACK, MapColor.COLOR_BLACK);

        registerGroundPlant("elanor", MapColor.COLOR_YELLOW);
        registerGroundPlant("niphredil", MapColor.SNOW);
        registerGroundPlant("simbelmyne", MapColor.SNOW);
        registerGroundPlant("shire_heather", MapColor.COLOR_PINK);
        registerGroundPlant("athelas", MapColor.PLANT);
        registerGroundPlant("bluebell", MapColor.COLOR_BLUE);
        registerGroundPlant("clover", MapColor.PLANT);
        registerGroundPlant("asphodel", MapColor.SNOW);
        registerGroundPlant("marigold", MapColor.COLOR_ORANGE);
        registerGroundPlant("lavender", MapColor.COLOR_PURPLE);
        registerGroundPlant("flowering_grass", MapColor.PLANT);
        registerGroundPlant("thistle", MapColor.PLANT);
        registerGroundPlant("nettle", MapColor.PLANT);
        registerGroundPlant("fern_sprout", MapColor.PLANT);
        registerGroundPlant("blueberry_bush", MapColor.PLANT);
        registerGroundPlant("blackberry_bush", MapColor.PLANT);
        registerGroundPlant("raspberry_bush", MapColor.PLANT);
        registerGroundPlant("cranberry_bush", MapColor.PLANT);
        registerGroundPlant("elderberry_bush", MapColor.PLANT);
        registerGroundPlant("wildberry_bush", MapColor.PLANT);
    }

    private LotrBlocks() {
    }

    public static void register() {
    }

    private static BlockBehaviour.Properties rock(MapColor mapColor) {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                .mapColor(mapColor)
                .strength(1.5F, 10.0F)
                .sound(SoundType.STONE);
    }

    private static void registerTreeFamily(String name, MapColor woodColor, MapColor leafColor) {
        TREE_BLOCKS.add(registerLog(name + "_log", woodColor));
        if (!name.equals("charred")) {
            TREE_BLOCKS.add(registerLeaves(name + "_leaves", leafColor));
        }
        TREE_BLOCKS.add(registerSapling(name + "_sapling"));
    }

    private static Block registerLog(String name, MapColor mapColor) {
        return registerBlock(name, properties -> new RotatedPillarBlock(properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                .mapColor(mapColor)
                .strength(2.0F)
                .sound(SoundType.WOOD));
    }

    private static Block registerLeaves(String name, MapColor mapColor) {
        return registerBlock(name, properties -> new UntintedParticleLeavesBlock(0.01F, ParticleTypes.CHERRY_LEAVES, properties),
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                        .mapColor(mapColor)
                        .strength(0.2F)
                        .sound(SoundType.GRASS)
                        .noOcclusion()
                        .randomTicks());
    }

    private static Block registerSapling(String name) {
        String treeName = name.substring(0, name.length() - "_sapling".length());
        TreeGrower grower = new TreeGrower(Lotr_craft.MOD_ID + ":" + treeName,
                Optional.empty(),
                Optional.of(configuredFeatureKey(treeName + "_tree")),
                Optional.empty());
        return registerBlock(name, properties -> new LotrSaplingBlock(grower, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
                .mapColor(MapColor.PLANT)
                .noOcclusion()
                .randomTicks()
                .strength(0.0F)
                .sound(SoundType.GRASS));
    }

    private static void registerGroundPlant(String name, MapColor mapColor) {
        registerGroundPlantBlock(name, mapColor);
    }

    private static Block registerGroundPlantBlock(String name, MapColor mapColor) {
        Block block = registerBlock(name, properties -> new LotrGroundPlantBlock(properties), BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)
                .mapColor(mapColor)
                .noOcclusion()
                .replaceable()
                .strength(0.0F)
                .sound(SoundType.GRASS));
        FOLIAGE_BLOCKS.add(block);
        return block;
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, name));
    }

    private static Block registerBlock(String name, BlockBehaviour.Properties properties) {
        return registerBlock(name, Block::new, properties);
    }

    private static Block registerSurfaceBlock(String name, BlockBehaviour.Properties properties) {
        Block block = registerBlock(name, properties);
        SURFACE_BLOCKS.add(block);
        return block;
    }

    private static Block registerBlock(String name, BlockFactory blockFactory, BlockBehaviour.Properties properties) {
        Identifier id = Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, name);
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
        Block block = Registry.register(BuiltInRegistries.BLOCK, blockKey, blockFactory.create(properties.setId(blockKey)));
        Registry.register(BuiltInRegistries.ITEM, itemKey, new BlockItem(block, new Item.Properties().setId(itemKey)));
        return block;
    }

    @FunctionalInterface
    private interface BlockFactory {
        Block create(BlockBehaviour.Properties properties);
    }
}
