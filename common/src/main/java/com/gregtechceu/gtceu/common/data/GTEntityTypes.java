package com.gregtechceu.gtceu.common.data;

import com.gregtechceu.gtceu.common.entity.SpaceShipmentEntity;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.world.entity.MobCategory;

import static com.gregtechceu.gtceu.api.registry.GTRegistries.REGISTRATE;

public class GTEntityTypes {
    public static final EntityEntry<SpaceShipmentEntity> SPACE_SHIPMENT = REGISTRATE.entity("space_shipment", SpaceShipmentEntity::create, MobCategory.MISC)
            .onRegister(SpaceShipmentEntity::onEntityRegister)
            .register();

    public static void init() {

    }
}
