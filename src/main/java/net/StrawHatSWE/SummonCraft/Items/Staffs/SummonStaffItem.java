package net.StrawHatSWE.SummonCraft.Items.Staffs;

import net.StrawHatSWE.SummonCraft.Attachments.ModAttachments;
import net.StrawHatSWE.SummonCraft.Attachments.PlayerSummonData;
import net.StrawHatSWE.SummonCraft.Entities.Mobs.PlayerSummon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

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

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (!level.isClientSide()) {
            PlayerSummon summon = this.summonEntities.getFirst().get().create(level);
            PlayerSummonData summonData = player.getData(ModAttachments.SUMMON_DATA);

            assert summon != null;

            ItemStack staff = context.getItemInHand();
            staff.hurtAndBreak(
                    1,
                    player,
                    context.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND
            );

            BlockPos clickedPos = context.getClickedPos();
            Direction clickedFace = context.getClickedFace();
            BlockPos targetPos = clickedPos.relative(clickedFace);

            summon.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(targetPos), MobSpawnType.MOB_SUMMONED, null);

            summon.moveTo(
                    targetPos.getX() + 0.5D,
                    targetPos.getY(),
                    targetPos.getZ() + 0.5D,
                    context.getPlayer() != null ? context.getPlayer().getYRot() : 0.0F,
                    0.0F
            );

            if (player instanceof ServerPlayer serverPlayer) {
                summonData.addSummon(serverPlayer, level, summon.getUUID());
            }

            summon.setOwner(context.getPlayer());

            level.addFreshEntity(summon);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
