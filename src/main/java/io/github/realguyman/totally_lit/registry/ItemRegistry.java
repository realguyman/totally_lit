package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.references.BlockItemIds;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.WeatheringCopperCollection;

public class ItemRegistry {
    public static final Item GLOWSTONE_TORCH = new StandingAndWallBlockItem(
            BlockRegistry.GLOWSTONE_TORCH,
            BlockRegistry.GLOWSTONE_WALL_TORCH,
            Direction.DOWN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(BlockItemIds.GLOWSTONE_TORCH.item())
    );

    public static final Item GLOWSTONE_LANTERN = new BlockItem(
            BlockRegistry.GLOWSTONE_LANTERN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(BlockItemIds.GLOWSTONE_LANTERN.item())
    );

    public static final Item UNLIT_JACK_O_LANTERN = new BlockItem(
            BlockRegistry.UNLIT_JACK_O_LANTERN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(BlockItemIds.UNLIT_JACK_O_LANTERN.item())
    );

    public static final Item UNLIT_LANTERN = new BlockItem(
            BlockRegistry.UNLIT_LANTERN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(BlockItemIds.UNLIT_LANTERN.item())
    );

    public static final Item UNLIT_SOUL_LANTERN = new BlockItem(
            BlockRegistry.UNLIT_SOUL_LANTERN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(BlockItemIds.UNLIT_SOUL_LANTERN.item())
    );

    public static final Item UNLIT_SOUL_TORCH = new StandingAndWallBlockItem(
            BlockRegistry.UNLIT_SOUL_TORCH,
            BlockRegistry.UNLIT_SOUL_WALL_TORCH,
            Direction.DOWN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(BlockItemIds.UNLIT_SOUL_TORCH.item())
    );

    public static final Item UNLIT_TORCH = new StandingAndWallBlockItem(
            BlockRegistry.UNLIT_TORCH,
            BlockRegistry.UNLIT_WALL_TORCH,
            Direction.DOWN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(BlockItemIds.UNLIT_TORCH.item())
    );

    public static final Item UNLIT_COPPER_TORCH = new StandingAndWallBlockItem(
            BlockRegistry.UNLIT_COPPER_TORCH,
            BlockRegistry.UNLIT_COPPER_WALL_TORCH,
            Direction.DOWN,
            new Item.Properties()
                    .useBlockDescriptionPrefix()
                    .setId(BlockItemIds.UNLIT_COPPER_TORCH.item())
    );

    public static final WeatheringCopperCollection<Item> UNLIT_COPPER_LANTERN = WeatheringCopperCollection.registerItems(
            BlockItemIds.UNLIT_COPPER_LANTERN,
            BlockRegistry.UNLIT_COPPER_LANTERN,
            Items::registerBlock
    );

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
