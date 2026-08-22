package net.StrawHatSWE.SummonCraft.Entities.Mobs;

import net.StrawHatSWE.SummonCraft.Attachments.ModAttachments;
import net.StrawHatSWE.SummonCraft.Attachments.PlayerSummonData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentType;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class PlayerSummon extends PathfinderMob {
    private @Nullable UUID ownerUUID;

    protected PlayerSummon(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    public void setOwner(@Nullable Player player) {
        this.ownerUUID = player == null ? null : player.getUUID();
    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (this.ownerUUID != null && entity instanceof Player player) {
            return player.getUUID().equals(this.ownerUUID);
        }

        if (entity instanceof PlayerSummon otherSummon) {
            return this.ownerUUID != null && this.ownerUUID.equals(otherSummon.ownerUUID);
        }

        if (!(entity instanceof Enemy)) {
            return true;
        }

        return super.isAlliedTo(entity);
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (target == this || target instanceof PlayerSummon) {
            return false;
        }

        if (this.ownerUUID != null && target instanceof Player player) {
            if (player.getUUID().equals(this.ownerUUID)) {
                return false;
            }
        }

        return super.canAttack(target);    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FELL_OUT_OF_WORLD) || source.is(DamageTypes.GENERIC_KILL)) {
            return super.hurt(source, amount);
        }

        return false;
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isCrouching() && hand == InteractionHand.MAIN_HAND) {

            if (!this.level().isClientSide() && this.getOwnerUUID() == player.getUUID()) {
                PlayerSummonData summonData = player.getData(ModAttachments.SUMMON_DATA);

                this.discard();
                summonData.removeSummon(this.getUUID());

                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
        }

        return super.mobInteract(player, hand);
    }

    @Override()
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            this.setAggressive(this.getTarget() != null  && this.getTarget().isAlive());
        }
    }
}
