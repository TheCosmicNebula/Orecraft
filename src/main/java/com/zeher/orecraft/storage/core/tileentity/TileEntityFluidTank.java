package com.zeher.orecraft.storage.core.tileentity;

import com.zeher.orecraft.storage.core.util.StorageUtil;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.transfer.FluidTransfer;
import com.zeher.zeherlib.storage.IModFluidTank;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityFluidTank extends TileEntity implements IFluidHandler, ITickable, IModFluidTank {

	public FluidTank tank = new FluidTank(16000);
	private boolean needsUpdate = false;
	private int updateTimer = 0;

	public int fill_level;

	public int up;
	public int down;
	public int north;
	public int south;
	public int east;
	public int west;
	
	public int output_rate = Reference.VALUE.FLUIDSTORAGE.FLUIDTANK_OUTPUT;

	public TileEntityFluidTank() {
	}

	@Override
	public void update() {
		
		if (this.tank.getFluidAmount() > 0 && this != null) {
			FluidTransfer.OUTPUT.TANK.outputFluid(this.world, this.pos);
		}
	}

	public int getFillLevel() {
		return this.fill_level;
	}

	public void setFillLevel(int set) {
		this.fill_level = set;
	}

	public void updateFillLevel() {
		this.fill_level = this.getCurrentStoredAmount() / 1000;
	}

	public TileEntityFluidTank(BlockPos pos) {
		this.world.getTileEntity(pos);
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

	public int getCapacity() {
		StorageUtil.syncBlockAndRerender(world, pos);
		return this.tank.getCapacity();
	}

	public int fill(FluidStack resource, boolean doFill) {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(world, pos);
		needsUpdate = true;
		return this.tank.fill(resource, doFill);
	}

	public FluidStack drain(FluidStack resource, boolean doDrain) {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(world, pos);
		return this.tank.drain(resource.amount, doDrain);
	}

	public FluidStack drain(int maxDrain, boolean doDrain) {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(world, pos);
		needsUpdate = true;
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

	public float getAdjustedVolume() {
		float amount = tank.getFluidAmount();
		float capacity = tank.getCapacity();
		float volume = (amount / capacity) * 0.8F;
		return volume;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		tank.readFromNBT(tag);

		tag.setInteger("DirUp", this.up);
		tag.setInteger("DirDown", this.down);
		tag.setInteger("DirNorth", this.north);
		tag.setInteger("DirSouth", this.south);
		tag.setInteger("DirEast", this.east);
		tag.setInteger("DirWest", this.west);

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tank.writeToNBT(tag);

		this.up = tag.getInteger("DirUp");
		this.down = tag.getInteger("DirDown");
		this.north = tag.getInteger("DirNorth");
		this.south = tag.getInteger("DirSouth");
		this.east = tag.getInteger("DirEast");
		this.west = tag.getInteger("DirWest");

		return super.writeToNBT(tag);
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

	public void cycleSide(String side) {
		if (side.equals("up")) {
			int i = getSide("up");
			i = i + 1;
			if (i > 2) {
				i = 0;
			}
			up = i;
		}
		if (side.equals("down")) {
			int i = getSide("down");
			i = i + 1;
			if (i > 2) {
				i = 0;
			}
			down = i;
		}
		if (side.equals("east")) {
			int i = getSide("east");
			i = i + 1;
			if (i > 2) {
				i = 0;
			}
			east = i;
		}
		if (side.equals("west")) {
			int i = getSide("west");
			i = i + 1;
			if (i > 2) {
				i = 0;
			}
			west = i;
		}
		if (side.equals("north")) {
			int i = getSide("north");
			i = i + 1;
			if (i > 2) {
				i = 0;
			}
			north = i;
		}
		if (side.equals("south")) {
			int i = getSide("south");
			i = i + 1;
			if (i > 2) {
				i = 0;
			}
			south = i;
		}
		Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(),
				pos.getX(), pos.getY(), pos.getZ());
		world.markBlockRangeForRenderUpdate(pos, pos);
		markDirty();
	}

	public void setSideNBT(String str, int value) {
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
		markDirty();
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
		}
		return 0;
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

	@Override
	public FluidTank getTank() {
		return this.tank;
	}

	@Override
	public int getOutputRate() {
		return output_rate;
	}

}