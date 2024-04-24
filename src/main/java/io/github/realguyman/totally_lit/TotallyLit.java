package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.TotallyLitConfig;
import io.github.realguyman.totally_lit.api.TotallyLitEntrypoint;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

// TODO: Ignition system: Items enchanted with Fire Aspect should ignite unlit blocks
// TODO: Ignition system: Unlit items should ignite when using on lit blocks
// TODO: Ignition system: Fire arrows should ignite unlit blocks
// TODO: Ignition system: Flint and steel should be damaged in crafting recipes


// TODO: Extinguish system: Add ability to extinguish light sources with potions
// TODO: Extinguish system: Add ability to extinguish light sources with water buckets in crafting recipes and in world


// TODO: Implement automatic texture generation for unlit variations of blocks with this technique: https://fabricmc.net/wiki/tutorial:datagen_buckets
// TODO: API: Implement custom Events for developers to subscribe to
// TODO: API: Move helpful classes, etc into API package, i.e., NoParticleTorchBlock
// TODO: Integrate: Tips, Patchouli, and Lanterns Belong on Walls
// TODO: Clean: Remove most custom Item and Block classes as they aren't needed
// TODO: Clean: Re-implement JSON files into Fabric's datagen to simplify project and prevent future errors
// TODO: Test: Implement testmod and more gametests
// TODO: Ensure dependencies are up to date
// TODO: Ensure GitHub integration+s are good
// TODO: Ensure Jitpack configuration is good
// TODO: Update README to specialize towards developers
// TODO: Update CHANGELOG
public class TotallyLit implements ModInitializer {
    public static final String MOD_ID = "totally_lit";
    public static final TotallyLitConfig CONFIG = TotallyLitConfig.createAndLoad();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final int MAX_TICKS_TO_BURN_FOR = 168_000;

    public static final Map<Block, Block> JACK_O_LANTERN_MAP = new HashMap<>();
    public static final Map<Block, Block> LANTERN_MAP = new HashMap<>();
    public static final Map<Block, Block> TORCH_MAP = new HashMap<>();

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getEntrypointContainers(MOD_ID, TotallyLitEntrypoint.class)
                .stream().map(EntrypointContainer::getEntrypoint).forEach(entrypoint -> {
                    entrypoint.buildMap();
                    LOGGER.debug("Built map for {}", entrypoint.getClass().getName());
                });

