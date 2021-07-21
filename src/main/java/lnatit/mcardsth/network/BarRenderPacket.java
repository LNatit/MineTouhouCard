package lnatit.mcardsth.network;

import lnatit.mcardsth.utils.LifeRenderer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

@Deprecated
public class BarRenderPacket extends IPacket
{
    private byte index;     //  1: life, 2: bomb, 3: power, 4: money, 5: all

    public BarRenderPacket(byte index)
    {
        this.index = index;
    }

    public static void encode(BarRenderPacket packet, PacketBuffer buffer)
    {
        buffer.writeByte(packet.index);
    }

    public static BarRenderPacket decode(PacketBuffer buffer)
    {
        return new BarRenderPacket(buffer.readByte());
    }

    public static void handle(BarRenderPacket packet, Supplier<NetworkEvent.Context> contextSupplier)
    {
        if (contextSupplier.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT))
        {
            contextSupplier.get().enqueueWork(() -> {
                switch (packet.index)
                {
                    case 1:
                        LifeRenderer.initRendererUpdater();
                        break;
                    case 2:
                        //TODO
                        break;
                    case 3:
                        //TODO
                        break;
                    case 4:
                        //TODO
                        break;
                    case 5:
                        LifeRenderer.initRendererUpdater();
                        //TODO
                        break;
                }
            });
        }

        contextSupplier.get().setPacketHandled(true);
    }
}
