package net.StrawHatSWE.SummonCraft.Entities.Mobs;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.Slingshotter;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SlingshotterRenderer extends HumanoidMobRenderer<Slingshotter, SkeletonModel<Slingshotter>> {

    // Vanilla Skeleton Texture
    private static final ResourceLocation SKELETON_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/skeleton/skeleton.png");

    public SlingshotterRenderer(EntityRendererProvider.Context context) {
        super(context, new SkeletonModel<>(context.bakeLayer(ModelLayers.SKELETON)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Slingshotter entity) {
        return SKELETON_TEXTURE;
    }
}
