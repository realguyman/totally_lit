package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.Initializer;
import io.github.realguyman.totally_lit.item.UnlitTorchItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {
    public static final Item UNLIT_LANTERN = new BlockItem(BlockRegistry.UNLIT_LANTERN, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final Item UNLIT_TORCH = new UnlitTorchItem(BlockRegistry.UNLIT_TORCH, BlockRegistry.UNLIT_WALL_TORCH, Items.TORCH, new Item.Settings().group(ItemGroup.DECORATIONS));

    private static void add(String path, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Initializer.IDENTIFIER, path), item);
    }

    public static void register() {
        add("unlit_lantern", UNLIT_LANTERN);
        add("unlit_torch", UNLIT_TORCH);
    }
}
