package net.StrawHatSWE.SummonCraft.datagen;

import net.StrawHatSWE.SummonCraft.Items.ModItems;
import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

import static net.StrawHatSWE.SummonCraft.Items.ModItems.*;

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

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SOUL_CRYSTAL.get())
                .pattern("SSS")
                .pattern("SDS")
                .pattern("SSS")
                .define('S', SOUL_ESSENCE.get())
                .define('D', Items.DIAMOND)
                .unlockedBy("has_soul_essence", has(ModItems.SOUL_ESSENCE.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, SLINGSHOTTER_STAFF.get())
                .pattern(" L ")
                .pattern("ICB")
                .pattern(" S ")
                .define('L', Items.LEATHER_HELMET)
                .define('I', SLINGSHOT.get())
                .define('C', SOUL_CRYSTAL.get())
                .define('B', Items.BONE)
                .define('S', Items.STICK)
                .unlockedBy("has_soul_crystal", has(SOUL_CRYSTAL.get()))
                .save(output);

        SimpleCookingRecipeBuilder.blasting(
                Ingredient.of(Items.SOUL_SAND),
                RecipeCategory.MISC,
                SOUL_ESSENCE.get(),
                1.0F,
                2000
        )
                .unlockedBy("has_soul_sand", has(Items.SOUL_SAND))
                .save(output, ResourceLocation.fromNamespaceAndPath(SummonCraft.MOD_ID, "soul_essence_from_smelting"));
    }
}
