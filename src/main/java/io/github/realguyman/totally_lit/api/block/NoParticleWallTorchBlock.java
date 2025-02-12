package io.github.realguyman.totally_lit.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * Simple wall torch block that doesn't emit any particles.
 */
public class NoParticleWallTorchBlock extends WallTorchBlock {
    public NoParticleWallTorchBlock(Settings settings) {
        super(null, settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {}
}
