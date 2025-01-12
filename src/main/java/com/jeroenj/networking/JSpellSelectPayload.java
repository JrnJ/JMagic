package com.jeroenj.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record JSpellSelectPayload(Identifier identifier) implements CustomPayload {
    public static final CustomPayload.Id<JSpellSelectPayload> ID = new CustomPayload.Id<>(JMagicPackets.SELECT_SPELL_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, JSpellSelectPayload> CODEC = PacketCodec.tuple(Identifier.PACKET_CODEC, JSpellSelectPayload::identifier, JSpellSelectPayload::new);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
