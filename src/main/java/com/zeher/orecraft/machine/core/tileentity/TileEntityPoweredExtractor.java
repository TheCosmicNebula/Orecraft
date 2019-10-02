package com.zeher.orecraft.machine.core.tileentity;

import com.zeher.orecraft.core.handler.SoundHandler;
import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.core.container.ContainerPoweredExtractor;
import com.zeher.orecraft.machine.core.recipe.ExtractorRecipes;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.value.ItemPowerValues;
import com.zeher.zeherlib.machine.IMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

public class TileEntityPoweredExtractor extends TileEntityLockable implements ITickable, ISidedInventory, IMachine {
	
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	
	private NonNullList<ItemStack> extractor_stacks = NonNullList.<ItemStack>withSize(6, ItemStack.EMPTY);

	private int cook_time;
	private String custom_name;

	private int stored;
	private int capacity = Reference.VALUE.MACHINE.poweredStored(0);
	private int input_rate = Reference.VALUE.MACHINE.POWERED_INPUT_RATE;
	
	private int cook_speed = Reference.VALUE.MACHINE.poweredProcessSpeed(0);

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
		return this.extractor_stacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.extractor_stacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.extractor_stacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.extractor_stacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.extractor_stacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.cook_time = 0;
			this.markDirty();
		}
	}

	public String getName() {
		return this.hasCustomName() ? this.custom_name : OrecraftReference.GUI_NAME.MACHINE.POWERED.EXTRACTOR;
	}

	public boolean hasCustomName() {
		return this.custom_name != null && !this.custom_name.isEmpty();
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.extractor_stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.extractor_stacks);
		this.cook_time = compound.getInteger("CookTime");
		this.stored = compound.getInteger("power");

		if (compound.hasKey("CustomName", 8)) {
			this.custom_name = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("CookTime", (short) this.cook_time);
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
		if (this.hasStored() && this.canExtract()) {
			if (!this.world.isRemote) {
				this.stored -= Reference.VALUE.MACHINE.machineDrainRate(cook_speed);
			}
			
			this.cook_time++;
			
			if (this.cook_time == this.cook_speed) {
				this.cook_time = 0;
				if (!this.world.isRemote) {
					this.extractItem();
				}
			}
			
		} else {
			this.cook_time = 0;
		}
		
		if (this.cook_time > 0) {
			this.markDirty();
		}

		if (this.hasStored() && this.canExtract()) {
			
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.5, this.pos.getY() + 0.4, this.pos.getZ() + 0.2, 0.0D, 0.0D, 0.0D, new int[0]);
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.5, this.pos.getY() + 0.4, this.pos.getZ() + 0.8, 0.0D, 0.0D, 0.0D, new int[0]);
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.8, this.pos.getY() + 0.4, this.pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D, new int[0]);
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.2, this.pos.getY() + 0.4, this.pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D, new int[0]);
			
			this.sound_timer++;
			
			if (this.sound_timer > 10) {
				if (this.world.isRemote) {
					this.world.playSound(this.pos.getX() + 0.5D, this.pos.getY(), this.pos.getZ() + 0.5D, SoundHandler.MACHINE.EXTRACTOR, SoundCategory.BLOCKS, 1F, 1.0F, false);
				}
				this.sound_timer = 0;
			}
		} else {
			this.sound_timer = 0;
		}

		if (this.stored > this.capacity) {
			this.stored = this.capacity;
		}
		
		int i = this.extractor_stacks.get(3).getCount();
		this.cook_speed = Reference.VALUE.MACHINE.poweredProcessSpeed(i);
		
		int i_one = this.extractor_stacks.get(4).getCount();
		this.capacity = Reference.VALUE.MACHINE.poweredStored(i_one);
		
		if (!this.world.isRemote) {
			if ((isItemPower(this.extractor_stacks.get(1))) && (this.stored <= this.capacity - ItemPowerValues.getItemPower(this.extractor_stacks.get(1).getItem()))) {
				if (!this.extractor_stacks.get(1).isItemStackDamageable()) {
					this.stored += ItemPowerValues.getItemPower(this.extractor_stacks.get(1).getItem());

					this.markDirty();

					if (this.extractor_stacks.get(1) != null) {
						this.extractor_stacks.get(1).setCount(this.extractor_stacks.get(1).getCount());
						if (this.extractor_stacks.get(1).getCount() == 0) {
							this.extractor_stacks.set(1, this.extractor_stacks.get(1).getItem().getContainerItem(this.extractor_stacks.get(1)));
						}
					}
				} else if (this.extractor_stacks.get(1).getItemDamage() < this.extractor_stacks.get(1).getMaxDamage()) {
					this.stored += ItemPowerValues.getItemPower(this.extractor_stacks.get(1).getItem());
					this.extractor_stacks.set(1, new ItemStack(this.extractor_stacks.get(1).getItem(), this.extractor_stacks.get(1).getCount(), this.extractor_stacks.get(1).getItemDamage() + 1));
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.cook_time * par1 / this.cook_speed;
	}

	public int getPowerRemainingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}
	
	public boolean canExtract() {
		if (this.extractor_stacks.get(0).isEmpty()) {
			return false;
		} else {
			ItemStack itemstack = ExtractorRecipes.getInstance().getExtractingResult((ItemStack) this.extractor_stacks.get(0));

			if (itemstack.isEmpty()) {
				return false;
			} else {
				ItemStack itemstack1 = this.extractor_stacks.get(2);
				if (itemstack1.isEmpty())
					return true;
				if (!itemstack1.isItemEqual(itemstack))
					return false;
				int result = itemstack1.getCount() + itemstack.getCount();
				return result <= getInventoryStackLimit() && result <= itemstack1.getMaxStackSize();
			}
		}
	}

	public void extractItem() {
		if (this.canExtract()) {
			ItemStack itemstack = this.extractor_stacks.get(0);
			ItemStack itemstack1 = ExtractorRecipes.getInstance().getExtractingResult(itemstack);
			ItemStack itemstack2 = this.extractor_stacks.get(2);

			if (itemstack2.isEmpty()) {
				this.extractor_stacks.set(2, itemstack1.copy());
			} else if (itemstack2.getItem() == itemstack1.getItem()) {
				itemstack2.grow(itemstack1.getCount());
			}

			if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1 && !(this.extractor_stacks.get(1)).isEmpty() && (this.extractor_stacks.get(1)).getItem() == Items.BUCKET) {
				this.extractor_stacks.set(1, new ItemStack(Items.WATER_BUCKET));
			}

			itemstack.shrink(1);
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
		return new ContainerPoweredExtractor(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 2:
			return this.cook_time;
		case 4:
			return this.stored;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) {
		switch (id) {
		case 2:
			this.cook_time = value;
			break;
		case 4:
			this.stored = value;
		}
	}

	public int getFieldCount() {
		return 2;
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
			if (facing == EnumFacing.DOWN) {
				return (T) handlerBottom;
			} else if (facing == EnumFacing.UP) {
				return (T) handlerTop;
			} else {
				return (T) handlerSide;
			}	
		return super.getCapability(capability, facing);
	}

	@Override
	public String getGuiID() {
		return null;
	}

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
	public boolean hasStored() {
		return this.stored > 0;
	}

	@Override
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
			return this.cook_time;
		}
		return -1;
	}
	
}