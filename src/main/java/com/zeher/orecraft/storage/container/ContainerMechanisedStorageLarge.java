package com.zeher.orecraft.storage.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import com.zeher.orecraft.storage.tileentity.TileEntityPoweredCapacitor;

public class ContainerMechanisedStorageLarge extends Container {
	private IInventory capacitor;
	private int power;

	public ContainerMechanisedStorageLarge(InventoryPlayer invPlayer, IInventory tile) {
		this.capacitor = tile;
		
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 44 + x * 18, 214));
		}
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 44 + x * 18, 156 + y * 18));
			}
		}
		
		for(int y = 0; y < 7; y++){
			for(int x = 0; x < 13; x++){
				addSlotToContainer(new Slot(tile, x + y * 13, 8 + x * 18, 16 + y * 18));
			}
		}
	}
	
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 7 * 13)
            {
                if (!this.mergeItemStack(itemstack1, 7 * 13, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 7 * 13, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.capacitor.isUsableByPlayer(entityplayer);
	}

}
