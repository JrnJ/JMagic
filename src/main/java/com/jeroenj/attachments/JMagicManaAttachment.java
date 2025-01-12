package com.jeroenj.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.Objects;

public class JMagicManaAttachment {
    public static final Codec<JMagicManaAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("current_mana").forGetter(JMagicManaAttachment::getCurrentMana)
    ).apply(instance, JMagicManaAttachment::new));

    public static PacketCodec<ByteBuf, JMagicManaAttachment> PACKET_CODEC = PacketCodecs.codec(CODEC);

    private int currentMana;

    public JMagicManaAttachment(int currentMana) {
        this.currentMana = currentMana;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int value) {
        this.currentMana = value;
    }

    public static JMagicManaAttachment of(PlayerEntity playerEntity) {
        if (Objects.nonNull(playerEntity)) {
            return playerEntity.getAttachedOrCreate(JMagicAttachmentTypes.PLAYER_MANA);
        } else throw new NullPointerException("Uwu owo nyaa");
    }
}
