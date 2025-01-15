package com.jeroenj.networking.payload;

import com.jeroenj.networking.JMagicPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record PlayerSpellsPayload(PlayerSpellsData data) implements CustomPayload {
    public static final CustomPayload.Id<PlayerSpellsPayload> ID = new CustomPayload.Id<>(JMagicPackets.SYNC_PLAYER_SPELLS_ID);
    public static final PacketCodec<RegistryByteBuf, PlayerSpellsPayload> PACKET_CODEC = PacketCodec.tuple(PlayerSpellsData.PACKET_CODEC, PlayerSpellsPayload::data, PlayerSpellsPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
