package io.github.realguyman.totally_lit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class UnlitWallTorchBlock extends WallTorchBlock {
    public UnlitWallTorchBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel((state) -> 0), null);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {}
}
