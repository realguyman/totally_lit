package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.FluidTags;

import java.util.concurrent.CompletableFuture;

public class FluidTagDatagen extends FabricTagProvider.FluidTagProvider {
    public FluidTagDatagen(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(TagRegistry.TORCH_IGNITER_FLUIDS)
                .addOptionalTag(FluidTags.LAVA);
    }
}
