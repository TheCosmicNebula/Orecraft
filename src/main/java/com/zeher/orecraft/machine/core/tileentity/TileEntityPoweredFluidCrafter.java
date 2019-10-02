package com.zeher.orecraft.machine.core.tileentity;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.core.container.ContainerPoweredFluidCrafter;
import com.zeher.orecraft.machine.core.recipe.FluidProcessorMeltRecipes;
import com.zeher.orecraft.storage.core.util.StorageUtil;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.value.ItemPowerValues;
import com.zeher.zeherlib.machine.IFluidMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
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

public class TileEntityPoweredFluidCrafter extends TileEntityLockable implements ITickable, ISidedInventory, IFluidMachine, IFluidHandler, IInventory {
	
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	
	private NonNullList<ItemStack> process_stacks = NonNullList.<ItemStack>withSize(11, ItemStack.EMPTY);

	private int fluid_capacity = 16000;

	private FluidTank tank = new FluidTank(fluid_capacity);
	private boolean needsUpdate = false;
	private int updateTimer = 0;

	public int fill_level;
	
	public int cook_time;
	private String custom_name;

	private int stored;
	private int capacity = Reference.VALUE.MACHINE.poweredStored(0);
	private int input_rate = Reference.VALUE.MACHINE.POWERED_INPUT_RATE;
	
	private int cook_speed = Reference.VALUE.MACHINE.poweredProcessSpeed(0);
	
	public int mode = 0;
	
	private int sound_timer = 0;

	public int getSizeInventory() {
		return this.process_stacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.process_stacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.process_stacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.process_stacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.process_stacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.process_stacks.get(index);
		
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		
		this.process_stacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.cook_time = 0;
			this.markDirty();
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.custom_name : OrecraftReference.GUI_NAME.MACHINE.POWERED.FLUIDCRAFTER;
	}

	public boolean hasCustomName() {
		return this.custom_name != null && !this.custom_name.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.custom_name = p_145951_1_;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.process_stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.process_stacks);
		this.cook_time = compound.getInteger("ProcessTime");
		this.stored = compound.getInteger("power");

		if (compound.hasKey("CustomName", 8)) {
			this.custom_name = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("ProcessTime", (short) this.cook_time);
		compound.setInteger("power", this.stored);
		ItemStackHelper.saveAllItems(compound, this.process_stacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.custom_name);
		}

