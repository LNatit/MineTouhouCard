package lnatit.mcardsth.handler;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import lnatit.mcardsth.utils.AdvancementUtils;
import lnatit.mcardsth.utils.PlayerPropertiesUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static deeplake.idlframework.idlnbtutils.IDLNBT.*;
import static deeplake.idlframework.idlnbtutils.IDLNBTConst.COUNT;
import static deeplake.idlframework.idlnbtutils.IDLNBTConst.QUESTED;
import static lnatit.mcardsth.LogUtils.Log;
import static lnatit.mcardsth.LogUtils.Warn;
import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

//TODO fill up log info & send feedback
@Mod.EventBusSubscriber(modid = MOD_ID)
public class CommandEventHandler
{
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event)
    {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

        LiteralCommandNode<CommandSource> cardsClt =
                dispatcher
                        .register(Commands.literal("cardscollect").requires(executor -> executor.hasPermissionLevel(2))
                                          .then(Commands.literal("grantall")
                                                        .then(Commands.argument("targets", EntityArgument.players())
                                                                      .executes(executor -> executeGrantAll(
                                                                              executor.getSource(),
                                                                              EntityArgument.getEntities(executor,
                                                                                                         "targets"
                                                                              )
                                                                                )
                                                                      )
                                                        )
                                          )
                                          .then(Commands.literal("removeall")
                                                        .then(Commands.argument("targets", EntityArgument.players())
                                                                      .executes(executor -> executeRemoveAll(
                                                                              executor.getSource(),
                                                                              EntityArgument.getEntities(executor,
                                                                                                         "targets"
                                                                              )
                                                                                )
                                                                      )
                                                        )
                                          )
                                          .then(Commands.literal("query")
                                                        .then(Commands.argument("targets", EntityArgument.players())
                                                                      .executes(executor -> executeQuery(
                                                                              executor.getSource(),
                                                                              EntityArgument.getEntities(executor,
                                                                                                         "targets"
                                                                              )
                                                                                )
                                                                      )
                                                        )
                                                        .then(Commands.literal("finished")
                                                                      .executes(executor -> executeQueryFinished(
                                                                              executor.getSource(), ImmutableList.of(
                                                                                      executor.getSource().assertIsEntity()))
                                                                      )
                                                        )
                                          )
                        );
        dispatcher.register(Commands.literal("cc").redirect(cardsClt));

        LiteralCommandNode<CommandSource> initLogin =
                dispatcher
                        .register(Commands.literal("initlogin").requires(executor -> executor.hasPermissionLevel(2))
                                          .then(Commands.argument("targets", EntityArgument.players())
                                                        .executes(executor -> executeInitAll(
                                                                executor.getSource(),
                                                                EntityArgument.getEntities(executor, "targets")
                                                                  )
                                                        )
                                          )
                        );

        LiteralCommandNode<CommandSource> qstBook =
                dispatcher
                        .register(Commands.literal("questbook").requires(executor -> executor.hasPermissionLevel(0))
                                          .executes(executor -> executeQstBook(
                                                  executor.getSource(), ImmutableList.of(
                                                          executor.getSource().assertIsEntity())
                                                    )
                                          )
                                          .then(Commands.argument("targets", EntityArgument.entities())
                                                        .requires(executor -> executor.hasPermissionLevel(2))
                                                        .executes(executor -> executeQstBook(
                                                                executor.getSource(),
                                                                EntityArgument.getEntities(executor, "targets")
                                                                  )
                                                        )
                                          )
                        );
    }

    private static int executeGrantAll(CommandSource source, Collection<? extends Entity> targets)
    {
        for (Entity entity : targets)
        {
            if (entity instanceof PlayerEntity)
            {
                int i = 0;
                for (RegistryObject<Item> itemObj : ItemReg.ITEMS.getEntries())
                {
                    Item item = itemObj.get();
                    if (item instanceof AbstractCard && item.getRegistryName() != null)
                    {
                        setPlayerIdeallandTagSafe((PlayerEntity) entity, item.getRegistryName().getPath(), true);
                        AdvancementUtils.giveAdvancement((PlayerEntity) entity,
                                                         item.getRegistryName().getPath() + "_card"
                        );
                        i++;
                    }
                }
                setPlayerIdeallandTagSafe((PlayerEntity) entity, COUNT, i);
                PlayerPropertiesUtils.syncPlayerCards((PlayerEntity) entity);
            }
        }
        Log(source.getName() + " executes a cardscollection grantall command");
        return 0;
    }

    private static int executeRemoveAll(CommandSource source, Collection<? extends Entity> targets)
    {
        for (Entity entity : targets)
        {
            if (entity instanceof PlayerEntity)
            {
                for (RegistryObject<Item> itemObj : ItemReg.ITEMS.getEntries())
                {
                    Item item = itemObj.get();
                    if (item instanceof AbstractCard && item.getRegistryName() != null)
                        setPlayerIdeallandTagSafe((PlayerEntity) entity, item.getRegistryName().getPath(), false);
                }
                setPlayerIdeallandTagSafe((PlayerEntity) entity, COUNT, 0);
                PlayerPropertiesUtils.syncPlayerCards((PlayerEntity) entity);
            }
        }
        Log(source.getName() + " executes a cardscollection removeall command");
        return 0;
    }

    private static int executeQuery(CommandSource source, Collection<? extends Entity> targets)
    {
        try
        {
            ServerPlayerEntity executor = source.asPlayer();

            for (Entity entity : targets)
            {
                if (entity instanceof ServerPlayerEntity)
                {
                    ITextComponent msg = new TranslationTextComponent("mesg.minecardstouhou.queryNameCount",
                                                                      entity.getName(),
                                                                      PlayerPropertiesUtils.playerCardsTotal(
                                                                              (PlayerEntity) entity)
                    );
                    executor.sendMessage(msg, null);
                }
            }
            Log(source.getName() + " executes a cardscollection query command");
        }
        catch (CommandSyntaxException e)
        {
            Warn(source.getName() + "can't execute this command!!");
        }
        return 0;
    }

    private static int executeQueryFinished(CommandSource source, Collection<? extends Entity> targets)
    {
        try
        {
            ServerPlayerEntity executor = source.asPlayer();

            executor.sendMessage(new TranslationTextComponent("mesg.minecardstouhou.queryFinished"), null);

            for (Entity entity : targets)
            {
                if (entity instanceof ServerPlayerEntity)
                {
                    if (PlayerPropertiesUtils.playerDataCheck((PlayerEntity) entity, true))
                        Warn(entity.getName() + "'s data abnormal!!!");

                    if (PlayerPropertiesUtils.playerCardsTotal((PlayerEntity) entity) == 56)
                    {
                        ITextComponent msg = new TranslationTextComponent("commands.list.nameAndId",
                                                                          entity.getName(),
                                                                          entity.getUniqueID()
                        );
                        executor.sendMessage(msg, null);
                    }
                }
            }
            Log(source.getName() + " executes a cardscollection queryfinished command");
        }
        catch (CommandSyntaxException e)
        {
            Warn(source.getName() + "can't execute this(finished) command!!");
        }
        return 0;
    }

    private static int executeInitAll(CommandSource source, Collection<? extends Entity> targets)
    {
        for (Entity entity : targets)
        {
            if (entity instanceof PlayerEntity)
            {
                setPlayerIdeallandTagSafe((PlayerEntity) entity, QUESTED, false);
                PlayerPropertiesUtils.syncPlayerCards((PlayerEntity) entity);
            }
        }
        Log(source.getName() + " executes a cardscollection initall command");
        return 0;
    }

    private static int executeQstBook(CommandSource source, Collection<? extends Entity> targets)
    {
        for (Entity entity : targets)
        {
            if (entity instanceof PlayerEntity)
            {
                Set<Item> set = new HashSet<>();
                set.add(ItemReg.TENKYU_S_PACKET.get());

                if (!((PlayerEntity) entity).inventory.hasAny(set) && getPlayerIdeallandBoolSafe((PlayerEntity) entity,
                                                                                                 QUESTED
                ))
                    ((PlayerEntity) entity).addItemStackToInventory(new ItemStack(ItemReg.TENKYU_S_PACKET.get()));
            }
        }
        Log(source.getName() + " executes a cardscollection get questbook command");
        return 0;
    }
}
