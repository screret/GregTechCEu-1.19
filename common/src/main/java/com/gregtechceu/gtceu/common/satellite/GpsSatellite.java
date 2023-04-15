package com.gregtechceu.gtceu.common.satellite;

import com.gregtechceu.gtceu.api.satellite.Satellite;
import com.gregtechceu.gtceu.api.satellite.SatelliteType;
import com.gregtechceu.gtceu.common.satellite.data.SatelliteData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote GpsSatellite
 */
public class GpsSatellite extends Satellite {
    public static Map<Level, List<Entity>> trackedEntities = new LinkedHashMap<>();

    private List<Entity> lastTrackedEntities = new ArrayList<>();

    public GpsSatellite(SatelliteType<?> type, SatelliteData data, ResourceKey<Level> level) {
        super(type, data, level);
    }

    @Override
    public void tickSatellite(Level level) {
        BlockPos pos = this.data.locationInWorld();
        var list = level.getEntities(null, AABB.ofSize(new Vec3(pos.getX(), level.getSeaLevel(), pos.getZ()), this.data.range(), 100, this.data.range()));
        lastTrackedEntities.removeAll(list);
        var tracked = trackedEntities.get(level);
        tracked.removeAll(lastTrackedEntities);
        tracked.addAll(list);
        lastTrackedEntities = list;
    }

    @Override
    public boolean runSatelliteFunction(Level level) {
        return true;

    }

    @Nullable
    @Override
    public ListTag serializeExtraData() {
        ListTag tracked = new ListTag();
        for (Entity entity : lastTrackedEntities) {
            tracked.add(StringTag.valueOf(entity.getUUID().toString()));
        }
        return tracked;
    }

    @Override
    public void deserializeExtraData(Tag nbt, Level level) {
        if (level instanceof ServerLevel serverLevel) {
            ListTag list = (ListTag) nbt;
            for (Tag tag : list) {
                StringTag str = (StringTag) tag;
                UUID uuid = UUID.fromString(str.getAsString());
                Entity ent = serverLevel.getEntity(uuid);
                if (ent != null) {
                    lastTrackedEntities.add(ent);
                }
            }

        }
    }

}
