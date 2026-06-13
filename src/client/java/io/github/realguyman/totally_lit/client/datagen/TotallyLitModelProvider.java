package io.github.realguyman.totally_lit.client.datagen;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import org.jspecify.annotations.NonNull;

public class TotallyLitModelProvider extends FabricModelProvider {
    public TotallyLitModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        generator.createNormalTorch(BlockRegistry.UNLIT_TORCH, BlockRegistry.UNLIT_WALL_TORCH);
        generator.createNormalTorch(BlockRegistry.UNLIT_SOUL_TORCH, BlockRegistry.UNLIT_SOUL_WALL_TORCH);
        generator.createNormalTorch(BlockRegistry.UNLIT_COPPER_TORCH, BlockRegistry.UNLIT_COPPER_WALL_TORCH);
        generator.createNormalTorch(BlockRegistry.GLOWSTONE_TORCH, BlockRegistry.GLOWSTONE_WALL_TORCH);
        generator.createLantern(BlockRegistry.UNLIT_LANTERN);
        generator.createLantern(BlockRegistry.UNLIT_SOUL_LANTERN);
        generator.createLantern(BlockRegistry.GLOWSTONE_LANTERN);
        BlockRegistry.UNLIT_COPPER_LANTERNS.waxedMapping().forEach(generator::createCopperLantern);
    }

    @Override
    public void generateItemModels(@NonNull ItemModelGenerators itemModelGenerator) {
    }
}
