package com.gregtechceu.gtceu.core.mixins;

import com.gregtechceu.gtceu.api.capability.IGpsTracked;
import com.gregtechceu.gtceu.common.data.GTDimensionTypes;
import com.gregtechceu.gtceu.common.entity.data.EntityOxygenSystem;
import com.gregtechceu.gtceu.common.entity.data.EntityTemperatureSystem;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IGpsTracked {
    @Getter
    @Setter
    private boolean gpsTracked;

    private LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void gtceu$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean("GT.GpsTracked", gpsTracked);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void gtceu$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("GT.GpsTracked")) gpsTracked = tag.getBoolean("GT.GpsTracked");
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void gtceu$tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        Level level = entity.level;
        if (!level.isClientSide) {
            if (level.getGameTime() % 10 == 0) {
                if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) {
                    return;
                }

                EntityOxygenSystem.oxygenTick(entity, (ServerLevel) level);

                if (level.dimension() != GTDimensionTypes.SPACE_LEVEL) {
                    return;
                }

                EntityTemperatureSystem.temperatureTick(entity, (ServerLevel) level);
            }
        }
    }
}
