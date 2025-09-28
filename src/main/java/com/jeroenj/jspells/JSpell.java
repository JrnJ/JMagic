package com.jeroenj.jspells;

import com.jeroenj.attachments.JMagicAttachmentTypes;
import com.jeroenj.attachments.JMagicManaAttachment;
import com.jeroenj.networking.payload.UsedSpellData;
import com.jeroenj.networking.payload.UsedSpellPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class JSpell {
//    public static final Codec<JSpell> CODEC;
//    public static final PacketCodec<ByteBuf, JSpell> PACKET_CODEC;

    private final Identifier id;
    private final String name;
    private final String description;
    private final int manaCost;
    private final int cooldown; // in ticks
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

    public JSpellCastResult canCast(PlayerEntity user) {
        if (cooldownTimer > 0) {
            return JSpellCastResult.ON_COOLDOWN;
        }
        int currentMana = user.getAttachedOrCreate(JMagicAttachmentTypes.PLAYER_MANA).getCurrentMana();
        if (manaCost > currentMana) {
            return JSpellCastResult.NO_MANA;
        }

        return JSpellCastResult.SUCCESS;
    }

    public final JSpellCastResult cast(ServerWorld world, ServerPlayerEntity user) {
        JSpellCastResult canCast = canCast(user);
        if (canCast != JSpellCastResult.SUCCESS) {
            return canCast; // TODO enable cooldown again
        }

        cooldownTimer = cooldown;
        int currentMana = user.getAttachedOrCreate(JMagicAttachmentTypes.PLAYER_MANA).getCurrentMana();
        user.setAttached(JMagicAttachmentTypes.PLAYER_MANA, new JMagicManaAttachment(currentMana - manaCost));
        serverCast(world, user);

        // TODO change atTick(0)
        world.getServer().execute(() -> {
            ServerPlayNetworking.send(user, new UsedSpellPayload(new UsedSpellData(this.getId(), 0)));
        });

        return JSpellCastResult.SUCCESS;
    }

    protected abstract void serverCast(ServerWorld world, ServerPlayerEntity user);
    protected void clientCast(World world, PlayerEntity user) // TODO MAKE ABSTRACT
    {

    }

    public String spellSelectInfo(PlayerEntity user) {
        return "";
    }

    protected void showInfoMessage(String message) {

    }

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
