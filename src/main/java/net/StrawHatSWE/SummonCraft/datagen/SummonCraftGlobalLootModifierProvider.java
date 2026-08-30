package net.StrawHatSWE.SummonCraft.datagen;

import net.StrawHatSWE.SummonCraft.Items.ModItems;
import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class SummonCraftGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public SummonCraftGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, SummonCraft.MOD_ID);
    }

    @Override
    protected void start() {
        this.add("slime_staff_to_slime", new AddItemModifier(
            new LootItemCondition[]{
                    new LootTableIdCondition.Builder(ResourceLocation.fromNamespaceAndPath("minecraft", "entities/slime")).build(),
                    LootItemRandomChanceCondition.randomChance(0.0001f).build()
            }, ModItems.SLIME_STAFF.get()
        ));
    }
}
