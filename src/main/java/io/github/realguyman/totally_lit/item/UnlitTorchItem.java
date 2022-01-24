package io.github.realguyman.totally_lit.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UnlitTorchItem extends WallStandingBlockItem {
    public UnlitTorchItem(Block standing, Block wall, Settings settings) {
        super(standing, wall, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockState state = world.getBlockState(context.getBlockPos());
        Fluid fluid = world.getFluidState(new BlockPos(context.getHitPos())).getFluid();

        // TODO: Use tags instead of checking for individual hot block sources.
        if (state.isOf(Blocks.TORCH) || state.isOf(Blocks.WALL_TORCH) || state.isOf(Blocks.FIRE) || state.isOf(Blocks.LAVA_CAULDRON) || fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA) {
            PlayerEntity player = context.getPlayer();

            if (player != null && !player.isSneaking() && player.giveItemStack(new ItemStack(Items.TORCH))) {
                player.getStackInHand(context.getHand()).decrement(1);
                return ActionResult.SUCCESS;
            }
        }

        return super.useOnBlock(context);
    }
}
