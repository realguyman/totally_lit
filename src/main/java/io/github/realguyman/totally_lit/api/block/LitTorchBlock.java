package io.github.realguyman.totally_lit.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.TorchBlock;
import net.minecraft.particle.ParticleEffect;

public class LitTorchBlock extends TorchBlock {
    private final Block unlitBlock;

    public LitTorchBlock(Settings settings, ParticleEffect particle, Block unlitBlock) {
        super(settings, particle);
        this.unlitBlock = unlitBlock;
    }

    public Block getUnlitBlock() {
        return unlitBlock;
    }
}
