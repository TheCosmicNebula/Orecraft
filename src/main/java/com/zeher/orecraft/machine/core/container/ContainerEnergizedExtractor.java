package com.zeher.orecraft.machine.core.container;

import com.zeher.orecraft.core.handler.ItemHandler;
import com.zeher.orecraft.machine.core.tileentity.TileEntityEnergizedFurnace;
import com.zeher.zeherlib.client.slot.SlotBattery;
import com.zeher.zeherlib.client.slot.SlotUpgrade;
import com.zeher.zeherlib.core.item.ItemUpgrade;
import com.zeher.zeherlib.storage.item.ItemStorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerEnergizedExtractor extends Container {
	private IInventory inventory;

	private int stored;

	private int cook_time_one;
	private int cook_time_two;
	
	public static final int input_slot = 0;
	public static final int input_slot2 = 2;
	
	public static final int output_slot = 4;
	public static final int output_slot2 = 3;
	
	public static final int battery_slot = 1;
	
	public static final int upgrade_slot1 = 5;
	public static final int upgrade_slot2 = 6;
	public static final int upgrade_slot3 = 7;
	
	public ContainerEnergizedExtractor(InventoryPlayer invPlayer, IInventory tile) {
		this.inventory = tile;
		
		/**@Inputs + @Battery*/
		this.addSlotToContainer(new Slot(tile, input_slot, 96, 18));
		this.addSlotToContainer(new SlotBattery(tile, battery_slot, 69, 61));
		this.addSlotToContainer(new Slot(tile, input_slot2, 112, 18));

		/**@Outputs*/
		this.addSlotToContainer(new SlotFurnaceOutput(invPlayer.player, tile, output_slot2, 112, 60));
		this.addSlotToContainer(new SlotFurnaceOutput(invPlayer.player, tile, output_slot, 96, 60));

		/**@Upgrades*/
		this.addSlotToContainer(new SlotUpgrade(tile, upgrade_slot1, 47, 17, ItemHandler.upgrade_speed));
		this.addSlotToContainer(new SlotUpgrade(tile, upgrade_slot2, 47, 39, ItemHandler.upgrade_capacity));
		this.addSlotToContainer(new SlotUpgrade(tile, upgrade_slot3, 47, 61, ItemHandler.upgrade_charge));
		
		/**@Inventory*/
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 92 + y * 18));
			}
		}
		
		/**@ActionBar*/
		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 150));
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

			if (this.stored != this.inventory.getField(1)) {
				containerlistener.sendWindowProperty(this, 1, this.inventory.getField(1));
			}
			if (this.cook_time_one != this.inventory.getField(2)) {
				containerlistener.sendWindowProperty(this, 2, this.inventory.getField(2));
			}
			if (this.cook_time_two != this.inventory.getField(3)) {
				containerlistener.sendWindowProperty(this, 3, this.inventory.getField(3));
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

			// If itemstack is in Output stack
			if (index == output_slot || index == output_slot2) {
				// try to place in player inventory / action bar; add 36+1 because mergeItemStack uses < index, so the last slot in the inventory won't get checked if you don't add 1
				if (!this.mergeItemStack(itemstack1, output_slot2, output_slot + 38 + 1, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			
			// if itemstack is in upgrade stack
			if (index >= upgrade_slot1 && index < upgrade_slot3 + 1) {
				if (!this.mergeItemStack(itemstack1, output_slot2, output_slot + 38 + 1, true)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}
			
			// itemstack is in player inventory, try to place in appropriate furnace slot
			else if (index != battery_slot && index != input_slot && index != input_slot2) {
				// if it can be smelted, place in the input slots
				if (!FurnaceRecipes.instance().getSmeltingResult(itemstack1).isEmpty() && !(itemstack1.getItem() instanceof ItemStorage)) {
					// try to place in either Input slot; add 1 to final input slot because mergeItemStack uses < index
					if (!this.mergeItemStack(itemstack1, input_slot, input_slot2 + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				// if it's an energy source, place in Fuel slot
				else if (TileEntityEnergizedFurnace.isItemPower(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, battery_slot, battery_slot + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				// if it's an upgrade, place in appropriate slot
				else if (itemstack1.getItem() instanceof ItemUpgrade) {
					if (!this.mergeItemStack(itemstack1, upgrade_slot1, upgrade_slot3 + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				
				// item in player's inventory, but not in action bar
				else if (index >= output_slot2 + 1 && index < output_slot2 + 31) {
					// place in action bar
					if (!this.mergeItemStack(itemstack1, output_slot + 31, output_slot2 + 39 + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				// item in action bar - place in player inventory
				else if (index >= output_slot + 28 && index < output_slot + 39 + 1 && !this.mergeItemStack(itemstack1, output_slot + 1, output_slot + 31, false)) {
					return ItemStack.EMPTY;
				}
			}
			// In one of the infuser slots; try to place in player inventory / action bar
			else if (!this.mergeItemStack(itemstack1, output_slot + 1, output_slot + 39 + 1, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0) {
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
