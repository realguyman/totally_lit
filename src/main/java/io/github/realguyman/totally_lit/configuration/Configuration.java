package io.github.realguyman.totally_lit.configuration;

import io.github.realguyman.totally_lit.Initializer;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = Initializer.IDENTIFIER)
public class Configuration extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("candles")
    @ConfigEntry.Gui.TransitiveObject
    public CandleConfiguration candleConfiguration = new CandleConfiguration();

    @ConfigEntry.Category("lanterns")
    @ConfigEntry.Gui.TransitiveObject
    public LanternConfiguration lanternConfiguration = new LanternConfiguration();

    @ConfigEntry.Category("torches")
    @ConfigEntry.Gui.TransitiveObject
    public TorchConfiguration torchConfiguration = new TorchConfiguration();
}
