package net.StrawHatSWE.SummonCraft.Entities.Projectiles;

import net.StrawHatSWE.SummonCraft.Entities.ModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class DirtProjectileEntity extends SlingshotProjectileEntity implements ItemSupplier {
    public DirtProjectileEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public DirtProjectileEntity(Level level, LivingEntity shooter, ItemStack ammoStack, ItemStack weaponStack) {
        super(
                ModEntities.DIRT_PROJECTILE.get(),
                shooter,
                level,
                ammoStack,
                weaponStack
        );
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return null;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        Entity entity = result.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 2);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.DIRT);
    }
}
