package io.github.realguyman.totally_lit.client.datagen;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TotallyLitItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public TotallyLitItemTagProvider(
            FabricPackOutput output,
            CompletableFuture<HolderLookup.Provider> completableFuture
    ) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider lookup) {
        builder(TagRegistry.CAMPFIRE_IGNITER_ITEMS).add(
                BlockItemIds.TORCH.item(),
                BlockItemIds.SOUL_TORCH.item(),
                BlockItemIds.COPPER_TORCH.item(),
                ItemIds.LAVA_BUCKET,
                BlockItemIds.MAGMA_BLOCK.item()
        ).addOptionalTag(ConventionalItemTags.IGNITER_TOOLS);

        builder(TagRegistry.JACK_O_LANTERN_IGNITER_ITEMS).add(
                BlockItemIds.TORCH.item(),
                BlockItemIds.SOUL_TORCH.item(),
                BlockItemIds.COPPER_TORCH.item()
        ).addOptionalTag(ConventionalItemTags.IGNITER_TOOLS);

        builder(TagRegistry.LANTERN_IGNITER_ITEMS).add(
                BlockItemIds.TORCH.item(),
                BlockItemIds.SOUL_TORCH.item(),
                BlockItemIds.COPPER_TORCH.item()
        ).addOptionalTag(ConventionalItemTags.IGNITER_TOOLS);

        builder(TagRegistry.TORCH_IGNITER_ITEMS).add(
                BlockItemIds.TORCH.item(),
                BlockItemIds.SOUL_TORCH.item(),
                BlockItemIds.COPPER_TORCH.item(),
                BlockItemIds.LANTERN.item(),
                BlockItemIds.SOUL_LANTERN.item(),
                ItemIds.LAVA_BUCKET,
                BlockItemIds.MAGMA_BLOCK.item()
        ).addOptionalTag(ConventionalItemTags.IGNITER_TOOLS);

        builder(TagRegistry.SOUL_FIRE_VARIANT_ITEMS).add(
                BlockItemIds.SOUL_TORCH.item(),
                BlockItemIds.SOUL_LANTERN.item(),
                BlockItemIds.SOUL_CAMPFIRE.item()
        );
    }
}
