package io.github.realguyman.totally_lit.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class NoParticleWallTorchBlock extends WallTorchBlock {
    public NoParticleWallTorchBlock(Settings settings) {
        super(settings, null);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {}
}
