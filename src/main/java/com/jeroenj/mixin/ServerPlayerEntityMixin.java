package com.jeroenj.mixin;

import com.jeroenj.JMagic;
import com.jeroenj.access.ServerPlayerEntityAccess;
import com.jeroenj.jspells.JMagicJSpells;
import com.jeroenj.jspells.JSpellManager;
import com.jeroenj.jspells.JSpellRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
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
            JSpellRegistry.getSpell(JMagicJSpells.METEOR_SPELL),
            JSpellRegistry.getSpell(JMagicJSpells.TELEPORT_SPELL),
            JSpellRegistry.getSpell(JMagicJSpells.LEAP_SPELL),
            JSpellRegistry.getSpell(JMagicJSpells.MANA_BOLT_SPELL),
            JSpellRegistry.getSpell(JMagicJSpells.SHRINK_SPELL),
            JSpellRegistry.getSpell(JMagicJSpells.GROW_SPELL)
    )));

    @Override
    public JSpellManager jMagic$getSpellManager() {
        return spellManager;
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        spellManager.tick((ServerPlayerEntity) (Object) this);
    }

    @Inject(at = @At("HEAD"), method = "fall", cancellable = true)
    private void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition, CallbackInfo ci) {

    }
}
