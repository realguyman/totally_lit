package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.configuration.Configuration;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

public class TotallyLit implements ModInitializer {
    public static final String IDENTIFIER = "totally_lit";
    private static final Configuration CONFIGURATION = AutoConfig.register(Configuration.class, PartitioningSerializer.wrap(GsonConfigSerializer::new)).getConfig();

    @Override
    public void onInitialize() {
        new BlockRegistry();
        new ItemRegistry();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(listener -> {
            listener.addAfter(Items.TORCH, ItemRegistry.UNLIT_TORCH);
            listener.addAfter(Items.SOUL_TORCH, ItemRegistry.UNLIT_SOUL_TORCH, ItemRegistry.GLOWSTONE_TORCH);
            listener.addAfter(Items.LANTERN, ItemRegistry.UNLIT_LANTERN);
            listener.addAfter(Items.SOUL_LANTERN, ItemRegistry.UNLIT_SOUL_LANTERN, ItemRegistry.GLOWSTONE_LANTERN);
        });
    }

    public static Configuration getConfiguration() {
        return CONFIGURATION;
    }
}
