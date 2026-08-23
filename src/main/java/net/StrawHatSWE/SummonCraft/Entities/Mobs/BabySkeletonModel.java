package net.StrawHatSWE.SummonCraft.Entities.Mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

public class BabySkeletonModel<T extends Slingshotter> extends HumanoidModel<T> {

    public static final ResourceLocation SKELETON_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SummonCraft.MOD_ID, "textures/entity/baby_skeletons/baby_skeleton_base.png");

    public BabySkeletonModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 11.0F, 0.0F));

        partdefinition.addOrReplaceChild("hat",
                CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 11.0F, 0.0F));

        partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 24)
                        .addBox(-2.0F, 0.0F, -1.0F, 4.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 11.0F, 0.0F));

        float arm_height = 7.0F;
        float arm_width = 2.0F;

        partdefinition.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(24, 0)
                        .addBox(-2.0F, 0.0F, -1.0F, arm_width, arm_height, arm_width, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, 11.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(24, 0)
                        .mirror()
                        .addBox(0.0F, 0.0F, -1.0F, arm_width, arm_height, arm_width, new CubeDeformation(0.0F)),
                PartPose.offset(2.0F, 11.0F, 0.0F));

        float leg_height = 6.0F;
        float leg_width = 2.0F;

        partdefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(24, 9)
                        .addBox(-1.0F, 0.0F, -1.0F, leg_width, leg_height, leg_width, new CubeDeformation(0.0F)),
                PartPose.offset(-1.0F, 24.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(24, 9)
                        .mirror()
                        .addBox(-1.0F, 0.0F, -1.0F, leg_width, leg_height, leg_width, new CubeDeformation(0.0F)),
                PartPose.offset(1.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.head.y = 11.0F;
        this.hat.y = 11.0F;
        this.body.y = 11.0F;

        this.rightArm.y = 11.0F;
        this.rightArm.x = -2.0F;
        this.rightArm.z = 0.0F;

        this.leftArm.y = 11.0F;
        this.leftArm.x = 2.0F;
        this.leftArm.z = 0.0F;

        this.rightLeg.y = 18.0F;
        this.rightLeg.x = -1.0F;
        this.rightLeg.z = 0.0F;

        this.leftLeg.y = 18.0F;
        this.leftLeg.x = 1.0F;
        this.leftLeg.z = 0.0F;

        if (entity.isAggressive()) {
            float rotationFactor = (float) (-90 * 3.14/180);
            this.rightArm.xRot = rotationFactor;
            this.leftArm.xRot = rotationFactor;

        }
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        super.translateToHand(arm, poseStack);

        poseStack.translate(0.0D, -0.0625D, 0.0D);
    }
}
