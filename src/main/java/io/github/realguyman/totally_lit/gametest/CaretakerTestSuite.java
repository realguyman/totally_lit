package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.TotallyLit;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;

public class CaretakerTestSuite {
    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void torchDoesNotExtinguishOverTimeWhenCaretakerIsPresent(GameTestHelper context) {
        context.getLevel().tickRateManager().setTickRate(TotallyLit.MAX_TICKS_TO_BURN_FOR);

        BlockPos pos = new BlockPos(1, 1, 1);
        context.spawn(EntityType.VILLAGER, pos);

        context.setBlock(pos, Blocks.TORCH);
        context.randomTick(pos);

        context.succeedWhenBlockPresent(Blocks.TORCH, pos);
    }

    @GameTest(skyAccess = true)
    public void torchDoesNotExtinguishWhenRainedOnWhenCaretakerIsPresent(GameTestHelper context) {
        final BlockPos pos = new BlockPos(1, 1, 1);

        context.setBlock(pos, Blocks.TORCH);

        context.getLevel().setWeatherParameters(0, 20, true, false);

        context.spawn(EntityType.VILLAGER, pos);

        context.randomTick(pos);

        context.succeedWhenBlockPresent(Blocks.TORCH, pos);
    }
}
