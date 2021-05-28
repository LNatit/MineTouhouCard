package lnatit.mcardsth.capabilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

public class PlayerProperties implements INBTSerializable<CompoundNBT>
{
    public static final byte MAX_LIFE = 7;
    static Factory factory = new Factory();
    private byte life;
    private byte spell;
    private byte life_fragment;
    private byte spell_fragment;
    private PlayerEntity player;

    public void initData(PlayerEntity player)
    {
        this.life = 2;
        this.spell = 3;
        this.life_fragment = 0;
        this.spell_fragment = 0;
        if (!player.world.isRemote)
            this.player = player;
        else this.player = null;
    }

    public boolean canExtend()
    {
        checkLife();
        if (this.life == 7)
            return false;
        else this.life++;
        return true;
    }

    public void getSpell()
    {
        this.spell++;
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

    public boolean getLifeFragment()
    {
        if (this.life_fragment < 2)
        {
            this.life_fragment++;
            return true;
        } else
        {
            boolean flag = this.canExtend();
            if (flag)
                this.life_fragment = 0;
            return flag;
        }
    }

    public void getSpellFragment()
    {
        if (this.spell_fragment < 2)
            this.spell_fragment++;
        else
        {
            this.getSpell();
            this.spell_fragment = 0;
        }
    }

    private void checkLife()
    {
        if (this.life > 7)
            this.life = 7;
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

        nbt.putUniqueId("UUID", this.player.getUniqueID());

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {

    }

    public void sync()
    {

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
