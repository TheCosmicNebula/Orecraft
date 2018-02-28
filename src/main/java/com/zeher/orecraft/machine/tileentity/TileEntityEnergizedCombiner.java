package com.zeher.orecraft.machine.tileentity;

import java.util.List;

import com.google.common.collect.Lists;
import com.zeher.orecraft.core.handlers.recipe.MachineCombinerCraftingHandler;
import com.zeher.orecraft.machine.block.BlockEnergizedCombiner;
import com.zeher.orecraft.machine.container.ContainerEnergizedCombiner;
import com.zeher.orecraft.storage.util.StorageUtil;
import com.zeher.trzlib.api.TRZUtil;
import com.zeher.trzlib.api.value.TRZItemPowerValues;
import com.zeher.trzlib.api.value.TRZMachineValues;

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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityEnergizedCombiner extends TileEntityLockable implements ITickable, ISidedInventory, IInventory, IFluidHandler {
	
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	private NonNullList<ItemStack> combinerItemStacks = NonNullList.<ItemStack>withSize(19, ItemStack.EMPTY);

	private int fluid_capacity = 16000;

	public FluidTank tank = new FluidTank(fluid_capacity);
	private boolean needsUpdate = false;
	private int updateTimer = 0;

	public int fill_level;
	
	private int combinerBurnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;
	private String combinerCustomName;

	public int stored;
	public int capacity = 500000;
	public int cookSpeed = 80;
	
	public int mode = 0;
	
	public int input_rate = TRZMachineValues.energizedInputRate();

	public int getSizeInventory() {
		return this.combinerItemStacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.combinerItemStacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.combinerItemStacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.combinerItemStacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.combinerItemStacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.combinerItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.combinerItemStacks.set(index, stack);

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
		return this.hasCustomName() ? this.combinerCustomName : "Energized Combiner";
	}

	public boolean hasCustomName() {
		return this.combinerCustomName != null && !this.combinerCustomName.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.combinerCustomName = p_145951_1_;
	}

	public static void registerFixesCombiner(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY,
				new ItemStackDataLists(TileEntityEnergizedCombiner.class, new String[] { "Items" }));
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.combinerItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.combinerItemStacks);
		this.combinerBurnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.stored = compound.getInteger("power");

		if (compound.hasKey("CustomName", 8)) {
			this.combinerCustomName = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short) this.combinerBurnTime);
		compound.setInteger("CookTime", (short) this.cookTime);
		compound.setInteger("CookTimeTotal", (short) this.totalCookTime);
		compound.setInteger("power", this.stored);
		ItemStackHelper.saveAllItems(compound, this.combinerItemStacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.combinerCustomName);
		}

		return compound;
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isBurning() {
		return this.combinerBurnTime > 0;
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

		if (!this.combinerItemStacks.get(10).isEmpty()) {
			smeltSpeed();
		} else {
			smeltSpeed();
		}
		if (!this.combinerItemStacks.get(11).isEmpty()) {
			powerSlot();
		} else {
			powerSlot();
		}

		if ((hasPower() && (isSmelting()))) {
			--this.stored;
		}

		if (this.isBurning()) {
			--this.combinerBurnTime;
		}

		if (!this.world.isRemote) {
			if ((isItemPower(this.combinerItemStacks.get(13))) && (this.stored <= this.capacity
					- TRZItemPowerValues.getItemPower(this.combinerItemStacks.get(13).getItem()))) {
				if (!this.combinerItemStacks.get(13).isItemStackDamageable()) {
					this.stored += TRZItemPowerValues.getItemPower(this.combinerItemStacks.get(13).getItem());

					flag1 = true;
					if (this.combinerItemStacks.get(13) != null) {
						this.combinerItemStacks.get(13).setCount(this.combinerItemStacks.get(13).getCount());
						if (this.combinerItemStacks.get(13).getCount() == 0) {
							this.combinerItemStacks.set(13, this.combinerItemStacks.get(13).getItem()
									.getContainerItem(this.combinerItemStacks.get(1)));
						}
					}
				} else if (this.combinerItemStacks.get(13).getItemDamage() < this.combinerItemStacks.get(13)
						.getMaxDamage()) {
					this.stored += TRZItemPowerValues.getItemPower(this.combinerItemStacks.get(13).getItem());
					this.combinerItemStacks.set(13,
							new ItemStack(this.combinerItemStacks.get(13).getItem(),
									this.combinerItemStacks.get(13).getCount(),
									this.combinerItemStacks.get(13).getItemDamage() + 1));
				}
			}
			if (!this.isBurning() && this.hasPower() && this.canSmelt()) {
				this.combinerBurnTime = cookSpeed;
			}
			if (this.isBurning()) {
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
				BlockEnergizedCombiner.setState(!this.isBurning(), this.world, this.pos);
			}

			if (flag1) {
				this.markDirty();
			}
			if ((stored > capacity) && this.combinerItemStacks.get(11).getCount() == 0) {
				stored = capacity;
			}
			if ((stored > capacity) && this.combinerItemStacks.get(11).getCount() == 1) {
				stored = 540000;
			}
			if ((stored > capacity) && this.combinerItemStacks.get(11).getCount() == 2) {
				stored = 580000;
			}
			if ((stored > capacity) && this.combinerItemStacks.get(11).getCount() == 3) {
				stored = 620000;
			}
			if ((stored > capacity) && this.combinerItemStacks.get(11).getCount() == 4) {
				stored = 660000;
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
		return this.combinerBurnTime * par1 / this.currentItemBurnTime;
	}

	public int getPowerRemainingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}

	public boolean hasPower() {
		return this.stored > 0;
	}

	public boolean isSmelting() {
		return this.combinerBurnTime > 0;
	}

	public void smeltSpeed() {
		int i = this.combinerItemStacks.get(10).getCount();
		if (i == 0) {
			this.cookSpeed = 80;
		}
		if (i == 1) {
			this.cookSpeed = 75;
		}
		if (i == 2) {
			this.cookSpeed = 60;
		}
		if (i == 3) {
			this.cookSpeed = 65;
		}
		if (i == 4) {
			this.cookSpeed = 60;
		} else {
			this.cookSpeed = 80;
		}
	}

	public void powerSlot() {
		int i = this.combinerItemStacks.get(11).getCount();
		if (i == 0) {
			this.capacity = 500000;
		} else if (i == 1) {
			this.capacity = 540000;
		} else if (i == 2) {
			this.capacity = 580000;
		} else if (i == 3) {
			this.capacity = 620000;
		} else if (i == 4) {
			this.capacity = 660000;
		} else {
			this.capacity = 500000;
		}
	}

	public int getCookTime(ItemStack stack) {
		return cookSpeed;
	}

	private boolean canSmelt() {
		if (((ItemStack) this.combinerItemStacks.get(0)).isEmpty()
				&& ((ItemStack) this.combinerItemStacks.get(1)).isEmpty()
				&& ((ItemStack) this.combinerItemStacks.get(2)).isEmpty()
				&& ((ItemStack) this.combinerItemStacks.get(3)).isEmpty()
				&& ((ItemStack) this.combinerItemStacks.get(4)).isEmpty()
				&& ((ItemStack) this.combinerItemStacks.get(5)).isEmpty()
				&& ((ItemStack) this.combinerItemStacks.get(6)).isEmpty()
				&& ((ItemStack) this.combinerItemStacks.get(7)).isEmpty()
				&& ((ItemStack) this.combinerItemStacks.get(8)).isEmpty()) {
			return false;
		}
		if (!(this.combinerItemStacks.get(0).isEmpty())) {
			if (!(this.combinerItemStacks.get(4).isEmpty()) && !(this.combinerItemStacks.get(8).isEmpty())
					&& (this.combinerItemStacks.get(1).isEmpty()) && (this.combinerItemStacks.get(2).isEmpty())
					&& (this.combinerItemStacks.get(3).isEmpty()) && (this.combinerItemStacks.get(5).isEmpty())
					&& (this.combinerItemStacks.get(6).isEmpty()) && (this.combinerItemStacks.get(7).isEmpty())) {
				List<ItemStack> list = Lists.<ItemStack>newArrayList();
				list.add(this.combinerItemStacks.get(4));
				list.add(this.combinerItemStacks.get(8));
				ItemStack stack = MachineCombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
				if (stack.isEmpty()) {
					return false;
				} else {
					ItemStack outputStack = this.combinerItemStacks.get(9);
					if (outputStack.isEmpty()) {
						return true;
					}
					if (!(outputStack.isItemEqual(stack))) {
						return false;
					}
					int result = outputStack.getCount() + stack.getCount();
					return result <= getInventoryStackLimit() && result <= outputStack.getMaxStackSize();
				}
			}
			if (!this.combinerItemStacks.get(1).isEmpty() && !(this.combinerItemStacks.get(3).isEmpty())
					&& !(this.combinerItemStacks.get(5).isEmpty()) && !this.combinerItemStacks.get(7).isEmpty()
					&& this.combinerItemStacks.get(2).isEmpty() && this.combinerItemStacks.get(4).isEmpty()
					&& this.combinerItemStacks.get(6).isEmpty() && this.combinerItemStacks.get(8).isEmpty()) {
				List<ItemStack> list = Lists.<ItemStack>newArrayList();
				list.add(this.combinerItemStacks.get(1));
				list.add(this.combinerItemStacks.get(3));
				list.add(this.combinerItemStacks.get(5));
				list.add(this.combinerItemStacks.get(7));

				ItemStack stack = MachineCombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
				if (stack.isEmpty()) {
					return false;
				} else {
					ItemStack outputStack = this.combinerItemStacks.get(9);
					if (outputStack.isEmpty()) {
						return true;
					}
					if (!(outputStack.isItemEqual(stack))) {
						return false;
					}
					int result = outputStack.getCount() + stack.getCount();
					return result <= getInventoryStackLimit() && result <= outputStack.getMaxStackSize();
				}
			}
			if (!this.combinerItemStacks.get(2).isEmpty() && !(this.combinerItemStacks.get(4).isEmpty())
					&& !(this.combinerItemStacks.get(6).isEmpty()) && !this.combinerItemStacks.get(8).isEmpty()
					&& this.combinerItemStacks.get(1).isEmpty() && this.combinerItemStacks.get(3).isEmpty()
					&& this.combinerItemStacks.get(5).isEmpty() && this.combinerItemStacks.get(7).isEmpty()) {
				List<ItemStack> list = Lists.<ItemStack>newArrayList();
				list.add(this.combinerItemStacks.get(2));
				list.add(this.combinerItemStacks.get(4));
				list.add(this.combinerItemStacks.get(6));
				list.add(this.combinerItemStacks.get(8));

				ItemStack stack = MachineCombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
				if (stack.isEmpty()) {
					return false;
				} else {
					ItemStack outputStack = this.combinerItemStacks.get(9);
					if (outputStack.isEmpty()) {
						return true;
					}
					if (!(outputStack.isItemEqual(stack))) {
						return false;
					}
					int result = outputStack.getCount() + stack.getCount();
					return result <= getInventoryStackLimit() && result <= outputStack.getMaxStackSize();
				}
			}
			if (!this.combinerItemStacks.get(1).isEmpty() && !(this.combinerItemStacks.get(3).isEmpty())
					&& !(this.combinerItemStacks.get(5).isEmpty()) && !this.combinerItemStacks.get(7).isEmpty()
					&& !this.combinerItemStacks.get(2).isEmpty() && !this.combinerItemStacks.get(4).isEmpty()
					&& !this.combinerItemStacks.get(6).isEmpty() && !this.combinerItemStacks.get(8).isEmpty()) {
				List<ItemStack> list = Lists.<ItemStack>newArrayList();
				list.add(this.combinerItemStacks.get(1));
				list.add(this.combinerItemStacks.get(2));
				list.add(this.combinerItemStacks.get(3));
				list.add(this.combinerItemStacks.get(4));
				list.add(this.combinerItemStacks.get(5));
				list.add(this.combinerItemStacks.get(6));
				list.add(this.combinerItemStacks.get(7));
				list.add(this.combinerItemStacks.get(8));

				ItemStack stack = MachineCombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
				if (stack.isEmpty()) {
					return false;
				} else {
					ItemStack outputStack = this.combinerItemStacks.get(9);
					if (outputStack.isEmpty()) {
						return true;
					}
					if (!(outputStack.isItemEqual(stack))) {
						return false;
					}
					int result = outputStack.getCount() + stack.getCount();
					return result <= getInventoryStackLimit() && result <= outputStack.getMaxStackSize();
				}
			}
			return false;
		}
		return false;
	}

	public void smeltItem() {
		ItemStack siliconStack = this.combinerItemStacks.get(0);
		if (this.canSmelt() && !(siliconStack.isEmpty())) {
			if (!(this.combinerItemStacks.get(4).isEmpty()) && !(this.combinerItemStacks.get(8).isEmpty())
					&& (this.combinerItemStacks.get(1).isEmpty()) && (this.combinerItemStacks.get(2).isEmpty())
					&& (this.combinerItemStacks.get(3).isEmpty()) && (this.combinerItemStacks.get(5).isEmpty())
					&& (this.combinerItemStacks.get(6).isEmpty()) && (this.combinerItemStacks.get(7).isEmpty())) {
				this.stored -= TRZUtil.machinePowerDrain();
				List<ItemStack> list = Lists.<ItemStack>newArrayList();
				list.add(this.combinerItemStacks.get(4));
				list.add(this.combinerItemStacks.get(8));
				ItemStack inputStackOne = this.combinerItemStacks.get(4);
				ItemStack inputStackTwo = this.combinerItemStacks.get(8);
				ItemStack outputStack = this.combinerItemStacks.get(9);
				ItemStack resultStack = MachineCombinerCraftingHandler.getInstance().findMatchingRecipe(list,
						this.world);

				if (outputStack.isEmpty()) {
					this.combinerItemStacks.set(9, resultStack.copy());
				} else if (resultStack.getItem().equals(outputStack.getItem())) {
					outputStack.grow(resultStack.getCount());
				}
				inputStackOne.shrink(1);
				inputStackTwo.shrink(1);
				siliconStack.shrink(2);
			}
			if (!(this.combinerItemStacks.get(1).isEmpty()) && !(this.combinerItemStacks.get(3).isEmpty())
					&& !(this.combinerItemStacks.get(5).isEmpty()) && !(this.combinerItemStacks.get(7).isEmpty())
					&& this.combinerItemStacks.get(2).isEmpty() && this.combinerItemStacks.get(4).isEmpty()
					&& this.combinerItemStacks.get(6).isEmpty() && this.combinerItemStacks.get(8).isEmpty()) {
				this.stored -= TRZUtil.machinePowerDrain();
				List<ItemStack> list = Lists.<ItemStack>newArrayList();
				list.add(this.combinerItemStacks.get(1));
				list.add(this.combinerItemStacks.get(3));
				list.add(this.combinerItemStacks.get(5));
				list.add(this.combinerItemStacks.get(7));
				ItemStack inputStackOne = this.combinerItemStacks.get(1);
				ItemStack inputStackTwo = this.combinerItemStacks.get(3);
				ItemStack inputStackThree = this.combinerItemStacks.get(5);
				ItemStack inputStackFour = this.combinerItemStacks.get(7);
				ItemStack outputStack = this.combinerItemStacks.get(9);
				ItemStack resultStack = MachineCombinerCraftingHandler.getInstance().findMatchingRecipe(list,
						this.world);

				if (outputStack.isEmpty()) {
					this.combinerItemStacks.set(9, resultStack.copy());
				} else if (resultStack.getItem().equals(outputStack.getItem())) {
					outputStack.grow(resultStack.getCount());
				}
				inputStackOne.shrink(1);
				inputStackTwo.shrink(1);
				inputStackThree.shrink(1);
				inputStackFour.shrink(1);
				siliconStack.shrink(2);
			}
			if (!(this.combinerItemStacks.get(2).isEmpty()) && !(this.combinerItemStacks.get(4).isEmpty())
					&& !(this.combinerItemStacks.get(6).isEmpty()) && !(this.combinerItemStacks.get(8).isEmpty())
					&& this.combinerItemStacks.get(1).isEmpty() && this.combinerItemStacks.get(3).isEmpty()
					&& this.combinerItemStacks.get(5).isEmpty() && this.combinerItemStacks.get(7).isEmpty()) {
				this.stored -= TRZUtil.machinePowerDrain();
				List<ItemStack> list = Lists.<ItemStack>newArrayList();
				list.add(this.combinerItemStacks.get(2));
				list.add(this.combinerItemStacks.get(4));
				list.add(this.combinerItemStacks.get(6));
				list.add(this.combinerItemStacks.get(8));
				ItemStack inputStackOne = this.combinerItemStacks.get(1);
				ItemStack inputStackTwo = this.combinerItemStacks.get(3);
				ItemStack inputStackThree = this.combinerItemStacks.get(5);
				ItemStack inputStackFour = this.combinerItemStacks.get(7);
				ItemStack outputStack = this.combinerItemStacks.get(9);
				ItemStack resultStack = MachineCombinerCraftingHandler.getInstance().findMatchingRecipe(list,
						this.world);

				if (outputStack.isEmpty()) {
					this.combinerItemStacks.set(9, resultStack.copy());
				} else if (resultStack.getItem().equals(outputStack.getItem())) {
					outputStack.grow(resultStack.getCount());
				}
				inputStackOne.shrink(1);
				inputStackTwo.shrink(1);
				inputStackThree.shrink(1);
				inputStackFour.shrink(1);
				siliconStack.shrink(2);
			}
			if (!(this.combinerItemStacks.get(1).isEmpty()) && !(this.combinerItemStacks.get(3).isEmpty())
					&& !(this.combinerItemStacks.get(5).isEmpty()) && !(this.combinerItemStacks.get(7).isEmpty())
					&& !this.combinerItemStacks.get(2).isEmpty() && !this.combinerItemStacks.get(4).isEmpty()
					&& !this.combinerItemStacks.get(6).isEmpty() && !this.combinerItemStacks.get(8).isEmpty()) {
				this.stored -= TRZUtil.machinePowerDrain();
				List<ItemStack> list = Lists.<ItemStack>newArrayList();
				list.add(this.combinerItemStacks.get(1));
				list.add(this.combinerItemStacks.get(2));
				list.add(this.combinerItemStacks.get(3));
				list.add(this.combinerItemStacks.get(4));
				list.add(this.combinerItemStacks.get(5));
				list.add(this.combinerItemStacks.get(6));
				list.add(this.combinerItemStacks.get(7));
				list.add(this.combinerItemStacks.get(8));
				ItemStack inputStackOne = this.combinerItemStacks.get(1);
				ItemStack inputStackTwo = this.combinerItemStacks.get(2);
				ItemStack inputStackThree = this.combinerItemStacks.get(3);
				ItemStack inputStackFour = this.combinerItemStacks.get(4);
				ItemStack inputStackFive = this.combinerItemStacks.get(5);
				ItemStack inputStackSix = this.combinerItemStacks.get(6);
				ItemStack inputStackSeven = this.combinerItemStacks.get(7);
				ItemStack inputStackEight = this.combinerItemStacks.get(8);
				ItemStack outputStack = this.combinerItemStacks.get(9);
				ItemStack resultStack = MachineCombinerCraftingHandler.getInstance().findMatchingRecipe(list,
						this.world);

				if (outputStack.isEmpty()) {
					this.combinerItemStacks.set(9, resultStack.copy());
				} else if (resultStack.getItem().equals(outputStack.getItem())) {
					outputStack.grow(resultStack.getCount());
				}
				inputStackOne.shrink(1);
				inputStackTwo.shrink(1);
				inputStackThree.shrink(1);
				inputStackFour.shrink(1);
				inputStackFive.shrink(1);
				inputStackSix.shrink(1);
				inputStackSeven.shrink(1);
				inputStackEight.shrink(1);
				siliconStack.shrink(2);
			}
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
		return new ContainerEnergizedCombiner(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 0:
			return this.combinerBurnTime;
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
			this.combinerBurnTime = value;
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
		this.combinerItemStacks.clear();
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
	
	public int getFluidLevelScaled(int one) {
		return this.tank.getFluidAmount() * one / this.tank.getCapacity();
	}
	
	public int getFillLevel() {
		return this.fill_level;
	}

	public void updateFillLevel() {
		this.fill_level = this.getCurrentStoredAmount() / 1000;
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

	public int fill(FluidStack resource, boolean doFill) {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(world, pos);
		needsUpdate = true;
		this.markDirty();
		return this.tank.fill(resource, doFill);
	}

	public FluidStack drain(FluidStack resource, boolean doDrain) {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(world, pos);
		this.markDirty();
		return this.tank.drain(resource.amount, doDrain);
	}

	public FluidStack drain(int maxDrain, boolean doDrain) {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(world, pos);
		needsUpdate = true;
		this.markDirty();
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

	public FluidTank getTank() {
		return this.tank;
	}
	
	public int getMode(){
		return this.mode;
	}
	
	public void setMode(int set){
		this.mode = set;
	}
	
	public void cycleMode(){
		if(this.mode < 2){
			this.mode++;
		} else {
			this.mode = 0;
		}
	}
}