package com.jeroenj.jspells.SunGod;

import com.jeroenj.JMagic;
import com.jeroenj.jspells.JMagicJSpells;
import com.jeroenj.jspells.JSpell;
import com.jeroenj.util.JHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

// TODO: toggle this on double jump
public class SunGodAirRun extends JSpell {
    public boolean active = false;

    public SunGodAirRun() {
        super(JMagicJSpells.SUN_GOD_AIR_RUN, "Air Run", "Air Run", 1, 2, JMagic.id("hud/icon/sun_god_air_run"));
    }

    @Override
    protected void serverCast(ServerWorld world, ServerPlayerEntity caster) {
        active = !active;
        if (active) {
            enable(caster);
        } else {
            disable(caster);
        }
    }

    private void enable(ServerPlayerEntity caster) {
        caster.setNoGravity(true);
    }

    private void disable(ServerPlayerEntity caster) {
        caster.setNoGravity(false);
    }

    @Override
    public void serverTick(ServerPlayerEntity caster) {
        super.serverTick(caster);
        if (!active) return;

        float rad = -caster.getYaw(1.0f) * (float) (Math.PI / 180.0);
        float x = MathHelper.sin(rad);
        float z = MathHelper.cos(rad);

        Vec3d result = new Vec3d(x, caster.getRotationVector().getY(), z);
        result = result.multiply(1.0);
        double maxClimbingVelocity = 0.5;
        if (Math.abs(result.getY()) > maxClimbingVelocity) {
            result = new Vec3d(result.getX(), MathHelper.clamp(result.getY(), -maxClimbingVelocity, maxClimbingVelocity), result.getZ());
        }

        caster.setVelocity(result);
        caster.velocityModified = true;
    }
}
