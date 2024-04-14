package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TagRegistry {
    public static final TagKey<Block> TORCH_IGNITER_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(TotallyLit.MOD_ID, "torch_igniters"));
    public static final TagKey<Fluid> TORCH_IGNITER_FLUIDS = TagKey.of(RegistryKeys.FLUID, new Identifier(TotallyLit.MOD_ID, "torch_igniters"));
    public static final TagKey<Item> TORCH_IGNITER_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(TotallyLit.MOD_ID, "torch_igniters"));
    public static final TagKey<Item> CAMPFIRE_IGNITER_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(TotallyLit.MOD_ID, "campfire_igniters"));
    public static final TagKey<Item> LANTERN_IGNITER_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(TotallyLit.MOD_ID, "lantern_igniters"));
    public static final TagKey<Block> LANTERN_IGNITER_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(TotallyLit.MOD_ID, "lantern_igniters"));
    public static final TagKey<Fluid> LANTERN_IGNITER_FLUIDS = TagKey.of(RegistryKeys.FLUID, new Identifier(TotallyLit.MOD_ID, "lantern_igniters"));
}
