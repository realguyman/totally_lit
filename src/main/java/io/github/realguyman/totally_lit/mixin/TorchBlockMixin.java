package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.Configuration;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;
import net.minecraft.world.tick.QueryableTickScheduler;
import net.minecraft.world.tick.WorldTickScheduler;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(TorchBlock.class)
public abstract class TorchBlockMixin extends Block {
    public TorchBlockMixin(Settings settings) {
        super(settings);
    }

    private boolean isExtinguishableTorch(BlockState state) {
        // TODO: Consider using tags instead of checking each individual block.
        return state.isOf(Blocks.TORCH) || state.isOf(Blocks.WALL_TORCH);
    }

    private void scheduleTorchToExtinguishOverTime(ServerWorld world, BlockPos pos, Block block) {
        world.createAndScheduleBlockTick(pos, block, Configuration.INSTANCE.burnDuration * 6_000, TickPriority.EXTREMELY_LOW);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient && Configuration.INSTANCE.extinguishOverTime && isExtinguishableTorch(state)) {
            QueryableTickScheduler<Block> ticks = world.getBlockTickScheduler();
            Block block = state.getBlock();

            if (!ticks.isQueued(pos, block) && !ticks.isTicking(pos, block)) {
                scheduleTorchToExtinguishOverTime((ServerWorld) world, pos, block);
            }
        }

        super.onBlockAdded(state, world, pos, oldState, notify);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (Configuration.INSTANCE.extinguishOverTime) {
            boolean updated = false;

            // TODO: Consider using tags instead of checking each individual block.
            if (state.isOf(Blocks.TORCH)) {
                updated = world.setBlockState(pos, BlockRegistry.UNLIT_TORCH.getDefaultState());
            } else if (state.isOf(Blocks.WALL_TORCH)) {
                updated = world.setBlockState(pos, BlockRegistry.UNLIT_WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING)));
            }

            if (updated) {
                world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.0625F, random.nextFloat() * 0.5F + 0.125F);
            }
        }

        super.scheduledTick(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return (Configuration.INSTANCE.extinguishOverTime || Configuration.INSTANCE.extinguishInRainChance > 0F) && isExtinguishableTorch(state);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (isExtinguishableTorch(state)) {
            if (world.hasRain(pos) && random.nextFloat() < Configuration.INSTANCE.extinguishInRainChance) {
                super.randomTick(state, world, pos, random);
                return;
            }

            if (Configuration.INSTANCE.extinguishOverTime) {
                WorldTickScheduler<Block> ticks = world.getBlockTickScheduler();
                Block block = state.getBlock();

                if (!ticks.isQueued(pos, block) && !ticks.isTicking(pos, block)) {
                    scheduleTorchToExtinguishOverTime(world, pos, block);
                }
            }
        }
    }
}
