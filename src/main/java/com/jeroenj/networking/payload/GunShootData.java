package com.jeroenj.networking.payload;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

public record GunShootData(Identifier identifier) {
    public static final Codec<GunShootData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("identifier").forGetter(GunShootData::identifier)
    ).apply(instance, GunShootData::new));
    public static PacketCodec<ByteBuf, GunShootData> PACKET_CODEC = PacketCodecs.codec(CODEC);
}
