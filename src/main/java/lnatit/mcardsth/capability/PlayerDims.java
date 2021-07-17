package lnatit.mcardsth.capability;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.concurrent.Callable;

public class PlayerDims implements INBTSerializable<CompoundNBT>
{
    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();

        

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {

    }

    public void sync(ServerPlayerEntity playerIn)
    {

    }

    static class Factory implements Callable<PlayerDims>
    {

        @Override
        public PlayerDims call() throws Exception
        {
            return new PlayerDims();
        }
    }
}
