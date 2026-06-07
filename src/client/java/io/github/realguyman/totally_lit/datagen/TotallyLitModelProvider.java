package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import org.jspecify.annotations.NonNull;

public class TotallyLitModelProvider extends FabricModelProvider {
    public TotallyLitModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerTorch(BlockRegistry.UNLIT_TORCH, BlockRegistry.UNLIT_WALL_TORCH);
        generator.registerTorch(BlockRegistry.UNLIT_SOUL_TORCH, BlockRegistry.UNLIT_SOUL_WALL_TORCH);
        generator.registerTorch(BlockRegistry.UNLIT_COPPER_TORCH, BlockRegistry.UNLIT_COPPER_WALL_TORCH);
        generator.registerTorch(BlockRegistry.GLOWSTONE_TORCH, BlockRegistry.GLOWSTONE_WALL_TORCH);
        generator.registerLantern(BlockRegistry.UNLIT_LANTERN);
        generator.registerLantern(BlockRegistry.UNLIT_SOUL_LANTERN);
        generator.registerLantern(BlockRegistry.GLOWSTONE_LANTERN);
        BlockRegistry.UNLIT_COPPER_LANTERNS.getWaxingMap().forEach(generator::registerCopperLantern);
    }

    @Override
    public void generateItemModels(@NonNull ItemModelGenerator itemModelGenerator) {
    }
}
