package com.lnatit.mtcc.capability;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.List;

@AutoRegisterCapability
public interface IProgressSupplier
{
    public List<ProgressEntry> getAllProgresses();

    public ProgressEntry getEntry(String entryId);

    public void addEntry(ProgressEntry entry);

    public boolean removeEntry(String id);
}
