package io.github.realguyman.totally_lit;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

import static io.github.realguyman.totally_lit.registry.BlockRegistry.UNLIT_TORCH;
import static io.github.realguyman.totally_lit.registry.BlockRegistry.UNLIT_WALL_TORCH;

public class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), UNLIT_TORCH, UNLIT_WALL_TORCH);
    }
}
