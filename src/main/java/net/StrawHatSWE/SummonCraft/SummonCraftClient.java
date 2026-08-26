package net.StrawHatSWE.SummonCraft;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.*;
import net.StrawHatSWE.SummonCraft.Entities.ModEntities;
import net.StrawHatSWE.SummonCraft.Items.ModItems;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SummonCraft.MOD_ID, dist = Dist.CLIENT)
// Added 'bus = EventBusSubscriber.Bus.MOD' below:
@EventBusSubscriber(modid = SummonCraft.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SummonCraftClient {

    public SummonCraftClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // 1. 'pulling' predicate: returns 1.0 if the player is actively using this item
            ItemProperties.register(
                    ModItems.SLINGSHOT.get(),
                    ResourceLocation.withDefaultNamespace("pulling"),
                    (stack, level, entity, seed) ->
                            entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
            );

            // 2. 'pull' predicate: returns 0.0 to 1.0 based on charge duration
            ItemProperties.register(
                    ModItems.SLINGSHOT.get(),
                    ResourceLocation.withDefaultNamespace("pull"),
                    (stack, level, entity, seed) -> {
                        if (entity == null || entity.getUseItem() != stack) {
                            return 0.0F;
                        }
                        // Max draw time (e.g., 20 ticks = 1 second)
                        return (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
                    }
            );
        });
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.COBBLESTONE_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.DIRT_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.SLINGSHOTTER.get(), SlingshotterRenderer::new);
        event.registerEntityRenderer(ModEntities.FRIENDLY_SLIME.get(), FriendlySlimeRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModLayers.BABY_SKELETON_LAYER, BabySkeletonModel::createBodyLayer);
        event.registerLayerDefinition(ModLayers.FRIENDLY_SLIME_LAYER, FriendlySlimeModel::createBodyLayer);
    }
}
