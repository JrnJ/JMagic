package com.jeroenj.jspells;

import com.jeroenj.attachments.JMagicAttachmentTypes;
import com.jeroenj.attachments.JMagicManaAttachment;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class JSpell {
//    public static final Codec<JSpell> CODEC;
//    public static final PacketCodec<ByteBuf, JSpell> PACKET_CODEC;

    private final String name;
    private final int manaCost;
    private final int cooldown;
    private final Identifier slotTexture;

    private int cooldownTimer = 0;

    public JSpell(String name, int manaCost, int cooldown, Identifier slotTexture) {
        this.name = name;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.slotTexture = slotTexture;
    }

    public final JSpellCastResult cast(ServerWorld world, Entity user) {
        if (cooldownTimer > 0) {
            return JSpellCastResult.ON_COOLDOWN;
        }
        int currentMana = user.getAttachedOrCreate(JMagicAttachmentTypes.PLAYER_MANA).getCurrentMana();
        if (manaCost > currentMana) {
            return JSpellCastResult.NO_MANA;
        }

        cooldownTimer = cooldown;
        user.setAttached(JMagicAttachmentTypes.PLAYER_MANA, new JMagicManaAttachment(currentMana - manaCost));
        performCast(world, user);
        return JSpellCastResult.SUCCESS;
    }

    protected abstract void performCast(ServerWorld world, Entity user);

    public void tick() {
        if (cooldownTimer > 0) {
            cooldownTimer--;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getManaCost() {
        return this.manaCost;
    }

    public int getCooldown() {
        return this.cooldown;
    }
    public Identifier getSlotTexture() {
        return this.slotTexture;
    }

    public int getCooldownTimer() {
        return this.cooldownTimer;
    }
}
