package io.github.realguyman.totally_lit.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.util.math.Direction;

public class LitTorchItem extends VerticallyAttachableBlockItem {
    private final Item unlitItem;

    public LitTorchItem(Block standingBlock, Block wallBlock, Settings settings, Direction verticalAttachmentDirection, Item unlitItem) {
        super(standingBlock, wallBlock, settings, verticalAttachmentDirection);
        this.unlitItem = unlitItem;
    }

    public Item getUnlitItem() {
        return unlitItem;
    }
}
