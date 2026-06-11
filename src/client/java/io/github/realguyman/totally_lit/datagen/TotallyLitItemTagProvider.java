package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
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
        valueLookupBuilder(TagRegistry.CAMPFIRE_IGNITER_ITEMS).add(
                Items.TORCH,
                Items.SOUL_TORCH,
                Items.COPPER_TORCH,
                Items.LAVA_BUCKET,
                Items.MAGMA_BLOCK
        ).addOptionalTag(ConventionalItemTags.IGNITER_TOOLS);

        valueLookupBuilder(TagRegistry.JACK_O_LANTERN_IGNITER_ITEMS).add(
                Items.TORCH,
                Items.SOUL_TORCH,
                Items.COPPER_TORCH
        ).addOptionalTag(ConventionalItemTags.IGNITER_TOOLS);

        valueLookupBuilder(TagRegistry.LANTERN_IGNITER_ITEMS).add(
                Items.TORCH,
                Items.SOUL_TORCH,
                Items.COPPER_TORCH
        ).addOptionalTag(ConventionalItemTags.IGNITER_TOOLS);

        valueLookupBuilder(TagRegistry.TORCH_IGNITER_ITEMS).add(
                Items.TORCH,
                Items.SOUL_TORCH,
                Items.COPPER_TORCH,
                Items.LANTERN,
                Items.SOUL_LANTERN,
                Items.LAVA_BUCKET,
                Items.MAGMA_BLOCK
        ).addOptionalTag(ConventionalItemTags.IGNITER_TOOLS);

        valueLookupBuilder(TagRegistry.SOUL_FIRE_VARIANT_ITEMS).add(
                Items.SOUL_TORCH,
                Items.SOUL_LANTERN,
                Items.SOUL_CAMPFIRE
        );
    }
}
