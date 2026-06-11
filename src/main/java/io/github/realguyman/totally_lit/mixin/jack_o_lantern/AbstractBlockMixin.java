package io.github.realguyman.totally_lit.mixin.jack_o_lantern;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.LevelTicks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public abstract class AbstractBlockMixin {
    @Shadow protected abstract void tick(BlockState state, ServerLevel world, BlockPos pos, net.minecraft.util.RandomSource random);

    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    private void canScheduleJackOLantern(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!TotallyLit.JACK_O_LANTERN_MAP.containsKey(state.getBlock())) {
            return;
        }

        cir.setReturnValue(true);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void scheduleJackOLantern(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!TotallyLit.JACK_O_LANTERN_MAP.containsKey(state.getBlock())) {
            return;
        }

        final boolean isRaining = world.isRainingAt(pos.above());
        final boolean isChanceInFavor = random.nextFloat() < TotallyLit.CONFIG.jackOLanterns.extinguishInRainChance();
        final boolean canExtinguishOverTime = TotallyLit.CONFIG.jackOLanterns.extinguishOverTime();

        if (isRaining && isChanceInFavor) {
            this.tick(state, world, pos, random);
        } else if (canExtinguishOverTime) {
            LevelTicks<Block> scheduler = world.getBlockTicks();
            Block block = state.getBlock();

            if (!scheduler.hasScheduledTick(pos, block) && !scheduler.willTickThisTick(pos, block)) {
                world.scheduleTick(pos, block, TotallyLit.CONFIG.jackOLanterns.burnDuration());
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void extinguishJackOLantern(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!TotallyLit.JACK_O_LANTERN_MAP.containsKey(state.getBlock())) {
            return;
        }

        if (TotallyLit.CONFIG.caretakerCheckRadius() > 0 && TotallyLit.isCaretakerPresent(pos, world)) {
            return;
        }

        TotallyLit.JACK_O_LANTERN_MAP.forEach((lit, unlit) -> {
            if (state.is(lit) && world.setBlockAndUpdate(pos, unlit.withPropertiesOf(state))) {
                world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.125F, random.nextFloat() * 0.5F + 0.125F);
                TotallyLit.CACHED_CARETAKER_BLOCKS.invalidate(pos);
            }
        });
    }
}
