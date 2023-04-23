package com.gregtechceu.gtceu.data.tags;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.MarkerMaterials.Color;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.data.tag.TagUtil;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class ItemTagLoader {

    public static void initItem(RegistrateTagsProvider<Item> provider) {
        createItem(provider, lens, Color.White, GTItems.MATERIAL_ITEMS.get(lens, Glass).getId());
        createItem(provider, "pistons", rl("piston"), rl("sticky_piston"));

        // TODO add to planks mc tag?
        //for (Material material : new Material[]{GTMaterials.Wood, GTMaterials.TreatedWood}) {
        //    for (ItemLike woodPlateStack : ChemicalHelper.getItems(new UnificationEntry(TagPrefix.plate, material))) {
        //        ChemicalHelper.registerUnificationEntry(woodPlateStack, TagPrefix.plank, material);
        //    }
        //}

        // todo match ae2 certus quartz tag
        //OreDictionary.registerUnificationEntry("crystalCertusQuartz", ChemicalHelper.get(TagPrefix.gem, GTMaterials.CertusQuartz));
    }

    public static void initBlock(RegistrateTagsProvider<Block> provider) {
        createBlock(provider, CustomTags.PASSES_FLOOD_FILL, "#fences", "iron_bars", "tnt");
    }

    private static void createItem(RegistrateTagsProvider<Item> provider, String tagName, ResourceLocation... rls) {
        createItem(provider, TagUtil.createItemTag(tagName), rls);
    }

    private static void createBlock(RegistrateTagsProvider<Block> provider, String tagName, String... rls) {
        createBlock(provider, TagUtil.createModBlockTag(tagName), rls);
    }

    private static void createItem(RegistrateTagsProvider<Item> provider, TagPrefix prefix, Material material, ResourceLocation... rls) {
        createItem(provider, ChemicalHelper.getTag(prefix, material), rls);
    }

    private static void createItem(RegistrateTagsProvider<Item> provider, TagKey<Item> tagKey, ResourceLocation... rls) {
        TagBuilder builder = provider.getOrCreateRawBuilder(tagKey);
        for (ResourceLocation rl : rls) {
            builder.addElement(rl);
        }
        builder.build();
    }

    private static void createBlock(RegistrateTagsProvider<Block> provider, TagKey<Block> tagKey, String... rls) {
        TagBuilder builder = provider.getOrCreateRawBuilder(tagKey);
        for (String str : rls) {
            if (str.startsWith("#")) builder.addOptionalTag(rl(str.substring(1)));
            else builder.addElement(rl(str));
        }
        builder.build();
    }

    private static ResourceLocation rl(String name) {
        return new ResourceLocation(name);
    }
}
