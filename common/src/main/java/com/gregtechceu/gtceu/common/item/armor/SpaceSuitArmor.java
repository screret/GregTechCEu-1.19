package com.gregtechceu.gtceu.common.item.armor;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTFluids;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.lowdragmc.lowdraglib.msic.FluidStorage;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class SpaceSuitArmor extends ArmorItem {
    private final FluidStorage fluidStorage;

    public SpaceSuitArmor(EquipmentSlot slot, Properties properties) {
        super(GTArmorMaterials.SPACE, slot, properties);
        if (slot == EquipmentSlot.CHEST) this.fluidStorage = new FluidStorage(16000L, fluidStack -> fluidStack.getFluid().isSame(GTMaterials.Oxygen.getFluid()));
        else fluidStorage = null;
    }

    public static boolean hasFullSet(LivingEntity entity) {
        int slotCount = 0;
        int armorCount = 0;
        for (ItemStack stack : entity.getArmorSlots()) {
            slotCount++;
            if (stack.getItem() instanceof SpaceSuitArmor) {
                armorCount++;
            }
        }
        return slotCount > 0 && armorCount == slotCount;
    }

    /**
     * Checks if the entity is wearing a space suit and if that space suit has oxygen.
     *
     * @param entity The entity wearing the space suit
     * @return Whether the entity has oxygen or not
     */
    public static boolean hasOxygenatedSpaceSuit(LivingEntity entity) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof SpaceSuitArmor suit) {
            return suit.fluidStorage.getFluidAmount() > 0;
        }

        return false;
    }

    public static void consumeSpaceSuitOxygen(LivingEntity entity, long amount) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof SpaceSuitArmor suit) {
            suit.fluidStorage.drain(amount, false);
            entity.setItemSlot(EquipmentSlot.CHEST, chest);
        }
    }

}
