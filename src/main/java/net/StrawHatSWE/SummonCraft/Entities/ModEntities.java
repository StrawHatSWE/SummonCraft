package net.StrawHatSWE.SummonCraft.Entities;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.Slingshotter;
import net.StrawHatSWE.SummonCraft.Entities.Projectiles.CobblestoneProjectileEntity;
import net.StrawHatSWE.SummonCraft.Entities.Projectiles.DirtProjectileEntity;
import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, SummonCraft.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<CobblestoneProjectileEntity>> COBBLESTONE_PROJECTILE =
            ENTITY_TYPES.register("cobblestone_projectile", () ->
                    EntityType.Builder.<CobblestoneProjectileEntity>of(CobblestoneProjectileEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("cobblestone_projectile")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<DirtProjectileEntity>> DIRT_PROJECTILE =
            ENTITY_TYPES.register("dirt_projectile", () ->
                    EntityType.Builder.<DirtProjectileEntity>of(DirtProjectileEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("dirt_projectile")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<Slingshotter>> SLINGSHOTTER =
            ENTITY_TYPES.register("slingshotter", () ->
                    EntityType.Builder.<Slingshotter>of(Slingshotter::new, MobCategory.MISC)
                            .sized(0.6F, 1.99F)
                            .clientTrackingRange(8)
                            .updateInterval(3)
                            .build("slingshotter")
            );
}
