package com.gregtechceu.gtceu.data.recipe.misc;

import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.recipe.DimensionCondition;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.SPACE_SHUTTLE_RECIPES;

public class SpaceShuttleRecipeLoader {

    public static void init(Consumer<FinishedRecipe> provider) {
        SPACE_SHUTTLE_RECIPES.recipeBuilder("gps_satellite")
                .inputItems(GPS_SATELLITE.asStack(), SATELLITE_ROCKET.asStack())
                .inputFluids(RocketFuel.getFluid(16000))
                .addData("satellite_type", "gtceu:gps_satellite")
                .duration(36000).save(provider);

        SPACE_SHUTTLE_RECIPES.recipeBuilder("ore_finder_satellite")
                .inputItems(ORE_FINDER_SATELLITE.asStack(), SATELLITE_ROCKET.asStack())
                .inputFluids(RocketFuel.getFluid(16000))
                .addData("satellite_type", "gtceu:ore_finder_satellite")
                .duration(36000).save(provider);

        SPACE_SHUTTLE_RECIPES.recipeBuilder("laser_satellite")
                .inputItems(LASER_SATELLITE.asStack(), SATELLITE_ROCKET.asStack())
                .inputFluids(RocketFuel.getFluid(24000))
                .addData("satellite_type", "gtceu:laser_satellite")
                .duration(54000).save(provider);

        SPACE_SHUTTLE_RECIPES.recipeBuilder("dyson_swarm_satellite")
                .inputItems(DYSON_SWARM_SATELLITE.asStack(), SATELLITE_ROCKET.asStack())
                .inputFluids(RocketFuel.getFluid(32000))
                .addData("satellite_type", "gtceu:dyson_swarm_satellite")
                .duration(72000).save(provider);

        SPACE_SHUTTLE_RECIPES.recipeBuilder("manned_launch_station")
                .inputItems(MANNED_ROCKET.asStack())
                .inputFluids(RocketFuel.getFluid(24000))
                .addData("satellite_type", "gtceu:manned_rocket")
                .duration(36000).save(provider);
    }
}
