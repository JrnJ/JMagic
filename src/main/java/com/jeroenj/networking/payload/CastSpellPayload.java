package com.jeroenj.networking.payload;

import com.jeroenj.networking.JMagicPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record CastSpellPayload(CastSpellData data) implements CustomPayload {
    public static final CustomPayload.Id<CastSpellPayload> ID = new CustomPayload.Id<>(JMagicPackets.CAST_SPELL_ID);
    public static final PacketCodec<RegistryByteBuf, CastSpellPayload> PACKET_CODEC = PacketCodec.tuple(CastSpellData.PACKET_CODEC, CastSpellPayload::data, CastSpellPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
