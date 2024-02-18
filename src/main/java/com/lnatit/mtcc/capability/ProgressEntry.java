package com.lnatit.mtcc.capability;

import com.lnatit.mtcc.item.TeaDrop;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;

public class ProgressEntry implements INBTSerializable<CompoundTag>
{
    // progress.mtcc.${imitateAs.namespace}.${id}.name
    // progress.mtcc.${imitateAs.namespace}.${id}.desc
    private String id;
    public static final String ID_KEY = "prog_id";
    private int triggerFlag;
    public static final String TYPE_KEY = "prog_trigger";
    private TeaDrop.DropTier tier;
    public static final String TIER_KEY = "prog_tier";
    private Item imitateItem;
    public static final String IMITATE_KEY = "prog_icon";
    private final CompoundTag itemTag = new CompoundTag();
    public static final String TAG_KEY = "with_tag";

    public ProgressEntry(String id, int triggerFlag, TeaDrop.DropTier tier, ItemStack target)
    {
        this.id = id;
        this.triggerFlag = triggerFlag;
        this.tier = tier;
        this.imitateItem = target.getItem();
        this.itemTag.merge(target.getOrCreateTag());
    }

    public ProgressEntry(String id, ItemStack target)
    {
        this(id, 0, TeaDrop.DropTier.COMMON, target);
    }

    private ProgressEntry()
    {
    }

    public ItemStack toItemStack()
    {
        ItemStack target = new ItemStack(this.imitateItem, 1);
        target.setTag(itemTag);
        return TeaDrop.imitateTo(target);
    }

    @Override
    public CompoundTag serializeNBT()
    {
        CompoundTag tag = new CompoundTag();
        tag.putString(ID_KEY, this.id);
        tag.putInt(TYPE_KEY, this.triggerFlag);
        tag.putInt(TIER_KEY, tier.ordinal());
        ResourceLocation rl = ForgeRegistries.ITEMS.getKey(this.imitateItem);
        tag.putString(IMITATE_KEY, rl != null ? rl.toString() : "minecraft:air");
        tag.put(TAG_KEY, this.itemTag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        this.id = nbt.getString(ID_KEY);
        this.triggerFlag = nbt.getInt(TYPE_KEY);
        this.tier = TeaDrop.DropTier.from(nbt.getInt(TIER_KEY));
        ResourceLocation rl = new ResourceLocation(nbt.getString(IMITATE_KEY));
        this.imitateItem = ForgeRegistries.ITEMS.getValue(rl);
        this.itemTag.merge(nbt.getCompound(TAG_KEY));
    }

    public String getId()
    {
        return id;
    }

    public Component getChatLink()
    {
        return this.imitateItem.getDescription();
    }
    public static ProgressEntry deserializeFromTag(Tag nbt)
    {
        ProgressEntry entry = new ProgressEntry();
        entry.deserializeNBT((CompoundTag) nbt);
        return entry;
    }

    public enum TriggerType
    {
        INTERACT("interact_with"),
        GET_ITEM("get_item"),
        ADVANCEMENT("advancement"),
        VISIT_DIM("visit_dimension"),
        UNION("complete_on"),
        MEET("meet_with"),
        VIRAL("viral_from");

        private final int mask = 1 << this.ordinal();
        private final String key;

        TriggerType(String key)
        {
            this.key = key;
        }

        public int getMask()
        {
            return mask;
        }

        public int flagOf(TriggerType... types)
        {
            int flag = 0;
            for (TriggerType type : types)
                flag |= type.getMask();
            return flag;
        }
    }
}
