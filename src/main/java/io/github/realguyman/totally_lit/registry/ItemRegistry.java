package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.Initializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;

public class ItemRegistry {
    public static final Item UNLIT_TORCH = new StandingAndWallBlockItem(BlockRegistry.UNLIT_TORCH, BlockRegistry.UNLIT_WALL_TORCH, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    public static void register() {
        Registry.register(Registry.ITEM, new ResourceLocation(Initializer.IDENTIFIER, "unlit_torch"), UNLIT_TORCH);
    }
}
