package com.gregtechceu.gtceu.core.mixins;

import com.gregtechceu.gtceu.api.capability.IGpsTracked;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements IGpsTracked {
    private boolean gpsTracked;

    @Override
    public boolean isGpsTracked() {
        return gpsTracked;
    }

    @Override
    public void setGpsTracked(boolean tracked) {
        gpsTracked = tracked;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void gtceu$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean("gtceu:GpsTracked", gpsTracked);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void gtceu$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("gtceu:GpsTracked")) gpsTracked = tag.getBoolean("gtceu:GpsTracked");
    }
}
