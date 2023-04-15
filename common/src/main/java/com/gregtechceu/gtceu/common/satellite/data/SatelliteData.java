package com.gregtechceu.gtceu.common.satellite.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote SatelliteData
 */
public record SatelliteData(BlockPos locationInWorld, int range) {


    public SatelliteData copy() {
        return new SatelliteData(new BlockPos(this.locationInWorld), this.range);
    }

    public static SatelliteData DEFAULT = new SatelliteData(new BlockPos(0, 0, 0), 0);

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("pos", BlockPos.CODEC.encode(locationInWorld, NbtOps.INSTANCE, new CompoundTag()).result().orElseThrow());
        tag.putInt("range", range);
        return tag;
    }

    public static SatelliteData deserializeNBT(CompoundTag nbt) {
        var locationInWorld = BlockPos.CODEC.decode(NbtOps.INSTANCE, nbt.getCompound("pos")).result().orElseThrow().getFirst();
        int range = nbt.getInt("range");
        return new SatelliteData(locationInWorld, range);
    }
}
