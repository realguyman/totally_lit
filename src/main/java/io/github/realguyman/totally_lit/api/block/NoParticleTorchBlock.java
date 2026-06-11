package io.github.realguyman.totally_lit.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Simple torch block that doesn't emit any particles.
 */
public class NoParticleTorchBlock extends TorchBlock {
    public NoParticleTorchBlock(Properties settings) {
        super(null, settings);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
    }
}
