package com.gregtechceu.gtceu.core.mixins;

import com.gregtechceu.gtceu.common.worldgen.voidore.VoidOreSavedData;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {

    @Inject(method = "applyBiomeDecoration", at = @At("HEAD"))
    private void gtceu$applyBiomeDecoration$addVoidOres(WorldGenLevel level, ChunkAccess chunk, StructureManager structureManager, CallbackInfo ci) {
        //var savedData = VoidOreSavedData.getOrCreate(level.getLevel());
        //savedData.generate(chunk.getPos(), level.getRandom());
    }
}
