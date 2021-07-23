package deeplake.idlframework.idlnbtutils;

import lnatit.mcardsth.network.NBTPacket;
import lnatit.mcardsth.network.NetworkManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import static deeplake.idlframework.idlnbtutils.IDLNBTConst.MCARDSTH;

/**
 * @author TaoismDeepLake
 */
public class IDLNBT
{
    public static CompoundNBT getTagSafe(CompoundNBT tag, String key) {
        if(tag == null) {
            return new CompoundNBT();
        }

        return tag.getCompound(key);
    }

    public static CompoundNBT getPlayerIdlTagSafe(PlayerEntity player) {
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = getTagSafe(playerData, PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getTagSafe(data, MCARDSTH);

        return idl_data;
    }

    public static CompoundNBT getPlayerIdeallandTagGroupSafe(PlayerEntity player, String key) {
        return getPlayerIdlTagSafe(player).getCompound(key);
    }

    public static int[] getPlayerIdeallandIntArraySafe(PlayerEntity player, String key) {
        return getPlayerIdlTagSafe(player).getIntArray(key);
    }

    public static int getPlayerIdeallandIntSafe(PlayerEntity player, String key) {
        return getPlayerIdlTagSafe(player).getInt(key);
    }
    public static float getPlayerIdeallandFloatSafe(PlayerEntity player, String key) {
        return getPlayerIdlTagSafe(player).getFloat(key);
    }
    public static double getPlayerIdeallandDoubleSafe(PlayerEntity player, String key) {
        return getPlayerIdlTagSafe(player).getDouble(key);
    }
    public static boolean getPlayerIdeallandBoolSafe(PlayerEntity player, String key) {
        return getPlayerIdlTagSafe(player).getBoolean(key);
    }
    public static String getPlayerIdeallandStrSafe(PlayerEntity player, String key) {
        return getPlayerIdlTagSafe(player).getString(key);
    }

    public static void setPlayerIdeallandTagSafe(PlayerEntity player, String key, int value) {
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = getTagSafe(playerData, PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getPlayerIdlTagSafe(player);

        idl_data.putInt(key, value);

        data.put(MCARDSTH, idl_data);
        playerData.put(PlayerEntity.PERSISTED_NBT_TAG, data);

        if (player instanceof ServerPlayerEntity)
            sync(player, key, idl_data, (byte) 2);
    }

    public static void setPlayerIdeallandTagSafe(PlayerEntity player, String key, int[] value) {
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = getTagSafe(playerData, PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getPlayerIdlTagSafe(player);

        idl_data.putIntArray(key, value);

        data.put(MCARDSTH, idl_data);
        playerData.put(PlayerEntity.PERSISTED_NBT_TAG, data);

        if (player instanceof ServerPlayerEntity)
            sync(player, key, idl_data, (byte) 5);
    }

    public static void setPlayerIdeallandTagSafe(PlayerEntity player, String key, double value) {
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = getTagSafe(playerData, PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getPlayerIdlTagSafe(player);

        idl_data.putDouble(key, value);

        data.put(MCARDSTH, idl_data);
        playerData.put(PlayerEntity.PERSISTED_NBT_TAG, data);

        if (player instanceof ServerPlayerEntity)
            sync(player, key, idl_data, (byte) 4);
    }

    public static void setPlayerIdeallandTagSafe(PlayerEntity player, String key, boolean value) {
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = getTagSafe(playerData, PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getPlayerIdlTagSafe(player);

        idl_data.putBoolean(key, value);

        data.put(MCARDSTH, idl_data);
        playerData.put(PlayerEntity.PERSISTED_NBT_TAG, data);

        if (player instanceof ServerPlayerEntity)
            sync(player, key, idl_data, (byte) 1);
    }

    public static void setPlayerIdeallandTagSafe(PlayerEntity player, String key, String value) {
        CompoundNBT playerData = player.getPersistentData();
        CompoundNBT data = getTagSafe(playerData, PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT idl_data = getPlayerIdlTagSafe(player);

        idl_data.putString(key, value);

        data.put(MCARDSTH, idl_data);
        playerData.put(PlayerEntity.PERSISTED_NBT_TAG, data);

        if (player instanceof ServerPlayerEntity)
            sync(player, key, idl_data, (byte) 3);
    }

    private static void sync(Entity entity, String key, CompoundNBT nbt, byte typeIndex)
    {
        if (entity instanceof ServerPlayerEntity)
            NetworkManager.serverSendToPlayer(new NBTPacket(key, nbt, typeIndex), (ServerPlayerEntity) entity);
    }

    public static void sync(ServerPlayerEntity player, CompoundNBT nbt)
    {
        NetworkManager.serverSendToPlayer(new NBTPacket("", nbt, (byte) 0), player);
    }

    public static void syncAll(ServerPlayerEntity player)
    {
        NetworkManager.serverSendToPlayer(new NBTPacket("", getPlayerIdlTagSafe(player), (byte) 0), player);
    }
}
