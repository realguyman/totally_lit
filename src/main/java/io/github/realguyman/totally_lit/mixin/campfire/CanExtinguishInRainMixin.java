package io.github.realguyman.totally_lit.mixin.campfire;

import io.github.realguyman.totally_lit.MyModInitializer;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class CanExtinguishInRainMixin {
    @Inject(method = "hasRandomTicks", at = @At("HEAD"), cancellable = true)
    private void canExtinguish(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        final boolean isLitCampfire = CampfireBlock.isLitCampfire(state);
        final boolean canExtinguishInRain = MyModInitializer.CONFIG.campfires.extinguishInRainChance() > 0F;

        if (isLitCampfire && canExtinguishInRain) {
            cir.setReturnValue(true);
        }
    }
}
