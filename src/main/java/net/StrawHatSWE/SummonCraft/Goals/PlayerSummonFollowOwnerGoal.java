package net.StrawHatSWE.SummonCraft.Goals;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.FriendlySlime;
import net.StrawHatSWE.SummonCraft.Entities.Mobs.PlayerSummon;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class PlayerSummonFollowOwnerGoal extends Goal {
    protected final PlayerSummon summon;
    protected final double speed;
    protected final float maxDistance;
    protected final float minDistance;
    protected final double maxTargetDistanceSqr;
    protected final double teleportDistanceSqr;

    private boolean isRetreating = false;

    public PlayerSummonFollowOwnerGoal(PlayerSummon summon, double speed, float maxDistance, float minDistance, double maxTargetDistance, double teleportDistance) {
        this.summon = summon;
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
        this.maxTargetDistanceSqr = maxTargetDistance * maxTargetDistance;
        this.teleportDistanceSqr = teleportDistance * teleportDistance;

        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    protected boolean checkAndHandleTarget() {LivingEntity target = this.summon.getTarget();
        if (target != null && target.isAlive()) {
            Player player = summon.getOwner();

            if (this.summon.distanceToSqr(target) > this.maxTargetDistanceSqr) {
                this.summon.setTarget(null);
                this.isRetreating = true;
                return true;
            }

            if (player != null && this.summon.distanceToSqr(player) >= this.teleportDistanceSqr) {
                this.summon.setTarget(null);
                this.isRetreating = true;
                return true;
            }

            return false;
        }
        return true;
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

    @Override
    public void tick() {
        Player player = summon.getOwner();
        if (player == null) return;

        if (this.isRetreating && this.summon.getTarget() != null) {
            this.summon.setTarget(null);
        }

        double distToPlayerSqr = this.summon.distanceToSqr(player);
        if (distToPlayerSqr >= this.teleportDistanceSqr) {
            this.tryToTeleportToOwner(player);
            return;
        }

        if (this.summon.getMoveControl() instanceof FriendlySlime.FriendlySlimeMoveControl slimeMoveControl) {
            double dx = player.getX() - this.summon.getX();
            double dz = player.getZ() - this.summon.getZ();
            float targetYRot = (float) (Math.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F;

            slimeMoveControl.setDirection(targetYRot, false);
            slimeMoveControl.setWantedMovement(this.speed);
        } else {
            this.summon.getNavigation().moveTo(player, this.speed);
        }

        this.summon.getLookControl().setLookAt(player, 30.0F, 30.0F);
    }

    protected void tryToTeleportToOwner(Player player) {
        BlockPos playerPos = player.blockPosition();

        for (int i = 0; i < 10; ++i) {
            int dx = this.summon.getRandom().nextInt(7) - 3;
            int dy = this.summon.getRandom().nextInt(3) - 1;
            int dz = this.summon.getRandom().nextInt(7) - 3;

            BlockPos targetPos = playerPos.offset(dx, dy, dz);

            if (this.canTeleportTo(targetPos)) {
                this.summon.moveTo(targetPos.getX() + 0.5D, targetPos.getY(), targetPos.getZ() + 0.5D, this.summon.getYRot(), this.summon.getXRot());
                this.summon.getNavigation().stop();
                this.summon.resetFallDistance();
                this.summon.setTarget(null);
                this.isRetreating = false;
                return;
            }
        }
    }

    private boolean canTeleportTo(BlockPos pos) {
        BlockPos below = pos.below();
        BlockState stateBelow = this.summon.level().getBlockState(below);
        if (!stateBelow.isValidSpawn(this.summon.level(), below, this.summon.getType())) {
            return false;
        }

        BlockPos currentPos = this.summon.blockPosition();
        return this.summon.level().noCollision(
                this.summon,
                this.summon.getBoundingBox().move(pos.getX() - currentPos.getX(), pos.getY() - currentPos.getY(), pos.getZ() - currentPos.getZ())
        );
    }

    @Override
    public void stop() {
        this.summon.getNavigation().stop();

        Player player = summon.getOwner();
        if (player != null) {
            double halfMaxDistSqr = (this.maxDistance * 0.5D) * (this.maxDistance * 0.5D);
            if (this.summon.distanceToSqr(player) <= halfMaxDistSqr) {
                this.isRetreating = false;
            }
        }
    }
}
