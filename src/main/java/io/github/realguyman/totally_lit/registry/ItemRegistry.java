package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ItemRegistry {
    public static final Item GLOWSTONE_TORCH = new VerticallyAttachableBlockItem(
            BlockRegistry.GLOWSTONE_TORCH,
            BlockRegistry.GLOWSTONE_WALL_TORCH,
            Direction.DOWN,
            new Item.Settings()
                    .useBlockPrefixedTranslationKey()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TotallyLit.MOD_ID, "glowstone_torch")))
    );

    public static final Item GLOWSTONE_LANTERN = new BlockItem(
            BlockRegistry.GLOWSTONE_LANTERN,
            new Item.Settings()
                    .useBlockPrefixedTranslationKey()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TotallyLit.MOD_ID, "glowstone_lantern")))
    );

    public static final Item UNLIT_JACK_O_LANTERN = new BlockItem(
            BlockRegistry.UNLIT_JACK_O_LANTERN,
            new Item.Settings()
                    .useBlockPrefixedTranslationKey()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TotallyLit.MOD_ID, "unlit_jack_o_lantern")))
    );

    public static final Item UNLIT_LANTERN = new BlockItem(
            BlockRegistry.UNLIT_LANTERN,
            new Item.Settings()
                    .useBlockPrefixedTranslationKey()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TotallyLit.MOD_ID, "unlit_lantern")))
    );

    public static final Item UNLIT_SOUL_LANTERN = new BlockItem(
            BlockRegistry.UNLIT_SOUL_LANTERN,
            new Item.Settings()
                    .useBlockPrefixedTranslationKey()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TotallyLit.MOD_ID, "unlit_soul_lantern")))
    );

    public static final Item UNLIT_SOUL_TORCH = new VerticallyAttachableBlockItem(
            BlockRegistry.UNLIT_SOUL_TORCH,
            BlockRegistry.UNLIT_SOUL_WALL_TORCH,
            Direction.DOWN,
            new Item.Settings()
                    .useBlockPrefixedTranslationKey()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TotallyLit.MOD_ID, "unlit_soul_torch")))
    );

    public static final Item UNLIT_TORCH = new VerticallyAttachableBlockItem(
            BlockRegistry.UNLIT_TORCH,
            BlockRegistry.UNLIT_WALL_TORCH,
            Direction.DOWN,
            new Item.Settings()
                    .useBlockPrefixedTranslationKey()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TotallyLit.MOD_ID, "unlit_torch")))
    );

    private static Item add(String path, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(TotallyLit.MOD_ID, path), item);
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
