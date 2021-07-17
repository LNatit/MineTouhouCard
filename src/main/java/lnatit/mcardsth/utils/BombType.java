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
            playerIn.addStat(Stats.ITEM_USED.get(ItemReg.ABS_BOMB.get()), 1);

            switch (type)
            {
                case DEFAULT:
                    bombDefault(worldIn, playerIn);
                    break;
                case REIMU:
                    bombReimu(worldIn, playerIn);
                    break;
                case MARISA:
                    bombMarisa(worldIn, playerIn);
                    break;
                case SAKUYA:
                    bombSakuya(worldIn, playerIn);
                    break;
                case SANAE:
                    bombSanae(worldIn, playerIn);
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

    public static void bombDefault(World worldIn, PlayerEntity playerIn)
    {
        worldIn.createExplosion(playerIn,
                playerIn.getPosX(),
                playerIn.getPosY(),
                playerIn.getPosZ(),
                60.F,
                Explosion.Mode.NONE
        );
    }

    public static void bombSStrike(World worldIn, PlayerEntity playerIn)
    {
        if (playerIn instanceof ServerPlayerEntity)
            NetworkManager.serverSendToAllPlayer(new ParticleRenderPacket((byte) 1, playerIn.getPosition()));
    }

    //TODO unfinished!!! Customize SpellCard
    public static void bombReimu(World worldIn, PlayerEntity playerIn)
    {
        bombDefault(worldIn, playerIn);
    }

    public static void bombMarisa(World worldIn, PlayerEntity playerIn)
    {
        bombDefault(worldIn, playerIn);
    }

    public static void bombSakuya(World worldIn, PlayerEntity playerIn)
    {
        bombDefault(worldIn, playerIn);
    }

    public static void bombSanae(World worldIn, PlayerEntity playerIn)
    {
        bombDefault(worldIn, playerIn);
    }
}
