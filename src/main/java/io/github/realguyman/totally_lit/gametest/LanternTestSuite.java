package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;

public class LanternTestSuite {
    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void lanternBlockDoesExtinguishOverTime(GameTestHelper context) {
        TestUtil.blockDoesExtinguishOverTime(
                context,
                Blocks.LANTERN,
                BlockRegistry.UNLIT_LANTERN
        );
    }

    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void unwaxedCopperLanternBlockDoesExtinguishOverTime(GameTestHelper context) {
        context.getLevel().tickRateManager().setTickRate(TotallyLit.MAX_TICKS_TO_BURN_FOR);

        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlock(pos, Blocks.COPPER_LANTERN.unaffected());
        context.randomTick(pos);

        context.succeedWhen(() -> {
            context.assertTrue(BlockRegistry.UNLIT_COPPER_LANTERNS.asList().contains(context.getBlockState(pos).getBlock()), "Was not one of the unwaxed unlit copper lanterns");
        });
    }

    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void waxedCopperLanternBlockDoesExtinguishOverTime(GameTestHelper context) {
        TestUtil.blockDoesExtinguishOverTime(
                context,
                Blocks.COPPER_LANTERN.waxed(),
                BlockRegistry.UNLIT_COPPER_LANTERNS.waxed()
        );
    }

    @GameTest
    public void lanternItemEntityDoesExtinguishWhenSubmergedInWater(
            GameTestHelper context
    ) {
        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(
                context,
                Items.LANTERN,
                ItemRegistry.UNLIT_LANTERN
        );
    }

    @GameTest
    public void lanternWaterloggedBlockDoesExtinguishWhenRandomlyTicked(
            GameTestHelper context
    ) {
        TestUtil.waterloggedBlockDoesExtinguishWhenRandomlyTicked(
                context,
                Blocks.LANTERN,
                BlockRegistry.UNLIT_LANTERN
        );
    }

    @GameTest
    public void playerCanIgniteUnlitLanternOnGroundWithFlintAndSteelInHand(GameTestHelper context) {
        var pos = new BlockPos(0, 0, 0);
        var player = context.makeMockPlayer(GameType.SURVIVAL);

        context.setBlock(pos, BlockRegistry.UNLIT_LANTERN.defaultBlockState());
        player.setItemInHand(InteractionHand.MAIN_HAND, Items.FLINT_AND_STEEL.getDefaultInstance());
        context.useBlock(pos, player);

        context.succeedWhenBlockPresent(Blocks.LANTERN, pos);
    }
}
