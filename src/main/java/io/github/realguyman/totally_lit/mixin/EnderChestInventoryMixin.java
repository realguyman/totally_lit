package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.BiConsumer;

@Mixin(PlayerEnderChestContainer.class)
public abstract class EnderChestInventoryMixin extends SimpleContainer {
    @Override
    public void setItem(int slot, ItemStack stack) {
        super.setItem(slot, stack);

        if (!TotallyLit.CONFIG.replaceWithUnlitVariantsInContainers()) {
            return;
        }

        BiConsumer<Block, Block> extinguish = (lit, unlit) -> {
            if (stack.is(lit.asItem())) {
                setItem(slot, new ItemStack(unlit.asItem(), stack.getCount()));
            }
        };

        TotallyLit.TORCH_MAP.forEach(extinguish);
        TotallyLit.LANTERN_MAP.forEach(extinguish);
        TotallyLit.JACK_O_LANTERN_MAP.forEach(extinguish);
    }
}
