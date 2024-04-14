package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.MyConfig;
import io.github.realguyman.totally_lit.api.TotallyLitEntrypoint;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class MyModInitializer implements ModInitializer {
    public static final String MOD_ID = "totally_lit";
    public static final MyConfig CONFIG = MyConfig.createAndLoad();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final int MAX_TICKS_TO_BURN_FOR = 168_000;

    public static final HashMap<Block, Block> BLOCK_MAP = new HashMap<>();
    public static final HashMap<Item, Item> ITEM_MAP = new HashMap<>();

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getEntrypointContainers(MOD_ID, TotallyLitEntrypoint.class)
                .stream().map(EntrypointContainer::getEntrypoint).forEach(TotallyLitEntrypoint::buildMap);

        ItemRegistry.register();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(listener -> {
            listener.addAfter(Items.TORCH, ItemRegistry.UNLIT_TORCH);
            listener.addAfter(Items.SOUL_TORCH, ItemRegistry.UNLIT_SOUL_TORCH, ItemRegistry.GLOWSTONE_TORCH);
            listener.addAfter(Items.LANTERN, ItemRegistry.UNLIT_LANTERN);
            listener.addAfter(Items.SOUL_LANTERN, ItemRegistry.UNLIT_SOUL_LANTERN, ItemRegistry.GLOWSTONE_LANTERN);
        });
    }
}
