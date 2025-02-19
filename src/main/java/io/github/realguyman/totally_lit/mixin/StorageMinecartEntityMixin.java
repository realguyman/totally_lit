package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.Block;
import net.minecraft.entity.vehicle.StorageMinecartEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(StorageMinecartEntity.class)
public abstract class StorageMinecartEntityMixin {
    @Shadow
    public abstract void setStack(int slot, ItemStack stack);

    @Inject(at = @At("TAIL"), method = "setStack")
    private void replaceWithUnlitVariant(int slot, ItemStack stack, CallbackInfo ci) {
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
