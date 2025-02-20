package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.TotallyLitConfig;
import io.github.realguyman.totally_lit.api.TotallyLitEntrypoint;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
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

// TODO: Extinguish system: Add ability to extinguish light sources with water buckets in world
// TODO: Ignition system: Fire arrows should ignite unlit blocks
// TODO: Test: Implement more gametests and testmod
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
            return TypedActionResult.success(stack);
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
        final ItemStack stack              = player.getStackInHand(hand);
        final boolean   stackHasFireAspect = EnchantmentHelper.get(stack).containsKey(Enchantments.FIRE_ASPECT);

        if (player.isSneaking()) {
            return ActionResult.PASS;
        }

        if (!stack.isIn(igniters) && (!TotallyLit.CONFIG.fireAspectIgnitesUnlitVariants() || !stackHasFireAspect)) {
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

            stack.damage(1, player, (playerInScope) -> playerInScope.sendToolBreakStatus(hand));
            world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 0.125F, world.getRandom().nextFloat() * 0.5F + 0.125F);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
