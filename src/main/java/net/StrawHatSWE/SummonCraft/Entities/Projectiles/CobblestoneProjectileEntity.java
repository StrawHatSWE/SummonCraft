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

public class CobblestoneProjectileEntity extends SlingshotProjectileEntity implements ItemSupplier {

    public CobblestoneProjectileEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public CobblestoneProjectileEntity(Level level, LivingEntity shooter, ItemStack ammoStack, ItemStack weaponStack) {
        super(
                ModEntities.COBBLESTONE_PROJECTILE.get(),
                shooter,
                level,
                ammoStack,
                weaponStack
        );

        this.setBaseDamage(2.0D);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return null;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.COBBLESTONE);
    }
}
