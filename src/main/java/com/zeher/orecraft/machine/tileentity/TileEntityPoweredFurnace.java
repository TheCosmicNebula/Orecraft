package com.zeher.orecraft.machine.tileentity;

import com.zeher.orecraft.machine.block.BlockPoweredFurnace;
import com.zeher.orecraft.machine.container.ContainerPoweredFurnace;
import com.zeher.trzlib.api.TRZUtil;
import com.zeher.trzlib.api.value.TRZItemPowerValues;
import com.zeher.trzlib.api.value.TRZMachineValues;
import com.zeher.trzlib.machine.TRZIMachine;
import com.zeher.trzlib.machine.TRZTileEntityMachine;

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
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
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

public class TileEntityPoweredFurnace extends TRZTileEntityMachine implements ITickable, ISidedInventory, TRZIMachine {
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack>withSize(6, ItemStack.EMPTY);

	private int furnaceBurnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;
	private String furnaceCustomName;
	
	public int stored;
	public int capacity = TRZMachineValues.poweredBasicStored(0);
	public int input_rate = TRZMachineValues.poweredInputRate();
	
	public int cookSpeed = 200;

	public int getSizeInventory() {
		return this.furnaceItemStacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.furnaceItemStacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.furnaceItemStacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.furnaceItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack)
				&& ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.furnaceItemStacks.set(index, stack);

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
		return this.hasCustomName() ? this.furnaceCustomName : "Powered Furnace";
	}

	public boolean hasCustomName() {
		return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.furnaceCustomName = p_145951_1_;
	}

	public static void registerFixesFurnace(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY,
				new ItemStackDataLists(TileEntityPoweredFurnace.class, new String[] { "Items" }));
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.furnaceItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);
		this.furnaceBurnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.stored = compound.getInteger("power");

		if (compound.hasKey("CustomName", 8)) {
			this.furnaceCustomName = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short) this.furnaceBurnTime);
		compound.setInteger("CookTime", (short) this.cookTime);
		compound.setInteger("CookTimeTotal", (short) this.totalCookTime);
		compound.setInteger("power", this.stored);
		ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.furnaceCustomName);
		}

		return compound;
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isBurning() {
		return this.furnaceBurnTime > 0;
	}

	public boolean isCooking() {
		return this.cookTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory) {
		return inventory.getField(0) > 0;
	}

	public void update() {
		boolean flag = this.isCooking();
		boolean flag1 = false;

		if (!this.furnaceItemStacks.get(3).isEmpty()) {
			smeltSpeed();
		} else {
			smeltSpeed();
		}
		if (!this.furnaceItemStacks.get(4).isEmpty()) {
			powerSlot();
		} else {
			powerSlot();
		}

		if ((hasStored() && (isSmelting()))) {
			--this.stored;
		}
		if(this.isBurning()){
			--this.furnaceBurnTime;
		}
		
		if (!this.world.isRemote) {
			if ((isItemPower(this.furnaceItemStacks.get(1))) && (this.stored <= this.capacity
					- TRZItemPowerValues.getItemPower(this.furnaceItemStacks.get(1).getItem()))) {
				if (!this.furnaceItemStacks.get(1).isItemStackDamageable()) {
					this.stored += TRZItemPowerValues.getItemPower(this.furnaceItemStacks.get(1).getItem());

					flag1 = true;
					if (this.furnaceItemStacks.get(1) != null) {
						this.furnaceItemStacks.get(1).setCount(this.furnaceItemStacks.get(1).getCount());
						if (this.furnaceItemStacks.get(1).getCount() == 0) {
							this.furnaceItemStacks.set(1, this.furnaceItemStacks.get(1).getItem()
									.getContainerItem(this.furnaceItemStacks.get(1)));
						}
					}
				} else if (this.furnaceItemStacks.get(1).getItemDamage() < this.furnaceItemStacks.get(1)
						.getMaxDamage()) {
					this.stored += TRZItemPowerValues.getItemPower(this.furnaceItemStacks.get(1).getItem());
					this.furnaceItemStacks.set(1,
							new ItemStack(this.furnaceItemStacks.get(1).getItem(),
									this.furnaceItemStacks.get(1).getCount(),
									this.furnaceItemStacks.get(1).getItemDamage() + 1));
				}
			}
			if(!this.isBurning() && this.hasStored() && this.canSmelt()){
				this.furnaceBurnTime = cookSpeed;
			}
			if(this.isBurning()){
				flag1 = true;
			}
			
			if ((hasStored()) && (canSmelt()))
		      {
		        this.cookTime += 1;
		        if (this.cookTime == this.cookSpeed)
		        {
		          this.cookTime = 0;
		          smeltItem();
		          smeltSpeed();
		          powerSlot();
		          flag1 = true;
		        }
		      }
		      else
		      {
		        this.cookTime = 0;
		      }
		      if (flag != this.isBurning())
		      {
		        flag1 = true;
		        BlockPoweredFurnace.setState(this.isBurning(), this.world, this.pos);
		      }
			
			if (flag1)
	        {
	            this.markDirty();
	        }
			if ((stored > capacity) && this.furnaceItemStacks.get(4).getCount() == 0) {
				stored = capacity;
			}
			if ((stored > capacity) && this.furnaceItemStacks.get(4).getCount() == 1) {
				stored = 160000;
			}
			if ((stored > capacity) && this.furnaceItemStacks.get(4).getCount() == 2) {
				stored = 200000;
			}
			if ((stored > capacity) && this.furnaceItemStacks.get(4).getCount() == 3) {
				stored = 240000;
			}
			if ((stored > capacity) && this.furnaceItemStacks.get(4).getCount() == 4) {
				stored = 280000;
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
		return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
	}

	public int getPowerRemainingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}

	public boolean isSmelting() {
		return this.furnaceBurnTime > 0;
	}

	public void smeltSpeed() {
		int i = this.furnaceItemStacks.get(3).getCount();
		if (i == 0) {
			this.cookSpeed = 200;
		}
		if (i == 1) {
			this.cookSpeed = 190;
		}
		if (i == 2) {
			this.cookSpeed = 180;
		}
		if (i == 3) {
			this.cookSpeed = 170;
		}
		if (i == 4) {
			this.cookSpeed = 160;
		} else {
			this.cookSpeed = 200;
		}
	}

	public void powerSlot() {
		int i = this.furnaceItemStacks.get(4).getCount();
		if (i == 0) {
			this.capacity = TRZMachineValues.poweredBasicStored(0);
		} else if (i == 1) {
			this.capacity = TRZMachineValues.poweredBasicStored(1);
		} else if (i == 2) {
			this.capacity = TRZMachineValues.poweredBasicStored(2);
		} else if (i == 3) {
			this.capacity = TRZMachineValues.poweredBasicStored(3);
		} else if (i == 4) {
			this.capacity = TRZMachineValues.poweredBasicStored(4);
		} else {
			this.capacity = TRZMachineValues.poweredBasicStored(0);
		}
	}

	public int getCookTime(ItemStack stack) {
		return cookSpeed;
	}

	private boolean canSmelt() {
		if (((ItemStack) this.furnaceItemStacks.get(0)).isEmpty()) {
			return false;
		} else {
			ItemStack itemstack = FurnaceRecipes.instance()
					.getSmeltingResult((ItemStack) this.furnaceItemStacks.get(0));

			if (itemstack.isEmpty()) {
				return false;
			} else {
				ItemStack itemstack1 = (ItemStack) this.furnaceItemStacks.get(2);
				if (itemstack1.isEmpty())
					return true;
				if (!itemstack1.isItemEqual(itemstack))
					return false;
				int result = itemstack1.getCount() + itemstack.getCount();
				return result <= getInventoryStackLimit() && result <= itemstack1.getMaxStackSize();
			}
		}
	}

	public void smeltItem() {
		if (this.canSmelt()) {
			this.stored -= TRZUtil.machinePowerDrain();
			ItemStack itemstack = (ItemStack) this.furnaceItemStacks.get(0);
			ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
			ItemStack itemstack2 = (ItemStack) this.furnaceItemStacks.get(2);

			if (itemstack2.isEmpty()) {
				this.furnaceItemStacks.set(2, itemstack1.copy());
			} else if (itemstack2.getItem() == itemstack1.getItem()) {
				itemstack2.grow(itemstack1.getCount());
			}

			if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1
					&& !((ItemStack) this.furnaceItemStacks.get(1)).isEmpty()
					&& ((ItemStack) this.furnaceItemStacks.get(1)).getItem() == Items.BUCKET) {
				this.furnaceItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
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
		return new ContainerPoweredFurnace(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 0:
			return this.furnaceBurnTime;
		case 1:
			return this.currentItemBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		case 4:
			return this.stored;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.furnaceBurnTime = value;
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
			this.stored = value;
		}
	}

	public int getFieldCount() {
		return 5;
	}

	public void clear() {
		this.furnaceItemStacks.clear();
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

	@Override
	public int getStored() {
		return stored;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public int getInputRate() {
		return input_rate;
	}

	@Override
	public boolean hasStored() {
		return stored > 0;
	}
	
	public void addStored(int add){
		this.stored += add;
	}

}