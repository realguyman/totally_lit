package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;

public class SoulTorchTestSuite {
    @GameTest
    public void soulTorchItemEntityDoesExtinguishWhenSubmergedInWater(
            GameTestHelper context
    ) {
        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(
                context,
                Items.SOUL_TORCH,
                ItemRegistry.UNLIT_SOUL_TORCH
        );
    }
}
