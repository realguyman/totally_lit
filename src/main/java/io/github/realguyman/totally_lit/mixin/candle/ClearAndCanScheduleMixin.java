package io.github.realguyman.totally_lit.mixin.candle;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class ClearAndCanScheduleMixin {
    @Inject(method = "hasRandomTicks", at = @At("HEAD"), cancellable = true)
    private void canSchedule(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!AbstractCandleBlock.isLitCandle(state)) {
            return;
        }

        boolean extinguishOverTime = TotallyLit.CONFIG.candles.extinguishOverTime();
        boolean extinguishInRain = TotallyLit.CONFIG.candles.extinguishInRainChance() > 0F;

        if (extinguishOverTime || extinguishInRain) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void clearNextScheduledExtinguish(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        if (!world.isClient() && AbstractCandleBlock.isLitCandle(state)) {
            ((ServerWorld) world).getBlockTickScheduler().clearNextTicks(new BlockBox(pos));
        }
    }
}
