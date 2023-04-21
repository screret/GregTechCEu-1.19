package com.gregtechceu.gtceu.common.data.forge;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.common.data.GTBiomes;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GTBiomesImpl {
    private static final DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(ForgeRegistries.BIOMES, GTCEu.MOD_ID);

    public static void register(IEventBus bus) {
        BIOME_REGISTER.register(bus);
    }

    public static void init() {
        BIOME_REGISTER.register(GTBiomes.SPACE.location().getPath(), GTBiomes::space);
    }
}
