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

public class ExtractorRecipes {
	
	private static final ExtractorRecipes INSTANCE = new ExtractorRecipes();
	
	private final Map<ItemStack, ItemStack> extracting_list = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experience_list = Maps.<ItemStack, Float>newHashMap();

	public static ExtractorRecipes getInstance() {
		return INSTANCE;
	}

	private ExtractorRecipes() { }

	public void addExtracting(Block input, ItemStack stack, float experience) {
		this.addExtracting(Item.getItemFromBlock(input), stack, experience);
	}

	public void addExtracting(Item input, ItemStack stack, float experience) {
		this.addExtractingRecipe(new ItemStack(input, 1, 32767), stack, experience);
	}

	public void addExtractingRecipe(ItemStack input, ItemStack stack, float experience) {
		if (getExtractingResult(input) != ItemStack.EMPTY) {
			net.minecraftforge.fml.common.FMLLog.info("Ignored extracting recipe with conflicting input: " + input + " = " + stack);
			return;
		}
		this.extracting_list.put(input, stack);
		this.experience_list.put(stack, Float.valueOf(experience));
	}

	public ItemStack getExtractingResult(ItemStack stack) {
		for (Entry<ItemStack, ItemStack> entry : this.extracting_list.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return (ItemStack) entry.getValue();
			}
		}
		return ItemStack.EMPTY;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, ItemStack> getExtractingList() {
		return this.extracting_list;
	}

	public float getExtractingExperience(ItemStack stack) {
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