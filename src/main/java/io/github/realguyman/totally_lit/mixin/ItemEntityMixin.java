package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    protected ItemEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getItem();

    @Shadow public abstract void setItem(ItemStack stack);

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (isUnderWater() && TotallyLit.CONFIG.itemEntitiesExtinguishWhenSubmerged()) {
            BiConsumer<Block, Block> extinguish = (lit, unlit) -> {
                if (getItem().is(lit.asItem())) {
                    setItem(new ItemStack(unlit.asItem(), getItem().getCount()));
                }
            };

            TotallyLit.JACK_O_LANTERN_MAP.forEach(extinguish);
            TotallyLit.LANTERN_MAP.forEach(extinguish);
            TotallyLit.TORCH_MAP.forEach(extinguish);
        }
    }
}
