package com.zeher.orecraft.machine.core.container;

import com.zeher.orecraft.core.handler.ItemHandler;
import com.zeher.zeherlib.client.slot.SlotBattery;
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

public class ContainerPoweredCharger extends Container {
	private IInventory inventory;

	private int stored;

	public ContainerPoweredCharger(InventoryPlayer invPlayer, IInventory tile) {
		this.inventory = tile;
		
		this.addSlotToContainer(new SlotBattery(tile, 0, 56, 61));
		
		this.addSlotToContainer(new SlotBattery(tile, 1, 82, 17));
		this.addSlotToContainer(new SlotBattery(tile, 2, 104, 17));
		this.addSlotToContainer(new SlotBattery(tile, 3, 126, 17));
		
		this.addSlotToContainer(new SlotBattery(tile, 4, 82, 39));
		this.addSlotToContainer(new SlotBattery(tile, 5, 104, 39));
		this.addSlotToContainer(new SlotBattery(tile, 6, 126, 39));
		
		this.addSlotToContainer(new SlotBattery(tile, 7, 82, 61));
		this.addSlotToContainer(new SlotBattery(tile, 8, 104, 61));
		this.addSlotToContainer(new SlotBattery(tile, 9, 126, 61));
		
		/**@Upgrades*/
		this.addSlotToContainer(new SlotUpgrade(tile, 10, 34, 17, ItemHandler.upgrade_speed));
		this.addSlotToContainer(new SlotUpgrade(tile, 11, 34, 39, ItemHandler.upgrade_capacity));
		this.addSlotToContainer(new SlotUpgrade(tile, 12, 34, 61, ItemHandler.upgrade_charge));
		
		
		/**@Armour*/
		/*
		this.addSlotToContainer(new Slot(invPlayer, 39, 173, 6));
		this.addSlotToContainer(new Slot(invPlayer, 38, 173, 25));
		this.addSlotToContainer(new Slot(invPlayer, 37, 173, 44));
		this.addSlotToContainer(new Slot(invPlayer, 36, 173, 63));
		 */
		
		/**@Inventory*/
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 95 + y * 18));
			}
		}
		/**@Actionbar*/
		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 153));
		}
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
				containerlistener.sendWindowProperty(this, 0, this.inventory.getField(0));
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
