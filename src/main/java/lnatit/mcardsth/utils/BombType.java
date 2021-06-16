package lnatit.mcardsth.utils;

import lnatit.mcardsth.capability.PlayerProperties;
import lnatit.mcardsth.capability.PlayerPropertiesProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public enum BombType
{
    DEFAULT,
    REIMU,
    MARISA,
    SAKUYA,
    SANAE;

    public static boolean playerBomb(World worldIn, PlayerEntity playerIn, BombType type)
    {
        boolean flag = checkSpell(playerIn);
        if (flag)
        {
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
