package io.github.realguyman.totally_lit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.realguyman.totally_lit.api.TotallyLitEntrypoint;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.BlockEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

// TODO: Extinguish system: Add ability to extinguish light sources with water buckets in world
// TODO: Ignition system: Fire arrows should ignite unlit blocks
// TODO: Consider implementing block entities to store data for torches,
//       lanterns, and jack o'lanterns to be better prepared for more
//       advanced features: such as modifying burn rates under certain
//       conditions.
// TODO: Consider using precipitation ticks instead of random ticks to
//       extinguish in the rain. May be better for performance and
//       semantics.
public class TotallyLit implements ModInitializer {
    public static final String MOD_ID = "totally_lit";
    public static final TotallyLitConfig CONFIG = TotallyLitConfig.createAndLoad();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final int MAX_TICKS_TO_BURN_FOR = 168_000;

    public static final Map<Block, Block> JACK_O_LANTERN_MAP = new HashMap<>();
    public static final Map<Block, Block> LANTERN_MAP = new HashMap<>();
    public static final Map<Block, Block> TORCH_MAP = new HashMap<>();

    public static final Cache<BlockPos, Boolean> CACHED_CARETAKER_BLOCKS = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(5))
            .maximumSize(16_384)
            .build();

    public static boolean isCaretakerPresent(BlockPos pos, ServerLevel world) {
        return CACHED_CARETAKER_BLOCKS.get(
                pos,
                key -> !world.getEntitiesOfClass(
                                Entity.class,
                                new AABB(key).inflate(TotallyLit.CONFIG.caretakerCheckRadius()),
                                EntitySelector.LIVING_ENTITY_STILL_ALIVE
                        ).stream()
                        .filter(entity -> entity.is(TagRegistry.CARETAKERS))
                        .toList()
                        .isEmpty()
        );
    }

    @Override
    public void onInitialize() {
        FabricLoader.getInstance()
                .getEntrypointContainers(MOD_ID, TotallyLitEntrypoint.class)
                .stream()
                .map(EntrypointContainer::getEntrypoint)
                .forEach(entrypoint -> {
                    entrypoint.buildMap();
                    LOGGER.debug("Built map for {}", entrypoint.getClass().getName());
                });

        ItemRegistry.register();

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(listener -> {
            listener.insertAfter(Items.JACK_O_LANTERN, ItemRegistry.UNLIT_JACK_O_LANTERN);
            listener.insertAfter(Items.TORCH, ItemRegistry.UNLIT_TORCH);
            listener.insertAfter(Items.SOUL_TORCH, ItemRegistry.UNLIT_SOUL_TORCH, ItemRegistry.GLOWSTONE_TORCH);
            listener.insertAfter(Items.COPPER_TORCH, ItemRegistry.UNLIT_COPPER_TORCH);
            listener.insertAfter(Items.LANTERN, ItemRegistry.UNLIT_LANTERN);
            listener.insertAfter(Items.SOUL_LANTERN, ItemRegistry.UNLIT_SOUL_LANTERN, ItemRegistry.GLOWSTONE_LANTERN);

            listener.insertAfter(Items.COPPER_LANTERN.exposed(), ItemRegistry.UNLIT_COPPER_LANTERNS.exposed());
            listener.insertAfter(Items.COPPER_LANTERN.oxidized(), ItemRegistry.UNLIT_COPPER_LANTERNS.oxidized());
            listener.insertAfter(Items.COPPER_LANTERN.weathered(), ItemRegistry.UNLIT_COPPER_LANTERNS.weathered());
            listener.insertAfter(Items.COPPER_LANTERN.unaffected(), ItemRegistry.UNLIT_COPPER_LANTERNS.unaffected());
            listener.insertAfter(Items.COPPER_LANTERN.waxedExposed(), ItemRegistry.UNLIT_COPPER_LANTERNS.waxedExposed());
            listener.insertAfter(Items.COPPER_LANTERN.waxedOxidized(), ItemRegistry.UNLIT_COPPER_LANTERNS.waxedOxidized());
            listener.insertAfter(Items.COPPER_LANTERN.waxedWeathered(), ItemRegistry.UNLIT_COPPER_LANTERNS.waxedWeathered());
            listener.insertAfter(Items.COPPER_LANTERN.waxed(), ItemRegistry.UNLIT_COPPER_LANTERNS.waxed());
        });

        OxidizableBlocksRegistry.registerWeatheringCopperBlocks(BlockRegistry.UNLIT_COPPER_LANTERNS);

        BlockEvents.USE_ITEM_ON.register((stack, state, world, blockPos, player, hand, hitResult) -> {
            return igniteUnlitBlock(player, world, hand, hitResult, LANTERN_MAP, TagRegistry.LANTERN_IGNITER_ITEMS);
        });

        BlockEvents.USE_ITEM_ON.register((stack, state, world, blockPos, player, hand, hitResult) -> {
            return igniteUnlitBlock(player, world, hand, hitResult, JACK_O_LANTERN_MAP, TagRegistry.JACK_O_LANTERN_IGNITER_ITEMS);
        });

        BlockEvents.USE_ITEM_ON.register((stack, state, world, blockPos, player, hand, hitResult) -> {
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

    private InteractionResult igniteUnlitItemInHand(
            Player player,
            Level world,
            InteractionHand hand,
            BlockHitResult hitResult,
            Map<Block, Block> map,
            TagKey<Block> igniterBlocks,
            TagKey<Fluid> igniterFluids
    ) {
        final BlockPos pos = hitResult.getBlockPos();
        final BlockState state = world.getBlockState(pos);
        final boolean isIgniterFluid = world.getFluidState(pos.relative(hitResult.getDirection())).is(igniterFluids);
        final boolean isIgniterBlock = state.is(igniterBlocks);


        if ((!isIgniterBlock && !isIgniterFluid) || player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }

        final ItemStack stack = player.getItemInHand(hand);

        for (Map.Entry<Block, Block> entry : map.entrySet()) {
            final Item lit = entry.getKey().asItem();
            final Item unlit = entry.getValue().asItem();

            if (!stack.is(unlit)) {
                continue;
            }

            if (!player.addItem(new ItemStack(lit))) {
                return InteractionResult.FAIL;
            }

            stack.shrink(1);
            world.playSound(null, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 0.125F, world.getRandom().nextFloat() * 0.5F + 0.125F);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private InteractionResult igniteUnlitItemInHandFromRaycast(
            Player player,
            Level world,
            InteractionHand hand,
            Map<Block, Block> map,
            TagKey<Fluid> igniterFluids
    ) {
        final HitResult hit = player.pick(3, 0, true);
        final BlockPos pos = ((BlockHitResult) hit).getBlockPos();
        final ItemStack stack = player.getItemInHand(hand);

        if (!world.getFluidState(pos).is(igniterFluids)) {
            return InteractionResult.PASS;
        }

        for (Map.Entry<Block, Block> entry : map.entrySet()) {
            Item lit = entry.getKey().asItem();
            Item unlit = entry.getValue().asItem();

            if (!stack.is(unlit)) {
                continue;
            }

            if (!player.addItem(new ItemStack(lit))) {
                return InteractionResult.FAIL;
            }

            stack.shrink(1);
            world.playSound(null, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 0.125F, world.getRandom().nextFloat() * 0.5F + 0.125F);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private InteractionResult igniteUnlitBlock(
            Player player,
            Level world,
            InteractionHand hand,
            BlockHitResult hitResult,
            Map<Block, Block> map,
            TagKey<Item> igniters
    ) {
        final ItemStack stack = player.getItemInHand(hand);

        final boolean hasFireAspect = stack.getEnchantments().keySet().contains(
                world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(
                        Enchantments.FIRE_ASPECT
                )
        );

        if (player.isShiftKeyDown()) {
            return null;
        }

        if (
                !stack.is(igniters) &&
                        (!TotallyLit.CONFIG.fireAspectIgnitesUnlitVariants() || !hasFireAspect)
        ) {
            return null;
        }

        final BlockPos pos = hitResult.getBlockPos();
        final BlockState state = world.getBlockState(pos);

        for (Map.Entry<Block, Block> entry : map.entrySet()) {
            final Block lit = entry.getKey();
            final Block unlit = entry.getValue();

            if (!state.is(unlit)) {
                continue;
            }

            if (!world.setBlockAndUpdate(pos, lit.withPropertiesOf(state))) {
                return InteractionResult.FAIL;
            }

            stack.hurtAndBreak(1, player, EquipmentSlot.values()[hand.ordinal()]);
            world.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 0.125F, world.getRandom().nextFloat() * 0.5F + 0.125F);
            return InteractionResult.SUCCESS;
        }

        return null;
    }
}
