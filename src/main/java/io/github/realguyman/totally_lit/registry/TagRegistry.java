package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.MyModInitializer;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TagRegistry {
    public static final TagKey<Block> SOUL_FIRE_VARIANT_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MyModInitializer.MOD_ID, "soul_fire_variants"));
    public static final TagKey<Item> SOUL_FIRE_VARIANT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MyModInitializer.MOD_ID, "soul_fire_variants"));

    public static final TagKey<Block> CAMPFIRE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MyModInitializer.MOD_ID, "campfires"));
    public static final TagKey<Block> CAMPFIRE_IGNITER_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MyModInitializer.MOD_ID, "campfire_igniters"));
    public static final TagKey<Fluid> CAMPFIRE_IGNITER_FLUIDS = TagKey.of(RegistryKeys.FLUID, new Identifier(MyModInitializer.MOD_ID, "campfire_igniters"));
    public static final TagKey<Item> CAMPFIRE_IGNITER_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MyModInitializer.MOD_ID, "campfire_igniters"));
    public static final TagKey<Item> CAMPFIRE_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MyModInitializer.MOD_ID, "campfires"));

    public static final TagKey<Block> CANDLE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MyModInitializer.MOD_ID, "candles"));
    public static final TagKey<Block> CANDLE_IGNITER_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MyModInitializer.MOD_ID, "candle_igniters"));
    public static final TagKey<Fluid> CANDLE_IGNITER_FLUIDS = TagKey.of(RegistryKeys.FLUID, new Identifier(MyModInitializer.MOD_ID, "candle_igniters"));
    public static final TagKey<Item> CANDLE_IGNITER_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MyModInitializer.MOD_ID, "candle_igniters"));
    public static final TagKey<Item> CANDLE_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MyModInitializer.MOD_ID, "candles"));

    public static final TagKey<Block> JACK_O_LANTERN_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MyModInitializer.MOD_ID, "jack_o_lanterns"));
    public static final TagKey<Block> JACK_O_LANTERN_IGNITER_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MyModInitializer.MOD_ID, "jack_o_lantern_igniters"));
    public static final TagKey<Fluid> JACK_O_LANTERN_IGNITER_FLUIDS = TagKey.of(RegistryKeys.FLUID, new Identifier(MyModInitializer.MOD_ID, "jack_o_lantern_igniters"));
    public static final TagKey<Item> JACK_O_LANTERN_IGNITER_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MyModInitializer.MOD_ID, "jack_o_lantern_igniters"));
    public static final TagKey<Item> JACK_O_LANTERN_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MyModInitializer.MOD_ID, "jack_o_lanterns"));

    public static final TagKey<Block> LANTERN_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MyModInitializer.MOD_ID, "lanterns"));
    public static final TagKey<Block> LANTERN_IGNITER_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MyModInitializer.MOD_ID, "lantern_igniters"));
    public static final TagKey<Fluid> LANTERN_IGNITER_FLUIDS = TagKey.of(RegistryKeys.FLUID, new Identifier(MyModInitializer.MOD_ID, "lantern_igniters"));
    public static final TagKey<Item> LANTERN_IGNITER_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MyModInitializer.MOD_ID, "lantern_igniters"));
    public static final TagKey<Item> LANTERN_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MyModInitializer.MOD_ID, "lanterns"));

    public static final TagKey<Block> TORCH_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MyModInitializer.MOD_ID, "torches"));
    public static final TagKey<Block> TORCH_IGNITER_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(MyModInitializer.MOD_ID, "torch_igniters"));
    public static final TagKey<Fluid> TORCH_IGNITER_FLUIDS = TagKey.of(RegistryKeys.FLUID, new Identifier(MyModInitializer.MOD_ID, "torch_igniters"));
    public static final TagKey<Item> TORCH_IGNITER_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MyModInitializer.MOD_ID, "torch_igniters"));
    public static final TagKey<Item> TORCH_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MyModInitializer.MOD_ID, "torches"));
}
