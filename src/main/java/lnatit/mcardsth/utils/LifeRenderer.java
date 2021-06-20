package lnatit.mcardsth.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import lnatit.mcardsth.capability.PlayerProperties;
import lnatit.mcardsth.capability.PlayerPropertiesProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.util.LazyOptional;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

//@OnlyIn(Dist.CLIENT) TODO take care of after-revive logic
public class LifeRenderer
{
    public static final int PERSIST = 5*20;
    public static final int FADE = 20;//ticks
    public static final int PATTERN_PIXEL = 0;   // TODO unfinished: transfer to config.
    public static final int SIZE = 9;
    public static final int GAP = 1;
    private static final ResourceLocation ICONS = new ResourceLocation(MOD_ID, "textures/icons.png");

//    private static int lifeTotal = 0;
//    private static int lifeTotalLast = 0;

    private static boolean doRender = true;

    private static int tickCount = 0;
    private static float alpha = 0;
    private static boolean doFade = false;

    public static void Render(MatrixStack matrixStack)
    {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;

        int left = mc.getMainWindow().getScaledWidth() / 2 - 91;
        int top = mc.getMainWindow().getScaledHeight() - ForgeIngameGui.left_height + SIZE + GAP;
        int lifeTotal = getPlayerLife(player);

        renderLife(lifeTotal, mc, matrixStack, left, top, true);
    }

    private static void renderLife(int lifeRemain, Minecraft mc, MatrixStack matrixStack, int left, int top, boolean enableAlpha)
    {
        mc.getTextureManager().bindTexture(ICONS);

        if (enableAlpha)
            enableAlpha();

        if (doRender)
        {
            int frag = lifeRemain % 3;
            int intact = (lifeRemain - frag) / 3;
            int uOffset = 0;

            for (int i = 0; i < intact; i++)
            {
                AbstractGui.blit(matrixStack, left, top, uOffset, PATTERN_PIXEL, SIZE, SIZE, 36, 18);
                left += SIZE + GAP;
            }

            if (frag != 0)
            {
                uOffset += frag * SIZE;
                AbstractGui.blit(matrixStack, left, top, uOffset, PATTERN_PIXEL, SIZE, SIZE, 36, 18);
            }
        }

        disableAlpha();

        mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
    }

    /**
     * Util Methods below
     */
    private static void enableAlpha()
    {
        RenderSystem.enableBlend();

        if (alpha == 1.0f)
            return;

        RenderSystem.color4f(1.0f, 1.0f, 1.0f, alpha);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }

    private static void disableAlpha()
    {
        RenderSystem.disableBlend();

        if (alpha == 1.0f)
            return;

        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void initRendererUpdater()
    {
        doRender = true;
        tickCount = 0;
        doFade = false;
    }

    public static void updateRenderer()
    {
        if (doRender)
        {
            tickCount++;

            if (tickCount > (PERSIST - FADE))
            {
                initAlphaUpdater();
                updateAlpha();
            }

            if (tickCount == PERSIST)
                doRender = false;
        }
    }

    private static void initAlphaUpdater()
    {
        if (!doFade)
        {
            alpha = 1;
            doFade = true;
        }
    }

    private static void updateAlpha()
    {
        if (!doFade)
            return;

        alpha = 1 - ((float) (tickCount - PERSIST + FADE) / FADE);
    }

    private static int getPlayerLife(PlayerEntity player)
    {
        int count = 0;

        LazyOptional<PlayerProperties> cap = player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        PlayerProperties playerProperties = cap.orElse(null);

        count += 3 * (playerProperties.getLife());
        count += playerProperties.getLifeFragment();
        return count;
    }
}
