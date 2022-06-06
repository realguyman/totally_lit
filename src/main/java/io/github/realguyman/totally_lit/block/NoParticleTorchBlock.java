package io.github.realguyman.totally_lit.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class NoParticleTorchBlock extends TorchBlock {
    public NoParticleTorchBlock(Settings settings) {
        super(settings, null);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {}
}
