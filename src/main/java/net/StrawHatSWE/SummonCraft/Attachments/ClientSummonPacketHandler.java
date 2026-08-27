package net.StrawHatSWE.SummonCraft.Attachments;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientSummonPacketHandler {
    public static void handle(ClientboundSummonSyncPacket payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = Minecraft.getInstance().player;
            if (player != null) {
                PlayerSummonData data = player.getData(ModAttachments.SUMMON_DATA);
                data.setClientValues(payload.currentSlots(), payload.maxSlots());
            }
        });
    }
}
