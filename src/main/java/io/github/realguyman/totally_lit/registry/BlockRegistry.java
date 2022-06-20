package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLitModInitializer;
import io.github.realguyman.totally_lit.api.block.*;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {
    public static final Block GLOWSTONE_TORCH = add("glowstone_torch", new NoParticleTorchBlock(Settings.copy(Blocks.TORCH)));
    public static final Block GLOWSTONE_WALL_TORCH = add("glowstone_wall_torch", new NoParticleWallTorchBlock(Settings.copy(Blocks.TORCH).dropsLike(GLOWSTONE_TORCH)));
    public static final Block GLOWSTONE_LANTERN = add("glowstone_lantern", new LanternBlock(Settings.copy(Blocks.LANTERN)));
    public static final Block UNLIT_LANTERN = add("unlit_lantern", new UnlitLanternBlock(Settings.copy(Blocks.LANTERN).luminance(state -> 0), Blocks.LANTERN));
    public static final Block UNLIT_SOUL_LANTERN = add("unlit_soul_lantern", new UnlitLanternBlock(Settings.copy(UNLIT_LANTERN), Blocks.SOUL_LANTERN));
    public static final Block UNLIT_SOUL_TORCH = add("unlit_soul_torch", new UnlitTorchBlock(Settings.copy(Blocks.TORCH).luminance(state -> 0), Blocks.SOUL_TORCH));
    public static final Block UNLIT_SOUL_WALL_TORCH = add("unlit_soul_wall_torch", new UnlitWallTorchBlock(Settings.copy(Blocks.WALL_TORCH).luminance(state -> 0).dropsLike(UNLIT_SOUL_TORCH), Blocks.SOUL_WALL_TORCH));
    public static final Block UNLIT_TORCH = add("unlit_torch", new UnlitTorchBlock(Settings.copy(Blocks.TORCH).luminance(state -> 0), Blocks.TORCH));
    public static final Block UNLIT_WALL_TORCH = add("unlit_wall_torch", new UnlitWallTorchBlock(Settings.copy(Blocks.WALL_TORCH).luminance(state -> 0).dropsLike(UNLIT_TORCH), Blocks.WALL_TORCH));

    private static Block add(String path, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(TotallyLitModInitializer.IDENTIFIER, path), block);
    }
}
