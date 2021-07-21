package lnatit.mcardsth.handler;

import lnatit.mcardsth.item.ItemReg;
import lnatit.mcardsth.utils.AbilityCardUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

//TODO remains to be overwritten
@Mod.EventBusSubscriber(modid = MOD_ID)
public class PlayerBeingAttacked
{
    private static Random random = new Random();

    @SubscribeEvent
    public static void onPlayerBeingAttacked(LivingAttackEvent event)
    {
        if (event.getEntityLiving() instanceof ServerPlayerEntity)
        {
            DamageSource source = event.getSource();
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
            if (AbilityCardUtils.doPlayerHold(player, ItemReg.KOISHI.get()) &&
                    !source.isUnblockable() &&
                    !source.canHarmInCreative() &&
                    !source.isExplosion() &&
                    !source.isProjectile() &&
                    !source.isMagicDamage() &&
                    !source.isFireDamage() &&
                    source.getTrueSource() != null &&
                    !source.getTrueSource().getTags().contains("boss"))
                event.setCanceled(true);
            else if (AbilityCardUtils.doPlayerHold(player, ItemReg.SPEEDQUEEN.get()) &&
                    random.nextBoolean() && !player.isSneaking() &&
                    (player.inventory.getCurrentItem().isEmpty() ||
                            player.inventory.getCurrentItem().getItem() == ItemReg.SPEEDQUEEN.get()))
                event.setCanceled(true);
        }
    }
}
