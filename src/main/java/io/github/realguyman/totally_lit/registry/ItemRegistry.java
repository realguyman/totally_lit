package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.item.UnlitLanternItem;
import io.github.realguyman.totally_lit.item.UnlitTorchItem;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ItemRegistry {
    public static final Item GLOWSTONE_TORCH = new VerticallyAttachableBlockItem(BlockRegistry.GLOWSTONE_TORCH, BlockRegistry.GLOWSTONE_WALL_TORCH, new Item.Settings(), Direction.DOWN);
    public static final Item GLOWSTONE_LANTERN = new BlockItem(BlockRegistry.GLOWSTONE_LANTERN, new Item.Settings());
    public static final Item UNLIT_JACK_O_LANTERN = new BlockItem(BlockRegistry.UNLIT_JACK_O_LANTERN, new Item.Settings());
    public static final Item UNLIT_LANTERN = new UnlitLanternItem(BlockRegistry.UNLIT_LANTERN, new Item.Settings(), Items.LANTERN);
    public static final Item UNLIT_SOUL_LANTERN = new UnlitLanternItem(BlockRegistry.UNLIT_SOUL_LANTERN, new Item.Settings(), Items.SOUL_LANTERN);
    public static final Item UNLIT_SOUL_TORCH = new UnlitTorchItem(BlockRegistry.UNLIT_SOUL_TORCH, BlockRegistry.UNLIT_SOUL_WALL_TORCH, new Item.Settings(), Direction.DOWN, Items.SOUL_TORCH);
    public static final Item UNLIT_TORCH = new UnlitTorchItem(BlockRegistry.UNLIT_TORCH, BlockRegistry.UNLIT_WALL_TORCH, new Item.Settings(), Direction.DOWN, Items.TORCH);

    private static Item add(String path, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TotallyLit.MOD_ID, path), item);
    }

    public static void register() {
        add("glowstone_torch", GLOWSTONE_TORCH);
        add("glowstone_lantern", GLOWSTONE_LANTERN);
        add("unlit_jack_o_lantern", UNLIT_JACK_O_LANTERN);
        add("unlit_lantern", UNLIT_LANTERN);
        add("unlit_soul_lantern", UNLIT_SOUL_LANTERN);
        add("unlit_soul_torch", UNLIT_SOUL_TORCH);
        add("unlit_torch", UNLIT_TORCH);
    }
}
