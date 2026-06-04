package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TotallyLitBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public TotallyLitBlockTagProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> future
    ) {
        super(output, future);
    }

    @Override
    protected void configure(RegistryWrapper.@NonNull WrapperLookup lookup) {
        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .setReplace(false)
                .add(
                        BlockRegistry.UNLIT_LANTERN,
                        BlockRegistry.UNLIT_SOUL_LANTERN,
                        BlockRegistry.GLOWSTONE_LANTERN
                );

        valueLookupBuilder(TagRegistry.SOUL_FIRE_VARIANT_BLOCKS).add(
                Blocks.SOUL_CAMPFIRE,
                Blocks.SOUL_LANTERN,
                Blocks.SOUL_TORCH,
                Blocks.SOUL_WALL_TORCH
        );

        valueLookupBuilder(TagRegistry.LANTERN_IGNITER_BLOCKS).add(
                Blocks.TORCH,
                Blocks.WALL_TORCH,
                Blocks.SOUL_TORCH,
                Blocks.SOUL_WALL_TORCH
        ).addOptionalTag(BlockTags.FIRE);

        valueLookupBuilder(TagRegistry.TORCH_IGNITER_BLOCKS).add(
                Blocks.TORCH,
                Blocks.WALL_TORCH,
                Blocks.SOUL_TORCH,
                Blocks.SOUL_WALL_TORCH,
                Blocks.LAVA_CAULDRON,
                Blocks.MAGMA_BLOCK
        ).addOptionalTag(BlockTags.FIRE);
    }
}
