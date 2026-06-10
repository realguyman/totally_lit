package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.TotallyLit;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

public class CaretakerTestSuite {
    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void torchDoesNotExtinguishOverTimeWhenCaretakerIsPresent(TestContext context) {
        context.getWorld().getTickManager().setTickRate(TotallyLit.MAX_TICKS_TO_BURN_FOR);

        BlockPos pos = new BlockPos(1, 1, 1);
        context.spawnEntity(EntityType.VILLAGER, pos);

        context.setBlockState(pos, Blocks.TORCH);
        context.forceRandomTick(pos);

        context.expectBlockAtEnd(Blocks.TORCH, pos);
    }

    @GameTest(skyAccess = true)
    public void torchDoesNotExtinguishWhenRainedOnWhenCaretakerIsPresent(TestContext context) {
        final BlockPos pos = new BlockPos(1, 1, 1);

        context.setBlockState(pos, Blocks.TORCH);

        context.getWorld().setWeather(0, 20, true, false);

        context.spawnEntity(EntityType.VILLAGER, pos);

        context.forceRandomTick(pos);

        context.expectBlockAtEnd(Blocks.TORCH, pos);
    }
}
