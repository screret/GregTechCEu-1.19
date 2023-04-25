package com.gregtechceu.gtceu.common.machine.electric;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TieredEnergyMachine;
import com.gregtechceu.gtceu.api.machine.feature.IAutoOutputItem;
import com.gregtechceu.gtceu.api.machine.feature.IUIMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.voidore.VoidOreType;
import com.gregtechceu.gtceu.common.worldgen.voidore.VoidOreSavedData;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.gui.widget.custom.PlayerInventoryWidget;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.DropSaved;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class VoidOreMinerMachine extends TieredEnergyMachine implements IAutoOutputItem, IUIMachine {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(VoidOreMinerMachine.class, TieredEnergyMachine.MANAGED_FIELD_HOLDER);

    @Getter
    @Setter
    @Persisted
    @DescSynced
    protected boolean autoOutputItems;
    @Persisted @DropSaved
    protected final NotifiableItemStackHandler cache;

    public VoidOreMinerMachine(IMachineBlockEntity holder, int tier, Object... args) {
        super(holder, tier, args);
        this.cache = createCacheFluidHandler(args);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    protected NotifiableItemStackHandler createCacheFluidHandler(Object... args) {
        return new NotifiableItemStackHandler(this, 16, IO.OUT);
    }

    @Override
    public boolean isAllowInputFromOutputSideItems() {
        return false;
    }

    @Override
    public void setAllowInputFromOutputSideItems(boolean allow) {

    }

    @Override
    public @Nullable Direction getOutputFacingItems() {
        return getFrontFacing();
    }

    @Override
    public void setOutputFacingItems(Direction outputFacing) {
        setFrontFacing(outputFacing);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        subscribeServerTick(this::update);
    }

    public void update() {
        cache.exportToNearby(getOutputFacingItems());

        //do not do anything without enough energy supplied
        if (energyContainer.getEnergyStored() < GTValues.V[getTier()] * 2) {
            return;
        }
        if (!getLevel().isClientSide) {
            ServerLevel level = (ServerLevel) getLevel();
            var ores = VoidOreSavedData.getOrCreate(level);
            var oreToMine = ores.getFromBlockPos(getPos());
            if (oreToMine == null) return;
            mineOre(level, oreToMine);
        }
    }

    protected List<ItemStack> mineOre(ServerLevel level, VoidOreType type) {
        var pos = getPos();
        var drops = type.getDrops(getBlockState(), new LootContext.Builder(level).withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ())));
        if (!drops.isEmpty()) {
            Iterator<ItemStack> iterator = drops.iterator();
            while (iterator.hasNext()) {
                ItemStack output = ItemStack.EMPTY;
                if (!output.isEmpty()) {
                    for (int i = 0; i < cache.getSlots(); i++) {
                        ItemStack leftStack = cache.insertItem(i, output.copy(), false);
                        output.setCount(leftStack.getCount());
                        if (output.isEmpty()) break;
                    }
                }
                if (output.isEmpty()) iterator.remove();
            }
        }
        return drops.isEmpty() ? null : drops;
    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        WidgetGroup group = new WidgetGroup(12, 32, 54, 54);
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3;++y) {
                group.addWidget(new SlotWidget(cache, y + x * 3, x * 18, y * 18));
            }
        }

        return new ModularUI(176, 166, this, entityPlayer)
                .background(GuiTextures.BACKGROUND)
                .widget(new LabelWidget(6, 6, getBlockState().getBlock().getDescriptionId()))
                .widget(group)
                .widget(new PlayerInventoryWidget());
    }
}
