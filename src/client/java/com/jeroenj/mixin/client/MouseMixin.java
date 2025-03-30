package com.jeroenj.mixin.client;

import com.jeroenj.hud.SpellSelectHud;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(at = @At("HEAD"), method = "onMouseScroll", cancellable = true)
    public void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (SpellSelectHud.isShowing()) {
            if (vertical > 0) {
                SpellSelectHud.decreaseCategory();
            } else {
                SpellSelectHud.increaseCategory();
            }
            ci.cancel();
        }
    }
}
