package io.github.realguyman.totally_lit.references;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public class BlockIds {
    public static final ResourceKey<Block> UNLIT_WALL_TORCH = create("unlit_wall_torch");
    public static final ResourceKey<Block> UNLIT_COPPER_WALL_TORCH = create("unlit_copper_wall_torch");
    public static final ResourceKey<Block> UNLIT_SOUL_WALL_TORCH = create("unlit_soul_wall_torch");
    public static final ResourceKey<Block> UNLIT_GLOWSTONE_WALL_TORCH = create("unlit_glowstone_wall_torch");

    private static ResourceKey<Block> create(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, name));
    }
}
