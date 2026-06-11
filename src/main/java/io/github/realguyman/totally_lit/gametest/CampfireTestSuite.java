package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.TotallyLit;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;

public class CampfireTestSuite {
    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void campfireBlockDoesExtinguishOverTime(GameTestHelper context) {
        BlockPos pos = new BlockPos(0, 2, 0);

        context.setBlock(
                pos,
                Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, true)
        );

        context.succeedWhen(() -> {
            context.assertBlockProperty(pos, CampfireBlock.LIT, false);
        });
    }
}
