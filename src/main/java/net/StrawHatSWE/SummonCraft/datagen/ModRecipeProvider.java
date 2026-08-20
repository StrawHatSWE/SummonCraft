package net.StrawHatSWE.SummonCraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

import static net.StrawHatSWE.SummonCraft.Items.ModItems.SLINGSHOT;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SLINGSHOT.get())
                .pattern("STS")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('T', Items.STRING)
                .unlockedBy("has_stick", has(Items.STICK))
                .save(output);
    }
}
