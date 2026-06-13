package io.github.realguyman.totally_lit.client;

import io.github.realguyman.totally_lit.TotallyLit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class TotallyLitClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getModContainer(TotallyLit.MOD_ID).ifPresent(modContainer -> {
            ResourceLoader.registerBuiltinPack(
                    Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_campfire"),
                    modContainer,
                    Component.translatable("resourcePack.totally_lit.unlit_campfire.name"),
                    PackActivationType.DEFAULT_ENABLED
            );
        });
    }
}