		return compound;
	}
	
	public void handleUpdateTag(NBTTagCompound tag)  {
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
		if (!this.world.isRemote) {
			if (this.hasStored() && this.canProcess()) {
				this.stored -= Reference.VALUE.MACHINE.machineDrainRate(cook_speed);
				this.cook_time++;
				
				if (this.cook_time == this.cook_speed) {
					this.cook_time = 0;
					this.processItem();
				}
			} else {
				this.cook_time = 0;
			}
			
			if (this.cook_time > 0) {
				//BlockPoweredFluidCrafter.setState(false, this.world, this.pos);
				this.markDirty();
			}
		}
			
		if (this.stored > this.capacity) {
			this.stored = this.capacity;
		}
		
		int i = this.process_stacks.get(5).getCount();
		this.cook_speed = Reference.VALUE.MACHINE.poweredProcessSpeed(i);
		
		int i_one = this.process_stacks.get(6).getCount();
		this.capacity = Reference.VALUE.MACHINE.poweredStored(i_one);
		
		if (!this.world.isRemote) {
			if (this.isItemPower(this.process_stacks.get(1)) && (this.stored <= this.capacity - ItemPowerValues.getItemPower(this.process_stacks.get(1).getItem()))) {
				if (!this.process_stacks.get(1).isItemStackDamageable()) {
					this.stored += ItemPowerValues.getItemPower(this.process_stacks.get(1).getItem());

					this.markDirty();
					if (this.process_stacks.get(1) != null) {
						this.process_stacks.get(1).setCount(this.process_stacks.get(1).getCount());
						if (this.process_stacks.get(1).getCount() == 0) {
							this.process_stacks.set(1, this.process_stacks.get(1).getItem().getContainerItem(this.process_stacks.get(1)));
						}
					}
				} else if (this.process_stacks.get(1).getItemDamage() < this.process_stacks.get(1).getMaxDamage()) {
					this.stored += ItemPowerValues.getItemPower(this.process_stacks.get(1).getItem());
					this.process_stacks.set(1, new ItemStack(this.process_stacks.get(1).getItem(), this.process_stacks.get(1).getCount(), this.process_stacks.get(1).getItemDamage() + 1));
				}
			}
			
		}
	}

	@SideOnly(Side.CLIENT)
	public int getProcessProgressScaled(int par1) {
		return this.cook_time * par1 / this.cook_speed;
	}
	
	public int getPowerRemainingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}
	
	public boolean canProcess() {
		/*if(mode == 0){
			if (((ItemStack) this.process_stacks.get(0)).isEmpty()) {
				return false;
			} else {
				ItemStack itemstack = MachineProcessorCleaningRecipes.instance().getCleaningResult((ItemStack) this.process_stacks.get(0));
	
				if (itemstack.isEmpty()) {
					return false;
				} else {
					ItemStack itemstack1 = (ItemStack) this.process_stacks.get(2);
					if (itemstack1.isEmpty()) {
						return true;
					}
					
					if (!itemstack1.isItemEqual(itemstack)) {
						return false;
					}
					
					int result = itemstack1.getCount() + itemstack.getCount();
					return result <= getInventoryStackLimit() && result <= itemstack1.getMaxStackSize();
				}
			}
		}*/
		if(mode == 0){
			if (((ItemStack) this.process_stacks.get(0)).isEmpty()) {
				return false;
			} else {
				FluidStack fluid = FluidProcessorMeltRecipes.getInstance().getRecipeResult((ItemStack) this.process_stacks.get(0));
				
				if (fluid == null) {
					return false;
				} else {
					return true;
				}
			}
		} else {
			return false;
		}
	}

	public void processItem() {
		if(mode == 0){
			
			FluidStack fluid = FluidProcessorMeltRecipes.getInstance().getRecipeResult((ItemStack) this.process_stacks.get(0));
			this.process_stacks.get(0).shrink(1);
			
			int amount = fill(fluid, false);
				
			if (amount == fluid.amount) {
				this.fill(fluid, true);
			}
		}
		/*if(this.mode == 0){
			if (this.canProcess()) {
				this.stored -= Util.machinePowerDrain();
				ItemStack itemstack = (ItemStack) this.process_stacks.get(0);
				ItemStack itemstack1 = MachineProcessorCleaningRecipes.instance().getCleaningResult(itemstack);
				ItemStack itemstack2 = (ItemStack) this.process_stacks.get(2);
	
				if (itemstack2.isEmpty()) {
					this.process_stacks.set(2, itemstack1.copy());
				} else if (itemstack2.getItem() == itemstack1.getItem()) {
					itemstack2.grow(itemstack1.getCount());
				}
	
				if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1
						&& !((ItemStack) this.process_stacks.get(1)).isEmpty()
						&& ((ItemStack) this.process_stacks.get(1)).getItem() == Items.BUCKET) {
					this.process_stacks.set(1, new ItemStack(Items.WATER_BUCKET));
				}
	
				itemstack.shrink(1);
			}
		}
		
		if(this.mode == 1){
			if (this.canProcess()) {
				this.stored -= Util.machinePowerDrain();
				ItemStack itemstack = (ItemStack) this.process_stacks.get(0);
				ItemStack itemstack1 = MachineProcessorRefiningRecipes.instance().getRefiningResult(itemstack);
				ItemStack itemstack2 = (ItemStack) this.process_stacks.get(2);
	
				if (itemstack2.isEmpty()) {
					this.process_stacks.set(2, itemstack1.copy());
				} else if (itemstack2.getItem() == itemstack1.getItem()) {
					itemstack2.grow(itemstack1.getCount());
				}
	
				if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1
						&& !((ItemStack) this.process_stacks.get(1)).isEmpty()
						&& ((ItemStack) this.process_stacks.get(1)).getItem() == Items.BUCKET) {
					this.process_stacks.set(1, new ItemStack(Items.WATER_BUCKET));
				}
	
				itemstack.shrink(1);
			}
		}*/
	}

	public static boolean isItemPower(ItemStack par0ItemStack) {
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
		return new ContainerPoweredFluidCrafter(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 2:
			return this.cook_time;
		case 4:
			return this.stored;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) {
		switch (id) {
		case 2:
			this.cook_time = value;
			break;
		case 4:
			this.stored = value;
		}
	}

	public int getFieldCount() {
		return 2;
	}

	public void clear() {
		this.process_stacks.clear();
	}

	IItemHandler handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
	IItemHandler handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
	IItemHandler handlerSide = new SidedInvWrapper(this, EnumFacing.WEST);

	@Override
	public <T> T getCapability(Capability<T> capability, @javax.annotation.Nullable EnumFacing facing) {
		if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == EnumFacing.DOWN) {
				return (T) handlerBottom;
			} else if (facing == EnumFacing.UP) {
				return (T) handlerTop;
			} else {
				return (T) handlerSide;
			}
		return super.getCapability(capability, facing);
	}

	@Override
	public String getGuiID() {
		return null;
	}
	
	public void checkFluidInputSlot() {
		if (!this.process_stacks.get(7).isEmpty()) {
			if (this.process_stacks.get(7).getItem().equals(Items.BUCKET)) {
				if (this.tank.getFluidAmount() > 0) {
					ItemStack fillStack = FluidUtil.tryFillContainer(this.process_stacks.get(7), this.tank, 1000, null, false).result;
					if (this.process_stacks.get(8).isEmpty()) {
						if (fillStack != null) {
							this.tank.drain(FluidUtil.getFluidContained(fillStack).amount, true);
							this.process_stacks.get(7).shrink(1);
							this.process_stacks.set(8, fillStack);
							StorageUtil.syncBlockAndRerender(world, pos);
						}
					}
				}
			}
			FluidStack fluid = FluidUtil.getFluidContained(this.process_stacks.get(7));
			if (fluid != null) {
				int amount = tank.fill(fluid, false);
				if (amount == fluid.amount) {
					if (this.process_stacks.get(8).getItem().equals(Items.BUCKET)
							&& this.process_stacks.get(8).getCount() < 16) {
						if (fluid.getFluid().getTemperature() > 1000) {
							this.tank.fill(fluid, true);
							this.process_stacks.get(7).shrink(1);
							this.process_stacks.get(8).grow(1);
							StorageUtil.syncBlockAndRerender(world, pos);
						}
					}
					if (this.process_stacks.get(8).isEmpty()) {
						if (fluid.getFluid().getTemperature() > 1000) {
							this.tank.fill(fluid, true);
							this.process_stacks.get(7).shrink(1);
							this.process_stacks.set(8, new ItemStack(Items.BUCKET));
							StorageUtil.syncBlockAndRerender(world, pos);
						}
					}
				}
			}
		}
		this.markDirty();
	}
	
	public void checkFluidCapacity(){
		int i = this.process_stacks.get(5).getCount();
		
		if(i == 0){
			this.tank.setCapacity(this.fluid_capacity);
		} else if(i == 1){
			this.tank.setCapacity(this.fluid_capacity + 4000);
		}
	}

	public int getFluidLevelScaled(int one) {
		return this.tank.getFluidAmount() * one / this.tank.getCapacity();
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
	
	public int getMode(){
		return this.mode;
	}
	
	public void setMode(int set){
		this.mode = set;
	}
	
	public void cycleMode(){
		if(this.mode < 2){
			this.mode++;
		} else {
			this.mode = 0;
		}
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
		return this.cook_speed;
	}
	
}