package net.StrawHatSWE.SummonCraft.Attachments;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ClientboundSummonSyncPacket(float currentSlots, float maxSlots) implements CustomPacketPayload {
    public static final Type<ClientboundSummonSyncPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("yourmod", "summon_sync"));

    public static final StreamCodec<ByteBuf, ClientboundSummonSyncPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, ClientboundSummonSyncPacket::currentSlots,
            ByteBufCodecs.FLOAT, ClientboundSummonSyncPacket::maxSlots,
            ClientboundSummonSyncPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}