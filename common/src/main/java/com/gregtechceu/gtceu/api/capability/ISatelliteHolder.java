package com.gregtechceu.gtceu.api.capability;

import com.gregtechceu.gtceu.api.satellite.Satellite;
import com.gregtechceu.gtceu.api.satellite.SatelliteType;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nullable;
import java.util.List;

public interface ISatelliteHolder {

    /**
     * @return all satellites of this capability (= level)
     */
    List<Satellite> getSatellites();

    /**
     * ticks all satellites this capability has
     */
    void tickSatellites();

    /**
     * @param type satellite type
     * @return satellites of type {@code clazz}
     */
    @SuppressWarnings("unchecked")
    default <T extends Satellite> List<T> getSatellitesOfType(SatelliteType<T> type) {
        return (List<T>) getSatellites().stream().filter(obj -> obj.getType() == type).toList();
    }

    /**
     *
     * @param position the position from which distance is measured from
     * @return the closest satellite to this position, or null if none
     */
    @Nullable
    Satellite getClosestSatellite(Vec2 position);

    /**
     *
     * @param position position that is searched around
     * @param range range that is searched
     * @return all satellites in area, sorted by distance (smallest first)
     */
    @Nullable
    List<Satellite> getSatellitesNearPos(Vec2 position, int range);

    void addSatellite(Satellite satellite);

    void destroySatellite(Satellite satellite);
}
