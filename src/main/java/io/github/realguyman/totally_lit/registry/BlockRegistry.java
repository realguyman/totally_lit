package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.block.*;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockRegistry {
    public static final Block GLOWSTONE_TORCH;
    public static final Block GLOWSTONE_WALL_TORCH;
    public static final Block GLOWSTONE_LANTERN;
    public static final Block UNLIT_JACK_O_LANTERN;
    public static final Block UNLIT_LANTERN;
    public static final Block UNLIT_SOUL_LANTERN;
    public static final Block UNLIT_SOUL_TORCH;
    public static final Block UNLIT_SOUL_WALL_TORCH;
    public static final Block UNLIT_TORCH;
    public static final Block UNLIT_WALL_TORCH;

    private static Block add(String path, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(TotallyLit.MOD_ID, path), block);
    }

    static {
        GLOWSTONE_TORCH = add("glowstone_torch", new NoParticleTorchBlock(Settings.copy(Blocks.TORCH)));
        GLOWSTONE_WALL_TORCH = add("glowstone_wall_torch", new NoParticleWallTorchBlock(Settings.copy(Blocks.TORCH).dropsLike(GLOWSTONE_TORCH)));
        GLOWSTONE_LANTERN = add("glowstone_lantern", new LanternBlock(Settings.copy(Blocks.LANTERN)));
        UNLIT_JACK_O_LANTERN = add("unlit_jack_o_lantern", new CarvedPumpkinBlock(Settings.copy(Blocks.JACK_O_LANTERN).luminance(state -> 0)));
        UNLIT_LANTERN = add("unlit_lantern", new LanternBlock(Settings.copy(Blocks.LANTERN).luminance(state -> 0)));
        UNLIT_SOUL_LANTERN = add("unlit_soul_lantern", new LanternBlock(Settings.copy(UNLIT_LANTERN)));
        UNLIT_SOUL_TORCH = add("unlit_soul_torch", new NoParticleTorchBlock(Settings.copy(Blocks.TORCH).luminance(state -> 0)));
        UNLIT_SOUL_WALL_TORCH = add("unlit_soul_wall_torch", new NoParticleWallTorchBlock(Settings.copy(Blocks.WALL_TORCH).luminance(state -> 0).dropsLike(UNLIT_SOUL_TORCH)));
        UNLIT_TORCH = add("unlit_torch", new NoParticleTorchBlock(Settings.copy(Blocks.TORCH).luminance(state -> 0)));
        UNLIT_WALL_TORCH = add("unlit_wall_torch", new NoParticleWallTorchBlock(Settings.copy(Blocks.WALL_TORCH).luminance(state -> 0).dropsLike(UNLIT_TORCH)));
    }
}
