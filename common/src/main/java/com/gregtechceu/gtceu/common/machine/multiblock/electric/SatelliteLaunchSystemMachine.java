package com.gregtechceu.gtceu.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.UITemplate;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.space.satellite.data.SatelliteData;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.item.KeyCardBehaviour;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import com.lowdragmc.lowdraglib.gui.widget.DraggableScrollableWidgetGroup;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.syncdata.ISubscription;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nullable;
import java.util.UUID;

public class SatelliteLaunchSystemMachine extends WorkableElectricMultiblockMachine {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(SatelliteLaunchSystemMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);
    public static final int SATELLITE_RANGE_MULTIPLIER = 64;

    @Persisted
    public final NotifiableItemStackHandler inventory;
    @Nullable
    protected ISubscription inventorySubs;
    @Nullable
    private UUID currentSatelliteOwnerUUID;

    public SatelliteLaunchSystemMachine(IMachineBlockEntity holder) {
        super(holder);
        this.inventory = createInventory();
    }

    @Override
    public boolean isWorkingEnabled() {
        return super.isWorkingEnabled() && !inventory.getStackInSlot(0).isEmpty();
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    protected NotifiableItemStackHandler createInventory() {
        return new NotifiableItemStackHandler(this, 1, IO.BOTH);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        inventorySubs = inventory.addChangedListener(this::updatedInventory);
    }

    protected void updatedInventory() {
        var stack = inventory.storage.getStackInSlot(0);
        if (inventory.storage.getStackInSlot(0).is(GTItems.KEYCARD.get())) {
            this.currentSatelliteOwnerUUID = KeyCardBehaviour.getOwner(stack);
        } else {
            this.currentSatelliteOwnerUUID = null;
        }
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        if (recipeLogic.lastRecipe != null) {
            var data = this.recipeLogic.lastRecipe.data;
            var type = data.contains("satellite_type") ? GTRegistries.SATELLITES.get(new ResourceLocation(data.getString("satellite_type"))) : null;
            BlockPos pos = this.getPos();
            GTCapabilityHelper.getSatellites(this.getLevel()).addSatellite(data == null ? null : type.getFactory().create(type, new SatelliteData(new Vec2(pos.getX(), pos.getZ()), this.getTier() * SATELLITE_RANGE_MULTIPLIER, currentSatelliteOwnerUUID), this.getLevel().dimension()));
        }

    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        var screen = new DraggableScrollableWidgetGroup(4, 4, 162, 121).setBackground(getScreenTexture());
        screen.addWidget(new LabelWidget(4, 5, self().getBlockState().getBlock().getDescriptionId()));
        screen.addWidget(new ComponentPanelWidget(4, 17, this::addDisplayText)
                .setMaxWidthLimit(156)
                .clickHandler(this::handleDisplayClick));
        return new ModularUI(190, 216, this, entityPlayer)
                .background(GuiTextures.BACKGROUND)
                .widget(new SlotWidget(inventory, 0, 168, 107).setBackgroundTexture(new GuiTextureGroup(GuiTextures.SLOT)))
                .widget(screen)
                .widget(UITemplate.bindPlayerInventory(entityPlayer.getInventory(), GuiTextures.SLOT, 7, 134, true));
    }
}
