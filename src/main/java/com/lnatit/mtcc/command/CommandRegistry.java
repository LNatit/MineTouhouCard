package com.lnatit.mtcc.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.lnatit.mtcc.MTCC.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandRegistry
{
    @SubscribeEvent()
    public static void onCommandRegister(RegisterCommandsEvent event)
    {
        CommandBuildContext pContext = event.getBuildContext();
        event.getDispatcher().register(
                Commands.literal(MOD_ID)
                        .requires(executor -> executor.hasPermission(2))
                        .then(Commands.literal("imitate")
                                      .then(Commands.argument("target", ItemArgument.item(pContext))
                                                    .executes(CommandImpl::imitateItem)
                                      )
                        )
                        .then(Commands.literal("entry")
                                      .then(Commands.literal("list")
                                                    .executes(CommandImpl::listEntries)
                                      )
                                      .then(Commands.literal("add")
                                                    .then(Commands.argument("target", ItemArgument.item(pContext))
                                                                  .executes(CommandImpl::addEntry)
                                                    )
                                      )
                                      .then(Commands.literal("remove")
                                                    .then(Commands.argument("entry", StringArgumentType.string())
                                                                  .suggests(CommandImpl::suggestEntryList)
                                                                  .executes(CommandImpl::removeEntry)
                                                    )
                                      )
                        )
        );
    }
}
