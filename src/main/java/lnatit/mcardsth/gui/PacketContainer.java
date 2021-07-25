package lnatit.mcardsth.gui;


import lnatit.mcardsth.item.AbstractCard;
import lnatit.mcardsth.item.ItemReg;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;

import java.util.function.Supplier;

import static lnatit.mcardsth.gui.PacketScreen.INVENTORY;

public class PacketContainer extends Container
{
    public final NonNullList<ItemStack> itemList = NonNullList.create();

    protected PacketContainer(PlayerEntity player)
    {
        super((ContainerType<?>) null, 0);
        for (RegistryObject<Item> itemObj : ItemReg.ITEMS.getEntries())
        {
            Item item = itemObj.get();
            if (item instanceof AbstractCard && item.getCreativeTabs() != null)
            itemList.add(new ItemStack(item));
        }
        this.InventoryInit();

        PlayerInventory playerinventory = player.inventory;

        for (int i = 0; i < 7; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new LockedSlot(INVENTORY, i * 9 + j, 9 + j * 18, 18 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
            this.addSlot(new Slot(playerinventory, k, 9 + k * 18, 156));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        return ItemStack.EMPTY;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in
     * is null for the initial slot that was double-clicked.
     */
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != INVENTORY;
    }

    /**
     * Returns true if the player can "drag-spilt" items into this slot,. returns true by default. Called to check if
     * the slot can be added to a list of Slots to split the held ItemStack across.
     */
    public boolean canDragIntoSlot(Slot slotIn)
    {
        return slotIn.inventory != INVENTORY;
    }

    private void InventoryInit()
    {
        int i = (this.itemList.size() + 9 - 1) / 9 - 5;

        for (int k = 0; k < 5; ++k)
        {
            for (int l = 0; l < 9; ++l)
            {
                int i1 = l + k * 9;
                if (i1 >= 0 && i1 < this.itemList.size())
                {
                    INVENTORY.setInventorySlotContents(l + k * 9, this.itemList.get(i1));
                }
                else
                {
                    INVENTORY.setInventorySlotContents(l + k * 9, ItemStack.EMPTY);
                }
            }
        }
    }

    static class LockedSlot extends Slot
    {
        public LockedSlot(IInventory inventoryIn, int index, int xPosition, int yPosition)
        {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return false;
        }

        @Override
        public boolean canTakeStack(PlayerEntity playerIn)
        {
            return false;
        }
    }
}
