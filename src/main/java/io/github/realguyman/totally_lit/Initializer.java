package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;

public class Initializer implements ModInitializer {
    public static final String IDENTIFIER = "totally_lit";

    @Override
    public void onInitialize() {
        Configuration.load();
        BlockRegistry.register();
        ItemRegistry.register();
    }
}
