package com.jeroenj.jspells;

import com.jeroenj.access.ServerPlayerEntityAccess;
import com.jeroenj.attachments.JMagicAttachmentTypes;
import com.jeroenj.attachments.JMagicManaAttachment;
import com.jeroenj.networking.payload.CastSpellPayload;
import com.jeroenj.networking.payload.UsedSpellData;
import com.jeroenj.networking.payload.UsedSpellPayload;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class JSpell {
//    public static final Codec<JSpell> CODEC;
//    public static final PacketCodec<ByteBuf, JSpell> PACKET_CODEC;

    private final Identifier id;
    private final String name;
    private final String description;
    private final int manaCost;
    private final int cooldown;
    private final Identifier slotTexture;

    private int cooldownTimer = 0;

    public JSpell(Identifier id, String name, String description, int manaCost, int cooldown, Identifier slotTexture) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.slotTexture = slotTexture;
    }

    public final JSpellCastResult cast(ServerWorld world, ServerPlayerEntity user) {
        if (cooldownTimer > 0) {
//            return JSpellCastResult.ON_COOLDOWN;
        }
        int currentMana = user.getAttachedOrCreate(JMagicAttachmentTypes.PLAYER_MANA).getCurrentMana();
        if (manaCost > currentMana) {
//            return JSpellCastResult.NO_MANA;
        }

        cooldownTimer = cooldown;
        user.setAttached(JMagicAttachmentTypes.PLAYER_MANA, new JMagicManaAttachment(currentMana - manaCost));
        performCast(world, user);

        // TODO change atTick(0)
        world.getServer().execute(() -> {
            ServerPlayNetworking.send(user, new UsedSpellPayload(new UsedSpellData(this.getId(), 0)));
        });

        return JSpellCastResult.SUCCESS;
    }

    protected abstract void performCast(ServerWorld world, Entity user);

    public void tick() {
        if (cooldownTimer > 0) {
            cooldownTimer--;
        }
    }

    public Identifier getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
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
    public void setCooldownTimer(int cooldown) {
        cooldownTimer = cooldown;
    }

    // HelperMethods
    protected void instantiateEntity(Entity entity) {

    }
}
