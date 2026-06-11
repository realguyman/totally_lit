package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalFluidTags;
import net.minecraft.core.HolderLookup;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TotallyLitFluidTagProvider extends FabricTagsProvider.FluidTagsProvider {
    public TotallyLitFluidTagProvider(
            FabricPackOutput output,
            CompletableFuture<HolderLookup.Provider> completableFuture
    ) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider arg) {
        valueLookupBuilder(TagRegistry.TORCH_IGNITER_FLUIDS)
                .addOptionalTag(ConventionalFluidTags.LAVA);
    }
}
