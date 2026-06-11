package io.github.realguyman.totally_lit;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import static io.github.realguyman.totally_lit.registry.BlockRegistry.*;

public class TotallyLitClient implements ClientModInitializer {
    public static final String MOD_ID = "totally_lit";

    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
            ResourceLoader.registerBuiltinPack(
                    Identifier.fromNamespaceAndPath(MOD_ID, "unlit_campfire"),
                    modContainer,
                    Component.translatable("resourcePack.totally_lit.unlit_campfire.name"),
                    PackActivationType.DEFAULT_ENABLED
            );
        });

        BlockRenderLayerMap.putBlocks(
                ChunkSectionLayer.CUTOUT,
                GLOWSTONE_LANTERN,
                GLOWSTONE_TORCH,
                GLOWSTONE_WALL_TORCH,
                UNLIT_LANTERN,
                UNLIT_SOUL_LANTERN,
                UNLIT_SOUL_TORCH,
                UNLIT_SOUL_WALL_TORCH,
                UNLIT_COPPER_TORCH,
                UNLIT_COPPER_WALL_TORCH,
                UNLIT_TORCH,
                UNLIT_WALL_TORCH
        );

        UNLIT_COPPER_LANTERNS.forEach(block -> {
            BlockRenderLayerMap.putBlock(block, ChunkSectionLayer.CUTOUT);
        });
    }
}
