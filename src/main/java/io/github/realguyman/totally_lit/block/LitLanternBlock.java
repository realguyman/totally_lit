package io.github.realguyman.totally_lit.block;

import net.minecraft.block.Block;
import net.minecraft.block.LanternBlock;

public class LitLanternBlock extends LanternBlock {
    private final Block unlitBlock;

    public LitLanternBlock(Settings settings, Block unlitBlock) {
        super(settings);
        this.unlitBlock = unlitBlock;
    }

    public Block getUnlitBlock() {
        return unlitBlock;
    }
}
