package com.jeroenj.mixin;

import com.jeroenj.jpassives.BouncePassive;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
            BouncePassive passive = new BouncePassive();
            passive.onActivate(player);
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
            player.addVelocity(0, 5.0, 0);
            player.velocityModified = true;
        }
    }
}
