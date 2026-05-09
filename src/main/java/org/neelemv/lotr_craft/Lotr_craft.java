package org.neelemv.lotr_craft;

import org.neelemv.lotr_craft.item.RingItem;
import org.neelemv.lotr_craft.item.TheOneRingItem;

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

    public static final Item GOLD_RING = registerItem("gold_ring", RingItem::new, new Item.Properties().stacksTo(1));
    public static final Item THE_ONE_RING = registerItem("the_one_ring", TheOneRingItem::new, new Item.Properties().stacksTo(1));
    public static final CreativeModeTab LOTR_CRAFT_TAB = registerCreativeTab();

    @Override
    public void onInitialize() {
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
                })
                .build();

        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
    }

    @FunctionalInterface
    private interface ItemFactory {
        Item create(Item.Properties properties);
    }
}
