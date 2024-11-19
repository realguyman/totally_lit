package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TagRegistry {
    private static <T> TagKey<T> add(RegistryKey<? extends Registry<T>> registryKey, String path) {
        return TagKey.of(registryKey, Identifier.of(TotallyLit.MOD_ID, path));
    }
    
    private static TagKey<Block> addBlock(String path) {
        return add(RegistryKeys.BLOCK, path);
    }

    private static TagKey<Item> addItem(String path) {
        return add(RegistryKeys.ITEM, path);
    }

    private static TagKey<Fluid> addFluid(String path) {
        return add(RegistryKeys.FLUID, path);
    }

    private static TagKey<EntityType<?>> addEntityType(String path) {
        return add(RegistryKeys.ENTITY_TYPE, path);
    }

    public static final TagKey<Block> SOUL_FIRE_VARIANT_BLOCKS = addBlock("soul_fire_variants");
    public static final TagKey<Item> SOUL_FIRE_VARIANT_ITEMS = addItem("soul_fire_variants");

    public static final TagKey<Block> CAMPFIRE_BLOCKS = addBlock("campfires");
    public static final TagKey<Block> CAMPFIRE_IGNITER_BLOCKS = addBlock("campfire_igniters");
    public static final TagKey<Fluid> CAMPFIRE_IGNITER_FLUIDS = addFluid("campfire_igniters");
    public static final TagKey<Item> CAMPFIRE_IGNITER_ITEMS = addItem("campfire_igniters");
    public static final TagKey<Item> CAMPFIRE_ITEMS = addItem("campfires");

    public static final TagKey<Block> CANDLE_BLOCKS = addBlock("candles");
    public static final TagKey<Block> CANDLE_IGNITER_BLOCKS = addBlock("candle_igniters");
    public static final TagKey<Fluid> CANDLE_IGNITER_FLUIDS = addFluid("candle_igniters");
    public static final TagKey<Item> CANDLE_IGNITER_ITEMS = addItem("candle_igniters");
    public static final TagKey<Item> CANDLE_ITEMS = addItem("candles");

    public static final TagKey<Block> JACK_O_LANTERN_BLOCKS = addBlock("jack_o_lanterns");
    public static final TagKey<Block> JACK_O_LANTERN_IGNITER_BLOCKS = addBlock("jack_o_lantern_igniters");
    public static final TagKey<Fluid> JACK_O_LANTERN_IGNITER_FLUIDS = addFluid("jack_o_lantern_igniters");
    public static final TagKey<Item> JACK_O_LANTERN_IGNITER_ITEMS = addItem("jack_o_lantern_igniters");
    public static final TagKey<Item> JACK_O_LANTERN_ITEMS = addItem("jack_o_lanterns");

    public static final TagKey<Block> LANTERN_BLOCKS = addBlock("lanterns");
    public static final TagKey<Block> LANTERN_IGNITER_BLOCKS = addBlock("lantern_igniters");
    public static final TagKey<Fluid> LANTERN_IGNITER_FLUIDS = addFluid("lantern_igniters");
    public static final TagKey<Item> LANTERN_IGNITER_ITEMS = addItem("lantern_igniters");
    public static final TagKey<Item> LANTERN_ITEMS = addItem("lanterns");

    public static final TagKey<Block> TORCH_BLOCKS = addBlock("torches");
    public static final TagKey<Block> TORCH_IGNITER_BLOCKS = addBlock("torch_igniters");
    public static final TagKey<Fluid> TORCH_IGNITER_FLUIDS = addFluid("torch_igniters");
    public static final TagKey<Item> TORCH_IGNITER_ITEMS = addItem("torch_igniters");
    public static final TagKey<Item> TORCH_ITEMS = addItem("torches");

    public static final TagKey<EntityType<?>> CARETAKERS = addEntityType("caretakers");
}
