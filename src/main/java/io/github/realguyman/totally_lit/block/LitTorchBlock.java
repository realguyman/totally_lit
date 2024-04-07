package io.github.realguyman.totally_lit.block;

import net.minecraft.block.Block;
import net.minecraft.block.TorchBlock;
import net.minecraft.particle.DefaultParticleType;

public class LitTorchBlock extends TorchBlock {
    private final Block unlitBlock;

    public LitTorchBlock(Settings settings, DefaultParticleType particleType, Block unlitBlock) {
        super(particleType, settings);
        this.unlitBlock = unlitBlock;
    }

    public Block getUnlitBlock() {
        return unlitBlock;
    }
}
