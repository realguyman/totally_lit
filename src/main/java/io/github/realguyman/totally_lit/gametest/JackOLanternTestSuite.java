package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

public class JackOLanternTestSuite {
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void jackOLanternBlockDoesExtinguishOverTime(TestContext context) {
        TestUtil.blockDoesExtinguishOverTime(context, Blocks.JACK_O_LANTERN, Blocks.CARVED_PUMPKIN);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void jackOLanternItemEntityDoesExtinguishWhenSubmergedInWater(TestContext context) {
        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(context, Items.JACK_O_LANTERN, Items.CARVED_PUMPKIN);
    }
}
