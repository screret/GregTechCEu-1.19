package com.gregtechceu.gtceu.common.item;

import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.UUID;

public class KeyCardBehaviour implements IItemComponent {

    public KeyCardBehaviour() {

    }

    @Nullable
    public static UUID getOwner(ItemStack stack) {
        if (stack.is(GTItems.KEYCARD.get()) && stack.hasTag() && stack.getTag().contains("gtceu:KeyCardOwner")) {
            return ExtraCodecs.UUID.parse(NbtOps.INSTANCE, stack.getTag().get("gtceu:KeyCardOwner")).result().orElse(null);
        }
        return null;
    }

    public static void setOwner(ItemStack stack, LivingEntity entity) {
        if (stack.is(GTItems.KEYCARD.get())) {
            stack.getOrCreateTag().put("gtceu:KeyCardOwner", ExtraCodecs.UUID.encode(entity.getUUID(), NbtOps.INSTANCE, new IntArrayTag(new int[4])).result().orElse(new CompoundTag()));
        }
    }

}
