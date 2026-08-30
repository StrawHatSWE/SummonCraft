package net.StrawHatSWE.SummonCraft.Items.Staffs;

import net.StrawHatSWE.SummonCraft.Entities.Mobs.PlayerSummon;
import net.minecraft.world.entity.EntityType;

import java.util.List;
import java.util.function.Supplier;

public class SlingshotterStaffItem extends SummonStaffItem {
    public SlingshotterStaffItem(Supplier<? extends EntityType<? extends PlayerSummon>> summonEntities, Properties properties) {
        super(summonEntities, properties);
    }
}
