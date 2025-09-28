package com.jeroenj.jspells;

import com.jeroenj.access.ClientPlayerEntityAccess;
import com.jeroenj.access.ClientSpellCaster;
import com.jeroenj.jpassives.JMagicPassive;
import com.jeroenj.networking.payload.CastSpellData;
import com.jeroenj.networking.payload.CastSpellPayload;
import com.jeroenj.networking.payload.UsedSpellPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class JClientSpellManager implements ClientSpellCaster {
    private JSpell selectedSpell;
    private final List<JSpell> spells = Arrays.asList(new JSpell[JSpellManager.SPELL_COUNT]);

    public JClientSpellManager() {
        ClientPlayNetworking.registerGlobalReceiver(UsedSpellPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                JSpell spell = getSpell(payload.data().identifier());
                spell.setCooldownTimer(spell.getCooldown());
            });
        });
    }

    public void tick() {
        for (JSpell spell : spells) {
            if (spell == null) continue;
            spell.tick();
        }
    }

    public void setSpells(List<Identifier> spells) {
        for (int i = 0; i < spells.size(); i++) {
            this.spells.set(i, JSpellRegistry.getSpell(spells.get(i)));
        }
        selectedSpell = this.spells.get(0);
    }

    public void selectSpell(int index) {
        // Send Packet
        JSpell spell = spells.get(index);
        if (spell == null) {
            return;
        }
        selectedSpell = spell;
    }

    public JSpellCastResult castSpell(PlayerEntity caster) {
        if (selectedSpell != null) {
            return castSpell(selectedSpell, caster);
        }

        return JSpellCastResult.ERROR;
    }

    public JSpellCastResult castSpell(int index, PlayerEntity caster) {
        return castSpell(getSpell(index), caster);
    }

    public JSpellCastResult castSpell(JSpell spell, PlayerEntity caster) {
        // Client-Side check
        // Server will do the same check too, and if it fails will call rejected
        JSpellCastResult canCast = spell.canCast(caster);
        if (canCast != JSpellCastResult.SUCCESS) {
            return canCast; // TODO enable cooldown again
        }

        spell.clientCast(caster.getWorld(), caster); // client-cast
        ClientPlayNetworking.send(new CastSpellPayload(new CastSpellData(spell.getId()))); // server-cast

        return JSpellCastResult.SUCCESS;
    }

    public JSpell getSelectedSpell() {
        return selectedSpell;
    }

    public JSpell getSpell(int index) {
        return this.spells.get(index);
    }

    public JSpell getSpell(Identifier identifier) {
        for (JSpell spell : this.spells) {
            if (spell == null) continue;

            Identifier id = spell.getId();
            if (id.equals(identifier)) {
                return spell;
            }
        }

        return null;
    }
}
