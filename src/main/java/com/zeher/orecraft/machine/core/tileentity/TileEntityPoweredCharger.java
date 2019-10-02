package com.zeher.orecraft.machine.core.tileentity;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.core.container.ContainerPoweredCharger;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.value.ItemPowerValues;
import com.zeher.zeherlib.machine.IMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityPoweredCharger extends TileEntityLockable implements ITickable, ISidedInventory, IMachine {
	
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	
	private NonNullList<ItemStack> charge_stacks = NonNullList.<ItemStack>withSize(13, ItemStack.EMPTY);
	
	private String custom_name;

	private int stored;
	private int capacity = Reference.VALUE.MACHINE.poweredStored(0);
	
	private int input_rate = 9000;

	public int getSizeInventory() {
		return this.charge_stacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.charge_stacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.charge_stacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.charge_stacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.charge_stacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.charge_stacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.charge_stacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.markDirty();
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.custom_name : OrecraftReference.GUI_NAME.MACHINE.POWERED.CHARGER;
	}

	public boolean hasCustomName() {
		return this.custom_name != null && !this.custom_name.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.custom_name = p_145951_1_;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.charge_stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.charge_stacks);
		this.stored = compound.getInteger("stored");

		if (compound.hasKey("CustomName", 8)) {
			this.custom_name = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("stored", this.stored);
		ItemStackHelper.saveAllItems(compound, this.charge_stacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.custom_name);
		}
		return compound;
	}

	public int getInventoryStackLimit() {
		return 64;
	}
	
	public void update() {
		if (this.stored > this.capacity) {
			this.stored = this.capacity;
		}
		
		int i = this.charge_stacks.get(11).getCount();
		this.capacity = Reference.VALUE.MACHINE.poweredStored(i);
		
		if (!this.world.isRemote) {
			for(int x = 1; x < 10; x++){
				if (this.isItemStored(this.charge_stacks.get(x)) && !(this.charge_stacks.get(x).isEmpty()) && (this.stored > 0)) {
					if (this.charge_stacks.get(x).getItemDamage() > 0) {
						this.charge_stacks.get(x).setItemDamage(this.charge_stacks.get(x).getItemDamage() - 1);
						this.stored -= ItemPowerValues.getItemPower(this.charge_stacks.get(x).getItem());
					}
				}
			}
			if (this.isItemStored(this.charge_stacks.get(0)) && (this.stored <= this.capacity - ItemPowerValues.getItemPower(this.charge_stacks.get(0).getItem()))) {
				if (!this.charge_stacks.get(0).isItemStackDamageable()) {
					this.stored += ItemPowerValues.getItemPower(this.charge_stacks.get(0).getItem());
					
					this.markDirty();
					if (this.charge_stacks.get(0) != null) {
						this.charge_stacks.get(0).setCount(this.charge_stacks.get(0).getCount());
						if (this.charge_stacks.get(0).getCount() == 0) {
							this.charge_stacks.set(0, this.charge_stacks.get(0).getItem().getContainerItem(this.charge_stacks.get(0)));
						}
					}
				} else if (this.charge_stacks.get(0).getItemDamage() < this.charge_stacks.get(0) .getMaxDamage()) {
					this.stored += ItemPowerValues.getItemPower(this.charge_stacks.get(0).getItem());
					this.charge_stacks.set(0, new ItemStack(this.charge_stacks.get(0).getItem(), this.charge_stacks.get(0).getCount(), this.charge_stacks.get(0).getItemDamage() + 1));
				}
			}	
		}
	}
	
	public int getStoredRemainingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}

	public static boolean isItemStored(ItemStack par0ItemStack) {
		return ItemPowerValues.getItemPower(par0ItemStack.getItem()) > 0;
	}

	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public void openInventory(EntityPlayer player) { }

	public void closeInventory(EntityPlayer player) { }

	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES);
	}

	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (direction == EnumFacing.DOWN && index == 1) {
			Item item = stack.getItem();

			if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
				return false;
			}
		}
		return true;
	}

	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerPoweredCharger(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 0:
			return this.stored;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.stored = value;
		}
	}

	public int getFieldCount() {
		return 1;
	}

	public void clear() {
		this.charge_stacks.clear();
	}

	IItemHandler handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
	IItemHandler handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
	IItemHandler handlerSide = new SidedInvWrapper(this, EnumFacing.WEST);

	@Override
	public <T> T getCapability(Capability<T> capability, @javax.annotation.Nullable EnumFacing facing) {
		if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == EnumFacing.DOWN)
				return (T) handlerBottom;
			else if (facing == EnumFacing.UP)
				return (T) handlerTop;
			else
				return (T) handlerSide;
		return super.getCapability(capability, facing);
	}

	@Override
	public String getGuiID() {
		return null;
	}

	@Override
	public int getStored() {
		return this.stored;
	}

	@Override
	public int getCapacity() {
		return this.capacity;
	}

	@Override
	public int getInputRate() {
		return this.input_rate;
	}

	@Override
	public boolean hasStored() {
		return this.stored > 0;
	}

	@Override
	public void addStored(int add) {
		this.stored += add;
	}

	@Override
	public int getCookSpeed() {
		return 0;
	}

	@Override
	public int getCookTime(int i) {
		return 0;
	}
	
}