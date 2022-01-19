package io.github.realguyman.totally_lit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class Configuration {
    private static final Path filePath = FabricLoader.getInstance().getConfigDir().resolve(Initializer.IDENTIFIER + ".json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static int burnTime = 24000;
    public static float extinguishInRainChance = 0.25F;

    public static void load() {
        if (filePath.toFile().exists()) {
            try {
                Reader reader = Files.newBufferedReader(filePath);
                Data data = gson.fromJson(reader, Data.class);
                burnTime = Math.max(data.burnTime, 6000);
                extinguishInRainChance = Math.min(data.extinguishInRainChance, 1.0F);
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not read configuration file due to: " + e);
            }
        } else {
            save();
        }
    }

    public static void save() {
        try {
            Writer writer = Files.newBufferedWriter(filePath);
            gson.toJson(new Data(24000, 0.25F), writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not write to file due to: " + e);
        }
    }

    private static class Data {
        private final String burnTimeComment = "How long torches will last before they burn out in ticks. Minimum ticks: 6,000 Note: 24,000 ticks = 1 Minecraft day";
        private final int burnTime;

        private final String extinguishInRainChanceComment = "The chance a torch will extinguish in the rain when randomly ticked. E.g: 0.25 = 25%";
        private final float extinguishInRainChance;

        private Data(int burnTime, float extinguishInRainChance) {
            this.burnTime = burnTime;
            this.extinguishInRainChance = extinguishInRainChance;
        }
    }
}
