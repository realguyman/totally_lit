package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLitModInitializer;
import io.github.realguyman.totally_lit.block.UnlitLanternBlock;
import io.github.realguyman.totally_lit.block.UnlitTorchBlock;
import io.github.realguyman.totally_lit.block.UnlitWallTorchBlock;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {
    public static final Block UNLIT_LANTERN = add("unlit_lantern", new UnlitLanternBlock(Settings.copy(Blocks.LANTERN).luminance(state -> 0), Blocks.LANTERN));
    public static final Block UNLIT_TORCH = add("unlit_torch", new UnlitTorchBlock(Settings.copy(Blocks.TORCH).luminance(state -> 0), null, Blocks.TORCH));
    public static final Block UNLIT_WALL_TORCH = add("unlit_wall_torch", new UnlitWallTorchBlock(Settings.copy(Blocks.WALL_TORCH).luminance(state -> 0), null, Blocks.WALL_TORCH));

    private static Block add(String path, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(TotallyLitModInitializer.IDENTIFIER, path), block);
    }
}
