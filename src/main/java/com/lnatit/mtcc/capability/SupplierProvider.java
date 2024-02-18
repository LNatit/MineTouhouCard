package com.lnatit.mtcc.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SupplierProvider extends CapabilityProvider<SupplierProvider> implements ICapabilitySerializable<CompoundTag>
{
    public static Capability<IProgressSupplier> SUPPLIER = CapabilityManager.get(new CapabilityToken<>()
    {
    });
    LazyOptional<SupplierCapability> handler = LazyOptional.of(SupplierCapability::new);

    protected SupplierProvider()
    {
        super(SupplierProvider.class);
    }

    @Override
    public CompoundTag serializeNBT()
    {
        return handler.resolve().get().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        handler.ifPresent(cap -> cap.deserializeNBT(nbt));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        return SUPPLIER.orEmpty(cap, handler.cast());
    }
}
