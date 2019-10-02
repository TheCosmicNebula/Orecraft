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

public class GrinderRecipes {
	
	private static final GrinderRecipes INSTANCE = new GrinderRecipes();
	
	private final Map<ItemStack, ItemStack> grinding_list = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experience_list = Maps.<ItemStack, Float>newHashMap();

	public static GrinderRecipes getInstance() {
		return INSTANCE;
	}

	private GrinderRecipes() { }

	public void addGrinding(Block input, ItemStack stack, float experience) {
		this.addGrinding(Item.getItemFromBlock(input), stack, experience);
	}

	public void addGrinding(Item input, ItemStack stack, float experience) {
		this.addGrindingRecipe(new ItemStack(input, 1, 32767), stack, experience);
	}

	public void addGrindingRecipe(ItemStack input, ItemStack stack, float experience) {
		if (this.getGrindingResult(input) != ItemStack.EMPTY) {
			net.minecraftforge.fml.common.FMLLog.info("Ignored grinding recipe with conflicting input: " + input + " = " + stack);
			return;
		}
		this.grinding_list.put(input, stack);
		this.experience_list.put(stack, experience);
	}

	public ItemStack getGrindingResult(ItemStack stack) {
		for (Entry<ItemStack, ItemStack> entry : this.grinding_list.entrySet()) {
			if (this.compareItemStacks(stack, entry.getKey())) {
				return entry.getValue();
			}
		}
		return ItemStack.EMPTY;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, ItemStack> getGrindingList() {
		return this.grinding_list;
	}

	public float getGrindingExperience(ItemStack stack) {
		for (Entry<ItemStack, Float> entry : this.experience_list.entrySet()) {
			if (this.compareItemStacks(stack, entry.getKey())) {
				return entry.getValue().floatValue();
			}
		}
		return 0.0F;
	}
}