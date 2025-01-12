package com.jeroenj.entity;

import com.jeroenj.JMagic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public final class JMagicEntities {

    public static final EntityType<MeteorEntity> METEOR = Registry.register(
            Registries.ENTITY_TYPE, JMagic.id("meteor"),
            EntityType.Builder.create(MeteorEntity::new, SpawnGroup.MISC)
                    .dimensions(1.0f, 1.0f)
                    .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, JMagic.id("meteor"))));

    public static void initialize() {

    }
}
