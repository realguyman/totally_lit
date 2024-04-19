package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.api.TotallyLitEntrypoint;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.block.Blocks;

public class TotallyLitVanillaMap implements TotallyLitEntrypoint {
    public void buildMap() {
        this.addJackOLanternBlock(Blocks.JACK_O_LANTERN, BlockRegistry.UNLIT_JACK_O_LANTERN);

        this.addLanternBlock(Blocks.LANTERN, BlockRegistry.UNLIT_LANTERN);
        this.addLanternBlock(Blocks.SOUL_LANTERN, BlockRegistry.UNLIT_SOUL_LANTERN);

        this.addTorchBlock(Blocks.TORCH, BlockRegistry.UNLIT_TORCH);
        this.addTorchBlock(Blocks.WALL_TORCH, BlockRegistry.UNLIT_WALL_TORCH);
        this.addTorchBlock(Blocks.SOUL_TORCH, BlockRegistry.UNLIT_SOUL_TORCH);
        this.addTorchBlock(Blocks.SOUL_WALL_TORCH, BlockRegistry.UNLIT_SOUL_WALL_TORCH);
    }
}
