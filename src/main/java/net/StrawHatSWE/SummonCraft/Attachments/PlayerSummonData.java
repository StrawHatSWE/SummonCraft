package net.StrawHatSWE.SummonCraft.Attachments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerSummonData {
    private int baseMaxSummons = 3;
    private final List<UUID> summons = new ArrayList<>();

    public PlayerSummonData() {}

    public List<UUID> getPlayerSummons() {
        return summons;
    }

    public void addSummon(Level level, UUID summon) {
        if (level instanceof ServerLevel serverLevel) {
            summons.add(summon);

            if (summons.size() > getActualMaximum()) {
                Entity firstSummon = serverLevel.getEntity(summons.get(0));
                firstSummon.discard();

                summons.remove(0);
            }
        }
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
}
