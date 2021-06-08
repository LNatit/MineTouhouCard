package lnatit.mcardsth.network;

import lnatit.mcardsth.capability.PlayerPropertiesProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class nbtPacket
{
    private CompoundNBT nbt;

    public nbtPacket(CompoundNBT nbtIn)
    {
        this.nbt = nbtIn;
    }

    public static void encode(nbtPacket packet, PacketBuffer buffer)
    {
        buffer.writeCompoundTag(packet.nbt);
    }

    public static nbtPacket decode(PacketBuffer buffer)
    {
        return new nbtPacket(buffer.readCompoundTag());
    }

    /**
     * Half-finished
     */
    public static void handle(nbtPacket packet, Supplier<NetworkEvent.Context> contextSupplier)
    {
        if (contextSupplier.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT))
        {
            contextSupplier.get().enqueueWork(() -> {
                if (Minecraft.getInstance().player != null)
                    Minecraft.getInstance().player.getCapability(PlayerPropertiesProvider.CPP_DEFAULT).ifPresent(cap -> cap.deserializeNBT(packet.nbt));
            });
        }

        contextSupplier.get().setPacketHandled(true);
    }
}
