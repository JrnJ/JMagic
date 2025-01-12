package com.jeroenj.mixin;

import com.jeroenj.JMagic;
import com.jeroenj.access.ServerPlayerEntityAccess;
import com.jeroenj.jspells.JSpellManager;
import com.jeroenj.jspells.JSpellRegistry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements ServerPlayerEntityAccess {

    @Unique
    private final JSpellManager spellManager = new JSpellManager(new ArrayList<>(List.of(
            JSpellRegistry.getSpell(JMagic.id("meteor")),
            JSpellRegistry.getSpell(JMagic.id("teleport")),
            JSpellRegistry.getSpell(JMagic.id("leap"))
    )));

    @Override
    public JSpellManager jMagic$getSpellManager() {
        return spellManager;
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        spellManager.tick((ServerPlayerEntity) (Object) this);
    }
}
