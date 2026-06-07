package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.test.TestContext;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;

public class LanternTestSuite {
    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void lanternBlockDoesExtinguishOverTime(TestContext context) {
        TestUtil.blockDoesExtinguishOverTime(
                context,
                Blocks.LANTERN,
                BlockRegistry.UNLIT_LANTERN
        );
    }

    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void unwaxedCopperLanternBlockDoesExtinguishOverTime(TestContext context) {
        context.getWorld().getTickManager().setTickRate(TotallyLit.MAX_TICKS_TO_BURN_FOR);

        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlockState(pos, Blocks.COPPER_LANTERNS.unaffected());
        context.forceRandomTick(pos);

        context.addInstantFinalTask(() -> {
            context.assertTrue(BlockRegistry.UNLIT_COPPER_LANTERNS.getAll().contains(context.getBlockState(pos).getBlock()), "Was not one of the unwaxed unlit copper lanterns");
        });
    }

    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void waxedCopperLanternBlockDoesExtinguishOverTime(TestContext context) {
        TestUtil.blockDoesExtinguishOverTime(
                context,
                Blocks.COPPER_LANTERNS.waxed(),
                BlockRegistry.UNLIT_COPPER_LANTERNS.waxed()
        );
    }

    @GameTest
    public void lanternItemEntityDoesExtinguishWhenSubmergedInWater(
            TestContext context
    ) {
        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(
                context,
                Items.LANTERN,
                ItemRegistry.UNLIT_LANTERN
        );
    }

    @GameTest
    public void lanternWaterloggedBlockDoesExtinguishWhenRandomlyTicked(
            TestContext context
    ) {
        TestUtil.waterloggedBlockDoesExtinguishWhenRandomlyTicked(
                context,
                Blocks.LANTERN,
                BlockRegistry.UNLIT_LANTERN
        );
    }

    @GameTest
    public void playerCanIgniteUnlitLanternOnGroundWithFlintAndSteelInHand(TestContext context) {
        var pos = new BlockPos(0, 0, 0);
        var player = context.createMockPlayer(GameMode.SURVIVAL);

        context.setBlockState(pos, BlockRegistry.UNLIT_LANTERN.getDefaultState());
        player.setStackInHand(Hand.MAIN_HAND, Items.FLINT_AND_STEEL.getDefaultStack());
        context.useBlock(pos, player);

        context.expectBlockAtEnd(Blocks.LANTERN, pos);
    }
}
