package io.github.realguyman.totally_lit.api;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.block.Block;

public interface TotallyLitEntrypoint {
    /**
     * Build a map correlating lit blocks and items to their unlit counterparts.
     */
    void buildMap();

    /**
     * Correlate a lit torch to an unlit torch.
     *
     * @param lit   The torch considered ignited.
     * @param unlit The torch considered extinguished.
     */
    default void addTorch(Block lit, Block unlit) {
        TotallyLit.TORCH_MAP.put(lit, unlit);
    }

    /**
     * Correlate a lit lantern to an unlit lantern.
     *
     * @param lit   The lantern considered ignited.
     * @param unlit The lantern considered extinguished.
     */
    default void addLantern(Block lit, Block unlit) {
        TotallyLit.LANTERN_MAP.put(lit, unlit);
    }

    /**
     * Correlate a lit jack o'lantern to an unlit jack o'lantern.
     *
     * @param lit   The jack o'lantern considered ignited.
     * @param unlit The jack o'lantern considered extinguished.
     */
    default void addJackOLantern(Block lit, Block unlit) {
        TotallyLit.JACK_O_LANTERN_MAP.put(lit, unlit);
    }
}
