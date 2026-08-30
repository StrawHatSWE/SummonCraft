package net.StrawHatSWE.SummonCraft.Attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.StrawHatSWE.SummonCraft.Entities.Mobs.PlayerSummon;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;

public class PlayerSummonData {
    public record SummonEntry(UUID summonUuid, float slotValue) {
        public static final StreamCodec<ByteBuf, SummonEntry> STREAM_CODEC = StreamCodec.composite(
                UUIDUtil.STREAM_CODEC, SummonEntry::summonUuid,
                ByteBufCodecs.FLOAT, SummonEntry::slotValue,
                SummonEntry::new
        );
    }

    private int baseMaxSummons = 3;
    private final List<SummonEntry> summons = new ArrayList<>();
    private final Set<UUID> markedForDesummon = new HashSet<>();

    private float clientCurrentUsage = 0.0F;
    private float clientMaxSlots = 3.0F;

    public PlayerSummonData() {}

    public List<UUID> getPlayerSummons() {
        return summons.stream().map(SummonEntry::summonUuid).toList();
    }

    public void addSummon(ServerPlayer player, Level level, UUID summon) {
        if (level instanceof ServerLevel serverLevel) {
            summons.add(new SummonEntry(summon, 1.0F));

            if (getCurrentSummonUsage() > getActualMaximum()) {
                Entity firstSummon = serverLevel.getEntity(summons.get(0).summonUuid());

                if (firstSummon != null) {
                    firstSummon.discard();
                } else {
                    markedForDesummon.add(summons.get(0).summonUuid());
                }

                summons.remove(0);
            }

            syncHud(player);
        }
    }

    public void removeSummon(ServerPlayer player, UUID summon) {
        summons.removeIf(entry -> entry.summonUuid().equals(summon));

        syncHud(player);
    }

    public int getActualMaximum() {
        return clientMaxSlots > 0 ? (int) clientMaxSlots : baseMaxSummons;
    }

    public float getCurrentSummonUsage() {
        if (!summons.isEmpty()) {
            return summons.stream().map(SummonEntry::slotValue).reduce(0.0F, Float::sum);
        }
        return clientCurrentUsage;    }

    public void clearSummons(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            for (UUID summon : summons.stream().map(SummonEntry::summonUuid).toList()) {
                Entity firstSummon = serverLevel.getEntity(summon);
                firstSummon.discard();
            }

            summons.clear();
        }
    }

    public boolean isMarkedForDesummon(UUID uuid) {
        return this.markedForDesummon.contains(uuid);
    }

    public void consumeDesummonMark(UUID uuid) {
        this.markedForDesummon.remove(uuid);
    }

    private void syncHud(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new ClientboundSummonSyncPacket(
                getCurrentSummonUsage(),
                getActualMaximum()
        ));
    }

    public void setClientValues(float currentUsage, float maxSlots) {
        this.clientCurrentUsage = currentUsage;
        this.clientMaxSlots = maxSlots;
    }
}
