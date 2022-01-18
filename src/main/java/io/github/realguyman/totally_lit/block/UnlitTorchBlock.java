package io.github.realguyman.totally_lit.block;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class UnlitTorchBlock extends TorchBlock {
    public UnlitTorchBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel((state) -> 0), null);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (state.is(BlockRegistry.UNLIT_TORCH) && player.getItemInHand(hand).is(Items.TORCH)) {
            level.setBlockAndUpdate(pos, Blocks.TORCH.defaultBlockState());
            // TODO: Add a quiet yet flamey sound.
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, result);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
    }
}
