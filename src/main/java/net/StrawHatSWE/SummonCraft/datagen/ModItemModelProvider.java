package net.StrawHatSWE.SummonCraft.datagen;

import net.StrawHatSWE.SummonCraft.Items.ModItems;
import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SummonCraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Generates the base handheld model (parent: minecraft:item/handheld)
        handheldItem(ModItems.SLINGSHOT.get());

        // Generates pulling/charging models for property overrides
        ModelFile pulling0 = handheldItem("slingshot_pulling_0");
        ModelFile pulling1 = handheldItem("slingshot_pulling_1");
        ModelFile pulling2 = handheldItem("slingshot_pulling_2");

        withExistingParent(ModItems.SLINGSHOT.getId().getPath(), ResourceLocation.withDefaultNamespace("item/handheld"))
                .override()
                .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
                .model(pulling0)
                .end()

                .override()
                .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
                .predicate(ResourceLocation.withDefaultNamespace("pull"), 0.65F)
                .model(pulling1)
                .end()

                .override()
                .predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
                .predicate(ResourceLocation.withDefaultNamespace("pull"), 0.9F)
                .model(pulling2)
                .end();

        handheldItem(ModItems.SLINGSHOTTER_STAFF.get());

        handheldItem(ModItems.SOUL_ESSENCE.get());
        handheldItem(ModItems.SOUL_CRYSTAL.get());
    }

    // Helper method to generate model JSON files for custom texture names
    private ItemModelBuilder handheldItem(String name) {
        return withExistingParent(name, ResourceLocation.withDefaultNamespace("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(SummonCraft.MOD_ID, "item/" + name));
    }
}
