package com.zeher.orecraft.machine.tileentity;

import com.zeher.orecraft.core.handlers.recipe.MachineGrinderRecipes;
import com.zeher.orecraft.machine.block.BlockPoweredGrinder;
import com.zeher.orecraft.machine.container.ContainerPoweredGrinder;
import com.zeher.trzlib.api.TRZUtil;
import com.zeher.trzlib.api.value.TRZItemPowerValues;

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

public class TileEntityPoweredGrinder extends TileEntityLockable implements ITickable, ISidedInventory {
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	private NonNullList<ItemStack> grinderItemStacks = NonNullList.<ItemStack>withSize(6, ItemStack.EMPTY);

	private int grinderGrindTime;
	private int currentItemGrindTime;
	private int grindTime;
	private int totalCookTime;
	private String grinderCustomName;

	public int _power;
	public int _max_power = 120000;
	public int grindSpeed = 200;

	public int getSizeInventory() {
		return this.grinderItemStacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.grinderItemStacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.grinderItemStacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.grinderItemStacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.grinderItemStacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.grinderItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack)
				&& ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.grinderItemStacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.totalCookTime = this.getCookTime(stack);
			this.grindTime = 0;
			this.markDirty();
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.grinderCustomName : "Powered Grinder";
	}

	public boolean hasCustomName() {
		return this.grinderCustomName != null && !this.grinderCustomName.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.grinderCustomName = p_145951_1_;
	}

	public static void registerFixesGrinder(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY,
				new ItemStackDataLists(TileEntityPoweredGrinder.class, new String[] { "Items" }));
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.grinderItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.grinderItemStacks);
		this.grinderGrindTime = compound.getInteger("GrindTime");
		this.grindTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this._power = compound.getInteger("power");

		if (compound.hasKey("CustomName", 8)) {
			this.grinderCustomName = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("GrindTime", (short) this.grinderGrindTime);
		compound.setInteger("CookTime", (short) this.grindTime);
		compound.setInteger("CookTimeTotal", (short) this.totalCookTime);
		compound.setInteger("power", this._power);
		ItemStackHelper.saveAllItems(compound, this.grinderItemStacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.grinderCustomName);
		}
		return compound;
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isGrinding() {
		return this.grinderGrindTime > 0;
	}

	public boolean isCooking() {
		return this.grindTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isGrinding(IInventory inventory) {
		return inventory.getField(0) > 0;
	}

	public void update() {
		boolean flag = this.isCooking();
		boolean flag1 = false;

		if (!this.grinderItemStacks.get(3).isEmpty()) {
			grindSpeed();
		} else {
			grindSpeed();
		}
		if (!this.grinderItemStacks.get(4).isEmpty()) {
			powerSlot();
		} else {
			powerSlot();
		}

		if ((hasPower() && (isGrinding()))) {
			--this._power;
		}
		if(this.isGrinding()){
			--this.grinderGrindTime;
		}
		
		if (!this.world.isRemote) {
			if ((isItemPower(this.grinderItemStacks.get(1))) && (this._power <= this._max_power
					- TRZItemPowerValues.getItemPower(this.grinderItemStacks.get(1).getItem()))) {
				if (!this.grinderItemStacks.get(1).isItemStackDamageable()) {
					this._power += TRZItemPowerValues.getItemPower(this.grinderItemStacks.get(1).getItem());

					flag1 = true;
					if (this.grinderItemStacks.get(1) != null) {
						this.grinderItemStacks.get(1).setCount(this.grinderItemStacks.get(1).getCount());
						if (this.grinderItemStacks.get(1).getCount() == 0) {
							this.grinderItemStacks.set(1, this.grinderItemStacks.get(1).getItem()
									.getContainerItem(this.grinderItemStacks.get(1)));
						}
					}
				} else if (this.grinderItemStacks.get(1).getItemDamage() < this.grinderItemStacks.get(1)
						.getMaxDamage()) {
					this._power += TRZItemPowerValues.getItemPower(this.grinderItemStacks.get(1).getItem());
					this.grinderItemStacks.set(1,
							new ItemStack(this.grinderItemStacks.get(1).getItem(),
									this.grinderItemStacks.get(1).getCount(),
									this.grinderItemStacks.get(1).getItemDamage() + 1));
				}
			}
			if(!this.isGrinding() && this.hasPower() && this.canGrind()){
				this.grinderGrindTime = grindSpeed;
			}
			if(this.isGrinding()){
				flag1 = true;
			}
			
			if ((hasPower()) && (canGrind()))
		      {
		        this.grindTime += 1;
		        if (this.grindTime == this.grindSpeed)
		        {
		          this.grindTime = 0;
		          grindItem();
		          grindSpeed();
		          powerSlot();
		          flag1 = true;
		        }
		      }
		      else
		      {
		        this.grindTime = 0;
		      }
		      if (flag != this.isGrinding())
		      {
		        flag1 = true;
		        BlockPoweredGrinder.setState(this.isGrinding(), this.world, this.pos);
		      }
			
			if (flag1)
	        {
	            this.markDirty();
	        }

			if ((_power > _max_power) && this.grinderItemStacks.get(4).getCount() == 0) {
				_power = _max_power;
			}
			if ((_power > _max_power) && this.grinderItemStacks.get(4).getCount() == 1) {
				_power = 160000;
			}
			if ((_power > _max_power) && this.grinderItemStacks.get(4).getCount() == 2) {
				_power = 200000;
			}
			if ((_power > _max_power) && this.grinderItemStacks.get(4).getCount() == 3) {
				_power = 240000;
			}
			if ((_power > _max_power) && this.grinderItemStacks.get(4).getCount() == 4) {
				_power = 280000;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.grindTime * par1 / this.grindSpeed;
	}

	@SideOnly(Side.CLIENT)
	public int getGrindTimeRemainingScaled(int par1) {
		if (this.currentItemGrindTime == 0) {
			this.currentItemGrindTime = this.grindSpeed;
		}
		return this.grinderGrindTime * par1 / this.currentItemGrindTime;
	}

	public int getPowerRemainingScaled(int par1) {
		return this._power * par1 / this._max_power;
	}

	public boolean hasPower() {
		return this._power > 0;
	}

	public void grindSpeed() {
		int i = this.grinderItemStacks.get(3).getCount();
		if (i == 0) {
			this.grindSpeed = 200;
		}
		if (i == 1) {
			this.grindSpeed = 190;
		}
		if (i == 2) {
			this.grindSpeed = 180;
		}
		if (i == 3) {
			this.grindSpeed = 170;
		}
		if (i == 4) {
			this.grindSpeed = 160;
		} else {
			this.grindSpeed = 200;
		}
	}

	public void powerSlot() {
		int i = this.grinderItemStacks.get(4).getCount();
		if (i == 0) {
			this._max_power = 120000;
		} else if (i == 1) {
			this._max_power = 160000;
		} else if (i == 2) {
			this._max_power = 200000;
		} else if (i == 3) {
			this._max_power = 240000;
		} else if (i == 4) {
			this._max_power = 280000;
		} else {
			this._max_power = 120000;
		}
	}

	public int getCookTime(ItemStack stack) {
		return grindSpeed;
	}

	private boolean canGrind() {
		if (((ItemStack) this.grinderItemStacks.get(0)).isEmpty()) {
			return false;
		} else {
			ItemStack itemstack = MachineGrinderRecipes.instance()
					.getGrindingResult((ItemStack) this.grinderItemStacks.get(0));

			if (itemstack.isEmpty()) {
				return false;
			} else {
				ItemStack itemstack1 = (ItemStack) this.grinderItemStacks.get(2);
				if (itemstack1.isEmpty())
					return true;
				if (!itemstack1.isItemEqual(itemstack))
					return false;
				int result = itemstack1.getCount() + itemstack.getCount();
				return result <= getInventoryStackLimit() && result <= itemstack1.getMaxStackSize();
			}
		}
	}

	public void grindItem() {
		if (this.canGrind()) {
			this._power -= TRZUtil.machinePowerDrain();
			ItemStack itemstack = (ItemStack) this.grinderItemStacks.get(0);
			ItemStack itemstack1 = MachineGrinderRecipes.instance().getGrindingResult(itemstack);
			ItemStack itemstack2 = (ItemStack) this.grinderItemStacks.get(2);

			if (itemstack2.isEmpty()) {
				this.grinderItemStacks.set(2, itemstack1.copy());
			} else if (itemstack2.getItem() == itemstack1.getItem()) {
				itemstack2.grow(itemstack1.getCount());
			}

			if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1 && !((ItemStack) this.grinderItemStacks.get(1)).isEmpty() && ((ItemStack) this.grinderItemStacks.get(1)).getItem() == Items.BUCKET) {
				this.grinderItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
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
		return new ContainerPoweredGrinder(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 0:
			return this.grinderGrindTime;
		case 1:
			return this.currentItemGrindTime;
		case 2:
			return this.grindTime;
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
			this.grinderGrindTime = value;
			break;
		case 1:
			this.currentItemGrindTime = value;
			break;
		case 2:
			this.grindTime = value;
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
		this.grinderItemStacks.clear();
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