package com.zeher.orecraft.core.handler;

import com.zeher.orecraft.machine.core.recipe.*;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class RegistryHandler {

	public static void addGrinding(Item input, ItemStack output, float xp) {
		GrinderRecipes.getInstance().addGrinding(input, output, xp);
	}

	public static void addGrinding(Block input, ItemStack output, float xp) {
		GrinderRecipes.getInstance().addGrinding(input, output, xp);
	}

	public static void addExtracting(Item input, ItemStack output, float xp) {
		ExtractorRecipes.getInstance().addExtracting(input, output, xp);
	}

	public static void addExtracting(Block input, ItemStack output, float xp) {
		ExtractorRecipes.getInstance().addExtracting(input, output, xp);
	}

	public static void addProcessorClean(Item input, ItemStack output, float xp) {
		OreProcessorCleaningRecipes.getInstance().addCleaning(input, output, xp);
	}

	public static void addProcessorClean(Block input, ItemStack output, float xp) {
		OreProcessorCleaningRecipes.getInstance().addCleaning(input, output, xp);
	}
	
	public static void addProcessorRefine(Item input, ItemStack output, float xp) {
		OreProcessorRefiningRecipes.getInstance().addRefining(input, output, xp);
	}

	public static void addProcessorRefine(Block input, ItemStack output, float xp) {
		OreProcessorRefiningRecipes.getInstance().addRefining(input, output, xp);
	}

	public static void addCompressing(Item input, ItemStack output, float xp) {
		CompressorRecipes.getInstance().addCompressing(input, output, xp);
	}

	public static void addCompressing(Block input, ItemStack output, float xp) {
		CompressorRecipes.getInstance().addCompressing(input, output, xp);
	}
	
	public static void addFluidMeltRecipe(Item input, FluidStack output){
		FluidProcessorMeltRecipes.getInstance().addRecipe(input, output);
	}
	
	public static void addFluidMeltRecipe(Block input, FluidStack output){
		FluidProcessorMeltRecipes.getInstance().addRecipe(input, output);	
	}
}
