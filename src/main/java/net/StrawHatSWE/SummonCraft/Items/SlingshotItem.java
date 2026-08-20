package net.StrawHatSWE.SummonCraft.Items;

import net.StrawHatSWE.SummonCraft.Entities.Projectiles.CobblestoneProjectileEntity;
import net.StrawHatSWE.SummonCraft.Entities.Projectiles.DirtProjectileEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class SlingshotItem extends BowItem {

    public static final Predicate<ItemStack> SLINGSHOT_AMMO = itemStack ->
            itemStack.is(Items.DIRT) || itemStack.is(Items.COBBLESTONE);

    public SlingshotItem(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeCharged) {
        if (entity instanceof Player player) {
            ItemStack ammoStack = player.getProjectile(stack);

            if (!ammoStack.isEmpty() || player.hasInfiniteMaterials()) {
                if (ammoStack.isEmpty()) {
                    ammoStack = new ItemStack(Items.DIRT);
                }

                int useTime = this.getUseDuration(stack, entity) - timeCharged;
                useTime = EventHooks.onArrowLoose(stack, level, player, useTime, !ammoStack.isEmpty());

                if (useTime < 0) return;

                float power = getPowerForTime(useTime);

                if ((double) power >= 0.1) {
                    List<ItemStack> list = draw(stack, ammoStack, player);

                    if (level instanceof ServerLevel serverLevel && !list.isEmpty()) {
                        this.shoot(
                                serverLevel,
                                player,
                                player.getUsedItemHand(),
                                stack,
                                list,
                                power * 1.5F,
                                1.0F,
                                power == 1.0F,
                                null
                        );
                    }

                    level.playSound(
                            null,
                            player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ARROW_SHOOT,
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F
                    );

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

        @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weaponStack, ItemStack ammoStack, boolean isCritical) {
        if (ammoStack.is(Items.COBBLESTONE)) {
            return new CobblestoneProjectileEntity(level, shooter, ammoStack, weaponStack);
        }

        return new DirtProjectileEntity(level, shooter, ammoStack, weaponStack);
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + angle, 0.0F, velocity, inaccuracy);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return SLINGSHOT_AMMO;
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles(ItemStack item) {
        return SLINGSHOT_AMMO;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 10;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }
}