package com.jeroenj.jspells;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public final class JSpellClientHelper {
    // Util
    public static Vec3d getLookingAt(Entity user, double maxDistance, boolean intersectEntities) {
        Vec3d start = user.getCameraPosVec(1.0f);
        Vec3d direction = user.getRotationVec(1.0f);
        Vec3d end = start.add(direction.multiply(maxDistance));

        // Cannot hit entities through walls, so check this one regardles
        HitResult blockHitResult = user.getWorld().raycast(new RaycastContext(
                start, end,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                user
        ));

        if (!intersectEntities) {
            return blockHitResult.getPos();
        }

        EntityHitResult entityHitResult = ProjectileUtil.raycast(
                user,
                start, end,
                new Box(start, end).expand(1.0f),
                Entity::isAttackable,
                maxDistance * maxDistance
        );

        // No Entity found
        if (entityHitResult == null) {
            return blockHitResult.getPos();
        }

        // Entity found
        return entityHitResult.getPos();
    }
}
