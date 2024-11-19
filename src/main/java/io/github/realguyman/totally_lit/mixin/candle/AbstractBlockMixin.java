package io.github.realguyman.totally_lit.mixin.candle;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.tick.WorldTickScheduler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {
    @Shadow protected abstract void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random);

    @Inject(method = "hasRandomTicks", at = @At("HEAD"), cancellable = true)
    private void canSchedule(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void schedule(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (!AbstractCandleBlock.isLitCandle(state)) {
            return;
        }

        boolean raining = world.hasRain(pos.up());
        boolean chanceInFavor = random.nextFloat() < TotallyLit.CONFIG.candles.extinguishInRainChance();
        boolean waterlogged = false;

        if (state.contains(Properties.WATERLOGGED)) {
            waterlogged = state.get(Properties.WATERLOGGED);
        }

        if ((raining && chanceInFavor) || waterlogged) {
            this.scheduledTick(state, world, pos, random);
        } else if (TotallyLit.CONFIG.candles.extinguishOverTime()) {
            WorldTickScheduler<Block> scheduler = world.getBlockTickScheduler();
            Block block = state.getBlock();

            if (!scheduler.isQueued(pos, block) && !scheduler.isTicking(pos, block)) {
                world.scheduleBlockTick(pos, block, TotallyLit.CONFIG.candles.burnDuration());
            }
        }
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"))
    private void extinguish(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        var caretakers = world.getEntitiesByClass(
                Entity.class,
                new Box(pos).expand(32),
                EntityPredicates.VALID_LIVING_ENTITY
        ).stream().filter(entity -> entity.getType().isIn(TagRegistry.CARETAKERS)).toList();

        if (AbstractCandleBlock.isLitCandle(state) && caretakers.isEmpty()) {
            AbstractCandleBlock.extinguish(null, state, world, pos);
        }
    }
}
