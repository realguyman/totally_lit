package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecartContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(AbstractMinecartContainer.class)
public abstract class StorageMinecartEntityMixin {
    @Shadow
    public abstract void setItem(int slot, ItemStack stack);

    @Inject(at = @At("TAIL"), method = "setItem")
    private void replaceWithUnlitVariant(int slot, ItemStack stack, CallbackInfo ci) {
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
