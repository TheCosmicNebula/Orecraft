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

public class ContainerEnergizedCapacitor extends Container {
	private IInventory capacitor;
	private int power;

	public ContainerEnergizedCapacitor(InventoryPlayer invPlayer, IInventory tile) {
		this.capacitor = tile;
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 142));
		}
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 84 + y * 18));
			}
		}
		addSlotToContainer(new Slot(tile, 1, 96, 17));
		
		addSlotToContainer(new Slot(tile, 8, 52, 52));
		addSlotToContainer(new Slot(tile, 9, 74, 52));
		addSlotToContainer(new Slot(tile, 10, 96, 52));
		addSlotToContainer(new Slot(tile, 11, 118, 52));
		addSlotToContainer(new Slot(tile, 12, 140, 52));
		
		addSlotToContainer(new Slot(invPlayer, 39, 173, 6));
		addSlotToContainer(new Slot(invPlayer, 38, 173, 25));
		addSlotToContainer(new Slot(invPlayer, 37, 173, 44));
		addSlotToContainer(new Slot(invPlayer, 36, 173, 63));

	}

	@Override
	public void addListener(IContainerListener containerListener) {
		super.addListener(containerListener);
		containerListener.sendAllWindowProperties(this, this.capacitor);
	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.listeners.size(); i++) {
			IContainerListener containerlistener = (IContainerListener) this.listeners.get(i);

			if (this.power != this.capacitor.getField(0)) {
				containerlistener.sendProgressBarUpdate(this, 0, this.capacitor.getField(0));
			}
		}
		this.power = this.capacitor.getField(0);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.capacitor.setField(id, data);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < this.capacitor.getSizeInventory()) {
				if (!this.mergeItemStack(itemstack1, this.capacitor.getSizeInventory(), this.inventorySlots.size(),
						true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, this.capacitor.getSizeInventory(), false)) {
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
		return this.capacitor.isUsableByPlayer(entityplayer);
	}

}
