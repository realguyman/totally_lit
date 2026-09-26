package io.github.realguyman.totally_lit.client.datagen;

import io.github.realguyman.totally_lit.references.BlockItemIds;
import io.github.realguyman.totally_lit.registry.BlockRegistry;
import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockIds;
import net.minecraft.tags.BlockTags;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class TotallyLitBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public TotallyLitBlockTagProvider(
            FabricPackOutput output,
            CompletableFuture<HolderLookup.Provider> future
    ) {
        super(output, future);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider lookup) {
        builder(BlockTags.MINEABLE_WITH_PICKAXE).add(
                BlockItemIds.UNLIT_LANTERN.block(),
                BlockItemIds.UNLIT_SOUL_LANTERN.block(),
                BlockItemIds.GLOWSTONE_LANTERN.block()
        );

        BlockRegistry.UNLIT_COPPER_LANTERN.forEach((block) -> {
            builder(BlockTags.MINEABLE_WITH_PICKAXE).add(Objects.requireNonNull(block.properties().blockId()));
        });

        builder(TagRegistry.SOUL_FIRE_VARIANT_BLOCKS).add(
                net.minecraft.references.BlockItemIds.SOUL_CAMPFIRE.block(),
                net.minecraft.references.BlockItemIds.SOUL_LANTERN.block(),
                net.minecraft.references.BlockItemIds.SOUL_TORCH.block(),
                BlockIds.SOUL_FIRE,
                BlockIds.SOUL_WALL_TORCH
        );

        builder(TagRegistry.LANTERN_IGNITER_BLOCKS).add(
                net.minecraft.references.BlockItemIds.TORCH.block(),
                BlockIds.WALL_TORCH,
                net.minecraft.references.BlockItemIds.SOUL_TORCH.block(),
                BlockIds.SOUL_WALL_TORCH,
                net.minecraft.references.BlockItemIds.COPPER_TORCH.block(),
                BlockIds.COPPER_WALL_TORCH
        ).addOptionalTag(BlockTags.FIRE);

        builder(TagRegistry.TORCH_IGNITER_BLOCKS).add(
                net.minecraft.references.BlockItemIds.TORCH.block(),
                BlockIds.WALL_TORCH,
                net.minecraft.references.BlockItemIds.SOUL_TORCH.block(),
                BlockIds.SOUL_WALL_TORCH,
                net.minecraft.references.BlockItemIds.COPPER_TORCH.block(),
                BlockIds.COPPER_WALL_TORCH,
                BlockIds.LAVA_CAULDRON,
                net.minecraft.references.BlockItemIds.MAGMA_BLOCK.block()
        ).addOptionalTag(BlockTags.FIRE);
    }
}
