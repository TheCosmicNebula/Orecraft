package com.zeher.orecraft.machine.core.tileentity;

import java.util.Random;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.core.container.ContainerEnergizedExtractor;
import com.zeher.orecraft.machine.core.recipe.ExtractorRecipes;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.value.ItemPowerValues;
import com.zeher.zeherlib.machine.IMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityEnergizedExtractor extends TileEntityLockable implements ITickable, ISidedInventory, IMachine {
	
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	
	private NonNullList<ItemStack> extractor_stacks = NonNullList.<ItemStack>withSize(8, ItemStack.EMPTY);

	private String custom_name;

	private int cook_time_one;
	private int cook_time_two;

	private int stored;
	private int capacity = Reference.VALUE.MACHINE.energizedStored(0);
	public int input_rate = Reference.VALUE.MACHINE.ENERGIZED_INPUT_RATE;
	
	private int cook_speed = Reference.VALUE.MACHINE.energizedProcessSpeed(0);

	private int sound_timer = 0;
	
	public int getSizeInventory() {
		return this.extractor_stacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.extractor_stacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.extractor_stacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.extractor_stacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.extractor_stacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.extractor_stacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack)
				&& ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.extractor_stacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.markDirty();
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.custom_name : OrecraftReference.GUI_NAME.MACHINE.ENERGIZED.EXTRACTOR;
	}

	public boolean hasCustomName() {
		return this.custom_name != null && !this.custom_name.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.custom_name = p_145951_1_;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.extractor_stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.extractor_stacks);
		this.stored = compound.getInteger("power");

		if (compound.hasKey("CustomName", 8)) {
			this.custom_name = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("power", this.stored);
		ItemStackHelper.saveAllItems(compound, this.extractor_stacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.custom_name);
		}

		return compound;
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void update() {
		if (!this.world.isRemote) {
			if (this.hasStored() && this.canExtractOne()) {
				this.stored -= Reference.VALUE.MACHINE.machineDrainRate(cook_speed);
				this.cook_time_one++;
	
				if (this.cook_time_one == this.cook_speed) {
					this.cook_time_one = 0;
					this.extractItemOne();
				}
			} else {
				this.cook_time_one = 0;
			}
			
			if (this.cook_time_one > this.cook_speed) {
				this.cook_time_one = this.cook_speed;
			}
	
			if (this.hasStored() && this.canExtractTwo()) {
				this.stored -= Reference.VALUE.MACHINE.machineDrainRate(cook_speed);
				this.cook_time_two++;
	
				if (this.cook_time_two == this.cook_speed) {
					this.cook_time_two = 0;
					this.extractItemTwo();
				}
			} else {
				this.cook_time_two = 0;
			}
			
			if (this.cook_time_two > this.cook_speed) {
				this.cook_time_two = this.cook_speed;
			}

			if (this.cook_time_one == 1) {
				//BlockEnergizedExtractor.setState(true, this.world, this.pos);
				this.markDirty();
			}
		}
		
		if (this.stored > this.capacity) {
			this.stored = this.capacity;
		}
		
		int i = this.extractor_stacks.get(5).getCount();
		this.cook_speed = Reference.VALUE.MACHINE.energizedProcessSpeed(i);
		
		int i_one = this.extractor_stacks.get(6).getCount();
		this.capacity = Reference.VALUE.MACHINE.energizedStored(i_one);
		
		if (!this.world.isRemote) {
			if ((isItemPower(this.extractor_stacks.get(1))) && (this.stored <= this.capacity - ItemPowerValues.getItemPower(this.extractor_stacks.get(1).getItem()))) {
				if (!this.extractor_stacks.get(1).isItemStackDamageable()) {
					this.stored += ItemPowerValues.getItemPower(this.extractor_stacks.get(1).getItem());

					this.markDirty();
					if (this.extractor_stacks.get(1) != null) {
						this.extractor_stacks.get(1).setCount(this.extractor_stacks.get(1).getCount());
						if (this.extractor_stacks.get(1).getCount() == 0) {
							this.extractor_stacks.set(1, this.extractor_stacks.get(1).getItem()
									.getContainerItem(this.extractor_stacks.get(1)));
						}
					}
				} else if (this.extractor_stacks.get(1).getItemDamage() < this.extractor_stacks.get(1).getMaxDamage()) {
					this.stored += ItemPowerValues.getItemPower(this.extractor_stacks.get(1).getItem());
					this.extractor_stacks.set(1, new ItemStack(this.extractor_stacks.get(1).getItem(), this.extractor_stacks.get(1).getCount(), this.extractor_stacks.get(1).getItemDamage() + 1));
				}
			}
		}
		
		if (this.hasStored() && this.canExtractOne() || this.hasStored() && this.canExtractTwo()) {
			Random rand = new Random();
			
			if (rand.nextDouble() < 0.1D) {
				this.world.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}
			
			this.world.spawnParticle(EnumParticleTypes.FLAME, this.pos.getX() + 0.2, this.pos.getY() + 0.4, this.pos.getZ() + 0.2, 0.0D, 0.0D, 0.0D, new int[0]);
			this.world.spawnParticle(EnumParticleTypes.FLAME, this.pos.getX() + 0.2, this.pos.getY() + 0.4, this.pos.getZ() + 0.8, 0.0D, 0.0D, 0.0D, new int[0]);
			this.world.spawnParticle(EnumParticleTypes.FLAME, this.pos.getX() + 0.8, this.pos.getY() + 0.4, this.pos.getZ() + 0.2, 0.0D, 0.0D, 0.0D, new int[0]);
			this.world.spawnParticle(EnumParticleTypes.FLAME, this.pos.getX() + 0.8, this.pos.getY() + 0.4, this.pos.getZ() + 0.8, 0.0D, 0.0D, 0.0D, new int[0]);
			
			this.world.spawnParticle(EnumParticleTypes.FLAME, this.pos.getX() + 0.5, this.pos.getY() + 0.4, this.pos.getZ() + 0.2, 0.0D, 0.0D, 0.0D, new int[0]);
			this.world.spawnParticle(EnumParticleTypes.FLAME, this.pos.getX() + 0.5, this.pos.getY() + 0.4, this.pos.getZ() + 0.8, 0.0D, 0.0D, 0.0D, new int[0]);
			this.world.spawnParticle(EnumParticleTypes.FLAME, this.pos.getX() + 0.8, this.pos.getY() + 0.4, this.pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D, new int[0]);
			this.world.spawnParticle(EnumParticleTypes.FLAME, this.pos.getX() + 0.2, this.pos.getY() + 0.4, this.pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D, new int[0]);
			
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.2, this.pos.getY() + 0.4, this.pos.getZ() + 0.2, 0.0D, 0.0D, 0.0D, new int[0]);
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.2, this.pos.getY() + 0.4, this.pos.getZ() + 0.8, 0.0D, 0.0D, 0.0D, new int[0]);
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.8, this.pos.getY() + 0.4, this.pos.getZ() + 0.2, 0.0D, 0.0D, 0.0D, new int[0]);
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.8, this.pos.getY() + 0.4, this.pos.getZ() + 0.8, 0.0D, 0.0D, 0.0D, new int[0]);
			
		}
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaledOne(int scale) {
		return this.cook_time_one * scale / this.cook_speed;
	}

	public int getCookProgressScaledTwo(int scale) {
		return this.cook_time_two * scale / this.cook_speed;
	}

	public int getPowerRemainingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}
	
	public boolean canExtractOne() {
		if (this.extractor_stacks.get(0).isEmpty()) {
			return false;
		} else {
			ItemStack recipe_stack = ExtractorRecipes.getInstance().getExtractingResult(this.extractor_stacks.get(0));
			if (recipe_stack.isEmpty()) {
				return false;
			} else {
				ItemStack output_stack = this.extractor_stacks.get(4);
				if (output_stack.isEmpty()) {
					return true;
				} else if (!output_stack.isItemEqual(recipe_stack)) {
					return false;
				} else {
					int result = output_stack.getCount() + recipe_stack.getCount();
					return result <= this.getInventoryStackLimit() && result <= output_stack.getMaxStackSize();
				}
			}
		}
	}

	public boolean canExtractTwo() {
		if (this.extractor_stacks.get(2).isEmpty()) {
			return false;
		} else {
			ItemStack recipe_stack = ExtractorRecipes.getInstance().getExtractingResult(this.extractor_stacks.get(2));
			if (recipe_stack.isEmpty()) {
				return false;
			} else {
				ItemStack output_stack = this.extractor_stacks.get(3);
				if (output_stack.isEmpty()) {
					return true;
				} else if (!output_stack.isItemEqual(recipe_stack)) {
					return false;
				} else {
					int result = output_stack.getCount() + recipe_stack.getCount();
					return result <= this.getInventoryStackLimit() && result <= output_stack.getMaxStackSize();
				}
			}
		}
	}

	public void extractItemOne() {
		if (this.canExtractOne()) {
			ItemStack input_stack = this.extractor_stacks.get(0);
			ItemStack output_stack = this.extractor_stacks.get(4);
			ItemStack recipe_stack = ExtractorRecipes.getInstance().getExtractingResult(input_stack);

			if (output_stack.isEmpty()) {
				this.setInventorySlotContents(4, recipe_stack.copy());
				input_stack.shrink(1);
			} else if (output_stack.getItem() == recipe_stack.getItem()) {
				output_stack.grow(recipe_stack.getCount());
				input_stack.shrink(1);
			}
		}
	}

	public void extractItemTwo() {
		if (this.canExtractTwo()) {
			ItemStack input_stack = this.extractor_stacks.get(2);
			ItemStack output_stack = this.extractor_stacks.get(3);
			ItemStack recipe_stack = ExtractorRecipes.getInstance().getExtractingResult(input_stack);

			if (output_stack.isEmpty()) {
				this.setInventorySlotContents(3, recipe_stack.copy());
				input_stack.shrink(1);
			} else if (output_stack.getItem() == recipe_stack.getItem()) {
				output_stack.grow(recipe_stack.getCount());
				input_stack.shrink(1);
			}
		}
	}

	public static boolean isItemPower(ItemStack par0ItemStack) {
		return ItemPowerValues.getItemPower(par0ItemStack.getItem()) > 0;
	}

	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public void openInventory(EntityPlayer player) { }

	public void closeInventory(EntityPlayer player) { }

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
		return new ContainerEnergizedExtractor(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 1:
			return this.stored;
		case 2:
			return this.cook_time_one;
		case 3:
			return this.cook_time_two;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) {
		switch (id) {
		case 1:
			this.stored = value;
			break;
		case 2:
			this.cook_time_one = value;
			break;
		case 3:
			this.cook_time_two = value;
		}
	}

	public int getFieldCount() {
		return 3;
	}

	public void clear() {
		this.extractor_stacks.clear();
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

	public void addStored(int add) {
		this.stored += add;
	}

	@Override
	public int getCookSpeed() {
		return this.cook_speed;
	}
	
	@Override
	public int getCookTime(int i) {
		if (i == 0) {
			return this.cook_time_one;
		}
		
		if (i == 1) {
			return this.cook_time_two;
		} else {
			return -1;
		}
	}

}