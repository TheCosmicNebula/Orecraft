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

public class MachineGrinderRecipes {
	private static final MachineGrinderRecipes grinding_base = new MachineGrinderRecipes();
	private final Map<ItemStack, ItemStack> grindingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

	public static MachineGrinderRecipes instance() {
		return grinding_base;
	}

	private MachineGrinderRecipes() {
	}

	public void addGrinding(Block input, ItemStack stack, float experience) {
		this.addGrinding(Item.getItemFromBlock(input), stack, experience);
	}

	public void addGrinding(Item input, ItemStack stack, float experience) {
		this.addGrindingRecipe(new ItemStack(input, 1, 32767), stack, experience);
	}

	public void addGrindingRecipe(ItemStack input, ItemStack stack, float experience) {
		if (getGrindingResult(input) != ItemStack.EMPTY) {
			net.minecraftforge.fml.common.FMLLog
					.info("Ignored grinding recipe with conflicting input: " + input + " = " + stack);
			return;
		}
		this.grindingList.put(input, stack);
		this.experienceList.put(stack, Float.valueOf(experience));
	}

	public ItemStack getGrindingResult(ItemStack stack) {
		for (Entry<ItemStack, ItemStack> entry : this.grindingList.entrySet()) {
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

	public Map<ItemStack, ItemStack> getGrindingList() {
		return this.grindingList;
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