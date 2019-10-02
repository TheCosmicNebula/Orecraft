package com.zeher.orecraft.storage.core.container;

import com.zeher.orecraft.machine.core.recipe.ExtractorRecipes;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredExtractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Example extends Container {

	public static final int input_slot = 0;
	public static final int input_slot2 = 3;
	public static final int output_slot = 2;
	public static final int output_slot2 = 4;

	public static final int battery_slot = 1;

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// If itemstack is in Output stack
			if (par2 == output_slot) {
				// try to place in player inventory / action bar; add 36+1 because mergeItemStack uses < index, so the last slot in the inventory won't get checked if you don't add 1
				if (!this.mergeItemStack(itemstack1, output_slot + 1, output_slot + 36 + 1, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			// itemstack is in player inventory, try to place in appropriate furnace slot
			else if (par2 != battery_slot && par2 != input_slot && par2 != input_slot2) {
				// if it can be smelted, place in the input slots
				if (ExtractorRecipes.getInstance().getExtractingResult(itemstack1) != null) {
					// try to place in either Input slot; add 1 to final input slot because
					// mergeItemStack uses < index
					if (!this.mergeItemStack(itemstack1, input_slot, input_slot2 + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				// if it's an energy source, place in Fuel slot
				else if (TileEntityPoweredExtractor.isItemPower(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, battery_slot, battery_slot + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				// item in player's inventory, but not in action bar
				else if (par2 >= output_slot + 1 && par2 < output_slot + 28) {
					// place in action bar
					if (!this.mergeItemStack(itemstack1, output_slot + 28, output_slot + 37, false)) {
						return ItemStack.EMPTY;
					}
				}
				// item in action bar - place in player inventory
				else if (par2 >= output_slot + 28 && par2 < output_slot + 37
						&& !this.mergeItemStack(itemstack1, output_slot + 1, output_slot + 28, false)) {
					return ItemStack.EMPTY;
				}
			}
			// In one of the infuser slots; try to place in player inventory / action bar
			else if (!this.mergeItemStack(itemstack1, output_slot + 1, output_slot + 37, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
}
