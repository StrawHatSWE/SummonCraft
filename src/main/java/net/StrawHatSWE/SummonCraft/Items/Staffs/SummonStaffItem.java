package net.StrawHatSWE.SummonCraft.Items.Staffs;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.PlayerSummon;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Supplier;

public class SummonStaffItem extends Item {
    public SummonStaffItem(List<Supplier<? extends EntityType<? extends PlayerSummon>>> summonEntities, Properties properties) {
        super(properties);
        this.summonEntities = summonEntities;
    }

    public SummonStaffItem(Supplier<? extends EntityType<? extends PlayerSummon>> summonEntities, Properties properties) {
        super(properties);
        this.summonEntities = List.of(summonEntities);
    }

    protected List<Supplier<? extends EntityType<? extends PlayerSummon>>> summonEntities;
}
