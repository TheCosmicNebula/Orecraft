package com.zeher.orecraft.machine.tileentity;

import com.zeher.orecraft.machine.container.ContainerPoweredCharger;
import com.zeher.trzlib.api.value.TRZItemPowerValues;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityPoweredCharger extends TileEntityLockable implements ITickable, ISidedInventory {
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	private NonNullList<ItemStack> charge_stacks = NonNullList.<ItemStack>withSize(13, ItemStack.EMPTY);
	
	private String grinderCustomName;
	private boolean isCharging;

	public int stored;
	public int capacity = 120000;

	public int getSizeInventory() {
		return this.charge_stacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.charge_stacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.charge_stacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.charge_stacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.charge_stacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.charge_stacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack)
				&& ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.charge_stacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.markDirty();
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.grinderCustomName : "Powered Charger";
	}

	public boolean hasCustomName() {
		return this.grinderCustomName != null && !this.grinderCustomName.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.grinderCustomName = p_145951_1_;
	}

	public static void registerFixesCharger(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY,
				new ItemStackDataLists(TileEntityPoweredCharger.class, new String[] { "Items" }));
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.charge_stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.charge_stacks);
		this.stored = compound.getInteger("stored");

		if (compound.hasKey("CustomName", 8)) {
			this.grinderCustomName = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("stored", this.stored);
		ItemStackHelper.saveAllItems(compound, this.charge_stacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.grinderCustomName);
		}
		return compound;
	}

	public int getInventoryStackLimit() {
		return 64;
	}
	
	public void update() {
		
		if (!this.charge_stacks.get(7).isEmpty()) {
			storedSlot();
		} else {
			storedSlot();
		}
		
		if (!this.world.isRemote) {
			for(int x = 1; x < 10; x++){
				if ((isItemStored(this.charge_stacks.get(x)) && !(this.charge_stacks.get(x).isEmpty()) && (this.stored > 0))) {
					if (this.charge_stacks.get(x).getItemDamage() > 0) {
						this.charge_stacks.get(x).setItemDamage(this.charge_stacks.get(x).getItemDamage() - 1);
						this.stored -= TRZItemPowerValues.getItemPower(this.charge_stacks.get(x).getItem());
					}
				}
			}
			if ((isItemStored(this.charge_stacks.get(0))) && (this.stored <= this.capacity - TRZItemPowerValues.getItemPower(this.charge_stacks.get(0).getItem()))) {
				if (!this.charge_stacks.get(0).isItemStackDamageable()) {
					this.stored += TRZItemPowerValues.getItemPower(this.charge_stacks.get(0).getItem());
					
					if (this.charge_stacks.get(0) != null) {
						
						this.charge_stacks.get(0).setCount(this.charge_stacks.get(0).getCount());
						
						if (this.charge_stacks.get(0).getCount() == 0) {
							this.charge_stacks.set(0, this.charge_stacks.get(0).getItem() .getContainerItem(this.charge_stacks.get(0)));
						}
					}
				} else if (this.charge_stacks.get(0).getItemDamage() < this.charge_stacks.get(0) .getMaxDamage()) {
					this.stored += TRZItemPowerValues.getItemPower(this.charge_stacks.get(0).getItem());
					this.charge_stacks.set(0, new ItemStack(this.charge_stacks.get(0).getItem(), this.charge_stacks.get(0).getCount(), this.charge_stacks.get(0).getItemDamage() + 1));
				}
			}
			
			if ((stored > capacity) && this.charge_stacks.get(11).getCount() == 0) {
				stored = capacity;
			}
			if ((stored > capacity) && this.charge_stacks.get(11).getCount() == 1) {
				stored = 160000;
			}
			if ((stored > capacity) && this.charge_stacks.get(11).getCount() == 2) {
				stored = 200000;
			}
			if ((stored > capacity) && this.charge_stacks.get(11).getCount() == 3) {
				stored = 240000;
			}
			if ((stored > capacity) && this.charge_stacks.get(11).getCount() == 4) {
				stored = 280000;
			}
		}
	}
	
	public int getStoredRemainingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}

	public boolean hasStored() {
		return this.stored > 0;
	}

	public void storedSlot() {
		int i = this.charge_stacks.get(11).getCount();
		if (i == 0) {
			this.capacity = 120000;
		} else if (i == 1) {
			this.capacity = 160000;
		} else if (i == 2) {
			this.capacity = 200000;
		} else if (i == 3) {
			this.capacity = 240000;
		} else if (i == 4) {
			this.capacity = 280000;
		} else {
			this.capacity = 120000;
		}
	}

	public static boolean isItemStored(ItemStack par0ItemStack) {
		return TRZItemPowerValues.getItemPower(par0ItemStack.getItem()) > 0;
	}

	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
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
		return new ContainerPoweredCharger(playerInventory, this);
	}

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
		}
	}

	public int getFieldCount() {
		return 1;
	}

	public void clear() {
		this.charge_stacks.clear();
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
}