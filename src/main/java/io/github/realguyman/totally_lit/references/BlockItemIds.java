package io.github.realguyman.totally_lit.references;

import io.github.realguyman.totally_lit.TotallyLit;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.WeatheringCopperCollection;

public class BlockItemIds {
    public static final BlockItemId UNLIT_TORCH = create("unlit_torch");
    public static final BlockItemId UNLIT_COPPER_TORCH = create("unlit_copper_torch");
    public static final BlockItemId UNLIT_SOUL_TORCH = create("unlit_soul_torch");
    public static final BlockItemId UNLIT_SOUL_LANTERN = create("unlit_soul_lantern");
    public static final BlockItemId UNLIT_LANTERN = create("unlit_lantern");
    public static final BlockItemId UNLIT_JACK_O_LANTERN = create("unlit_jack_o_lantern");
    public static final BlockItemId GLOWSTONE_LANTERN = create("glowstone_lantern");
    public static final BlockItemId GLOWSTONE_TORCH = create("glowstone_torch");

    public static WeatheringCopperCollection<BlockItemId> UNLIT_COPPER_LANTERN = WeatheringCopperCollection.prefixWithState(
            WeatheringCopperCollection.create("unlit_copper_lantern")
    ).map(BlockItemIds::create);

    private static BlockItemId create(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(TotallyLit.MOD_ID, name);
        return BlockItemId.create(id, id);
    }
}
