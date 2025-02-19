package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.Block;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.BiConsumer;

@Mixin(EnderChestInventory.class)
public abstract class EnderChestInventoryMixin extends SimpleInventory {
    @Override
    public void setStack(int slot, ItemStack stack) {
        super.setStack(slot, stack);

        if (!TotallyLit.CONFIG.replaceWithUnlitVariantsInContainers()) {
            return;
        }

        BiConsumer<Block, Block> extinguish = (lit, unlit) -> {
            if (stack.isOf(lit.asItem())) {
                setStack(slot, new ItemStack(unlit.asItem(), stack.getCount()));
            }
        };

        TotallyLit.TORCH_MAP.forEach(extinguish);
        TotallyLit.LANTERN_MAP.forEach(extinguish);
        TotallyLit.JACK_O_LANTERN_MAP.forEach(extinguish);
    }
}
