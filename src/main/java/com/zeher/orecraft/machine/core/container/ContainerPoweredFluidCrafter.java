package com.zeher.orecraft.machine.core.container;

import com.zeher.orecraft.core.handler.ItemHandler;
import com.zeher.zeherlib.client.slot.SlotBattery;
import com.zeher.zeherlib.client.slot.SlotBucket;
import com.zeher.zeherlib.client.slot.SlotRestrictedAccess;
import com.zeher.zeherlib.client.slot.SlotUpgrade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerPoweredFluidCrafter extends Container {
	
	private IInventory inventory;
	private int processTime;
	private int totalCookTime;
	private int processBurnTime;
	private int currentItemBurnTime;

	private int power;

	public ContainerPoweredFluidCrafter(InventoryPlayer invPlayer, IInventory tile) {
		this.inventory = tile;
		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 154));
		}
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 96 + y * 18));
			}
		}
		
		this.addSlotToContainer(new Slot(tile, 0, 68, 39));
		this.addSlotToContainer(new SlotBattery(tile, 1, 33, 61));
		
		this.addSlotToContainer(new SlotUpgrade(tile, 3, 11, 17, ItemHandler.upgrade_speed));
		this.addSlotToContainer(new SlotUpgrade(tile, 4, 11, 39, ItemHandler.upgrade_capacity));
		this.addSlotToContainer(new SlotUpgrade(tile, 5, 11, 61, ItemHandler.upgrade_charge));
		
		this.addSlotToContainer(new SlotUpgrade(tile, 6, 148, 17, ItemHandler.upgrade_fluid_speed));
		this.addSlotToContainer(new SlotUpgrade(tile, 7, 148, 39, ItemHandler.upgrade_fluid_capacity));
		this.addSlotToContainer(new SlotUpgrade(tile, 8, 148, 61, ItemHandler.upgrade_fluid_efficiency));
		
		this.addSlotToContainer(new SlotBucket(tile, 9, 104, 17));
		this.addSlotToContainer(new SlotRestrictedAccess(tile, 10, 104, 61, 16));
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

			if (this.power != this.inventory.getField(4)) {
				containerlistener.sendWindowProperty(this, 4, this.inventory.getField(4));
			}
			if (this.processTime != this.inventory.getField(2)) {
				containerlistener.sendWindowProperty(this, 2, this.inventory.getField(2));
			}

			if (this.processBurnTime != this.inventory.getField(0)) {
				containerlistener.sendWindowProperty(this, 0, this.inventory.getField(0));
			}

			if (this.currentItemBurnTime != this.inventory.getField(1)) {
				containerlistener.sendWindowProperty(this, 1, this.inventory.getField(1));
			}

			if (this.totalCookTime != this.inventory.getField(3)) {
				containerlistener.sendWindowProperty(this, 3, this.inventory.getField(3));
			}
		}
		this.power = this.inventory.getField(0);
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
				if (index >= 3 && index < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
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
