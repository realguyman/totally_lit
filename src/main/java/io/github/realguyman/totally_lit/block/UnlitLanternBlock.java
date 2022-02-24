package io.github.realguyman.totally_lit.block;

import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;

public class UnlitLanternBlock extends LanternBlock {
    public UnlitLanternBlock() {
        super(Settings.copy(Blocks.LANTERN).luminance(state -> 0));
    }
}
