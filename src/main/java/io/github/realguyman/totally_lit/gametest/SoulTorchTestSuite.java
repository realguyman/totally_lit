package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.item.Items;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

public class SoulTorchTestSuite {
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void soulTorchItemEntityDoesExtinguishWhenSubmergedInWater(TestContext context) {
        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(context, Items.SOUL_TORCH, ItemRegistry.UNLIT_SOUL_TORCH);
    }
}
