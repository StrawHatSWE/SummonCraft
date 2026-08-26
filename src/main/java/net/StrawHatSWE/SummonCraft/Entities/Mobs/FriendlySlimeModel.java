package net.StrawHatSWE.SummonCraft.Entities.Mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
public class FriendlySlimeModel<T extends FriendlySlime> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart innerBody;
    private final ModelPart outerShell;

    public FriendlySlimeModel(ModelPart root) {
        this.root = root;
        this.innerBody = root.getChild("inner");
        this.outerShell = root.getChild("outer");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Inner Core & Face
        partdefinition.addOrReplaceChild("inner",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F)
                        .texOffs(32, 4).addBox(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 1.0F)
                        .texOffs(32, 0).addBox(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 1.0F)
                        .texOffs(32, 8).addBox(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F),
                PartPose.ZERO);

        // Outer Shell
        partdefinition.addOrReplaceChild("outer",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.innerBody.visible = true;
        this.outerShell.visible = false;
    }

    public void renderOuter(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay) {
        this.innerBody.visible = false;
        this.outerShell.visible = true;

        this.root.render(poseStack, buffer, packedLight, packedOverlay);

        this.innerBody.visible = true;
        this.outerShell.visible = false;
    }
}