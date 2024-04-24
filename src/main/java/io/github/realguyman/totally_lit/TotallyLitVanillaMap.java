package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.api.TotallyLitEntrypoint;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.block.Blocks;

public class TotallyLitVanillaMap implements TotallyLitEntrypoint {
    @Override
    public void buildMap() {
        addJackOLantern(Blocks.JACK_O_LANTERN, BlockRegistry.UNLIT_JACK_O_LANTERN);

        addLantern(Blocks.LANTERN, BlockRegistry.UNLIT_LANTERN);
        addLantern(Blocks.SOUL_LANTERN, BlockRegistry.UNLIT_SOUL_LANTERN);

        addTorch(Blocks.TORCH, BlockRegistry.UNLIT_TORCH);
        addTorch(Blocks.WALL_TORCH, BlockRegistry.UNLIT_WALL_TORCH);
        addTorch(Blocks.SOUL_TORCH, BlockRegistry.UNLIT_SOUL_TORCH);
        addTorch(Blocks.SOUL_WALL_TORCH, BlockRegistry.UNLIT_SOUL_WALL_TORCH);
    }
}
