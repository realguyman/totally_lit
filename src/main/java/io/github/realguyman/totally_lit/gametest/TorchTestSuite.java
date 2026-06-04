package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.test.TestContext;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;

public class TorchTestSuite {
    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void torchBlockDoesExtinguishOverTime(TestContext context) {
        TotallyLit.CONFIG.torches.extinguishOverTime(true);

        TestUtil.blockDoesExtinguishOverTime(
                context,
                Blocks.TORCH,
                BlockRegistry.UNLIT_TORCH
        );
    }

    @GameTest
    public void torchItemEntityDoesExtinguishWhenSubmergedInWater(
            TestContext context
    ) {
        TotallyLit.CONFIG.itemEntitiesExtinguishWhenSubmerged(true);

        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(
                context,
                Items.TORCH,
                ItemRegistry.UNLIT_TORCH
        );
    }

    @GameTest
    public void torchItemDoesNotExtinguishWhenSubmergedInWater(
            TestContext context
    ) {
        BlockPos pos = new BlockPos(0, 0, 0);
        context.setBlockState(pos, Blocks.WATER);
        context.spawnItem(Items.TORCH, pos);
        context.expectEntityWithDataEnd(pos, EntityType.ITEM, entity -> entity.getStack().getItem(), Items.TORCH);
    }

    @GameTest(skyAccess = true)
    public void torchBlockDoesExtinguishInRain(TestContext context) {
        TotallyLit.CONFIG.torches.extinguishOverTime(false);
        TotallyLit.CONFIG.torches.extinguishInRainChance(1.0F);

        context.getWorld().setWeather(0, 20, true, false);

        var lit = Blocks.TORCH;
        var unlit = BlockRegistry.UNLIT_TORCH;

        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlockState(pos, lit.getDefaultState());

        context.addInstantFinalTask(() -> {
            context.forceRandomTick(pos);
            context.expectBlock(unlit, pos);
        });
    }

    @GameTest
    public void playerCanIgniteUnlitTorchOnGroundWithLitTorchInHand(TestContext context) {
        var pos = new BlockPos(0, 0, 0);
        var player = context.createMockPlayer(GameMode.SURVIVAL);

        context.setBlockState(pos, BlockRegistry.UNLIT_TORCH.getDefaultState());
        player.setStackInHand(Hand.MAIN_HAND, Items.TORCH.getDefaultStack());
        context.useBlock(pos, player);

        context.expectBlockAtEnd(Blocks.TORCH, pos);
    }
}
