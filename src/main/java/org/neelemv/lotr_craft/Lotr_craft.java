package org.neelemv.lotr_craft;

import org.neelemv.lotr_craft.block.LotrBlocks;
import org.neelemv.lotr_craft.item.RingItem;
import org.neelemv.lotr_craft.item.MiddleEarthMapItem;
import org.neelemv.lotr_craft.item.TheOneRingItem;
import org.neelemv.lotr_craft.network.LotrNetworking;
import org.neelemv.lotr_craft.worldgen.LotrWorldgen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Lotr_craft implements ModInitializer {
    public static final String MOD_ID = "lotr_craft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Item GOLD_RING = registerItem("gold_ring", RingItem::new, new Item.Properties().stacksTo(1));
    public static final Item THE_ONE_RING = registerItem("the_one_ring", TheOneRingItem::new, new Item.Properties().stacksTo(1));
    public static final Item MIDDLE_EARTH_MAP = registerItem("middle_earth_map", MiddleEarthMapItem::new, new Item.Properties().stacksTo(1));
    public static final CreativeModeTab LOTR_CRAFT_TAB = registerCreativeTab();

    @Override
    public void onInitialize() {
        LotrBlocks.register();
        LotrNetworking.register();
        LotrWorldgen.register();
    }

    private static Item registerItem(String name, ItemFactory itemFactory, Item.Properties properties) {
        Identifier id = Identifier.fromNamespaceAndPath(MOD_ID, name);
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
        return Registry.register(BuiltInRegistries.ITEM, key, itemFactory.create(properties.setId(key)));
    }

    private static CreativeModeTab registerCreativeTab() {
        Identifier id = Identifier.fromNamespaceAndPath(MOD_ID, "lotr_craft");
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);

        CreativeModeTab tab = FabricCreativeModeTab.builder()
                .title(Component.translatable("itemGroup.lotr_craft"))
                .icon(() -> new ItemStack(GOLD_RING))
                .displayItems((parameters, output) -> {
                    output.accept(GOLD_RING);
                    output.accept(THE_ONE_RING);
                    output.accept(MIDDLE_EARTH_MAP);
                    output.accept(LotrBlocks.MORDOR_ROCK);
                    output.accept(LotrBlocks.GONDOR_ROCK);
                    output.accept(LotrBlocks.ROHAN_ROCK);
                    output.accept(LotrBlocks.BLUE_ROCK);
                    output.accept(LotrBlocks.RED_ROCK);
                    output.accept(LotrBlocks.CHALK_ROCK);
                    output.accept(LotrBlocks.MORDOR_DIRT);
                    output.accept(LotrBlocks.MORDOR_GRAVEL);
                    output.accept(LotrBlocks.MUD);
                    output.accept(LotrBlocks.MUD_GRASS);
                    output.accept(LotrBlocks.WHITE_SAND);
                    for (net.minecraft.world.level.block.Block block : LotrBlocks.TREE_BLOCKS) {
                        output.accept(block);
                    }
                    for (net.minecraft.world.level.block.Block block : LotrBlocks.FOLIAGE_BLOCKS) {
                        output.accept(block);
                    }
                })
                .build();

        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
    }

    @FunctionalInterface
    private interface ItemFactory {
        Item create(Item.Properties properties);
    }
}
