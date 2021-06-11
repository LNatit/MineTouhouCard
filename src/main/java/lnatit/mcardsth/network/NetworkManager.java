package lnatit.mcardsth.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

public class NetworkManager
{
    private static final String PROTOCOL_VERSION = "1.0.0";

    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPacket()
    {
        INSTANCE.registerMessage(0, nbtPacket.class, nbtPacket::encode, nbtPacket::decode, nbtPacket::handle);
    }

    public static void playerSendToServer(nbtPacket packet)
    {
        INSTANCE.sendToServer(packet);
    }

    public static void serverSendToPlayer(nbtPacket packet, ServerPlayerEntity player)
    {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void serverSendToAllPlayer(nbtPacket packet)
    {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }
}
