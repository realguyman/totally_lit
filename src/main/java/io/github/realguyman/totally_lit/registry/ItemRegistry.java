package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLitModInitializer;
import io.github.realguyman.totally_lit.api.item.UnlitLanternItem;
import io.github.realguyman.totally_lit.api.item.UnlitTorchItem;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {
    public static final Item GLOWSTONE_TORCH = add("glowstone_torch", new WallStandingBlockItem(BlockRegistry.GLOWSTONE_TORCH, BlockRegistry.GLOWSTONE_WALL_TORCH, new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static final Item GLOWSTONE_LANTERN = add("glowstone_lantern", new BlockItem(BlockRegistry.GLOWSTONE_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static final Item UNLIT_LANTERN = add("unlit_lantern", new UnlitLanternItem(BlockRegistry.UNLIT_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS), Items.LANTERN));
    public static final Item UNLIT_SOUL_LANTERN = add("unlit_soul_lantern", new UnlitLanternItem(BlockRegistry.UNLIT_SOUL_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS), Items.SOUL_LANTERN));
    public static final Item UNLIT_SOUL_TORCH = add("unlit_soul_torch", new UnlitTorchItem(BlockRegistry.UNLIT_SOUL_TORCH, BlockRegistry.UNLIT_SOUL_WALL_TORCH, new Item.Settings().group(ItemGroup.DECORATIONS), Items.SOUL_TORCH));
    public static final Item UNLIT_TORCH = add("unlit_torch", new UnlitTorchItem(BlockRegistry.UNLIT_TORCH, BlockRegistry.UNLIT_WALL_TORCH, new Item.Settings().group(ItemGroup.DECORATIONS), Items.TORCH));

    private static Item add(String path, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TotallyLitModInitializer.IDENTIFIER, path), item);
    }
}
