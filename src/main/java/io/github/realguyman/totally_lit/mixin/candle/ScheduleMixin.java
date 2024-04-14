package io.github.realguyman.totally_lit.mixin.candle;

import io.github.realguyman.totally_lit.MyModInitializer;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.tick.WorldTickScheduler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public abstract class ScheduleMixin {
    @Shadow public abstract void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random);

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void schedule(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (!AbstractCandleBlock.isLitCandle(state)) {
            return;
        }

        if (MyModInitializer.CONFIG.candles.extinguishOverTime()) {
            WorldTickScheduler<Block> scheduler = world.getBlockTickScheduler();
            Block block = state.getBlock();

            if (!scheduler.isQueued(pos, block) && !scheduler.isTicking(pos, block)) {
                world.scheduleBlockTick(pos, block, MyModInitializer.CONFIG.candles.burnDuration());
            }
        }

        boolean raining = world.hasRain(pos.up());
        boolean chanceInFavor = random.nextFloat() < MyModInitializer.CONFIG.candles.extinguishInRainChance();
        boolean waterlogged = false;

        if (state.contains(CandleBlock.WATERLOGGED)) {
            waterlogged = state.get(CandleBlock.WATERLOGGED);
        }

        if ((raining && chanceInFavor) || waterlogged) {
            this.scheduledTick(state, world, pos, random);
        }
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"))
    private void extinguish(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (AbstractCandleBlock.isLitCandle(state)) {
            AbstractCandleBlock.extinguish(null, state, world, pos);
        }
    }
}
