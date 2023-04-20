package com.gregtechceu.gtceu.common.entity.forge;

import com.gregtechceu.gtceu.common.entity.SpaceShuttleEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpaceShuttleEntityImpl extends SpaceShuttleEntity {
    public SpaceShuttleEntityImpl(EntityType<? extends SpaceShuttleEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }

    public static void onEntityRegister(EntityType<SpaceShuttleEntity> entityType) {
    }

    public static SpaceShuttleEntity create(EntityType<? extends SpaceShuttleEntity> type, Level level) {
        return new SpaceShuttleEntityImpl(type, level);
    }
}
