package lnatit.mcardsth.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.LinkedList;
import java.util.List;

import static lnatit.mcardsth.capability.PlayerPropertiesProvider.CPP_DEFAULT;

@Deprecated
public class CapabilityMap
{
    public static final List<Capability<? extends INBTSerializable<CompoundNBT>>> CAPABILITY_LIST = new LinkedList<>();
    public static boolean doExist = false;

    public static void init()
    {
        CAPABILITY_LIST.add(CPP_DEFAULT);
    }


}
