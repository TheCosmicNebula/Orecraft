package com.zeher.orecraft.machine.core.tileentity;

import com.zeher.orecraft.storage.core.util.StorageUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class TileEntityCombinerPedestal extends TileEntityLockable implements ITickable, ISidedInventory, IInventory {
	private static final int[] acc_slots = { 0 };
	private NonNullList<ItemStack> pedestal_stacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);

	public int getSizeInventory() {
		return this.pedestal_stacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.pedestal_stacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		if (((ItemStack) this.pedestal_stacks.get(0)).getCount() < 1) {
			ItemStack itemstack = (ItemStack) this.pedestal_stacks.get(index);
			boolean flag = (!stack.isEmpty()) && (stack.isItemEqual(itemstack)) && (ItemStack.areItemStackTagsEqual(stack, itemstack));
			this.pedestal_stacks.set(index, stack);
			if (stack.getCount() > getInventoryStackLimit()) {
				stack.setCount(getInventoryStackLimit());
			}
		}
		StorageUtil.syncBlockAndRerender(this.world, this.pos);
	}

	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.pedestal_stacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.pedestal_stacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.pedestal_stacks, index);
	}

	public int getInventoryStackLimit() {
		return 1;
	}

	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public void openInventory(EntityPlayer player) {
	}

	public void closeInventory(EntityPlayer player) {
	}

	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	public int getField(int id) {
		return 0;
	}

	public void setField(int id, int value) {
	}

	public int getFieldCount() {
		return 0;
	}

	public void clear() {
		this.pedestal_stacks.clear();
	}

	public String getName() {
		return null;
	}

	public boolean hasCustomName() {
		return false;
	}

	public String getGuiID() {
		return null;
	}
	
	public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.UP ? acc_slots : side == EnumFacing.DOWN ? acc_slots : acc_slots;
	}

	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		if (((ItemStack) this.pedestal_stacks.get(0)).isEmpty()) {
			return this.isItemValidForSlot(0, itemStackIn);
		}
		return false;
	}

	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if ((direction == EnumFacing.DOWN) && (index == 0)) {
			Item item = stack.getItem();
			if ((item != Items.WATER_BUCKET) && (item != Items.BUCKET)) {
				return false;
			}
		}
		return true;
	}

	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return null;
	}
	
	@Override
	public void update() {
	}
}
