package net.StrawHatSWE.SummonCraft;

import net.StrawHatSWE.SummonCraft.Attachments.ModAttachments;
import net.StrawHatSWE.SummonCraft.Entities.ModEntities;
import net.StrawHatSWE.SummonCraft.Items.ModItems;
import net.StrawHatSWE.SummonCraft.datagen.ModLootModifiers;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(SummonCraft.MODID)
public class SummonCraft {
    public static final String MODID = "summoncraft";
    public static final String MOD_ID = "summoncraft";

    public static final Logger LOGGER = LogUtils.getLogger();

    public SummonCraft(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ModItems.ITEMS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);

        ModLootModifiers.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.COMBAT)) {
            event.accept(ModItems.SLINGSHOT.get());
            event.accept(ModItems.SLINGSHOTTER_STAFF.get());
            event.accept(ModItems.SLIME_STAFF.get());
        }

        if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
            event.accept(ModItems.SOUL_ESSENCE.get());
            event.accept(ModItems.SOUL_CRYSTAL.get());
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
