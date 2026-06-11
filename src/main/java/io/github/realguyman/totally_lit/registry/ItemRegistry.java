package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.WeatheringCopperItems;

public class ItemRegistry {
    public static final Item GLOWSTONE_TORCH = new StandingAndWallBlockItem(
            BlockRegistry.GLOWSTONE_TORCH,
            BlockRegistry.GLOWSTONE_WALL_TORCH,
            Direction.DOWN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "glowstone_torch")))
    );

    public static final Item GLOWSTONE_LANTERN = new BlockItem(
            BlockRegistry.GLOWSTONE_LANTERN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "glowstone_lantern")))
    );

    public static final Item UNLIT_JACK_O_LANTERN = new BlockItem(
            BlockRegistry.UNLIT_JACK_O_LANTERN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_jack_o_lantern")))
    );

    public static final Item UNLIT_LANTERN = new BlockItem(
            BlockRegistry.UNLIT_LANTERN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_lantern")))
    );

    public static final Item UNLIT_SOUL_LANTERN = new BlockItem(
            BlockRegistry.UNLIT_SOUL_LANTERN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_soul_lantern")))
    );

    public static final Item UNLIT_SOUL_TORCH = new StandingAndWallBlockItem(
            BlockRegistry.UNLIT_SOUL_TORCH,
            BlockRegistry.UNLIT_SOUL_WALL_TORCH,
            Direction.DOWN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_soul_torch")))
    );

    public static final Item UNLIT_TORCH = new StandingAndWallBlockItem(
            BlockRegistry.UNLIT_TORCH,
            BlockRegistry.UNLIT_WALL_TORCH,
            Direction.DOWN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_torch")))
    );

    public static final Item UNLIT_COPPER_TORCH = new StandingAndWallBlockItem(
            BlockRegistry.UNLIT_COPPER_TORCH,
            BlockRegistry.UNLIT_COPPER_WALL_TORCH,
            Direction.DOWN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_copper_torch")))
    );

    public static final WeatheringCopperItems UNLIT_COPPER_LANTERNS = WeatheringCopperItems.create(BlockRegistry.UNLIT_COPPER_LANTERNS, Items::registerBlock);

    private static Item add(String path, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, path), item);
    }

    public static void register() {
        add("glowstone_torch", GLOWSTONE_TORCH);
        add("glowstone_lantern", GLOWSTONE_LANTERN);
        add("unlit_jack_o_lantern", UNLIT_JACK_O_LANTERN);
        add("unlit_lantern", UNLIT_LANTERN);
        add("unlit_soul_lantern", UNLIT_SOUL_LANTERN);
        add("unlit_soul_torch", UNLIT_SOUL_TORCH);
        add("unlit_torch", UNLIT_TORCH);
        add("unlit_copper_torch", UNLIT_COPPER_TORCH);
    }
}
