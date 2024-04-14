package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.MyModInitializer;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

public class TorchTestSuite {
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = MyModInitializer.MAX_TICKS_TO_BURN_FOR)
    public void torchBlockDoesExtinguishOverTime(TestContext context) {
        TestUtil.blockDoesExtinguishOverTime(context, Blocks.TORCH, BlockRegistry.UNLIT_TORCH);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void torchItemEntityDoesExtinguishWhenSubmergedInWater(TestContext context) {
        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(context, Items.TORCH, ItemRegistry.UNLIT_TORCH);
    }
}
