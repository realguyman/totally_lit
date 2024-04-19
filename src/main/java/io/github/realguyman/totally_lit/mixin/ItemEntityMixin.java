package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    protected ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getStack();

    @Shadow public abstract void setStack(ItemStack stack);

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (isSubmergedInWater() && TotallyLit.CONFIG.itemEntitiesExtinguishWhenSubmerged()) {
            BiConsumer<Block, Block> extinguish = (lit, unlit) -> {
                if (getStack().isOf(lit.asItem())) {
                    setStack(new ItemStack(unlit.asItem(), getStack().getCount()));
                }
            };

            TotallyLit.JACK_O_LANTERN_MAP.forEach(extinguish);
            TotallyLit.LANTERN_MAP.forEach(extinguish);
            TotallyLit.TORCH_MAP.forEach(extinguish);
        }
    }
}
