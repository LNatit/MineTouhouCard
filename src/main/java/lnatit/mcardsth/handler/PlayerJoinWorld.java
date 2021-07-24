package lnatit.mcardsth.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static deeplake.idlframework.idlnbtutils.IDLNBT.syncAll;
import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class PlayerJoinWorld
{
    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayerEntity)
            syncAll((ServerPlayerEntity) entity);
    }
}
