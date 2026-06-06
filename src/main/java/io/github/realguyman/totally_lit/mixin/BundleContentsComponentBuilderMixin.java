package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.Block;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Map;

@Mixin(BundleContentsComponent.Builder.class)
public abstract class BundleContentsComponentBuilderMixin {
    @ModifyArg(method = "add(Lnet/minecraft/item/ItemStack;)I", at = @At(value = "INVOKE", target = "Ljava/util/List;add(ILjava/lang/Object;)V"), index = 1)
    private Object replaceWithUnlitItem(Object object) {
        if (!TotallyLit.CONFIG.replaceWithUnlitVariantsInBundles()) {
            return object;
        }

        ItemStack stack = (ItemStack) object;

        for (Map.Entry<Block, Block> entry : TotallyLit.TORCH_MAP.entrySet()) {
            Item lit = entry.getKey().asItem();

            if (!stack.isOf(lit)) {
                continue;
            }

            Item unlit = entry.getValue().asItem();

            return stack.withItem(unlit);
        }

        for (Map.Entry<Block, Block> entry : TotallyLit.JACK_O_LANTERN_MAP.entrySet()) {
            Item lit = entry.getKey().asItem();

            if (!stack.isOf(lit)) {
                continue;
            }

            Item unlit = entry.getValue().asItem();

            return stack.withItem(unlit);
        }

        for (Map.Entry<Block, Block> entry : TotallyLit.LANTERN_MAP.entrySet()) {
            Item lit = entry.getKey().asItem();

            if (!stack.isOf(lit)) {
                continue;
            }

            Item unlit = entry.getValue().asItem();

            return stack.withItem(unlit);
        }

        return stack;
    }
}
