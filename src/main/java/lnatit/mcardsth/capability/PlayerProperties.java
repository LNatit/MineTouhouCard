package lnatit.mcardsth.capability;

import lnatit.mcardsth.network.NetworkManager;
import lnatit.mcardsth.network.nbtPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
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

    public void initProperties()
    {
        this.life = 2;
        this.spell = 3;
        this.lifeFragment = 0;
        this.spellFragment = 0;
        System.out.println("capability init!!!");
    }

    public boolean Extend(PlayerEntity player)
    {
        checkLife();
        if (this.life == MAX_LIFE)
            return false;
        else this.life++;
        sync(player);
        return true;
    }

    public void addSpell(PlayerEntity player)
    {
        this.spell++;
        sync(player);
    }

    public void collectPower(PlayerEntity player, float points)
    {
        if (points + this.power <= MAX_POWER) {
            this.power += points;
        } else {
            this.power = MAX_POWER;
        }
        sync(player);
    }

    public boolean canHit(PlayerEntity player)
    {
        if (this.life == 0)
            return false;
        else this.life--;
        sync(player);
        return true;
    }

    public boolean canSpell(PlayerEntity player)
    {
        if (this.spell == 0)
            return false;
        else this.spell--;
        sync(player);
        return true;
    }

    public void losePower(PlayerEntity player, float points)
    {
        if (points <= this.power) {
            this.power -= points;
        } else {
            this.power = 0.0f;
        }
        sync(player);
    }

    public byte getLife()
    {
        return this.life;
    }

    public byte getSpell()
    {
        return this.spell;
    }

    public float getPower()
    {
        return this.power;
    }

    public boolean addLifeFragment(PlayerEntity player)
    {
        if (this.lifeFragment < 2)
        {
            this.lifeFragment++;
            return true;
        } else
        {
            boolean flag = this.Extend(null);
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
    }

    public void sync(PlayerEntity playerIn)
    {
        if (playerIn instanceof ServerPlayerEntity)
            NetworkManager.serverSendToPlayer(new nbtPacket(serializeNBT()), (ServerPlayerEntity) playerIn);
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