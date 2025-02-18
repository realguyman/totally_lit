package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagDatagen extends FabricTagProvider.BlockTagProvider {
    public BlockTagDatagen(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
    ) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(TagRegistry.SOUL_FIRE_VARIANT_BLOCKS).add(
                Blocks.SOUL_CAMPFIRE,
                Blocks.SOUL_LANTERN,
                Blocks.SOUL_TORCH,
                Blocks.SOUL_WALL_TORCH
        );

        getOrCreateTagBuilder(TagRegistry.LANTERN_IGNITER_BLOCKS).add(
                Blocks.TORCH,
                Blocks.WALL_TORCH,
                Blocks.SOUL_TORCH,
                Blocks.SOUL_WALL_TORCH
        ).addOptionalTag(BlockTags.FIRE);

        getOrCreateTagBuilder(TagRegistry.TORCH_IGNITER_BLOCKS).add(
                Blocks.TORCH,
                Blocks.WALL_TORCH,
                Blocks.SOUL_TORCH,
                Blocks.SOUL_WALL_TORCH,
                Blocks.LAVA_CAULDRON,
                Blocks.MAGMA_BLOCK
        ).addOptionalTag(BlockTags.FIRE);
    }
}
