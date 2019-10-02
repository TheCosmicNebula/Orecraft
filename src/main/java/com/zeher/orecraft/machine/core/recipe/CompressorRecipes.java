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

public class CompressorRecipes {
	
	private static final CompressorRecipes INSTANCE = new CompressorRecipes();
	
	private final Map<ItemStack, ItemStack> compressing_list = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experience_list = Maps.<ItemStack, Float>newHashMap();

	public static CompressorRecipes getInstance() {
		return INSTANCE;
	}

	private CompressorRecipes() { }

	public void addCompressing(Block input, ItemStack stack, float experience) {
		this.addCompressing(Item.getItemFromBlock(input), stack, experience);
	}

	public void addCompressing(Item input, ItemStack stack, float experience) {
		this.addCompressingRecipe(new ItemStack(input, 1, 32767), stack, experience);
	}

	public void addCompressingRecipe(ItemStack input, ItemStack stack, float experience) {
		if (getCompressingResult(input) != ItemStack.EMPTY) {
			net.minecraftforge.fml.common.FMLLog.info("Ignored compressing recipe with conflicting input: " + input + " = " + stack);
			return;
		}
		
		this.compressing_list.put(input, stack);
		this.experience_list.put(stack, Float.valueOf(experience));
	}

	public ItemStack getCompressingResult(ItemStack stack) {
		for (Entry<ItemStack, ItemStack> entry : this.compressing_list.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return (ItemStack) entry.getValue();
			}
		}
		return ItemStack.EMPTY;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, ItemStack> getCompressingList() {
		return this.compressing_list;
	}

	public float getCompressingExperience(ItemStack stack) {
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