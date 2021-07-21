package lnatit.mcardsth.handler;

import lnatit.mcardsth.utils.LifeRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Deprecated
@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class GameOverlayRender
{
//    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
//    public static void onGameOverlayRender(RenderGameOverlayEvent.Post event)
//    {
//        if (event.getType() != RenderGameOverlayEvent.ElementType.HEALTH)
//            return;
//
//        LifeRenderer.Render(event.getMatrixStack());
//    }
}
