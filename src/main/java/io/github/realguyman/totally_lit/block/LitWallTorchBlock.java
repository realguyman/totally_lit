package io.github.realguyman.totally_lit.block;

import net.minecraft.block.Block;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particle.DefaultParticleType;

public class LitWallTorchBlock extends WallTorchBlock {
    private final Block unlitBlock;

    public LitWallTorchBlock(Settings settings, DefaultParticleType particleType, Block unlitBlock) {
        super(particleType, settings);
        this.unlitBlock = unlitBlock;
    }

    public Block getUnlitBlock() {
        return unlitBlock;
    }
}
