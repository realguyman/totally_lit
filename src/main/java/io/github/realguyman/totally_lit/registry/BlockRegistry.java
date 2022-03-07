package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.Initializer;
import io.github.realguyman.totally_lit.block.UnlitLanternBlock;
import io.github.realguyman.totally_lit.block.UnlitTorchBlock;
import io.github.realguyman.totally_lit.block.UnlitWallTorchBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {
    public static final Block UNLIT_LANTERN = add("unlit_lantern", new UnlitLanternBlock());
    public static final Block UNLIT_TORCH = add("unlit_torch", new UnlitTorchBlock());
    public static final Block UNLIT_WALL_TORCH = add("unlit_wall_torch", new UnlitWallTorchBlock());

    private static Block add(String path, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(Initializer.IDENTIFIER, path), block);
    }
}
