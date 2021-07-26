package lnatit.mcardsth.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientGuiUtil
{
    public static void displayGuiScreen(World world, PlayerEntity player)
    {
        if (world.isRemote)
            Minecraft.getInstance().displayGuiScreen(new PacketScreen(player));
    }
}
