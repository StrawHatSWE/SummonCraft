package net.StrawHatSWE.SummonCraft.Entities;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class PlayerSummon extends PathfinderMob implements OwnableEntity {
    protected static EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_OWNERUUID_ID;

    protected PlayerSummon(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        entityData.set(DATA_OWNERUUID_ID, Optional.empty());
    }

    public PlayerSummon(EntityType<? extends PathfinderMob> type, Level level, Player player) {
        this(type, level);

        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(player).map(EntityReference::of));
    }

    protected void defineSynchedData(@NotNull SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DATA_OWNERUUID_ID, Optional.empty());
    }
}
