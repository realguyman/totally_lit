package io.github.realguyman.totally_lit.util;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

public final class TestUtil {
    private TestUtil() throws InstantiationException {
        throw new InstantiationException("Constructor is private, therefore class cannot be instantiated.");
    }

    public static void itemEntityDoesExtinguishWhenSubmergedInWater(TestContext context, Item lit, Item unlit) {
        BlockPos pos = new BlockPos(0, 2, 0);
        context.setBlockState(pos, Blocks.WATER);
        context.spawnItem(lit, pos);
        context.expectEntityWithDataEnd(pos, EntityType.ITEM, entity -> entity.getStack().getItem(), unlit);
    }

    public static void blockDoesExtinguishOverTime(TestContext context, Block lit, Block unlit) {
        context.getWorld().getTickManager().setTickRate(TotallyLit.MAX_TICKS_TO_BURN_FOR);

        BlockPos pos = new BlockPos(0, 2, 0);
        context.setBlockState(pos, lit);
        context.forceRandomTick(pos);
        context.expectBlockAtEnd(unlit, pos);
    }

    public static void abstractCandleBlockDoesExtinguishOverTime(TestContext context, AbstractCandleBlock candleBlock) {
        context.getWorld().getTickManager().setTickRate(TotallyLit.MAX_TICKS_TO_BURN_FOR);

        BlockPos pos = new BlockPos(0, 2, 0);
        context.setBlockState(pos, candleBlock.getDefaultState().with(AbstractCandleBlock.LIT, true));
        context.forceRandomTick(pos);
        context.addInstantFinalTask(() -> {
            context.expectBlockProperty(pos, AbstractCandleBlock.LIT, false);
        });
    }

    public static void waterloggedBlockDoesExtinguishWhenRandomlyTicked(TestContext context, Block lit, Block unlit) {
        context.getWorld().getTickManager().setTickRate(TotallyLit.MAX_TICKS_TO_BURN_FOR);

        BlockPos pos = new BlockPos(0, 2, 0);
        context.setBlockState(pos, lit.getDefaultState().with(LanternBlock.WATERLOGGED, true));
        context.forceRandomTick(pos);
        context.expectBlockAtEnd(unlit, pos);
    }
}
