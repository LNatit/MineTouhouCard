package com.lnatit.mtcc.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecorderCapability implements IProgressRecorder, INBTSerializable<CompoundTag>
{
    List<String> progressList = new ArrayList<>();
    public static final String LIST_KEY = "prog_list_recorder";
    public static final String ENTRY_KEY = "id";

    @Override
    public List<String> getRecordedProgresses()
    {
        return this.progressList;
    }

    @Override
    public boolean checkProgress(String id)
    {
        return this.progressList.contains(id);
    }

    @Override
    public void grantProgress(String id)
    {
        this.progressList.add(id);
    }

    @Override
    public CompoundTag serializeNBT()
    {
        List<CompoundTag> tagList =
                progressList.stream()
                            .map(entry ->
                                 {
                                     CompoundTag tag = new CompoundTag();
                                     tag.putString(ENTRY_KEY, entry);
                                     return tag;
                                 })
                            .toList();
        ListTag list = new ListTag();
        list.addAll(tagList);
        CompoundTag tags = new CompoundTag();
        tags.put(LIST_KEY, list);
        return tags;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        ListTag list = nbt.getList(LIST_KEY, Tag.TAG_COMPOUND);
        this.progressList = list.stream()
                                   .map(tag -> ((CompoundTag) tag).getString(ENTRY_KEY))
                                   .collect(Collectors.toList());
    }
}
