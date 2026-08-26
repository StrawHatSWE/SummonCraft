package net.StrawHatSWE.SummonCraft.Entities.Mobs.Slimes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
public class FriendlySlimeOuterLayer extends RenderLayer<FriendlySlime, FriendlySlimeModel<FriendlySlime>> {

    private static final ResourceLocation OUTER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("summoncraft", "textures/entity/slimes/slime_outer.png");

    public FriendlySlimeOuterLayer(RenderLayerParent<FriendlySlime, FriendlySlimeModel<FriendlySlime>> parent, EntityModelSet modelSet) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, FriendlySlime entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(OUTER_TEXTURE));
            int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);

            this.getParentModel().renderOuter(poseStack, consumer, packedLight, overlay);
        }
    }
}
