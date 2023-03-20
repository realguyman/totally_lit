package io.github.realguyman.totally_lit.block;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UnlitWallTorchBlock extends NoParticleWallTorchBlock {
    private final Block litBlock;

    public UnlitWallTorchBlock(Settings settings, Block litBlock) {
        super(settings);
        this.litBlock = litBlock;
    }

    public Block getLitBlock() {
        return litBlock;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
        if (player.getStackInHand(hand).isIn(TagRegistry.TORCH_IGNITER_ITEMS) && world.setBlockState(pos, litBlock.getDefaultState().with(FACING, state.get(FACING)))) {
            // TODO: Add a quiet flame sound.
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, result);
    }
}
