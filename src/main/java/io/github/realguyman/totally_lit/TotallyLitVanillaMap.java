package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.api.TotallyLitEntrypoint;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.world.level.block.Blocks;

public class TotallyLitVanillaMap implements TotallyLitEntrypoint {
    @Override
    public void buildMap() {
        addJackOLantern(Blocks.JACK_O_LANTERN, BlockRegistry.UNLIT_JACK_O_LANTERN);

        addLantern(Blocks.LANTERN, BlockRegistry.UNLIT_LANTERN);
        addLantern(Blocks.SOUL_LANTERN, BlockRegistry.UNLIT_SOUL_LANTERN);

        addTorch(Blocks.TORCH, BlockRegistry.UNLIT_TORCH);
        addTorch(Blocks.WALL_TORCH, BlockRegistry.UNLIT_WALL_TORCH);
        addTorch(Blocks.SOUL_TORCH, BlockRegistry.UNLIT_SOUL_TORCH);
        addTorch(Blocks.SOUL_WALL_TORCH, BlockRegistry.UNLIT_SOUL_WALL_TORCH);
        addTorch(Blocks.COPPER_TORCH, BlockRegistry.UNLIT_COPPER_TORCH);
        addTorch(Blocks.COPPER_WALL_TORCH, BlockRegistry.UNLIT_COPPER_WALL_TORCH);

        addLantern(Blocks.COPPER_LANTERN.weathering().unaffected(), BlockRegistry.UNLIT_COPPER_LANTERN.weathering().unaffected());
        addLantern(Blocks.COPPER_LANTERN.weathering().oxidized(), BlockRegistry.UNLIT_COPPER_LANTERN.weathering().oxidized());
        addLantern(Blocks.COPPER_LANTERN.weathering().weathered(), BlockRegistry.UNLIT_COPPER_LANTERN.weathering().weathered());
        addLantern(Blocks.COPPER_LANTERN.weathering().exposed(), BlockRegistry.UNLIT_COPPER_LANTERN.weathering().exposed());
        addLantern(Blocks.COPPER_LANTERN.waxed().unaffected(), BlockRegistry.UNLIT_COPPER_LANTERN.waxed().unaffected());
        addLantern(Blocks.COPPER_LANTERN.waxed().oxidized(), BlockRegistry.UNLIT_COPPER_LANTERN.waxed().oxidized());
        addLantern(Blocks.COPPER_LANTERN.waxed().weathered(), BlockRegistry.UNLIT_COPPER_LANTERN.waxed().weathered());
        addLantern(Blocks.COPPER_LANTERN.waxed().exposed(), BlockRegistry.UNLIT_COPPER_LANTERN.waxed().exposed());
    }
}
