package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.Initializer;
import io.github.realguyman.totally_lit.item.UnlitTorchItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {
    public static final Item UNLIT_TORCH = new UnlitTorchItem(BlockRegistry.UNLIT_TORCH, BlockRegistry.UNLIT_WALL_TORCH, new Item.Settings().group(ItemGroup.DECORATIONS));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(Initializer.IDENTIFIER, "unlit_torch"), UNLIT_TORCH);
    }
}
