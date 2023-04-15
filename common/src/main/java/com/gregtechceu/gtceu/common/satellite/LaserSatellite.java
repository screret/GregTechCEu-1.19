package com.gregtechceu.gtceu.common.satellite;

import com.gregtechceu.gtceu.api.satellite.Satellite;
import com.gregtechceu.gtceu.api.satellite.SatelliteType;
import com.gregtechceu.gtceu.common.satellite.data.SatelliteData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;

public class LaserSatellite extends Satellite {
    private int currentMinedY;
    private boolean isMining = false;

    public LaserSatellite(SatelliteType<?> type, SatelliteData data, ResourceKey<Level> level) {
        super(type, data, level);
    }

    @Override
    public void tickSatellite(Level level) {
        if (isMining) {
            int x = this.data.locationInWorld().getX();
            int z = this.data.locationInWorld().getZ();
            for (int i = x - 1; i < x + 1; ++x) {
                for (int j = z - 1; j < z + 1; ++z) {
                    level.setBlock(new BlockPos(i, currentMinedY, j), Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }
    }

    @Override
    public boolean runSatelliteFunction(Level level) {
        currentMinedY = level.getHeight(Heightmap.Types.WORLD_SURFACE, this.data.locationInWorld().getX(), this.data.locationInWorld().getZ());

        return true;
    }

    @Override
    public CompoundTag serializeExtraData() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("currentY", currentMinedY);
        tag.putBoolean("mining", isMining);
        return tag;
    }

    @Override
    public void deserializeExtraData(Tag tag, Level level) {
        CompoundTag compound = (CompoundTag) tag;
        this.isMining = compound.getBoolean("mining");
        this.currentMinedY = compound.getInt("currentY");
    }
}
