package org.neelemv.lotr_craft.block;

import org.neelemv.lotr_craft.Lotr_craft;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public final class LotrBlocks {
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

    private static Block registerBlock(String name, BlockBehaviour.Properties properties) {
        Identifier id = Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, name);
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
        Block block = Registry.register(BuiltInRegistries.BLOCK, blockKey, new Block(properties.setId(blockKey)));
        Registry.register(BuiltInRegistries.ITEM, itemKey, new BlockItem(block, new Item.Properties().setId(itemKey)));
        return block;
    }
}
