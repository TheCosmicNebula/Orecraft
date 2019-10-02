package com.zeher.orecraft.storage.core.tileentity;

import javax.annotation.Nullable;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.storage.core.block.BlockPoweredCapacitor;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.connection.EnumSide;
import com.zeher.zeherlib.api.transfer.EnergyTransfer;
import com.zeher.zeherlib.api.value.ItemPowerValues;
import com.zeher.zeherlib.storage.IStorage;
import com.zeher.zeherlib.storage.item.ItemStorage;

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

public class TileEntityPoweredCapacitor extends TileEntity implements ISidedInventory, IInventory, ITickable, IStorage {

	private static final int[] slots_top = { 1 };
	private static final int[] slots_bottom = { 2, 1 };
	private static final int[] slots_sides = { 1 };
	
	private NonNullList<ItemStack> capacitor_stacks = NonNullList.<ItemStack>withSize(13, ItemStack.EMPTY);
	
	private String custom_name;
	
	public int stored;
	public int capacity = Reference.VALUE.STORAGE.POWERED_CAPACITY;
	public int input_rate = Reference.VALUE.STORAGE.POWERED_INPUT;
	public int output_rate = Reference.VALUE.STORAGE.POWERED_OUTPUT;

	private EnumSide[] side_array = EnumSide.getDefaultArray();
	
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
		return this.capacitor_stacks.size();
	}
	
	@Override
	public void setSide(EnumFacing facing, EnumSide value) {
		for (EnumFacing c : EnumFacing.VALUES) {
			this.side_array[facing.getIndex()] = value;
		}
		
		this.markDirty();
	}

	@Override
	public EnumSide getSide(EnumFacing facing) {
		for (EnumFacing c : EnumFacing.VALUES) {
			return this.side_array[facing.getIndex()];
		}
		return null;
	}
	
	@Override
	public void cycleside(EnumFacing facing) {
		EnumSide next = this.side_array[facing.getIndex()].getNext();
		this.setSide(facing, next);
		
		Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
		world.markBlockRangeForRenderUpdate(pos, pos);
		this.markDirty();
	}
	
	public void setStack(EnumFacing facing, ItemStack stack) {
		switch(facing) {
			case UP:
				capacitor_stacks.set(2, stack);
				break;
			case DOWN:
				capacitor_stacks.set(3, stack);
				break;
			case NORTH:
				capacitor_stacks.set(6, stack);
				break;
			case SOUTH:
				capacitor_stacks.set(7, stack);
				break;
			case EAST:
				capacitor_stacks.set(4, stack);
				break;
			case WEST:
				capacitor_stacks.set(5, stack);
				break;
				default:
		}
	}

	@Override
	public void update() {
		boolean flag = this.stored > 0;
		boolean flag1 = false;
		
		ItemStack stackInput = capacitor_stacks.get(1);
		ItemStack stackOutput = capacitor_stacks.get(0);
		
		if (!this.world.isRemote) {
			for(int x = 8; x < 13; x++){
				if (this.isItemPowerStorage(x) && !(this.capacitor_stacks.get(x).isEmpty()) && (this.stored > 0)) {
					if (this.capacitor_stacks.get(x).getItemDamage() > 0) {
						this.capacitor_stacks.get(x).setItemDamage(this.capacitor_stacks.get(x).getItemDamage() - 1);
						this.stored -= ItemPowerValues.getItemPower(this.capacitor_stacks.get(x).getItem());
					}
				}
			}
			if (this.isItemPower(1) && (this.stored <= this.capacity - ItemPowerValues.getItemPower(stackInput.getItem()))) {
				if (!stackInput.isItemStackDamageable()) {
					this.stored += ItemPowerValues.getItemPower(stackInput.getItem());

					flag1 = true;
					if (!(stackInput.isEmpty())) {
						stackInput.setCount(stackInput.getCount() - 1);
						if (stackInput.isEmpty()) {
							this.capacitor_stacks.set(1, stackInput.getItem().getContainerItem(stackInput));
						}
					}
				} else if (stackInput.getItemDamage() < stackInput.getMaxDamage()) {
					this.stored += ItemPowerValues.getItemPower(stackInput.getItem());
					this.capacitor_stacks.set(1, new ItemStack(stackInput.getItem(), stackInput.getCount(), stackInput.getItemDamage() + 1));
				}
			}
			if (flag1) {
				this.update();
				this.markDirty();
			}
		}

		IBlockState state = world.getBlockState(this.pos);
		Block block = state.getBlock();
		
		if (block instanceof BlockPoweredCapacitor) {
			for (EnumFacing c : EnumFacing.VALUES) {
				Block block_is = this.world.getBlockState(this.pos.offset(c)).getBlock();
				this.setStack(c, new ItemStack(block_is));
			}
		}
		
		if (this.stored > 0 && this != null) {
			EnergyTransfer.OUTPUT.STORAGE.outputEnergy(this.world, this.pos);
		}
	}
	
	@Override
	public void addStored(int add) {
		this.stored += add;
	}

	private boolean isItemPowerStorage(int index) {
		if (!(this.capacitor_stacks.get(index).isEmpty())) {
			return this.capacitor_stacks.get(index).getItem() instanceof ItemStorage;
		}
		return false;
	}

	public int getPowerRemainingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}

	public ItemStack getStackInSlot(int i) {
		return this.capacitor_stacks.get(i);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.capacitor_stacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.capacitor_stacks, index);
	}

	public boolean isItemPower(int index) {
		return ItemPowerValues.getItemPower(this.capacitor_stacks.get(index).getItem()) > 0;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.capacitor_stacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.capacitor_stacks.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.custom_name : OrecraftReference.GUI_NAME.STORAGE.POWERED.CAPACITOR;
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
		this.capacitor_stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.capacitor_stacks);
		this.stored = compound.getInteger("power");
		
		this.setSide(EnumFacing.DOWN, EnumSide.getSideFromIndex(compound.getInteger("DOWN_INDEX")));
		this.setSide(EnumFacing.UP, EnumSide.getSideFromIndex(compound.getInteger("UP_INDEX")));
		this.setSide(EnumFacing.NORTH, EnumSide.getSideFromIndex(compound.getInteger("NORTH_INDEX")));
		this.setSide(EnumFacing.SOUTH, EnumSide.getSideFromIndex(compound.getInteger("SOUTH_INDEX")));
		this.setSide(EnumFacing.WEST, EnumSide.getSideFromIndex(compound.getInteger("WEST_INDEX")));
		this.setSide(EnumFacing.EAST, EnumSide.getSideFromIndex(compound.getInteger("EAST_INDEX")));
		
		if (compound.hasKey("custom_name", 8)) {
			this.custom_name = compound.getString("custom_name");
		}
		super.readFromNBT(compound);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, this.capacitor_stacks);
		compound.setInteger("power", this.stored);
		
		compound.setInteger("DOWN_INDEX", this.side_array[0].getIndex());
		compound.setInteger("UP_INDEX", this.side_array[1].getIndex());
		compound.setInteger("NORTH_INDEX", this.side_array[2].getIndex());
		compound.setInteger("SOUTH_INDEX", this.side_array[3].getIndex());
		compound.setInteger("WEST_INDEX", this.side_array[4].getIndex());
		compound.setInteger("EAST_INDEX", this.side_array[5].getIndex());
		
		if (this.hasCustomName()) {
			compound.setString("custom_name", this.custom_name);
		}
		return super.writeToNBT(compound);
	}
	
	@Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("DOWN_INDEX", this.side_array[0].getIndex());
		compound.setInteger("UP_INDEX", this.side_array[1].getIndex());
		compound.setInteger("NORTH_INDEX", this.side_array[2].getIndex());
		compound.setInteger("SOUTH_INDEX", this.side_array[3].getIndex());
		compound.setInteger("WEST_INDEX", this.side_array[4].getIndex());
		compound.setInteger("EAST_INDEX", this.side_array[5].getIndex());
		
		//System.out.println("Sending Packet - " + getSide("up") + " " + getSide("down") + " " + getSide("north") + " " + getSide("south") + " " + getSide("east") + " " + getSide("west"));
		writeToNBT(compound);
        return new SPacketUpdateTileEntity(pos, 0, compound);
    }

    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt) {
    	NBTTagCompound compound = pkt.getNbtCompound();
    	this.setSide(EnumFacing.DOWN, EnumSide.getSideFromIndex(compound.getInteger("DOWN_INDEX")));
		this.setSide(EnumFacing.UP, EnumSide.getSideFromIndex(compound.getInteger("UP_INDEX")));
		this.setSide(EnumFacing.NORTH, EnumSide.getSideFromIndex(compound.getInteger("NORTH_INDEX")));
		this.setSide(EnumFacing.SOUTH, EnumSide.getSideFromIndex(compound.getInteger("SOUTH_INDEX")));
		this.setSide(EnumFacing.WEST, EnumSide.getSideFromIndex(compound.getInteger("WEST_INDEX")));
		this.setSide(EnumFacing.EAST, EnumSide.getSideFromIndex(compound.getInteger("EAST_INDEX")));
    }
    
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }
    
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }
    
	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.capacitor_stacks) {
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

	@Override
	public void minusStored(int value) {
		this.stored -= value;
	}
	
	@Override
	public EnumSide[] getSideArray() {
		return this.side_array;
	}

	@Override
	public void setSideArray(EnumSide[] array_from) {
		this.side_array = array_from;
	}

}