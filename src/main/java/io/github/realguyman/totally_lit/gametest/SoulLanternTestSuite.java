package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

public class SoulLanternTestSuite {
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void soulLanternItemEntityDoesExtinguishWhenSubmergedInWater(TestContext context) {
        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(context, Items.SOUL_LANTERN, ItemRegistry.UNLIT_SOUL_LANTERN);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void soulLanternWaterloggedBlockDoesExtinguishWhenRandomlyTicked(TestContext context) {
        TestUtil.waterloggedBlockDoesExtinguishWhenRandomlyTicked(context, Blocks.SOUL_LANTERN, BlockRegistry.UNLIT_SOUL_LANTERN);
    }
}
