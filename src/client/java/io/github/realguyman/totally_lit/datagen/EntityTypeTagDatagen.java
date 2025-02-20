package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;

public class EntityTypeTagDatagen extends FabricTagProvider.EntityTypeTagProvider {
    public EntityTypeTagDatagen(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(TagRegistry.CARETAKERS)
                .add(EntityType.ILLUSIONER)
                .add(EntityType.PILLAGER)
                .add(EntityType.VILLAGER)
                .add(EntityType.VINDICATOR)
                .add(EntityType.WANDERING_TRADER)
                .add(EntityType.WITCH);
    }
}
