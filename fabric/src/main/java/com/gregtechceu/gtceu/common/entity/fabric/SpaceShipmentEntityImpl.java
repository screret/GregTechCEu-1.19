package com.gregtechceu.gtceu.common.entity.fabric;

import com.gregtechceu.gtceu.api.capability.fabric.GTCapability;
import com.gregtechceu.gtceu.common.entity.SpaceShipmentEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class SpaceShipmentEntityImpl extends SpaceShipmentEntity {
    public SpaceShipmentEntityImpl(EntityType<? extends SpaceShipmentEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static void onEntityRegister(EntityType<SpaceShipmentEntity> entityType) {

    }

    public static SpaceShipmentEntity create(EntityType<? extends SpaceShipmentEntity> type, Level level) {
        return new SpaceShipmentEntityImpl(type, level);
    }
}
