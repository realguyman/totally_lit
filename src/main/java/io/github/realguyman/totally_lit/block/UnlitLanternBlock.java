package io.github.realguyman.totally_lit.block;

import net.minecraft.block.Block;
import net.minecraft.block.LanternBlock;

public class UnlitLanternBlock extends LanternBlock {
    private final Block litBlock;

    public UnlitLanternBlock(Settings settings, Block litBlock) {
        super(settings);
        this.litBlock = litBlock;
    }

    public Block getLitBlock() {
        return litBlock;
    }
}
