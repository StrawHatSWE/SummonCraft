package net.StrawHatSWE.SummonCraft.Goals;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.Slimes.FriendlySlime;
import net.StrawHatSWE.SummonCraft.Entities.Mobs.PlayerSummon;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

public class NoTeleportFollowOwnerGoal extends Goal {
    protected final PlayerSummon summon;
    protected final double speed;
    protected final float maxDistance;
    protected final float minDistance;

    public NoTeleportFollowOwnerGoal(PlayerSummon summon, double speed, float maxDistance, float minDistance) {
        this.summon = summon;
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
    }

    @Override
    public boolean canUse() {
        Player player = summon.getOwner();

        if (player != null
                && !player.isSpectator()
                && player.isAlive()) {
            return summon.distanceToSqr(player) > (double) (this.maxDistance * this.maxDistance);
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        Player player = summon.getOwner();
        if (player == null || !player.isAlive()) {
            return false;
        }

        return this.summon.distanceToSqr(player) > (double)(this.minDistance * this.minDistance);
    }

    @Override
    public void tick() {
        Player player = summon.getOwner();
        if (player != null) {
            this.summon.getLookControl().setLookAt(player, 10.0F, (float)this.summon.getMaxHeadXRot());
            this.summon.getNavigation().moveTo(player, this.speed);

            if (this.summon.getMoveControl() instanceof FriendlySlime.FriendlySlimeMoveControl slimeMoveControl) {
                slimeMoveControl.setDirection(this.summon.getYRot(), false);
                slimeMoveControl.setWantedMovement(this.speed);
            }
        }
    }

    @Override
    public void stop() {
        this.summon.getNavigation().stop();
    }
}
