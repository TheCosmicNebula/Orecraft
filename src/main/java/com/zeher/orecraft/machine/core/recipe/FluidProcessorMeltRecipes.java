package com.zeher.orecraft.machine.core.recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.zeher.orecraft.Orecraft;

import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidProcessorMeltRecipes {
	
	private static final FluidProcessorMeltRecipes INSTANCE = new FluidProcessorMeltRecipes();
	
	private final Map<ItemStack, FluidStack> recipe_list = Maps.<ItemStack, FluidStack>newHashMap();

	public static FluidProcessorMeltRecipes getInstance() {
		return INSTANCE;
	}

	private FluidProcessorMeltRecipes() {
	}

	public void addRecipe(Block input, FluidStack output) {
		this.addRecipe(Item.getItemFromBlock(input), output);
	}

	public void addRecipe(Item input, FluidStack output) {
		this.addRecipeRecipe(new ItemStack(input, 1, 32767), output);
	}

	public void addRecipeRecipe(ItemStack input, FluidStack output) {
		if (getRecipeResult(input) != null) {
			net.minecraftforge.fml.common.FMLLog.info("Ignored recipe recipe with conflicting input: " + input + " = " + output);
			return;
		}
		this.recipe_list.put(input, output);
	}

	public FluidStack getRecipeResult(ItemStack stack) {
		for (Entry<ItemStack, FluidStack> entry : this.recipe_list.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return (FluidStack) entry.getValue();
			}
		}
		return null;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, FluidStack> getrecipe_list() {
		return this.recipe_list;
	}

	
}