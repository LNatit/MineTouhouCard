package deeplake.idlframework.idlnbtutils;

import lnatit.mcardsth.network.NBTPacket;
import lnatit.mcardsth.network.NetworkManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;

import static deeplake.idlframework.idlnbtutils.IDLNBT.*;

/**
 * Simplified util class
 *
 * @author TaoismDeepLake
 */
public class IDLNBTUtils
{
    public static CompoundNBT getNBT(Entity entity)
    {
        CompoundNBT nbt = entity.getPersistentData();
        return nbt;
    }

    public static boolean SetInt(Entity entity, String key, int value)
    {
        CompoundNBT nbt = getNBT(entity);
        nbt.putInt(key, value);
        sync(entity, key, nbt, (byte) 2);
        return true;
    }

    public static boolean SetInt(Entity entity, String key, int value, boolean doSync)
    {
        CompoundNBT nbt = getNBT(entity);
        nbt.putInt(key, value);
        if (doSync)
            sync(entity, key, nbt, (byte) 2);
        return true;
    }

    public static boolean SetIntAuto(Entity entity, String key, int value)
    {
        if (entity instanceof PlayerEntity)
        {
            setPlayerIdeallandTagSafe((PlayerEntity) entity, key, value);
            return true;
        }
        CompoundNBT nbt = getNBT(entity);
        nbt.putInt(key, value);
        return true;
    }

    public static boolean AddIntAuto(Entity entity, String key, int value)
    {
        int oldVal = GetIntAuto(entity, key, 0);
        SetIntAuto(entity, key, value + oldVal);
        return true;
    }

    public static int GetInt(Entity entity, String key, int defaultVal)
    {
        if (EntityHasKey(entity, key))
        {
            CompoundNBT nbt = getNBT(entity);
            return nbt.getInt(key);
        }
        else return defaultVal;
    }

    public static int GetIntAuto(Entity entity, String key, int defaultVal)
    {
        if (entity instanceof PlayerEntity)
        {
            return getPlayerIdeallandIntSafe((PlayerEntity) entity, key);
        }

        if (EntityHasKey(entity, key))
        {
            CompoundNBT nbt = getNBT(entity);
            return nbt.getInt(key);
        }
        else return defaultVal;
    }

    public static boolean SetBoolean(Entity entity, String key, boolean value)
    {
        CompoundNBT nbt = getNBT(entity);
        nbt.putBoolean(key, value);
        sync(entity, key, nbt, (byte) 1);
        return true;
    }

    public static boolean SetBoolean(Entity entity, String key, boolean value, boolean doSync)
    {
        CompoundNBT nbt = getNBT(entity);
        nbt.putBoolean(key, value);
        if (doSync)
            sync(entity, key, nbt, (byte) 1);
        return true;
    }

    public static boolean GetBoolean(Entity entity, String key, boolean defaultVal)
    {
        if (EntityHasKey(entity, key))
        {
            CompoundNBT nbt = getNBT(entity);
            return nbt.getBoolean(key);
        }
        else return defaultVal;
    }

    public static boolean SetString(Entity entity, String key, String value)
    {
        CompoundNBT nbt = getNBT(entity);
        nbt.putString(key, value);
        sync(entity, key, nbt, (byte) 3);
        return true;
    }

    public static boolean SetString(Entity entity, String key, String value, boolean doSync)
    {
        CompoundNBT nbt = getNBT(entity);
        nbt.putString(key, value);
        if (doSync)
            sync(entity, key, nbt, (byte) 3);
        return true;
    }

    public static String GetString(Entity entity, String key, String defaultVal)
    {
        if (EntityHasKey(entity, key))
        {
            CompoundNBT nbt = getNBT(entity);
            return nbt.getString(key);
        }
        else return defaultVal;
    }

//    public static int[] GetIntArray(LivingEntity entity, String key)
//    {
//        if (EntityHasKey(entity, key))
//        {
//            CompoundNBT nbt = getNBT(entity);
//            return nbt.getIntArray(key);
//        }
//        else
//        {
//            return new int[0];
//        }
//    }

    @Nullable
    public static boolean EntityHasKey(Entity entity, String key)
    {
        return getNBT(entity).contains(key);
    }

    private static void sync(Entity entity, String key, CompoundNBT nbt, byte typeIndex)
    {
        if (entity instanceof ServerPlayerEntity)
            NetworkManager.serverSendToPlayer(new NBTPacket(key, nbt, typeIndex), (ServerPlayerEntity) entity);
    }

    public static void sync(ServerPlayerEntity player, CompoundNBT nbt)
    {
        NetworkManager.serverSendToPlayer(new NBTPacket(null, nbt, (byte) 0), player);
    }

    public static void syncAll(ServerPlayerEntity player)
    {
        NetworkManager.serverSendToPlayer(new NBTPacket(null, player.getPersistentData(), (byte) 0), player);
    }
}
