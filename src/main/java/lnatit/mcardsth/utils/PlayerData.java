package lnatit.mcardsth.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import static deeplake.idlframework.idlnbtutils.IDLNBT.*;

public class PlayerData
{
    public static final String LIFE_COUNT = "life_c";
    public static final String LIFE_FRAGMENT = "life_f";
    public static final String BOMB_COUNT = "bomb_c";
    public static final String BOMB_FRAGMENT = "bomb_f";

    //TODO transfer to config
    public static final PlayerData DEFAULT = new PlayerData((byte) 0, (byte) 0, (byte) 0, (byte) 0);

    public static final byte MAX_LIFE = 7;

    private byte life;
    private byte bomb;
    private byte lifeFragment;
    private byte bombFragment;

    public PlayerData(PlayerEntity player)
    {
        CompoundNBT nbt = getPlayerIdlTagSafe(player);

        this.life = nbt.getByte(LIFE_COUNT);
        this.lifeFragment = nbt.getByte(LIFE_FRAGMENT);
        this.bomb = nbt.getByte(BOMB_COUNT);
        this.bombFragment = nbt.getByte(BOMB_FRAGMENT);
    }

    private PlayerData(byte life, byte bomb, byte lifeFragment, byte bombFragment)
    {
        this.life = life;
        this.bomb = bomb;
        this.lifeFragment = lifeFragment;
        this.bombFragment = bombFragment;
    }
//
//    public void init()
//    {
//        this.life = 2;
//        this.bomb = 3;
//        this.lifeFragment = 0;
//        this.bombFragment = 0;
//    }

    public boolean Extend()
    {
        checkLife();
        if (this.life == MAX_LIFE)
            return false;
        else this.life++;
        return true;
    }

    public void addBomb()
    {
        this.bomb++;
    }

    public boolean canHit()
    {
        if (this.life == 0)
            return false;
        else this.life--;
        return true;
    }

    public boolean canSpell()
    {
        if (this.bomb == 0)
            return false;
        else this.bomb--;
        return true;
    }

    public byte getLife()
    {
        return this.life;
    }

    public byte getLifeFragment()
    {
        return this.lifeFragment;
    }

    public byte getBomb()
    {
        return this.bomb;
    }

    public byte getBombFragment()
    {
        return this.bombFragment;
    }

    public boolean addLifeFragment()
    {
        if (this.lifeFragment < 2)
        {
            this.lifeFragment++;
            return true;
        }
        else
        {
            boolean flag = this.Extend();
            if (flag)
                this.lifeFragment = 0;
            return flag;
        }
    }

    public void addBombFragment()
    {
        if (this.bombFragment < 2)
            this.bombFragment++;
        else
        {
            this.addBomb();
            this.bombFragment = 0;
        }
    }

    private void checkLife()
    {
        if (this.life > MAX_LIFE)
            this.life = MAX_LIFE;
    }

    /**
     * Call this method to apply changes
     *
     * @param player target player
     */
    public void ApplyAndSync(PlayerEntity player)
    {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putByte(LIFE_COUNT, this.life);
        nbt.putByte(LIFE_FRAGMENT, this.lifeFragment);
        nbt.putByte(BOMB_COUNT, this.bomb);
        nbt.putByte(BOMB_FRAGMENT, this.bombFragment);

        getPlayerIdlTagSafe(player).merge(nbt);
        if (player instanceof ServerPlayerEntity)
            sync((ServerPlayerEntity) player, nbt);
    }
}
