package net.StrawHatSWE.SummonCraft.Attachments;

import net.StrawHatSWE.SummonCraft.SummonCraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = SummonCraft.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class PlayerServerEvents {

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        PlayerSummonData summonData = player.getData(ModAttachments.SUMMON_DATA);

        if (!player.level().isClientSide()) {
            summonData.clearSummons(player.level());
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            PlayerSummonData summonData = player.getData(ModAttachments.SUMMON_DATA);

            if (!player.level().isClientSide()) {
                summonData.clearSummons(player.level());
            }
        }
    }
}
