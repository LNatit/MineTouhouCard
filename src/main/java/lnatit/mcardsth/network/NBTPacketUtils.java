package lnatit.mcardsth.network;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import static deeplake.idlframework.idlnbtutils.IDLNBT.getPlayerIdlTagSafe;
import static deeplake.idlframework.idlnbtutils.IDLNBT.getTagSafe;
import static deeplake.idlframework.idlnbtutils.IDLNBTConst.MCARDSTH;

public class NBTPacketUtils
{
    public static CompoundNBT getPlayerIdlTagSafe(ClientPlayerEntity player)
    {
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = getTagSafe(playerData, ClientPlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getTagSafe(data, MCARDSTH);

        return idl_data;
    }

    public static void setPlayerIdeallandTagSafe(ClientPlayerEntity player, String key, int value)
    {
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = getTagSafe(playerData, ClientPlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getPlayerIdlTagSafe(player);

        idl_data.putInt(key, value);

        data.put(MCARDSTH, idl_data);
        playerData.put(ClientPlayerEntity.PERSISTED_NBT_TAG, data);
    }

    public static void setPlayerIdeallandTagSafe(ClientPlayerEntity player, String key, int[] value)
    {
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = getTagSafe(playerData, ClientPlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getPlayerIdlTagSafe(player);

        idl_data.putIntArray(key, value);

        data.put(MCARDSTH, idl_data);
        playerData.put(ClientPlayerEntity.PERSISTED_NBT_TAG, data);
    }

    public static void setPlayerIdeallandTagSafe(ClientPlayerEntity player, String key, double value)
    {
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = getTagSafe(playerData, ClientPlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getPlayerIdlTagSafe(player);

        idl_data.putDouble(key, value);

        data.put(MCARDSTH, idl_data);
        playerData.put(ClientPlayerEntity.PERSISTED_NBT_TAG, data);
    }

    public static void setPlayerIdeallandTagSafe(ClientPlayerEntity player, String key, boolean value)
    {
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = getTagSafe(playerData, ClientPlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getPlayerIdlTagSafe(player);

        idl_data.putBoolean(key, value);

        data.put(MCARDSTH, idl_data);
        playerData.put(ClientPlayerEntity.PERSISTED_NBT_TAG, data);
    }

    public static void setPlayerIdeallandTagSafe(ClientPlayerEntity player, String key, String value)
    {
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = getTagSafe(playerData, ClientPlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getPlayerIdlTagSafe(player);

        idl_data.putString(key, value);

        data.put(MCARDSTH, idl_data);
        playerData.put(ClientPlayerEntity.PERSISTED_NBT_TAG, data);
    }

    public static void mergePlayerIdeallandTangSafe(ClientPlayerEntity player, CompoundNBT nbtIn)
    {
        CompoundNBT nbt = player.getPersistentData();
        CompoundNBT data = getTagSafe(nbt, PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getPlayerIdlTagSafe(player);

        idl_data.merge(nbtIn);

        data.put(MCARDSTH, idl_data);
        nbt.put(PlayerEntity.PERSISTED_NBT_TAG, data);
    }
}
