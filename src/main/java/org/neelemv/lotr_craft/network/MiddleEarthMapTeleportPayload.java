package org.neelemv.lotr_craft.network;

import org.neelemv.lotr_craft.Lotr_craft;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record MiddleEarthMapTeleportPayload(int mapX, int mapZ) implements CustomPacketPayload {
    public static final Type<MiddleEarthMapTeleportPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "middle_earth_map_teleport"));
    public static final StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, MiddleEarthMapTeleportPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            MiddleEarthMapTeleportPayload::mapX,
            ByteBufCodecs.INT,
            MiddleEarthMapTeleportPayload::mapZ,
            MiddleEarthMapTeleportPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
