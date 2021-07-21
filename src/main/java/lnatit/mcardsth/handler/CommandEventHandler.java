package lnatit.mcardsth.handler;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;

import static deeplake.idlframework.idlnbtutils.IDLNBTConst.COUNT;
import static deeplake.idlframework.idlnbtutils.IDLNBTUtils.SetInt;
import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class CommandEventHandler
{
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event)
    {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        LiteralCommandNode<CommandSource> cmd =
                dispatcher
                .register(Commands.literal("cardscollect").requires(executor -> executor.hasPermissionLevel(2))
                        .then(Commands.literal("grantall")
                                      .then(Commands.argument("target", EntityArgument.entities())
                                                    .executes(executor -> executeGrantAll(executor.getSource(), ImmutableList.of(executor.getSource().assertIsEntity())))
                                           )
                             )
                        .then(Commands.literal("removeall")
                                      .then(Commands.argument("target", EntityArgument.entities())
                                                    .executes(executor -> executeRemoveAll(executor.getSource(), ImmutableList.of(executor.getSource().assertIsEntity())))
                                           )
                             )
                         );
        dispatcher.register(Commands.literal("bs").redirect(cmd));
    }

    private static int executeGrantAll(CommandSource source, Collection<? extends Entity> targets)
    {
        for (Entity entity: targets)
        {
            if (entity instanceof PlayerEntity)
            {
                int i = 0;
                for (RegistryObject<Item> itemObj : ItemReg.ITEMS.getEntries())
                {
                    Item item = itemObj.get();
                    if (item instanceof AbstractCard && item.getRegistryName() != null)
                    {
                        entity.getPersistentData().putBoolean(item.getRegistryName().getNamespace(), true);
                        i++;
                    }
                }
                SetInt(entity, COUNT, i);
            }
        }
        return 0;
    }

    private static int executeRemoveAll(CommandSource source, Collection<? extends Entity> targets)
    {
        for (Entity entity: targets)
        {
            if (entity instanceof PlayerEntity)
            {
                for (RegistryObject<Item> itemObj : ItemReg.ITEMS.getEntries())
                {
                    Item item = itemObj.get();
                    if (item instanceof AbstractCard && item.getRegistryName() != null)
                        entity.getPersistentData().putBoolean(item.getRegistryName().getNamespace(), false);
                }
                SetInt(entity, COUNT, 0);
            }
        }
        return 0;
    }
}
