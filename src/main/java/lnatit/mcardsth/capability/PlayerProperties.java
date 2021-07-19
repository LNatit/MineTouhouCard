package lnatit.mcardsth.capability;

import lnatit.mcardsth.network.BarRenderPacket;
import lnatit.mcardsth.network.NetworkManager;
import lnatit.mcardsth.network.NBTPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.concurrent.Callable;

public class PlayerProperties implements INBTSerializable<CompoundNBT>
{
    public static final byte MAX_LIFE = 7;
    public static final float MAX_POWER = 4.00f;
    static Factory factory = new Factory();

    private byte life;
    private byte spell;
    private byte lifeFragment;
    private byte spellFragment;
    private float power;
    private float money;

    public void initProperties()
    {
        this.life = 2;
        this.spell = 3;
        this.lifeFragment = 0;
        this.spellFragment = 0;
        this.power = 1.0F;
        this.money = 0F;
    }

    public boolean Extend(PlayerEntity player)
    {
        checkLife();
        if (this.life == MAX_LIFE)
            return false;
        else this.life++;
        sync(player, (byte) 1);
        return true;
    }

    public void addSpell(PlayerEntity player)
    {
        this.spell++;
        sync(player, (byte) 2);
    }

    public void collectPower(PlayerEntity player, float points)
    {
        if (points + this.power <= MAX_POWER) {
            this.power += points;
        } else {
            this.power = MAX_POWER;
        }
        sync(player, (byte) 3);
    }

    public void collectMoney(PlayerEntity player, float points)
    {
        this.money += points;
        sync(player, (byte) 4);
    }

    public boolean canHit(PlayerEntity player)
    {
        if (this.life == 0)
            return false;
        else this.life--;
        sync(player, (byte) 1);
        return true;
    }

    public boolean canSpell(PlayerEntity player)
    {
        if (this.spell == 0)
            return false;
        else this.spell--;
        sync(player, (byte) 2);
        return true;
    }

    public void losePower(PlayerEntity player, float points)
    {
        if (points <= this.power - 1)
            this.power -= points;
        else this.power = 1.0f;
        sync(player, (byte) 3);
    }

    //TODO transfer power to money!!!
    public boolean canPay(PlayerEntity player, float points)
    {
        if (points > this.money)
            return false;
        else this.money -= points;
        sync(player, (byte) 0);
        return true;
    }

    public void loseMoney(PlayerEntity player, float points)
    {
        if (points <= this.money)
            this.money -= points;
        else this.money = 0.0f;
        sync(player, (byte) 4);
    }

    public byte getLife()
    {
        return this.life;
    }

    public byte getLifeFragment()
    {
        return this.lifeFragment;
    }

    public byte getSpell()
    {
        return this.spell;
    }

    public byte getSpellFragment()
    {
        return this.spellFragment;
    }

    public float getPower()
    {
        return this.power;
    }

    public float getMoney()
    {
        return this.money;
    }

    public boolean addLifeFragment(PlayerEntity player)
    {
        if (this.lifeFragment < 2)
        {
            this.lifeFragment++;
            return true;
        } else
        {
            boolean flag = this.Extend(player);
            if (flag)
                this.lifeFragment = 0;
            return flag;
        }
    }

    public void addSpellFragment(PlayerEntity player)
    {
        if (this.spellFragment < 2)
            this.spellFragment++;
        else
        {
            this.addSpell(player);
            this.spellFragment = 0;
        }
    }

    private void checkLife()
    {
        if (this.life > MAX_LIFE)
            this.life = MAX_LIFE;
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putByte("Life", this.life);
        nbt.putByte("Spell", this.spell);
        nbt.putByte("LifeFragment", this.lifeFragment);
        nbt.putByte("SpellFragment", this.spellFragment);
        nbt.putFloat("Power", this.power);
        nbt.putFloat("Money", this.money);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        this.life = nbt.getByte("Life");
        this.spell = nbt.getByte("Spell");
        this.lifeFragment = nbt.getByte("LifeFragment");
        this.spellFragment = nbt.getByte("SpellFragment");
        this.power = nbt.getFloat("Power");
        this.money = nbt.getFloat("Money");
    }

    public void sync(PlayerEntity playerIn, byte index)
    {
        if (playerIn instanceof ServerPlayerEntity)
        {
            NetworkManager.serverSendToPlayer(new NBTPacket(serializeNBT()), (ServerPlayerEntity) playerIn);
            NetworkManager.serverSendToPlayer(new BarRenderPacket(index), (ServerPlayerEntity) playerIn);
        }
    }

    static class Factory implements Callable<PlayerProperties>
    {
        @Override
        public PlayerProperties call()
        {
            return new PlayerProperties();
        }
    }
}
