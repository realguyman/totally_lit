package io.github.realguyman.totally_lit.mixin.candle;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.ticks.LevelTicks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public abstract class AbstractBlockMixin {
    @Shadow protected abstract void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random);

    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    private void canSchedule(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!AbstractCandleBlock.isLit(state)) {
            return;
        }

        cir.setReturnValue(true);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void scheduleCandle(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!AbstractCandleBlock.isLit(state)) {
            return;
        }

        boolean raining = world.isRainingAt(pos.above());
        boolean chanceInFavor = random.nextFloat() < TotallyLit.CONFIG.candles.extinguishInRainChance();
        boolean waterlogged = false;

        if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            waterlogged = state.getValue(BlockStateProperties.WATERLOGGED);
        }

        if ((raining && chanceInFavor) || waterlogged) {
            this.tick(state, world, pos, random);
        } else if (TotallyLit.CONFIG.candles.extinguishOverTime()) {
            LevelTicks<Block> scheduler = world.getBlockTicks();
            Block block = state.getBlock();

            if (!scheduler.hasScheduledTick(pos, block) && !scheduler.willTickThisTick(pos, block)) {
                world.scheduleTick(pos, block, TotallyLit.CONFIG.candles.burnDuration());
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void extinguishCandle(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!AbstractCandleBlock.isLit(state)) {
            return;
        }

        if (TotallyLit.CONFIG.caretakerCheckRadius() > 0 && TotallyLit.isCaretakerPresent(pos, world)) {
            return;
        }

        AbstractCandleBlock.extinguish(null, state, world, pos);
        TotallyLit.CACHED_CARETAKER_BLOCKS.invalidate(pos);
    }
}
