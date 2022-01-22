package io.github.realguyman.totally_lit.block;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class UnlitTorchBlock extends TorchBlock {
    public UnlitTorchBlock() {
        super(Settings.copy(Blocks.TORCH).luminance(state -> 0), null);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
        boolean updated = false;

        if (state.isOf(BlockRegistry.UNLIT_TORCH) && player.getStackInHand(hand).isOf(Items.TORCH)) {
            updated = world.setBlockState(pos, Blocks.TORCH.getDefaultState());
            // TODO: Add a quiet yet flamey sound.
        }

        return updated ? ActionResult.SUCCESS : super.onUse(state, world, pos, player, hand, result);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {}
}
