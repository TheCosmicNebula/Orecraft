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

public class MachineCompressorRecipes {
	private static final MachineCompressorRecipes compressing_base = new MachineCompressorRecipes();
	private final Map<ItemStack, ItemStack> compressingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

	public static MachineCompressorRecipes instance() {
		return compressing_base;
	}

	private MachineCompressorRecipes() {
	}

	public void addCompressing(Block input, ItemStack stack, float experience) {
		this.addCompressing(Item.getItemFromBlock(input), stack, experience);
	}

	public void addCompressing(Item input, ItemStack stack, float experience) {
		this.addCompressingRecipe(new ItemStack(input, 1, 32767), stack, experience);
	}

	public void addCompressingRecipe(ItemStack input, ItemStack stack, float experience) {
		if (getCompressingResult(input) != ItemStack.EMPTY) {
			net.minecraftforge.fml.common.FMLLog
					.info("Ignored compressing recipe with conflicting input: " + input + " = " + stack);
			return;
		}
		this.compressingList.put(input, stack);
		this.experienceList.put(stack, Float.valueOf(experience));
	}

	public ItemStack getCompressingResult(ItemStack stack) {
		for (Entry<ItemStack, ItemStack> entry : this.compressingList.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return (ItemStack) entry.getValue();
			}
		}
		return ItemStack.EMPTY;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem()
				&& (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, ItemStack> getCompressingList() {
		return this.compressingList;
	}

	public float getCompressingExperience(ItemStack stack) {
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