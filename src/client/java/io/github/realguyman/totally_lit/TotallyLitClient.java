package io.github.realguyman.totally_lit;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static io.github.realguyman.totally_lit.registry.BlockRegistry.*;

public class TotallyLitClient implements ClientModInitializer {
    public static final String MOD_ID = "totally_lit";

    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(
                    Identifier.of(MOD_ID, "unlit_campfire"),
                    modContainer,
                    Text.translatable("resourcePack.totally_lit.unlit_campfire.name"),
                    ResourcePackActivationType.DEFAULT_ENABLED
            );
        });

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
