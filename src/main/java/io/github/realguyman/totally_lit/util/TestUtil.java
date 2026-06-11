package io.github.realguyman.totally_lit.util;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;

public final class TestUtil {
    private TestUtil() throws InstantiationException {
        throw new InstantiationException("Constructor is private, therefore class cannot be instantiated.");
    }

    public static void itemEntityDoesExtinguishWhenSubmergedInWater(GameTestHelper context, Item lit, Item unlit) {
        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlock(pos, Blocks.WATER);
        context.spawnItem(lit, pos);
        context.succeedWhenEntityData(pos, EntityType.ITEM, entity -> entity.getItem().getItem(), unlit);
    }

    public static void blockDoesExtinguishOverTime(GameTestHelper context, Block lit, Block unlit) {
        context.getLevel().tickRateManager().setTickRate(TotallyLit.MAX_TICKS_TO_BURN_FOR);

        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlock(pos, lit);
        context.randomTick(pos);
        context.succeedWhenBlockPresent(unlit, pos);
    }

    public static void abstractCandleBlockDoesExtinguishOverTime(GameTestHelper context, AbstractCandleBlock candleBlock) {
        context.getLevel().tickRateManager().setTickRate(TotallyLit.MAX_TICKS_TO_BURN_FOR);

        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlock(pos, candleBlock.defaultBlockState().setValue(AbstractCandleBlock.LIT, true));
        context.randomTick(pos);
        context.succeedWhen(() -> {
            context.assertBlockProperty(pos, AbstractCandleBlock.LIT, false);
        });
    }

    public static void waterloggedBlockDoesExtinguishWhenRandomlyTicked(GameTestHelper context, Block lit, Block unlit) {
        context.getLevel().tickRateManager().setTickRate(TotallyLit.MAX_TICKS_TO_BURN_FOR);

        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlock(pos, lit.defaultBlockState().setValue(LanternBlock.WATERLOGGED, true));
        context.randomTick(pos);
        context.succeedWhenBlockPresent(unlit, pos);
    }
}
