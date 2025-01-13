package com.jeroenj.networking.persistant;

import com.jeroenj.networking.JMagicPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record InitialSyncPayload(JMagicDirtData value) implements CustomPayload {
    public static final CustomPayload.Id<InitialSyncPayload> ID = new CustomPayload.Id<>(JMagicPackets.INITIAL_SYNC_ID);
    public static final PacketCodec<RegistryByteBuf, InitialSyncPayload> CODEC = PacketCodec.tuple(JMagicDirtData.PACKET_CODEC, InitialSyncPayload::value, InitialSyncPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
