package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class JackOLanternTestSuite {
    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void jackOLanternBlockDoesExtinguishOverTime(GameTestHelper context) {
        TestUtil.blockDoesExtinguishOverTime(
                context,
                Blocks.JACK_O_LANTERN,
                BlockRegistry.UNLIT_JACK_O_LANTERN
        );
    }

    @GameTest
    public void jackOLanternItemEntityDoesExtinguishWhenSubmergedInWater(
            GameTestHelper context
    ) {
        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(
                context,
                Items.JACK_O_LANTERN,
                ItemRegistry.UNLIT_JACK_O_LANTERN
        );
    }
}
