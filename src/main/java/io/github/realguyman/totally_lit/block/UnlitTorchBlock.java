package io.github.realguyman.totally_lit.block;

import net.minecraft.block.Block;

public class UnlitTorchBlock extends NoParticleTorchBlock {
    private final Block litBlock;

    public UnlitTorchBlock(Settings settings, Block litBlock) {
        super(settings);
        this.litBlock = litBlock;
    }

    public Block getLitBlock() {
        return litBlock;
    }
}
