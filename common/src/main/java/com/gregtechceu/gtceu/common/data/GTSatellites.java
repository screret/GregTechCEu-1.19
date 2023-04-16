package com.gregtechceu.gtceu.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.satellite.Satellite;
import com.gregtechceu.gtceu.api.satellite.SatelliteType;
import com.gregtechceu.gtceu.common.satellite.DysonSwarmSatellite;
import com.gregtechceu.gtceu.common.satellite.EmptySatellite;
import com.gregtechceu.gtceu.common.satellite.GpsSatellite;
import com.gregtechceu.gtceu.common.satellite.LaserSatellite;
import net.minecraft.resources.ResourceLocation;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote GTSatellites
 */
public class GTSatellites {

    public static final SatelliteType<EmptySatellite> EMPTY = register("empty", new SatelliteType<>(EmptySatellite::new));
    public static final SatelliteType<GpsSatellite> GPS = register("gps", new SatelliteType<>(GpsSatellite::new));
    public static final SatelliteType<LaserSatellite> LASER = register("laser", new SatelliteType<>(LaserSatellite::new));
    public static final SatelliteType<DysonSwarmSatellite> DYSON_SWARM = register("dyson_swarm", new SatelliteType<>(DysonSwarmSatellite::new));


    public static  <T extends Satellite> SatelliteType<T> register(String name, SatelliteType<T> satellite) {
        ResourceLocation id = GTCEu.id(name);
        GTRegistries.SATELLITES.register(id, satellite);
        return satellite;
    }

    public static void init() {

    }
}
