package com.lnatit.mtcc.capability;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.List;

@AutoRegisterCapability()
public interface IProgressRecorder
{
    public List<String> getRecordedProgresses();

    public boolean checkProgress(String id);

    public void grantProgress(String id);
}
