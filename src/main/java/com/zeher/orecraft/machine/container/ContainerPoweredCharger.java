package com.zeher.orecraft.machine.container;

import com.zeher.orecraft.core.handlers.ItemHandler;
import com.zeher.trzlib.client.slot.TRZSlotMachineUpgrade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerPoweredCharger extends Container {
	private IInventory inventory;

	private int stored;

	public ContainerPoweredCharger(InventoryPlayer invPlayer, IInventory tile) {
		this.inventory = tile;
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 144));
		}
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 86 + y * 18));
			}
		}
		addSlotToContainer(new Slot(tile, 0, 101, 47));
		
		addSlotToContainer(new Slot(tile, 1, 57, 12));
		addSlotToContainer(new Slot(tile, 2, 79, 12));
		addSlotToContainer(new Slot(tile, 3, 101, 12));
		addSlotToContainer(new Slot(tile, 4, 123, 12));
		addSlotToContainer(new Slot(tile, 5, 145, 12));
		
		addSlotToContainer(new Slot(tile, 6, 53, 47));
		addSlotToContainer(new Slot(tile, 7, 75, 47));
		addSlotToContainer(new Slot(tile, 8, 127, 47));
		addSlotToContainer(new Slot(tile, 9, 149, 47));
		
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 10, 8, 12, ItemHandler.upgrade_speed));
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 11, 8, 34, ItemHandler.upgrade_capacity));
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 12, 8, 56, ItemHandler.upgrade_charge));
		
		addSlotToContainer(new Slot(invPlayer, 39, 173, 6));
		addSlotToContainer(new Slot(invPlayer, 38, 173, 25));
		addSlotToContainer(new Slot(invPlayer, 37, 173, 44));
		addSlotToContainer(new Slot(invPlayer, 36, 173, 63));
	}

	@Override
	public void addListener(IContainerListener containerListener) {
		super.addListener(containerListener);
		containerListener.sendAllWindowProperties(this, this.inventory);
	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.listeners.size(); i++) {
			IContainerListener containerlistener = (IContainerListener) this.listeners.get(i);

			if (this.stored != this.inventory.getField(0)) {
				containerlistener.sendProgressBarUpdate(this, 0, this.inventory.getField(0));
			}
		}
		this.stored = this.inventory.getField(0);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		this.inventory.setField(id, value);
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.inventory.isUsableByPlayer(entityplayer);
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 1 && index != 0) {
				return ItemStack.EMPTY;
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}
}
