package net.StrawHatSWE.SummonCraft.Entities.Mobs;

import net.StrawHatSWE.SummonCraft.Goals.PlayerSummonFollowOwnerGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class FriendlySlime extends PlayerSummon {

    public float targetSquish;
    public float squish;
    public float oSquish;
    private boolean wasOnGround;
    private static final float MAX_IDLE_DISTANCE = 10.0F;

    public FriendlySlime(EntityType<? extends PlayerSummon> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FriendlySlimeMoveControl(this);


    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new FriendlySlimeFloatGoal(this));
        this.goalSelector.addGoal(1, new FriendlySlimeAttackGoal(this, 40.0F));

        this.goalSelector.addGoal(2, new PlayerSummonFollowOwnerGoal(this, 1.5D, MAX_IDLE_DISTANCE, 2.0F, 40.0F, 50.0F));

        this.goalSelector.addGoal(3, new FriendlySlimeRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new FriendlySlimeKeepOnJumpingGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, true, targetPredicate));
    }

    @Override
    public void tick() {
        this.squish += (this.targetSquish - this.squish) * 0.5F;
        this.oSquish = this.squish;
        super.tick();

        if (this.onGround() && !this.wasOnGround) {
            this.targetSquish = -0.5F;
        } else if (!this.onGround() && this.wasOnGround) {
            this.targetSquish = 1.0F;
        }

        this.wasOnGround = this.onGround();
        this.decreaseSquish();
    }

    protected void decreaseSquish() {
        this.targetSquish *= 0.6F;
    }

    public int getJumpDelay() {
        return (this.random.nextInt(20) + 10) / 3;
    }

    protected boolean isDealsDamage() {
        return true;
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    protected void dealDamage(LivingEntity p_33638_) {
        if (this.isAlive() && this.isWithinMeleeAttackRange(p_33638_) && this.hasLineOfSight(p_33638_)) {
            DamageSource damagesource = this.damageSources().mobAttack(this);
            if (p_33638_.hurt(damagesource, this.getAttackDamage())) {
                this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                if (this.level() instanceof ServerLevel serverlevel) {
                    EnchantmentHelper.doPostAttackEffects(serverlevel, p_33638_, damagesource);
                }
            }
        }
    }

    @Override
    public void push(Entity entity) {
        super.push(entity);

        if (entity instanceof LivingEntity target && !this.isAlliedTo(target)) {
            if (this.isDealsDamage() && this.canAttack(target)) {
                this.dealDamage(target);
            }
        }
    }

    static class FriendlySlimeKeepOnJumpingGoal extends Goal {
        private final FriendlySlime slime;

        public FriendlySlimeKeepOnJumpingGoal(FriendlySlime slime) {
            this.slime = slime;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.slime.isPassenger();
        }

        @Override
        public void tick() {
            ((FriendlySlimeMoveControl) this.slime.getMoveControl()).setWantedMovement(1.0D);
        }
    }

    static class FriendlySlimeRandomDirectionGoal extends Goal {
        private final FriendlySlime slime;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public FriendlySlimeRandomDirectionGoal(FriendlySlime slime) {
            this.slime = slime;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.slime.getTarget() == null
                    && (this.slime.onGround() || this.slime.isInWater() || this.slime.isInLava() || this.slime.hasEffect(MobEffects.LEVITATION));
        }

        @Override
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.slime.getRandom().nextInt(60));
                this.chosenDegrees = (float) this.slime.getRandom().nextInt(360);
            }
            ((FriendlySlimeMoveControl) this.slime.getMoveControl()).setDirection(this.chosenDegrees, false);
        }
    }

    static class FriendlySlimeFloatGoal extends Goal {
        private final FriendlySlime slime;

        public FriendlySlimeFloatGoal(FriendlySlime slime) {
            this.slime = slime;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            slime.getNavigation().setCanFloat(true);
        }

        @Override
        public boolean canUse() {
            return (this.slime.isInWater() || this.slime.isInLava())
                    && this.slime.getMoveControl() instanceof FriendlySlimeMoveControl;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.slime.getRandom().nextFloat() < 0.8F) {
                this.slime.getJumpControl().jump();
            }
            ((FriendlySlimeMoveControl) this.slime.getMoveControl()).setWantedMovement(1.2D);
        }
    }

    public static class FriendlySlimeMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final FriendlySlime slime;

        public FriendlySlimeMoveControl(FriendlySlime slime) {
            super(slime);
            this.slime = slime;
            this.yRot = 180.0F * slime.getYRot() / (float) Math.PI;
        }

        public void setDirection(float yRot, boolean aggressive) {
            this.yRot = yRot;
        }

        public void setWantedMovement(double speed) {
            this.speedModifier = speed;
            this.operation = Operation.MOVE_TO;
        }

        @Override
        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();

            if (this.operation != Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = Operation.WAIT;
                if (this.mob.onGround()) {
                    this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));

                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.slime.getJumpDelay();
                        this.slime.getJumpControl().jump();
                    } else {
                        this.slime.xxa = 0.0F;
                        this.slime.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }
            }
        }
    }

    static class FriendlySlimeAttackGoal extends Goal {
        private final FriendlySlime slime;
        private final double maxOwnerLeashSqr;

        public FriendlySlimeAttackGoal(FriendlySlime slime, double maxOwnerLeash) {
            this.slime = slime;
            this.maxOwnerLeashSqr = maxOwnerLeash * maxOwnerLeash;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        private boolean isOwnerTooFar() {
            if (this.slime.getOwnerUUID() == null) return false;
            Player owner = this.slime.level().getPlayerByUUID(this.slime.getOwnerUUID());
            return owner != null && this.slime.distanceToSqr(owner) >= this.maxOwnerLeashSqr;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.slime.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }
            if (isOwnerTooFar()) {
                return false;
            }

            return this.slime.canAttack(target) && this.slime.getMoveControl() instanceof FriendlySlimeMoveControl;
        }

        @Override
        public void start() {
            super.start();
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.slime.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            }

            if (isOwnerTooFar()) {
                this.slime.setTarget(null);
                return false;
            }

            return this.slime.canAttack(target);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.slime.getTarget();
            if (target == null) return;

            this.slime.lookAt(target, 30.0F, 30.0F);

            if (this.slime.getMoveControl() instanceof FriendlySlimeMoveControl slimeMoveControl) {
                double dx = target.getX() - this.slime.getX();
                double dz = target.getZ() - this.slime.getZ();
                float targetYRot = (float) (Math.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F;

                slimeMoveControl.setDirection(targetYRot, true);
                slimeMoveControl.setWantedMovement(1.5D);
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }
}
