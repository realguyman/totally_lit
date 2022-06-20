package io.github.realguyman.totally_lit.api.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class LitLanternItem extends BlockItem {
    private final Item unlitItem;

    public LitLanternItem(Block block, Settings settings, Item unlitItem) {
        super(block, settings);
        this.unlitItem = unlitItem;
    }

    public Item getUnlitItem() {
        return unlitItem;
    }
}
