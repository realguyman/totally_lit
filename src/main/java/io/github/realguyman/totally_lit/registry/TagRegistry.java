package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLitModInitializer;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TagRegistry {
    public static final TagKey<Block> TORCH_IGNITER_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier(TotallyLitModInitializer.IDENTIFIER, "torch_igniters"));
    public static final TagKey<Fluid> TORCH_IGNITER_FLUIDS = TagKey.of(Registry.FLUID_KEY, new Identifier(TotallyLitModInitializer.IDENTIFIER, "torch_igniters"));
    public static final TagKey<Item> TORCH_IGNITER_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier(TotallyLitModInitializer.IDENTIFIER, "torch_igniters"));
    public static final TagKey<Block> EXTINGUISHABLE_TORCH_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier(TotallyLitModInitializer.IDENTIFIER, "extinguishable_torches"));
    public static final TagKey<Item> EXTINGUISHABLE_TORCH_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier(TotallyLitModInitializer.IDENTIFIER, "extinguishable_torches"));
}
