package io.github.realguyman.totally_lit.api.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class UnlitLanternItem extends BlockItem {
    private final Item litItem;

    public UnlitLanternItem(Block block, Settings settings, Item litItem) {
        super(block, settings);
        this.litItem = litItem;
    }

    public Item getLitItem() {
        return litItem;
    }
}
