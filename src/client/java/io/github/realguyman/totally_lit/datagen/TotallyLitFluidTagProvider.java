package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalFluidTags;
import net.minecraft.registry.RegistryWrapper;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TotallyLitFluidTagProvider extends FabricTagProvider.FluidTagProvider {
    public TotallyLitFluidTagProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture
    ) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.@NonNull WrapperLookup arg) {
        valueLookupBuilder(TagRegistry.TORCH_IGNITER_FLUIDS)
                .addOptionalTag(ConventionalFluidTags.LAVA);
    }
}
