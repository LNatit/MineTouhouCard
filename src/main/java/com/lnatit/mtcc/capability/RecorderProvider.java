package com.lnatit.mtcc.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RecorderProvider extends CapabilityProvider<RecorderProvider> implements ICapabilitySerializable<CompoundTag>
{
    public static Capability<IProgressRecorder> RECORDER = CapabilityManager.get(new CapabilityToken<>()
    {
    });
    LazyOptional<RecorderCapability> handler = LazyOptional.of(RecorderCapability::new);

    public RecorderProvider()
    {
        super(RecorderProvider.class);
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
        return RECORDER.orEmpty(cap, handler.cast());
    }
}
