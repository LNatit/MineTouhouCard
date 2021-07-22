package lnatit.mcardsth.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static deeplake.idlframework.idlnbtutils.IDLNBT.*;
import static deeplake.idlframework.idlnbtutils.IDLNBTConst.MCARDSTH;

public class NBTPacket extends IPacket
{
    private CompoundNBT nbt;
    private String id;
    private byte typeIndex;

    public NBTPacket(String id, CompoundNBT nbtIn, byte typeIndex)
    {
        this.nbt = nbtIn;
        this.id = id;
        this.typeIndex = typeIndex;
    }

    public static void encode(NBTPacket packet, PacketBuffer buffer)
    {
        buffer.writeString(packet.id);
        buffer.writeCompoundTag(packet.nbt);
        buffer.writeByte(packet.typeIndex);
    }

    public static NBTPacket decode(PacketBuffer buffer)
    {
        return new NBTPacket(buffer.readString(), buffer.readCompoundTag(), buffer.readByte());
    }

    public static void handle(NBTPacket packet, Supplier<NetworkEvent.Context> contextSupplier)
    {
        if (contextSupplier.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT))
        {
            contextSupplier.get().enqueueWork(() -> {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                if (player != null)
                {
                    String id = packet.id;
                    switch (packet.typeIndex)
                    {
                        case 0:
                            CompoundNBT nbt = player.getPersistentData();
                            CompoundNBT data = getTagSafe(nbt, PlayerEntity.PERSISTED_NBT_TAG);
                            CompoundNBT idl_data = getPlayerIdlTagSafe(player);

                            idl_data.merge(packet.nbt);

                            data.put(MCARDSTH, idl_data);
                            nbt.put(PlayerEntity.PERSISTED_NBT_TAG, data);
                            break;
                        case 1:
                            setPlayerIdeallandTagSafe(player, id, packet.nbt.getBoolean(id));
                            break;
                        case 2:
                            setPlayerIdeallandTagSafe(player, id, packet.nbt.getInt(id));
                            break;
                        case 3:
                            setPlayerIdeallandTagSafe(player, id, packet.nbt.getString(id));
                            break;
                        case 4:
                            setPlayerIdeallandTagSafe(player, id, packet.nbt.getDouble(id));
                            break;
                        case 5:
                            setPlayerIdeallandTagSafe(player, id, packet.nbt.getIntArray(id));
                            break;
                    }
                }
            });
        }

        contextSupplier.get().setPacketHandled(true);
    }
}
