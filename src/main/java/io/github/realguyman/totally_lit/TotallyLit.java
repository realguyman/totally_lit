package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.registry.ItemRegistry;
import io.github.realguyman.totally_lit.TotallyLitConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TotallyLit implements ModInitializer {
    public static final String MOD_ID = "totally_lit";
    public static final TotallyLitConfig CONFIG = TotallyLitConfig.createAndLoad();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final int MAX_TICKS_TO_BURN_FOR = 168_000;

    @Override
    public void onInitialize() {
        ItemRegistry.register();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(listener -> {
            listener.addAfter(Items.TORCH, ItemRegistry.UNLIT_TORCH);
            listener.addAfter(Items.SOUL_TORCH, ItemRegistry.UNLIT_SOUL_TORCH, ItemRegistry.GLOWSTONE_TORCH);
            listener.addAfter(Items.LANTERN, ItemRegistry.UNLIT_LANTERN);
            listener.addAfter(Items.SOUL_LANTERN, ItemRegistry.UNLIT_SOUL_LANTERN, ItemRegistry.GLOWSTONE_LANTERN);
        });
    }
}
