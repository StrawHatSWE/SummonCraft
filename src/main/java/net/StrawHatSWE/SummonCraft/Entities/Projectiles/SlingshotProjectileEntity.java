package net.StrawHatSWE.SummonCraft.Entities.Projectiles;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.PlayerSummon;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public abstract class SlingshotProjectileEntity extends AbstractArrow {
    protected SlingshotProjectileEntity(EntityType<? extends AbstractArrow> p_331098_, Level p_331626_) {
        super(p_331098_, p_331626_);
    }

    protected SlingshotProjectileEntity(EntityType<? extends AbstractArrow> entityType, LivingEntity owner, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon
    ) {
        super(entityType, owner, level, pickupItemStack, firedFromWeapon);
    }

    protected SlingshotProjectileEntity(EntityType<? extends AbstractArrow> entityType, double x, double y, double z, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon
    ) {
        super(entityType, x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        if (this.getOwner() instanceof PlayerSummon summon) {
            if (summon.getOwnerUUID() != null && target.getUUID().equals(summon.getOwnerUUID())) {
                return false;
            }
            if (summon.isAlliedTo(target)) {
                return false;
            }
        }

        return super.canHitEntity(target);
    }

    @Override
    public void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        if (this.getOwner() instanceof  PlayerSummon) {
            this.discard();
        }
    }
}