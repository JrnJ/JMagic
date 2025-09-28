package com.jeroenj.item;

import com.jeroenj.sound.JMagicSounds;
import com.jeroenj.util.JHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class GunItem extends InspectableItem implements ReloadableItem {
    // Shooting
    private int shootCooldownTime = 2; // 10 per second or every 2 ticks based on 20tps
    private int shootCooldownTimer = 0;
    private boolean onShootCooldown = false;

    // Ammo
    public final int maxAmmo = 30;
    private int ammoAmount;

    // Reloading
    private boolean reloading = false;
    private final int reloadTime;
    private int reloadTimer = 0;

    // Inspecting
    private boolean inspecting = false;
    private final int inspectTime;
    private int inspectTimer = 0;

    private boolean gunSelected = false;
    private final SoundEvent reloadSound;
    private final SoundEvent selectSound;
    private final SoundEvent inspectSound;
    private final SoundEvent shootSound;

    public GunItem(Settings settings, int reloadTime, int inspectTime,
                   SoundEvent reloadSound, SoundEvent selectSound, SoundEvent inspectSound, SoundEvent shootSound) {
        super(settings);

        this.reloadTime = reloadTime;

        this.reloadSound = reloadSound;
        this.selectSound = selectSound;
        this.inspectSound = inspectSound;
        this.shootSound = shootSound;

        this.inspectTime = inspectTime;
        this.ammoAmount = this.maxAmmo;
    }

    public void onInMainHand(Entity entity) {
        JHelper.playClientSound(entity, selectSound, 1.0f, 1.0f);
    }

    public void onExitMainHand(Entity entity) {
        // reload stuff
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) return;

        if (selected) {
            if (!gunSelected) {
                gunSelected = true;
                onInMainHand(entity);
            }

            if (reloading) {
                reloadTimer--;
                if (reloadTimer <= 0) {
                    reloading = false;
                    this.ammoAmount = this.maxAmmo;
                }
            }

            if (onShootCooldown) {
                shootCooldownTimer--;
                if (shootCooldownTimer <= 0) {
                    onShootCooldown = false;
                }
            }

            if (inspecting) {
                inspectTimer--;
                if (inspectTimer <= 0) {
                    inspecting = false;
                }
            }
        } else if (gunSelected) {
            gunSelected = false;
            onExitMainHand(entity);

            if (reloading) {
                reloading = false;
            }
        }
    }

    @Override
    public void reload(PlayerEntity player) {
        if (reloading || ammoAmount == maxAmmo) {
            return;
        }

        reloading = true;
        JHelper.playClientSound(player, reloadSound, 1.0f, 1.0f);
        reloadTimer = reloadTime;
    }

    @Override
    public void clientInspect(PlayerEntity player) {
        if (reloading || inspecting) return;

        inspecting = true;
        inspectTimer = inspectTime;
        JHelper.playClientSound(player, inspectSound, 1.0f, 1.0f);
        super.clientInspect(player);
    }

    public int getAmmo() {
        return ammoAmount;
    }

    //
    public void shoot(PlayerEntity user) {
        if (ammoAmount <= 0 || reloading || onShootCooldown) {
            return;
        }

        onShootCooldown = true;
        shootCooldownTimer = shootCooldownTime;
        ammoAmount--;
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity)user;
        JHelper.playServerSound(serverPlayer, shootSound, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }

    public void zoom() {

    }
}
