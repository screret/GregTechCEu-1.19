package com.gregtechceu.gtceu.api.satellite;

import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.satellite.data.SatelliteData;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote Satellite
 */
public abstract class Satellite {
    //public static EmptySatellite EMPTY = GTSatellites.EMPTY.getDefaultInstance();

    @Getter
    protected SatelliteData data;
    @Getter
    protected ResourceKey<Level> level;
    @Getter
    protected final SatelliteType<?> type;

    public Satellite(SatelliteType<?> type, SatelliteData data, ResourceKey<Level> level) {
        this.type = type;
        this.data = data;
        this.level = level;
    }

    public Satellite copy() {
        Satellite copy = this.type.getDefaultInstance();
        copy.data = this.data.copy();
        copy.level = this.level;
        return copy;
    }

    public abstract void tickSatellite(Level level);
    public abstract boolean runSatelliteFunction(Level level);

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", GTRegistries.SATELLITES.getKey(type).toString());

        tag.put("data", this.data.serializeNBT());

        CompoundTag levelTag = new CompoundTag();
        levelTag.putString("registry", this.level.registry().toString());
        levelTag.putString("location", this.level.location().toString());
        tag.put("level", levelTag);

        Tag extra = serializeExtraData();
        if (extra != null) tag.put("extra", extra);
        return tag;
    }
    @Nullable
    public abstract Tag serializeExtraData();

    public static Satellite deserializeNBT(CompoundTag nbt, Level level) {
        SatelliteType<?> type = GTRegistries.SATELLITES.get(new ResourceLocation(nbt.getString("id")));
        SatelliteType.SatelliteFactory<?> satellite = type.getFactory();

        SatelliteData data = SatelliteData.deserializeNBT(nbt.getCompound("data"));

        CompoundTag levelTag = nbt.getCompound("level");
        ResourceKey<Level> levelResourceKey = ResourceKey.create(new ResourceLocation(levelTag.getString("registry")), new ResourceLocation(levelTag.getString("location")));

        Satellite sat = satellite.create(type, data, levelResourceKey);
        if (nbt.contains("extra")) sat.deserializeExtraData(nbt.get("extra"), level);
        return sat;
    }

    public abstract void deserializeExtraData(Tag tag, Level level);
}
