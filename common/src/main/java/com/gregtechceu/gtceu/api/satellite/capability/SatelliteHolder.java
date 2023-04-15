package com.gregtechceu.gtceu.api.satellite.capability;

import com.gregtechceu.gtceu.api.capability.ISatelliteHolder;
import com.gregtechceu.gtceu.api.satellite.Satellite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SatelliteHolder implements ISatelliteHolder {
    private final List<Satellite> satellites = new ArrayList<>();
    private final Level level;

    public SatelliteHolder(Level level) {
        this.level = level;
    }

    @Override
    public List<Satellite> getAllSatellites() {
        return null;
    }

    @Override
    public Satellite getClosestSatellite(Vec3 position) {
        return satellites.stream().sorted(Comparator.comparingDouble(obj -> obj.getData().locationInWorld().distToCenterSqr(position))).findFirst().orElse(null);
    }

    @Override
    public boolean addSatellite(Satellite satellite, Level level) {
        return satellites.add(satellite);
    }

    @Override
    public boolean destroySatellite(Satellite satellite, Level level) {
        return false;
    }

    public ListTag serializeNBT() {
        ListTag tag = new ListTag();
        for (Satellite satellite : satellites) {
            tag.add(satellite.serializeNBT());
        }
        return tag;
    }

    public void deserializeNBT(ListTag arg, Level level) {
        for (Tag tag : arg) {
            satellites.add(Satellite.deserializeNBT((CompoundTag) tag, level));
        }
    }
}
