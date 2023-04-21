package com.gregtechceu.gtceu.forge;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.capability.forge.compat.EUToFEProvider;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.api.item.forge.ComponentItemImpl;
import com.gregtechceu.gtceu.api.item.forge.DrumMachineItemImpl;
import com.gregtechceu.gtceu.api.machine.feature.IInteractedMachine;
import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.space.satellite.capability.SatelliteHolder;
import com.gregtechceu.gtceu.api.space.station.capability.SpaceStationHolder;
import com.gregtechceu.gtceu.common.ServerCommands;
import com.gregtechceu.gtceu.common.data.GTDimensionTypes;
import com.gregtechceu.gtceu.data.loader.forge.OreDataLoaderImpl;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author KilaBash
 * @date 2022/8/27
 * @implNote ForgeCommonEventListener
 */
@Mod.EventBusSubscriber(modid = GTCEu.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEventListener {

    @SubscribeEvent
    public static void registerItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof ComponentItemImpl componentItem) {

            final ItemStack itemStack = event.getObject();
            event.addCapability(GTCEu.id("capability"), new ICapabilityProvider() {
                @NotNull
                @Override
                public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                    return componentItem.getCapability(itemStack, cap);
                }
            });
        }
        if (event.getObject().getItem() instanceof DrumMachineItemImpl drumMachineItem) {
            final ItemStack itemStack = event.getObject();
            event.addCapability(GTCEu.id("fluid"), new ICapabilityProvider() {
                @Override
                public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
                    return drumMachineItem.getCapability(itemStack, capability);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        var blockState = event.getLevel().getBlockState(event.getPos());
        if (blockState.hasBlockEntity() && blockState.getBlock() instanceof MetaMachineBlock block
                && block.getMachine(event.getLevel(), event.getPos()) instanceof IInteractedMachine machine) {
            if (machine.onLeftClick(event.getEntity(), event.getLevel(), event.getHand(), event.getPos(), event.getFace())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        ServerCommands.createServerCommands().forEach(event.getDispatcher()::register);
    }

    @SubscribeEvent
    public static void registerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new OreDataLoaderImpl());
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
        event.addCapability(GTCEu.id("fe_capability"), new EUToFEProvider(event.getObject()));
    }

    @SubscribeEvent
    public static void registerLevelCapabilities(AttachCapabilitiesEvent<Level> event) {
        if (!event.getObject().dimensionType().hasCeiling() && !event.getObject().isClientSide) {
            var satellites = new SatelliteHolder((ServerLevel) event.getObject());
            event.addCapability(GTCEu.id("satellites"), new ICapabilitySerializable<ListTag>() {
                @Override
                public ListTag serializeNBT() {
                    return satellites.serializeNBT();
                }

                @Override
                public void deserializeNBT(ListTag arg) {
                    satellites.deserializeNBT(arg, event.getObject());
                }

                @Override
                public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
                    return GTCapability.CAPABILITY_SATELLITES.orEmpty(capability, LazyOptional.of(() -> satellites));
                }
            });
        }
        if (event.getObject().dimensionTypeId() == GTDimensionTypes.SPACE_DIMENSION) {
            var stations = new SpaceStationHolder((ServerLevel) event.getObject());
            event.addCapability(GTCEu.id("space_stations"), new ICapabilitySerializable<CompoundTag>() {
                @Override
                public CompoundTag serializeNBT() {
                    return stations.serializeNBT();
                }

                @Override
                public void deserializeNBT(CompoundTag arg) {
                    stations.deserializeNBT(arg);
                }

                @Override
                public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
                    return GTCapability.CAPABILITY_SPACE_STATIONS.orEmpty(capability, LazyOptional.of(() -> stations));
                }
            });
        }
    }

    @SubscribeEvent
    public static void serverTick(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.SERVER && !event.level.dimensionType().hasCeiling()) {
            var sat = GTCapabilityHelper.getSatellites(event.level);
            if (sat != null) sat.tickSatellites();
        }
    }

}
