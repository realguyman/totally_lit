package io.github.realguyman.totally_lit;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

@Config(name = Initializer.IDENTIFIER)
public class Configuration implements ConfigData {
    @ConfigEntry.Gui.Excluded
    public static Configuration INSTANCE;

    @ConfigEntry.BoundedDiscrete(min = 1, max = 4 * 7)
    @ConfigEntry.Gui.Tooltip
    public int burnDuration = 4;

    public float extinguishInRainChance = 0.25F;

    public boolean extinguishOverTime = true;

    public static void initialize() {
        AutoConfig.register(Configuration.class, JanksonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(Configuration.class).getConfig();
    }
}
