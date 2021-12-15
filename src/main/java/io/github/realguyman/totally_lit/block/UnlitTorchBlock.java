package io.github.realguyman.totally_lit.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class UnlitTorchBlock extends TorchBlock {
    public UnlitTorchBlock() {
        super(FabricBlockSettings.copyOf(Blocks.TORCH).lightLevel(0));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {}
}