package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.Configuration;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.LevelTicks;
import net.minecraft.world.ticks.TickPriority;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(TorchBlock.class)
public abstract class TorchBlockMixin extends Block {
    public TorchBlockMixin(Properties properties) {
        super(properties);
    }

    private boolean isExtinguishableTorch(BlockState state) {
        // TODO: Consider using tags instead of checking each individual block.
        return state.is(Blocks.TORCH) || state.is(Blocks.WALL_TORCH);
    }

    private void scheduleTorchToExtinguishOverTime(ServerLevel level, BlockPos pos, Block block) {
        level.scheduleTick(pos, block, Configuration.INSTANCE.burnDuration * 6_000, TickPriority.EXTREMELY_LOW);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean notify) {
        if (!level.isClientSide && Configuration.INSTANCE.extinguish && isExtinguishableTorch(state)) {
            LevelTickAccess<Block> ticks = level.getBlockTicks();
            Block block = state.getBlock();


            if (!ticks.hasScheduledTick(pos, block) && !ticks.willTickThisTick(pos, block)) {
                scheduleTorchToExtinguishOverTime((ServerLevel) level, pos, block);
            }
        }

        super.onPlace(state, level, pos, oldState, notify);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (Configuration.INSTANCE.extinguish) {
            boolean updated = false;

            // TODO: Consider using tags instead of checking each individual block.
            if (state.is(Blocks.TORCH)) {
                updated = level.setBlockAndUpdate(pos, BlockRegistry.UNLIT_TORCH.defaultBlockState());
            } else if (state.is(Blocks.WALL_TORCH)) {
                updated = level.setBlockAndUpdate(pos, BlockRegistry.UNLIT_WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, state.getValue(WallTorchBlock.FACING)));
            }

            if (updated) {
                level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.0625F, random.nextFloat() * 0.5F + 0.125F);
            }
        }

        super.tick(state, level, pos, random);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return (Configuration.INSTANCE.extinguish || Configuration.INSTANCE.extinguishInRainChance > 0F) && isExtinguishableTorch(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (isExtinguishableTorch(state)) {
            if (level.isRainingAt(pos) && random.nextFloat() < Configuration.INSTANCE.extinguishInRainChance) {
                super.randomTick(state, level, pos, random);
                return;
            }

            if (Configuration.INSTANCE.extinguish) {
                LevelTicks<Block> ticks = level.getBlockTicks();
                Block block = state.getBlock();

                if (!ticks.hasScheduledTick(pos, block) && !ticks.willTickThisTick(pos, block)) {
                    scheduleTorchToExtinguishOverTime(level, pos, block);
                }
            }
        }
    }
}