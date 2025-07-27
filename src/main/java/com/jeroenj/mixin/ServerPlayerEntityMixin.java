package com.jeroenj.mixin;

import com.jeroenj.JMagic;
import com.jeroenj.access.ServerPlayerEntityAccess;
import com.jeroenj.jspells.JMagicJSpells;
import com.jeroenj.jspells.JSpellManager;
import com.jeroenj.jspells.JSpellRegistry;
import com.jeroenj.sound.JMagicSounds;
import com.jeroenj.util.JHelper;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
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
            JSpellRegistry.getSpell(JMagicJSpells.GROW_SPELL),
            JSpellRegistry.getSpell(JMagicJSpells.TOGGLE_SUN_GOD_SPELL),
            JSpellRegistry.getSpell(JMagicJSpells.SUN_GOD_GIANT_SPELL)
    )));

    @Override
    public JSpellManager jMagic$getSpellManager() {
        return spellManager;
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        spellManager.tick((ServerPlayerEntity) (Object) this);
    }

    @Inject(at = @At("HEAD"), method = "jump")
    private void jump(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        JHelper.playServerSound(
                player, JMagicSounds.CARTOON_BOING, SoundCategory.PLAYERS, 0.25f, 0.9f + (player.getServerWorld().getRandom().nextFloat() * 0.1f));
    }
}
