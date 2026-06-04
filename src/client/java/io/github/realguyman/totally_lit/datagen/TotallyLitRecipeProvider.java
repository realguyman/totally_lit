package io.github.realguyman.totally_lit.datagen;

import io.github.realguyman.totally_lit.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TotallyLitRecipeProvider extends FabricRecipeProvider {
    public TotallyLitRecipeProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> future
    ) {
        super(output, future);
    }

    @Override
    protected @NonNull RecipeGenerator getRecipeGenerator(
            RegistryWrapper.@NonNull WrapperLookup lookup,
            @NonNull RecipeExporter exporter
    ) {
        return new RecipeGenerator(lookup, exporter) {
            @Override
            public void generate() {
                createShaped(RecipeCategory.DECORATIONS, ItemRegistry.UNLIT_TORCH, 4)
                        .pattern("#")
                        .pattern("|")
                        .input('#', ItemTags.COALS)
                        .input('|', Items.STICK)
                        .group("multi_bench")
                        .criterion(hasItem(Items.COAL), conditionsFromItem(Items.COAL))
                        .offerTo(exporter);

                createShaped(RecipeCategory.DECORATIONS, ItemRegistry.UNLIT_SOUL_TORCH, 4)
                        .pattern("#")
                        .pattern("|")
                        .pattern("s")
                        .input('#', ItemTags.COALS)
                        .input('|', Items.STICK)
                        .input('s', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                        .group("multi_bench")
                        .criterion(hasItem(Items.SOUL_SAND), conditionsFromItem(Items.SOUL_SAND))
                        .offerTo(exporter);

                createShaped(RecipeCategory.DECORATIONS, ItemRegistry.GLOWSTONE_TORCH, 4)
                        .pattern("#")
                        .pattern("|")
                        .input('#', ConventionalItemTags.GLOWSTONE_DUSTS)
                        .input('|', Items.STICK)
                        .group("multi_bench")
                        .criterion(hasItem(Items.GLOWSTONE_DUST), conditionsFromItem(Items.GLOWSTONE_DUST))
                        .offerTo(exporter);

                createShaped(RecipeCategory.DECORATIONS, ItemRegistry.UNLIT_LANTERN)
                        .pattern("nnn")
                        .pattern("ntn")
                        .pattern("nnn")
                        .input('n', ConventionalItemTags.IRON_NUGGETS)
                        .input('t', ItemRegistry.UNLIT_TORCH)
                        .group("multi_bench")
                        .criterion(hasItem(ItemRegistry.UNLIT_TORCH), conditionsFromItem(ItemRegistry.UNLIT_TORCH))
                        .offerTo(exporter);

                createShaped(RecipeCategory.DECORATIONS, ItemRegistry.UNLIT_SOUL_LANTERN)
                        .pattern("nnn")
                        .pattern("ntn")
                        .pattern("nnn")
                        .input('n', ConventionalItemTags.IRON_NUGGETS)
                        .input('t', ItemRegistry.UNLIT_SOUL_TORCH)
                        .group("multi_bench")
                        .criterion(hasItem(ItemRegistry.UNLIT_SOUL_TORCH), conditionsFromItem(ItemRegistry.UNLIT_SOUL_TORCH))
                        .offerTo(exporter);

                createShaped(RecipeCategory.DECORATIONS, ItemRegistry.GLOWSTONE_LANTERN)
                        .pattern("nnn")
                        .pattern("ntn")
                        .pattern("nnn")
                        .input('n', ConventionalItemTags.IRON_NUGGETS)
                        .input('t', ItemRegistry.GLOWSTONE_TORCH)
                        .group("multi_bench")
                        .criterion(hasItem(ItemRegistry.GLOWSTONE_TORCH), conditionsFromItem(ItemRegistry.GLOWSTONE_TORCH))
                        .offerTo(exporter);

                createShaped(RecipeCategory.DECORATIONS, ItemRegistry.UNLIT_JACK_O_LANTERN)
                        .pattern("p")
                        .pattern("t")
                        .input('p', Items.CARVED_PUMPKIN)
                        .input('t', ItemRegistry.UNLIT_TORCH)
                        .group("multi_bench")
                        .criterion(hasItem(ItemRegistry.UNLIT_TORCH), conditionsFromItem(ItemRegistry.UNLIT_TORCH))
                        .offerTo(exporter);

                offerFoodCookingRecipe(
                        "campfire_cooking",
                        RecipeSerializer.CAMPFIRE_COOKING,
                        CampfireCookingRecipe::new,
                        20,
                        ItemRegistry.UNLIT_TORCH,
                        Items.TORCH,
                        0
                );

                offerFoodCookingRecipe(
                        "campfire_cooking",
                        RecipeSerializer.CAMPFIRE_COOKING,
                        CampfireCookingRecipe::new,
                        20,
                        ItemRegistry.UNLIT_SOUL_TORCH,
                        Items.SOUL_TORCH,
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
