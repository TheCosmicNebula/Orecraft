package com.zeher.orecraft.machine.core.container;

import com.zeher.orecraft.core.handler.ItemHandler;
import com.zeher.orecraft.machine.client.slot.SlotOreProcessor;
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

public class ContainerPoweredOreProcessor extends Container {
	
	private IInventory inventory;
	private int cook_time;
	private int stored;

	private int power;

	public ContainerPoweredOreProcessor(InventoryPlayer invPlayer, IInventory tile) {
		this.inventory = tile;
		
		/**@Inputslot*/
		this.addSlotToContainer(new Slot(tile, 0, 63, 17));
		
		/**@Batteryslot*/
		this.addSlotToContainer(new SlotBattery(tile, 1, 35, 61));
		
		/**@Ouputslot*/
		this.addSlotToContainer(new SlotOreProcessor(invPlayer.player, tile, 2, 63, 61));

		/**@Upgradeslots*/
		this.addSlotToContainer(new SlotUpgrade(tile, 3, 13, 17, ItemHandler.upgrade_speed));
		this.addSlotToContainer(new SlotUpgrade(tile, 4, 13, 39, ItemHandler.upgrade_capacity));
		this.addSlotToContainer(new SlotUpgrade(tile, 5, 13, 61, ItemHandler.upgrade_charge));
		
		/**@FluidUpgradeslots*/
		this.addSlotToContainer(new SlotUpgrade(tile, 6, 146, 17, ItemHandler.upgrade_fluid_speed));
		this.addSlotToContainer(new SlotUpgrade(tile, 7, 146, 39, ItemHandler.upgrade_fluid_capacity));
		this.addSlotToContainer(new SlotUpgrade(tile, 8, 146, 61, ItemHandler.upgrade_fluid_efficiency));
		
		/**@Bucketslots*/
		this.addSlotToContainer(new SlotBucket(tile, 9, 102, 17));
		this.addSlotToContainer(new SlotRestrictedAccess(tile, 10, 102, 61, 16));
		
		/**@Inventory*/
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 96 + y * 18));
			}
		}
		
		/**@Actiontbar*/
		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 154));
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
