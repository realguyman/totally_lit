package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.api.block.NoParticleTorchBlock;
import io.github.realguyman.totally_lit.api.block.NoParticleWallTorchBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

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
    public static final WeatheringCopperBlocks UNLIT_COPPER_LANTERNS;

    private static Block add(String path, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, path), block);
    }

    static {
        GLOWSTONE_TORCH = add(
                "glowstone_torch",
                new NoParticleTorchBlock(
                        Properties.ofFullCopy(Blocks.TORCH)
                                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "glowstone_torch")))
                )
        );

        GLOWSTONE_WALL_TORCH = add(
                "glowstone_wall_torch",
                new NoParticleWallTorchBlock(
                        Properties.ofFullCopy(Blocks.TORCH)
                                .overrideLootTable(GLOWSTONE_TORCH.getLootTable())
                                .overrideDescription(GLOWSTONE_TORCH.getDescriptionId())
                                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "glowstone_wall_torch")))
                )
        );

        GLOWSTONE_LANTERN = add(
                "glowstone_lantern",
                new LanternBlock(
                        Properties.ofFullCopy(Blocks.LANTERN)
                                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "glowstone_lantern")))
                )
        );

        UNLIT_JACK_O_LANTERN = add(
                "unlit_jack_o_lantern",
                new CarvedPumpkinBlock(
                        Properties.ofFullCopy(Blocks.JACK_O_LANTERN)
                                .lightLevel(state -> 0)
                                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_jack_o_lantern")))
                )
        );

        UNLIT_LANTERN = add(
                "unlit_lantern",
                new LanternBlock(
                        Properties.ofFullCopy(Blocks.LANTERN)
                                .lightLevel(state -> 0)
                                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_lantern")))
                )
        );

        UNLIT_SOUL_LANTERN = add(
                "unlit_soul_lantern",
                new LanternBlock(
                        Properties.ofFullCopy(UNLIT_LANTERN)
                                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_soul_lantern")))
                )
        );

        UNLIT_SOUL_TORCH = add(
                "unlit_soul_torch",
                new NoParticleTorchBlock(
                        Properties.ofFullCopy(Blocks.TORCH)
                                .lightLevel(state -> 0)
                                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_soul_torch")))
                )
        );

        UNLIT_SOUL_WALL_TORCH = add(
                "unlit_soul_wall_torch",
                new NoParticleWallTorchBlock(
                        Properties.ofFullCopy(Blocks.WALL_TORCH)
                                .lightLevel(state -> 0)
                                .overrideLootTable(UNLIT_SOUL_TORCH.getLootTable())
                                .overrideDescription(UNLIT_SOUL_TORCH.getDescriptionId())
                                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_soul_wall_torch")))
                )
        );

        UNLIT_TORCH = add("unlit_torch",
                new NoParticleTorchBlock(
                        Properties.ofFullCopy(Blocks.TORCH)
                                .lightLevel(state -> 0)
                                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_torch")))
                )
        );

        UNLIT_WALL_TORCH = add("unlit_wall_torch",
                new NoParticleWallTorchBlock(
                        Properties.ofFullCopy(Blocks.WALL_TORCH)
                                .lightLevel(state -> 0)
                                .overrideLootTable(UNLIT_TORCH.getLootTable())
                                .overrideDescription(UNLIT_TORCH.getDescriptionId())
                                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_wall_torch")))
                )
        );

        UNLIT_COPPER_TORCH = add("unlit_copper_torch",
                new NoParticleTorchBlock(
                        Properties.ofFullCopy(Blocks.COPPER_TORCH)
                                .lightLevel(state -> 0)
                                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_copper_torch")))
                )
        );

        UNLIT_COPPER_WALL_TORCH = add("unlit_copper_wall_torch",
                new NoParticleWallTorchBlock(
                        Properties.ofFullCopy(Blocks.COPPER_WALL_TORCH)
                                .lightLevel(state -> 0)
                                .overrideLootTable(UNLIT_COPPER_TORCH.getLootTable())
                                .overrideDescription(UNLIT_COPPER_TORCH.getDescriptionId())
                                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, "unlit_copper_wall_torch")))
                )
        );

        UNLIT_COPPER_LANTERNS = WeatheringCopperBlocks.create(
                "unlit_copper_lantern",
                BlockRegistry::addCopperSet,
                LanternBlock::new,
                WeatheringLanternBlock::new,
                oxidationLevel -> BlockBehaviour.Properties.of()
                        .mapColor(MapColor.METAL)
                        .forceSolidOn()
                        .strength(3.5F)
                        .sound(SoundType.LANTERN)
                        .lightLevel(state -> 0)
                        .noOcclusion()
                        .pushReaction(PushReaction.DESTROY)
        );
    }

    private static Block addCopperSet(
            String id,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties settings
    ) {
        Block block = factory.apply(
                settings.setId(
                        ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, id))
                )
        );


        return add(id, block);
    }
}
