package net.StrawHatSWE.SummonCraft.Entities.Mobs;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.Slingshotter;
import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

import static net.StrawHatSWE.SummonCraft.Entities.Mobs.BabySkeletonModel.SKELETON_TEXTURE;
import static net.StrawHatSWE.SummonCraft.Entities.Mobs.ModLayers.BABY_SKELETON_LAYER;

public class SlingshotterRenderer extends HumanoidMobRenderer<Slingshotter, BabySkeletonModel<Slingshotter>> {

    public SlingshotterRenderer(EntityRendererProvider.Context context) {
        super(context, new BabySkeletonModel<>(context.bakeLayer(BABY_SKELETON_LAYER)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(Slingshotter entity) {
        return SKELETON_TEXTURE; // Returns baby_skeleton_base.png safely now
    }
}
