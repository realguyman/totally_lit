package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.Initializer;
import io.github.realguyman.totally_lit.block.UnlitTorchBlock;
import io.github.realguyman.totally_lit.block.UnlitWallTorchBlock;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class BlockRegistry {
    public static final Block UNLIT_TORCH = new UnlitTorchBlock();
    public static final Block UNLIT_WALL_TORCH = new UnlitWallTorchBlock();

    private static void add(String identifier, Block block) {
        Registry.register(Registry.BLOCK, new ResourceLocation(Initializer.IDENTIFIER, identifier), block);
    }

    public static void register() {
        add("unlit_torch", UNLIT_TORCH);
        add("unlit_wall_torch", UNLIT_WALL_TORCH);
    }
}
