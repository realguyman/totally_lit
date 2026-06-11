package io.github.realguyman.totally_lit.mixin.lantern;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
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
    @Shadow
    protected abstract void tick(BlockState state, ServerLevel world, BlockPos pos, net.minecraft.util.RandomSource random);

    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    private void canScheduleLantern(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!TotallyLit.LANTERN_MAP.containsKey(state.getBlock())) {
            return;
        }

        cir.setReturnValue(true);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void scheduleLantern(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!TotallyLit.LANTERN_MAP.containsKey(state.getBlock())) {
            return;
        }

        final boolean isRaining = world.isRainingAt(pos.above());
        final boolean isChanceInFavor = random.nextFloat() < TotallyLit.CONFIG.lanterns.extinguishInRainChance();
        final boolean canExtinguishOverTime = TotallyLit.CONFIG.lanterns.extinguishOverTime();

        if ((isRaining && isChanceInFavor) || state.getValue(LanternBlock.WATERLOGGED)) {
            this.tick(state, world, pos, random);
        } else if (canExtinguishOverTime && !state.is(TagRegistry.SOUL_FIRE_VARIANT_BLOCKS)) {
            LevelTicks<Block> scheduler = world.getBlockTicks();
            Block block = state.getBlock();

            if (!scheduler.hasScheduledTick(pos, block) && !scheduler.willTickThisTick(pos, block)) {
                world.scheduleTick(pos, block, TotallyLit.CONFIG.lanterns.burnDuration());
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void extinguishLantern(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!TotallyLit.LANTERN_MAP.containsKey(state.getBlock())) {
            return;
        }

        if (TotallyLit.CONFIG.caretakerCheckRadius() > 0 && TotallyLit.isCaretakerPresent(pos, world)) {
            return;
        }

        TotallyLit.LANTERN_MAP.forEach((lit, unlit) -> {
            if (state.is(lit) && world.setBlockAndUpdate(pos, unlit.withPropertiesOf(state))) {
                world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.125F, random.nextFloat() * 0.5F + 0.125F);
                TotallyLit.CACHED_CARETAKER_BLOCKS.invalidate(pos);
            }
        });
    }
}
