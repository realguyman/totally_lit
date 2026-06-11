package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TotallyLitEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public TotallyLitEntityTypeTagProvider(
            FabricDataOutput output,
            CompletableFuture<HolderLookup.Provider> future
    ) {
        super(output, future);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        valueLookupBuilder(TagRegistry.CARETAKERS)
                .add(EntityType.ILLUSIONER)
                .add(EntityType.PILLAGER)
                .add(EntityType.VILLAGER)
                .add(EntityType.VINDICATOR)
                .add(EntityType.WANDERING_TRADER)
                .add(EntityType.WITCH);
    }
}
