package com.gregtechceu.gtceu.api.satellite;

import com.gregtechceu.gtceu.common.satellite.data.SatelliteData;
import lombok.Getter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote SatelliteType
 */
public class SatelliteType<T extends Satellite> {
    @Getter
    private final T defaultInstance;
    @Getter
    private final SatelliteFactory<T> factory;

    public SatelliteType(SatelliteFactory<T> factory) {
        this.factory = factory;
        this.defaultInstance = factory.create(this, SatelliteData.DEFAULT, Level.OVERWORLD);
    }

    @FunctionalInterface
    public interface SatelliteFactory<T extends Satellite> {
        T create(SatelliteType<?> type, SatelliteData data, ResourceKey<Level> level);
    }

}
