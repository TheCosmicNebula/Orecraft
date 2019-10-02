package com.zeher.orecraft.transfer.core.tileentity;

import com.zeher.zeherlib.api.connection.Connection;
import com.zeher.zeherlib.api.connection.ConnectionType;
import com.zeher.zeherlib.transfer.IItemPipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityItemPipeClear extends TileEntity implements ITickable, IItemPipe {

	public EnumFacing side;

	public int up;
	public int down;
	public int north;
	public int south;
	public int east;
	public int west;

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

	public ConnectionType.PIPE.ITEM getItemConnectionState(EnumFacing side) {
		TileEntity tile_offset = world.getTileEntity(this.pos.offset(side));
		IBlockState state_offset = this.world.getBlockState(this.pos.offset(side));
		
		
		return Connection.PIPE.TILEENTITY.getItemConnections(this, side, tile_offset, state_offset, this.getSide(side));
	}

	public void readFromNBT(NBTTagCompound compound) {
		this.up = compound.getInteger("up");
		super.readFromNBT(compound);

	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("up", this.up);
		return super.writeToNBT(compound);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
