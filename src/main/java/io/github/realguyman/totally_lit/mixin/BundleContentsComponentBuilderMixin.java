package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Map;

@Mixin(BundleContents.Mutable.class)
public abstract class BundleContentsComponentBuilderMixin {
    @ModifyArg(method = "tryInsert(Lnet/minecraft/world/item/ItemStack;)I", at = @At(value = "INVOKE", target = "Ljava/util/List;add(ILjava/lang/Object;)V"), index = 1)
    private Object replaceWithUnlitItem(Object object) {
        if (!TotallyLit.CONFIG.replaceWithUnlitVariantsInBundles()) {
            return object;
        }

        ItemStack stack = (ItemStack) object;

        for (Map.Entry<Block, Block> entry : TotallyLit.TORCH_MAP.entrySet()) {
            Item lit = entry.getKey().asItem();

            if (!stack.is(lit)) {
                continue;
            }

            Item unlit = entry.getValue().asItem();

            return stack.transmuteCopy(unlit);
        }

        for (Map.Entry<Block, Block> entry : TotallyLit.JACK_O_LANTERN_MAP.entrySet()) {
            Item lit = entry.getKey().asItem();

            if (!stack.is(lit)) {
                continue;
            }

            Item unlit = entry.getValue().asItem();

            return stack.transmuteCopy(unlit);
        }

        for (Map.Entry<Block, Block> entry : TotallyLit.LANTERN_MAP.entrySet()) {
            Item lit = entry.getKey().asItem();

            if (!stack.is(lit)) {
                continue;
            }

            Item unlit = entry.getValue().asItem();

            return stack.transmuteCopy(unlit);
        }

        return stack;
    }
}
