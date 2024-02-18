package com.lnatit.mtcc.command;

import com.lnatit.mtcc.capability.IProgressSupplier;
import com.lnatit.mtcc.capability.ProgressEntry;
import com.lnatit.mtcc.capability.SupplierProvider;
import com.lnatit.mtcc.item.TeaDrop;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class CommandImpl
{
    public static int imitateItem(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        if (context.getSource().isPlayer())
        {
            ServerPlayer player = context.getSource().getPlayer();
            assert player != null;
            ItemStack target = ItemArgument.getItem(context, "target").createItemStack(1, false);
            ItemStack itemstack1 = TeaDrop.imitateTo(target);
            boolean flag = player.getInventory().add(itemstack1);
            if (flag && itemstack1.isEmpty())
            {
                itemstack1.setCount(1);
                ItemEntity itementity1 = player.drop(itemstack1, false);
                if (itementity1 != null)
                {
                    itementity1.makeFakeItem();
                }

                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                         SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F,
                                         ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F
                );
                player.containerMenu.broadcastChanges();
            }
            else
            {
                ItemEntity itementity = player.drop(itemstack1, false);
                if (itementity != null)
                {
                    itementity.setNoPickUpDelay();
                    itementity.setTarget(player.getUUID());
                }
            }
        }
        return 1;
    }

    // TODO L10N
    public static int listEntries(CommandContext<CommandSourceStack> context)
    {
        Collection<ProgressEntry> collection = getSupplierFromContext(context).getAllProgresses();
        if (collection.isEmpty())
            context.getSource().sendSuccess(() -> Component.literal("no entry"), false);
        else
        {
            context.getSource()
                   .sendSuccess(() -> Component.translatable("commands.datapack.list.enabled.success",
                                                             collection.size(),
                                                             ComponentUtils.formatList(collection,
                                                                                       ProgressEntry::getChatLink
                                                             )
                   ), false);
        }
        return collection.size();
    }

    public static int addEntry(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ItemStack target = ItemArgument.getItem(context, "target").createItemStack(1, false);
        ProgressEntry entry = new ProgressEntry(target.getDescriptionId(), target);
        getSupplierFromContext(context).addEntry(entry);
        context.getSource().sendSuccess(() -> Component.literal("Added " + entry.getChatLink() + '!'), true);
        return 0;
    }

    public static int removeEntry(CommandContext<CommandSourceStack> context)
    {
        String entryName = StringArgumentType.getString(context, "entry");
        IProgressSupplier supplier = getSupplierFromContext(context);
        if (supplier.removeEntry(entryName))
        {
            context.getSource()
                   .sendSuccess(() -> Component.literal("removed entry: " + entryName), false);
            return 1;
        }
        context.getSource()
               .sendFailure(Component.literal("no entry named [" + entryName + "] found!"));
        return 0;
    }

    public static CompletableFuture<Suggestions> suggestEntryList(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder)
    {
        IProgressSupplier supplier = getSupplierFromContext(context);
        return SharedSuggestionProvider
                .suggest(supplier.getAllProgresses().stream().map(ProgressEntry::getId),
                         builder
                );
    }

    public static IProgressSupplier getSupplierFromContext(CommandContext<CommandSourceStack> context)
    {
        Level overWorld = context.getSource().getLevel().getServer().getLevel(Level.OVERWORLD);
        if (overWorld == null)
            throw new RuntimeException("overworld not exist!");
        @NotNull LazyOptional<IProgressSupplier> cap =
                overWorld.getCapability(SupplierProvider.SUPPLIER);
        if (!cap.isPresent())
            throw new RuntimeException("no capability found!");
        return cap.orElseThrow(() -> new RuntimeException("wtf?"));
    }
}
