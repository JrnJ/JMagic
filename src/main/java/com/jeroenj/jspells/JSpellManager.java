package com.jeroenj.jspells;

import com.jeroenj.attachments.JMagicAttachmentTypes;
import com.jeroenj.attachments.JMagicManaAttachment;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.List;

public class JSpellManager {
    private JSpell selectedSpell;

    // 8 for now
    private final List<JSpell> spells;

    public JSpellManager(List<JSpell> spells) {
        this.spells = spells;
        selectedSpell = spells.getFirst();
    }

    public void tick(Entity user) {
        int currentMana = user.getAttachedOrCreate(JMagicAttachmentTypes.PLAYER_MANA).getCurrentMana();
        if (currentMana < 100) {
            currentMana = Math.clamp(currentMana + 1, 0, 100);
            user.setAttached(JMagicAttachmentTypes.PLAYER_MANA, new JMagicManaAttachment(currentMana));
        }

        for (JSpell spell : spells) {
            spell.tick();
        }
    }

    public void select(int index) {
        if (index < spells.size() && spells.get(index) instanceof JSpell spell) {
            selectedSpell = spell;
        } else {
            System.out.println("No spell here ^^");
        }
    }

    public JSpellCastResult cast(ServerWorld world, Entity user) {
        return selectedSpell.cast(world, user);
    }

    public JSpell get(int index) {
        if (index < spells.size()) {
            return spells.get(index);
        } else {
            return null;
        }
    }

    public JSpell getSelected() {
        return selectedSpell;
    }
}
