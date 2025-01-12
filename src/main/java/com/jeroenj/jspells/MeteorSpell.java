package com.jeroenj.jspells;

import com.jeroenj.JMagic;
import com.jeroenj.entity.JMagicEntities;
import com.jeroenj.entity.MeteorEntity;
import com.jeroenj.util.JHelper;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MeteorSpell extends JSpell {
    public static final float MAX_DISTANCE = 25.0f;
    public static final SoundEvent SUMMON_SOUND = SoundEvents.ENTITY_PLAYER_HURT;

    MeteorSpell() {
        super("Meteor", 20, 60, JMagic.id("hud/icon/meteor"));
    }

    @Override
    protected void performCast(ServerWorld world, Entity user) {
        Vec3d lookingAtPos = JSpellClientHelper.GetLookingAt(user, MAX_DISTANCE, true);
        lookingAtPos = lookingAtPos.add(0.0, 10.0, 0.0);

        MeteorEntity meteor = new MeteorEntity(JMagicEntities.METEOR, world);
        meteor.setPosition(lookingAtPos);
        world.spawnEntity(meteor);

        JHelper.playServerSound(user, SUMMON_SOUND, 1.0f, 1.0f);
        JHelper.spawnServerParticle(world, ParticleTypes.CRIT, lookingAtPos, 3, 0.0, 0.0, 0.0, 0.0);
    }
}
