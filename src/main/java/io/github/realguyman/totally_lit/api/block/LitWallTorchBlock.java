package io.github.realguyman.totally_lit.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particle.ParticleEffect;

public class LitWallTorchBlock extends WallTorchBlock {
    private final Block unlitBlock;

    public LitWallTorchBlock(Settings settings, ParticleEffect particleEffect, Block unlitBlock) {
        super(settings, particleEffect);
        this.unlitBlock = unlitBlock;
    }

    public Block getUnlitBlock() {
        return unlitBlock;
    }
}
