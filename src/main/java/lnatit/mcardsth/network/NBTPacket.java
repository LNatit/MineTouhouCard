package lnatit.mcardsth.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

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
                            player.getPersistentData().merge(packet.nbt);
                            break;
                        case 1:
                            player.getPersistentData().putBoolean(id, packet.nbt.getBoolean(id));
                            break;
                        case 2:
                            player.getPersistentData().putInt(id, packet.nbt.getInt(id));
                            break;
                        case 3:
                            player.getPersistentData().putString(id, packet.nbt.getString(id));
                            break;
                    }
                }
            });
        }

        contextSupplier.get().setPacketHandled(true);
    }
}
