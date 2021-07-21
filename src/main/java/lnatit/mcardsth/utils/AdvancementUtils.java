package lnatit.mcardsth.utils;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

public class AdvancementUtils
{
    public static boolean giveAdvancement(PlayerEntity player, String id)
    {
        if (player.world.isRemote)
        {
            return false;
        }

        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
        Advancement advancement = findAdvancement(player.getServer(), id);
        if (advancement == null)
        {
            return false;
        }

        return giveAdvancement(player, advancement);
    }

    public static boolean giveAdvancement(PlayerEntity player, Advancement advancement)
    {
        if (player.world.isRemote)
        {
            return false;
        }

        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
        if (advancement == null)
        {
            return false;
        }
        AdvancementProgress advancementprogress = serverPlayerEntity.getAdvancements().getProgress(advancement);
        if (advancementprogress.isDone()) {
            return false;
        } else {
            for(String criteria : advancementprogress.getRemaningCriteria()) {
                serverPlayerEntity.getAdvancements().grantCriterion(advancement, criteria);
            }

            return true;
        }
    }

    public static boolean hasAdvancement(ServerPlayerEntity player, String id)
    {
        Advancement advancement = findAdvancement(player.server, id);
        if (advancement == null)
        {
            return false;
        }
        else {
            return hasAdvancement(player, advancement);
        }
    }
    public static boolean hasAdvancement(ServerPlayerEntity player, Advancement advancement)
    {
        AdvancementProgress advancementprogress = player.getAdvancements().getProgress(advancement);
        return advancementprogress.isDone();
    }

    public static Advancement findAdvancement(MinecraftServer server, String id)
    {
        Advancement advancement = server.getAdvancementManager().getAdvancement(new ResourceLocation(MOD_ID, id));
        if (advancement == null)
        {
            advancement = server.getAdvancementManager().getAdvancement(new ResourceLocation(id));//Also try vanilla
        }

        if (advancement == null)
        {
            return null;
        }
        else
        {
            return advancement;
        }
    }
}
