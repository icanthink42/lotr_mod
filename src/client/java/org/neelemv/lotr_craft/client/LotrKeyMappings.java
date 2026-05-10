package org.neelemv.lotr_craft.client;

import org.neelemv.lotr_craft.Lotr_craft;

import com.mojang.blaze3d.platform.InputConstants;

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public final class LotrKeyMappings {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "controls"));
    public static final KeyMapping OPEN_MAP = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.lotr_craft.open_map",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_M,
            CATEGORY));
    public static final KeyMapping MAP_TELEPORT = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.lotr_craft.map_teleport",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_T,
            CATEGORY));

    private LotrKeyMappings() {
    }

    public static void register() {
    }
}
