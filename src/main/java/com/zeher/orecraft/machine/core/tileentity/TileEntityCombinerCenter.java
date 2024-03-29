package com.zeher.orecraft.machine.core.tileentity;

import java.util.List;

import com.google.common.collect.Lists;
import com.zeher.orecraft.core.handler.SoundHandler;
import com.zeher.orecraft.machine.core.block.BlockCombinerPedestal;
import com.zeher.orecraft.machine.core.recipe.CombinerCraftingHandler;
import com.zeher.orecraft.storage.core.util.StorageUtil;
import com.zeher.zeherlib.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class TileEntityCombinerCenter extends TileEntityLockable implements ITickable, ISidedInventory, IInventory {
	private static final int[] acc_slots = { 0 };
	private NonNullList<ItemStack> center_stacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	
	public int cook_time;
	
	public int stored;
	public int capacity = 800000;
	public int input_rate = Reference.VALUE.MACHINE.ENERGIZED_INPUT_RATE;

	public int sound_timer = 0;
	
	public void update() {
		
		if (this.canCombine()) {
			this.sound_timer++;
			
			if (this.sound_timer > 13) {
				this.world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundHandler.MACHINE.LASERHUM, SoundCategory.BLOCKS, 1F, 1.0F, false);
			
				this.sound_timer = 0;
			}
		} else {
			this.sound_timer = 0;
		}
		
		if (this.canCombine()) {
			this.cook_time++;
			
			if (this.cook_time == this.getProcessTime().intValue()) {
				this.cook_time = 0;
				this.combineItem();
				this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
				this.world.playSound(this.pos.getX() + 0.5D, this.pos.getY(), this.pos.getZ() + 0.5D, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.3F, 1F, false);
			}
		} else {
			this.cook_time = 0;
		}
		
		if (this.cook_time > 0) {
			this.markDirty();
		}
		
		if ((this.stored > this.capacity) && (((ItemStack) this.center_stacks.get(11)).getCount() == 0)) {
			this.stored = this.capacity;
		}
		
		if ((this.stored > this.capacity) && (((ItemStack) this.center_stacks.get(11)).getCount() == 1)) {
			this.stored = 540000;
		}
		
		if ((this.stored > this.capacity) && (((ItemStack) this.center_stacks.get(11)).getCount() == 2)) {
			this.stored = 580000;
		}
		
		if ((this.stored > this.capacity) && (((ItemStack) this.center_stacks.get(11)).getCount() == 3)) {
			this.stored = 620000;
		}
		
		if ((this.stored > this.capacity) && (((ItemStack) this.center_stacks.get(11)).getCount() == 4)) {
			this.stored = 660000;
		}
	}

	public Integer getProcessTime() {
		TileEntity tile_n = this.world.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() - 3));
		TileEntity tile_s = this.world.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() + 3));

		TileEntity tile_e = this.world.getTileEntity(new BlockPos(this.pos.getX() + 3, this.pos.getY(), this.pos.getZ()));
		TileEntity tile_w = this.world.getTileEntity(new BlockPos(this.pos.getX() - 3, this.pos.getY(), this.pos.getZ()));

		TileEntity tile_ne = this.world.getTileEntity(new BlockPos(this.pos.getX() + 2, this.pos.getY(), this.pos.getZ() - 2));
		TileEntity tile_nw = this.world.getTileEntity(new BlockPos(this.pos.getX() - 2, this.pos.getY(), this.pos.getZ() - 2));

		TileEntity tile_se = this.world.getTileEntity(new BlockPos(this.pos.getX() + 2, this.pos.getY(), this.pos.getZ() + 2));
		TileEntity tile_sw = this.world.getTileEntity(new BlockPos(this.pos.getX() - 2, this.pos.getY(), this.pos.getZ() + 2));
		
		if (((tile_n instanceof TileEntityCombinerPedestal)) && ((tile_s instanceof TileEntityCombinerPedestal)) && ((tile_e instanceof TileEntityCombinerPedestal))
				&& ((tile_w instanceof TileEntityCombinerPedestal)) && ((tile_ne instanceof TileEntityCombinerPedestal)) && ((tile_nw instanceof TileEntityCombinerPedestal))
				&& ((tile_se instanceof TileEntityCombinerPedestal)) && ((tile_sw instanceof TileEntityCombinerPedestal))) {
			
			ItemStack stack_n = ((TileEntityCombinerPedestal) tile_n).getStackInSlot(0);
			ItemStack stack_s = ((TileEntityCombinerPedestal) tile_s).getStackInSlot(0);

			ItemStack stack_e = ((TileEntityCombinerPedestal) tile_e).getStackInSlot(0);
			ItemStack stack_w = ((TileEntityCombinerPedestal) tile_w).getStackInSlot(0);

			ItemStack stack_ne = ((TileEntityCombinerPedestal) tile_ne).getStackInSlot(0);
			ItemStack stack_nw = ((TileEntityCombinerPedestal) tile_nw).getStackInSlot(0);
			ItemStack stack_se = ((TileEntityCombinerPedestal) tile_se).getStackInSlot(0);
			ItemStack stack_sw = ((TileEntityCombinerPedestal) tile_sw).getStackInSlot(0);
			
			if ((!stack_n.isEmpty()) && (!stack_s.isEmpty()) && (stack_e.isEmpty()) && (stack_w.isEmpty()) && (stack_ne.isEmpty()) && (stack_nw.isEmpty()) && (stack_se.isEmpty())
					&& (stack_sw.isEmpty())) {
				
				List<ItemStack> list = Lists.newArrayList();
				list.add(stack_n);
				list.add(stack_s);

				ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
				if (result_stack.isEmpty()) {
					return 0;
				}
				
				ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
				if (output_stack.isEmpty()) {
					return 0;
				}
				
				if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, this.world).getItem())) {
					return CombinerCraftingHandler.getInstance().findProcessTime(list, this.world);
				}
				
				return 0;
			}
			if ((stack_n.isEmpty()) && (stack_s.isEmpty()) && (!stack_e.isEmpty()) && (!stack_w.isEmpty()) && (stack_ne.isEmpty()) && (stack_nw.isEmpty()) && (stack_se.isEmpty())
					&& (stack_sw.isEmpty())) {
				
				List<ItemStack> list = Lists.newArrayList();
				list.add(stack_e);
				list.add(stack_w);

				ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
				if (result_stack.isEmpty()) {
					return 0;
				}
				
				ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
				if (output_stack.isEmpty()) {
					return 0;
				}
				
				if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, this.world).getItem())) {
					return CombinerCraftingHandler.getInstance().findProcessTime(list, this.world);
				}
				
				return 0;
			}
			if ((!stack_n.isEmpty()) && (!stack_s.isEmpty()) && (!stack_e.isEmpty()) && (!stack_w.isEmpty()) && (stack_ne.isEmpty()) && (stack_nw.isEmpty()) && (stack_se.isEmpty())
					&& (stack_sw.isEmpty())) {
				
				List<ItemStack> list = Lists.newArrayList();
				list.add(stack_n);
				list.add(stack_s);
				list.add(stack_e);
				list.add(stack_w);

				ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
				if (result_stack.isEmpty()) {
					return 0;
				}
				
				ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
				if (output_stack.isEmpty()) {
					return 0;
				}
				
				if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, this.world).getItem())) {
					return CombinerCraftingHandler.getInstance().findProcessTime(list, this.world);
				} 
				
				return 0;
			}
			if ((stack_n.isEmpty()) && (stack_s.isEmpty()) && (stack_e.isEmpty())
					&& (stack_w.isEmpty()) && (!stack_ne.isEmpty()) && (!stack_nw.isEmpty())
					&& (!stack_se.isEmpty()) && (!stack_sw.isEmpty())) {
				List<ItemStack> list = Lists.newArrayList();
				list.add(stack_ne);
				list.add(stack_nw);
				list.add(stack_se);
				list.add(stack_sw);

				ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
				if (result_stack.isEmpty()) {
					return 0;
				}
				ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
				if (output_stack.isEmpty()) {
					return 0;
				}
				if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance()
						.findFocusStack(list, this.world).getItem())) {
					return CombinerCraftingHandler.getInstance().findProcessTime(list, this.world);
				}
				return 0;
			}
			if ((!stack_n.isEmpty()) && (!stack_s.isEmpty()) && (!stack_e.isEmpty())
					&& (!stack_w.isEmpty()) && (!stack_ne.isEmpty()) && (!stack_nw.isEmpty())
					&& (!stack_se.isEmpty()) && (!stack_sw.isEmpty())) {
				List<ItemStack> list = Lists.newArrayList();
				list.add(stack_n);
				list.add(stack_s);
				list.add(stack_e);
				list.add(stack_w);
				list.add(stack_ne);
				list.add(stack_nw);
				list.add(stack_se);
				list.add(stack_sw);

				ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list,
						this.world);
				if (result_stack.isEmpty()) {
					return 0;
				}
				ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
				if (output_stack.isEmpty()) {
					return 0;
				}
				if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance()
						.findFocusStack(list, this.world).getItem())) {
					return CombinerCraftingHandler.getInstance().findProcessTime(list, this.world);
				}
				return 0;
			}
		}
		return 0;
	}

	public boolean isSetup() {
		if (((this.world.getBlockState(new BlockPos(this.pos.getX() + 3, this.pos.getY(), this.pos.getZ())).getBlock() instanceof BlockCombinerPedestal))
				&& ((this.world.getBlockState(new BlockPos(this.pos.getX() - 3, this.pos.getY(), this.pos.getZ())).getBlock() instanceof BlockCombinerPedestal))
				&& ((this.world.getBlockState(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() + 3)).getBlock() instanceof BlockCombinerPedestal))
				&& ((this.world.getBlockState(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() - 3)).getBlock() instanceof BlockCombinerPedestal))
				&& ((this.world.getBlockState(new BlockPos(this.pos.getX() + 2, this.pos.getY(), this.pos.getZ() + 2)).getBlock() instanceof BlockCombinerPedestal))
				&& ((this.world.getBlockState(new BlockPos(this.pos.getX() + 2, this.pos.getY(), this.pos.getZ() - 2)).getBlock() instanceof BlockCombinerPedestal))
				&& ((this.world.getBlockState(new BlockPos(this.pos.getX() - 2, this.pos.getY(), this.pos.getZ() + 2)).getBlock() instanceof BlockCombinerPedestal))
				&& ((this.world.getBlockState(new BlockPos(this.pos.getX() - 2, this.pos.getY(), this.pos.getZ() - 2)).getBlock() instanceof BlockCombinerPedestal))) {
			return true;
		}
		return false;
	}
	

	public boolean hasPower() {
		return true;
	}

	public int getSizeInventory() {
		return this.center_stacks.size();
	}

	public boolean canCombine() {
		if (isSetup()) {
			TileEntity tile_n = this.world.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() - 3));
			TileEntity tile_s = this.world.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() + 3));

			TileEntity tile_e = this.world.getTileEntity(new BlockPos(this.pos.getX() + 3, this.pos.getY(), this.pos.getZ()));
			TileEntity tile_w = this.world.getTileEntity(new BlockPos(this.pos.getX() - 3, this.pos.getY(), this.pos.getZ()));

			TileEntity tile_ne = this.world.getTileEntity(new BlockPos(this.pos.getX() + 2, this.pos.getY(), this.pos.getZ() - 2));
			TileEntity tile_nw = this.world.getTileEntity(new BlockPos(this.pos.getX() - 2, this.pos.getY(), this.pos.getZ() - 2));

			TileEntity tile_se = this.world.getTileEntity(new BlockPos(this.pos.getX() + 2, this.pos.getY(), this.pos.getZ() + 2));
			TileEntity tile_sw = this.world.getTileEntity(new BlockPos(this.pos.getX() - 2, this.pos.getY(), this.pos.getZ() + 2));
			
			if (((tile_n instanceof TileEntityCombinerPedestal)) && ((tile_s instanceof TileEntityCombinerPedestal)) && ((tile_e instanceof TileEntityCombinerPedestal))
					&& ((tile_w instanceof TileEntityCombinerPedestal)) && ((tile_ne instanceof TileEntityCombinerPedestal)) && ((tile_nw instanceof TileEntityCombinerPedestal))
					&& ((tile_se instanceof TileEntityCombinerPedestal)) && ((tile_sw instanceof TileEntityCombinerPedestal))) {
				
				ItemStack stack_n = ((TileEntityCombinerPedestal) tile_n).getStackInSlot(0);
				ItemStack stack_s = ((TileEntityCombinerPedestal) tile_s).getStackInSlot(0);

				ItemStack stack_e = ((TileEntityCombinerPedestal) tile_e).getStackInSlot(0);
				ItemStack stack_w = ((TileEntityCombinerPedestal) tile_w).getStackInSlot(0);

				ItemStack stack_ne = ((TileEntityCombinerPedestal) tile_ne).getStackInSlot(0);
				ItemStack stack_nw = ((TileEntityCombinerPedestal) tile_nw).getStackInSlot(0);
				ItemStack stack_se = ((TileEntityCombinerPedestal) tile_se).getStackInSlot(0);
				ItemStack stack_sw = ((TileEntityCombinerPedestal) tile_sw).getStackInSlot(0);
				
				if ((!stack_n.isEmpty()) && (!stack_s.isEmpty()) && (stack_e.isEmpty())
						&& (stack_w.isEmpty()) && (stack_ne.isEmpty()) && (stack_nw.isEmpty())
						&& (stack_se.isEmpty()) && (stack_sw.isEmpty())) {
					List<ItemStack> list = Lists.newArrayList();
					list.add(stack_n);
					list.add(stack_s);

					ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
					if (result_stack.isEmpty()) {
						return false;
					}
					
					ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
					if (output_stack.isEmpty()) {
						return false;
					}
					
					if (output_stack.isItemEqual(CombinerCraftingHandler.getInstance().findFocusStack(list, this.world))) {
						return true;
					}
					
					return false;
				}
				if ((stack_n.isEmpty()) && (stack_s.isEmpty()) && (!stack_e.isEmpty()) && (!stack_w.isEmpty()) && (stack_ne.isEmpty()) && (stack_nw.isEmpty()) && (stack_se.isEmpty()) 
						&& (stack_sw.isEmpty())) {
					List<ItemStack> list = Lists.newArrayList();
					list.add(stack_e);
					list.add(stack_w);
					
					ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
					if (result_stack.isEmpty()) {
						return false;
					}
					
					ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
					if (output_stack.isEmpty()) {
						return false;
					}
					
					if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, this.world).getItem())) {
						return true;
					}
					
					return false;
				}
				if ((!stack_n.isEmpty()) && (!stack_s.isEmpty()) && (!stack_e.isEmpty()) && (!stack_w.isEmpty()) && (stack_ne.isEmpty()) && (stack_nw.isEmpty()) && (stack_se.isEmpty())
						&& (stack_sw.isEmpty())) {
					List<ItemStack> list = Lists.newArrayList();
					list.add(stack_n);
					list.add(stack_s);
					list.add(stack_e);
					list.add(stack_w);
					
					//System.out.println(MachineCombinerCraftingHandler.getInstance().getRecipeList().get(5));
					
					//System.out.println(list);

					ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
					
					if (result_stack.isEmpty()) {
						return false;
					}
					
					ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
					if (output_stack.isEmpty()) {
						return false;
					}
					
					if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, this.world).getItem())) {
						return true;
					}
					
					return false;
				}
				if ((stack_n.isEmpty()) && (stack_s.isEmpty()) && (stack_e.isEmpty()) && (stack_w.isEmpty()) && (!stack_ne.isEmpty()) && (!stack_nw.isEmpty()) && (!stack_se.isEmpty())
						&& (!stack_sw.isEmpty())) {
					List<ItemStack> list = Lists.newArrayList();
					list.add(stack_ne);
					list.add(stack_nw);
					list.add(stack_se);
					list.add(stack_sw);

					ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
					if (result_stack.isEmpty()) {
						return false;
					}
					
					ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
					if (output_stack.isEmpty()) {
						return false;
					}
					
					if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, this.world).getItem())) {
						return true;
					}
					
					return false;
				}
				if ((!stack_n.isEmpty()) && (!stack_s.isEmpty()) && (!stack_e.isEmpty()) && (!stack_w.isEmpty()) && (!stack_ne.isEmpty()) && (!stack_nw.isEmpty()) && (!stack_se.isEmpty())
						&& (!stack_sw.isEmpty())) {
					List<ItemStack> list = Lists.newArrayList();
					list.add(stack_n);
					list.add(stack_s);
					list.add(stack_e);
					list.add(stack_w);
					list.add(stack_ne);
					list.add(stack_nw);
					list.add(stack_se);
					list.add(stack_sw);

					ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
					if (result_stack.isEmpty()) {
						return false;
					}
					
					ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
					if (output_stack.isEmpty()) {
						return false;
					}
					
					if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance() .findFocusStack(list, this.world).getItem())) {
						return true;
					}
					
					return false;
				}
			}
		}
		return false;
	}

	public void combineItem() {
		if (this.isSetup() && this.canCombine()) {
			TileEntity tile_n = this.world.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() - 3));
			TileEntity tile_s = this.world.getTileEntity(new BlockPos(this.pos.getX(), this.pos.getY(), this.pos.getZ() + 3));

			TileEntity tile_e = this.world.getTileEntity(new BlockPos(this.pos.getX() + 3, this.pos.getY(), this.pos.getZ()));
			TileEntity tile_w = this.world.getTileEntity(new BlockPos(this.pos.getX() - 3, this.pos.getY(), this.pos.getZ()));

			TileEntity tile_ne = this.world.getTileEntity(new BlockPos(this.pos.getX() + 2, this.pos.getY(), this.pos.getZ() - 2));
			TileEntity tile_nw = this.world.getTileEntity(new BlockPos(this.pos.getX() - 2, this.pos.getY(), this.pos.getZ() - 2));

			TileEntity tile_se = this.world.getTileEntity(new BlockPos(this.pos.getX() + 2, this.pos.getY(), this.pos.getZ() + 2));
			TileEntity tile_sw = this.world.getTileEntity(new BlockPos(this.pos.getX() - 2, this.pos.getY(), this.pos.getZ() + 2));
			
			if (((tile_n instanceof TileEntityCombinerPedestal)) && ((tile_s instanceof TileEntityCombinerPedestal)) && ((tile_e instanceof TileEntityCombinerPedestal))
					&& ((tile_w instanceof TileEntityCombinerPedestal)) && ((tile_ne instanceof TileEntityCombinerPedestal)) && ((tile_nw instanceof TileEntityCombinerPedestal))
					&& ((tile_se instanceof TileEntityCombinerPedestal)) && ((tile_sw instanceof TileEntityCombinerPedestal))) {
				
				ItemStack stack_n = ((TileEntityCombinerPedestal) tile_n).getStackInSlot(0);
				ItemStack stack_s = ((TileEntityCombinerPedestal) tile_s).getStackInSlot(0);

				ItemStack stack_e = ((TileEntityCombinerPedestal) tile_e).getStackInSlot(0);
				ItemStack stack_w = ((TileEntityCombinerPedestal) tile_w).getStackInSlot(0);

				ItemStack stack_ne = ((TileEntityCombinerPedestal) tile_ne).getStackInSlot(0);
				ItemStack stack_nw = ((TileEntityCombinerPedestal) tile_nw).getStackInSlot(0);
				ItemStack stack_se = ((TileEntityCombinerPedestal) tile_se).getStackInSlot(0);
				ItemStack stack_sw = ((TileEntityCombinerPedestal) tile_sw).getStackInSlot(0);
				
				if ((!stack_n.isEmpty()) && (!stack_s.isEmpty()) && (stack_e.isEmpty()) && (stack_w.isEmpty()) && (stack_ne.isEmpty()) && (stack_nw.isEmpty()) && (stack_se.isEmpty()) 
						&& (stack_sw.isEmpty())) {
					List<ItemStack> list = Lists.newArrayList();
					list.add(stack_n);
					list.add(stack_s);

					ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
					if (!result_stack.isEmpty()) {
						
						ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
						if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, this.world).copy().getItem())) {
							output_stack.shrink(1);
							setInventorySlotContents(0, result_stack.copy());

							stack_n.shrink(1);
							stack_s.shrink(1);
						}
					}
				}
				if ((stack_n.isEmpty()) && (stack_s.isEmpty()) && (!stack_e.isEmpty()) && (!stack_w.isEmpty()) && (stack_ne.isEmpty()) && (stack_nw.isEmpty())
						&& (stack_se.isEmpty()) && (stack_sw.isEmpty())) {
					List<ItemStack> list = Lists.newArrayList();
					list.add(stack_e);
					list.add(stack_w);

					ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
					if (!result_stack.isEmpty()) {
						ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
						if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, this.world).copy().getItem())) {
							output_stack.shrink(1);
							setInventorySlotContents(0, result_stack.copy());

							stack_e.shrink(1);
							stack_w.shrink(1);
						}
					}
				}
				if ((!stack_n.isEmpty()) && (!stack_s.isEmpty()) && (!stack_e.isEmpty())
						&& (!stack_w.isEmpty()) && (stack_ne.isEmpty()) && (stack_nw.isEmpty())
						&& (stack_se.isEmpty()) && (stack_sw.isEmpty())) {
					List<ItemStack> list = Lists.newArrayList();
					list.add(stack_n);
					list.add(stack_s);
					list.add(stack_e);
					list.add(stack_w);

					ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list,
							this.world);
					if (!result_stack.isEmpty()) {
						ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
						if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance()
								.findFocusStack(list, this.world).copy().getItem())) {
							output_stack.shrink(1);
							setInventorySlotContents(0, result_stack.copy());

							stack_n.shrink(1);
							stack_s.shrink(1);
							stack_e.shrink(1);
							stack_w.shrink(1);
						}
					}
				}
				if ((stack_n.isEmpty()) && (stack_s.isEmpty()) && (stack_e.isEmpty())
						&& (stack_w.isEmpty()) && (!stack_ne.isEmpty()) && (!stack_nw.isEmpty())
						&& (!stack_se.isEmpty()) && (!stack_sw.isEmpty())) {
					List<ItemStack> list = Lists.newArrayList();
					list.add(stack_ne);
					list.add(stack_nw);
					list.add(stack_se);
					list.add(stack_sw);

					ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
					if (!result_stack.isEmpty()) {
						ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
						if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, this.world).copy().getItem())) {
							output_stack.shrink(1);
							setInventorySlotContents(0, result_stack.copy());

							stack_ne.shrink(1);
							stack_nw.shrink(1);
							stack_se.shrink(1);
							stack_sw.shrink(1);
						}
					}
				}
				if ((!stack_n.isEmpty()) && (!stack_s.isEmpty()) && (!stack_e.isEmpty())
						&& (!stack_w.isEmpty()) && (!stack_ne.isEmpty()) && (!stack_nw.isEmpty())
						&& (!stack_se.isEmpty()) && (!stack_sw.isEmpty())) {
					List<ItemStack> list = Lists.newArrayList();
					list.add(stack_n);
					list.add(stack_s);
					list.add(stack_e);
					list.add(stack_w);
					list.add(stack_ne);
					list.add(stack_nw);
					list.add(stack_se);
					list.add(stack_sw);

					ItemStack result_stack = CombinerCraftingHandler.getInstance().findMatchingRecipe(list, this.world);
					if (!result_stack.isEmpty()) {
						ItemStack output_stack = (ItemStack) this.center_stacks.get(0);
						if (output_stack.getItem().equals(CombinerCraftingHandler.getInstance().findFocusStack(list, this.world).copy().getItem())) {
							output_stack.shrink(1);
							setInventorySlotContents(0, result_stack.copy());

							stack_n.shrink(1);
							stack_s.shrink(1);
							stack_e.shrink(1);
							stack_w.shrink(1);
							stack_ne.shrink(1);
							stack_nw.shrink(1);
							stack_se.shrink(1);
							stack_sw.shrink(1);
						}
					}
				}
			}
		}
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.center_stacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		if (((ItemStack) this.center_stacks.get(0)).getCount() < 1) {
			ItemStack itemstack = (ItemStack) this.center_stacks.get(index);
			boolean flag = (!stack.isEmpty()) && (stack.isItemEqual(itemstack)) && (ItemStack.areItemStackTagsEqual(stack, itemstack));
			this.center_stacks.set(index, stack);
			if (stack.getCount() > this.getInventoryStackLimit()) {
				stack.setCount(getInventoryStackLimit());
			}
		}
		StorageUtil.syncBlockAndRerender(this.world, this.pos);
	}

	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.center_stacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.center_stacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.center_stacks, index);
	}

	public int getInventoryStackLimit() {
		return 1;
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

	public int getField(int id) {
		return 0;
	}

	public void setField(int id, int value) {
	}

	public int getFieldCount() {
		return 0;
	}

	public void clear() {
		this.center_stacks.clear();
	}

	public String getName() {
		return null;
	}

	public boolean hasCustomName() {
		return false;
	}
	
	public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.UP ? acc_slots : side == EnumFacing.DOWN ? acc_slots : acc_slots;
	}

	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		if (((ItemStack) this.center_stacks.get(0)).isEmpty()) {
			return this.isItemValidForSlot(0, itemStackIn);
		}
		return false;
	}

	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if ((direction == EnumFacing.DOWN) && (index == 0)) {
			Item item = stack.getItem();
			if ((item != Items.WATER_BUCKET) && (item != Items.BUCKET)) {
				return false;
			}
		}
		return true;
	}

	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return null;
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.center_stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.center_stacks);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, this.center_stacks);

		return compound;
	}

	@Override
	public String getGuiID() {
		return null;
	}
}
