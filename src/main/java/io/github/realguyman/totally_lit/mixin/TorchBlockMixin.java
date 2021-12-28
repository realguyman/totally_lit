package io.github.realguyman.totally_lit.mixin;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.ticks.TickPriority;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(TorchBlock.class)
public abstract class TorchBlockMixin extends Block {
    public TorchBlockMixin(Properties properties) {
        super(properties);
    }

    private boolean isExtinguishableTorch(BlockState state) {
        Block block = state.getBlock();
        return block == Blocks.TORCH || block == Blocks.WALL_TORCH;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean notify) {
        // Schedule a tick for the torch if it hasn't been scheduled.
        if (!level.isClientSide() && isExtinguishableTorch(state) && !level.getBlockTicks().hasScheduledTick(pos, state.getBlock()) && !level.getBlockTicks().willTickThisTick(pos, state.getBlock())) {
            level.scheduleTick(pos, state.getBlock(), 24000, TickPriority.EXTREMELY_LOW);
        }

        super.onPlace(state, level, pos, oldState, notify);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        // TODO: Replace with a burnt torch which drops charcoal chunks instead of an unlit torch.
        // TODO: Look into using Tags instead of comparing each block individually.
        // TODO: Refactor this method.
        if (state.getBlock().equals(Blocks.TORCH)) {
            level.setBlockAndUpdate(pos, BlockRegistry.UNLIT_TORCH.defaultBlockState());
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.75F, random.nextFloat() * 0.75F + 0.25F);
        } else if (state.getBlock().equals(Blocks.WALL_TORCH)) {
            level.setBlockAndUpdate(pos, BlockRegistry.UNLIT_WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, state.getValue(WallTorchBlock.FACING)));
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.75F + 0.25F);
        }

        super.tick(state, level, pos, random);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        if (isExtinguishableTorch(state)) {
            return true;
        }

        return super.isRandomlyTicking(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        // Schedule a tick for the torch if it hasn't been scheduled.
        if (isExtinguishableTorch(state) && !level.getBlockTicks().hasScheduledTick(pos, state.getBlock()) && !level.getBlockTicks().willTickThisTick(pos, state.getBlock())) {
            level.scheduleTick(pos, state.getBlock(), 24000, TickPriority.EXTREMELY_LOW);
        }

        // TODO: Make the chance torches extinguish in the rain configurable.
        if (isExtinguishableTorch(state) && level.isRainingAt(pos) && random.nextInt(4) == 0) {
            // TODO: If the scheduled tick replaces the lit torch with a dim, or burnt torch, this method must not call it.
            super.randomTick(state, level, pos, random);
        }
    }
}