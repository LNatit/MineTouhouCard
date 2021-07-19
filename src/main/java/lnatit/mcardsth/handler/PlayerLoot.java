package lnatit.mcardsth.handler;

import lnatit.mcardsth.item.ItemReg;
import lnatit.mcardsth.utils.AbilityCardUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class PlayerLoot
{
    @SubscribeEvent
    public static void onPlayerLoot(LootingLevelEvent event)
    {
        if (event.getDamageSource().getTrueSource() != null)
        {
            Entity entity = event.getDamageSource().getTrueSource();
            if (entity instanceof ServerPlayerEntity && AbilityCardUtils.doPlayerHold((PlayerEntity) entity, ItemReg.MONEY.get()))
                event.setLootingLevel(event.getLootingLevel() + 3);
        }
    }
}
