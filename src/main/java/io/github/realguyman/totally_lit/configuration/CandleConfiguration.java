package io.github.realguyman.totally_lit.configuration;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "candles")
public class CandleConfiguration implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 1, max = 4 * 7)
    @ConfigEntry.Gui.Tooltip
    public int burnDuration = 4;

    public float extinguishInRainChance = 1F;

    public boolean extinguishOverTime = true;
}
