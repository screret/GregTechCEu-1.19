package com.gregtechceu.gtceu.api.satellite.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.phys.Vec2;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote SatelliteData
 */
public record SatelliteData(Vec2 locationInWorld, int range) {


    public SatelliteData copy() {
        return new SatelliteData(new Vec2(this.locationInWorld.x, this.locationInWorld.y), this.range);
    }

    public static SatelliteData DEFAULT = new SatelliteData(Vec2.ZERO, 0);

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        CompoundTag pos = new CompoundTag();
        pos.putFloat("x", this.locationInWorld.x);
        pos.putFloat("y", this.locationInWorld.y);
        tag.put("pos", pos);

        tag.putInt("range", range);
        return tag;
    }

    public static SatelliteData deserializeNBT(CompoundTag nbt) {
        CompoundTag pos = nbt.getCompound("pos");
        var locationInWorld = new Vec2(pos.getFloat("x"), pos.getFloat("y"));
        int range = nbt.getInt("range");
        return new SatelliteData(locationInWorld, range);
    }
}
