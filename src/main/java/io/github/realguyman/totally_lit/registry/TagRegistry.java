package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.Initializer;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TagRegistry {
    public static final TagKey<Block> TORCH_IGNITERS = TagKey.of(Registry.BLOCK_KEY, new Identifier(Initializer.IDENTIFIER, "torch_igniters"));
}
