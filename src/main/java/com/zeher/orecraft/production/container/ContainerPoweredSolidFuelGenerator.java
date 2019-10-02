package com.zeher.orecraft.production.container;

import com.zeher.orecraft.core.handler.ItemHandler;
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

public class ContainerPoweredSolidFuelGenerator extends Container {
	private IInventory inventory;
	private int cook_time;

	private int stored;

	public ContainerPoweredSolidFuelGenerator(InventoryPlayer invPlayer, IInventory tile) {
		
		this.inventory = tile;
		
		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 151));
		}
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 93 + y * 18));
			}
		}
		this.addSlotToContainer(new Slot(tile, 0, 105, 28));

		this.addSlotToContainer(new SlotUpgrade(tile, 1, 51, 17, ItemHandler.upgrade_speed));
		this.addSlotToContainer(new SlotUpgrade(tile, 2, 51, 39, ItemHandler.upgrade_capacity));
		this.addSlotToContainer(new SlotUpgrade(tile, 3, 51, 61, ItemHandler.upgrade_generator));
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

			if (this.stored != this.inventory.getField(4)) {
				containerlistener.sendWindowProperty(this, 4, this.inventory.getField(4));
			}
			if (this.cook_time != this.inventory.getField(2)) {
				containerlistener.sendWindowProperty(this, 2, this.inventory.getField(2));
			}
		}
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
