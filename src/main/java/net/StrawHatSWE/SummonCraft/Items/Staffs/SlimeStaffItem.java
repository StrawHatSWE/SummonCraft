package net.StrawHatSWE.SummonCraft.Items.Staffs;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.PlayerSummon;
import net.minecraft.world.entity.EntityType;

import java.util.List;
import java.util.function.Supplier;

public class SlimeStaffItem extends SummonStaffItem{
    public SlimeStaffItem(Supplier<? extends EntityType<? extends PlayerSummon>> summonEntity, Properties properties) {
        super(summonEntity, properties);
    }
}
