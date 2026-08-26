package net.StrawHatSWE.SummonCraft.Goals;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.FriendlySlime;
import net.StrawHatSWE.SummonCraft.Entities.Mobs.PlayerSummon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class SlimeNoTeleportFollowOwnerGoal extends NoTeleportFollowOwnerGoal {
    private final double leashDistance = this.maxDistance * this.maxDistance * 2.5 * 2.5;
    private boolean isRetreating = false;

    public SlimeNoTeleportFollowOwnerGoal(PlayerSummon summon, double speed, float maxDistance, float minDistance) {
        super(summon, speed, maxDistance, minDistance);

        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    private boolean checkAndHandleTarget() {
        LivingEntity target = this.summon.getTarget();
        if (target != null && target.isAlive()) {
            if (this.summon.distanceToSqr(target) > this.leashDistance) {
                this.summon.setTarget(null);
                this.isRetreating = true;
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        Player player = summon.getOwner();
        if (player != null) {
            double dx = player.getX() - this.summon.getX();
            double dz = player.getZ() - this.summon.getZ();
            float targetYRot = (float) (Math.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F;

            if (this.summon.getMoveControl() instanceof FriendlySlime.FriendlySlimeMoveControl slimeMoveControl) {
                slimeMoveControl.setDirection(targetYRot, false);
                slimeMoveControl.setWantedMovement(this.speed);
            }

            this.summon.getLookControl().setLookAt(player, 30.0F, 30.0F);
        }
    }

    @Override
    public boolean canUse() {
        Player player = summon.getOwner();
        if (player == null || player.isSpectator() || !player.isAlive()) {
            return false;
        }

        double distToPlayerSqr = this.summon.distanceToSqr(player);
        double halfMaxDistSqr = (this.maxDistance * 0.5D) * (this.maxDistance * 0.5D);

        if (this.isRetreating && distToPlayerSqr <= halfMaxDistSqr) {
            this.isRetreating = false;
        }

        if (this.isRetreating) {
            return true;
        }

        if (!checkAndHandleTarget()) {
            return false;
        }

        return distToPlayerSqr > (double) (this.maxDistance * this.maxDistance);
    }

    @Override
    public boolean canContinueToUse() {
        Player player = summon.getOwner();
        if (player == null || !player.isAlive()) {
            return false;
        }

        double distToPlayerSqr = this.summon.distanceToSqr(player);
        double halfMaxDistSqr = (this.maxDistance * 0.5D) * (this.maxDistance * 0.5D);

        if (this.isRetreating && distToPlayerSqr <= halfMaxDistSqr) {
            this.isRetreating = false;
        }

        if (this.isRetreating) {
            return true;
        }

        if (!checkAndHandleTarget()) {
            return false;
        }

        return distToPlayerSqr > (double) (this.minDistance * this.minDistance);
    }
}
