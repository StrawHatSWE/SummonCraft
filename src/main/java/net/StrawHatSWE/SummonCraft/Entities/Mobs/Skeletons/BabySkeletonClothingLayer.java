package net.StrawHatSWE.SummonCraft.Entities.Mobs.Skeletons;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class BabySkeletonClothingLayer<T extends Mob, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation CLOTHES_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SummonCraft.MOD_ID, "textures/entity/baby_skeletons/baby_skeleton_clothes.png");

    public BabySkeletonClothingLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       T entity, float limbSwing, float limbSwingAmount, float partialTick,
                       float ageInTicks, float netHeadYaw, float headPitch) {

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(CLOTHES_TEXTURE));

        this.getParentModel().renderToBuffer(
                poseStack,
                vertexConsumer,
                packedLight,
                LivingEntityRenderer.getOverlayCoords(entity, 0.0F),
                -1
        );
    }
}
