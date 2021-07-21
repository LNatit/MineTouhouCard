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

    public NBTPacket(@Nullable String id, CompoundNBT nbtIn)
    {
        this.nbt = nbtIn;
        this.id = id;
    }

    public static void encode(NBTPacket packet, PacketBuffer buffer)
    {
        buffer.writeCompoundTag(packet.nbt);
        buffer.writeString(packet.id);
    }

    public static NBTPacket decode(PacketBuffer buffer)
    {
        return new NBTPacket(buffer.readString(), buffer.readCompoundTag());
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
                    if (id != null)
                        player.getPersistentData().putBoolean(id, packet.nbt.getBoolean(id));
                    else player.getPersistentData().merge(packet.nbt);
                }
            });
        }

        contextSupplier.get().setPacketHandled(true);
    }
}
