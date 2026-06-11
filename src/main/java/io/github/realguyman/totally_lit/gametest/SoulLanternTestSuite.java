package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class SoulLanternTestSuite {
    @GameTest
    public void soulLanternItemEntityDoesExtinguishWhenSubmergedInWater(
            GameTestHelper context
    ) {
        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(
                context,
                Items.SOUL_LANTERN,
                ItemRegistry.UNLIT_SOUL_LANTERN
        );
    }

    @GameTest
    public void soulLanternWaterloggedBlockDoesExtinguishWhenRandomlyTicked(
            GameTestHelper context
    ) {
        TestUtil.waterloggedBlockDoesExtinguishWhenRandomlyTicked(
                context,
                Blocks.SOUL_LANTERN,
                BlockRegistry.UNLIT_SOUL_LANTERN
        );
    }
}
