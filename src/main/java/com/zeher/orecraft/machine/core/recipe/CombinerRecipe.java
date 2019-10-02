package com.zeher.orecraft.machine.core.recipe;

import java.util.List;

import com.google.common.collect.Lists;
import com.zeher.zeherlib.client.recipe.IRecipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class CombinerRecipe implements IRecipe {
	/** Is the ItemStack that you get when craft the recipe. */
	private final ItemStack recipeOutput;
	/** Is a List of ItemStack that composes the recipe. */
	public final List<ItemStack> recipeItems;

	private final ItemStack focus_stack;
	private final Integer process_time;
	
	private final float[] colour;

	public CombinerRecipe(ItemStack output, List<ItemStack> list, ItemStack focus_stack, float[] colour, Integer process_time) {
		this.recipeOutput = output;
		this.recipeItems = list;
		this.focus_stack = focus_stack;
		this.process_time = process_time;
		this.colour = colour;
	}

	public ItemStack getRecipeOutput() {
		return this.recipeOutput;
	}

	public NonNullList<ItemStack> getRemainingItems(List<ItemStack> stack) {
		NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(stack.size(), ItemStack.EMPTY);

		for (int i = 0; i < nonnulllist.size(); ++i) {
			ItemStack itemstack = stack.get(i);
			nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
		}

		return nonnulllist;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(List<ItemStack> stack, World worldIn) {
		List<ItemStack> list = Lists.newArrayList(this.recipeItems);

		for (int j = 0; j < stack.size(); ++j) {
			ItemStack itemstack = stack.get(j);

			if (!itemstack.isEmpty()) {
				boolean flag = false;

				for (ItemStack itemstack1 : list) {
					if (itemstack.isItemEqual(itemstack1) && (itemstack1.getMetadata() == 32767 || itemstack.getMetadata() == itemstack1.getMetadata())) {
						flag = true;
						list.remove(itemstack1);
						break;
					}
				}

				if (!flag) {
					return false;
				}
			}
		}

		return list.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(List<ItemStack> stack) {
		return this.recipeOutput.copy();
	}

	/**
	 * Returns the size of the recipe area
	 */
	public int getRecipeSize() {
		return this.recipeItems.size();
	}

	@Override
	public List<ItemStack> getRecipeInput() {
		return this.recipeItems;
	}

	@Override
	public ItemStack getFocusStack(List<ItemStack> stack) {
		return focus_stack;
	}

	@Override
	public Integer getProcessTime(List<ItemStack> stack) {
		return process_time;
	}
	
	@Override
	public float[] getColour(List<ItemStack> stack) {
		return colour;
	}
}