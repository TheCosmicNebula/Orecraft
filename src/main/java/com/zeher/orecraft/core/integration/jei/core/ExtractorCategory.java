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

public class ExtractorCategory extends BlankRecipeCategory {

	private final String loc_title;
	private final IDrawable background;
	
	private final IDrawableAnimated process;
	private final IDrawableAnimated stored;
	
	protected static final int input_slot = 0;
	protected static final int output_slot = 2;
	
	private final ResourceLocation gui = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/powered/extractor/gui_extractor.png");
	
	public ExtractorCategory(IGuiHelper helper) {
		loc_title = I18n.format(OrecraftReference.JEI.EXTRACTOR_UNLOC);
		background = helper.createDrawable(new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/powered/extractor/gui_extractor_jei.png"), 0, 0, 78, 72);
		
		IDrawableStatic process_draw = helper.createDrawable(this.gui, 176, 0, 16, 16);
		IDrawableStatic stored_draw = helper.createDrawable(this.gui, 176, 16, 18, 40);
		
		this.process = helper.createAnimatedDrawable(process_draw, 100, IDrawableAnimated.StartDirection.TOP, false);
		this.stored = helper.createAnimatedDrawable(stored_draw, 200, IDrawableAnimated.StartDirection.TOP, true);
	}
	
	@Override
	public String getUid() {
		return OrecraftReference.JEI.EXTRACTOR_UID;
	}

	@Override
	public String getTitle() {
		return loc_title;
	}

	@Override
	public String getModName() {
		return Orecraft.MOD_NAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		process.draw(minecraft, 55, 28);
		stored.draw(minecraft, 27, 5);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		if (!(recipeWrapper instanceof ExtractorRecipe)) {
			return;
		}
		
		IGuiItemStackGroup gui_stacks = recipeLayout.getItemStacks();
		
		gui_stacks.init(input_slot, true, 54, 6);
		gui_stacks.init(output_slot, false, 54, 48);
		
		gui_stacks.set(ingredients);
	}

}
