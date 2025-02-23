package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class TotallyLitDataGeneratorEntrypoint implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        final FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(TotallyLitBlockLootTableProvider::new);
        pack.addProvider(TotallyLitBlockTagProvider::new);
        pack.addProvider(TotallyLitEntityTypeTagProvider::new);
        pack.addProvider(TotallyLitFluidTagProvider::new);
        pack.addProvider(TotallyLitItemTagProvider::new);
        pack.addProvider(TotallyLitModelProvider::new);
        pack.addProvider(TotallyLitRecipeProvider::new);
    }
}
