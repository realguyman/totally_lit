package io.github.realguyman.totally_lit.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Simple wall torch block that doesn't emit any particles.
 */
public class NoParticleWallTorchBlock extends WallTorchBlock {
    public NoParticleWallTorchBlock(Properties settings) {
        super(null, settings);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {}
}
