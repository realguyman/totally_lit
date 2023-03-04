package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

public class LanternTestSuite {
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void lanternBlockDoesExtinguishOverTime(TestContext context) {
        TestUtil.blockDoesExtinguishOverTime(context, Blocks.LANTERN, BlockRegistry.UNLIT_LANTERN);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void lanternItemEntityDoesExtinguishWhenSubmergedInWater(TestContext context) {
        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(context, Items.LANTERN, ItemRegistry.UNLIT_LANTERN);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void lanternWaterloggedBlockDoesExtinguishWhenRandomlyTicked(TestContext context) {
        TestUtil.waterloggedBlockDoesExtinguishWhenRandomlyTicked(context, Blocks.LANTERN, BlockRegistry.UNLIT_LANTERN);
    }
}
