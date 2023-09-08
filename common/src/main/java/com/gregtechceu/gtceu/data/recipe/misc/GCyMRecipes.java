package com.gregtechceu.gtceu.data.recipe.misc;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.AlloyBlastProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import net.minecraft.data.recipes.FinishedRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GCyMBlocks.*;
import static com.gregtechceu.gtceu.common.data.GCyMMachines.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.MIXER_RECIPES;

public class GCyMRecipes {

    private GCyMRecipes() {}

    public static void init(Consumer<FinishedRecipe> provider) {
        registerManualRecipes(provider);
        registerMachineRecipes(provider);
    }

    private static void registerManualRecipes(Consumer<FinishedRecipe> provider) {
        registerPartsRecipes(provider);
        registerMultiblockControllerRecipes(provider);
    }

    private static void registerMultiblockControllerRecipes(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_macerator", LARGE_MACERATION_TOWER.asStack(), "PCP", "BXB", "MKM", 'C', CustomTags.IV_CIRCUITS, 'P', ChemicalHelper.get(TagPrefix.plate, GTMaterials.TungstenCarbide), 'B', GTItems.ELECTRIC_PISTON_IV.asStack(), 'M', GTItems.ELECTRIC_MOTOR_IV.asStack(), 'X', GTMachines.MACERATOR[GTValues.IV - 1].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_arc_smelter", LARGE_ARC_SMELTER.asStack(), "KDK", "CXC", "PPP", 'C', CustomTags.IV_CIRCUITS, 'P', ChemicalHelper.get(TagPrefix.plate, GTMaterials.TantalumCarbide), 'X', GTMachines.ARC_FURNACE[GTValues.IV - 1].asStack(), 'D',ChemicalHelper.get(TagPrefix.dust, GTMaterials.Graphite) ,'K', new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_ore_washer", LARGE_ORE_WASHING_PLANT.asStack(), "PGP", "CXC", "MKM", 'C', CustomTags.IV_CIRCUITS, 'G',GTBlocks.CASING_TEMPERED_GLASS.asStack() ,'P', GTItems.ELECTRIC_PUMP_IV.asStack(), 'M', GTItems.CONVEYOR_MODULE_IV.asStack(), 'X', GTMachines.ORE_WASHER[GTValues.IV - 1].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_sifter", LARGE_SIFTING_FUNNEL.asStack(), "PCP", "EXE", "PKP", 'C', CustomTags.IV_CIRCUITS, 'P', ChemicalHelper.get(TagPrefix.plate, GTMaterials.HSLASteel), 'E', GTItems.ELECTRIC_PISTON_IV.asStack(), 'X', GTMachines.SIFTER[GTValues.IV - 1].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_engraver", LARGE_ENGRAVING_LASER.asStack(), "ICI", "EXE", "PKP", 'C', CustomTags.IV_CIRCUITS, 'P', ChemicalHelper.get(TagPrefix.plateDouble, GTMaterials.TantalumCarbide),'I',GTItems.EMITTER_IV.asStack() ,'E', GTItems.ELECTRIC_PISTON_IV.asStack(), 'X', GTMachines.LASER_ENGRAVER[GTValues.IV - 1].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_packer", LARGE_PACKER.asStack(), "RCR", "PXP", "KPK", 'C', CustomTags.EV_CIRCUITS, 'P', ChemicalHelper.get(TagPrefix.plate, GTMaterials.HSLASteel),'R',GTItems.ROBOT_ARM_HV.asStack() ,'K', GTItems.CONVEYOR_MODULE_HV.asStack(), 'X', GTMachines.PACKER[GTValues.HV - 1].asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_mixer", LARGE_MIXER.asStack(), "FCF", "RXR", "MKM", 'C', CustomTags.IV_CIRCUITS, 'F', ChemicalHelper.get(TagPrefix.pipeNormalFluid, GTMaterials.Polybenzimidazole),'R',ChemicalHelper.get(TagPrefix.rotor, GTMaterials.Osmiridium) ,'M', GTItems.ELECTRIC_MOTOR_IV.asStack(), 'X', GTMachines.MIXER[GTValues.IV - 1].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_centrifuge", LARGE_CENTRIFUGE.asStack(), "SFS", "CXC", "MKM", 'C', CustomTags.IV_CIRCUITS, 'F', ChemicalHelper.get(TagPrefix.pipeHugeFluid, GTMaterials.StainlessSteel),'S',ChemicalHelper.get(TagPrefix.spring, GTMaterials.MolybdenumDisilicide) ,'M', GTItems.ELECTRIC_MOTOR_IV.asStack(), 'X', GTMachines.CENTRIFUGE[GTValues.IV - 1].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_thermal_centrifuge", LARGE_THERMAL_CENTRIFUGE.asStack(), "SFS", "CXC", "MKM", 'C', CustomTags.IV_CIRCUITS, 'F', ChemicalHelper.get(TagPrefix.pipeHugeFluid, GTMaterials.StainlessSteel),'S',ChemicalHelper.get(TagPrefix.spring, GTMaterials.MolybdenumDisilicide) ,'M', GTItems.ELECTRIC_MOTOR_IV.asStack(), 'X', GTMachines.THERMAL_CENTRIFUGE[GTValues.IV - 1].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_assembler", LARGE_ASSEMBLER.asStack(), "RKR", "CXC", "MKM", 'C', CustomTags.IV_CIRCUITS, 'R', GTItems.ROBOT_ARM_IV.asStack() ,'M', GTItems.CONVEYOR_MODULE_IV.asStack(), 'X', GTMachines.ASSEMBLER[GTValues.IV - 1].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_circuit_assembler", LARGE_CIRCUIT_ASSEMBLER.asStack(), "RKR", "CXC", "MKM", 'C', CustomTags.IV_CIRCUITS, 'R', GTItems.ROBOT_ARM_IV.asStack() ,'M', GTItems.CONVEYOR_MODULE_IV.asStack(), 'X', GTMachines.CIRCUIT_ASSEMBLER[GTValues.IV - 1].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_electrolyzer", LARGE_ELECTROLYZER.asStack(), "PCP", "WXW", "PKP", 'C', CustomTags.IV_CIRCUITS, 'P', ChemicalHelper.get(TagPrefix.plate, GTMaterials.BlackSteel) ,'W', ChemicalHelper.get(TagPrefix.wireGtQuadruple, GTMaterials.Osmium), 'X', GTMachines.ELECTROLYZER[GTValues.IV - 1].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "blast_alloy_smelter", BLAST_ALLOY_SMELTER.asStack(), "TCT", "WXW", "TCT", 'C', CustomTags.EV_CIRCUITS, 'T', ChemicalHelper.get(TagPrefix.plate, TantalumCarbide) ,'W', ChemicalHelper.get(TagPrefix.wireGtQuadruple, GTMaterials.Osmium), 'X', GTMachines.ALLOY_SMELTER[GTValues.EV - 1].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, Aluminium));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "mega_blast_furnace", MEGA_BLAST_FURNACE.asStack(),"PCP", "FSF", "DWD", 'C', CustomTags.ZPM_CIRCUITS,'S', GTMachines.ELECTRIC_BLAST_FURNACE.asStack(), 'F', GTItems.FIELD_GENERATOR_ZPM.asStack(), 'P', new UnificationEntry(spring, Naquadah), 'D', new UnificationEntry(plateDense, NaquadahAlloy), 'W', new UnificationEntry(wireGtQuadruple, RutheniumTriniumAmericiumNeutronate));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "mega_vacuum_freezer", MEGA_VACUUM_FREEZER.asStack(),  "PCP", "FSF", "DWD", 'C', CustomTags.ZPM_CIRCUITS, 'S', GTMachines.VACUUM_FREEZER.asStack(), 'F', GTItems.FIELD_GENERATOR_ZPM.asStack(), 'P', new UnificationEntry(pipeNormalFluid, NiobiumTitanium), 'D', new UnificationEntry(plateDense, RhodiumPlatedPalladium), 'W', new UnificationEntry(wireGtQuadruple, RutheniumTriniumAmericiumNeutronate));
    }

    private static void registerPartsRecipes(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, "crushing_wheels", CRUSHING_WHEELS.asStack(2), "TTT", "UCU","UMU", 'T', ChemicalHelper.get(gearSmall,TungstenCarbide), 'U', ChemicalHelper.get(gear, Ultimet), 'C', CASING_SECURE_MACERATION.asStack(), 'M', GTItems.ELECTRIC_MOTOR_IV.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, "slicing_blades", SLICING_BLADES.asStack(2), "PPP", "UCU","UMU", 'P', ChemicalHelper.get(plate,TungstenCarbide), 'U', ChemicalHelper.get(gear, Ultimet), 'C', CASING_SHOCK_PROOF.asStack(), 'M', GTItems.ELECTRIC_MOTOR_IV.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, "electrolytic_cell", ELECTROLYTIC_CELL.asStack(2), "WWW", "WCW","ZKZ", 'W', ChemicalHelper.get(wireGtDouble,Platinum), 'Z', CustomTags.IV_CIRCUITS, 'C', CASING_NONCONDUCTING.asStack(), 'K', ChemicalHelper.get(cableGtSingle,Tungsten));
        VanillaRecipeHelper.addShapedRecipe(provider, "heat_vent", HEAT_VENT.asStack(2), "PDP", "RLR","PDP", 'P', ChemicalHelper.get(plate,TantalumCarbide), 'D', ChemicalHelper.get(plateDouble,MolybdenumDisilicide), 'R', ChemicalHelper.get(rotor,Titanium), 'L', ChemicalHelper.get(rodLong,MolybdenumDisilicide));
    }

    private static void registerMachineRecipes(Consumer<FinishedRecipe> provider) {
        registerAssemblerRecipes(provider);
        registerMixerRecipes(provider);
        registerBlastAlloyRecipes(provider);
    }

    private static void registerAssemblerRecipes(Consumer<FinishedRecipe> provider){
        ASSEMBLER_RECIPES.recipeBuilder("crushing_wheels")
                .inputItems(ChemicalHelper.get(gearSmall,TungstenCarbide,2))
                .inputItems(ChemicalHelper.get(gear,Ultimet,3))
                .inputItems(CASING_SECURE_MACERATION.asStack())
                .inputItems(GTItems.ELECTRIC_MOTOR_IV.asStack())
                .outputItems(CRUSHING_WHEELS.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("slicing_blades")
                .inputItems(ChemicalHelper.get(plate,TungstenCarbide,2))
                .inputItems(ChemicalHelper.get(gear,Ultimet,3))
                .inputItems(CASING_SHOCK_PROOF.asStack())
                .inputItems(GTItems.ELECTRIC_MOTOR_IV.asStack())
                .outputItems(SLICING_BLADES.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("electrolytic_cell")
                .inputItems(ChemicalHelper.get(wireGtDouble,Platinum,4))
                .inputItems(ChemicalHelper.get(cableGtSingle,Tungsten,1))
                .inputItems(CASING_NONCONDUCTING.asStack())
                .inputItems(CustomTags.IV_CIRCUITS)
                .outputItems(ELECTROLYTIC_CELL.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("mds_coil_block")
                .inputItems(ChemicalHelper.get(ring,MolybdenumDisilicide,32))
                .inputItems(ChemicalHelper.get(foil,Graphene,16))
                .inputFluids(HSLASteel.getFluid(144))
                .outputItems(MOLYBDENUM_DISILICIDE_COIL_BLOCK.asStack(1))
                .duration(500).EUt(1920)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("heat_vent")
                .inputItems(ChemicalHelper.get(plate,TantalumCarbide,3))
                .inputItems(ChemicalHelper.get(plateDouble,MolybdenumDisilicide,2))
                .inputItems(ChemicalHelper.get(rotor,Titanium,1))
                .inputItems(ChemicalHelper.get(rodLong,MolybdenumDisilicide,1))
                .outputItems(HEAT_VENT.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_hsla_nonconducting")
                .inputItems(plate, HSLASteel, 6).inputItems(frameGt, HSLASteel).circuitMeta(6)
                .outputItems(CASING_NONCONDUCTING.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_incoloy_vibration_safe")
                .inputItems(plate, IncoloyMA956, 6).inputItems(frameGt, IncoloyMA956).circuitMeta(6)
                .outputItems(CASING_VIBRATION_SAFE.asStack(2))
                .EUt(16).duration(50)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_watertight")
                .inputItems(plate, WatertightSteel, 6).inputItems(frameGt, WatertightSteel).circuitMeta(6)
                .outputItems(CASING_WATERTIGHT.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_secure_maceration")
                .inputItems(plate, Zeron100, 6).inputItems(frameGt, Titanium).circuitMeta(6)
                .outputItems(CASING_SECURE_MACERATION.asStack(2))
                .EUt(16).duration(50)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_high_temperature_smelting")
                .inputItems(plate, TitaniumCarbide, 4).inputItems(plate, HSLASteel, 2).inputItems(frameGt, TungstenCarbide).circuitMeta(6)
                .outputItems(CASING_HIGH_TEMPERATURE_SMELTING.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_laser_safe_engraving")
                .inputItems(plate, TitaniumTungstenCarbide, 6).inputItems(frameGt, Titanium).circuitMeta(6)
                .outputItems(CASING_LASER_SAFE_ENGRAVING.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_large_scale_assembling")
                .inputItems(plate, Stellite100, 6).inputItems(frameGt, Tungsten).circuitMeta(6)
                .outputItems(CASING_LARGE_SCALE_ASSEMBLING.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_shock_proof")
                .inputItems(plate, HastelloyC276, 6).inputItems(frameGt, HastelloyC276).circuitMeta(6)
                .outputItems(CASING_SHOCK_PROOF.asStack(2))
                .duration(50).EUt(16)
                .save(provider);
    }

    private static void registerMixerRecipes(Consumer<FinishedRecipe> provider){
        MIXER_RECIPES.recipeBuilder("tantalum_carbide")
                .inputItems(dust, Tantalum)
                .inputItems(dust, Carbon)
                .outputItems(dust, TantalumCarbide, 2)
                .duration(150).EUt(VA[IV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("hsla_steel")
                .inputItems(dust, Invar, 2)
                .inputItems(dust, Vanadium)
                .inputItems(dust, Titanium)
                .inputItems(dust, Molybdenum)
                .outputItems(dust, HSLASteel, 5)
                .duration(140).EUt(VA[HV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("incoloy_ma_956")
                .inputItems(dust, VanadiumSteel, 4)
                .inputItems(dust, Manganese, 2)
                .inputItems(dust, Aluminium, 5)
                .inputItems(dust, Yttrium, 2)
                .outputItems(dust, IncoloyMA956, 13)
                .duration(200).EUt(VA[IV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("watertight_steel")
                .inputItems(dust, Iron, 7)
                .inputItems(dust, Aluminium, 4)
                .inputItems(dust, Nickel, 2)
                .inputItems(dust, Chromium)
                .inputItems(dust, Sulfur)
                .outputItems(dust, HSLASteel, 15)
                .duration(220).EUt(VA[IV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("molybdenum_disilicide")
                .inputItems(dust, Molybdenum)
                .inputItems(dust, Silicon, 2)
                .outputItems(dust, MolybdenumDisilicide, 3)
                .duration(180).EUt(VA[EV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("hastelloy_x")
                .inputItems(dust, Nickel, 8)
                .inputItems(dust, Iron, 3)
                .inputItems(dust, Tungsten, 4)
                .inputItems(dust, Molybdenum, 2)
                .inputItems(dust, Chromium)
                .inputItems(dust, Niobium)
                .outputItems(dust, HastelloyX, 19)
                .duration(210).EUt(VA[IV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("maraging_steel_300")
                .inputItems(dust, Iron, 16)
                .inputItems(dust, Titanium)
                .inputItems(dust, Aluminium)
                .inputItems(dust, Nickel, 4)
                .inputItems(dust, Cobalt, 2)
                .outputItems(dust, MaragingSteel300, 24)
                .duration(230).EUt(VA[IV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("stellite_100")
                .inputItems(dust, Iron, 4)
                .inputItems(dust, Chromium, 3)
                .inputItems(dust, Tungsten, 2)
                .inputItems(dust, Molybdenum)
                .outputItems(dust, Stellite100, 10)
                .duration(200).EUt(VA[IV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("titanium_carbide")
                .inputItems(dust, Titanium)
                .inputItems(dust, Carbon)
                .outputItems(dust, TitaniumCarbide, 2)
                .duration(160).EUt(VA[EV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("titanium_tungsten_carbide")
                .inputItems(dust, TungstenCarbide)
                .inputItems(dust, TitaniumCarbide, 2)
                .outputItems(dust, TitaniumTungstenCarbide, 3)
                .duration(180).EUt(VA[IV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("hastelloy_c_276")
                .inputItems(dust, Nickel, 12)
                .inputItems(dust, Molybdenum, 8)
                .inputItems(dust, Chromium, 7)
                .inputItems(dust, Tungsten, 1)
                .inputItems(dust, Cobalt, 1)
                .inputItems(dust, Copper, 1)
                .outputItems(dust, HastelloyC276, 30)
                .duration(240).EUt(VA[IV])
                .save(provider);
    }

    private static void registerBlastAlloyRecipes(Consumer<FinishedRecipe> provider) {
        ingot.executeHandler(PropertyKey.ALLOY_BLAST, (tagPrefix, material, property) -> generateAlloyBlastRecipes(tagPrefix, material, property, provider));
    }

    /**
     * Generates alloy blast recipes for a material
     *
     * @param material the material to generate for
     * @param property the blast property of the material
     */
    public static void generateAlloyBlastRecipes(@Nullable TagPrefix unused, @Nonnull Material material,
                                                 @Nonnull AlloyBlastProperty property,
                                                 @Nonnull Consumer<FinishedRecipe> provider) {
        if (material.hasProperty(PropertyKey.BLAST)) {
            property.getRecipeProducer().produce(material, material.getProperty(PropertyKey.BLAST), provider);
        }
    }
}