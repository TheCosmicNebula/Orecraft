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

public class MachineFluidCrafterRecipes {
	private static final MachineFluidCrafterRecipes fluid_base = new MachineFluidCrafterRecipes();
	private final Map<ItemStack, ItemStack> recipeList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Fluid> fluidList = Maps.<ItemStack, Fluid>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

	public static MachineFluidCrafterRecipes instance() {
		return fluid_base;
	}

	private MachineFluidCrafterRecipes() {
	}

	public void addRecipe(Block input, ItemStack stack, Fluid fluid, float experience) {
		this.addRecipe(Item.getItemFromBlock(input), stack, fluid, experience);
	}

	public void addRecipe(Item input, ItemStack stack, Fluid fluid, float experience) {
		this.addRecipe(new ItemStack(input, 1, 32767), stack, fluid, experience);
	}

	public void addRecipe(ItemStack input, ItemStack stack, Fluid fluid, float experience) {
		if (getRecipeResult(input) != ItemStack.EMPTY) {
			net.minecraftforge.fml.common.FMLLog.info("Ignored grinding recipe with conflicting input: " + input + " = " + stack);
			return;
		}
		this.recipeList.put(input, stack);
		this.fluidList.put(input, fluid);
		this.experienceList.put(stack, Float.valueOf(experience));
	}

	public ItemStack getRecipeResult(ItemStack stack) {
		for (Entry<ItemStack, ItemStack> entry : this.recipeList.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return (ItemStack) entry.getValue();
			}
		}
		return ItemStack.EMPTY;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public Fluid getFluid(ItemStack stack) {
		for(int x = 0; x < this.recipeList.size(); x++){
			if(this.recipeList.get(x).equals(stack)){
				Fluid fluid = this.fluidList.get(x);
				return fluid;
			}
		}
		return null;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, ItemStack> getGrindingList() {
		return this.recipeList;
	}
	
	public Map<ItemStack, Fluid> getFluidList(){
		return this.fluidList;
	}

	public float getGrindingExperience(ItemStack stack) {
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1)
			return ret;

		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return ((Float) entry.getValue()).floatValue();
			}
		}
		return 0.0F;
	}
}