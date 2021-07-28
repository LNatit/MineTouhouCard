package lnatit.mcardsth.utils;

import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import lnatit.mcardsth.network.NetworkManager;
import lnatit.mcardsth.network.ParticleRenderPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public enum BombType
{
    DEFAULT,
    S_STRIKE;

    public static boolean playerBomb(World worldIn, PlayerEntity playerIn, BombType bombType)
    {
        if (bombType == S_STRIKE)
        {
            bombSStrike(worldIn, playerIn);
            return true;
        }
        else
        {
            boolean flag = checkSpell(playerIn);
            if (flag)
            {
                boolean enhance = PlayerPropertiesUtils.doPlayerCollected(playerIn, (AbstractCard) ItemReg.MAGICSCROLL.get());
                bombDefault(worldIn, playerIn, enhance);
                playerIn.addStat(Stats.ITEM_USED.get(ItemReg.ABS_SPELL.get()));
            }
            return flag;
        }
    }

    private static boolean checkSpell(PlayerEntity playerIn)
    {
        PlayerData data = new PlayerData(playerIn);

        boolean flag = data.canSpell();

        if (flag)
            data.ApplyAndSync(playerIn);

        return flag;
    }

    private static void bombSStrike(World worldIn, PlayerEntity playerIn)
    {
        if (playerIn instanceof ServerPlayerEntity)
            NetworkManager.serverSendToAllPlayer(new ParticleRenderPacket((byte) 1, playerIn.getPosition()));
    }

    private static void bombDefault(World worldIn, PlayerEntity playerIn, boolean enhance)
    {
        worldIn.createExplosion(playerIn,
                                playerIn.getPosX(),
                                playerIn.getPosY(),
                                playerIn.getPosZ(),
                                enhance ? 8.F : 16.F,
                                Explosion.Mode.NONE
                               );
    }
}
