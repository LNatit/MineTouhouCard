package lnatit.mcardsth.handler.capregister;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

@Deprecated
public class RegistryCapablilty
{
    private boolean doSync = true;
    private boolean doUpdateOnDeath = true;
    private boolean doUpdateOnClone = false;

    private INBTSerializable<CompoundNBT> CAPABILITY;
    
}
