package lnatit.mcardsth.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;
import java.util.List;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class PlayerBomb
{
    @SubscribeEvent
    public static void onExplosionDetonate(ExplosionEvent.Detonate event)
    {
        List<Entity> list = event.getAffectedEntities();
        List<Entity> temp = new LinkedList<>();
        Entity exploder = event.getExplosion().getExploder();
        if (exploder instanceof PlayerEntity)
        {
            list.remove(exploder);
            for (Entity entity: list)
            {
                if (entity instanceof LivingEntity && !entity.isOnSameTeam(exploder))
                {
                    entity.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) exploder), 20F);
                    temp.add(entity);
                }
            }
            for (Entity entity: temp)
                list.remove(entity);
        }
    }
}