        ItemRegistry.register();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(listener -> {
            listener.addAfter(Items.JACK_O_LANTERN, ItemRegistry.UNLIT_JACK_O_LANTERN);
            listener.addAfter(Items.TORCH, ItemRegistry.UNLIT_TORCH);
            listener.addAfter(Items.SOUL_TORCH, ItemRegistry.UNLIT_SOUL_TORCH, ItemRegistry.GLOWSTONE_TORCH);
            listener.addAfter(Items.LANTERN, ItemRegistry.UNLIT_LANTERN);
            listener.addAfter(Items.SOUL_LANTERN, ItemRegistry.UNLIT_SOUL_LANTERN, ItemRegistry.GLOWSTONE_LANTERN);
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            return igniteUnlitBlock(player, world, hand, hitResult, LANTERN_MAP, TagRegistry.LANTERN_IGNITER_ITEMS);
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            return igniteUnlitBlock(player, world, hand, hitResult, JACK_O_LANTERN_MAP, TagRegistry.JACK_O_LANTERN_IGNITER_ITEMS);
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            return igniteUnlitBlock(player, world, hand, hitResult, TORCH_MAP, TagRegistry.TORCH_IGNITER_ITEMS);
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            return igniteUnlitItemInHand(
                    player,
                    world,
                    hand,
                    hitResult,
                    JACK_O_LANTERN_MAP,
                    TagRegistry.JACK_O_LANTERN_IGNITER_BLOCKS,
                    TagRegistry.JACK_O_LANTERN_IGNITER_FLUIDS
            );
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            return igniteUnlitItemInHandFromRaycast(
                    player,
                    world,
                    hand,
                    JACK_O_LANTERN_MAP,
                    TagRegistry.JACK_O_LANTERN_IGNITER_FLUIDS
            );
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            return igniteUnlitItemInHand(
                    player,
                    world,
                    hand,
                    hitResult,
                    LANTERN_MAP,
                    TagRegistry.LANTERN_IGNITER_BLOCKS,
                    TagRegistry.LANTERN_IGNITER_FLUIDS
            );
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            return igniteUnlitItemInHandFromRaycast(
                    player,
                    world,
                    hand,
                    LANTERN_MAP,
                    TagRegistry.LANTERN_IGNITER_FLUIDS
            );
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            return igniteUnlitItemInHand(
                    player,
                    world,
                    hand,
                    hitResult,
                    TORCH_MAP,
                    TagRegistry.TORCH_IGNITER_BLOCKS,
                    TagRegistry.TORCH_IGNITER_FLUIDS
            );
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            return igniteUnlitItemInHandFromRaycast(
                    player,
                    world,
                    hand,
                    TORCH_MAP,
                    TagRegistry.TORCH_IGNITER_FLUIDS
            );
        });
    }

    private ActionResult igniteUnlitItemInHand(
            PlayerEntity player,
            World world,
            Hand hand,
            BlockHitResult hitResult,
            Map<Block, Block> map,
            TagKey<Block> igniterBlocks,
            TagKey<Fluid> igniterFluids
    ) {
        final BlockPos pos = hitResult.getBlockPos();
        final BlockState state = world.getBlockState(pos);
        final boolean isIgniterFluid = world.getFluidState(pos.offset(hitResult.getSide())).isIn(igniterFluids);
        final boolean isIgniterBlock = state.isIn(igniterBlocks);


        if ((!isIgniterBlock && !isIgniterFluid) || player.isSneaking()) {
            return ActionResult.PASS;
        }

        final ItemStack stack = player.getStackInHand(hand);

        for (Map.Entry<Block, Block> entry : map.entrySet()) {
            final Item lit = entry.getKey().asItem();
            final Item unlit = entry.getValue().asItem();

            if (!stack.isOf(unlit)) {
                continue;
            }

            if (!player.giveItemStack(new ItemStack(lit))) {
                return ActionResult.FAIL;
            }

            stack.decrement(1);
            world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 0.125F, world.getRandom().nextFloat() * 0.5F + 0.125F);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private TypedActionResult<ItemStack> igniteUnlitItemInHandFromRaycast(
            PlayerEntity player,
            World world,
            Hand hand,
            Map<Block, Block> map,
            TagKey<Fluid> igniterFluids
    ) {
        final HitResult hit = player.raycast(3, 0, true);
        final BlockPos pos = ((BlockHitResult) hit).getBlockPos();
        final ItemStack stack = player.getStackInHand(hand);

        if (!world.getFluidState(pos).isIn(igniterFluids)) {
            return TypedActionResult.pass(stack);
        }

        for (Map.Entry<Block, Block> entry : map.entrySet()) {
            Item lit = entry.getKey().asItem();
            Item unlit = entry.getValue().asItem();

            if (!stack.isOf(unlit)) {
                continue;
            }

            if (!player.giveItemStack(new ItemStack(lit))) {
                return TypedActionResult.fail(stack);
            }

            stack.decrement(1);
            world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 0.125F, world.getRandom().nextFloat() * 0.5F + 0.125F);
            return TypedActionResult.success(stack, true);
        }

        return TypedActionResult.pass(stack);
    }

    private ActionResult igniteUnlitBlock(
            PlayerEntity player,
            World world,
            Hand hand,
            BlockHitResult hitResult,
            Map<Block, Block> map,
            TagKey<Item> igniters
    ) {
        final ItemStack stack = player.getStackInHand(hand);

        if (!stack.isIn(igniters) || player.isSneaking()) {
            return ActionResult.PASS;
        }

        final BlockPos pos = hitResult.getBlockPos();
        final BlockState state = world.getBlockState(pos);

        for (Map.Entry<Block, Block> entry : map.entrySet()) {
            final Block lit = entry.getKey();
            final Block unlit = entry.getValue();

            if (!state.isOf(unlit)) {
                continue;
            }

            if (!world.setBlockState(pos, lit.getStateWithProperties(state))) {
                return ActionResult.FAIL;
            }

            stack.damage(1, player, playerInScope -> playerInScope.sendToolBreakStatus(hand));
            world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 0.125F, world.getRandom().nextFloat() * 0.5F + 0.125F);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
