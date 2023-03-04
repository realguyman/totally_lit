package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

public class TotallyLitTestSuite {
    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = 168_000)
    public void torchExtinguishesOverTime(TestContext context) {
        testBlockExtinguishesOverTime(context, Blocks.TORCH, BlockRegistry.UNLIT_TORCH);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = 168_000)
    public void lanternExtinguishesOverTime(TestContext context) {
        testBlockExtinguishesOverTime(context, Blocks.LANTERN, BlockRegistry.UNLIT_LANTERN);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = 168_000)
    public void jackOLanternExtinguishesOverTime(TestContext context) {
        testBlockExtinguishesOverTime(context, Blocks.JACK_O_LANTERN, Blocks.CARVED_PUMPKIN);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = 168_000)
    public void campfireExtinguishesOverTime(TestContext context) {
        BlockPos pos = new BlockPos(0, 2, 0);
        context.setBlockState(pos, Blocks.CAMPFIRE.getDefaultState().with(CampfireBlock.LIT, true));
        context.addInstantFinalTask(() -> {
            context.expectBlockProperty(pos, CampfireBlock.LIT, false);
        });
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = 168_000)
    public void candleExtinguishesOverTime(TestContext context) {
        BlockPos pos = new BlockPos(0, 2, 0);
        context.setBlockState(pos, Blocks.CANDLE.getDefaultState().with(CandleBlock.LIT, true));
        context.forceRandomTick(pos);
        context.addInstantFinalTask(() -> {
            context.expectBlockProperty(pos, CandleBlock.LIT, false);
        });
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = 168_000)
    public void candleCakeExtinguishesOverTime(TestContext context) {
        BlockPos pos = new BlockPos(0, 2, 0);
        context.setBlockState(pos, Blocks.CANDLE_CAKE.getDefaultState().with(CandleCakeBlock.LIT, true));
        context.forceRandomTick(pos);
        context.addInstantFinalTask(() -> {
            context.expectBlockProperty(pos, CandleCakeBlock.LIT, false);
        });
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = 20)
    public void torchItemEntityExtinguishesWhenSubmerged(TestContext context) {
        testItemEntityExtinguishesInWater(context, Items.TORCH, ItemRegistry.UNLIT_TORCH);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = 20)
    public void soulTorchItemEntityExtinguishesWhenSubmerged(TestContext context) {
        testItemEntityExtinguishesInWater(context, Items.SOUL_TORCH, ItemRegistry.UNLIT_SOUL_TORCH);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = 20)
    public void lanternItemEntityExtinguishesWhenSubmerged(TestContext context) {
        testItemEntityExtinguishesInWater(context, Items.LANTERN, ItemRegistry.UNLIT_LANTERN);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = 20)
    public void soulLanternItemEntityExtinguishesWhenSubmerged(TestContext context) {
        testItemEntityExtinguishesInWater(context, Items.SOUL_LANTERN, ItemRegistry.UNLIT_SOUL_LANTERN);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, tickLimit = 20)
    public void jackOLanternItemEntityExtinguishesWhenSubmerged(TestContext context) {
        testItemEntityExtinguishesInWater(context, Items.JACK_O_LANTERN, Items.CARVED_PUMPKIN);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void waterloggedLanternExtinguishesWhenRandomlyTicked(TestContext context) {
        testWaterloggedLanternBlockExtinguishesWhenRandomlyTicked(context, Blocks.LANTERN, BlockRegistry.UNLIT_LANTERN);
    }

    @GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)
    public void waterloggedSoulLanternExtinguishesWhenRandomlyTicked(TestContext context) {
        testWaterloggedLanternBlockExtinguishesWhenRandomlyTicked(context, Blocks.SOUL_LANTERN, BlockRegistry.UNLIT_SOUL_LANTERN);
    }

    private void testBlockExtinguishesOverTime(TestContext context, Block from, Block to) {
        BlockPos pos = new BlockPos(0, 2, 0);
        context.setBlockState(pos, from);
        context.forceRandomTick(pos);
        context.expectBlockAtEnd(to, pos);
    }

    private void testItemEntityExtinguishesInWater(TestContext context, Item from, Item to) {
        BlockPos pos = new BlockPos(0, 2, 0);
        context.setBlockState(pos, Blocks.WATER);
        context.spawnItem(from, pos);
        context.expectEntityWithDataEnd(pos, EntityType.ITEM, entity -> entity.getStack().getItem(), to);
    }

    private void testWaterloggedLanternBlockExtinguishesWhenRandomlyTicked(TestContext context, Block from, Block to) {
        BlockPos pos = new BlockPos(0, 2, 0);
        context.setBlockState(pos, from.getDefaultState().with(LanternBlock.WATERLOGGED, true));
        context.forceRandomTick(pos);
        context.expectBlockAtEnd(to, pos);
    }
}
