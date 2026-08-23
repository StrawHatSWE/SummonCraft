package net.StrawHatSWE.SummonCraft.Items;

import net.StrawHatSWE.SummonCraft.Entities.ModEntities;
import net.StrawHatSWE.SummonCraft.Items.Staffs.SlingshotterStaffItem;
import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SummonCraft.MOD_ID);

    public static final DeferredItem<Item> SLINGSHOT = ITEMS.registerItem(
            "slingshot",
            SlingshotItem::new,
            new Item.Properties().durability(256)
    );

    public static final DeferredItem<Item> SLINGSHOTTER_STAFF = ITEMS.registerItem(
            "slingshotter_staff",
            properties -> new SlingshotterStaffItem(ModEntities.SLINGSHOTTER, properties),
            new Item.Properties().durability(256)
    );

    public static final DeferredItem<Item> SOUL_ESSENCE = ITEMS.registerItem(
            "soul_essence",
            properties -> new SoulEssenceItem(properties),
            new Item.Properties().stacksTo(64)
    );

    public static final DeferredItem<Item> SOUL_CRYSTAL = ITEMS.registerItem(
            "soul_crystal",
            properties -> new SoulCrystalItem(properties),
            new Item.Properties()
    );
}
