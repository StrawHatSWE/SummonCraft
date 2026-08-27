package net.StrawHatSWE.SummonCraft.UserInterface;

import net.StrawHatSWE.SummonCraft.Attachments.ModAttachments;
import net.StrawHatSWE.SummonCraft.Attachments.PlayerSummonData;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameType;

public class SummonSlotOverlay implements LayeredDraw.Layer {
    private static final ResourceLocation BAR_BG_SPRITE =
            ResourceLocation.withDefaultNamespace("hud/experience_bar_background");
    private static final ResourceLocation BAR_FILL_SPRITE =
            ResourceLocation.withDefaultNamespace("hud/experience_bar_progress");

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            return;
        }

        PlayerSummonData summonData = mc.player.getData(ModAttachments.SUMMON_DATA);

        float maxSlots = summonData.getActualMaximum();
        float currentSlots = summonData.getCurrentSummonUsage();
        if (maxSlots <= 0 || currentSlots <= 0) return;

        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        int x = screenWidth / 2 + 10;
        int y = screenHeight - 49;
        int barWidth = 80;
        int barHeight = 5;

        guiGraphics.blitSprite(BAR_BG_SPRITE, x, y, barWidth, barHeight);

        float ratio = (float) currentSlots / maxSlots;
        int filledWidth = (int) (ratio * barWidth);

        if (filledWidth > 0) {
            guiGraphics.blitSprite(BAR_FILL_SPRITE, barWidth, barHeight, 0, 0, x, y, filledWidth, barHeight);
        }
    }
}
