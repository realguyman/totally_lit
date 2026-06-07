package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.api.TotallyLitEntrypoint;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import net.minecraft.block.Blocks;

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

        addLantern(Blocks.COPPER_LANTERNS.unaffected(), BlockRegistry.UNLIT_COPPER_LANTERNS.unaffected());
        addLantern(Blocks.COPPER_LANTERNS.oxidized(), BlockRegistry.UNLIT_COPPER_LANTERNS.oxidized());
        addLantern(Blocks.COPPER_LANTERNS.weathered(), BlockRegistry.UNLIT_COPPER_LANTERNS.weathered());
        addLantern(Blocks.COPPER_LANTERNS.exposed(), BlockRegistry.UNLIT_COPPER_LANTERNS.exposed());
        addLantern(Blocks.COPPER_LANTERNS.waxed(), BlockRegistry.UNLIT_COPPER_LANTERNS.waxed());
        addLantern(Blocks.COPPER_LANTERNS.waxedOxidized(), BlockRegistry.UNLIT_COPPER_LANTERNS.waxedOxidized());
        addLantern(Blocks.COPPER_LANTERNS.waxedWeathered(), BlockRegistry.UNLIT_COPPER_LANTERNS.waxedWeathered());
        addLantern(Blocks.COPPER_LANTERNS.waxedExposed(), BlockRegistry.UNLIT_COPPER_LANTERNS.waxedExposed());
    }
}
