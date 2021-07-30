package lnatit.mcardsth.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static lnatit.mcardsth.network.NBTPacketUtils.*;

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
        contextSupplier.get().enqueueWork(() ->
                                          {
                                              ClientPlayerEntity player = Minecraft.getInstance().player;
                                              if (player != null)
                                              {
                                                  String id = packet.id;
                                                  switch (packet.typeIndex)
                                                  {
                                                      case 0:
                                                          mergePlayerIdeallandTangSafe(player, packet.nbt);
                                                          break;
                                                      case 1:
                                                          setPlayerIdeallandTagSafe(player, id,
                                                                                    packet.nbt.getBoolean(id)
                                                          );
                                                          break;
                                                      case 2:
                                                          setPlayerIdeallandTagSafe(player, id, packet.nbt.getInt(id));
                                                          break;
                                                      case 3:
                                                          setPlayerIdeallandTagSafe(player, id,
                                                                                    packet.nbt.getString(id)
                                                          );
                                                          break;
                                                      case 4:
                                                          setPlayerIdeallandTagSafe(player, id,
                                                                                    packet.nbt.getDouble(id)
                                                          );
                                                          break;
                                                      case 5:
                                                          setPlayerIdeallandTagSafe(player, id,
                                                                                    packet.nbt.getIntArray(id)
                                                          );
                                                          break;
                                                  }
                                              }
                                          });

        contextSupplier.get().setPacketHandled(true);
    }
}
