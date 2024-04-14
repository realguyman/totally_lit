package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.MyModInitializer;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

public class CampfireTestSuite {
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = MyModInitializer.MAX_TICKS_TO_BURN_FOR)
    public void campfireBlockDoesExtinguishOverTime(TestContext context) {
        BlockPos pos = new BlockPos(0, 2, 0);
        context.setBlockState(pos, Blocks.CAMPFIRE.getDefaultState().with(CampfireBlock.LIT, true));
        context.addInstantFinalTask(() -> {
            context.expectBlockProperty(pos, CampfireBlock.LIT, false);
        });
    }
}
