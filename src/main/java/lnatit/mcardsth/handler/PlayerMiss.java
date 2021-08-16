package lnatit.mcardsth.handler;

import lnatit.mcardsth.entity.CardEntity;
import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import lnatit.mcardsth.utils.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
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
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;
import static lnatit.mcardsth.utils.PlayerPropertiesUtils.*;
import static net.minecraft.item.Items.EMERALD;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class PlayerMiss
{
    public static final Random rand = new Random();

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void OnPlayerMiss(LivingDeathEvent event)
    {
        if (!(event.getEntityLiving() instanceof ServerPlayerEntity))
            return;

        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        PlayerData data = new PlayerData(player);
        World world = player.world;
        double x = player.chasingPosX - 0.5, y = player.chasingPosY - 0.5, z = player.chasingPosZ - 0.5;

        if (rand.nextFloat() > 0.9F)
        {
            CardEntity cardEntity1 = new CardEntity(world, x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), (AbstractCard) ItemReg.EXTEND.get());
            world.addEntity(cardEntity1);
            cardEntity1.setNoGravity(true);
            cardEntity1.setInvulnerable(true);
            cardEntity1.entityCollisionReduction = 1F;
        }

        if (rand.nextFloat() > 0.98F)
        {
            CardEntity cardEntity2 = new CardEntity(world, x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), (AbstractCard) ItemReg.EXTEND2.get());
            world.addEntity(cardEntity2);
            cardEntity2.setNoGravity(true);
            cardEntity2.setInvulnerable(true);
            cardEntity2.entityCollisionReduction = 1F;
        }

        if (event.getSource().canHarmInCreative())
        {
            PlayerData.DEFAULT.ApplyAndSync(player);
            return;
        }

        if (doPlayersAbilityEnabled(player))
        {
            boolean spawnDrops = PlayerPropertiesUtils.doPlayerCollected(player, (AbstractCard) ItemReg.DBOMBEXTD.get());

            if (PlayerPropertiesUtils.doPlayerCollected(player, (AbstractCard) ItemReg.ROKUMON.get()))
            {
                ItemStack stack = player.inventory.offHandInventory.get(0);

                int loss = Config.ROKUMON_SACRIFICE.get();
                if (stack.getItem() == EMERALD && stack.getCount() >= loss)
                {
                    stack.shrink(loss);

                    //物品使用统计数据更新
                    player.addStat(Stats.ITEM_USED.get(EMERALD), loss);

                    BombType.playerBomb(player.world, player, BombType.S_STRIKE);

                    playerRevive(event, (ServerPlayerEntity) player, false, false);
                    playerRecover((ServerPlayerEntity) player, 8F, new EffectInstance(Effects.RESISTANCE, 20, 5));

                    if (spawnDrops)
                        player.addPotionEffect(new EffectInstance(Effects.LUCK, 30 * 20, 3));

                    return;
                }
            }

            if (PlayerPropertiesUtils.doPlayerCollected(player, (AbstractCard) ItemReg.AUTOBOMB.get()) && data.canSpell())
            {
                data.canSpell();

                //物品使用统计数据更新
                player.addStat(Stats.ITEM_USED.get(ItemReg.ABS_SPELL.get()));
                player.addStat(Stats.ITEM_USED.get(ItemReg.ABS_SPELL.get()));

                BombType.playerBomb(player.world, player, BombType.S_STRIKE);

                playerRevive(event, (ServerPlayerEntity) player, false, false);
                playerRecover((ServerPlayerEntity) player, 12F, new EffectInstance(Effects.RESISTANCE, 20, 5));

                if (spawnDrops)
                    player.addPotionEffect(new EffectInstance(Effects.LUCK, 30 * 20, 3));

                data.ApplyAndSync(player);

                return;
            }

            if (data.canHit())
            {
                //物品使用统计数据更新
                player.addStat(Stats.ITEM_USED.get(ItemReg.ABS_LIFE.get()));

                playerRevive(event, (ServerPlayerEntity) player, spawnDrops, true);
                playerRecover((ServerPlayerEntity) player, 16F, new EffectInstance(Effects.RESISTANCE, 100, 5));

                if (spawnDrops)
                    player.addPotionEffect(new EffectInstance(Effects.LUCK, 30 * 20, 3));

                if (PlayerPropertiesUtils.doPlayerCollected(player, (AbstractCard) ItemReg.DEADSPELL.get()))
                    world.addEntity(new ItemEntity(world, x, y, z, new ItemStack(ItemReg.BOMB.get())));

                data.ApplyAndSync(player);
                return;
            }
        }

        //若事件未被取消，则初始化玩家的PlayerData数据
        if (!event.isCanceled())
            PlayerData.DEFAULT.ApplyAndSync(player);
    }

    public static void playerRevive(LivingDeathEvent event, ServerPlayerEntity serverPlayerEntity, boolean spawnDrops, boolean updateStat)
    {
        //事件取消
        event.setCanceled(true);

        //中立生物仇恨重置（func_241157_eT_()）
        if (serverPlayerEntity.world.getGameRules().getBoolean(GameRules.FORGIVE_DEAD_PLAYERS))
            EntityDeathUtils.forgivePlayer(serverPlayerEntity);

        EntityDeathUtils.spawnDrops(serverPlayerEntity,
                                    spawnDrops,
                                    doPlayerCollected(serverPlayerEntity, (AbstractCard) ItemReg.KOISHI.get()) ? 5 : 10,
                                    doPlayerCollected(serverPlayerEntity, (AbstractCard) ItemReg.POWERMAX.get()));

        if (updateStat)
        {
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
                    }
                    else if (team.getDeathMessageVisibility() == Team.Visible.HIDE_FOR_OWN_TEAM)
                    {
                        serverPlayerEntity.server.getPlayerList().sendMessageToTeamOrAllPlayers(serverPlayerEntity, itextcomponent);
                    }
                }
                else
                {
                    serverPlayerEntity.server.getPlayerList().func_232641_a_(itextcomponent, ChatType.SYSTEM, Util.DUMMY_UUID);
                }
            }

            //计分板数据更新
            serverPlayerEntity.getWorldScoreboard().forAllObjectives(ScoreCriteria.DEATH_COUNT, serverPlayerEntity.getScoreboardName(), Score::incrementScore);

            //统计数据（被生物杀死）更新
            LivingEntity damageSource = serverPlayerEntity.getAttackingEntity();
            if (damageSource != null)
            {
                serverPlayerEntity.addStat(Stats.ENTITY_KILLED_BY.get(damageSource.getType()));
                CriteriaTriggers.ENTITY_KILLED_PLAYER.trigger(serverPlayerEntity, damageSource, event.getSource());
                //生成凋零玫瑰
                EntityDeathUtils.createWitherRose(serverPlayerEntity, damageSource);
            }

            //死亡相关统计数据更新
            serverPlayerEntity.addStat(Stats.DEATHS);
            serverPlayerEntity.takeStat(Stats.CUSTOM.get(Stats.TIME_SINCE_DEATH));
            serverPlayerEntity.takeStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));

            //重置战斗纪录
            serverPlayerEntity.getCombatTracker().reset();
        }
    }

    public static void playerRecover(ServerPlayerEntity serverPlayerEntity, float health, EffectInstance... effects)
    {
        //生命回复，饱食度回复，所有效果（药水&火焰&窒息）清除，无敌5s
        serverPlayerEntity.setHealth(health);
        serverPlayerEntity.clearActivePotions();
        for (EffectInstance instance : effects)
            serverPlayerEntity.addPotionEffect(instance);
        serverPlayerEntity.extinguish();
    }
}
