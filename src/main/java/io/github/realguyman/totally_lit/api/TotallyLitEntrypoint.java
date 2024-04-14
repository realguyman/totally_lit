package io.github.realguyman.totally_lit.api;

import io.github.realguyman.totally_lit.MyModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface TotallyLitEntrypoint {
    /**
     * Build a map correlating lit blocks and items to their unlit counterparts.
     */
    void buildMap();

    default void put(Block lit, Block unlit) {
        MyModInitializer.BLOCK_MAP.put(lit, unlit);
    }

    default void put(Item lit, Item unlit) {
        MyModInitializer.ITEM_MAP.put(lit, unlit);
    }
}
