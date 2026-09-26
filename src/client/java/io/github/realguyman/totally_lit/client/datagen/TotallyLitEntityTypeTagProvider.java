package io.github.realguyman.totally_lit.client.datagen;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityTypeIds;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TotallyLitEntityTypeTagProvider extends FabricTagsProvider.EntityTypeTagsProvider {
    public TotallyLitEntityTypeTagProvider(
            FabricPackOutput output,
            CompletableFuture<HolderLookup.Provider> future
    ) {
        super(output, future);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        builder(TagRegistry.CARETAKERS).add(
                EntityTypeIds.ILLUSIONER,
                EntityTypeIds.PILLAGER,
                EntityTypeIds.VILLAGER,
                EntityTypeIds.VINDICATOR,
                EntityTypeIds.WANDERING_TRADER,
                EntityTypeIds.WITCH
        );
    }
}
