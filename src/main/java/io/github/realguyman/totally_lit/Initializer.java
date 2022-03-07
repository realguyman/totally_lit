package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.configuration.Configuration;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;

public class Initializer implements ModInitializer {
    public static final String IDENTIFIER = "totally_lit";
    private static final Configuration CONFIGURATION = AutoConfig.register(Configuration.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new)).getConfig();

    @Override
    public void onInitialize() {}

    public static Configuration getConfiguration() {
        return CONFIGURATION;
    }
}
