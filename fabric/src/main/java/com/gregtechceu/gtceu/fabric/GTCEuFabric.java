package com.gregtechceu.gtceu.fabric;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.capability.fabric.compat.EUToREProvider;
import com.gregtechceu.gtceu.api.capability.fabric.GTCapability;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.fabric.GTCapability;
import com.gregtechceu.gtceu.api.machine.feature.IInteractedMachine;
import com.gregtechceu.gtceu.api.recipe.ingredient.fabric.SizedIngredientImpl;
import com.gregtechceu.gtceu.api.space.satellite.capability.SatelliteHolder;
import com.gregtechceu.gtceu.api.space.station.capability.SpaceStationHolder;
import com.gregtechceu.gtceu.common.ServerCommands;
import com.gregtechceu.gtceu.common.data.GTDimensionTypes;
import com.gregtechceu.gtceu.common.fabric.CommonProxyImpl;
import com.gregtechceu.gtceu.data.loader.fabric.OreDataLoaderImpl;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import team.reborn.energy.api.EnergyStorage;

public class GTCEuFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        GTCEu.init();
        CommonProxyImpl.init();
        CustomIngredientSerializer.register(SizedIngredientImpl.Serializer.INSTANCE);
        AttackBlockCallback.EVENT.register(((player, world, hand, pos, direction) -> {
            var blockState = world.getBlockState(pos);
            if (blockState.hasBlockEntity() && blockState.getBlock() instanceof MetaMachineBlock block
                    && block.getMachine(world, pos) instanceof IInteractedMachine machine) {
                if (machine.onLeftClick(player, world, hand, pos, direction)) {
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        }));
        // register server commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ServerCommands.createServerCommands().forEach(dispatcher::register));

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new OreDataLoaderImpl());

        if (GTCEu.isRebornEnergyLoaded()) {
            GTCapability.CAPABILITY_ENERGY.registerFallback(new EUToREProvider(EnergyStorage.SIDED::find));
        }


        // register satellite ticking
        ServerTickEvents.START_WORLD_TICK.register((serverLevel) -> {
            if (!serverLevel.dimensionType().hasCeiling()) {
                var sat = GTCapabilityHelper.getSatellites(serverLevel);
                if (sat != null) sat.tickSatellites();
            }
        });

        ServerWorldEvents.LOAD.register((server, world) -> {
            if (!world.dimensionType().hasCeiling()) {
                GTCapability.CAPABILITY_SATELLITES.register(((level, context) -> new SatelliteHolder((ServerLevel) level)), world);
            }
            if (world.dimensionTypeId() == GTDimensionTypes.SPACE) {
                GTCapability.CAPABILITY_SPACE_STATIONS.register(((level, context) -> new SpaceStationHolder((ServerLevel) level)), world);
            }
        });
    }
}
