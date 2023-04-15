package com.gregtechceu.gtceu.common.entity.forge;

import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.common.entity.SpaceShipmentEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpaceShipmentEntityImpl extends SpaceShipmentEntity {
    public SpaceShipmentEntityImpl(EntityType<? extends SpaceShipmentEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }

    public static void onEntityRegister(EntityType<SpaceShipmentEntity> entityType) {
    }

    public static SpaceShipmentEntity create(EntityType<? extends SpaceShipmentEntity> type, Level level) {
        return new SpaceShipmentEntityImpl(type, level);
    }
}
