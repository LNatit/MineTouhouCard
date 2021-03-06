package lnatit.mcardsth.utils;

import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Utils for player's death-related methods
 */
public class EntityDeathUtils
{
    public static Random rand = new Random();

    public static void forgivePlayer(ServerPlayerEntity player)
    {
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(player.getPosition())).grow(32.0D, 10.0D, 32.0D);
        player.world
                .getLoadedEntitiesWithinAABB(MobEntity.class, axisalignedbb)
                .stream()
                .filter((entity) ->
                        {
                            return entity instanceof IAngerable;
                        })
                .forEach((mobEntity) ->
                         {
                             ((IAngerable) mobEntity).func_233681_b_(player);
                         });
    }

    public static void spawnDrops(ServerPlayerEntity player, boolean doDrop, int loss, boolean powermax)
    {
        dropInventory(player, doDrop);
        dropExperience(player, loss, powermax);
    }

    public static void dropInventory(ServerPlayerEntity player, boolean doDrop)
    {
        if (!player.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && doDrop)
        {
            CursedItemsVanish(player);
            PlayerInventory playerInventory = player.inventory;
            NonNullList<ItemStack> inventory = playerInventory.armorInventory;

            for (int i = 0; i < inventory.size(); i++)
            {
                ItemStack itemStack = inventory.get(i);
                if (!itemStack.isEmpty())
                {
                    playerInventory.player.dropItem(itemStack, true, false);
                    inventory.set(i, ItemStack.EMPTY);
                }
            }

            inventory = playerInventory.mainInventory;

            ItemStack currentItem = playerInventory.getCurrentItem();
            for (int i = 0; i < inventory.size(); i++)
            {
                ItemStack itemStack = inventory.get(i);
                if (!itemStack.isEmpty() && itemStack != currentItem)
                {
                    playerInventory.player.dropItem(itemStack, true, false);
                    inventory.set(i, ItemStack.EMPTY);
                }
            }
        }
    }

    public static void CursedItemsVanish(ServerPlayerEntity player)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (!itemstack.isEmpty() && EnchantmentHelper.hasVanishingCurse(itemstack))
                player.inventory.removeStackFromSlot(i);
        }

    }

    public static void dropExperience(ServerPlayerEntity player, int loss, boolean powermax)
    {
        int i = 0;
        if (!player.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY))
        {
            int expLvl = player.experienceLevel;
            if (expLvl >= 40)
            {
                i = expLvl - 40 + loss * getExperienceOf(40 - loss + 1);
                player.setExperienceLevel(40 - loss);
            }
            else
            {
                i = loss;
                if (powermax && expLvl > 20 && expLvl - i < 20)
                    i = expLvl - 20;
                player.setExperienceLevel(expLvl - i);
                i = i * getExperienceOf(expLvl - i + 1);
            }
        }

        if (PlayerPropertiesUtils.doPlayerCollected(player, (AbstractCard) ItemReg.POWERMAX.get()))


        while (i > 0)
        {
            int j = ExperienceOrbEntity.getXPSplit(i);
            i -= j;
            double x = player.getPosX() + 4 * rand.nextDouble() - 2;
            double y = player.getPosY() + 4 * rand.nextDouble() - 2;
            double z = player.getPosZ() + 4 * rand.nextDouble() - 2;
            player.world.addEntity(new ExperienceOrbEntity(player.world, x, y, z, j));
        }

    }

    public static int getExperienceOf(int level)
    {
        int exp;
        if (level > 0 && level <= 15)
            exp = 2 * level + 7;
        else if (level <= 30)
            exp = 5 * level - 38;
        else exp = 9 * level - 158;
        return exp < 0 ? 0 : exp;
    }

    public static void createWitherRose(ServerPlayerEntity player, @Nullable LivingEntity damageSource)
    {
        if (!player.world.isRemote)
        {
            boolean flag = false;
            if (damageSource instanceof WitherEntity)
            {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(player.world, player))
                {
                    BlockPos blockpos = player.getPosition();
                    BlockState blockstate = Blocks.WITHER_ROSE.getDefaultState();
                    if (player.world.isAirBlock(blockpos) && blockstate.isValidPosition(player.world, blockpos))
                    {
                        player.world.setBlockState(blockpos, blockstate, 3);
                        flag = true;
                    }
                }

                if (!flag)
                {
                    ItemEntity itementity = new ItemEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ(), new ItemStack(Items.WITHER_ROSE));
                    player.world.addEntity(itementity);
                }
            }
        }
    }
}
