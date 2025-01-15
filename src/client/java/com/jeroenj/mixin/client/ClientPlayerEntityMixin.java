package com.jeroenj.mixin.client;

import com.jeroenj.access.ClientPlayerEntityAccess;
import com.jeroenj.item.MagicWand;
import com.jeroenj.jspells.JClientSpellManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.jeroenj.JMagicClient.mc;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements ClientPlayerEntityAccess {

    @Unique
    private final JClientSpellManager spellManager = new JClientSpellManager();

    @Override
    @NotNull
    public JClientSpellManager jMagic$getClientSpellManager() {
        return spellManager;
    }

    @Inject(at = @At("TAIL"), method = "init")
    private void init(CallbackInfo ci) {
        MagicWand.setClientSpellCaster(((ClientPlayerEntityAccess) mc.player).jMagic$getClientSpellManager());
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        spellManager.tick();
//        spellManager.tick((ServerPlayerEntity) (Object) this);
    }
}
