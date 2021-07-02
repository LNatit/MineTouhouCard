package lnatit.mcardsth.handler;

import lnatit.mcardsth.capability.PlayerProperties;
import lnatit.mcardsth.capability.PlayerPropertiesProvider;
import lnatit.mcardsth.event.FakeClone;
import lnatit.mcardsth.item.ItemReg;
import lnatit.mcardsth.utils.LifeRenderer;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.Stats;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;
import static lnatit.mcardsth.handler.EntityUtils.*;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class PlayerMiss
{
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void OnPlayerMiss(LivingDeathEvent event)
    {
        LivingEntity livingEntity = event.getEntityLiving();

        //TODO package the method.
        if (livingEntity instanceof ServerPlayerEntity)
        {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) livingEntity;
            LazyOptional<PlayerProperties> cap = serverPlayerEntity.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
            PlayerProperties playerProperties = cap.orElse(null);

            //TODO optimize logic when /kill

            if (!playerProperties.canHit(serverPlayerEntity))
                return;

            //死亡信息广播
            boolean flag = serverPlayerEntity.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES);
            if (flag)
            {
                ITextComponent itextcomponent = serverPlayerEntity.getCombatTracker().getDeathMessage();
                Team team = serverPlayerEntity.getTeam();
                if (team != null && team.getDeathMessageVisibility() != Team.Visible.ALWAYS)
                {
                    if (team.getDeathMessageVisibility() == Team.Visible.HIDE_FOR_OTHER_TEAMS)
                    {
                        serverPlayerEntity.server.getPlayerList().sendMessageToAllTeamMembers(serverPlayerEntity, itextcomponent);
                    } else if (team.getDeathMessageVisibility() == Team.Visible.HIDE_FOR_OWN_TEAM)
                    {
                        serverPlayerEntity.server.getPlayerList().sendMessageToTeamOrAllPlayers(serverPlayerEntity, itextcomponent);
                    }
                } else
                {
                    serverPlayerEntity.server.getPlayerList().func_232641_a_(itextcomponent, ChatType.SYSTEM, Util.DUMMY_UUID);
                }
            }

            //事件取消
            event.setCanceled(true);

            //物品使用统计数据更新
            serverPlayerEntity.addStat(Stats.ITEM_USED.get(ItemReg.ABS_LIFE.get()));

            //中立生物仇恨重置（func_241157_eT_()）
            if (serverPlayerEntity.world.getGameRules().getBoolean(GameRules.FORGIVE_DEAD_PLAYERS))
            {
                forgivePlayer(serverPlayerEntity);
            }

            //TODO unfinished 生成掉落物（物品和经验，非全掉落）（重写 spawnDrops()）
            spawnDrops(serverPlayerEntity);

            //计分板数据更新
            serverPlayerEntity.getWorldScoreboard().forAllObjectives(ScoreCriteria.DEATH_COUNT, serverPlayerEntity.getScoreboardName(), Score::incrementScore);

            //统计数据（被生物杀死）更新
            LivingEntity damageSource = serverPlayerEntity.getAttackingEntity();
            if (damageSource != null)
            {
                serverPlayerEntity.addStat(Stats.ENTITY_KILLED_BY.get(damageSource.getType()));
                CriteriaTriggers.ENTITY_KILLED_PLAYER.trigger(serverPlayerEntity, damageSource, event.getSource());
                //生成凋零玫瑰
                createWitherRose(serverPlayerEntity, damageSource);
            }

            //死亡相关统计数据更新
            serverPlayerEntity.addStat(Stats.DEATHS);
            serverPlayerEntity.takeStat(Stats.CUSTOM.get(Stats.TIME_SINCE_DEATH));
            serverPlayerEntity.takeStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));

            //生命回复，饱食度回复，所有效果（药水&火焰&窒息）清除，无敌5s
            livingEntity.setHealth(10F);
            livingEntity.clearActivePotions();
            livingEntity.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 100, 5));
            livingEntity.extinguish();

            //重置战斗纪录
            serverPlayerEntity.getCombatTracker().reset();

            //发布假事件
            MinecraftForge.EVENT_BUS.post(new FakeClone(serverPlayerEntity, serverPlayerEntity, true));

            net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerRespawnEvent(serverPlayerEntity, false);
        }
    }
}
