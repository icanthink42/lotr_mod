package org.neelemv.lotr_craft.network;

import org.neelemv.lotr_craft.Lotr_craft;
import org.neelemv.lotr_craft.worldgen.MiddleEarthMapConstants;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.Heightmap;

public final class LotrNetworking {
    private LotrNetworking() {
    }

    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(MiddleEarthMapTeleportPayload.TYPE, MiddleEarthMapTeleportPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MiddleEarthMapTeleportPayload.TYPE, (payload, context) -> teleportToMapPoint(context.player(), payload.mapX(), payload.mapZ()));
    }

    private static void teleportToMapPoint(ServerPlayer player, int mapX, int mapZ) {
        if (!hasMiddleEarthMap(player) || mapX < 0 || mapX >= MiddleEarthMapConstants.MAP_WIDTH || mapZ < 0 || mapZ >= MiddleEarthMapConstants.MAP_HEIGHT) {
            return;
        }

        ServerLevel level = player.level();
        int blockX = MiddleEarthMapConstants.mapToBlockX(mapX);
        int blockZ = MiddleEarthMapConstants.mapToBlockZ(mapZ);
        level.getChunk(blockX >> 4, blockZ >> 4);
        int blockY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockX, blockZ);
        player.teleportTo(blockX + 0.5, blockY, blockZ + 0.5);
    }

    private static boolean hasMiddleEarthMap(ServerPlayer player) {
        return player.getMainHandItem().getItem() == Lotr_craft.MIDDLE_EARTH_MAP || player.getOffhandItem().getItem() == Lotr_craft.MIDDLE_EARTH_MAP;
    }
}
