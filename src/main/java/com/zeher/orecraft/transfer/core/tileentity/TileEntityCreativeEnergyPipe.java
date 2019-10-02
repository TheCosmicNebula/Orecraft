package com.zeher.orecraft.transfer.core.tileentity;

import com.zeher.orecraft.transfer.core.block.BlockPoweredEnergyPipe;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.connection.Connection;
import com.zeher.zeherlib.api.connection.ConnectionType;
import com.zeher.zeherlib.api.connection.EnumSide;
import com.zeher.zeherlib.api.transfer.EnergyTransfer;
import com.zeher.zeherlib.api.value.ItemPowerValues;
import com.zeher.zeherlib.transfer.IEnergyPipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityCreativeEnergyPipe extends TileEntity implements ITickable, IInventory, IEnergyPipe {

	private NonNullList<ItemStack> pipe_stacks = NonNullList.<ItemStack>withSize(6, ItemStack.EMPTY);

	public int stored;
	public int capacity = Reference.VALUE.ENERGYPIPE.CREATIVE_CAPACITY;
	public int transfer_rate = Reference.VALUE.ENERGYPIPE.CREATIVE_TRANSFER;
	public EnumFacing last_from;

	private EnumSide[] side_array = EnumSide.getDefaultArray();
	
	@Override
	public void update() {
		IBlockState state = world.getBlockState(this.pos);
		Block block = state.getBlock();
		
		if (block instanceof BlockPoweredEnergyPipe) {
			for (EnumFacing c : EnumFacing.VALUES) {
				boolean is = ((BlockPoweredEnergyPipe) block).getSide(c, this.world, this.pos);
				
				if (is) {
					Block block_is = this.world.getBlockState(this.pos.offset(c)).getBlock();
					this.setStack(c, new ItemStack(block_is));
				} else {
					this.setStack(c, ItemStack.EMPTY);
				}
			}
		}
		
		if (this.stored > 0 && this != null) {
			EnergyTransfer.OUTPUT.ENERGYPIPE.outputEnergy(this.world, this.pos, this.last_from);
		}
	}

	public int getStored() {
		return this.stored;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public int getTransferRate() {
		return this.transfer_rate;
	}

	public void addStored(int add) {
		this.stored += add;
		this.markDirty();
	}

	public void addStored(int add, EnumFacing side_from) {
		this.stored += add;
		this.last_from = side_from;
		this.markDirty();
	}
	
	public void setSide(EnumFacing facing, EnumSide value) {
		for (EnumFacing c : EnumFacing.VALUES) {
			this.side_array[facing.getIndex()] = value;
		}
	}

	public EnumSide getSide(EnumFacing facing) {
		for (EnumFacing c : EnumFacing.VALUES) {
			return this.side_array[facing.getIndex()];
		}
		return null;
	}
	
	public void setStack(EnumFacing facing, ItemStack stack) {
		switch(facing) {
		case UP:
			pipe_stacks.set(0, stack);
			break;
		case DOWN:
			pipe_stacks.set(1, stack);
			break;
		case NORTH:
			pipe_stacks.set(4, stack);
			break;
		case SOUTH:
			pipe_stacks.set(5, stack);
			break;
		case EAST:
			pipe_stacks.set(2, stack);
			break;
		case WEST:
			pipe_stacks.set(3, stack);
			break;
			default:
		}
	}
	
	public EnumFacing getLastFrom() {
		return this.last_from;
	}
	
	public void minusStored(int value) {
		this.stored -= value;
	}
	
	@Override
	public void cycleside(EnumFacing facing) {
		EnumSide next = this.side_array[facing.getIndex()].getNext();
		this.setSide(facing, next);
		
		Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
		world.markBlockRangeForRenderUpdate(pos, pos);
		markDirty();
	}

	public int getPowerConnectionState(EnumFacing side, int x, int z, int y) {
		return 0;
	}

	public ConnectionType.PIPE.ENERGY getPipeConnectionState(EnumFacing side) {
		TileEntity tile_offset = world.getTileEntity(this.pos.offset(side));
		IBlockState state_offset = this.world.getBlockState(this.pos.offset(side));
		
		return Connection.PIPE.TILEENTITY.getEnergyConnections(side, tile_offset, state_offset, this.getSide(side));
	}

	public void readFromNBT(NBTTagCompound compound) {
		this.setSide(EnumFacing.UP, EnumSide.getSideFromIndex(compound.getInteger("up")));
		this.stored = compound.getInteger("stored");
		super.readFromNBT(compound);

	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("up", this.side_array[0].getIndex());
		compound.setInteger("stored", this.stored);
		return super.writeToNBT(compound);
	}

	public String getName() {
		return "";
	}

	public boolean hasCustomName() {
		return false;
	}

	public int getSizeInventory() {
		return 0;
	}

	public boolean isEmpty() {
		return false;
	}

	public ItemStack getStackInSlot(int index) {
		return this.pipe_stacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.pipe_stacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.pipe_stacks, index);
	}

	public boolean isItemPower(int index) {
		return ItemPowerValues.getItemPower(this.pipe_stacks.get(index).getItem()) > 0;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.pipe_stacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.pipe_stacks.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUsableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(this.pos) <= 64.0D;
	}

	public void openInventory(EntityPlayer player) { }

	public void closeInventory(EntityPlayer player) { }

	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	public int getFieldCount() {
		return 0;
	}

	public void clear() { }
	
	@Override
	public EnumSide[] getSideArray() {
		return this.side_array;
	}

	@Override
	public void setSideArray(EnumSide[] array_from) {
		this.side_array = array_from;
	}

}