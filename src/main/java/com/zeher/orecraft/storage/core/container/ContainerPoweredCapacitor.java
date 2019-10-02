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

import com.zeher.orecraft.storage.core.tileentity.TileEntityPoweredCapacitor;

public class ContainerPoweredCapacitor extends Container {
	private IInventory capacitor;
	private int power;

	public ContainerPoweredCapacitor(InventoryPlayer invPlayer, IInventory tile) {
		this.capacitor = tile;

		this.addSlotToContainer(new Slot(tile, 1, 92, 27));
		
		this.addSlotToContainer(new Slot(tile, 8, 48, 62));
		this.addSlotToContainer(new Slot(tile, 9, 70, 62));
		this.addSlotToContainer(new Slot(tile, 10, 92, 62));
		this.addSlotToContainer(new Slot(tile, 11, 114, 62));
		this.addSlotToContainer(new Slot(tile, 12, 136, 62));
		
		this.addSlotToContainer(new Slot(invPlayer, 39, 44, 27));
		this.addSlotToContainer(new Slot(invPlayer, 38, 66, 27));
		this.addSlotToContainer(new Slot(invPlayer, 37, 118, 27));
		this.addSlotToContainer(new Slot(invPlayer, 36, 140, 27));
		
		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 152));
		}
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 94 + y * 18));
			}
		}
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
				containerlistener.sendWindowProperty(this, 0, this.capacitor.getField(0));
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
