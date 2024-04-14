package io.github.realguyman.totally_lit.mixin.torch;

import io.github.realguyman.totally_lit.MyModInitializer;
import io.github.realguyman.totally_lit.block.LitTorchBlock;
import io.github.realguyman.totally_lit.block.LitWallTorchBlock;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
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
        final boolean isTorch = state.isOf(Blocks.TORCH);
        final boolean isWallTorch = state.isOf(Blocks.WALL_TORCH);
        final boolean isSoulTorch = state.isOf(Blocks.SOUL_TORCH);
        final boolean isSoulWallTorch = state.isOf(Blocks.SOUL_WALL_TORCH);
        final boolean isLitTorch = state.getBlock() instanceof LitTorchBlock;
        final boolean isLitWallTorch = state.getBlock() instanceof LitWallTorchBlock;

        if (!isTorch && !isWallTorch && !isSoulTorch && !isSoulWallTorch && !isLitTorch && !isLitWallTorch) {
            return;
        }

        final boolean isRaining = world.hasRain(pos.up());
        final boolean isChanceInFavor = random.nextFloat() < MyModInitializer.CONFIG.torches.extinguishInRainChance();
        final boolean canExtinguishOverTime = MyModInitializer.CONFIG.torches.extinguishOverTime();

        if (isRaining && isChanceInFavor) {
            this.scheduledTick(state, world, pos, random);
        } else if (canExtinguishOverTime && !isSoulTorch && !isSoulWallTorch) {
            WorldTickScheduler<Block> scheduler = world.getBlockTickScheduler();
            Block block = state.getBlock();

            if (!scheduler.isQueued(pos, block) && !scheduler.isTicking(pos, block)) {
                world.scheduleBlockTick(pos, block, MyModInitializer.CONFIG.torches.burnDuration());
            }
        }
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"))
    private void extinguish(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        boolean updated = false;

        if (state.isOf(Blocks.TORCH)) {
            updated = world.setBlockState(pos, BlockRegistry.UNLIT_TORCH.getDefaultState());
        } else if (state.isOf(Blocks.WALL_TORCH)) {
            updated = world.setBlockState(pos, BlockRegistry.UNLIT_WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING)));
        } else if (state.isOf(Blocks.SOUL_TORCH)) {
            updated = world.setBlockState(pos, BlockRegistry.UNLIT_SOUL_TORCH.getDefaultState());
        } else if (state.isOf(Blocks.SOUL_WALL_TORCH)) {
            updated = world.setBlockState(pos, BlockRegistry.UNLIT_SOUL_WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING)));
        } else if (state.getBlock() instanceof LitTorchBlock litTorch) {
            updated = world.setBlockState(pos, litTorch.getUnlitBlock().getDefaultState());
        } else if (state.getBlock() instanceof LitWallTorchBlock litWallTorch) {
            updated = world.setBlockState(pos, litWallTorch.getUnlitBlock().getDefaultState().with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING)));
        }

        if (updated) {
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.0625F, random.nextFloat() * 0.5F + 0.125F);
        }
    }
}
