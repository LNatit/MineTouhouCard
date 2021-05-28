package lnatit.mcardsth.capabilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;
import java.util.concurrent.Callable;

public class PlayerProperties implements INBTSerializable<CompoundNBT>
{
    public static final byte MAX_LIFE = 7;
    public static final float MAX_POWER = 4.00f;
    static Factory factory = new Factory();
    private byte life;
    private byte spell;
    private byte life_fragment;
    private byte spell_fragment;
    private float power;
    private PlayerEntity player;

    public void initProperties(PlayerEntity player)
    {
        this.life = 2;
        this.spell = 3;
        this.life_fragment = 0;
        this.spell_fragment = 0;
        if (!player.world.isRemote)
            this.player = player;
        else this.player = null;
    }

    public boolean Extend()
    {
        checkLife();
        if (this.life == MAX_LIFE)
            return false;
        else this.life++;
        return true;
    }

    public void addSpell()
    {
        this.spell++;
    }

    public void collectPower(float points)
    {
        if (points + this.power <= MAX_POWER) {
            this.power += points;
        } else {
            this.power = MAX_POWER;
        }
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
        if (this.spell == 0)
            return false;
        else this.spell--;
        return true;
    }

    public void losePower(float points)
    {
        if (points <= this.power) {
            this.power -= points;
        } else {
            this.power = 0.0f;
        }
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

    public boolean addLifeFragment()
    {
        if (this.life_fragment < 2)
        {
            this.life_fragment++;
            return true;
        } else
        {
            boolean flag = this.Extend();
            if (flag)
                this.life_fragment = 0;
            return flag;
        }
    }

    public void addSpellFragment()
    {
        if (this.spell_fragment < 2)
            this.spell_fragment++;
        else
        {
            this.addSpell();
            this.spell_fragment = 0;
        }
    }

    private void checkLife()
    {
        if (this.life > MAX_LIFE)
            this.life = MAX_LIFE;
    }

    private boolean isRemote()
    {
        return this.player == null;
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putByte("life", this.life);
        nbt.putByte("spell", this.spell);
        nbt.putByte("life_fragment", this.life_fragment);
        nbt.putByte("spell_fragment", this.spell_fragment);
        nbt.putFloat("power", this.power);

        boolean flag = (player != null);
        nbt.putBoolean("flag", flag);
        if (flag)
            nbt.putUniqueId("UUID", this.player.getUniqueID());

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        this.life = nbt.getByte("life");
        this.spell = nbt.getByte("spell");
        this.life_fragment = nbt.getByte("life_fragment");
        this.spell_fragment = nbt.getByte("spell_fragment");
        this.power = nbt.getFloat("power");

        if (nbt.getBoolean("flag"))
        {
            UUID uniqueId = nbt.getUniqueId("UUID");
        }
        /*
        TODO 逻辑未完成：
         反序列化时要考虑玩家不在线的情况
        */
    }

    public void sync()
    {
        if (this.player != null)
        {

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
