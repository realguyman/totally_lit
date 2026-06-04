package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.Blocks;
import net.minecraft.test.TestContext;

public class CandleTestSuite {
    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void candleBlockDoesExtinguishOverTime(TestContext context) {
        TestUtil.abstractCandleBlockDoesExtinguishOverTime(
                context,
                (AbstractCandleBlock) Blocks.CANDLE
        );
    }
}
