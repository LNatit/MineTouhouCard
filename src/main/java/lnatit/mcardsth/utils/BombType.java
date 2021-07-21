package lnatit.mcardsth.utils;

import lnatit.mcardsth.capability.PlayerProperties;
import lnatit.mcardsth.capability.PlayerPropertiesProvider;
import lnatit.mcardsth.item.ItemReg;
import lnatit.mcardsth.network.NetworkManager;
import lnatit.mcardsth.network.ParticleRenderPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

//TODO simplify the contents
public enum BombType
{
    DEFAULT,
    S_STRIKE,
    REIMU,
    MARISA,
    SAKUYA,
    SANAE;

    public static boolean playerBomb(World worldIn, PlayerEntity playerIn, BombType type)
    {
        if (type == S_STRIKE)
        {
            bombSStrike(worldIn, playerIn);
            return true;
        }
        boolean flag = checkSpell(playerIn);
        if (flag)
        {
            boolean enhance = AbilityCardUtils.doPlayerHold(playerIn, ItemReg.MAGICSCROLL.get());
            playerIn.addStat(Stats.ITEM_USED.get(ItemReg.ABS_BOMB.get()), 1);

            switch (type)
            {
                case DEFAULT:
                    bombDefault(worldIn, playerIn, enhance);
                    break;
                case REIMU:
                    bombReimu(worldIn, playerIn, enhance);
                    break;
                case MARISA:
                    bombMarisa(worldIn, playerIn, enhance);
                    break;
                case SAKUYA:
                    bombSakuya(worldIn, playerIn, enhance);
                    break;
                case SANAE:
                    bombSanae(worldIn, playerIn, enhance);
                    break;
            }
        }

        return flag;
    }

    public static boolean checkSpell(PlayerEntity playerIn)
    {
        LazyOptional<PlayerProperties> cap = playerIn.getCapability(PlayerPropertiesProvider.CPP_DEFAULT);
        PlayerProperties playerProperties = cap.orElse(null);

        return playerProperties.canSpell(playerIn);
    }

    public static void bombDefault(World worldIn, PlayerEntity playerIn, boolean enhance)
    {
        worldIn.createExplosion(playerIn,
                playerIn.getPosX(),
                playerIn.getPosY(),
                playerIn.getPosZ(),
                enhance ? 60.F : 80.F,
                Explosion.Mode.NONE
        );
    }

    public static void bombSStrike(World worldIn, PlayerEntity playerIn)
    {
        if (playerIn instanceof ServerPlayerEntity)
            NetworkManager.serverSendToAllPlayer(new ParticleRenderPacket((byte) 1, playerIn.getPosition()));
    }

    //TODO unfinished!!! Customize SpellCard
    public static void bombReimu(World worldIn, PlayerEntity playerIn, boolean enhance)
    {
        bombDefault(worldIn, playerIn, enhance);
    }

    public static void bombMarisa(World worldIn, PlayerEntity playerIn, boolean enhance)
    {
        bombDefault(worldIn, playerIn, enhance);
    }

    public static void bombSakuya(World worldIn, PlayerEntity playerIn, boolean enhance)
    {
        bombDefault(worldIn, playerIn, enhance);
    }

    public static void bombSanae(World worldIn, PlayerEntity playerIn, boolean enhance)
    {
        bombDefault(worldIn, playerIn, enhance);
    }
}
