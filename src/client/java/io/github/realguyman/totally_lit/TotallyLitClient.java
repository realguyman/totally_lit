package io.github.realguyman.totally_lit;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

import static io.github.realguyman.totally_lit.registry.BlockRegistry.*;

public class TotallyLitClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderLayer.getCutout(),
                GLOWSTONE_LANTERN,
                GLOWSTONE_TORCH,
                GLOWSTONE_WALL_TORCH,
                UNLIT_LANTERN,
                UNLIT_SOUL_LANTERN,
                UNLIT_SOUL_TORCH,
                UNLIT_SOUL_WALL_TORCH,
                UNLIT_TORCH,
                UNLIT_WALL_TORCH
        );
    }
}
