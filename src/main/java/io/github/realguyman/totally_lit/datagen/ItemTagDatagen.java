package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ItemTagDatagen extends FabricTagProvider.ItemTagProvider {
    public ItemTagDatagen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        getOrCreateTagBuilder(TagRegistry.CAMPFIRE_IGNITER_ITEMS).add(
                Items.TORCH,
                Items.SOUL_TORCH,
                Items.LAVA_BUCKET,
                Items.MAGMA_BLOCK
        );

        getOrCreateTagBuilder(TagRegistry.JACK_O_LANTERN_IGNITER_ITEMS).add(
                Items.TORCH,
                Items.SOUL_TORCH,
                Items.FLINT_AND_STEEL
        );

        getOrCreateTagBuilder(TagRegistry.LANTERN_IGNITER_ITEMS).add(
                Items.TORCH,
                Items.SOUL_TORCH,
                Items.FLINT_AND_STEEL
        );

        getOrCreateTagBuilder(TagRegistry.TORCH_IGNITER_ITEMS).add(
                Items.TORCH,
                Items.SOUL_TORCH,
                Items.LANTERN,
                Items.SOUL_LANTERN,
                Items.LAVA_BUCKET,
                Items.MAGMA_BLOCK,
                Items.FLINT_AND_STEEL
        );

        getOrCreateTagBuilder(TagRegistry.SOUL_FIRE_VARIANT_ITEMS).add(
                Items.SOUL_TORCH,
                Items.SOUL_LANTERN,
                Items.SOUL_CAMPFIRE
        );
    }
}
