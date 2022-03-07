package io.github.realguyman.totally_lit.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class UnlitWallTorchBlock extends WallTorchBlock {
    private final Block litBlock;

    public UnlitWallTorchBlock(Settings settings, ParticleEffect particleEffect, Block litBlock) {
        super(settings, particleEffect);
        this.litBlock = litBlock;
    }

    public Block getLitBlock() {
        return litBlock;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
        if (player.getStackInHand(hand).isOf(Items.TORCH) && world.setBlockState(pos, litBlock.getDefaultState().with(Properties.FACING, state.get(Properties.FACING)))) {
            // TODO: Add a quiet flame sound.
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, result);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {}
}
