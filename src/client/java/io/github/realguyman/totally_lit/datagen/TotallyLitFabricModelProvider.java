package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class TotallyLitFabricModelProvider extends FabricModelProvider {
    public TotallyLitFabricModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerTorch(BlockRegistry.UNLIT_TORCH, BlockRegistry.UNLIT_WALL_TORCH);
        generator.registerTorch(BlockRegistry.UNLIT_SOUL_TORCH, BlockRegistry.UNLIT_SOUL_WALL_TORCH);
        generator.registerTorch(BlockRegistry.GLOWSTONE_TORCH, BlockRegistry.GLOWSTONE_WALL_TORCH);
        generator.registerLantern(BlockRegistry.UNLIT_LANTERN);
        generator.registerLantern(BlockRegistry.UNLIT_SOUL_LANTERN);
        generator.registerLantern(BlockRegistry.GLOWSTONE_LANTERN);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {}
}
