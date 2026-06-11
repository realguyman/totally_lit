package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class TotallyLitBlockLootTableProvider extends FabricBlockLootSubProvider {
    public TotallyLitBlockLootTableProvider(
            FabricPackOutput output,
            CompletableFuture<HolderLookup.Provider> future
    ) {
        super(output, future);
    }

    @Override
    public void generate() {
        addDrops(
                BlockRegistry.UNLIT_TORCH,
                BlockRegistry.UNLIT_SOUL_TORCH,
                BlockRegistry.UNLIT_COPPER_TORCH,
                BlockRegistry.UNLIT_LANTERN,
                BlockRegistry.UNLIT_SOUL_LANTERN,
                BlockRegistry.UNLIT_JACK_O_LANTERN,
                BlockRegistry.GLOWSTONE_TORCH,
                BlockRegistry.GLOWSTONE_LANTERN
        );
    }

    private void addDrops(Block... blocks) {
        for (Block block : blocks) dropSelf(block);
    }
}
