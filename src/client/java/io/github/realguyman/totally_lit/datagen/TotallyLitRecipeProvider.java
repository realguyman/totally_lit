package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TotallyLitRecipeProvider extends FabricRecipeProvider {
    public TotallyLitRecipeProvider(
            FabricPackOutput output,
            CompletableFuture<HolderLookup.Provider> future
    ) {
        super(output, future);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(
            HolderLookup.@NonNull Provider lookup,
            @NonNull RecipeOutput exporter
    ) {
        return new RecipeProvider(lookup, exporter) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.DECORATIONS, ItemRegistry.UNLIT_TORCH, 4)
                        .pattern("#")
                        .pattern("|")
                        .define('#', ItemTags.COALS)
                        .define('|', Items.STICK)
                        .group("multi_bench")
                        .unlockedBy(getHasName(Items.COAL), has(Items.COAL))
                        .save(output);

                shaped(RecipeCategory.DECORATIONS, ItemRegistry.UNLIT_SOUL_TORCH, 4)
                        .pattern("#")
                        .pattern("|")
                        .pattern("s")
                        .define('#', ItemTags.COALS)
                        .define('|', Items.STICK)
                        .define('s', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                        .group("multi_bench")
                        .unlockedBy(getHasName(Items.SOUL_SAND), has(Items.SOUL_SAND))
                        .save(output);

                shaped(RecipeCategory.DECORATIONS, ItemRegistry.UNLIT_COPPER_TORCH, 4)
                        .pattern("c")
                        .pattern("#")
                        .pattern("|")
                        .define('c', ConventionalItemTags.COPPER_NUGGETS)
                        .define('#', ItemTags.COALS)
                        .define('|', Items.STICK)
                        .group("multi_bench")
                        .unlockedBy(getHasName(Items.COPPER_NUGGET), has(Items.COPPER_NUGGET))
                        .save(output);

                shaped(RecipeCategory.DECORATIONS, ItemRegistry.GLOWSTONE_TORCH, 4)
                        .pattern("#")
                        .pattern("|")
                        .define('#', ConventionalItemTags.GLOWSTONE_DUSTS)
                        .define('|', Items.STICK)
                        .group("multi_bench")
                        .unlockedBy(getHasName(Items.GLOWSTONE_DUST), has(Items.GLOWSTONE_DUST))
                        .save(output);

                shaped(RecipeCategory.DECORATIONS, ItemRegistry.UNLIT_LANTERN)
                        .pattern("nnn")
                        .pattern("ntn")
                        .pattern("nnn")
                        .define('n', ConventionalItemTags.IRON_NUGGETS)
                        .define('t', ItemRegistry.UNLIT_TORCH)
                        .group("multi_bench")
                        .unlockedBy(getHasName(ItemRegistry.UNLIT_TORCH), has(ItemRegistry.UNLIT_TORCH))
                        .save(output);

                shaped(RecipeCategory.DECORATIONS, ItemRegistry.UNLIT_SOUL_LANTERN)
                        .pattern("nnn")
                        .pattern("ntn")
                        .pattern("nnn")
                        .define('n', ConventionalItemTags.IRON_NUGGETS)
                        .define('t', ItemRegistry.UNLIT_SOUL_TORCH)
                        .group("multi_bench")
                        .unlockedBy(getHasName(ItemRegistry.UNLIT_SOUL_TORCH), has(ItemRegistry.UNLIT_SOUL_TORCH))
                        .save(output);

                shaped(RecipeCategory.DECORATIONS, ItemRegistry.UNLIT_COPPER_LANTERNS.unaffected())
                        .pattern("nnn")
                        .pattern("ntn")
                        .pattern("nnn")
                        .define('n', ConventionalItemTags.COPPER_NUGGETS)
                        .define('t', ItemRegistry.UNLIT_COPPER_TORCH)
                        .group("multi_bench")
                        .unlockedBy(getHasName(ItemRegistry.UNLIT_COPPER_TORCH), has(ItemRegistry.UNLIT_COPPER_TORCH))
                        .save(output);

                shaped(RecipeCategory.DECORATIONS, ItemRegistry.GLOWSTONE_LANTERN)
                        .pattern("nnn")
                        .pattern("ntn")
                        .pattern("nnn")
                        .define('n', ConventionalItemTags.IRON_NUGGETS)
                        .define('t', ItemRegistry.GLOWSTONE_TORCH)
                        .group("multi_bench")
                        .unlockedBy(getHasName(ItemRegistry.GLOWSTONE_TORCH), has(ItemRegistry.GLOWSTONE_TORCH))
                        .save(output);

                shaped(RecipeCategory.DECORATIONS, ItemRegistry.UNLIT_JACK_O_LANTERN)
                        .pattern("p")
                        .pattern("t")
                        .define('p', Items.CARVED_PUMPKIN)
                        .define('t', ItemRegistry.UNLIT_TORCH)
                        .group("multi_bench")
                        .unlockedBy(getHasName(ItemRegistry.UNLIT_TORCH), has(ItemRegistry.UNLIT_TORCH))
                        .save(output);

                simpleCookingRecipe(
                        "campfire_cooking",
                        CampfireCookingRecipe::new,
                        20,
                        ItemRegistry.UNLIT_TORCH,
                        Items.TORCH,
                        0
                );

                simpleCookingRecipe(
                        "campfire_cooking",
                        CampfireCookingRecipe::new,
                        20,
                        ItemRegistry.UNLIT_SOUL_TORCH,
                        Items.SOUL_TORCH,
                        0
                );

                simpleCookingRecipe(
                        "campfire_cooking",
                        CampfireCookingRecipe::new,
                        20,
                        ItemRegistry.UNLIT_COPPER_TORCH,
                        Items.COPPER_TORCH,
                        0
                );
            }
        };
    }

    @Override
    public String getName() {
        return "TotallyLitRecipeProvider";
    }
}
