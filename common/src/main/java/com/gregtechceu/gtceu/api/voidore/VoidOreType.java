package com.gregtechceu.gtceu.api.voidore;

import com.gregtechceu.gtceu.api.item.GTToolItem;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.item.tool.MaterialToolTier;
import com.gregtechceu.gtceu.common.data.GTItems;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Collections;
import java.util.List;

public record VoidOreType(String name, ResourceLocation drops) {
    public static ItemStack BREAK_TOOL = new ItemStack(Items.NETHERITE_PICKAXE);

    public static VoidOreType load(ServerLevel level, CompoundTag tag) {
        String name = tag.getString("name");
        //Object2FloatArrayMap<ItemLike> drops = new Object2FloatArrayMap<>();
        var dropsName = new ResourceLocation(tag.getString("drops"));

        /*for (var key : dropsName.getAllKeys()) {
            drops.put(Registry.ITEM.get(new ResourceLocation(key)), dropsName.getFloat(key));
        }*/
        return new VoidOreType(name, dropsName);
    }

    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        ResourceLocation resourceLocation = this.drops;
        if (resourceLocation == BuiltInLootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootContext lootContext = builder.withParameter(LootContextParams.BLOCK_STATE, state).withParameter(LootContextParams.TOOL, BREAK_TOOL).create(LootContextParamSets.BLOCK);
            ServerLevel serverLevel = lootContext.getLevel();
            LootTable lootTable = serverLevel.getServer().getLootTables().get(resourceLocation);
            return lootTable.getRandomItems(lootContext);
        }
    }

    public void save(CompoundTag tag) {
        tag.putString("name", this.name);
        tag.putString("drops", this.drops.toString());
    }
}
