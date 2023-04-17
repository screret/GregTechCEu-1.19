package com.gregtechceu.gtceu.api.satellite.capability;

import com.google.common.collect.Comparators;
import com.gregtechceu.gtceu.api.capability.ISatelliteHolder;
import com.gregtechceu.gtceu.api.satellite.Satellite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SatelliteHolder implements ISatelliteHolder {
    private final List<Satellite> satellites = new ArrayList<>();
    private final ServerLevel level;

    public SatelliteHolder(ServerLevel level) {
        this.level = level;
    }

    @Override
    public List<Satellite> getSatellites() {
        return satellites;
    }

    public void tickSatellites() {
        getSatellites().forEach(satellite -> satellite.tickSatellite(level));
    }

    @Override
    public Satellite getClosestSatellite(Vec2 position) {
        return satellites.stream().min(Comparator.comparingDouble(obj -> obj.getData().locationInWorld().distanceToSqr(position))).orElse(null);
    }

    @Nullable
    @Override
    public List<Satellite> getSatellitesNearPos(Vec2 position, int range) {
        return satellites.stream().filter(sat -> sat.getData().locationInWorld().distanceToSqr(position) <= range * range).sorted(Comparator.comparingDouble(sat -> sat.getData().locationInWorld().distanceToSqr(position))).collect(Collectors.toList());
    }

    @Override
    public void addSatellite(@Nullable Satellite satellite) {
        satellites.add(satellite);
    }

    @Override
    public void destroySatellite(Satellite satellite) {
        satellites.remove(satellite);
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
