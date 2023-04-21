package com.gregtechceu.gtceu.common.machine.multiblock;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class StructurePackageScannerMachine extends WorkableMultiblockMachine implements IDisplayUIMachine {

    public StructurePackageScannerMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.setWorkingEnabled(false);
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        Direction facing = this.getFrontFacing();
        BlockPos start = this.getPos().offset(facing.getStepX(), 0, facing.getStepZ());
    }
}
