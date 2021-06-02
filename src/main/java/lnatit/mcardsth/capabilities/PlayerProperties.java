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
    private byte lifeFragment;
    private byte spellFragment;
    private float power;
    private PlayerEntity player;

    public void initProperties(PlayerEntity player)
    {
        this.life = 2;
        this.spell = 3;
        this.lifeFragment = 0;
        this.spellFragment = 0;
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
        if (this.lifeFragment < 2)
        {
            this.lifeFragment++;
            return true;
        } else
        {
            boolean flag = this.Extend();
            if (flag)
                this.lifeFragment = 0;
            return flag;
        }
    }

    public void addSpellFragment()
    {
        if (this.spellFragment < 2)
            this.spellFragment++;
        else
        {
            this.addSpell();
            this.spellFragment = 0;
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

        nbt.putByte("Life", this.life);
        nbt.putByte("Spell", this.spell);
        nbt.putByte("LifeFragment", this.lifeFragment);
        nbt.putByte("SpellFragment", this.spellFragment);
        nbt.putFloat("Power", this.power);

        if (player != null)
            nbt.putUniqueId("UUID", this.player.getUniqueID());

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

        if (nbt.contains("UUID"))
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
