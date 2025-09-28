package com.jeroenj.networking.payload;

import com.jeroenj.networking.JMagicPackets;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record GunShootPayload(GunShootData data) implements CustomPayload {
    public static final CustomPayload.Id<GunShootPayload> ID = new CustomPayload.Id<>(JMagicPackets.GUN_SHOOT_ID);
    public static final PacketCodec<RegistryByteBuf, GunShootPayload> PACKET_CODEC = PacketCodec.tuple(GunShootData.PACKET_CODEC, GunShootPayload::data, GunShootPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
