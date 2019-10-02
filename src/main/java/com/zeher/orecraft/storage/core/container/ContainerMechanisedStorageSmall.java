package com.zeher.orecraft.storage.core.container;

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

public class ContainerMechanisedStorageSmall extends Container {
	private IInventory storage;
	private int power;

	public ContainerMechanisedStorageSmall(InventoryPlayer invPlayer, IInventory tile) {
		this.storage = tile;
		
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 10; x++){
				this.addSlotToContainer(new Slot(tile, x + y * 10, 8 + x * 18, 16 + y * 18));
			}
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 17 + x * 18, 120 + y * 18));
			}
		}
		
		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(invPlayer, x, 17 + x * 18, 178));
		}
	}
	
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 5 * 10) {
				if (!this.mergeItemStack(itemstack1, 5 * 10, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 5 * 10, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.storage.isUsableByPlayer(entityplayer);
	}

}
