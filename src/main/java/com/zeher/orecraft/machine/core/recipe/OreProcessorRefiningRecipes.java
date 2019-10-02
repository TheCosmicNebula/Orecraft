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

public class OreProcessorRefiningRecipes {
	
	private static final OreProcessorRefiningRecipes INSTANCE = new OreProcessorRefiningRecipes();
	
	private final Map<ItemStack, ItemStack> cleaning_list = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experience_list = Maps.<ItemStack, Float>newHashMap();

	public static OreProcessorRefiningRecipes getInstance() {
		return INSTANCE;
	}

	private OreProcessorRefiningRecipes() { }

	public void addRefining(Block input, ItemStack stack, float experience) {
		this.addRefining(Item.getItemFromBlock(input), stack, experience);
	}

	public void addRefining(Item input, ItemStack stack, float experience) {
		this.addRefiningRecipe(new ItemStack(input, 1, 32767), stack, experience);
	}

	public void addRefiningRecipe(ItemStack input, ItemStack stack, float experience) {
		if (getRefiningResult(input) != ItemStack.EMPTY) {
			net.minecraftforge.fml.common.FMLLog.info("Ignored cleaning recipe with conflicting input: " + input + " = " + stack);
			return;
		}
		this.cleaning_list.put(input, stack);
		this.experience_list.put(stack, Float.valueOf(experience));
	}

	public ItemStack getRefiningResult(ItemStack stack) {
		for (Entry<ItemStack, ItemStack> entry : this.cleaning_list.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return (ItemStack) entry.getValue();
			}
		}
		return ItemStack.EMPTY;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, ItemStack> getRefiningList() {
		return this.cleaning_list;
	}

	public float getRefiningExperience(ItemStack stack) {
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1)
			return ret;

		for (Entry<ItemStack, Float> entry : this.experience_list.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return ((Float) entry.getValue()).floatValue();
			}
		}
		return 0.0F;
	}
}