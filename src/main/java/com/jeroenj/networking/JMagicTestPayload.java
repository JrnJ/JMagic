package com.jeroenj.networking;

import com.jeroenj.networking.codec.JMagicTestRecord;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record JMagicTestPayload(JMagicTestRecord testValue) implements CustomPayload {
    public static final CustomPayload.Id<JMagicTestPayload> ID = new CustomPayload.Id<>(JMagicPackets.TEST_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, JMagicTestPayload> CODEC = PacketCodec.tuple(JMagicTestRecord.PACKET_CODEC, JMagicTestPayload::testValue, JMagicTestPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
