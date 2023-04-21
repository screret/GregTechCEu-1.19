package com.gregtechceu.gtceu.common.data.fabric;

import com.gregtechceu.gtceu.common.data.GTBiomes;
import net.minecraft.data.BuiltinRegistries;

public class GTBiomesImpl {
    public static void init() {
        BuiltinRegistries.register(BuiltinRegistries.BIOME, GTBiomes.SPACE, GTBiomes.space());
    }
}
