package com.jeroenj.networking.payload;

import com.jeroenj.networking.JMagicPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record UsedSpellPayload(UsedSpellData data) implements CustomPayload {
    public static final CustomPayload.Id<UsedSpellPayload> ID = new CustomPayload.Id<>(JMagicPackets.USED_SPELL_ID);
    public static final PacketCodec<RegistryByteBuf, UsedSpellPayload> PACKET_CODEC = PacketCodec.tuple(UsedSpellData.PACKET_CODEC, UsedSpellPayload::data, UsedSpellPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
