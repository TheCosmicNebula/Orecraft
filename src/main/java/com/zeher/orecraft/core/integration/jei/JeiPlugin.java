package com.zeher.orecraft.core.integration.jei;

import javax.annotation.Nonnull;

import com.zeher.orecraft.core.handler.*;
import com.zeher.orecraft.core.integration.jei.core.*;
import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.client.gui.*;
import com.zeher.orecraft.machine.core.container.*;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JeiPlugin implements IModPlugin {
	
	@Override
	public void register(@Nonnull IModRegistry registry) {
		final IJeiHelpers helper = registry.getJeiHelpers();
		final IGuiHelper gui_helper = helper.getGuiHelper();
		
		registry.addRecipeCategories(new GrinderCategory(gui_helper), new CompressorCategory(gui_helper), new ExtractorCategory(gui_helper));
		registry.addRecipes(GrinderRecipe.RecipeMaker.getGrindingRecipes(helper), OrecraftReference.JEI.GRINDER_UID);
		registry.addRecipes(CompressorRecipe.RecipeMaker.getCompressingRecipes(helper), OrecraftReference.JEI.COMPRESSOR_UID);
		registry.addRecipes(ExtractorRecipe.RecipeMaker.getExtractingRecipes(helper), OrecraftReference.JEI.EXTRACTOR_UID);
		
		registry.addRecipeClickArea(GuiPoweredGrinder.class, 104, 39, 16, 16, OrecraftReference.JEI.GRINDER_UID);
		registry.addRecipeClickArea(GuiEnergizedGrinder.class, 95, 39, 34, 16, OrecraftReference.JEI.GRINDER_UID);
		
		registry.addRecipeClickArea(GuiPoweredCompressor.class, 104, 39, 16, 16, OrecraftReference.JEI.COMPRESSOR_UID);
		registry.addRecipeClickArea(GuiEnergizedCompressor.class, 95, 39, 34, 16, OrecraftReference.JEI.COMPRESSOR_UID);
		
		registry.addRecipeClickArea(GuiPoweredExtractor.class, 104, 39, 16, 16, OrecraftReference.JEI.EXTRACTOR_UID);
		
		IRecipeTransferRegistry transfer = registry.getRecipeTransferRegistry();

		transfer.addRecipeTransferHandler(ContainerPoweredGrinder.class, OrecraftReference.JEI.GRINDER_UID, 0, 1, 3, 36);
		transfer.addRecipeTransferHandler(ContainerEnergizedGrinder.class, OrecraftReference.JEI.GRINDER_UID, 0, 1, 3, 36);
		
		transfer.addRecipeTransferHandler(ContainerPoweredCompressor.class, OrecraftReference.JEI.COMPRESSOR_UID, 0, 1, 3, 36);
		transfer.addRecipeTransferHandler(ContainerEnergizedCompressor.class, OrecraftReference.JEI.COMPRESSOR_UID, 0, 1, 3, 36);
		
		transfer.addRecipeTransferHandler(ContainerPoweredExtractor.class, OrecraftReference.JEI.EXTRACTOR_UID, 0, 1, 3, 36);

		registry.addRecipeCategoryCraftingItem(new ItemStack(BlockHandler.REGISTRATION.MACHINE.BLOCK_POWERED_FURNACE), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCategoryCraftingItem(new ItemStack(BlockHandler.REGISTRATION.MACHINE.BLOCK_POWERED_GRINDER), OrecraftReference.JEI.GRINDER_UID);
		registry.addRecipeCategoryCraftingItem(new ItemStack(BlockHandler.REGISTRATION.MACHINE.BLOCK_POWERED_COMPRESSOR), OrecraftReference.JEI.COMPRESSOR_UID);
		registry.addRecipeCategoryCraftingItem(new ItemStack(BlockHandler.REGISTRATION.MACHINE.BLOCK_POWERED_EXTRACTOR), OrecraftReference.JEI.EXTRACTOR_UID);
		
		registry.addRecipeCategoryCraftingItem(new ItemStack(BlockHandler.REGISTRATION.MACHINE.BLOCK_ENERGIZED_FURNACE), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCategoryCraftingItem(new ItemStack(BlockHandler.REGISTRATION.MACHINE.BLOCK_ENERGIZED_GRINDER), OrecraftReference.JEI.GRINDER_UID);
		registry.addRecipeCategoryCraftingItem(new ItemStack(BlockHandler.REGISTRATION.MACHINE.BLOCK_ENERGIZED_COMPRESSOR), OrecraftReference.JEI.COMPRESSOR_UID);
		
		/**@BLACKLIST**/
		IIngredientBlacklist blacklist = helper.getIngredientBlacklist();
	}
	
}
