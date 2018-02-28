package com.zeher.orecraft.production.tileentity;

import com.zeher.orecraft.production.block.BlockCoalGeneratorBasic;
import com.zeher.orecraft.production.container.ContainerCoalGeneratorBasic;
import com.zeher.trzlib.api.value.TRZMachineValues;
import com.zeher.trzlib.api.value.TRZProducerValues;
import com.zeher.trzlib.production.TRZIProducer;
import com.zeher.trzlib.production.TRZTileEntityProducer;
import com.zeher.trzlib.transfer.TRZTileEntityEnergyPipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityCoalGeneratorBasic extends TRZTileEntityProducer implements ITickable, ISidedInventory, TRZIProducer {
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	private NonNullList<ItemStack> generator_stacks = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);

	private int generate_burn_time;
	private int generate_time;
	private String generatorCustomName;

	public int stored;
	public int capacity = TRZMachineValues.energizedBasicStored(0);
	public int output_rate = TRZProducerValues.basicOutputRate();
	public int generation_rate = 40;
	public int generation_time = 300;

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
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack)
				&& ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.generator_stacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.generate_time = 0;
			this.markDirty();
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.generatorCustomName : "Coal Generator";
	}

	public boolean hasCustomName() {
		return this.generatorCustomName != null && !this.generatorCustomName.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.generatorCustomName = p_145951_1_;
	}

	public static void registerFixesgenerator(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY,
				new ItemStackDataLists(TileEntityCoalGeneratorBasic.class, new String[] { "Items" }));
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.generator_stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.generator_stacks);
		this.generate_burn_time = compound.getInteger("BurnTime");
		this.generate_time = compound.getInteger("generate_time");
		this.stored = compound.getInteger("power");

		if (compound.hasKey("CustomName", 8)) {
			this.generatorCustomName = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short) this.generate_burn_time);
		compound.setInteger("generate_time", (short) this.generate_time);
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

	public boolean isBurning() {
		return this.generate_burn_time > 0;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory) {
		return inventory.getField(0) > 0;
	}

	public void update() {
		boolean flag = !this.isBurning();
		boolean flag1 = false;
		boolean item = false;
		
		checkGenerateSpeed();
		checkCapacity();
		
		if(this.isBurning()){
			--this.generate_burn_time;
			this.stored += this.generation_rate;
		}

		if (!this.world.isRemote) {
			if(!this.isBurning() && this.canBurn() && this.stored < this.capacity){
				this.generate_burn_time = generation_time;
			}
			if(this.isBurning()){
				flag1 = true;
			}
			
			if (canBurn() && this.stored < this.capacity) {
				this.generate_time += 1;
				
				if(this.generate_burn_time == (generation_time - 1)){
					this.generator_stacks.get(0).shrink(1);
				}
				if (this.generate_time == this.generation_time) {
					this.generate_time = 0;
					checkGenerateSpeed();
					checkCapacity();
					flag1 = true;
				}
			} else {
				this.generate_time = 0;
				//item = false;
			}
			if (flag == this.isBurning()) {
				flag1 = true;
				BlockCoalGeneratorBasic.setState(this.isBurning(), this.world, this.pos);
			}

			if (flag1) {
				this.markDirty();
			}
			
			if ((stored > capacity) && this.generator_stacks.get(2).getCount() == 0) {
				stored = capacity;
			}
			if ((stored > capacity) && this.generator_stacks.get(2).getCount() == 1) {
				stored = TRZMachineValues.energizedBasicStored(1);
			}
			if ((stored > capacity) && this.generator_stacks.get(2).getCount() == 2) {
				stored = TRZMachineValues.energizedBasicStored(2);
			}
			if ((stored > capacity) && this.generator_stacks.get(2).getCount() == 3) {
				stored = TRZMachineValues.energizedBasicStored(3);
			}
			if ((stored > capacity) && this.generator_stacks.get(2).getCount() == 4) {
				stored = TRZMachineValues.energizedBasicStored(4);
			}
		}
		
		if (this.stored > 0) {
			IBlockState this_state = world.getBlockState(this.pos);
			Block this_block = this_state.getBlock();
			TileEntity this_tile = world.getTileEntity(this.pos);

			if (this_tile != null) {
				TileEntity tile_up = world.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY() + 1, this.pos.getZ()));
				if (tile_up != null) {
					if (tile_up instanceof TRZTileEntityEnergyPipe) {
						int pipe_stored = ((TRZTileEntityEnergyPipe) tile_up).getStored();
						int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_up).getCapacity();
						int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_up).getTransferRate();
						int gap_stored = pipe_capacity - pipe_stored;
						
						if(pipe_stored <= pipe_capacity){
							if(pipe_transfer_rate <= this.output_rate){
								if(gap_stored >= pipe_transfer_rate){
									this.stored -= pipe_transfer_rate;
									((TRZTileEntityEnergyPipe) tile_up).addStored(pipe_transfer_rate, EnumFacing.UP);
								}
								else if(gap_stored < pipe_transfer_rate){
									this.stored -= gap_stored;
									((TRZTileEntityEnergyPipe) tile_up).addStored(gap_stored, EnumFacing.UP);
								}
							} else if(pipe_transfer_rate > this.output_rate){
								if(gap_stored >= pipe_transfer_rate){
									this.stored -= this.output_rate;
									((TRZTileEntityEnergyPipe) tile_up).addStored(this.output_rate, EnumFacing.UP);
								}
							}
						} 
						if(pipe_stored == pipe_capacity){
							((TRZTileEntityEnergyPipe) tile_up).addStored(0, EnumFacing.UP);
						}
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.generate_burn_time * par1 / this.generation_time;
	}

	public int getPowerRemainingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}

	public boolean hasPower() {
		return this.stored > 0;
	}
	
	public void checkGenerateSpeed() {
		int i = this.generator_stacks.get(1).getCount();
		Item item = this.generator_stacks.get(0).getItem();
		
		//if(item.equals(Items.COAL)){
			if (i == 0) {
				this.generation_time = 300;
				this.generation_rate = 40;
			} else if (i == 1) {
				this.generation_time = 240;
				this.generation_rate = 50;
			} else if (i == 2) {
				this.generation_time = 200;
				this.generation_rate = 60;
			} else if (i == 3) {
				this.generation_time = 160;
				this.generation_rate = 75;
			} else if (i == 4) {
				this.generation_time = 120;
				this.generation_rate = 100;
			} else {
				this.generation_time = 300;
				this.generation_rate = 40;
			}
		//}
	}

	public void checkCapacity() {
		int i = this.generator_stacks.get(2).getCount();
		if (i == 0) {
			this.capacity = TRZMachineValues.energizedBasicStored(0);
		} else if (i == 1) {
			this.capacity = TRZMachineValues.energizedBasicStored(1);
		} else if (i == 2) {
			this.capacity = TRZMachineValues.energizedBasicStored(2);
		} else if (i == 3) {
			this.capacity = TRZMachineValues.energizedBasicStored(3);
		} else if (i == 4) {
			this.capacity = TRZMachineValues.energizedBasicStored(4);
		} else {
			this.capacity = TRZMachineValues.energizedBasicStored(0);
		}
	}
	
	private boolean canBurn() {
		if(this.generate_time > 0 && this.generator_stacks.get(0).isEmpty()){
			return true;
		}
		if(this.generate_time > 0 && !this.generator_stacks.get(0).isEmpty()){
			return true;
		}
		if (this.generator_stacks.get(0).isEmpty() && this.generate_time == 0) {
			return false;
		}
		if(this.generate_time == 0 && this.generator_stacks.get(0).getItem().equals(Items.COAL)){
			return true;
		}
		if(this.generate_time == 0 && this.generator_stacks.get(0).getItem().equals(Item.getItemFromBlock(Blocks.COAL_BLOCK))){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false
				: player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
						(double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public void openInventory(EntityPlayer player) {
	}

	public void closeInventory(EntityPlayer player) {
	}

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
		return new ContainerCoalGeneratorBasic(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 0:
			return this.generate_burn_time;
		case 1:
			return this.generate_time;
		case 2:
			return this.stored;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.generate_burn_time = value;
			break;
		case 1:
			this.generate_time = value;
			break;
		case 2:
			this.stored = value;
		}
	}

	public int getFieldCount() {
		return 3;
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
	
}