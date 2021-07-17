package lnatit.mcardsth.network;

import lnatit.mcardsth.capability.PlayerPropertiesProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class NBTPacket extends IPacket
{
    private CompoundNBT nbt;

    public NBTPacket(CompoundNBT nbtIn)
    {
        this.nbt = nbtIn;
    }

    public static void encode(NBTPacket packet, PacketBuffer buffer)
    {
        buffer.writeCompoundTag(packet.nbt);
    }

    public static NBTPacket decode(PacketBuffer buffer)
    {
        return new NBTPacket(buffer.readCompoundTag());
    }

    public static void handle(NBTPacket packet, Supplier<NetworkEvent.Context> contextSupplier)
    {
        if (contextSupplier.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT))
        {
            contextSupplier.get().enqueueWork(() -> {
                ClientPlayerEntity player = Minecraft.getInstance().player;
                if (player != null)
                    player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT).ifPresent(cap -> cap.deserializeNBT(packet.nbt));
            });
        }

        contextSupplier.get().setPacketHandled(true);
    }
}
