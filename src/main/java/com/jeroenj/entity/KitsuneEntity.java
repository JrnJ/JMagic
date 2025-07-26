package com.jeroenj.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class KitsuneEntity extends AnimalEntity {

    private Goal followChickenGoal;

    public KitsuneEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.PIG_FOOD);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity entity) {
        return (KitsuneEntity)JMagicEntities.KITSUNE.create(serverWorld, SpawnReason.BREEDING);
    }

    protected void initGoals() {
        followChickenGoal = new ActiveTargetGoal<>(this, AnimalEntity.class, 10, false, false, (entity, world) -> {
            return entity instanceof ChickenEntity;
        });

        //

    }
}
