package com.gregtechceu.gtceu.common.entity.fabric;

import com.gregtechceu.gtceu.common.entity.SpaceShuttleEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class SpaceShuttleEntityImpl extends SpaceShuttleEntity {
    public SpaceShuttleEntityImpl(EntityType<? extends SpaceShuttleEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static void onEntityRegister(EntityType<SpaceShuttleEntity> entityType) {

    }

    public static SpaceShuttleEntity create(EntityType<? extends SpaceShuttleEntity> type, Level level) {
        return new SpaceShuttleEntityImpl(type, level);
    }
}
