package net.StrawHatSWE.SummonCraft.Entities.Mobs;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
public class FriendlySlimeRenderer extends MobRenderer<FriendlySlime, FriendlySlimeModel<FriendlySlime>> {

    private static final ResourceLocation INNER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("summoncraft", "textures/entity/slimes/slime.png");

    public FriendlySlimeRenderer(EntityRendererProvider.Context context) {
        super(context, new FriendlySlimeModel<>(context.bakeLayer(ModLayers.FRIENDLY_SLIME_LAYER)), 0.25F);

        this.addLayer(new FriendlySlimeOuterLayer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(FriendlySlime entity) {
        return INNER_TEXTURE;
    }
}
