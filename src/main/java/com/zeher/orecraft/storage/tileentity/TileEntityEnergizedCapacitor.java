package com.zeher.orecraft.storage.tileentity;

import javax.annotation.Nullable;

import com.zeher.orecraft.storage.block.BlockEnergizedCapacitor;
import com.zeher.trzlib.api.TRZBlockPos;
import com.zeher.trzlib.api.value.TRZCapacitorValues;
import com.zeher.trzlib.api.value.TRZItemPowerValues;
import com.zeher.trzlib.machine.TRZTileEntityMachine;
import com.zeher.trzlib.storage.TRZICapacitor;
import com.zeher.trzlib.storage.TRZTileEntityCapacitor;
import com.zeher.trzlib.storage.item.TRZItemStorage;
import com.zeher.trzlib.transfer.TRZBlockEnergyPipe;
import com.zeher.trzlib.transfer.TRZTileEntityEnergyPipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public class TileEntityEnergizedCapacitor extends TRZTileEntityCapacitor implements ISidedInventory, IInventory, ITickable, TRZICapacitor {

	private String custom_name;
	private NonNullList<ItemStack> capacitorItemStacks = NonNullList.<ItemStack>withSize(13, ItemStack.EMPTY);
	private static final int[] slots_top = { 1 };
	private static final int[] slots_bottom = { 2, 1 };
	private static final int[] slots_sides = { 1 };
	
	public int stored;
	public int capacity = TRZCapacitorValues.enderStorage();
	public int input_rate = TRZCapacitorValues.enderInput();
	public int output_rate = TRZCapacitorValues.enderOutput();
	
	public int up;
	public int down;
	public int north;
	public int south;
	public int east;
	public int west;
	
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
	public int getOutputRate() {
		return this.output_rate;
	}
	
	public boolean hasStored(){
		return this.stored > 0;
	}
	
	@Override
	public int getSizeInventory() {
		return this.capacitorItemStacks.size();
	}
	
	public void cycleSide(String side){
		if(side.equals("up")){
			int i = getSide("up");
			i = i+1;
			if(i > 2){
				i= 0;
			}
			up = i;
		}
		if(side.equals("down")){
			int i = getSide("down");
			i = i+1;
			if(i > 2){
				i= 0;
			}
			down = i;
		}
		if(side.equals("east")){
			int i = getSide("east");
			i = i+1;
			if(i > 2){
				i= 0;
			}
			east = i;
		}
		if(side.equals("west")){
			int i = getSide("west");
			i = i+1;
			if(i > 2){
				i= 0;
			}
			west = i;
		}
		if(side.equals("north")){
			int i = getSide("north");
			i = i+1;
			if(i > 2){
				i= 0;
			}
			north = i;
		}
		if(side.equals("south")){
			int i = getSide("south");
			i = i+1;
			if(i > 2){
				i= 0;
			}
			south = i;
		}
		Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
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

	@Override
	public void update() {
		boolean flag = this.stored > 0;
		boolean flag1 = false;
		ItemStack stackInput = capacitorItemStacks.get(1);
		ItemStack stackOutput = capacitorItemStacks.get(0);
		if (!this.world.isRemote) {
			
			for(int x = 8; x < 13; x++){
				if ((isItemPowerStorage(x)) && !(this.capacitorItemStacks.get(x).isEmpty()) && (this.stored > 0)) {
					if (this.capacitorItemStacks.get(x).getItemDamage() > 0) {
						this.capacitorItemStacks.get(x).setItemDamage(this.capacitorItemStacks.get(x).getItemDamage() - 1);
						this.stored -= TRZItemPowerValues.getItemPower(this.capacitorItemStacks.get(x).getItem());
					}
				}
			}
			if ((isItemPower(1))
					&& (this.stored <= this.capacity - TRZItemPowerValues.getItemPower(stackInput.getItem()))) {
				if (!stackInput.isItemStackDamageable()) {
					this.stored += TRZItemPowerValues.getItemPower(stackInput.getItem());

					flag1 = true;
					if (!(stackInput.isEmpty())) {
						stackInput.setCount(stackInput.getCount() - 1);
						if (stackInput.isEmpty()) {
							this.capacitorItemStacks.set(1, stackInput.getItem().getContainerItem(stackInput));
						}
					}
				} else if (stackInput.getItemDamage() < stackInput.getMaxDamage()) {
					this.stored += TRZItemPowerValues.getItemPower(stackInput.getItem());
					this.capacitorItemStacks.set(1,
							new ItemStack(stackInput.getItem(), stackInput.getCount(), stackInput.getItemDamage() + 1));
				}
			}
			if (flag1) {
				this.update();
				this.markDirty();
			}
		}

		IBlockState state = world.getBlockState(this.pos);
		Block block = state.getBlock();
		if (block instanceof BlockEnergizedCapacitor) {
			boolean up = ((BlockEnergizedCapacitor) block).getSide(EnumFacing.UP, world, this.pos);
			boolean down = ((BlockEnergizedCapacitor) block).getSide(EnumFacing.DOWN, world, pos);
			boolean east = ((BlockEnergizedCapacitor) block).getSide(EnumFacing.EAST, world, this.pos);
			boolean west = ((BlockEnergizedCapacitor) block).getSide(EnumFacing.WEST, world, pos);
			boolean north = ((BlockEnergizedCapacitor) block).getSide(EnumFacing.NORTH, world, this.pos);
			boolean south = ((BlockEnergizedCapacitor) block).getSide(EnumFacing.SOUTH, world, pos);

			if (up) {
				TRZBlockPos bp = new TRZBlockPos(pos);
				bp.orientation = EnumFacing.UP;
				bp.moveForwards(1);
				Block blockUp = world.getBlockState(bp.pos).getBlock();

				if (this.up == 1) {
					if (blockUp != null) {
						capacitorItemStacks.set(2, new ItemStack(blockUp));
					}
				}
				if (this.up == 1) {
					capacitorItemStacks.set(2, ItemStack.EMPTY);
				}
				if (this.up == 2) {
					capacitorItemStacks.set(2, new ItemStack(blockUp));
				}
			} else {
				capacitorItemStacks.set(2, ItemStack.EMPTY);
			}

			if (down) {
				TRZBlockPos bp = new TRZBlockPos(pos);
				bp.orientation = EnumFacing.DOWN;
				bp.moveForwards(1);
				Block blockDown = world.getBlockState(bp.pos).getBlock();

				if (this.down == 0) {
					if (blockDown instanceof TRZBlockEnergyPipe) {
						capacitorItemStacks.set(3, new ItemStack(blockDown));
					} else {
						capacitorItemStacks.set(3, ItemStack.EMPTY);
					}
				}
				if (this.down == 1) {
					capacitorItemStacks.set(3, ItemStack.EMPTY);
				}
				if (this.down == 2) {
					capacitorItemStacks.set(3, new ItemStack(blockDown));
				}
			} else {
				capacitorItemStacks.set(3, ItemStack.EMPTY);
			}

			if (east) {
				TRZBlockPos bp = new TRZBlockPos(pos);
				bp.orientation = EnumFacing.EAST;
				bp.moveForwards(1);
				Block blockEast = world.getBlockState(bp.pos).getBlock();

				if (this.east == 0) {
					if (blockEast instanceof TRZBlockEnergyPipe) {
						capacitorItemStacks.set(4, new ItemStack(blockEast));
					} else {
						capacitorItemStacks.set(4, ItemStack.EMPTY);
					}
				}
				if (this.east == 1) {
					capacitorItemStacks.set(4, ItemStack.EMPTY);
				}
				if (this.east == 2) {
					capacitorItemStacks.set(4, new ItemStack(blockEast));
				}
			} else {
				capacitorItemStacks.set(4, ItemStack.EMPTY);
			}

			if (west) {
				TRZBlockPos bp = new TRZBlockPos(pos);
				bp.orientation = EnumFacing.WEST;
				bp.moveForwards(1);
				Block blockWest = world.getBlockState(bp.pos).getBlock();

				if (this.west == 0) {
					if (blockWest instanceof TRZBlockEnergyPipe) {
						capacitorItemStacks.set(5, new ItemStack(blockWest));
					} else {
						capacitorItemStacks.set(5, ItemStack.EMPTY);
					}
				}
				if (this.west == 1) {
					capacitorItemStacks.set(5, ItemStack.EMPTY);
				}
				if (this.west == 2) {
					capacitorItemStacks.set(5, new ItemStack(blockWest));
				} 
			} else {
				capacitorItemStacks.set(5, ItemStack.EMPTY);
			}

			if (north) {
				TRZBlockPos bp = new TRZBlockPos(pos);
				bp.orientation = EnumFacing.NORTH;
				bp.moveForwards(1);
				Block blockNorth = world.getBlockState(bp.pos).getBlock();

				if (this.north == 0) {
					if (blockNorth instanceof TRZBlockEnergyPipe) {
						capacitorItemStacks.set(6, new ItemStack(blockNorth));
					} else {
						capacitorItemStacks.set(6, ItemStack.EMPTY);
					}
				}
				if (this.north == 1) {
					capacitorItemStacks.set(6, ItemStack.EMPTY);
				}
				if (this.north == 2) {
					capacitorItemStacks.set(6, new ItemStack(blockNorth));
				}
			} else {
				capacitorItemStacks.set(6, ItemStack.EMPTY);
			}

			if (south) {
				TRZBlockPos bp = new TRZBlockPos(pos);
				bp.orientation = EnumFacing.SOUTH;
				bp.moveForwards(1);
				Block blockSouth = world.getBlockState(bp.pos).getBlock();

				if (this.south == 0) {
					if (blockSouth instanceof TRZBlockEnergyPipe) {
						capacitorItemStacks.set(7, new ItemStack(blockSouth));
					} else {
						capacitorItemStacks.set(7, ItemStack.EMPTY);
					}
				}
				if (this.south == 1) {
					capacitorItemStacks.set(7, ItemStack.EMPTY);
				}
				if (this.south == 2) {
					capacitorItemStacks.set(7, new ItemStack(blockSouth));
				} 
			} else {
				capacitorItemStacks.set(7, ItemStack.EMPTY);
			}
		}
		
		if (this.stored > 0) {
			IBlockState this_state = world.getBlockState(this.pos);
			Block this_block = this_state.getBlock();
			TileEntity this_tile = world.getTileEntity(this.pos);

			if (this_tile != null) {
				TileEntity tile_up = world.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY() + 1, this.pos.getZ()));
				if (tile_up != null && this.up == 2) {
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
					}
					if (tile_up instanceof TRZTileEntityMachine) {
						int machine_stored = ((TRZTileEntityMachine) tile_up).getStored();
						int machine_capacity = ((TRZTileEntityMachine) tile_up).getCapacity();
						int machine_input_rate = ((TRZTileEntityMachine) tile_up).getInputRate();
						int gap_stored = machine_capacity - machine_stored;
						
						if (machine_stored <= machine_capacity) {
							if(machine_input_rate <= this.output_rate){
								if (gap_stored >= machine_input_rate) {
									this.stored -= machine_input_rate;
									((TRZTileEntityMachine) tile_up).addStored(machine_input_rate);
								}
								else if(gap_stored < machine_input_rate){
									this.stored -= gap_stored;
									((TRZTileEntityMachine) tile_up).addStored(gap_stored);
								}
							} else if(machine_input_rate > this.output_rate){
								if(gap_stored >= this.output_rate){
									this.stored -= this.output_rate;
									((TRZTileEntityMachine) tile_up).addStored(this.output_rate);
								}
							}
						}
					}
				}
				
				TileEntity tile_down = world.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY() - 1, this.pos.getZ()));
				if (tile_down != null && this.down == 2) {
					if (tile_down instanceof TRZTileEntityEnergyPipe) {
						int pipe_stored = ((TRZTileEntityEnergyPipe) tile_down).getStored();
						int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_down).getCapacity();
						int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_down).getTransferRate();
						int gap_stored = pipe_capacity - pipe_stored;
						
						if(pipe_stored <= pipe_capacity){
							if(pipe_transfer_rate <= this.output_rate){
								if(gap_stored >= pipe_transfer_rate){
									this.stored -= pipe_transfer_rate;
									((TRZTileEntityEnergyPipe) tile_down).addStored(pipe_transfer_rate, EnumFacing.DOWN);
								}
								else if(gap_stored < pipe_transfer_rate){
									this.stored -= gap_stored;
									((TRZTileEntityEnergyPipe) tile_down).addStored(gap_stored, EnumFacing.DOWN);
								}
							} else if(pipe_transfer_rate > this.output_rate){
								if(gap_stored >= pipe_transfer_rate){
									this.stored -= this.output_rate;
									((TRZTileEntityEnergyPipe) tile_down).addStored(this.output_rate, EnumFacing.DOWN);
								}
							}
						}
					}
					if (tile_down instanceof TRZTileEntityMachine) {
						int machine_stored = ((TRZTileEntityMachine) tile_down).getStored();
						int machine_capacity = ((TRZTileEntityMachine) tile_down).getCapacity();
						int machine_input_rate = ((TRZTileEntityMachine) tile_down).getInputRate();
						int gap_stored = machine_capacity - machine_stored;
						
						if (machine_stored <= machine_capacity) {
							if(machine_input_rate <= this.output_rate){
								if (gap_stored >= machine_input_rate) {
									this.stored -= machine_input_rate;
									((TRZTileEntityMachine) tile_down).addStored(machine_input_rate);
								}
								else if(gap_stored < machine_input_rate){
									this.stored -= gap_stored;
									((TRZTileEntityMachine) tile_down).addStored(gap_stored);
								}
							} else if(machine_input_rate > this.output_rate){
								if(gap_stored >= this.output_rate){
									this.stored -= this.output_rate;
									((TRZTileEntityMachine) tile_down).addStored(this.output_rate);
								}
							}
						}
					}
				}
				
				TileEntity tile_north = world.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() - 1));
				if (tile_north != null && this.north == 2) {
					if (tile_north instanceof TRZTileEntityEnergyPipe) {
						int pipe_stored = ((TRZTileEntityEnergyPipe) tile_north).getStored();
						int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_north).getCapacity();
						int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_north).getTransferRate();
						int gap_stored = pipe_capacity - pipe_stored;
						
						if(pipe_stored <= pipe_capacity){
							if(pipe_transfer_rate <= this.output_rate){
								if(gap_stored >= pipe_transfer_rate){
									this.stored -= pipe_transfer_rate;
									((TRZTileEntityEnergyPipe) tile_north).addStored(pipe_transfer_rate, EnumFacing.NORTH);
								}
								else if(gap_stored < pipe_transfer_rate){
									this.stored -= gap_stored;
									((TRZTileEntityEnergyPipe) tile_north).addStored(gap_stored, EnumFacing.NORTH);
								}
							} else if(pipe_transfer_rate > this.output_rate){
								if(gap_stored >= pipe_transfer_rate){
									this.stored -= this.output_rate;
									((TRZTileEntityEnergyPipe) tile_north).addStored(this.output_rate, EnumFacing.NORTH);
								}
							}
						}
					}
					if (tile_north instanceof TRZTileEntityMachine) {
						int machine_stored = ((TRZTileEntityMachine) tile_north).getStored();
						int machine_capacity = ((TRZTileEntityMachine) tile_north).getCapacity();
						int machine_input_rate = ((TRZTileEntityMachine) tile_north).getInputRate();
						int gap_stored = machine_capacity - machine_stored;
						
						if (machine_stored <= machine_capacity) {
							if(machine_input_rate <= this.output_rate){
								if (gap_stored >= machine_input_rate) {
									this.stored -= machine_input_rate;
									((TRZTileEntityMachine) tile_north).addStored(machine_input_rate);
								}
								else if(gap_stored < machine_input_rate){
									this.stored -= gap_stored;
									((TRZTileEntityMachine) tile_north).addStored(gap_stored);
								}
							} else if(machine_input_rate > this.output_rate){
								if(gap_stored >= this.output_rate){
									this.stored -= this.output_rate;
									((TRZTileEntityMachine) tile_north).addStored(this.output_rate);
								}
							}
						}
					}
				}
				
				TileEntity tile_south = world.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() + 1));
				if (tile_south != null && this.south == 2) {
					if (tile_south instanceof TRZTileEntityEnergyPipe) {
						int pipe_stored = ((TRZTileEntityEnergyPipe) tile_south).getStored();
						int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_south).getCapacity();
						int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_south).getTransferRate();
						int gap_stored = pipe_capacity - pipe_stored;
						
						if(pipe_stored <= pipe_capacity){
							if(pipe_transfer_rate <= this.output_rate){
								if(gap_stored >= pipe_transfer_rate){
									this.stored -= pipe_transfer_rate;
									((TRZTileEntityEnergyPipe) tile_south).addStored(pipe_transfer_rate, EnumFacing.SOUTH);
								}
								else if(gap_stored < pipe_transfer_rate){
									this.stored -= gap_stored;
									((TRZTileEntityEnergyPipe) tile_south).addStored(gap_stored, EnumFacing.SOUTH);
								}
							} else if(pipe_transfer_rate > this.output_rate){
								if(gap_stored >= pipe_transfer_rate){
									this.stored -= this.output_rate;
									((TRZTileEntityEnergyPipe) tile_south).addStored(this.output_rate, EnumFacing.SOUTH);
								}
							}
						}
					}
					if (tile_south instanceof TRZTileEntityMachine) {
						int machine_stored = ((TRZTileEntityMachine) tile_south).getStored();
						int machine_capacity = ((TRZTileEntityMachine) tile_south).getCapacity();
						int machine_input_rate = ((TRZTileEntityMachine) tile_south).getInputRate();
						int gap_stored = machine_capacity - machine_stored;
						
						if (machine_stored <= machine_capacity) {
							if(machine_input_rate <= this.output_rate){
								if (gap_stored >= machine_input_rate) {
									this.stored -= machine_input_rate;
									((TRZTileEntityMachine) tile_south).addStored(machine_input_rate);
								}
								else if(gap_stored < machine_input_rate){
									this.stored -= gap_stored;
									((TRZTileEntityMachine) tile_south).addStored(gap_stored);
								}
							} else if(machine_input_rate > this.output_rate){
								if(gap_stored >= this.output_rate){
									this.stored -= this.output_rate;
									((TRZTileEntityMachine) tile_south).addStored(this.output_rate);
								}
							}
						}
					}
				}
				
				TileEntity tile_west = world.getTileEntity(new BlockPos(this.pos.getX() - 1, this.pos.getY(), this.pos.getZ()));
				if (tile_west != null && this.west == 2) {
					if (tile_west instanceof TRZTileEntityEnergyPipe) {
						int pipe_stored = ((TRZTileEntityEnergyPipe) tile_west).getStored();
						int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_west).getCapacity();
						int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_west).getTransferRate();
						int gap_stored = pipe_capacity - pipe_stored;
						
						if(pipe_stored <= pipe_capacity){
							if(pipe_transfer_rate <= this.output_rate){
								if(gap_stored >= pipe_transfer_rate){
									this.stored -= pipe_transfer_rate;
									((TRZTileEntityEnergyPipe) tile_west).addStored(pipe_transfer_rate, EnumFacing.WEST);
								}
								else if(gap_stored < pipe_transfer_rate){
									this.stored -= gap_stored;
									((TRZTileEntityEnergyPipe) tile_west).addStored(gap_stored, EnumFacing.WEST);
								}
							} else if(pipe_transfer_rate > this.output_rate){
								if(gap_stored >= pipe_transfer_rate){
									this.stored -= this.output_rate;
									((TRZTileEntityEnergyPipe) tile_west).addStored(this.output_rate, EnumFacing.WEST);
								}
							}
						}
					}
					if (tile_west instanceof TRZTileEntityMachine) {
						int machine_stored = ((TRZTileEntityMachine) tile_west).getStored();
						int machine_capacity = ((TRZTileEntityMachine) tile_west).getCapacity();
						int machine_input_rate = ((TRZTileEntityMachine) tile_west).getInputRate();
						int gap_stored = machine_capacity - machine_stored;
						
						if (machine_stored <= machine_capacity) {
							if(machine_input_rate <= this.output_rate){
								if (gap_stored >= machine_input_rate) {
									this.stored -= machine_input_rate;
									((TRZTileEntityMachine) tile_west).addStored(machine_input_rate);
								}
								else if(gap_stored < machine_input_rate){
									this.stored -= gap_stored;
									((TRZTileEntityMachine) tile_west).addStored(gap_stored);
								}
							} else if(machine_input_rate > this.output_rate){
								if(gap_stored >= this.output_rate){
									this.stored -= this.output_rate;
									((TRZTileEntityMachine) tile_west).addStored(this.output_rate);
								}
							}
						}
					}
				}
				
				TileEntity tile_east = world.getTileEntity(new BlockPos(this.pos.getX() + 1, this.pos.getY(), this.pos.getZ()));
				if (tile_east != null && this.east == 2) {
					if (tile_east instanceof TRZTileEntityEnergyPipe) {
						int pipe_stored = ((TRZTileEntityEnergyPipe) tile_east).getStored();
						int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_east).getCapacity();
						int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_east).getTransferRate();
						int gap_stored = pipe_capacity - pipe_stored;
						
						if(pipe_stored <= pipe_capacity){
							if(pipe_transfer_rate <= this.output_rate){
								if(gap_stored >= pipe_transfer_rate){
									this.stored -= pipe_transfer_rate;
									((TRZTileEntityEnergyPipe) tile_east).addStored(pipe_transfer_rate, EnumFacing.EAST);
								}
								else if(gap_stored < pipe_transfer_rate){
									this.stored -= gap_stored;
									((TRZTileEntityEnergyPipe) tile_east).addStored(gap_stored, EnumFacing.EAST);
								}
							} else if(pipe_transfer_rate > this.output_rate){
								if(gap_stored >= pipe_transfer_rate){
									this.stored -= this.output_rate;
									((TRZTileEntityEnergyPipe) tile_east).addStored(this.output_rate, EnumFacing.EAST);
								}
							}
						}
					}
					if (tile_east instanceof TRZTileEntityMachine) {
						int machine_stored = ((TRZTileEntityMachine) tile_east).getStored();
						int machine_capacity = ((TRZTileEntityMachine) tile_east).getCapacity();
						int machine_input_rate = ((TRZTileEntityMachine) tile_east).getInputRate();
						int gap_stored = machine_capacity - machine_stored;
						
						if (machine_stored <= machine_capacity) {
							if(machine_input_rate <= this.output_rate){
								if (gap_stored >= machine_input_rate) {
									this.stored -= machine_input_rate;
									((TRZTileEntityMachine) tile_east).addStored(machine_input_rate);
								}
								else if(gap_stored < machine_input_rate){
									this.stored -= gap_stored;
									((TRZTileEntityMachine) tile_east).addStored(gap_stored);
								}
							} else if(machine_input_rate > this.output_rate){
								if(gap_stored >= this.output_rate){
									this.stored -= this.output_rate;
									((TRZTileEntityMachine) tile_east).addStored(this.output_rate);
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void addStored(int add) {
		this.stored += add;
	}

	private boolean isItemPowerStorage(int index) {
		if (!(this.capacitorItemStacks.get(index).isEmpty())) {
			return this.capacitorItemStacks.get(index).getItem() instanceof TRZItemStorage;
		}
		return false;
	}

	public int getPowerReaminingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}

	public ItemStack getStackInSlot(int i) {
		return this.capacitorItemStacks.get(i);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.capacitorItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.capacitorItemStacks, index);
	}

	public boolean isItemPower(int index) {
		return TRZItemPowerValues.getItemPower(this.capacitorItemStacks.get(index).getItem()) > 0;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.capacitorItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack)
				&& ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.capacitorItemStacks.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.custom_name : "Basic Capacitor";
	}

	public boolean hasCustomName() {
		return (this.custom_name != null) && (this.custom_name.length() > 0);
	}

	public void setGuiDisplayName(String par1Str) {
		this.custom_name = par1Str;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(this.pos) <= 64.0D;
	}

	public void openInventory() {
	}

	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	public void readFromNBT(NBTTagCompound compound) {
		this.capacitorItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.capacitorItemStacks);
		this.stored = compound.getInteger("power");
		
		//System.out.println("READ (NBT) - " + compound.getInteger("DirUp") + " " + compound.getInteger("DirDown") + " " + compound.getInteger("DirNorth") + " " + compound.getInteger("DirSouth") + " " + compound.getInteger("DirEast") + " " + compound.getInteger("DirWest"));
		this.up = compound.getInteger("DirUp");
		this.down = compound.getInteger("DirDown");
		this.north = compound.getInteger("DirNorth");
		this.south = compound.getInteger("DirSouth");
		this.east = compound.getInteger("DirEast");
		this.west = compound.getInteger("DirWest");
		
		if (compound.hasKey("custom_name", 8)) {
			this.custom_name = compound.getString("custom_name");
		}
		super.readFromNBT(compound);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, this.capacitorItemStacks);
		compound.setInteger("power", this.stored);
		
		//System.out.println("WRITE (NBT)" + up + " " + down + " " + north + " " + south + " " + east + " " + west);
		compound.setInteger("DirUp", this.up);
		compound.setInteger("DirDown", this.down);
		compound.setInteger("DirNorth", this.north);
		compound.setInteger("DirSouth", this.south);
		compound.setInteger("DirEast", this.east);
		compound.setInteger("DirWest", this.west);
		
		if (this.hasCustomName()) {
			compound.setString("custom_name", this.custom_name);
		}
		return super.writeToNBT(compound);
	}
	
	@Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("DirUp", this.up);
		tag.setInteger("DirDown", this.down);
		tag.setInteger("DirNorth", this.north);
		tag.setInteger("DirSouth", this.south);
		tag.setInteger("DirEast", this.east);
		tag.setInteger("DirWest", this.west);
		
		//System.out.println("Sending Packet - " + getSide("up") + " " + getSide("down") + " " + getSide("north") + " " + getSide("south") + " " + getSide("east") + " " + getSide("west"));
		writeToNBT(tag);
        return new SPacketUpdateTileEntity(pos, 0, tag);
    }

    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
    {
    	NBTTagCompound tag = pkt.getNbtCompound();
    	int a = tag.getInteger("DirUp");
    	int b = tag.getInteger("DirDown"); 
    	int c = tag.getInteger("DirNorth");
    	int d = tag.getInteger("DirSouth");
    	int e = tag.getInteger("DirEast");
    	int f = tag.getInteger("DirWest");
    	//System.out.println("Packet Received - " + a + " " + b + " " + c + " " + d + " " + e + " " + f);
    	this.up = a;
    	this.down = b;
    	this.north = c; 
    	this.south = d;
    	this.east = e;
    	this.west = f;
    }
    
    public void handleUpdateTag(NBTTagCompound tag)
    {
        this.readFromNBT(tag);
    }
    
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }
    
	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.capacitorItemStacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

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
			break;
		}
	}

	public int getFieldCount() {
		return 1;
	}

	@Override
	public void clear() {}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return side.UP != null ? slots_top : side.DOWN != null ? slots_bottom : slots_sides;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}
	
}
