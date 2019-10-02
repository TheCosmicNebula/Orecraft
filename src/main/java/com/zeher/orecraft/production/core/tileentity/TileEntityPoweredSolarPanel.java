package com.zeher.orecraft.production.core.tileentity;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.production.container.ContainerPoweredSolarPanel;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.transfer.EnergyTransfer;
import com.zeher.zeherlib.api.value.ProducerStoredValues;
import com.zeher.zeherlib.production.IProducer;

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

public class TileEntityPoweredSolarPanel extends TileEntityLockable implements ITickable, ISidedInventory, IProducer {

	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	
	private NonNullList<ItemStack> generator_stacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
	
	private String generatorCustomName;

	public int stored;
	public int capacity = ProducerStoredValues.poweredStored(0);
	public int output_rate = Reference.VALUE.PRODUCER.POWERED_OUTPUT_RATE;
	
	public int generation_rate;
	
	public int getSizeInventory() {
		return this.generator_stacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.generator_stacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.generator_stacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.generator_stacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.generator_stacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.generator_stacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.generator_stacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.markDirty();
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.generatorCustomName : OrecraftReference.GUI_NAME.PRODUCER.POWERED.SOLARPANEL;
	}

	public boolean hasCustomName() {
		return this.generatorCustomName != null && !this.generatorCustomName.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.generatorCustomName = p_145951_1_;
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.generator_stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.generator_stacks);
		this.stored = compound.getInteger("power");

		if (compound.hasKey("CustomName", 8)) {
			this.generatorCustomName = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("power", this.stored);
		ItemStackHelper.saveAllItems(compound, this.generator_stacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.generatorCustomName);
		}

		return compound;
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void update() {
		boolean flag = false;
		
		int i = this.generator_stacks.get(1).getCount();
		this.generation_rate = Reference.VALUE.PRODUCER.poweredSolarPanelGenerationRate(i);
		
		int i_one = this.generator_stacks.get(2).getCount();
		this.capacity = ProducerStoredValues.poweredStored(i_one);
		
		if (this.canProduce() && this.stored < this.capacity) {
			int scale = this.getTimeScaled();
			
			this.stored += this.generation_rate * scale;
			
			this.markDirty();
		}
		
		if (this.stored > 0 && this != null) {
			EnergyTransfer.OUTPUT.PRODUCER.outputEnergy(this.getWorld(), this.getPos());
		}
	}
	
	public int getStoredRemainingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}
	
	public boolean canProduce() {
		if (this.world.canSeeSky(this.pos)) {
			long time = this.world.getWorldTime();
			
			if (!this.world.isRaining()) {
				if (time >= 0 && time < 12500) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Integer getTimeScaled() {
		long time = this.world.getWorldTime();
		
		if (time >= 0 && time < 4000) {
			return 1;
		} else if (time >= 4000 && time < 8000) {
			return 2;
		} else if (time >= 8000 && time < 12500) {
			return 1;
		}
		return 1;
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
		return new ContainerPoweredSolarPanel(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 2:
			return this.stored;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) {
		switch (id) {
		case 2:
			this.stored = value;
		}
	}

	public int getFieldCount() {
		return 1;
	}

	public void clear() {
		this.generator_stacks.clear();
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
		return stored;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}
	
	@Override
	public boolean hasStored() {
		return stored > 0;
	}

	@Override
	public int getOutputRate() {
		return this.output_rate;
	}

	@Override
	public int getGenerationRate() {
		return this.generation_rate * this.getTimeScaled();
	}
	
	@Override
	public void minusStored(Integer value) {
		this.stored -= value;
	}
}
