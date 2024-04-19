package io.github.realguyman.totally_lit.api;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.Block;

public interface TotallyLitEntrypoint {
    /**
     * Build a map correlating lit blocks and items to their unlit counterparts.
     */
    void buildMap();

    default void addTorchBlock(Block lit, Block unlit) {
        TotallyLit.TORCH_MAP.put(lit, unlit);
    }

    default void addLanternBlock(Block lit, Block unlit) {
        TotallyLit.LANTERN_MAP.put(lit, unlit);
    }

    default void addJackOLanternBlock(Block lit, Block unlit) {
        TotallyLit.JACK_O_LANTERN_MAP.put(lit, unlit);
    }
}
