package net.StrawHatSWE.SummonCraft.Attachments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.*;

public class PlayerSummonData {
    private int baseMaxSummons = 3;
    private final List<UUID> summons = new ArrayList<>();
    private final Set<UUID> markedForDesummon = new HashSet<>();

    public PlayerSummonData() {}

    public List<UUID> getPlayerSummons() {
        return summons;
    }

    public void addSummon(Level level, UUID summon) {
        if (level instanceof ServerLevel serverLevel) {
            summons.add(summon);

            if (summons.size() > getActualMaximum()) {
                Entity firstSummon = serverLevel.getEntity(summons.get(0));

                if (firstSummon != null) {
                    firstSummon.discard();
                } else {
                    markedForDesummon.add(summons.get(0));
                }

                summons.remove(0);
            }
        }
    }

    public void removeSummon(UUID summon) {
        summons.remove(summon);
    }

    public int getActualMaximum() {
        return baseMaxSummons;
    }

    public void clearSummons(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            for (UUID summon : summons) {
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
}
