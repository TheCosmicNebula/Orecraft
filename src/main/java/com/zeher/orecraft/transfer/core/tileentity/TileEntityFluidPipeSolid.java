package com.zeher.orecraft.transfer.core.tileentity;

import java.util.Arrays;
import java.util.List;

import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.connection.Connection;
import com.zeher.zeherlib.api.connection.ConnectionType;
import com.zeher.zeherlib.api.transfer.FluidTransfer;
import com.zeher.zeherlib.transfer.IFluidPipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityFluidPipeSolid extends TileEntity implements IFluidHandler, ITickable, IFluidPipe {

	public FluidTank tank = new FluidTank(Reference.VALUE.FLUIDPIPE.FLUIDPIPE_STORED);
	public EnumFacing side;
	private static List<Integer> _connectionWhitelist = Arrays.asList(new Integer[0]);
	private static List<Integer> _connectionBlackList;

	EnumFacing last_from;
	
	public int up;
	public int down;
	public int north;
	public int south;
	public int east;
	public int west;
	
	public int output_rate = Reference.VALUE.FLUIDPIPE.FLUIDPIPE_OUTPUT;

	public void setSide(EnumFacing facing, int value) {
		switch (facing) {
			case UP:
				this.up = value;
			case DOWN:
				this.down = value;
			case NORTH:
				this.north = value;
			case SOUTH:
				this.south = value;
			case EAST:
				this.east = value;
			case WEST:
				this.west = value;
		}
	}

	public int getSide(EnumFacing facing) {
		switch (facing) {
			case UP:
				return this.up;
			case DOWN:
				return this.down;
			case NORTH:
				return this.north;
			case SOUTH:
				return this.south;
			case EAST:
				return this.east;
			case WEST:
				return this.west;
			default:
				return -1;
		}
	}
	
	public void setSide(String str, int value) {
		if (str == "up") {
			this.up = value;
		}
		if (str == "down") {
			this.down = value;
		}
		if (str == "north") {
			this.north = value;
		}
		if (str == "south") {
			this.south = value;
		}
		if (str == "east") {
			this.east = value;
		}
		if (str == "west") {
			this.west = value;
		}
	}
	
	public int getSide(String str) {
		if (str == "up") {
			return this.up;
		}
		if (str == "down") {
			return this.down;
		}
		if (str == "north") {
			return this.north;
		}
		if (str == "south") {
			return this.south;
		}
		if (str == "east") {
			return this.east;
		}
		if (str == "west") {
			return this.west;
		} else {
			return 0;
		}
	}

	public ConnectionType.PIPE.FLUID getFluidConnectionState(EnumFacing side) {
		IFluidPipe tile = (IFluidPipe) world.getTileEntity(pos);
		TileEntity tile_offset = world.getTileEntity(this.pos.offset(side));
		IBlockState state_offset = this.world.getBlockState(this.pos.offset(side));
		
		
		return Connection.PIPE.TILEENTITY.getFluidConnections(this, side, tile_offset, state_offset, this.getSide(side), this.tank);
	}

	public void readFromNBT(NBTTagCompound compound) {
		this.up = compound.getInteger("up");
		super.readFromNBT(compound);

	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("up", this.up);
		return super.writeToNBT(compound);
	}

	public int fill(FluidStack resource, boolean doFill, EnumFacing side_from) {
		this.last_from = side_from;
		return this.fill(resource, doFill);
	}
	
	public int fill(FluidStack resource, boolean doFill) {
		return this.tank.fill(resource, doFill);
	}
	

	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return this.tank.drain(resource.amount, doDrain);
	}

	public FluidStack drain(int maxDrain, boolean doDrain) {
		return this.tank.drain(maxDrain, doDrain);
	}

	public boolean canFill(EnumFacing from, Fluid fluid) {
		return true;
	}

	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return true;
	}

	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		return new FluidTankInfo[] { this.tank.getInfo() };
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return this.tank.getTankProperties();
	}

	public FluidTank getTank(){
		return this.tank;
	}

	@Override
	public void update() { 
		if (this.tank.getFluidAmount() > 0 && this != null) {
			FluidTransfer.OUTPUT.FLUIDPIPE.outputFluid(this.world, this.pos, this.last_from);
		}
	}

	@Override
	public int getOutputRate() {
		return output_rate;
	}

	@Override
	public boolean isFluidEmpty() {
		if (this.tank.getFluidAmount() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public EnumFacing getLastFrom() {
		return last_from;
	}
	
}
