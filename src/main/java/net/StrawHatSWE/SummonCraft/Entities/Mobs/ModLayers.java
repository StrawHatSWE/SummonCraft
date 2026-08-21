package net.StrawHatSWE.SummonCraft.Entities.Mobs;

import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModLayers {
    public static final ModelLayerLocation BABY_SKELETON_LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SummonCraft.MOD_ID, "baby_skeleton"), "main");
}
