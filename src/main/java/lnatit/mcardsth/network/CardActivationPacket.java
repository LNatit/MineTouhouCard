package lnatit.mcardsth.network;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CardActivationPacket extends IPacket
{
    private ItemStack itemStack;

    public CardActivationPacket(Item item)
    {
        this.itemStack = new ItemStack(item);
    }

    public static void encode(CardActivationPacket packet, PacketBuffer buffer)
    {
        buffer.writeItemStack(packet.itemStack);
    }

    public static CardActivationPacket decode(PacketBuffer buffer)
    {
        return new CardActivationPacket(buffer.readItemStack().getItem());
    }

    public static void handle(CardActivationPacket packet, Supplier<NetworkEvent.Context> contextSupplier)
    {
        if (contextSupplier.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT))
        {
            contextSupplier.get().enqueueWork(() -> {
                Minecraft.getInstance().gameRenderer.displayItemActivation(packet.itemStack);
            });
        }

        contextSupplier.get().setPacketHandled(true);
    }
}
