package lnatit.mcardsth.gui;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;

import static lnatit.mcardsth.gui.ContainerTypeReg.PACKET;

public class ClientGuiUtil
{
    public static void clientInit()
    {
        ScreenManager.registerFactory((ContainerType<PacketContainer>) PACKET.get(), PacketScreen::new);
    }
}
