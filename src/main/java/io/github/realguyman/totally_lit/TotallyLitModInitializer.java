package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.configuration.Configuration;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;

public class TotallyLitModInitializer implements ModInitializer {
    public static final String IDENTIFIER = "totally_lit";
    private static final Configuration CONFIGURATION = AutoConfig.register(Configuration.class, PartitioningSerializer.wrap(GsonConfigSerializer::new)).getConfig();

    @Override
    public void onInitialize() {
        new BlockRegistry();
        new ItemRegistry();
    }

    public static Configuration getConfiguration() {
        return CONFIGURATION;
    }
}
