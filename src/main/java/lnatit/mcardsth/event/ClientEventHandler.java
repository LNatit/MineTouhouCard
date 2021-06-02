package lnatit.mcardsth.event;

import lnatit.mcardsth.entity.EntityTypeReg;
import lnatit.mcardsth.entity.InstantCardRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler
{
    @SubscribeEvent
    public static void onClientSetUpEvent(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeReg.INSTANT_CARD.get(), InstantCardRenderer::new);
    }
}
