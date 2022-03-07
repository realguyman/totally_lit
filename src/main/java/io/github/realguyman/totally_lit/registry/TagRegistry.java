package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLitModInitializer;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TagRegistry {
    public static final TagKey<Block> TORCH_IGNITER_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier(TotallyLitModInitializer.IDENTIFIER, "torch_igniters"));
    public static final TagKey<Fluid> TORCH_IGNITER_FLUIDS = TagKey.of(Registry.FLUID_KEY, new Identifier(TotallyLitModInitializer.IDENTIFIER, "torch_igniters"));
}
