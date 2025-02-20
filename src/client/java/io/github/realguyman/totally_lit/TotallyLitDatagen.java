package io.github.realguyman.totally_lit;

import io.github.realguyman.totally_lit.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class TotallyLitDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(ItemTagDatagen::new);
        generator.addProvider(BlockTagDatagen::new);
        generator.addProvider(FluidTagDatagen::new);
        generator.addProvider(BlockLootTableDatagen::new);
        generator.addProvider(EntityTypeTagDatagen::new);
    }
}
