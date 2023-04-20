package com.gregtechceu.gtceu.api.space.station.capability;

import com.gregtechceu.gtceu.api.capability.ISpaceStationHolder;
import com.gregtechceu.gtceu.api.space.satellite.Satellite;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SpaceStationHolder implements ISpaceStationHolder {
    private final Int2ObjectMap<Vec2> stations = new Int2ObjectLinkedOpenHashMap<>(1);
    private final ServerLevel level;

    public SpaceStationHolder(ServerLevel level) {
        this.level = level;
    }

    @Override
    public Int2ObjectMap<Vec2> getStations() {
        return stations;
    }

    @Override
    public int getClosestStationId(Vec2 position) {
        var result = stations.int2ObjectEntrySet().stream().min(Comparator.comparingDouble(obj -> obj.getValue().distanceToSqr(position))).orElse(null);
        return result == null ? -1 : result.getIntKey();
    }

    @Override
    public Vec2 getStationPos(int id) {
        return stations.get(id);
    }

    @Nullable
    @Override
    public List<Integer> getStationsNearPos(Vec2 position, int range) {
        return stations.int2ObjectEntrySet().stream().filter(obj -> obj.getValue().distanceToSqr(position) <= range * range).sorted(Comparator.comparingDouble(obj -> obj.getValue().distanceToSqr(position))).map(Int2ObjectMap.Entry::getIntKey).collect(Collectors.toList());
    }

    @Override
    public void addStation(int stationId, Vec2 pos) {
        stations.put(stationId, pos);
    }

    @Override
    public void destroyStation(int id) {
        stations.remove(id);
    }

    @Override
    public int getFreeStationId() {
        return stations.size();
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        for (Int2ObjectMap.Entry<Vec2> station : stations.int2ObjectEntrySet()) {
            CompoundTag pos = new CompoundTag();
            pos.putInt("x", (int) station.getValue().x);
            pos.putInt("z", (int) station.getValue().y);
            tag.put(Integer.toString(station.getIntKey()), pos);
        }
        return tag;
    }

    public void deserializeNBT(CompoundTag arg) {
        for (String name : arg.getAllKeys()) {
            CompoundTag tag = arg.getCompound(name);
            Vec2 pos = new Vec2(tag.getInt("x"), tag.getInt("z"));
            stations.put(Integer.valueOf(name).intValue(), pos);
        }
    }
}
