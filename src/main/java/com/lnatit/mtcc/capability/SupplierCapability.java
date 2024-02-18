package com.lnatit.mtcc.capability;

import net.minecraft.nbt.*;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;
import java.util.stream.Collectors;

public class SupplierCapability implements IProgressSupplier, INBTSerializable<CompoundTag>
{
    private List<ProgressEntry> progressList = new ArrayList<>();
    public static final String LIST_KEY = "prog_list_supplier";
    private Map<String, ProgressEntry> progressMap;

    @Override
    public List<ProgressEntry> getAllProgresses()
    {
        return progressList;
    }

    @Override
    public ProgressEntry getEntry(String entryId)
    {
        return progressMap.get(entryId);
    }

    @Override
    public void addEntry(ProgressEntry entry)
    {
        this.progressList.add(entry);
        this.progressMap.put(entry.getId(), entry);
    }

    @Override
    public boolean removeEntry(String id)
    {
        if (this.progressMap.containsKey(id))
        {
            ProgressEntry entry = this.progressMap.get(id);
            this.progressList.remove(entry);
            this.progressMap.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public CompoundTag serializeNBT()
    {
        List<CompoundTag> tagList = progressList.stream()
                                                .map(ProgressEntry::serializeNBT)
                                                .toList();
        ListTag list = new ListTag();
        list.addAll(tagList);
        CompoundTag tags = new CompoundTag();
        tags.put(LIST_KEY, list);
        return tags;
    }

    // don't use this function to merge/update data
    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        ListTag list = nbt.getList(LIST_KEY, Tag.TAG_COMPOUND);
        this.progressList =
                list.stream()
                    .map(ProgressEntry::deserializeFromTag)
                    .collect(Collectors.toList());
        // update map info;
        this.progressMap = new HashMap<>();
        this.progressList.forEach(entry -> this.progressMap.put(entry.getId(), entry));
    }
}
