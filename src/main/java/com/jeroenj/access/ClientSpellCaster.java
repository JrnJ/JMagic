package com.jeroenj.access;

import com.jeroenj.jspells.JSpellCastResult;
import net.minecraft.entity.player.PlayerEntity;

public interface ClientSpellCaster {
    JSpellCastResult castSpell(PlayerEntity caster);
}
