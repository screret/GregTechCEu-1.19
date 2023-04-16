package com.gregtechceu.gtceu.common.satellite;

import com.gregtechceu.gtceu.api.satellite.Satellite;
import com.gregtechceu.gtceu.api.satellite.SatelliteType;
import com.gregtechceu.gtceu.api.satellite.data.SatelliteData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class OreFinderSatellite extends Satellite {
    public OreFinderSatellite(SatelliteType<?> type, SatelliteData data, ResourceKey<Level> level) {
        super(type, data, level);
    }

    @Override
    public void tickSatellite(Level level) {

    }

    @Override
    public boolean runSatelliteFunction(Level level) {
        return false;
    }

    @Override
    public CompoundTag serializeExtraData() {
        return null;
    }

    @Override
    public void deserializeExtraData(Tag tag, Level level) {

    }
}
