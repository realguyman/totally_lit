package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;

public class BlockLootTableDatagen extends FabricBlockLootTableProvider {
    public BlockLootTableDatagen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrops(
                BlockRegistry.UNLIT_TORCH,
                BlockRegistry.UNLIT_SOUL_TORCH,
                BlockRegistry.UNLIT_LANTERN,
                BlockRegistry.UNLIT_SOUL_LANTERN,
                BlockRegistry.UNLIT_JACK_O_LANTERN,
                BlockRegistry.GLOWSTONE_TORCH,
                BlockRegistry.GLOWSTONE_LANTERN
        );
    }

    private void addDrops(Block ...blocks) {
        for (Block block : blocks) addDrop(block);
    }
}
