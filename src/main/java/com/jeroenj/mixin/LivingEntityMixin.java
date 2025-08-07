package com.jeroenj.mixin;

import com.jeroenj.jpassives.ElytraBouncePassive;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    private void damage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source == world.getDamageSources().flyIntoWall()) {
            if ((Object)this instanceof ServerPlayerEntity player) {
                ElytraBouncePassive passive = new ElytraBouncePassive();
                passive.onActivate(player);
                cir.setReturnValue(false);
            }
        }
    }
}
