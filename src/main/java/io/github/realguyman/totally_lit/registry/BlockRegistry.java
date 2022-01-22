package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.Initializer;
import io.github.realguyman.totally_lit.block.UnlitTorchBlock;
import io.github.realguyman.totally_lit.block.UnlitWallTorchBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {
    public static final Block UNLIT_TORCH = new UnlitTorchBlock();
    public static final Block UNLIT_WALL_TORCH = new UnlitWallTorchBlock();

    private static void add(String path, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(Initializer.IDENTIFIER, path), block);
    }

    public static void register() {
        add("unlit_torch", UNLIT_TORCH);
        add("unlit_wall_torch", UNLIT_WALL_TORCH);
    }
}
