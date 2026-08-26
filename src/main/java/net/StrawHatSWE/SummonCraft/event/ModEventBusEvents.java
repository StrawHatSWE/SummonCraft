package net.StrawHatSWE.SummonCraft.event;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.FriendlySlime;
import net.StrawHatSWE.SummonCraft.Entities.Mobs.Slingshotter;
import net.StrawHatSWE.SummonCraft.Entities.ModEntities;
import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = SummonCraft.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SLINGSHOTTER.get(), Slingshotter.createAttributes().build());
        event.put(ModEntities.FRIENDLY_SLIME.get(), FriendlySlime.createAttributes().build());
    }
}
