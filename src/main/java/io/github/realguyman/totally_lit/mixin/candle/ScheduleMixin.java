package io.github.realguyman.totally_lit.mixin.candle;

import io.github.realguyman.totally_lit.TotallyLit;
import java.util.List;
import net.minecraft.block.*;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
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

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
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

        ci.cancel();
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"))
    private void extinguish(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        var nearbyVillagers = world.getEntitiesByType(
                TypeFilter.instanceOf(VillagerEntity.class),
                new Box(pos).expand(32),
                EntityPredicates.VALID_LIVING_ENTITY
        );

        if (AbstractCandleBlock.isLitCandle(state) && nearbyVillagers.isEmpty()) {
            AbstractCandleBlock.extinguish(null, state, world, pos);
        }
    }
}
