package com.jeroenj.networking.persistant;

import com.jeroenj.networking.JMagicPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record JMagicDirtPayload(JMagicDirtData value) implements CustomPayload {
    public static final CustomPayload.Id<JMagicDirtPayload> ID = new CustomPayload.Id<>(JMagicPackets.DIRT_BROKEN_ID);
    public static final PacketCodec<RegistryByteBuf, JMagicDirtPayload> CODEC = PacketCodec.tuple(JMagicDirtData.PACKET_CODEC, JMagicDirtPayload::value, JMagicDirtPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
