package net.StrawHatSWE.SummonCraft.Entities.Mobs;

import net.StrawHatSWE.SummonCraft.Entities.Projectiles.*;
import net.StrawHatSWE.SummonCraft.Items.ModItems;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.apache.commons.lang3.RandomUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class Slingshotter extends PlayerSummon implements RangedAttackMob {
    Predicate<LivingEntity> targetPredicate = target ->
            target instanceof Enemy
                    && !(target instanceof Creeper)
                    && !(target instanceof PlayerSummon);

    public Slingshotter(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    private final static List<ItemStack> ammo = List.of(
            new ItemStack(Items.COBBLESTONE),
            new ItemStack(Items.DIRT)
    );

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25D, 30, 15.0F));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, true, targetPredicate));

    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData);

        if (this.getMainHandItem().isEmpty()) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.SLINGSHOT.get()));
        }

        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);

        return data;
    }

    private AbstractArrow getArrow(ItemStack ammo) {
        ItemStack weapon = this.getMainHandItem();

        // Fallback if entity has empty hand
        if (weapon.isEmpty()) {
            weapon = new ItemStack(ModItems.SLINGSHOT.get());
        }

        if (ammo.is(Items.COBBLESTONE)) {
            return new CobblestoneProjectileEntity(this.level(), this, ammo.copyWithCount(1), weapon);
        }
        return new DirtProjectileEntity(this.level(), this, ammo.copyWithCount(1), weapon);
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float v) {
        ItemStack ammo = Slingshotter.ammo.get(RandomUtils.nextInt(0, Slingshotter.ammo.size()));
        AbstractArrow projectile = this.getArrow(ammo);

        System.out.println("ammo: " + ammo);
        System.out.println("projectile: " + projectile);


        double d0 = livingEntity.getX() - this.getX();
        double d1 = livingEntity.getY(0.3333333333333333) - projectile.getY();
        double d2 = livingEntity.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        projectile.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(projectile);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);

        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.SLINGSHOT.get()));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }
}
