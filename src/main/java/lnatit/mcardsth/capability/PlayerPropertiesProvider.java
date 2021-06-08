package lnatit.mcardsth.capability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerPropertiesProvider implements ICapabilitySerializable<CompoundNBT>
{
    @CapabilityInject(PlayerProperties.class)
    public static Capability<PlayerProperties> CPP_DEFAULT = null;

    static Storage storage = new Storage();

    private PlayerProperties defaultInstance = CPP_DEFAULT.getDefaultInstance();

    public PlayerPropertiesProvider(PlayerEntity player)
    {
        this.defaultInstance.initProperties();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        return cap == CPP_DEFAULT ? LazyOptional.of(() -> this.defaultInstance).cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        return defaultInstance.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        defaultInstance.deserializeNBT(nbt);
    }

    static class Storage implements Capability.IStorage<PlayerProperties>
    {

        @Nullable
        @Override
        public INBT writeNBT(Capability<PlayerProperties> capability, PlayerProperties instance, Direction side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<PlayerProperties> capability, PlayerProperties instance, Direction side, INBT nbt)
        {
            if (nbt instanceof CompoundNBT)
                instance.deserializeNBT((CompoundNBT) nbt);
        }
    }
}
