package io.github.realguyman.totally_lit.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.WallStandingBlockItem;

public class LitTorchItem extends WallStandingBlockItem {
    private final Item unlitItem;

    public LitTorchItem(Block standingBlock, Block wallBlock, Settings settings, Item unlitItem) {
        super(standingBlock, wallBlock, settings);
        this.unlitItem = unlitItem;
    }

    public Item getUnlitItem() {
        return unlitItem;
    }
}
