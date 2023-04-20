package com.gregtechceu.gtceu.common.data;

import com.gregtechceu.gtceu.common.entity.SpaceShuttleEntity;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.world.entity.MobCategory;

import static com.gregtechceu.gtceu.api.registry.GTRegistries.REGISTRATE;

public class GTEntityTypes {
    public static final EntityEntry<SpaceShuttleEntity> SPACE_SHIPMENT = REGISTRATE.entity("space_shipment", SpaceShuttleEntity::create, MobCategory.MISC)
            .onRegister(SpaceShuttleEntity::onEntityRegister)
            .register();

    public static void init() {

    }
}
