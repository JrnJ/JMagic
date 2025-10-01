package com.jeroenj.mixin;

import com.jeroenj.access.ServerPlayerEntityAccess;
import com.jeroenj.jpassives.BouncePassive;
import com.jeroenj.jpassives.GroundSlamPassive;
import com.jeroenj.jspells.JMagicJSpells;
import com.jeroenj.jspells.SunGod.SunGodGiantSpell;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private int sneakTicks = 0;
    private static final int MAX_SNEAK_TICKS = 60;
    private static final int MIN_SNEAK_TICKS_TO_BOOST = 20;

    @Inject(at = @At("HEAD"), method = "handleFallDamage", cancellable = true)
    private void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {

        if ((Object)this instanceof ServerPlayerEntity player) {
            // Bounce
            BouncePassive bouncePassive = new BouncePassive();

            // Ground Slam first
            SunGodGiantSpell spell = ((ServerPlayerEntityAccess) player).jMagic$getSpellManager().getSpell(JMagicJSpells.SUN_GOD_GIANT_SPELL, SunGodGiantSpell.class);
            if (spell != null && spell.active) {
                new GroundSlamPassive().onActivate(player);
                bouncePassive.playSound = false;
            }

            bouncePassive.onActivate(player);

            cir.setReturnValue(false); // always cancel fall damage
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.isSneaking() && player.isOnGround()) {
            if (sneakTicks < MAX_SNEAK_TICKS) {
                sneakTicks++;
            }
        } else {
            sneakTicks = 0;

        }

        if (sneakTicks == MAX_SNEAK_TICKS) {
            player.addVelocity(0, 2.5, 0);
            player.velocityModified = true;
        }
    }
}
