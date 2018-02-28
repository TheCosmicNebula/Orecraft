package com.zeher.orecraft.transfer.tileentity.pipe;

import com.zeher.orecraft.transfer.block.pipe.BlockPoweredEnergyPipe;
import com.zeher.trzlib.api.TRZBlockPos;
import com.zeher.trzlib.api.connect.TRZEnergyPipeConnectionType;
import com.zeher.trzlib.api.connect.TRZPipeConnectionsList;
import com.zeher.trzlib.api.value.TRZEnergyPipeValues;
import com.zeher.trzlib.api.value.TRZItemPowerValues;
import com.zeher.trzlib.machine.TRZTileEntityMachine;
import com.zeher.trzlib.storage.TRZBlockCapacitor;
import com.zeher.trzlib.storage.TRZTileEntityCapacitor;
import com.zeher.trzlib.transfer.TRZBlockEnergyPipe;
import com.zeher.trzlib.transfer.TRZIEnergyPipe;
import com.zeher.trzlib.transfer.TRZTileEntityEnergyPipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public class TileEntityPoweredEnergyPipe extends TRZTileEntityEnergyPipe
		implements ITickable, IInventory, TRZIEnergyPipe {

	private NonNullList<ItemStack> itemStacks = NonNullList.<ItemStack>withSize(6, ItemStack.EMPTY);

	public int stored;
	public int capacity = TRZEnergyPipeValues.capacityBasic();
	public int transfer_rate = TRZEnergyPipeValues.transferRateBasic();
	public EnumFacing last_from;

	public int up;
	public int down;
	public int north;
	public int south;
	public int east;
	public int west;

	@Override
	public void update() {
		IBlockState state = world.getBlockState(this.pos);
		Block block = state.getBlock();
		if (block instanceof BlockPoweredEnergyPipe) {
			boolean up = ((BlockPoweredEnergyPipe) block).getSide(EnumFacing.UP, world, this.pos);
			boolean down = ((BlockPoweredEnergyPipe) block).getSide(EnumFacing.DOWN, world, pos);
			boolean east = ((BlockPoweredEnergyPipe) block).getSide(EnumFacing.EAST, world, this.pos);
			boolean west = ((BlockPoweredEnergyPipe) block).getSide(EnumFacing.WEST, world, pos);
			boolean north = ((BlockPoweredEnergyPipe) block).getSide(EnumFacing.NORTH, world, this.pos);
			boolean south = ((BlockPoweredEnergyPipe) block).getSide(EnumFacing.SOUTH, world, pos);

			if (up) {
				TRZBlockPos bp = new TRZBlockPos(pos);
				bp.orientation = EnumFacing.UP;
				bp.moveForwards(1);
				Block blockUp = world.getBlockState(bp.pos).getBlock();
				itemStacks.set(0, new ItemStack(blockUp));
			} else {
				itemStacks.set(0, ItemStack.EMPTY);
			}
			if (down) {
				TRZBlockPos bp = new TRZBlockPos(pos);
				bp.orientation = EnumFacing.DOWN;
				bp.moveForwards(1);
				Block blockDown = world.getBlockState(bp.pos).getBlock();
				itemStacks.set(1, new ItemStack(blockDown));
			} else {
				itemStacks.set(1, ItemStack.EMPTY);
			}
			if (east) {
				TRZBlockPos bp = new TRZBlockPos(pos);
				bp.orientation = EnumFacing.EAST;
				bp.moveForwards(1);
				Block blockUp = world.getBlockState(bp.pos).getBlock();
				itemStacks.set(2, new ItemStack(blockUp));
			} else {
				itemStacks.set(2, ItemStack.EMPTY);
			}
			if (west) {
				TRZBlockPos bp = new TRZBlockPos(pos);
				bp.orientation = EnumFacing.WEST;
				bp.moveForwards(1);
				Block blockUp = world.getBlockState(bp.pos).getBlock();
				itemStacks.set(3, new ItemStack(blockUp));
			} else {
				itemStacks.set(3, ItemStack.EMPTY);
			}
			if (north) {
				TRZBlockPos bp = new TRZBlockPos(pos);
				bp.orientation = EnumFacing.NORTH;
				bp.moveForwards(1);
				Block blockUp = world.getBlockState(bp.pos).getBlock();
				itemStacks.set(4, new ItemStack(blockUp));
			} else {
				itemStacks.set(4, ItemStack.EMPTY);
			}
			if (south) {
				TRZBlockPos bp = new TRZBlockPos(pos);
				bp.orientation = EnumFacing.SOUTH;
				bp.moveForwards(1);
				Block blockUp = world.getBlockState(bp.pos).getBlock();
				itemStacks.set(5, new ItemStack(blockUp));
			} else {
				itemStacks.set(5, ItemStack.EMPTY);
			}
		}

		/*if (this.stored < 0) {
			this.stored = 0;
		}*/

		if (this.stored > 0) {
			IBlockState this_state = world.getBlockState(this.pos);
			Block this_block = this_state.getBlock();
			TileEntity this_tile = world.getTileEntity(this.pos);

			if (this_tile != null) {
				TileEntity tile_up = world
						.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY() + 1, this.pos.getZ()));
				if (tile_up != null && (this.up == 0 || this.up == 2)) {
					if (tile_up instanceof TRZTileEntityEnergyPipe) {
						if (this.last_from != null) {
							if (this.last_from.equals(EnumFacing.UP)) {
								if (((TRZTileEntityEnergyPipe) tile_up).getSide("down") == 0
										|| ((TRZTileEntityEnergyPipe) tile_up).getSide("down") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_up).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_up).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_up).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_up).addStored(pipe_transfer_rate,
														EnumFacing.UP);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_up).addStored(gap_stored,
														EnumFacing.UP);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_up).addStored(this.transfer_rate,
														EnumFacing.UP);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_up).addStored(gap_stored,
														EnumFacing.UP);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.NORTH)) {
								if (((TRZTileEntityEnergyPipe) tile_up).getSide("down") == 0
										|| ((TRZTileEntityEnergyPipe) tile_up).getSide("down") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_up).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_up).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_up).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_up).addStored(pipe_transfer_rate,
														EnumFacing.UP);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_up).addStored(gap_stored,
														EnumFacing.UP);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_up).addStored(this.transfer_rate,
														EnumFacing.UP);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_up).addStored(gap_stored,
														EnumFacing.UP);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.SOUTH)) {
								if (((TRZTileEntityEnergyPipe) tile_up).getSide("down") == 0
										|| ((TRZTileEntityEnergyPipe) tile_up).getSide("down") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_up).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_up).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_up).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_up).addStored(pipe_transfer_rate,
														EnumFacing.UP);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_up).addStored(gap_stored,
														EnumFacing.UP);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_up).addStored(this.transfer_rate,
														EnumFacing.UP);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_up).addStored(gap_stored,
														EnumFacing.UP);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.EAST)) {
								if (((TRZTileEntityEnergyPipe) tile_up).getSide("down") == 0
										|| ((TRZTileEntityEnergyPipe) tile_up).getSide("down") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_up).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_up).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_up).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_up).addStored(pipe_transfer_rate,
														EnumFacing.UP);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_up).addStored(gap_stored,
														EnumFacing.UP);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_up).addStored(this.transfer_rate,
														EnumFacing.UP);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_up).addStored(gap_stored,
														EnumFacing.UP);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.WEST)) {
								if (((TRZTileEntityEnergyPipe) tile_up).getSide("down") == 0
										|| ((TRZTileEntityEnergyPipe) tile_up).getSide("down") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_up).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_up).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_up).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_up).addStored(pipe_transfer_rate,
														EnumFacing.UP);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_up).addStored(gap_stored,
														EnumFacing.UP);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_up).addStored(this.transfer_rate,
														EnumFacing.UP);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_up).addStored(gap_stored,
														EnumFacing.UP);
											}
										}
									}
								}
							}
						} else {
							//System.out.println("");
						}
					}

					if (tile_up instanceof TRZTileEntityMachine) {
						int machine_stored = ((TRZTileEntityMachine) tile_up).getStored();
						int machine_capacity = ((TRZTileEntityMachine) tile_up).getCapacity();
						int machine_input_rate = ((TRZTileEntityMachine) tile_up).getInputRate();
						int gap_stored = machine_capacity - machine_stored;

						if (machine_stored <= machine_capacity) {
							if (gap_stored >= machine_input_rate) {
								this.stored -= machine_input_rate;
								((TRZTileEntityMachine) tile_up).addStored(machine_input_rate);
							} else if (gap_stored < machine_input_rate) {
								this.stored -= gap_stored;
								((TRZTileEntityMachine) tile_up).addStored(gap_stored);
							}
						}
					}
					if (tile_up instanceof TRZTileEntityCapacitor) {
						int tile_up_down = ((TRZTileEntityCapacitor) tile_up).getSide("down");
						if (tile_up_down == 0) {
							int capacitor_stored = ((TRZTileEntityCapacitor) tile_up).getStored();
							int capacitor_capacity = ((TRZTileEntityCapacitor) tile_up).getCapacity();
							int capacitor_input_rate = ((TRZTileEntityCapacitor) tile_up).getInputRate();
							int gap_stored = capacitor_capacity - capacitor_stored;

							if (capacitor_stored <= capacitor_capacity) {
								if (this.transfer_rate < capacitor_input_rate) {
									if (gap_stored >= this.transfer_rate) {
										this.stored -= this.transfer_rate;
										((TRZTileEntityCapacitor) tile_up).addStored(this.transfer_rate);
									} else if (gap_stored < this.transfer_rate) {
										this.stored -= gap_stored;
										((TRZTileEntityCapacitor) tile_up).addStored(gap_stored);
									}
								} else {
									if (gap_stored >= capacitor_input_rate) {
										this.stored -= capacitor_input_rate;
										((TRZTileEntityCapacitor) tile_up).addStored(capacitor_input_rate);
									} else if (gap_stored < capacitor_input_rate) {
										this.stored -= gap_stored;
										((TRZTileEntityCapacitor) tile_up).addStored(gap_stored);
									}
								}
							}
						}
					}
				}

				TileEntity tile_down = world
						.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY() - 1, this.pos.getZ()));
				if (tile_down != null && (this.down == 0 || this.down == 2)) {
					if (tile_down instanceof TRZTileEntityEnergyPipe) {
						if (this.last_from != null) {
							if (this.last_from.equals(EnumFacing.DOWN)) {
								if (((TRZTileEntityEnergyPipe) tile_down).getSide("up") == 0
										|| ((TRZTileEntityEnergyPipe) tile_down).getSide("up") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_down).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_down).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_down).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_down).addStored(pipe_transfer_rate,
														EnumFacing.DOWN);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_down).addStored(gap_stored,
														EnumFacing.DOWN);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_down).addStored(this.transfer_rate,
														EnumFacing.DOWN);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_down).addStored(gap_stored,
														EnumFacing.DOWN);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.NORTH)) {
								if (((TRZTileEntityEnergyPipe) tile_down).getSide("up") == 0
										|| ((TRZTileEntityEnergyPipe) tile_down).getSide("up") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_down).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_down).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_down).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_down).addStored(pipe_transfer_rate,
														EnumFacing.DOWN);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_down).addStored(gap_stored,
														EnumFacing.DOWN);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_down).addStored(this.transfer_rate,
														EnumFacing.DOWN);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_down).addStored(gap_stored,
														EnumFacing.DOWN);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.SOUTH)) {
								if (((TRZTileEntityEnergyPipe) tile_down).getSide("up") == 0
										|| ((TRZTileEntityEnergyPipe) tile_down).getSide("up") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_down).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_down).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_down).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (gap_stored >= pipe_transfer_rate) {
											this.stored -= pipe_transfer_rate;
											((TRZTileEntityEnergyPipe) tile_down).addStored(pipe_transfer_rate,
													EnumFacing.DOWN);
										} else if (gap_stored < pipe_transfer_rate) {
											this.stored -= gap_stored;
											((TRZTileEntityEnergyPipe) tile_down).addStored(gap_stored,
													EnumFacing.DOWN);
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.EAST)) {
								if (((TRZTileEntityEnergyPipe) tile_down).getSide("up") == 0
										|| ((TRZTileEntityEnergyPipe) tile_down).getSide("up") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_down).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_down).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_down).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_down).addStored(pipe_transfer_rate,
														EnumFacing.DOWN);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_down).addStored(gap_stored,
														EnumFacing.DOWN);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_down).addStored(this.transfer_rate,
														EnumFacing.DOWN);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_down).addStored(gap_stored,
														EnumFacing.DOWN);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.WEST)) {
								if (((TRZTileEntityEnergyPipe) tile_down).getSide("up") == 0
										|| ((TRZTileEntityEnergyPipe) tile_down).getSide("up") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_down).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_down).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_down).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_down).addStored(pipe_transfer_rate,
														EnumFacing.DOWN);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_down).addStored(gap_stored,
														EnumFacing.DOWN);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_down).addStored(this.transfer_rate,
														EnumFacing.DOWN);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_down).addStored(gap_stored,
														EnumFacing.DOWN);
											}
										}
									}
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
							if (gap_stored >= machine_input_rate) {
								this.stored -= machine_input_rate;
								((TRZTileEntityMachine) tile_down).addStored(machine_input_rate);
							} else if (gap_stored < machine_input_rate) {
								this.stored -= gap_stored;
								((TRZTileEntityMachine) tile_down).addStored(gap_stored);
							}
						}
					}
					if (tile_down instanceof TRZTileEntityCapacitor) {
						int tile_down_down = ((TRZTileEntityCapacitor) tile_down).getSide("up");
						if (tile_down_down == 0) {
							int capacitor_stored = ((TRZTileEntityCapacitor) tile_down).getStored();
							int capacitor_capacity = ((TRZTileEntityCapacitor) tile_down).getCapacity();
							int capacitor_input_rate = ((TRZTileEntityCapacitor) tile_down).getInputRate();
							int gap_stored = capacitor_capacity - capacitor_stored;

							if (capacitor_stored <= capacitor_capacity) {
								if (this.transfer_rate < capacitor_input_rate) {
									if (gap_stored >= this.transfer_rate) {
										this.stored -= this.transfer_rate;
										((TRZTileEntityCapacitor) tile_down).addStored(this.transfer_rate);
									} else if (gap_stored < this.transfer_rate) {
										this.stored -= gap_stored;
										((TRZTileEntityCapacitor) tile_down).addStored(gap_stored);
									}
								} else {
									if (gap_stored >= capacitor_input_rate) {
										this.stored -= capacitor_input_rate;
										((TRZTileEntityCapacitor) tile_down).addStored(capacitor_input_rate);
									} else if (gap_stored < capacitor_input_rate) {
										this.stored -= gap_stored;
										((TRZTileEntityCapacitor) tile_down).addStored(gap_stored);
									}
								}
							}
						}
					}
				}

				TileEntity tile_north = world
						.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() - 1));
				if (tile_north != null && (this.north == 0 || this.north == 2)) {
					if (tile_north instanceof TRZTileEntityEnergyPipe) {
						if (this.last_from != null) {
							if (this.last_from.equals(EnumFacing.NORTH)) {
								if (((TRZTileEntityEnergyPipe) tile_north).getSide("south") == 0
										|| ((TRZTileEntityEnergyPipe) tile_north).getSide("south") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_north).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_north).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_north).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_north).addStored(pipe_transfer_rate,
														EnumFacing.NORTH);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_north).addStored(gap_stored,
														EnumFacing.NORTH);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_north).addStored(this.transfer_rate,
														EnumFacing.NORTH);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_north).addStored(gap_stored,
														EnumFacing.NORTH);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.UP)) {
								if (((TRZTileEntityEnergyPipe) tile_north).getSide("south") == 0
										|| ((TRZTileEntityEnergyPipe) tile_north).getSide("south") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_north).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_north).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_north).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_north).addStored(pipe_transfer_rate,
														EnumFacing.NORTH);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_north).addStored(gap_stored,
														EnumFacing.NORTH);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_north).addStored(this.transfer_rate,
														EnumFacing.NORTH);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_north).addStored(gap_stored,
														EnumFacing.NORTH);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.DOWN)) {
								if (((TRZTileEntityEnergyPipe) tile_north).getSide("south") == 0
										|| ((TRZTileEntityEnergyPipe) tile_north).getSide("south") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_north).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_north).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_north).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_north).addStored(pipe_transfer_rate,
														EnumFacing.NORTH);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_north).addStored(gap_stored,
														EnumFacing.NORTH);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_north).addStored(this.transfer_rate,
														EnumFacing.NORTH);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_north).addStored(gap_stored,
														EnumFacing.NORTH);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.EAST)) {
								if (((TRZTileEntityEnergyPipe) tile_north).getSide("south") == 0
										|| ((TRZTileEntityEnergyPipe) tile_north).getSide("south") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_north).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_north).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_north).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_north).addStored(pipe_transfer_rate,
														EnumFacing.NORTH);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_north).addStored(gap_stored,
														EnumFacing.NORTH);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_north).addStored(this.transfer_rate,
														EnumFacing.NORTH);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_north).addStored(gap_stored,
														EnumFacing.NORTH);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.WEST)) {
								if (((TRZTileEntityEnergyPipe) tile_north).getSide("south") == 0
										|| ((TRZTileEntityEnergyPipe) tile_north).getSide("south") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_north).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_north).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_north).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_north).addStored(pipe_transfer_rate,
														EnumFacing.NORTH);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_north).addStored(gap_stored,
														EnumFacing.NORTH);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_north).addStored(this.transfer_rate,
														EnumFacing.NORTH);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_north).addStored(gap_stored,
														EnumFacing.NORTH);
											}
										}
									}
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
							if (gap_stored >= machine_input_rate) {
								this.stored -= machine_input_rate;
								((TRZTileEntityMachine) tile_north).addStored(machine_input_rate);
							} else if (gap_stored < machine_input_rate) {
								this.stored -= gap_stored;
								((TRZTileEntityMachine) tile_north).addStored(gap_stored);
							}
						}
					}
					if (tile_north instanceof TRZTileEntityCapacitor) {
						int tile_north_down = ((TRZTileEntityCapacitor) tile_north).getSide("south");
						if (tile_north_down == 0) {
							int capacitor_stored = ((TRZTileEntityCapacitor) tile_north).getStored();
							int capacitor_capacity = ((TRZTileEntityCapacitor) tile_north).getCapacity();
							int capacitor_input_rate = ((TRZTileEntityCapacitor) tile_north).getInputRate();
							int gap_stored = capacitor_capacity - capacitor_stored;

							if (capacitor_stored <= capacitor_capacity) {
								if (this.transfer_rate < capacitor_input_rate) {
									if (gap_stored >= this.transfer_rate) {
										this.stored -= this.transfer_rate;
										((TRZTileEntityCapacitor) tile_north).addStored(this.transfer_rate);
									} else if (gap_stored < this.transfer_rate) {
										this.stored -= gap_stored;
										((TRZTileEntityCapacitor) tile_north).addStored(gap_stored);
									}
								} else {
									if (gap_stored >= capacitor_input_rate) {
										this.stored -= capacitor_input_rate;
										((TRZTileEntityCapacitor) tile_north).addStored(capacitor_input_rate);
									} else if (gap_stored < capacitor_input_rate) {
										this.stored -= gap_stored;
										((TRZTileEntityCapacitor) tile_north).addStored(gap_stored);
									}
								}
							}
						}
					}
				}

				TileEntity tile_south = world
						.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() + 1));
				if (tile_south != null && (this.south == 0 || this.south == 2)) {
					if (tile_south instanceof TRZTileEntityEnergyPipe) {
						if (this.last_from != null) {
							if (this.last_from.equals(EnumFacing.SOUTH)) {
								if (((TRZTileEntityEnergyPipe) tile_south).getSide("north") == 0
										|| ((TRZTileEntityEnergyPipe) tile_south).getSide("north") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_south).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_south).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_south).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_south).addStored(pipe_transfer_rate,
														EnumFacing.SOUTH);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_south).addStored(gap_stored,
														EnumFacing.SOUTH);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_south).addStored(this.transfer_rate,
														EnumFacing.SOUTH);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_south).addStored(gap_stored,
														EnumFacing.SOUTH);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.UP)) {
								if (((TRZTileEntityEnergyPipe) tile_south).getSide("north") == 0
										|| ((TRZTileEntityEnergyPipe) tile_south).getSide("north") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_south).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_south).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_south).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_south).addStored(pipe_transfer_rate,
														EnumFacing.SOUTH);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_south).addStored(gap_stored,
														EnumFacing.SOUTH);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_south).addStored(this.transfer_rate,
														EnumFacing.SOUTH);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_south).addStored(gap_stored,
														EnumFacing.SOUTH);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.DOWN)) {
								if (((TRZTileEntityEnergyPipe) tile_south).getSide("north") == 0
										|| ((TRZTileEntityEnergyPipe) tile_south).getSide("north") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_south).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_south).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_south).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_south).addStored(pipe_transfer_rate,
														EnumFacing.SOUTH);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_south).addStored(gap_stored,
														EnumFacing.SOUTH);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_south).addStored(this.transfer_rate,
														EnumFacing.SOUTH);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_south).addStored(gap_stored,
														EnumFacing.SOUTH);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.EAST)) {
								if (((TRZTileEntityEnergyPipe) tile_south).getSide("north") == 0
										|| ((TRZTileEntityEnergyPipe) tile_south).getSide("north") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_south).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_south).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_south).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_south).addStored(pipe_transfer_rate,
														EnumFacing.SOUTH);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_south).addStored(gap_stored,
														EnumFacing.SOUTH);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_south).addStored(this.transfer_rate,
														EnumFacing.SOUTH);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_south).addStored(gap_stored,
														EnumFacing.SOUTH);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.WEST)) {
								if (((TRZTileEntityEnergyPipe) tile_south).getSide("north") == 0
										|| ((TRZTileEntityEnergyPipe) tile_south).getSide("north") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_south).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_south).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_south).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_south).addStored(pipe_transfer_rate,
														EnumFacing.SOUTH);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_south).addStored(gap_stored,
														EnumFacing.SOUTH);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_south).addStored(this.transfer_rate,
														EnumFacing.SOUTH);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_south).addStored(gap_stored,
														EnumFacing.SOUTH);
											}
										}
									}
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
							if (gap_stored >= machine_input_rate) {
								this.stored -= machine_input_rate;
								((TRZTileEntityMachine) tile_south).addStored(machine_input_rate);
							} else if (gap_stored < machine_input_rate) {
								this.stored -= gap_stored;
								((TRZTileEntityMachine) tile_south).addStored(gap_stored);
							}
						}
					}
					if (tile_south instanceof TRZTileEntityCapacitor) {
						int tile_south_south = ((TRZTileEntityCapacitor) tile_south).getSide("north");
						if (tile_south_south == 0) {
							int capacitor_stored = ((TRZTileEntityCapacitor) tile_south).getStored();
							int capacitor_capacity = ((TRZTileEntityCapacitor) tile_south).getCapacity();
							int capacitor_input_rate = ((TRZTileEntityCapacitor) tile_south).getInputRate();
							int gap_stored = capacitor_capacity - capacitor_stored;

							if (capacitor_stored <= capacitor_capacity) {
								if (this.transfer_rate < capacitor_input_rate) {
									if (gap_stored >= this.transfer_rate) {
										this.stored -= this.transfer_rate;
										((TRZTileEntityCapacitor) tile_south).addStored(this.transfer_rate);
									} else if (gap_stored < this.transfer_rate) {
										this.stored -= gap_stored;
										((TRZTileEntityCapacitor) tile_south).addStored(gap_stored);
									}
								} else {
									if (gap_stored >= capacitor_input_rate) {
										this.stored -= capacitor_input_rate;
										((TRZTileEntityCapacitor) tile_south).addStored(capacitor_input_rate);
									} else if (gap_stored < capacitor_input_rate) {
										this.stored -= gap_stored;
										((TRZTileEntityCapacitor) tile_south).addStored(gap_stored);
									}
								}
							}
						}
					}
				}

				TileEntity tile_west = world
						.getTileEntity(new BlockPos(this.pos.getX() - 1, this.pos.getY(), this.pos.getZ()));
				if (tile_west != null && (this.west == 0 || this.west == 2)) {
					if (tile_west instanceof TRZTileEntityEnergyPipe) {
						if (this.last_from != null) {
							if (this.last_from.equals(EnumFacing.WEST)) {
								if (((TRZTileEntityEnergyPipe) tile_west).getSide("east") == 0
										|| ((TRZTileEntityEnergyPipe) tile_west).getSide("east") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_west).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_west).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_west).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_west).addStored(pipe_transfer_rate,
														EnumFacing.WEST);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_west).addStored(gap_stored,
														EnumFacing.WEST);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_west).addStored(this.transfer_rate,
														EnumFacing.WEST);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_west).addStored(gap_stored,
														EnumFacing.WEST);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.UP)) {
								if (((TRZTileEntityEnergyPipe) tile_west).getSide("east") == 0
										|| ((TRZTileEntityEnergyPipe) tile_west).getSide("east") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_west).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_west).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_west).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_west).addStored(pipe_transfer_rate,
														EnumFacing.WEST);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_west).addStored(gap_stored,
														EnumFacing.WEST);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_west).addStored(this.transfer_rate,
														EnumFacing.WEST);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_west).addStored(gap_stored,
														EnumFacing.WEST);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.DOWN)) {
								if (((TRZTileEntityEnergyPipe) tile_west).getSide("east") == 0
										|| ((TRZTileEntityEnergyPipe) tile_west).getSide("east") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_west).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_west).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_west).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_west).addStored(pipe_transfer_rate,
														EnumFacing.WEST);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_west).addStored(gap_stored,
														EnumFacing.WEST);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_west).addStored(this.transfer_rate,
														EnumFacing.WEST);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_west).addStored(gap_stored,
														EnumFacing.WEST);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.NORTH)) {
								if (((TRZTileEntityEnergyPipe) tile_west).getSide("east") == 0
										|| ((TRZTileEntityEnergyPipe) tile_west).getSide("east") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_west).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_west).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_west).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_west).addStored(pipe_transfer_rate,
														EnumFacing.WEST);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_west).addStored(gap_stored,
														EnumFacing.WEST);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_west).addStored(this.transfer_rate,
														EnumFacing.WEST);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_west).addStored(gap_stored,
														EnumFacing.WEST);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.SOUTH)) {
								if (((TRZTileEntityEnergyPipe) tile_west).getSide("east") == 0
										|| ((TRZTileEntityEnergyPipe) tile_west).getSide("east") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_west).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_west).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_west).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_west).addStored(pipe_transfer_rate,
														EnumFacing.WEST);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_west).addStored(gap_stored,
														EnumFacing.WEST);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_west).addStored(this.transfer_rate,
														EnumFacing.WEST);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_west).addStored(gap_stored,
														EnumFacing.WEST);
											}
										}
									}
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
							if (gap_stored >= machine_input_rate) {
								this.stored -= machine_input_rate;
								((TRZTileEntityMachine) tile_west).addStored(machine_input_rate);
							} else if (gap_stored < machine_input_rate) {
								this.stored -= gap_stored;
								((TRZTileEntityMachine) tile_west).addStored(gap_stored);
							}
						}
					}
					if (tile_west instanceof TRZTileEntityCapacitor) {
						int tile_west_down = ((TRZTileEntityCapacitor) tile_west).getSide("east");
						if (tile_west_down == 0) {
							int capacitor_stored = ((TRZTileEntityCapacitor) tile_west).getStored();
							int capacitor_capacity = ((TRZTileEntityCapacitor) tile_west).getCapacity();
							int capacitor_input_rate = ((TRZTileEntityCapacitor) tile_west).getInputRate();
							int gap_stored = capacitor_capacity - capacitor_stored;

							if (capacitor_stored <= capacitor_capacity) {
								if (this.transfer_rate < capacitor_input_rate) {
									if (gap_stored >= this.transfer_rate) {
										this.stored -= this.transfer_rate;
										((TRZTileEntityCapacitor) tile_west).addStored(this.transfer_rate);
									} else if (gap_stored < this.transfer_rate) {
										this.stored -= gap_stored;
										((TRZTileEntityCapacitor) tile_west).addStored(gap_stored);
									}
								} else {
									if (gap_stored >= capacitor_input_rate) {
										this.stored -= capacitor_input_rate;
										((TRZTileEntityCapacitor) tile_west).addStored(capacitor_input_rate);
									} else if (gap_stored < capacitor_input_rate) {
										this.stored -= gap_stored;
										((TRZTileEntityCapacitor) tile_west).addStored(gap_stored);
									}
								}
							}
						}
					}
				}

				TileEntity tile_east = world
						.getTileEntity(new BlockPos(this.pos.getX() + 1, this.pos.getY(), this.pos.getZ()));
				if (tile_east != null && (this.east == 0 || this.east == 2)) {
					if (tile_east instanceof TRZTileEntityEnergyPipe) {
						if (this.last_from != null) {
							if (this.last_from.equals(EnumFacing.EAST)) {
								if (((TRZTileEntityEnergyPipe) tile_east).getSide("west") == 0
										|| ((TRZTileEntityEnergyPipe) tile_east).getSide("west") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_east).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_east).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_east).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_east).addStored(pipe_transfer_rate,
														EnumFacing.EAST);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_east).addStored(gap_stored,
														EnumFacing.EAST);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_east).addStored(this.transfer_rate,
														EnumFacing.EAST);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_east).addStored(gap_stored,
														EnumFacing.EAST);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.UP)) {
								if (((TRZTileEntityEnergyPipe) tile_east).getSide("west") == 0
										|| ((TRZTileEntityEnergyPipe) tile_east).getSide("west") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_east).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_east).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_east).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_east).addStored(pipe_transfer_rate,
														EnumFacing.EAST);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_east).addStored(gap_stored,
														EnumFacing.EAST);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_east).addStored(this.transfer_rate,
														EnumFacing.EAST);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_east).addStored(gap_stored,
														EnumFacing.EAST);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.DOWN)) {
								if (((TRZTileEntityEnergyPipe) tile_east).getSide("west") == 0
										|| ((TRZTileEntityEnergyPipe) tile_east).getSide("west") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_east).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_east).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_east).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_east).addStored(pipe_transfer_rate,
														EnumFacing.EAST);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_east).addStored(gap_stored,
														EnumFacing.EAST);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_east).addStored(this.transfer_rate,
														EnumFacing.EAST);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_east).addStored(gap_stored,
														EnumFacing.EAST);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.NORTH)) {
								if (((TRZTileEntityEnergyPipe) tile_east).getSide("west") == 0
										|| ((TRZTileEntityEnergyPipe) tile_east).getSide("west") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_east).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_east).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_east).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_east).addStored(pipe_transfer_rate,
														EnumFacing.EAST);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_east).addStored(gap_stored,
														EnumFacing.EAST);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_east).addStored(this.transfer_rate,
														EnumFacing.EAST);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_east).addStored(gap_stored,
														EnumFacing.EAST);
											}
										}
									}
								}
							}
							if (this.last_from.equals(EnumFacing.SOUTH)) {
								if (((TRZTileEntityEnergyPipe) tile_east).getSide("west") == 0
										|| ((TRZTileEntityEnergyPipe) tile_east).getSide("west") == 2) {
									int pipe_stored = ((TRZTileEntityEnergyPipe) tile_east).getStored();
									int pipe_capacity = ((TRZTileEntityEnergyPipe) tile_east).getCapacity();
									int pipe_transfer_rate = ((TRZTileEntityEnergyPipe) tile_east).getTransferRate();
									int gap_stored = pipe_capacity - pipe_stored;

									if (pipe_stored <= pipe_capacity) {
										if (pipe_transfer_rate <= this.transfer_rate) {
											if (gap_stored >= pipe_transfer_rate) {
												this.stored -= pipe_transfer_rate;
												((TRZTileEntityEnergyPipe) tile_east).addStored(pipe_transfer_rate,
														EnumFacing.EAST);
											} else if (gap_stored < pipe_transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_east).addStored(gap_stored,
														EnumFacing.EAST);
											}
										} else if (pipe_transfer_rate > this.transfer_rate) {
											if (gap_stored >= this.transfer_rate) {
												this.stored -= this.transfer_rate;
												((TRZTileEntityEnergyPipe) tile_east).addStored(this.transfer_rate,
														EnumFacing.EAST);
											} else if (gap_stored < this.transfer_rate) {
												this.stored -= gap_stored;
												((TRZTileEntityEnergyPipe) tile_east).addStored(gap_stored,
														EnumFacing.EAST);
											}
										}
									}
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
							if (gap_stored >= machine_input_rate) {
								this.stored -= machine_input_rate;
								((TRZTileEntityMachine) tile_east).addStored(machine_input_rate);
							} else if (gap_stored < machine_input_rate) {
								this.stored -= gap_stored;
								((TRZTileEntityMachine) tile_east).addStored(gap_stored);
							}
						}
					}
					if (tile_east instanceof TRZTileEntityCapacitor) {
						int tile_east_east = ((TRZTileEntityCapacitor) tile_east).getSide("west");
						if (tile_east_east == 0) {
							int capacitor_stored = ((TRZTileEntityCapacitor) tile_east).getStored();
							int capacitor_capacity = ((TRZTileEntityCapacitor) tile_east).getCapacity();
							int capacitor_input_rate = ((TRZTileEntityCapacitor) tile_east).getInputRate();
							int gap_stored = capacitor_capacity - capacitor_stored;

							if (capacitor_stored <= capacitor_capacity) {
								if (this.transfer_rate < capacitor_input_rate) {
									if (gap_stored >= this.transfer_rate) {
										this.stored -= this.transfer_rate;
										((TRZTileEntityCapacitor) tile_east).addStored(this.transfer_rate);
									} else if (gap_stored < this.transfer_rate) {
										this.stored -= gap_stored;
										((TRZTileEntityCapacitor) tile_east).addStored(gap_stored);
									}
								} else {
									if (gap_stored >= capacitor_input_rate) {
										this.stored -= capacitor_input_rate;
										((TRZTileEntityCapacitor) tile_east).addStored(capacitor_input_rate);
									} else if (gap_stored < capacitor_input_rate) {
										this.stored -= gap_stored;
										((TRZTileEntityCapacitor) tile_east).addStored(gap_stored);
									}
								}
							}
						}
					}
				}
			}
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

	public void setPower(int power) {
		boolean mustUpdate = power != this.stored;
		this.stored = power;
		if (mustUpdate) {
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

	public int getPowerConnectionState(EnumFacing side, int x, int z, int y) {
		return 0;
	}

	public TRZEnergyPipeConnectionType getEnergyConnectionState(EnumFacing side) {
		TRZBlockPos bp = new TRZBlockPos(this);
		bp.orientation = side;
		bp.moveForwards(1);

		TRZTileEntityEnergyPipe tile = (TRZTileEntityEnergyPipe) world.getTileEntity(pos);
		TileEntity tileOther = world.getTileEntity(bp.pos);

		IBlockState blockId = this.world.getBlockState(bp.pos);
		if (blockId == null) {
			return TRZEnergyPipeConnectionType.none;
		}
		if (blockId.getBlock() instanceof TRZBlockEnergyPipe) {
			if (tileOther != null && tileOther instanceof TRZTileEntityEnergyPipe) {
				if (side.equals(EnumFacing.UP) && (this.up == 2 || this.up == 0)) {
					if (((TRZTileEntityEnergyPipe) tileOther).getSide("down") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.DOWN) && (this.down == 2 || this.down == 0)) {
					if (((TRZTileEntityEnergyPipe) tileOther).getSide("up") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.NORTH) && (this.north == 2 || this.north == 0)) {
					if (((TRZTileEntityEnergyPipe) tileOther).getSide("south") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.SOUTH) && (this.south == 2 || this.south == 0)) {
					if (((TRZTileEntityEnergyPipe) tileOther).getSide("north") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.EAST) && (this.east == 2 || this.east == 0)) {
					if (((TRZTileEntityEnergyPipe) tileOther).getSide("west") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cableall;
					}
				}
				if (side.equals(EnumFacing.WEST) && (this.west == 2 || this.west == 0)) {
					if (((TRZTileEntityEnergyPipe) tileOther).getSide("east") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cableall;
					}
				}
			}
		}

		if (blockId.getBlock() instanceof TRZBlockCapacitor) {
			if (tileOther != null && tileOther instanceof TRZTileEntityCapacitor) {
				if (side.equals(EnumFacing.UP) && (this.up == 2 || this.up == 0)) {
					if (((TRZTileEntityCapacitor) tileOther).getSide("down") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cablesingle;
					}
				}
				if (side.equals(EnumFacing.DOWN) && (this.down == 2 || this.down == 0)) {
					if (((TRZTileEntityCapacitor) tileOther).getSide("up") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cablesingle;
					}
				}
				if (side.equals(EnumFacing.NORTH) && (this.north == 2 || this.north == 0)) {
					if (((TRZTileEntityCapacitor) tileOther).getSide("south") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cablesingle;
					}
				}
				if (side.equals(EnumFacing.SOUTH) && (this.south == 2 || this.south == 0)) {
					if (((TRZTileEntityCapacitor) tileOther).getSide("north") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cablesingle;
					}
				}
				if (side.equals(EnumFacing.EAST) && (this.east == 2 || this.east == 0)) {
					if (((TRZTileEntityCapacitor) tileOther).getSide("west") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cablesingle;
					}
				}
				if (side.equals(EnumFacing.WEST) && (this.west == 2 || this.west == 0)) {
					if (((TRZTileEntityCapacitor) tileOther).getSide("east") == 1) {
						return TRZEnergyPipeConnectionType.none;
					} else {
						return TRZEnergyPipeConnectionType.cablesingle;
					}
				}
			}
		}

		if (side.equals(EnumFacing.UP)) {
			if (this.up == 2) {
				return TRZEnergyPipeConnectionType.cableall;
			}
			if (this.up == 1) {
				return TRZEnergyPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.DOWN)) {
			if (this.down == 2) {
				return TRZEnergyPipeConnectionType.cableall;
			}
			if (this.down == 1) {
				return TRZEnergyPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.NORTH)) {
			if (this.north == 2) {
				return TRZEnergyPipeConnectionType.cableall;
			}
			if (this.north == 1) {
				return TRZEnergyPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.SOUTH)) {
			if (this.south == 2) {
				return TRZEnergyPipeConnectionType.cableall;
			}
			if (this.south == 1) {
				return TRZEnergyPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.EAST)) {
			if (this.east == 2) {
				return TRZEnergyPipeConnectionType.cableall;
			}
			if (this.east == 1) {
				return TRZEnergyPipeConnectionType.none;
			}
		}
		if (side.equals(EnumFacing.WEST)) {
			if (this.west == 2) {
				return TRZEnergyPipeConnectionType.cableall;
			}
			if (this.west == 1) {
				return TRZEnergyPipeConnectionType.none;
			}
		}
		if (TRZPipeConnectionsList.getEnergyConnectionBlocks(side, blockId, world, bp.pos)) {
			return TRZEnergyPipeConnectionType.cablesingle;
		}
		return TRZEnergyPipeConnectionType.none;
	}

	public void readFromNBT(NBTTagCompound compound) {
		this.up = compound.getInteger("up");
		this.stored = compound.getInteger("power");
		super.readFromNBT(compound);

	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("up", this.up);
		compound.setInteger("power", this.stored);
		return super.writeToNBT(compound);
	}

	public String getName() {
		return null;
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
		return this.itemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.itemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.itemStacks, index);
	}

	public boolean isItemPower(int index) {
		return TRZItemPowerValues.getItemPower(this.itemStacks.get(index).getItem()) > 0;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.itemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack)
				&& ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.itemStacks.set(index, stack);
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

	public void openInventory(EntityPlayer player) {
	}

	public void closeInventory(EntityPlayer player) {
	}

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

	public void clear() {
	}

	public EnumFacing getLastFrom() {
		return this.last_from;
	}

}
