package com.zeher.orecraft.core.handlers.recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.zeher.orecraft.OreCraft;

import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class MachineFluidMeltRecipes {
	private static final MachineFluidMeltRecipes recipe_base = new MachineFluidMeltRecipes();
	private final Map<ItemStack, FluidStack> recipeList = Maps.<ItemStack, FluidStack>newHashMap();

	public static MachineFluidMeltRecipes instance() {
		return recipe_base;
	}

	private MachineFluidMeltRecipes() {
	}

	public void addRecipe(Block input, FluidStack output) {
		this.addRecipe(Item.getItemFromBlock(input), output);
	}

	public void addRecipe(Item input, FluidStack output) {
		this.addRecipeRecipe(new ItemStack(input, 1, 32767), output);
	}

	public void addRecipeRecipe(ItemStack input, FluidStack output) {
		if (getRecipeResult(input) != null) {
			net.minecraftforge.fml.common.FMLLog
					.info("Ignored recipe recipe with conflicting input: " + input + " = " + output);
			return;
		}
		this.recipeList.put(input, output);
	}

	public FluidStack getRecipeResult(ItemStack stack) {
		for (Entry<ItemStack, FluidStack> entry : this.recipeList.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return (FluidStack) entry.getValue();
			}
		}
		return null;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem()
				&& (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, FluidStack> getRecipeList() {
		return this.recipeList;
	}

	
}