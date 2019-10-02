package com.zeher.orecraft.machine.core.container;

import com.zeher.orecraft.core.handler.ItemHandler;
import com.zeher.orecraft.machine.client.slot.SlotExtractor;
import com.zeher.orecraft.machine.core.recipe.ExtractorRecipes;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredExtractor;
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

public class ContainerPoweredExtractor extends Container {
	private IInventory inventory;
	private int cook_time;

	private int stored;

	public ContainerPoweredExtractor(InventoryPlayer invPlayer, IInventory tile) {
		this.inventory = tile;
		
		/**@Inputslot*/
		this.addSlotToContainer(new Slot(tile, 0, 104, 18));

		/**@Powerslot*/
		this.addSlotToContainer(new SlotBattery(tile, 1, 77, 61));

		/**@OutputSlot*/
		this.addSlotToContainer(new SlotExtractor(invPlayer.player, tile, 2, 104, 60));

		/**@Upgradeslots*/
		this.addSlotToContainer(new SlotUpgrade(tile, 3, 55, 17, ItemHandler.upgrade_speed));
		this.addSlotToContainer(new SlotUpgrade(tile, 4, 55, 39, ItemHandler.upgrade_capacity));
		this.addSlotToContainer(new SlotUpgrade(tile, 5, 55, 61, ItemHandler.upgrade_charge));
		
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
				if (!ExtractorRecipes.getInstance().getExtractingResult(itemstack1).isEmpty()) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (TileEntityPoweredExtractor.isItemPower(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
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
