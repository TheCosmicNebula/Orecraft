package com.zeher.orecraft.storage.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

public class TileEntityMechanisedStorageLarge extends TileEntity implements IInventory, ISidedInventory {

	private NonNullList<ItemStack> storage_stacks = NonNullList.<ItemStack>withSize(91, ItemStack.EMPTY);
	private static final int[] slots = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 
			11, 12, 13, 14, 15, 16, 17, 18, 19, 
			20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 
			30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
			40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 
			50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
			60, 61, 62, 63, 64, 65, 66, 67, 68, 69,
			70, 71, 72, 73, 74, 75, 76, 77, 78, 79,
			80, 81, 82, 83, 84, 85, 86, 87, 88, 89,
			90, 91};
	
	public String custom_name;
	
	public String getName()
    {
        return this.hasCustomName() ? this.custom_name : "Mechanised Storage";
    }

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return storage_stacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.storage_stacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }
        return true;
	}

	public ItemStack getStackInSlot(int i) {
		return this.storage_stacks.get(i);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.storage_stacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.storage_stacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.storage_stacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack)
				&& ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.storage_stacks.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
	}
	public int getInventoryStackLimit()
    {
        return 64;
    }

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(this.pos) <= 64.0D;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		this.storage_stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.storage_stacks);
		
		if (compound.hasKey("custom_name", 8)) {
			this.custom_name = compound.getString("custom_name");
		}
		super.readFromNBT(compound);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, this.storage_stacks);
		
		if (this.hasCustomName()) {
			compound.setString("custom_name", this.custom_name);
		}
		return super.writeToNBT(compound);
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return slots;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return true;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return true;
	}
}
