package com.zeher.orecraft.core.integration.jei.core;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.core.reference.OrecraftReference;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class CompressorCategory extends BlankRecipeCategory {

	private final String LOC_TITLE;
	private final IDrawable BACKGROUND;
	
	private final IDrawableAnimated PROCESS;
	private final IDrawableAnimated STORED;
	
	protected static final int INPUT_SLOT = 0;
	protected static final int OUTPUT_SLOT = 2;
	
	private final ResourceLocation GUI = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/powered/compressor/gui_compressor.png");
	
	public CompressorCategory(IGuiHelper helper) {
		LOC_TITLE = I18n.format(OrecraftReference.JEI.COMPRESSOR_UNLOC);
		BACKGROUND = helper.createDrawable(new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/powered/compressor/gui_compressor_jei.png"), 0, 0, 78, 72);
		
		IDrawableStatic process_draw = helper.createDrawable(this.GUI, 176, 0, 16, 16);
		IDrawableStatic stored_draw = helper.createDrawable(this.GUI, 176, 16, 18, 40);
		
		this.PROCESS = helper.createAnimatedDrawable(process_draw, 100, IDrawableAnimated.StartDirection.TOP, false);
		this.STORED = helper.createAnimatedDrawable(stored_draw, 200, IDrawableAnimated.StartDirection.TOP, true);
	}
	
	@Override
	public String getUid() {
		return OrecraftReference.JEI.COMPRESSOR_UID;
	}

	@Override
	public String getTitle() {
		return LOC_TITLE;
	}

	@Override
	public String getModName() {
		return Orecraft.MOD_NAME;
	}

	@Override
	public IDrawable getBackground() {
		return BACKGROUND;
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		PROCESS.draw(minecraft, 55, 28);
		STORED.draw(minecraft, 27, 5);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		if (!(recipeWrapper instanceof CompressorRecipe)) {
			return;
		}
		
		IGuiItemStackGroup gui_stacks = recipeLayout.getItemStacks();
		
		gui_stacks.init(INPUT_SLOT, true, 54, 6);
		gui_stacks.init(OUTPUT_SLOT, false, 54, 48);
		
		gui_stacks.set(ingredients);
	}

}
