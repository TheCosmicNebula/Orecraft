package com.zeher.orecraft.core.integration.jei.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.core.recipe.ExtractorRecipes;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class ExtractorRecipe extends BlankRecipeWrapper {

	private final List<List<ItemStack>> inputs;
	private final ItemStack output;
	
	public ExtractorRecipe(List<ItemStack>inputs, ItemStack output) {
		this.inputs = Collections.singletonList(inputs);
		this.output = output;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, output);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		ExtractorRecipes recipes = ExtractorRecipes.getInstance();
		
		float experience = recipes.getExtractingExperience(output);
		
		if (experience > 0) {
			String experience_string = I18n.format(OrecraftReference.JEI.EXPERIENCE);
			FontRenderer renderer = minecraft.fontRenderer;
			
			int string_width = renderer.getStringWidth(experience_string);
			
			renderer.drawString(experience + " " + experience_string, recipeWidth - string_width + 15, 5, Color.gray.getRGB());
		}
	}
	
	public static class RecipeMaker {
		
		public static List<ExtractorRecipe> getExtractingRecipes(IJeiHelpers helper) {
			IStackHelper stack_helper = helper.getStackHelper();
			ExtractorRecipes recipes = ExtractorRecipes.getInstance();
			
			Map<ItemStack, ItemStack> extracting_map = recipes.getExtractingList();
			
			List<ExtractorRecipe> recipes_list = new ArrayList<>();
			
			for (Map.Entry<ItemStack, ItemStack> entry : extracting_map.entrySet()) {
				ItemStack input = entry.getKey();
				ItemStack output = entry.getValue();
				
				List<ItemStack> inputs = stack_helper.getSubtypes(input);
				ExtractorRecipe recipe = new ExtractorRecipe(inputs, output);
				
				recipes_list.add(recipe);
			}
			
			return recipes_list;
		}
	}

}
