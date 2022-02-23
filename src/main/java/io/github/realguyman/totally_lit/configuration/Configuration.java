package io.github.realguyman.totally_lit.configuration;

import io.github.realguyman.totally_lit.Initializer;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = Initializer.IDENTIFIER)
public class Configuration extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Gui.TransitiveObject
    public TorchConfiguration torchConfiguration = new TorchConfiguration();
}
