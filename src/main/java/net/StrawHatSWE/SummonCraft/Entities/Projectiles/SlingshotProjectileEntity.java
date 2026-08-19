package net.StrawHatSWE.SummonCraft.Entities.Projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
}