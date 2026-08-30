package net.StrawHatSWE.SummonCraft.Attachments;

import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = SummonCraft.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModNetwork {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        // Must use "1" or a specific protocol version string
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                ClientboundSummonSyncPacket.TYPE,
                ClientboundSummonSyncPacket.STREAM_CODEC,
                ClientSummonPacketHandler::handle
        );
    }
}
