package com.zeher.orecraft.machine.core.recipe;

import java.util.List;

import com.google.common.collect.Lists;
import com.zeher.orecraft.core.handler.BlockHandler;
import com.zeher.orecraft.core.handler.ItemHandler;
import com.zeher.zeherlib.api.tesr.EnumTESRColour;
import com.zeher.zeherlib.client.recipe.IRecipe;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class CombinerCraftingHandler {
	
	private static final boolean test_recipes = false;
	
    private static final CombinerCraftingHandler INSTANCE = new CombinerCraftingHandler();
    private final List<IRecipe> recipes = Lists.<IRecipe>newArrayList();
    
	public static CombinerCraftingHandler getInstance() {
		return INSTANCE;
	}

    private CombinerCraftingHandler() {
    	this.addCombinerRecipe(new ItemStack(ItemHandler.component_silicon_wafer_ender), new ItemStack(ItemHandler.component_silicon_wafer_basic), 120, EnumTESRColour.DARK_GREEN.colour,   
    			new Object[]{
	    			new ItemStack(ItemHandler.dust_ender), 
	    			new ItemStack(ItemHandler.dust_ender), 
	    			new ItemStack(ItemHandler.plate_ender), 
	    			new ItemStack(ItemHandler.plate_ender)});
    	
    	this.addCombinerRecipe(new ItemStack(ItemHandler.plate_ender), new ItemStack(ItemHandler.plate_rune), 100, EnumTESRColour.DARK_GREEN.colour,
    			new Object[]{
	    			new ItemStack(ItemHandler.plate_carbon),
	    			new ItemStack(ItemHandler.plate_carbon), 
	    			new ItemStack(ItemHandler.dust_ender), 
	    			new ItemStack(ItemHandler.dust_ender)});
    	
    	this.addCombinerRecipe(new ItemStack(ItemHandler.dust_basic), new ItemStack(Items.REDSTONE), 40, EnumTESRColour.RED.colour,
    			new Object[] {
	    			new ItemStack(Items.GLOWSTONE_DUST),
	    			new ItemStack(Items.GLOWSTONE_DUST)});
    	
    	this.addCombinerRecipe(new ItemStack(ItemHandler.component_circuit_three_raw), new ItemStack(ItemHandler.component_circuit_one), 140, EnumTESRColour.DARK_GREEN.colour, 
    			new Object[]{
	    			new ItemStack(ItemHandler.plate_ender), 
	    			new ItemStack(ItemHandler.plate_ender), 
	    			new ItemStack(ItemHandler.component_silicon_wafer_ender),
	    			new ItemStack(ItemHandler.component_silicon_wafer_ender), 
	    			new ItemStack(ItemHandler.component_silicon_wafer_ender), 
	    			new ItemStack(ItemHandler.component_silicon_wafer_ender), 
	    			new ItemStack(ItemHandler.component_circuit_one), 
	    			new ItemStack(ItemHandler.component_circuit_one)});
    	
    	this.addCombinerRecipe(new ItemStack(ItemHandler.component_silicon_wafer_basic), new ItemStack(ItemHandler.component_silicon_wafer), 80, EnumTESRColour.RED.colour,
    			new Object[] {
	    			new ItemStack(ItemHandler.dust_basic), 
	    			new ItemStack(ItemHandler.dust_basic), 
	    			new ItemStack(ItemHandler.component_silicon_wafer),
	    			new ItemStack(ItemHandler.component_silicon_wafer)});
    	
    	if (this.test_recipes == true) {
    		this.testRecipes();
    	}
    }
    
    public void testRecipes () {
    	this.addCombinerRecipe(new ItemStack(Blocks.BEACON), new ItemStack(BlockHandler.REGISTRATION.OREBLOCKS.BLOCK_RUNE), 160, EnumTESRColour.LIGHT_GREEN.colour,
    			new Object[] {
					new ItemStack(ItemHandler.gem_godone),
					new ItemStack(ItemHandler.gem_godone),
					new ItemStack(ItemHandler.gem_godtwo),
					new ItemStack(ItemHandler.gem_godtwo)});
    	
    	this.addCombinerRecipe(new ItemStack(ItemHandler.dust_adamantite), new ItemStack(BlockHandler.REGISTRATION.OREBLOCKS.BLOCK_RUNE), 160, EnumTESRColour.LIGHT_GREEN.colour,
    			new Object[] {
					new ItemStack(ItemHandler.gem_amythest),
					new ItemStack(ItemHandler.gem_godone),
					new ItemStack(ItemHandler.gem_godtwo),
					new ItemStack(ItemHandler.gem_godtwo)});
    	
    	this.addCombinerRecipe(new ItemStack(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_COPPER), new ItemStack(ItemHandler.component_circuit_one), 160, EnumTESRColour.LIGHT_GREEN.colour,
    			new Object[] {
					new ItemStack(ItemHandler.gem_amythest),
					new ItemStack(ItemHandler.gem_godtwo),
					new ItemStack(ItemHandler.gem_godtwo),
					new ItemStack(ItemHandler.gem_godtwo)});
    }
    
    public void addCombinerRecipe(ItemStack stack, ItemStack focus_stack, Integer process_time, float[] colour, Object... recipeComponents) {
        List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for (Object object : recipeComponents)  {
            if (object instanceof ItemStack) {
                list.add(((ItemStack)object).copy());
            } else if (object instanceof Item) {
                list.add(new ItemStack((Item)object));
            } else if (object instanceof Block){
            
            	list.add(new ItemStack((Block) object));
            	
            } else if (!(object instanceof Block)){
            	throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
            }
        }
        
        this.recipes.add(new CombinerRecipe(stack, list, focus_stack, colour, process_time));
    }

    /**
     * Adds an @IRecipe to the list of crafting recipes.
     */
    public void addRecipe(IRecipe recipe)  {
        this.recipes.add(recipe);
    }

    /**
     * Retrieves an @ItemStack that has multiple recipes for it.
     */
    public ItemStack findMatchingRecipe(List<ItemStack> list, World worldIn) {
        for (IRecipe irecipe : this.recipes) {
            if (irecipe.matches(list, worldIn)) {
                return irecipe.getRecipeOutput();
            }
        }
        
        return ItemStack.EMPTY;
    }

	public ItemStack findFocusStack(List<ItemStack> list, World world) {
		for (IRecipe irecipe : this.recipes) {
			if (irecipe.matches(list, world)) {
				return irecipe.getFocusStack(list);
			}
		}
		return ItemStack.EMPTY;
	}

    public NonNullList<ItemStack> getRemainingItems(List<ItemStack> list, World worldIn) {
        for (IRecipe irecipe : this.recipes)   {
            if (irecipe.matches(list, worldIn))  {
                return irecipe.getRemainingItems(list);
            }
        }

        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(list.size(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)  {
            nonnulllist.set(i, list.get(i));
        }

        return nonnulllist;
    }

    /**
     * returns the List<> of all recipes
     */
    public List<IRecipe> getRecipeList() {
        return this.recipes;
    }

	public Integer findProcessTime(List<ItemStack> list, World world) {
		for (IRecipe irecipe : this.recipes) {
			if (irecipe.matches(list, world)) {
				return irecipe.getProcessTime(list);
			}
		}
		return 0;
	}
	
	public float[] findColour(List<ItemStack> list, World world) {
		for (IRecipe irecipe : this.recipes) {
			if (irecipe.matches(list, world)) {
				return irecipe.getColour(list);
			}
		}
		return EnumTESRColour.WHITE.colour;
	}

}