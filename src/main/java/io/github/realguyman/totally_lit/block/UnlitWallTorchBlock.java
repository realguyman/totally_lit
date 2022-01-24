package io.github.realguyman.totally_lit.block;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class UnlitWallTorchBlock extends WallTorchBlock {
    public UnlitWallTorchBlock() {
        super(Settings.copy(Blocks.WALL_TORCH).luminance(state -> 0), null);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
        if (state.isOf(BlockRegistry.UNLIT_WALL_TORCH) && player.getStackInHand(hand).isOf(Items.TORCH) && world.setBlockState(pos, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING)))) {
            // TODO: Add a quiet flame sound.
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, result);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {}
}
