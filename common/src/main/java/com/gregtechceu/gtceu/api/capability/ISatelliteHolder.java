package com.gregtechceu.gtceu.api.capability;

import com.gregtechceu.gtceu.api.satellite.Satellite;
import com.gregtechceu.gtceu.api.satellite.SatelliteType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public interface ISatelliteHolder {

    /**
     * @return all satellites
     */
    List<Satellite> getAllSatellites();

    /**
     * @param type satellite type
     * @return satellites of type {@code clazz}
     */
    @SuppressWarnings("unchecked")
    default <T extends Satellite> List<T> getSatellitesOfType(SatelliteType<T> type) {
        return (List<T>) getAllSatellites().stream().filter(obj -> obj.getType() == type).toList();
    }

    /**
     *
     * @param position the position from which distance is measured from
     * @return the closest satellite to this position
     */
    @Nullable
    Satellite getClosestSatellite(Vec3 position);

    boolean addSatellite(Satellite satellite, Level level);

    boolean destroySatellite(Satellite satellite, Level level);
}
