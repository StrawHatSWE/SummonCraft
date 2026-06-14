package net.StrawHatSWE.SummonCraft.Entities;

import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class Slinger extends PlayerSummon {
    protected Slinger(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    public @Nullable EntityReference<LivingEntity> getOwnerReference() {
        EntityReference entityReference = (EntityReference) ((Optional) this.entityData.get(DATA_OWNERUUID_ID)).orElse((Object) null);
        return entityReference;
    }

    protected void populateDefaultEquipmentSlots(@NonNull RandomSource random, @NonNull DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    public void performRangedAttack(LivingEntity target, float power) {
        ItemStack bowItem = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, (item) -> item instanceof BowItem));
        ItemStack projectile = this.getProjectile(bowItem);
        AbstractArrow arrow = ProjectileUtil.getMobArrow(this, projectile, power, bowItem);
        Item var7 = bowItem.getItem();

        if (var7 instanceof ProjectileWeaponItem weaponItem) {
            arrow = weaponItem.customArrow(arrow, projectile, bowItem);
        }

        double xd = target.getX() - this.getX();
        double yd = target.getY(0.3333333333333333) - arrow.getY();
        double zd = target.getZ() - this.getZ();
        double distanceToTarget = Math.sqrt(xd * xd + zd * zd);
        Level var15 = this.level();
        if (var15 instanceof ServerLevel serverLevel) {
            Projectile.spawnProjectileUsingShoot(arrow, serverLevel, projectile, xd, yd + distanceToTarget * (double)0.2F, zd, 1.6F, (float)(14 - serverLevel.getDifficulty().getId() * 4));
        }

        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    public static final DeferredRegister.Entities ENTITY_TYPES =
            DeferredRegister.createEntities(SummonCraft.MODID);

    public static final Supplier<EntityType<Slinger>> SLINGER = ENTITY_TYPES.register(
            "Slinger",
            () -> EntityType.Builder.of(
                    Slinger::new,
                    MobCategory.MISC
            ).build(
                    ResourceKey.create(
                    Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath("SummonCraft", "Slinger")
            ))
    );
}
