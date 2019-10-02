package com.zeher.orecraft.production.core.tileentity;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.production.container.ContainerPoweredSolidFuelGenerator;
import com.zeher.orecraft.storage.core.util.StorageUtil;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.transfer.EnergyTransfer;
import com.zeher.zeherlib.api.value.ProducerStoredValues;
import com.zeher.zeherlib.production.IFluidProducer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityPoweredHeatedFluidGenerator extends TileEntityLockable implements ITickable, ISidedInventory, IFluidProducer, IFluidHandler {

	private int fluid_capacity = 16000;

	public FluidTank tank = new FluidTank(fluid_capacity);
	private boolean needsUpdate = false;
	private int updateTimer = 0;

	public int fill_level;

	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	
	private NonNullList<ItemStack> generator_stacks = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);

	public int cook_time;
	
	private String custom_name;

	public int stored;
	public int capacity = ProducerStoredValues.poweredStored(0);
	public int output_rate = Reference.VALUE.PRODUCER.POWERED_OUTPUT_RATE;
	
	public int generation_rate;
	public int cook_speed;

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
			this.cook_time = 0;
			this.markDirty();
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.custom_name : OrecraftReference.GUI_NAME.PRODUCER.POWERED.HEATEDFLUID;
	}

	public boolean hasCustomName() {
		return this.custom_name != null && !this.custom_name.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.custom_name = p_145951_1_;
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.generator_stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.generator_stacks);
		this.cook_time = compound.getInteger("cook_time");
		this.stored = compound.getInteger("power");
		tank.readFromNBT(compound);
		
		if (compound.hasKey("CustomName", 8)) {
			this.custom_name = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("cook_time", (short) this.cook_time);
		compound.setInteger("power", this.stored);
		tank.writeToNBT(compound);
		
		ItemStackHelper.saveAllItems(compound, this.generator_stacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.custom_name);
		}
		return super.writeToNBT(compound);
	}
	
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new SPacketUpdateTileEntity(this.pos, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound tag = pkt.getNbtCompound();
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void update() {
		this.checkFluidInputSlot();
		
		if (this.canProduce() && this.stored < this.capacity) {
			this.cook_time++;
			this.stored += this.generation_rate;
			
			if (this.tank.getFluidAmount() <= this.generation_rate) {
				this.tank.drain(this.tank.getFluidAmount(), true);
			} else {
				this.tank.drain(1000 / this.cook_speed , true);
			}
			
			if (this.cook_time == this.cook_speed) {
				this.cook_time = 0;
			}
		} else {
			this.cook_time = 0;
		}
		
		if (this.cook_time > 0) {
			//BlockPoweredHeatedFluidGenerator.setState(true, this.world, this.pos);
			this.markDirty();
		}

		if (this.stored > this.capacity) {
			this.stored = this.capacity;
		}
		
		int i = this.generator_stacks.get(1).getCount();
		this.cook_speed = Reference.VALUE.PRODUCER.poweredGenerationSpeed(i);
		this.generation_rate = Reference.VALUE.PRODUCER.poweredGenerationRate(i);
		
		int i_one = this.generator_stacks.get(2).getCount();
		this.capacity = ProducerStoredValues.poweredStored(i_one);
		
		if (this.stored > 0 && this != null) {
			EnergyTransfer.OUTPUT.FLUIDPRODUCER.outputEnergy(this.world, this.pos);
		}
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.cook_time * par1 / this.cook_speed;
	}

	public int getPowerRemainingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}

	public void checkFluidInputSlot() {
		if (!this.generator_stacks.get(7).isEmpty()) {
			if (this.generator_stacks.get(7).getItem().equals(Items.BUCKET)) {
				if (this.tank.getFluidAmount() > 0) {
					ItemStack fillStack = FluidUtil.tryFillContainer(this.generator_stacks.get(7), this.tank, 1000, null, false).result;
					if (this.generator_stacks.get(8).isEmpty()) {
						if (fillStack != null) {
							this.tank.drain(FluidUtil.getFluidContained(fillStack).amount, true);
							this.generator_stacks.get(7).shrink(1);
							this.generator_stacks.set(8, fillStack);
							StorageUtil.syncBlockAndRerender(world, pos);
						}
					}
				}
			}
			FluidStack fluid = FluidUtil.getFluidContained(this.generator_stacks.get(7));
			if (fluid != null) {
				int amount = tank.fill(fluid, false);
				if (amount == fluid.amount) {
					if (this.generator_stacks.get(8).getItem().equals(Items.BUCKET)
							&& this.generator_stacks.get(8).getCount() < 16) {
						if (fluid.getFluid().getTemperature() > 1000) {
							this.tank.fill(fluid, true);
							this.generator_stacks.get(7).shrink(1);
							this.generator_stacks.get(8).grow(1);
							StorageUtil.syncBlockAndRerender(world, pos);
						}
					}
					if (this.generator_stacks.get(8).isEmpty()) {
						if (fluid.getFluid().getTemperature() > 1000) {
							this.tank.fill(fluid, true);
							this.generator_stacks.get(7).shrink(1);
							this.generator_stacks.set(8, new ItemStack(Items.BUCKET));
							StorageUtil.syncBlockAndRerender(world, pos);
						}
					}
				}
			}
		}
		this.markDirty();
	}
	
	public int getFluidLevelScaled(int one) {
		return this.tank.getFluidAmount() * one / this.tank.getCapacity();
	}

	public boolean canProduce() {
		if(this.isFluidEmpty()){
			return false;
		} else {
			if(this.tank.getFluid().getFluid().getTemperature() > 1000 && !this.isFluidEmpty()){
				return true;
			}
		}
		return false;
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
		return new ContainerPoweredSolidFuelGenerator(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 1:
			return this.cook_time;
		case 2:
			return this.stored;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) {
		switch (id) {
		case 1:
			this.cook_time = value;
			break;
		case 2:
			this.stored = value;
		}
	}

	public int getFieldCount() {
		return 2;
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

	public int getFillLevel() {
		return this.fill_level;
	}

	public void updateFillLevel() {
		this.fill_level = this.getCurrentStoredAmount() / 1000;
	}

	public Fluid getCurrentStoredFluid() {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(world, pos);
		if (!isFluidEmpty()) {
			return this.tank.getFluid().getFluid();
		}
		return null;
	}

	public boolean isFluidEmpty() {
		return tank.getFluidAmount() == 0;
	}

	public int getCurrentStoredAmount() {
		StorageUtil.syncBlockAndRerender(world, pos);
		return this.tank.getFluidAmount();
	}

	public int fill(FluidStack resource, boolean doFill) {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(world, pos);
		needsUpdate = true;
		this.markDirty();
		return this.tank.fill(resource, doFill);
	}

	public FluidStack drain(FluidStack resource, boolean doDrain) {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(world, pos);
		this.markDirty();
		return this.tank.drain(resource.amount, doDrain);
	}

	public FluidStack drain(int maxDrain, boolean doDrain) {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(world, pos);
		needsUpdate = true;
		this.markDirty();
		return this.tank.drain(maxDrain, doDrain);
	}

	public boolean canFill(EnumFacing from, Fluid fluid) {
		return true;
	}

	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return true;
	}

	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		StorageUtil.syncBlockAndRerender(world, pos);
		return new FluidTankInfo[] { this.tank.getInfo() };
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return this.tank.getTankProperties();
	}

	public FluidTank getTank() {
		return this.tank;
	}

	@Override
	public void clear() {
		this.generator_stacks.clear();
	}

	@Override
	public int getOutputRate() {
		return this.output_rate;
	}

	@Override
	public int getGenerationRate() {
		return this.generation_rate;
	}
	
	@Override
	public void minusStored(Integer value) {
		this.stored -= value;
	}

}