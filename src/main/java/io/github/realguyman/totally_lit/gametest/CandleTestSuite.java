package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Blocks;

public class CandleTestSuite {
    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void candleBlockDoesExtinguishOverTime(GameTestHelper context) {
        TestUtil.abstractCandleBlockDoesExtinguishOverTime(
                context,
                (AbstractCandleBlock) Blocks.CANDLE
        );
    }
}
