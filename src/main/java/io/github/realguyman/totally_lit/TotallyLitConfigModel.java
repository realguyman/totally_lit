package io.github.realguyman.totally_lit;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = "totally_lit")
@Config(name = "totally_lit", wrapperName = "TotallyLitConfig")
public class TotallyLitConfigModel {
    @SectionHeader("behaviors")
    public boolean itemEntitiesExtinguishWhenSubmerged = true;

    @SectionHeader("extinguishables")
    @Nest
    public Campfires campfires = new Campfires();

    @Nest
    public Candles candles = new Candles();

    @Nest
    public JackOLanterns jackOLanterns = new JackOLanterns();

    @Nest
    public Lanterns lanterns = new Lanterns();

    @Nest
    public Torches torches = new Torches();

    public static class Campfires {
        @RangeConstraint(min = 6_000, max = TotallyLit.MAX_TICKS_TO_BURN_FOR)
        public int burnDuration = 12_000;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public boolean defaultLitStateWhenPlaced = false;

        @RangeConstraint(min = 0F, max = 1F)
        public float extinguishInRainChance = 0F;

        public boolean extinguishOverTime = true;
    }

    public static class Candles {
        @RangeConstraint(min = 6_000, max = TotallyLit.MAX_TICKS_TO_BURN_FOR)
        public int burnDuration = 16_000;

        @RangeConstraint(min = 0F, max = 1F)
        public float extinguishInRainChance = 1F;

        public boolean extinguishOverTime = true;
    }

    public static class JackOLanterns {
        @RangeConstraint(min = 6_000, max = TotallyLit.MAX_TICKS_TO_BURN_FOR)
        public int burnDuration = 16_000;

        @RangeConstraint(min = 0F, max = 1F)
        public float extinguishInRainChance = 0F;

        public boolean extinguishOverTime = true;
    }

    public static class Lanterns {
        @RangeConstraint(min = 6_000, max = TotallyLit.MAX_TICKS_TO_BURN_FOR)
        public int burnDuration = 16_000;

        @RangeConstraint(min = 0F, max = 1F)
        public float extinguishInRainChance = 0F;

        public boolean extinguishOverTime = true;
    }

    public static class Torches {
        @RangeConstraint(min = 6_000, max = TotallyLit.MAX_TICKS_TO_BURN_FOR)
        public int burnDuration = 16_000;

        @RangeConstraint(min = 0F, max = 1F)
        public float extinguishInRainChance = 0.25F;

        public boolean extinguishOverTime = true;
    }
}
