package com.gregtechceu.gtceu.data.recipe.misc;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.OXYGEN_SPREADER_RECIPES;

public class OxygenSpreaderRecipeLoader {

    public static void init(Consumer<FinishedRecipe> provider) {
        OXYGEN_SPREADER_RECIPES.recipeBuilder("oxygen_10l")
                .circuitMeta(1)
                .inputFluids(Oxygen.getFluid(750))
                .save(provider);

        OXYGEN_SPREADER_RECIPES.recipeBuilder("air_10l")
                .circuitMeta(1)
                .inputFluids(Air.getFluid(1000))
                .save(provider);

        OXYGEN_SPREADER_RECIPES.recipeBuilder("oxygen_20l")
                .circuitMeta(2)
                .inputFluids(Oxygen.getFluid(1500))
                .save(provider);

        OXYGEN_SPREADER_RECIPES.recipeBuilder("air_20l")
                .circuitMeta(2)
                .inputFluids(Air.getFluid(2000))
                .save(provider);

        OXYGEN_SPREADER_RECIPES.recipeBuilder("oxygen_50l")
                .circuitMeta(3)
                .inputFluids(Oxygen.getFluid(3750))
                .save(provider);

        OXYGEN_SPREADER_RECIPES.recipeBuilder("air_50l")
                .circuitMeta(3)
                .inputFluids(Air.getFluid(5000))
                .save(provider);

        OXYGEN_SPREADER_RECIPES.recipeBuilder("oxygen_100l")
                .circuitMeta(4)
                .inputFluids(Oxygen.getFluid(7500))
                .save(provider);

        OXYGEN_SPREADER_RECIPES.recipeBuilder("air_100l")
                .circuitMeta(4)
                .inputFluids(Air.getFluid(10000))
                .save(provider);

        OXYGEN_SPREADER_RECIPES.recipeBuilder("oxygen_1000l")
                .circuitMeta(5)
                .inputFluids(Oxygen.getFluid(75000))
                .save(provider);

        OXYGEN_SPREADER_RECIPES.recipeBuilder("air_1000l")
                .circuitMeta(5)
                .inputFluids(Air.getFluid(100000))
                .save(provider);

        OXYGEN_SPREADER_RECIPES.recipeBuilder("oxygen_10000l")
                .circuitMeta(6)
                .inputFluids(Oxygen.getFluid(750000))
                .save(provider);

        OXYGEN_SPREADER_RECIPES.recipeBuilder("air_10000l")
                .circuitMeta(6)
                .inputFluids(Air.getFluid(1000000))
                .save(provider);
    }
}