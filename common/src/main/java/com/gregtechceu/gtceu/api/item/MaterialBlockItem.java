package com.gregtechceu.gtceu.api.item;

import com.gregtechceu.gtceu.api.block.MaterialBlock;
import com.lowdragmc.lowdraglib.client.renderer.IItemRendererProvider;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * @author KilaBash
 * @implNote MaterialBlockItem
 */
public class MaterialBlockItem extends BlockItem implements IItemRendererProvider {

    public MaterialBlockItem(MaterialBlock block, Properties properties) {
        super(block, properties);
    }

    @Override
    @Nonnull
    public MaterialBlock getBlock() {
        return (MaterialBlock)super.getBlock();
    }

    public static int tintColor(ItemStack itemStack, int index) {
        if (itemStack.getItem() instanceof MaterialBlockItem materialBlockItem) {
            return materialBlockItem.getBlock().material.getMaterialARGB();
        }
        return -1;
    }

    @Nullable
    @Override
    public IRenderer getRenderer(ItemStack stack) {
        return getBlock().getRenderer(getBlock().defaultBlockState());
    }

}
