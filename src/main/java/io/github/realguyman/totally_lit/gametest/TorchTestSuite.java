package io.github.realguyman.totally_lit.gametest;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.util.TestUtil;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;

public class TorchTestSuite {
    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void torchBlockDoesExtinguishOverTime(GameTestHelper context) {
        TotallyLit.CONFIG.torches.extinguishOverTime(true);

        TestUtil.blockDoesExtinguishOverTime(
                context,
                Blocks.TORCH,
                BlockRegistry.UNLIT_TORCH
        );
    }

    @GameTest(maxTicks = TotallyLit.MAX_TICKS_TO_BURN_FOR)
    public void copperTorchBlockDoesExtinguishOverTime(GameTestHelper context) {
        TotallyLit.CONFIG.torches.extinguishOverTime(true);

        TestUtil.blockDoesExtinguishOverTime(
                context,
                Blocks.COPPER_TORCH,
                BlockRegistry.UNLIT_COPPER_TORCH
        );
    }

    @GameTest
    public void torchItemEntityDoesExtinguishWhenSubmergedInWater(
            GameTestHelper context
    ) {
        TotallyLit.CONFIG.itemEntitiesExtinguishWhenSubmerged(true);

        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(
                context,
                Items.TORCH,
                ItemRegistry.UNLIT_TORCH
        );
    }

    @GameTest
    public void copperTorchItemEntityDoesExtinguishWhenSubmergedInWater(
            GameTestHelper context
    ) {
        TotallyLit.CONFIG.itemEntitiesExtinguishWhenSubmerged(true);

        TestUtil.itemEntityDoesExtinguishWhenSubmergedInWater(
                context,
                Items.COPPER_TORCH,
                ItemRegistry.UNLIT_COPPER_TORCH
        );
    }

    @GameTest
    public void torchItemDoesNotExtinguishWhenSubmergedInWater(
            GameTestHelper context
    ) {
        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlock(pos, Blocks.WATER);
        context.spawnItem(Items.TORCH, pos);
        context.succeedWhenEntityData(pos, EntityType.ITEM, entity -> entity.getItem().getItem(), Items.TORCH);
    }

    @GameTest
    public void copperTorchItemDoesNotExtinguishWhenSubmergedInWater(
            GameTestHelper context
    ) {
        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlock(pos, Blocks.WATER);
        context.spawnItem(Items.COPPER_TORCH, pos);
        context.succeedWhenEntityData(pos, EntityType.ITEM, entity -> entity.getItem().getItem(), Items.COPPER_TORCH);
    }

    @GameTest(skyAccess = true)
    public void torchBlockDoesExtinguishInRain(GameTestHelper context) {
        TotallyLit.CONFIG.torches.extinguishOverTime(false);
        TotallyLit.CONFIG.torches.extinguishInRainChance(1.0F);

        context.getLevel().getWeatherData().setRaining(true);

        var lit = Blocks.TORCH;
        var unlit = BlockRegistry.UNLIT_TORCH;

        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlock(pos, lit.defaultBlockState());

        context.succeedWhen(() -> {
            context.randomTick(pos);
            context.assertBlockPresent(unlit, pos);
        });
    }

    @GameTest(skyAccess = true)
    public void copperTorchBlockDoesExtinguishInRain(GameTestHelper context) {
        TotallyLit.CONFIG.torches.extinguishOverTime(false);
        TotallyLit.CONFIG.torches.extinguishInRainChance(1.0F);

        context.getLevel().getWeatherData().setRaining(true);

        var lit = Blocks.COPPER_TORCH;
        var unlit = BlockRegistry.UNLIT_COPPER_TORCH;

        BlockPos pos = new BlockPos(1, 1, 1);
        context.setBlock(pos, lit.defaultBlockState());

        context.succeedWhen(() -> {
            context.randomTick(pos);
            context.assertBlockPresent(unlit, pos);
        });
    }

    @GameTest
    public void playerCanIgniteUnlitTorchOnGroundWithLitTorchInHand(GameTestHelper context) {
        var pos = new BlockPos(1, 1, 1);
        var player = context.makeMockPlayer(GameType.SURVIVAL);

        context.setBlock(pos, BlockRegistry.UNLIT_TORCH.defaultBlockState());
        player.setItemInHand(InteractionHand.MAIN_HAND, Items.TORCH.getDefaultInstance());
        context.useBlock(pos, player);

        context.succeedWhenBlockPresent(Blocks.TORCH, pos);
    }

    @GameTest
    public void playerCanIgniteUnlitTorchOnGroundWithFlintAndSteelInHand(GameTestHelper context) {
        var pos = new BlockPos(1, 1, 1);
        var player = context.makeMockPlayer(GameType.SURVIVAL);

        context.setBlock(pos, BlockRegistry.UNLIT_TORCH.defaultBlockState());
        player.setItemInHand(InteractionHand.MAIN_HAND, Items.FLINT_AND_STEEL.getDefaultInstance());
        context.useBlock(pos, player);

        context.succeedWhenBlockPresent(Blocks.TORCH, pos);
    }

    @GameTest
    public void playerCanIgniteUnlitCopperTorchOnGroundWithLitCopperTorchInHand(GameTestHelper context) {
        var pos = new BlockPos(1, 1, 1);
        var player = context.makeMockPlayer(GameType.SURVIVAL);

        context.setBlock(pos, BlockRegistry.UNLIT_COPPER_TORCH.defaultBlockState());
        player.setItemInHand(InteractionHand.MAIN_HAND, Items.COPPER_TORCH.getDefaultInstance());
        context.useBlock(pos, player);

        context.succeedWhenBlockPresent(Blocks.COPPER_TORCH, pos);
    }

    @GameTest
    public void playerCanIgniteUnlitCopperTorchOnGroundWithFlintAndSteelInHand(GameTestHelper context) {
        var pos = new BlockPos(1, 1, 1);
        var player = context.makeMockPlayer(GameType.SURVIVAL);

        context.setBlock(pos, BlockRegistry.UNLIT_COPPER_TORCH.defaultBlockState());
        player.setItemInHand(InteractionHand.MAIN_HAND, Items.FLINT_AND_STEEL.getDefaultInstance());
        context.useBlock(pos, player);

        context.succeedWhenBlockPresent(Blocks.COPPER_TORCH, pos);
    }
}
