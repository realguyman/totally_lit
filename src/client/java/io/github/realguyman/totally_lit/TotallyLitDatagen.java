package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class TotallyLitDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        final FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(ItemTagDatagen::new);
        pack.addProvider(BlockTagDatagen::new);
        pack.addProvider(FluidTagDatagen::new);
        pack.addProvider(BlockLootTableDatagen::new);
        pack.addProvider(EntityTypeTagDatagen::new);
    }
}
