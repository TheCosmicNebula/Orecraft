package com.zeher.orecraft.production.container;

import com.zeher.orecraft.core.handler.ItemHandler;
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

public class ContainerPoweredPeltierGenerator extends Container {
	private IInventory inventory;
	private int cookTime;
	private int totalCookTime;
	private int generatorBurnTime;
	private int currentItemBurnTime;

	private int stored;

	public ContainerPoweredPeltierGenerator(InventoryPlayer invPlayer, IInventory tile) {
		this.inventory = tile;
		
		
		//addSlotToContainer(new Slot(tile, 0, 90, 31));

		addSlotToContainer(new SlotUpgrade(tile, 1, 11, 17, ItemHandler.upgrade_speed));
		addSlotToContainer(new SlotUpgrade(tile, 2, 11, 39, ItemHandler.upgrade_capacity));
		addSlotToContainer(new SlotUpgrade(tile, 3, 11, 61, ItemHandler.upgrade_generator));

		addSlotToContainer(new SlotUpgrade(tile, 4, 148, 17, ItemHandler.upgrade_fluid_speed));
		addSlotToContainer(new SlotUpgrade(tile, 5, 148, 39, ItemHandler.upgrade_fluid_capacity));
		addSlotToContainer(new SlotUpgrade(tile, 6, 148, 61, ItemHandler.upgrade_fluid_efficiency));
		
		addSlotToContainer(new SlotBucket(tile, 7, 82, 17));
		addSlotToContainer(new SlotRestrictedAccess(tile, 8, 82, 61, 22));
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 93 + y * 18));
			}
		}
		
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 151));
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

			if (this.stored != this.inventory.getField(4)) {
				containerlistener.sendWindowProperty(this, 4, this.inventory.getField(4));
			}
			if (this.cookTime != this.inventory.getField(2)) {
				containerlistener.sendWindowProperty(this, 2, this.inventory.getField(2));
			}

			if (this.generatorBurnTime != this.inventory.getField(0)) {
				containerlistener.sendWindowProperty(this, 0, this.inventory.getField(0));
			}

			if (this.currentItemBurnTime != this.inventory.getField(1)) {
				containerlistener.sendWindowProperty(this, 1, this.inventory.getField(1));
			}

			if (this.totalCookTime != this.inventory.getField(3)) {
				containerlistener.sendWindowProperty(this, 3, this.inventory.getField(3));
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
