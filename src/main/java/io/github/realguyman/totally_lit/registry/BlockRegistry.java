package io.github.realguyman.totally_lit.registry;

import io.github.realguyman.totally_lit.TotallyLit;
import io.github.realguyman.totally_lit.api.block.NoParticleTorchBlock;
import io.github.realguyman.totally_lit.api.block.NoParticleWallTorchBlock;
import net.minecraft.block.*;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

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
    public static final CopperBlockSet UNLIT_COPPER_LANTERNS;

    private static Block add(String path, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(TotallyLit.MOD_ID, path), block);
    }

    static {
        GLOWSTONE_TORCH = add(
                "glowstone_torch",
                new NoParticleTorchBlock(
                        Settings.copy(Blocks.TORCH)
                                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, "glowstone_torch")))
                )
        );

        GLOWSTONE_WALL_TORCH = add(
                "glowstone_wall_torch",
                new NoParticleWallTorchBlock(
                        Settings.copy(Blocks.TORCH)
                                .lootTable(GLOWSTONE_TORCH.getLootTableKey())
                                .overrideTranslationKey(GLOWSTONE_TORCH.getTranslationKey())
                                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, "glowstone_wall_torch")))
                )
        );

        GLOWSTONE_LANTERN = add(
                "glowstone_lantern",
                new LanternBlock(
                        Settings.copy(Blocks.LANTERN)
                                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, "glowstone_lantern")))
                )
        );

        UNLIT_JACK_O_LANTERN = add(
                "unlit_jack_o_lantern",
                new CarvedPumpkinBlock(
                        Settings.copy(Blocks.JACK_O_LANTERN)
                                .luminance(state -> 0)
                                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, "unlit_jack_o_lantern")))
                )
        );

        UNLIT_LANTERN = add(
                "unlit_lantern",
                new LanternBlock(
                        Settings.copy(Blocks.LANTERN)
                                .luminance(state -> 0)
                                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, "unlit_lantern")))
                )
        );

        UNLIT_SOUL_LANTERN = add(
                "unlit_soul_lantern",
                new LanternBlock(
                        Settings.copy(UNLIT_LANTERN)
                                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, "unlit_soul_lantern")))
                )
        );

        UNLIT_SOUL_TORCH = add(
                "unlit_soul_torch",
                new NoParticleTorchBlock(
                        Settings.copy(Blocks.TORCH)
                                .luminance(state -> 0)
                                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, "unlit_soul_torch")))
                )
        );

        UNLIT_SOUL_WALL_TORCH = add(
                "unlit_soul_wall_torch",
                new NoParticleWallTorchBlock(
                        Settings.copy(Blocks.WALL_TORCH)
                                .luminance(state -> 0)
                                .lootTable(UNLIT_SOUL_TORCH.getLootTableKey())
                                .overrideTranslationKey(UNLIT_SOUL_TORCH.getTranslationKey())
                                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, "unlit_soul_wall_torch")))
                )
        );

        UNLIT_TORCH = add("unlit_torch",
                new NoParticleTorchBlock(
                        Settings.copy(Blocks.TORCH)
                                .luminance(state -> 0)
                                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, "unlit_torch")))
                )
        );

        UNLIT_WALL_TORCH = add("unlit_wall_torch",
                new NoParticleWallTorchBlock(
                        Settings.copy(Blocks.WALL_TORCH)
                                .luminance(state -> 0)
                                .lootTable(UNLIT_TORCH.getLootTableKey())
                                .overrideTranslationKey(UNLIT_TORCH.getTranslationKey())
                                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, "unlit_wall_torch")))
                )
        );

        UNLIT_COPPER_TORCH = add("unlit_copper_torch",
                new NoParticleTorchBlock(
                        Settings.copy(Blocks.COPPER_TORCH)
                                .luminance(state -> 0)
                                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, "unlit_copper_torch")))
                )
        );

        UNLIT_COPPER_WALL_TORCH = add("unlit_copper_wall_torch",
                new NoParticleWallTorchBlock(
                        Settings.copy(Blocks.COPPER_WALL_TORCH)
                                .luminance(state -> 0)
                                .lootTable(UNLIT_COPPER_TORCH.getLootTableKey())
                                .overrideTranslationKey(UNLIT_COPPER_TORCH.getTranslationKey())
                                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, "unlit_copper_wall_torch")))
                )
        );

        UNLIT_COPPER_LANTERNS = CopperBlockSet.create(
                "unlit_copper_lantern",
                BlockRegistry::addCopperSet,
                LanternBlock::new,
                OxidizableLanternBlock::new,
                oxidationLevel -> AbstractBlock.Settings.create()
                        .mapColor(MapColor.IRON_GRAY)
                        .solid()
                        .strength(3.5F)
                        .sounds(BlockSoundGroup.LANTERN)
                        .luminance(state -> 0)
                        .nonOpaque()
                        .pistonBehavior(PistonBehavior.DESTROY)
        );
    }

    private static Block addCopperSet(
            String id,
            Function<AbstractBlock.Settings, Block> factory,
            AbstractBlock.Settings settings
    ) {
        Block block = factory.apply(
                settings.registryKey(
                        RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, id))
                )
        );


        return add(id, block);
//        return (Block) factory.apply(settings.registryKey(RegistryKeys.BLOCK, Identifier.of(TotallyLit.MOD_ID, copper_base)));
//        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla(id));
    }
}
