package org.neelemv.lotr_craft.network;

import org.neelemv.lotr_craft.Lotr_craft;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record FactionAlignmentSyncPayload(String encodedAlignments) implements CustomPacketPayload {
    public static final Type<FactionAlignmentSyncPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Lotr_craft.MOD_ID, "faction_alignment_sync"));
    public static final StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, FactionAlignmentSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            FactionAlignmentSyncPayload::encodedAlignments,
            FactionAlignmentSyncPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
