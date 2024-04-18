package io.github.realguyman.totally_lit.mixin.jack_o_lantern;

import io.github.realguyman.totally_lit.MyModInitializer;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
    @Shadow public abstract void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random);

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void schedule(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (!MyModInitializer.JACK_O_LANTERN_MAP.containsKey(state.getBlock())) {
            return;
        }

        final boolean isRaining = world.hasRain(pos.up());
        final boolean isChanceInFavor = random.nextFloat() < MyModInitializer.CONFIG.jackOLanterns.extinguishInRainChance();
        final boolean canExtinguishOverTime = MyModInitializer.CONFIG.jackOLanterns.extinguishOverTime();

        if (isRaining && isChanceInFavor) {
            this.scheduledTick(state, world, pos, random);
        } else if (canExtinguishOverTime) {
            WorldTickScheduler<Block> scheduler = world.getBlockTickScheduler();
            Block block = state.getBlock();

            if (!scheduler.isQueued(pos, block) && !scheduler.isTicking(pos, block)) {
                world.scheduleBlockTick(pos, block, MyModInitializer.CONFIG.jackOLanterns.burnDuration());
            }
        }
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"))
    private void extinguish(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        MyModInitializer.JACK_O_LANTERN_MAP.forEach((lit, unlit) -> {
            if (state.isOf(lit) && world.setBlockState(pos, unlit.getStateWithProperties(state))) {
                world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.125F, random.nextFloat() * 0.5F + 0.125F);
            }
        });
    }
}
