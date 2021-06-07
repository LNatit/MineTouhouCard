package lnatit.mcardsth.network;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SharedSeedRandom;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Random;
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
     * TODO unfinished
     */
    public static void handle(nbtPacket packet, Supplier<NetworkEvent.Context> contextSupplier)
    {
        contextSupplier.get().enqueueWork(() -> {

        });

        contextSupplier.get().setPacketHandled(true);
    }
}
