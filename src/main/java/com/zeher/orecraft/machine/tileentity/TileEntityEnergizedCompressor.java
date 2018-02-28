package com.zeher.orecraft.machine.tileentity;

import com.zeher.orecraft.core.handlers.recipe.MachineCompressorRecipes;
import com.zeher.orecraft.machine.block.BlockEnergizedCompressor;
import com.zeher.orecraft.machine.container.ContainerEnergizedCompressor;
import com.zeher.trzlib.api.TRZUtil;
import com.zeher.trzlib.api.value.TRZItemPowerValues;

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
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityEnergizedCompressor extends TileEntityLockable implements ITickable, ISidedInventory {
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	private NonNullList<ItemStack> compressorItemStacks = NonNullList.<ItemStack>withSize(8, ItemStack.EMPTY);

	private int compressorBurnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;
	private String compressorCustomName;

	public int _power;
	public int _max_power = 500000;
	public int cookSpeed = 30;

	public int getSizeInventory() {
		return this.compressorItemStacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.compressorItemStacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.compressorItemStacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.compressorItemStacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.compressorItemStacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.compressorItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack)
				&& ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.compressorItemStacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.totalCookTime = this.getCookTime(stack);
			this.cookTime = 0;
			this.markDirty();
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.compressorCustomName : "Energized Compressor";
	}

	public boolean hasCustomName() {
		return this.compressorCustomName != null && !this.compressorCustomName.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.compressorCustomName = p_145951_1_;
	}

	public static void registerFixesCompressor(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY,
				new ItemStackDataLists(TileEntityEnergizedCompressor.class, new String[] { "Items" }));
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.compressorItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.compressorItemStacks);
		this.compressorBurnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this._power = compound.getInteger("power");

		if (compound.hasKey("CustomName", 8)) {
			this.compressorCustomName = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short) this.compressorBurnTime);
		compound.setInteger("CookTime", (short) this.cookTime);
		compound.setInteger("CookTimeTotal", (short) this.totalCookTime);
		compound.setInteger("power", this._power);
		ItemStackHelper.saveAllItems(compound, this.compressorItemStacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.compressorCustomName);
		}

		return compound;
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isBurning() {
		return this.compressorBurnTime > 0;
	}

	public boolean isCooking() {
		return this.cookTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory) {
		return inventory.getField(0) > 0;
	}

	public void update() {
		boolean flag = !this.isBurning();
		boolean flag1 = false;

		if (!this.compressorItemStacks.get(5).isEmpty()) {
			smeltSpeed();
		} else {
			smeltSpeed();
		}
		if (!this.compressorItemStacks.get(6).isEmpty()) {
			powerSlot();
		} else {
			powerSlot();
		}
		
		if(this.isBurning()){
			--this.compressorBurnTime;
		}

		if (!this.world.isRemote) {
			if ((isItemPower(this.compressorItemStacks.get(1))) && (this._power <= this._max_power
					- TRZItemPowerValues.getItemPower(this.compressorItemStacks.get(1).getItem()))) {
				if (!this.compressorItemStacks.get(1).isItemStackDamageable()) {
					this._power += TRZItemPowerValues.getItemPower(this.compressorItemStacks.get(1).getItem());

					flag1 = true;
					if (this.compressorItemStacks.get(1) != null) {
						this.compressorItemStacks.get(1).setCount(this.compressorItemStacks.get(1).getCount());
						if (this.compressorItemStacks.get(1).getCount() == 0) {
							this.compressorItemStacks.set(1, this.compressorItemStacks.get(1).getItem()
									.getContainerItem(this.compressorItemStacks.get(1)));
						}
					}
				} else if (this.compressorItemStacks.get(1).getItemDamage() < this.compressorItemStacks.get(1)
						.getMaxDamage()) {
					this._power += TRZItemPowerValues.getItemPower(this.compressorItemStacks.get(1).getItem());
					this.compressorItemStacks.set(1,
							new ItemStack(this.compressorItemStacks.get(1).getItem(),
									this.compressorItemStacks.get(1).getCount(),
									this.compressorItemStacks.get(1).getItemDamage() + 1));
				}
			}
			if(!this.isBurning() && this.hasPower() && this.canSmelt()){
				this.compressorBurnTime = cookSpeed;
			}
			if(this.isBurning()){
				flag1 = true;
			}
			
			if ((hasPower()) && (canSmelt())) {
				this.cookTime += 1;
				if (this.cookTime == this.cookSpeed) {
					this.cookTime = 0;
					smeltItem();
					smeltSpeed();
					powerSlot();
					flag1 = true;
				}
			} else {
				this.cookTime = 0;
			}
			if (flag == this.isBurning()) {
				flag1 = true;
				BlockEnergizedCompressor.setState(!this.isBurning(), this.world, this.pos);
			}

			if (flag1) {
				this.markDirty();
			}
			if ((_power > _max_power) && this.compressorItemStacks.get(6).getCount() == 0) {
				_power = _max_power;
			}
			if ((_power > _max_power) && this.compressorItemStacks.get(6).getCount() == 1) {
				_power = 540000;
			}
			if ((_power > _max_power) && this.compressorItemStacks.get(6).getCount() == 2) {
				_power = 580000;
			}
			if ((_power > _max_power) && this.compressorItemStacks.get(6).getCount() == 3) {
				_power = 620000;
			}
			if ((_power > _max_power) && this.compressorItemStacks.get(6).getCount() == 4) {
				_power = 660000;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.cookTime * par1 / this.cookSpeed;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentItemBurnTime == 0) {
			this.currentItemBurnTime = this.cookSpeed;
		}
		return this.compressorBurnTime * par1 / this.currentItemBurnTime;
	}

	public int getPowerRemainingScaled(int par1) {
		return this._power * par1 / this._max_power;
	}

	public boolean hasPower() {
		return this._power > 0;
	}

	public boolean isSmelting() {
		return this.compressorBurnTime > 0;
	}

	public void smeltSpeed() {
		int i = this.compressorItemStacks.get(5).getCount();
		if (i == 0) {
			this.cookSpeed = 30;
		}
		if (i == 1) {
			this.cookSpeed = 25;
		}
		if (i == 2) {
			this.cookSpeed = 20;
		}
		if (i == 3) {
			this.cookSpeed = 15;
		}
		if (i == 4) {
			this.cookSpeed = 10;
		} else {
			this.cookSpeed = 30;
		}
	}

	public void powerSlot() {
		int i = this.compressorItemStacks.get(6).getCount();
		if (i == 0) {
			this._max_power = 500000;
		} else if (i == 1) {
			this._max_power = 540000;
		} else if (i == 2) {
			this._max_power = 580000;
		} else if (i == 3) {
			this._max_power = 620000;
		} else if (i == 4) {
			this._max_power = 660000;
		} else {
			this._max_power = 500000;
		}
	}

	public int getCookTime(ItemStack stack) {
		return cookSpeed;
	}

	private boolean canSmelt() {
		if (((ItemStack) this.compressorItemStacks.get(0)).isEmpty()
				&& ((ItemStack) this.compressorItemStacks.get(2)).isEmpty()) {
			return false;
		}
		if (!(this.compressorItemStacks.get(0).isEmpty()) && (this.compressorItemStacks.get(2).isEmpty())) {
			ItemStack stack = MachineCompressorRecipes.instance().getCompressingResult(this.compressorItemStacks.get(0));
			if (stack.isEmpty()) {
				return false;
			} else {
				ItemStack stack1 = compressorItemStacks.get(4);
				if (stack1.isEmpty()) {
					return true;
				}
				if (!stack1.isItemEqual(stack)) {
					return false;
				}
				int result = stack1.getCount() + stack.getCount();
				return result <= getInventoryStackLimit() && result <= stack1.getMaxStackSize();
			}
		}
		if (!(this.compressorItemStacks.get(2).isEmpty()) && (this.compressorItemStacks.get(0).isEmpty())) {
			ItemStack stack = MachineCompressorRecipes.instance().getCompressingResult(this.compressorItemStacks.get(2));
			if (stack.isEmpty()) {
				return false;
			} else {
				ItemStack stack1 = compressorItemStacks.get(3);
				if (stack1.isEmpty()) {
					return true;
				}
				if (!stack1.isItemEqual(stack)) {
					return false;
				}
				int result = stack1.getCount() + stack.getCount();
				return result <= getInventoryStackLimit() && result <= stack1.getMaxStackSize();
			}
		} else {
			ItemStack doubleStack0 = MachineCompressorRecipes.instance().getCompressingResult(this.compressorItemStacks.get(0));
			ItemStack doubleStack2 = MachineCompressorRecipes.instance().getCompressingResult(this.compressorItemStacks.get(2));

			if (doubleStack0.isEmpty() || doubleStack2.isEmpty()) {
				return false;
			} else {
				if (this.compressorItemStacks.get(3).isEmpty() || this.compressorItemStacks.get(4).isEmpty()) {
					return true;
				}
				if (!this.compressorItemStacks.get(3).isItemEqual(doubleStack2)
						|| !this.compressorItemStacks.get(4).isItemEqual(doubleStack0)) {
					return false;
				}
				int result = this.compressorItemStacks.get(3).getCount() + doubleStack2.getCount();
				int result2 = this.compressorItemStacks.get(4).getCount() + doubleStack0.getCount();
				return ((result <= getInventoryStackLimit()) && (result <= doubleStack2.getMaxStackSize()))
						|| ((result2 <= getInventoryStackLimit()) && (result2 <= doubleStack0.getMaxStackSize()));
			}
		}
	}

	public void smeltItem() {
		if (this.canSmelt() && !(this.compressorItemStacks.get(0).isEmpty())
				&& !(this.compressorItemStacks.get(2).isEmpty())) {
			this._power -= TRZUtil.machinePowerDrain() * 2;
			ItemStack itemstack = (ItemStack) this.compressorItemStacks.get(2);
			ItemStack itemstack1 = MachineCompressorRecipes.instance().getCompressingResult(itemstack);
			ItemStack itemstack2 = (ItemStack) this.compressorItemStacks.get(0);
			ItemStack itemstack3 = MachineCompressorRecipes.instance().getCompressingResult(itemstack2);

			ItemStack outputStack1 = this.compressorItemStacks.get(3);
			ItemStack outputStack2 = this.compressorItemStacks.get(4);

			if (outputStack1.isEmpty()) {
				this.compressorItemStacks.set(3, itemstack1.copy());
				itemstack.shrink(1);
			} else if (outputStack1.getItem() == itemstack1.getItem()) {
				outputStack1.grow(itemstack1.getCount());
				itemstack.shrink(1);
			}

			if (outputStack2.isEmpty()) {
				this.compressorItemStacks.set(4, itemstack3.copy());
				itemstack2.shrink(1);
			} else if (outputStack2.getItem() == itemstack3.getItem()) {
				outputStack2.grow(itemstack3.getCount());
				itemstack2.shrink(1);
			}

		}
		if (this.canSmelt() && !(this.compressorItemStacks.get(0).isEmpty())
				&& (this.compressorItemStacks.get(2).isEmpty())) {
			this._power -= TRZUtil.machinePowerDrain();
			ItemStack itemstack = this.compressorItemStacks.get(0);
			ItemStack itemstack1 = MachineCompressorRecipes.instance().getCompressingResult(itemstack);

			ItemStack outputStack2 = this.compressorItemStacks.get(4);
			if (outputStack2.isEmpty()) {
				this.compressorItemStacks.set(4, itemstack1.copy());
			} else if (outputStack2.getItem() == itemstack1.getItem()) {
				outputStack2.grow(itemstack1.getCount());
			}
			itemstack.shrink(1);
		}
		if (this.canSmelt() && !(this.compressorItemStacks.get(2).isEmpty())
				&& (this.compressorItemStacks.get(0).isEmpty())) {
			this._power -= TRZUtil.machinePowerDrain();
			ItemStack itemstack = this.compressorItemStacks.get(2);
			ItemStack itemstack1 = MachineCompressorRecipes.instance().getCompressingResult(itemstack);

			ItemStack outputStack2 = this.compressorItemStacks.get(3);
			if (outputStack2.isEmpty()) {
				this.compressorItemStacks.set(3, itemstack1.copy());
			} else if (outputStack2.getItem() == itemstack1.getItem()) {
				outputStack2.grow(itemstack1.getCount());
			}
			itemstack.shrink(1);
		}
	}

	public static boolean isItemPower(ItemStack par0ItemStack) {
		return TRZItemPowerValues.getItemPower(par0ItemStack.getItem()) > 0;
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
		return new ContainerEnergizedCompressor(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 0:
			return this.compressorBurnTime;
		case 1:
			return this.currentItemBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		case 4:
			return this._power;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.compressorBurnTime = value;
			break;
		case 1:
			this.currentItemBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
			break;
		case 4:
			this._power = value;
		}
	}

	public int getFieldCount() {
		return 5;
	}

	public void clear() {
		this.compressorItemStacks.clear();
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