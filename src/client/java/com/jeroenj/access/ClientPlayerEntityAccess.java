package com.jeroenj.access;

import com.jeroenj.jspells.JClientSpellManager;
import org.jetbrains.annotations.NotNull;

public interface ClientPlayerEntityAccess {
    @NotNull
    JClientSpellManager jMagic$getClientSpellManager();
}
