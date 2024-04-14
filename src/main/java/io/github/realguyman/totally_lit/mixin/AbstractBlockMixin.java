package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.block.LitLanternBlock;
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
public abstract class AbstractBlockMixin {
    @Shadow @Deprecated public abstract void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random);

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (state.isOf(Blocks.JACK_O_LANTERN)) {
            if ((world.hasRain(pos) && random.nextFloat() < TotallyLit.CONFIG.jackOLanterns.extinguishInRainChance())) {
                this.scheduledTick(state, world, pos, random);
            } else if (TotallyLit.CONFIG.jackOLanterns.extinguishOverTime()) {
                WorldTickScheduler<Block> scheduler = world.getBlockTickScheduler();
                Block block = state.getBlock();

                if (!scheduler.isQueued(pos, block) && !scheduler.isTicking(pos, block)) {
                    world.scheduleBlockTick(pos, block, TotallyLit.CONFIG.jackOLanterns.burnDuration());
                }
            }

            ci.cancel();
        } else if (state.isOf(Blocks.LANTERN) || state.isOf(Blocks.SOUL_LANTERN) || state.getBlock() instanceof LitLanternBlock) {
            if ((world.hasRain(pos) && random.nextFloat() < TotallyLit.CONFIG.lanterns.extinguishInRainChance()) || Boolean.TRUE.equals(state.get(LanternBlock.WATERLOGGED))) {
                this.scheduledTick(state, world, pos, random);
            } else if (TotallyLit.CONFIG.lanterns.extinguishOverTime() && !state.isOf(Blocks.SOUL_LANTERN)) {
                WorldTickScheduler<Block> scheduler = world.getBlockTickScheduler();
                Block block = state.getBlock();

                if (!scheduler.isQueued(pos, block) && !scheduler.isTicking(pos, block)) {
                    world.scheduleBlockTick(pos, block, TotallyLit.CONFIG.lanterns.burnDuration());
                }
            }

            ci.cancel();
        } else if (state.isOf(Blocks.TORCH) || state.isOf(Blocks.WALL_TORCH) || state.isOf(Blocks.SOUL_TORCH) || state.isOf(Blocks.SOUL_WALL_TORCH) || state.getBlock() instanceof LitTorchBlock || state.getBlock() instanceof LitWallTorchBlock) {
            if (world.hasRain(pos) && random.nextFloat() < TotallyLit.CONFIG.torches.extinguishInRainChance()) {
                this.scheduledTick(state, world, pos, random);
            } else if (TotallyLit.CONFIG.torches.extinguishOverTime() && !state.isOf(Blocks.SOUL_TORCH) && !state.isOf(Blocks.SOUL_WALL_TORCH)) {
                WorldTickScheduler<Block> scheduler = world.getBlockTickScheduler();
                Block block = state.getBlock();

                if (!scheduler.isQueued(pos, block) && !scheduler.isTicking(pos, block)) {
                    world.scheduleBlockTick(pos, block, TotallyLit.CONFIG.torches.burnDuration());
                }
            }

            ci.cancel();
        }
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"))
    private void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        boolean updated = false;

        if (state.isOf(Blocks.JACK_O_LANTERN)) {
            updated = world.setBlockState(pos, Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, state.get(CarvedPumpkinBlock.FACING)));
        } else if(state.isOf(Blocks.LANTERN)) {
            updated = world.setBlockState(pos, BlockRegistry.UNLIT_LANTERN.getDefaultState().with(LanternBlock.HANGING, state.get(LanternBlock.HANGING)).with(LanternBlock.WATERLOGGED, state.get(LanternBlock.WATERLOGGED)));
        } else if (state.isOf(Blocks.SOUL_LANTERN)) {
            updated = world.setBlockState(pos, BlockRegistry.UNLIT_SOUL_LANTERN.getDefaultState().with(LanternBlock.HANGING, state.get(LanternBlock.HANGING)).with(LanternBlock.WATERLOGGED, state.get(LanternBlock.WATERLOGGED)));
        } else if (state.isOf(Blocks.TORCH)) {
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
