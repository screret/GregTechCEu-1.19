package com.gregtechceu.gtceu.common.machine.electric;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.api.machine.TieredEnergyMachine;
import com.gregtechceu.gtceu.api.machine.WorkableTieredMachine;
import com.gregtechceu.gtceu.common.entity.data.EntityOxygenSystem;
import com.gregtechceu.gtceu.utils.FloodFiller3D;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import com.lowdragmc.lowdraglib.utils.Vector3;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.Int2LongFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.material.Fluids;

import java.util.Set;

public class OxygenSpreaderMachine extends SimpleTieredMachine {

    public OxygenSpreaderMachine(IMachineBlockEntity holder, int tier, Int2LongFunction tankScalingFunction, Object... args) {
        super(holder, tier, tankScalingFunction, args);
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        runAlgorithm();
    }

    public boolean canDistribute(int oxygenBlocks) {
        return ((FluidStack)recipeLogic.lastRecipe.getInputContents(FluidRecipeCapability.CAP).get(0).content).getAmount() / 1000 >= oxygenBlocks;
    }

    public void runAlgorithm() {
        Set<BlockPos> positions = FloodFiller3D.run(getLevel(), getPos());

        if (this.canDistribute(positions.size())) {
            EntityOxygenSystem.setEntry(this.getLevel(), this.getPos(), positions);
        } else if (!getLevel().isClientSide()) {
            EntityOxygenSystem.removeEntry(this.getLevel(), this.getPos());
        }

        this.spawnParticles(positions);
    }

    // Spawn the bubble particles in each oxygenated position. The "show" button must be clicked in the oxygen distributor GUI in order to work.
    public void spawnParticles(Set<BlockPos> positions) {
        if (!getLevel().isClientSide()) {
            for (BlockPos pos : positions) {
                this.getLevel().addParticle(new DustParticleOptions(new Vector3f(1, 1, 1), 1), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.5, 0.5, 0.5);
            }
        }
    }
}
