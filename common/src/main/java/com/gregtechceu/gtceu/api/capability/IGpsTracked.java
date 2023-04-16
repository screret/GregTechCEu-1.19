package com.gregtechceu.gtceu.api.capability;

import net.minecraft.world.entity.LivingEntity;

public interface IGpsTracked {

    boolean isGpsTracked();

    void setGpsTracked(boolean tracked);
}
