package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.api.block.NoParticleTorchBlock;
import io.github.realguyman.totally_lit.api.block.NoParticleWallTorchBlock;
import io.github.realguyman.totally_lit.references.BlockIds;
import io.github.realguyman.totally_lit.references.BlockItemIds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

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
    public static final Block UNLIT_COPPER_TORCH;
    public static final Block UNLIT_COPPER_WALL_TORCH;
    public static final WeatheringCopperCollection<Block> UNLIT_COPPER_LANTERN;

    static {
        GLOWSTONE_TORCH = add(
                "glowstone_torch",
                new NoParticleTorchBlock(
                        Properties.ofFullCopy(Blocks.TORCH)
                                .setId(BlockItemIds.GLOWSTONE_TORCH.block())
                )
        );

        GLOWSTONE_WALL_TORCH = add(
                "glowstone_wall_torch",
                new NoParticleWallTorchBlock(
                        Properties.ofFullCopy(Blocks.TORCH)
                                .overrideLootTable(GLOWSTONE_TORCH.getLootTable())
                                .overrideDescription(GLOWSTONE_TORCH.getDescriptionId())
                                .setId(BlockIds.UNLIT_GLOWSTONE_WALL_TORCH)
                )
        );

        GLOWSTONE_LANTERN = add(
                "glowstone_lantern",
                new LanternBlock(
                        Properties.ofFullCopy(Blocks.LANTERN)
                                .setId(BlockItemIds.GLOWSTONE_LANTERN.block())
                )
        );

        UNLIT_JACK_O_LANTERN = add(
                "unlit_jack_o_lantern",
                new CarvedPumpkinBlock(
                        Properties.ofFullCopy(Blocks.JACK_O_LANTERN)
                                .lightLevel(state -> 0)
                                .setId(BlockItemIds.UNLIT_JACK_O_LANTERN.block())
                )
        );

        UNLIT_LANTERN = add(
                "unlit_lantern",
                new LanternBlock(
                        Properties.ofFullCopy(Blocks.LANTERN)
                                .lightLevel(state -> 0)
                                .setId(BlockItemIds.UNLIT_LANTERN.block())
                )
        );

        UNLIT_SOUL_LANTERN = add(
                "unlit_soul_lantern",
                new LanternBlock(
                        Properties.ofFullCopy(UNLIT_LANTERN)
                                .setId(BlockItemIds.UNLIT_SOUL_LANTERN.block())
                )
        );

        UNLIT_SOUL_TORCH = add(
                "unlit_soul_torch",
                new NoParticleTorchBlock(
                        Properties.ofFullCopy(Blocks.TORCH)
                                .lightLevel(state -> 0)
                                .setId(BlockItemIds.UNLIT_SOUL_TORCH.block())
                )
        );

        UNLIT_SOUL_WALL_TORCH = add(
                "unlit_soul_wall_torch",
                new NoParticleWallTorchBlock(
                        Properties.ofFullCopy(Blocks.WALL_TORCH)
                                .lightLevel(state -> 0)
                                .overrideLootTable(UNLIT_SOUL_TORCH.getLootTable())
                                .overrideDescription(UNLIT_SOUL_TORCH.getDescriptionId())
                                .setId(BlockIds.UNLIT_SOUL_WALL_TORCH)
                )
        );

        UNLIT_TORCH = add("unlit_torch",
                new NoParticleTorchBlock(
                        Properties.ofFullCopy(Blocks.TORCH)
                                .lightLevel(state -> 0)
                                .setId(BlockItemIds.UNLIT_TORCH.block())
                )
        );

        UNLIT_WALL_TORCH = add("unlit_wall_torch",
                new NoParticleWallTorchBlock(
                        Properties.ofFullCopy(Blocks.WALL_TORCH)
                                .lightLevel(state -> 0)
                                .overrideLootTable(UNLIT_TORCH.getLootTable())
                                .overrideDescription(UNLIT_TORCH.getDescriptionId())
                                .setId(BlockIds.UNLIT_WALL_TORCH)
                )
        );

        UNLIT_COPPER_TORCH = add("unlit_copper_torch",
                new NoParticleTorchBlock(
                        Properties.ofFullCopy(Blocks.COPPER_TORCH)
                                .lightLevel(state -> 0)
                                .setId(BlockItemIds.UNLIT_COPPER_TORCH.block())
                )
        );

        UNLIT_COPPER_WALL_TORCH = add("unlit_copper_wall_torch",
                new NoParticleWallTorchBlock(
                        Properties.ofFullCopy(Blocks.COPPER_WALL_TORCH)
                                .lightLevel(state -> 0)
                                .overrideLootTable(UNLIT_COPPER_TORCH.getLootTable())
                                .overrideDescription(UNLIT_COPPER_TORCH.getDescriptionId())
                                .setId(BlockIds.UNLIT_COPPER_WALL_TORCH)
                )
        );

        UNLIT_COPPER_LANTERN = WeatheringCopperCollection.registerBlocks(
                BlockItemIds.UNLIT_COPPER_LANTERN,
                Blocks::register,
                (weatherState, properties) -> new LanternBlock(properties),
                WeatheringLanternBlock::new,
                (weatherState) -> Properties.ofFullCopy(Blocks.COPPER_LANTERN.weathering().pick(weatherState))
                        .lightLevel((blockState) -> 0)
        );
    }

    private static Block add(String path, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, path), block);
    }
}
