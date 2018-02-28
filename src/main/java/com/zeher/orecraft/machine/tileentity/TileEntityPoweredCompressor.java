package com.zeher.orecraft.machine.tileentity;

import com.zeher.orecraft.core.handlers.recipe.MachineCompressorRecipes;
import com.zeher.orecraft.machine.block.BlockPoweredCompressor;
import com.zeher.orecraft.machine.container.ContainerPoweredCompressor;
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

public class TileEntityPoweredCompressor extends TileEntityLockable implements ITickable, ISidedInventory {
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	private NonNullList<ItemStack> compressorItemStacks = NonNullList.<ItemStack>withSize(6, ItemStack.EMPTY);

	private int compressorCompressTime;
	private int currentItemBurnTime;
	private int compressTime;
	private int totalCookTime;
	private String compressorCustomName;

	public int _power;
	public int _max_power = 120000;
	public int compressSpeed = 200;

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
			this.compressTime = 0;
			this.markDirty();
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.compressorCustomName : "Powered Compressor";
	}

	public boolean hasCustomName() {
		return this.compressorCustomName != null && !this.compressorCustomName.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.compressorCustomName = p_145951_1_;
	}

	public static void registerFixesCompressor(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY,
				new ItemStackDataLists(TileEntityPoweredCompressor.class, new String[] { "Items" }));
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.compressorItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.compressorItemStacks);
		this.compressorCompressTime = compound.getInteger("BurnTime");
		this.compressTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this._power = compound.getInteger("power");

		if (compound.hasKey("CustomName", 8)) {
			this.compressorCustomName = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short) this.compressorCompressTime);
		compound.setInteger("CookTime", (short) this.compressTime);
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

	public boolean isCompressing() {
		return this.compressorCompressTime > 0;
	}

	public boolean isCooking() {
		return this.compressTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory) {
		return inventory.getField(0) > 0;
	}

	public void update() {
		boolean flag = this.isCompressing();
		boolean flag1 = false;

		if (!this.compressorItemStacks.get(3).isEmpty()) {
			compressSpeed();
		} else {
			compressSpeed();
		}
		if (!this.compressorItemStacks.get(4).isEmpty()) {
			powerSlot();
		} else {
			powerSlot();
		}

		if ((hasPower() && (isCompressing()))) {
			--this._power;
		}
		
		if(this.isCompressing()){
			--this.compressorCompressTime;
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
				} else if (this.compressorItemStacks.get(1).getItemDamage() < this.compressorItemStacks.get(1).getMaxDamage()) {
					this._power += TRZItemPowerValues.getItemPower(this.compressorItemStacks.get(1).getItem());
					this.compressorItemStacks.set(1, new ItemStack(this.compressorItemStacks.get(1).getItem(), this.compressorItemStacks.get(1).getCount(), this.compressorItemStacks.get(1).getItemDamage() + 1));
				}
			}
			if(!this.isCompressing() && this.hasPower() && this.canCompress()){
				this.compressorCompressTime = compressSpeed;
			}
			if(this.isCompressing()){
				flag1 = true;
			}
			
			if ((hasPower()) && (canCompress()))
		      {
		        this.compressTime += 1;
		        if (this.compressTime == this.compressSpeed)
		        {
		          this.compressTime = 0;
		          compressItem();
		          compressSpeed();
		          powerSlot();
		          flag1 = true;
		        }
		      }
		      else
		      {
		        this.compressTime = 0;
		      }
		      if (flag != this.isCompressing())
		      {
		        flag1 = true;
		        BlockPoweredCompressor.setState(this.isCompressing(), this.world, this.pos);
		      }
			
			if (flag1)
	        {
	            this.markDirty();
	        }
			
			if ((_power > _max_power) && this.compressorItemStacks.get(4).getCount() == 0) {
				_power = _max_power;
			}
			if ((_power > _max_power) && this.compressorItemStacks.get(4).getCount() == 1) {
				_power = 160000;
			}
			if ((_power > _max_power) && this.compressorItemStacks.get(4).getCount() == 2) {
				_power = 200000;
			}
			if ((_power > _max_power) && this.compressorItemStacks.get(4).getCount() == 3) {
				_power = 240000;
			}
			if ((_power > _max_power) && this.compressorItemStacks.get(4).getCount() == 4) {
				_power = 280000;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.compressTime * par1 / this.compressSpeed;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentItemBurnTime == 0) {
			this.currentItemBurnTime = this.compressSpeed;
		}
		return this.compressorCompressTime * par1 / this.currentItemBurnTime;
	}

	public int getPowerRemainingScaled(int par1) {
		return this._power * par1 / this._max_power;
	}

	public boolean hasPower() {
		return this._power > 0;
	}

	public void compressSpeed() {
		int i = this.compressorItemStacks.get(3).getCount();
		if (i == 0) {
			this.compressSpeed = 200;
		}
		if (i == 1) {
			this.compressSpeed = 190;
		}
		if (i == 2) {
			this.compressSpeed = 180;
		}
		if (i == 3) {
			this.compressSpeed = 170;
		}
		if (i == 4) {
			this.compressSpeed = 160;
		} else {
			this.compressSpeed = 200;
		}
	}

	public void powerSlot() {
		int i = this.compressorItemStacks.get(4).getCount();
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
		return compressSpeed;
	}

	private boolean canCompress() {
		if (((ItemStack) this.compressorItemStacks.get(0)).isEmpty()) {
			return false;
		} else {
			ItemStack itemstack = MachineCompressorRecipes.instance()
					.getCompressingResult((ItemStack) this.compressorItemStacks.get(0));

			if (itemstack.isEmpty()) {
				return false;
			} else {
				ItemStack itemstack1 = (ItemStack) this.compressorItemStacks.get(2);
				if (itemstack1.isEmpty())
					return true;
				if (!itemstack1.isItemEqual(itemstack))
					return false;
				int result = itemstack1.getCount() + itemstack.getCount();
				return result <= getInventoryStackLimit() && result <= itemstack1.getMaxStackSize();
			}
		}
	}

	public void compressItem() {
		if (this.canCompress()) {
			this._power -= TRZUtil.machinePowerDrain();
			ItemStack itemstack = (ItemStack) this.compressorItemStacks.get(0);
			ItemStack itemstack1 = MachineCompressorRecipes.instance().getCompressingResult(itemstack);
			ItemStack itemstack2 = (ItemStack) this.compressorItemStacks.get(2);

			if (itemstack2.isEmpty()) {
				this.compressorItemStacks.set(2, itemstack1.copy());
			} else if (itemstack2.getItem() == itemstack1.getItem()) {
				itemstack2.grow(itemstack1.getCount());
			}

			if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1
					&& !((ItemStack) this.compressorItemStacks.get(1)).isEmpty()
					&& ((ItemStack) this.compressorItemStacks.get(1)).getItem() == Items.BUCKET) {
				this.compressorItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
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
		return new ContainerPoweredCompressor(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 0:
			return this.compressorCompressTime;
		case 1:
			return this.currentItemBurnTime;
		case 2:
			return this.compressTime;
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
			this.compressorCompressTime = value;
			break;
		case 1:
			this.currentItemBurnTime = value;
			break;
		case 2:
			this.compressTime = value;
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