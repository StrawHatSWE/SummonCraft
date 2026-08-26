package net.StrawHatSWE.SummonCraft.Entities.Mobs.Skeletons.Slingshotter;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.Skeletons.BabySkeletonClothingLayer;
import net.StrawHatSWE.SummonCraft.Entities.Mobs.Skeletons.BabySkeletonModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

import static net.StrawHatSWE.SummonCraft.Entities.Mobs.Skeletons.BabySkeletonModel.SKELETON_TEXTURE;
import static net.StrawHatSWE.SummonCraft.Entities.Mobs.ModLayers.BABY_SKELETON_LAYER;

public class SlingshotterRenderer extends HumanoidMobRenderer<Slingshotter, BabySkeletonModel<Slingshotter>> {

    public SlingshotterRenderer(EntityRendererProvider.Context context) {
        super(context, new BabySkeletonModel<>(context.bakeLayer(BABY_SKELETON_LAYER)), 0.3F);

        this.addLayer(new BabySkeletonClothingLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Slingshotter entity) {
        return SKELETON_TEXTURE;
    }
}
