package net.StrawHatSWE.SummonCraft.Goals;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.PlayerSummon;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class NoTeleportFollowOwnerGoal extends Goal {
    private final PlayerSummon summon;
    private final double speed;
    private final float maxDistance;
    private final float minDistance;

    public NoTeleportFollowOwnerGoal(PlayerSummon summon, double speed, float maxDistance, float minDistance) {
        this.summon = summon;
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
    }

    private Player getSummonPlayer() {
        return summon.level().getPlayerByUUID(summon.getOwnerUUID());
    }

    @Override
    public boolean canUse() {
        Player player = getSummonPlayer();

        if (player != null
                && !player.isSpectator()
                && player.isAlive()) {
            return summon.distanceToSqr(player) > (double) (this.maxDistance * this.maxDistance);
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        Player player = getSummonPlayer();
        if (player == null || !player.isAlive()) {
            return false;
        }
        return !this.summon.getNavigation().isDone()
                && this.summon.distanceToSqr(player) > (double)(this.minDistance * this.minDistance);
    }

    @Override
    public void tick() {
        Player player = getSummonPlayer();
        if (player != null) {
            this.summon.getLookControl().setLookAt(player, 10.0F, (float)this.summon.getMaxHeadXRot());
            this.summon.getNavigation().moveTo(player, this.speed);
        }
    }

    @Override
    public void stop() {
        this.summon.getNavigation().stop();
    }
}
