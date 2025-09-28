package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import com.jeroenj.util.JHelper;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LeapSpell extends JSpell {
    public static final float LEAP_STRENGTH = 2.5f;
    public static final float UPWARDS_BOOST = 0.5f;

    public LeapSpell() {
        super(JMagicJSpells.LEAP_SPELL, "Leap", "Leap forward quickly.", 20, 60, JMagic.id("hud/icon/leap"));
    }

    @Override
    protected void serverCast(ServerWorld world, ServerPlayerEntity user) {
        Vec3d lookDirection = user.getRotationVec(1.0F);

        Vec3d leapVelocity = new Vec3d(
                lookDirection.x * LEAP_STRENGTH,
                lookDirection.y * LEAP_STRENGTH + UPWARDS_BOOST,
                lookDirection.z * LEAP_STRENGTH
        );

        user.addVelocity(leapVelocity.x, leapVelocity.y, leapVelocity.z);
        user.velocityModified = true;
        JHelper.playServerSound(user, SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
}
