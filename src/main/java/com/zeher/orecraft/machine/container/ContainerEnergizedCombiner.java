package com.zeher.orecraft.machine.container;

import com.zeher.orecraft.client.slot.SlotMachineCombiner;
import com.zeher.orecraft.core.handlers.ItemHandler;
import com.zeher.orecraft.core.handlers.recipe.MachineCompressorRecipes;
import com.zeher.trzlib.client.slot.TRZSlotBucket;
import com.zeher.trzlib.client.slot.TRZSlotItem;
import com.zeher.trzlib.client.slot.TRZSlotMachineUpgrade;
import com.zeher.trzlib.client.slot.TRZSlotNoAccess;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerEnergizedCombiner extends Container {
	private IInventory inventory;
	private int cookTime;
	private int totalCookTime;
	private int furnaceBurnTime;
	private int currentItemBurnTime;

	private int power;

	public ContainerEnergizedCombiner(InventoryPlayer invPlayer, IInventory tile) {
		this.inventory = tile;
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 30 + x * 18, 153));
		}
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 30 + x * 18, 95 + y * 18));
			}
		}
		// Silicon Slot
		addSlotToContainer(new TRZSlotItem(tile, 0, 127, 20, ItemHandler.component_silicon_refined, 64));

		// Input Slots, CW
		addSlotToContainer(new Slot(tile, 1, 58, 19));
		addSlotToContainer(new Slot(tile, 2, 79, 16));
		addSlotToContainer(new Slot(tile, 3, 100, 19));
		addSlotToContainer(new Slot(tile, 4, 103, 40));
		addSlotToContainer(new Slot(tile, 5, 100, 61));
		addSlotToContainer(new Slot(tile, 6, 79, 64));
		addSlotToContainer(new Slot(tile, 7, 58, 61));
		addSlotToContainer(new Slot(tile, 8, 55, 40));

		// Output Slot
		addSlotToContainer(new SlotMachineCombiner(invPlayer.player, tile, 9, 79, 40));

		// Upgrade Slots
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 10, 8, 17, ItemHandler.upgrade_speed));
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 11, 8, 39, ItemHandler.upgrade_capacity));
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 12, 8, 61, ItemHandler.upgrade_charge));

		// Power Slot
		addSlotToContainer(new Slot(tile, 13, 8, 95));
		
		//Fluid Slots
		addSlotToContainer(new TRZSlotBucket(tile, 14, 152, 17));
		addSlotToContainer(new TRZSlotNoAccess(tile, 15, 152, 61, 16));
		
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 16, 196, 17, ItemHandler.upgrade_fluid_speed));
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 17, 196, 39, ItemHandler.upgrade_fluid_capacity));
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 18, 196, 61, ItemHandler.upgrade_fluid_efficiency));
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
				containerlistener.sendProgressBarUpdate(this, 4, this.inventory.getField(4));
			}
			if (this.cookTime != this.inventory.getField(2)) {
				containerlistener.sendProgressBarUpdate(this, 2, this.inventory.getField(2));
			}

			if (this.furnaceBurnTime != this.inventory.getField(0)) {
				containerlistener.sendProgressBarUpdate(this, 0, this.inventory.getField(0));
			}

			if (this.currentItemBurnTime != this.inventory.getField(1)) {
				containerlistener.sendProgressBarUpdate(this, 1, this.inventory.getField(1));
			}

			if (this.totalCookTime != this.inventory.getField(3)) {
				containerlistener.sendProgressBarUpdate(this, 3, this.inventory.getField(3));
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
				if (!MachineCompressorRecipes.instance().getCompressingResult(itemstack1).isEmpty()) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 3 && index < 30) {
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
