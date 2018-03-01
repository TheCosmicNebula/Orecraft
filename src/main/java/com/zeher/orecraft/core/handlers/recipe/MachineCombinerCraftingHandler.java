package com.zeher.orecraft.core.handlers.recipe;

import java.util.List;

import com.google.common.collect.Lists;
import com.zeher.orecraft.core.handlers.ItemHandler;
import com.zeher.trzlib.client.recipe.ITRZRecipe;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class MachineCombinerCraftingHandler
{
    /** The static instance of this class */
    private static final MachineCombinerCraftingHandler INSTANCE = new MachineCombinerCraftingHandler();
    /** A list of all the recipes added */
    private final List<ITRZRecipe> recipes = Lists.<ITRZRecipe>newArrayList();

    /**
     * Returns the static instance of this class
     */
    public static MachineCombinerCraftingHandler getInstance()
    {
        /** The static instance of this class */
        return INSTANCE;
    }

    private MachineCombinerCraftingHandler()
    {
    	this.addCombinerRecipe(new ItemStack(ItemHandler.component_silicon_wafer_ender), new ItemStack(ItemHandler.component_silicon_wafer_basic), 120, new Object[]{
    			new ItemStack(ItemHandler.dust_ender), 
    			new ItemStack(ItemHandler.dust_ender), 
    			new ItemStack(ItemHandler.plate_ender), 
    			new ItemStack(ItemHandler.plate_ender)});
    	this.addCombinerRecipe(new ItemStack(ItemHandler.plate_ender), new ItemStack(ItemHandler.plate_rune), 100, new Object[]{
    			new ItemStack(ItemHandler.plate_carbon),
    			new ItemStack(ItemHandler.plate_carbon), 
    			new ItemStack(ItemHandler.dust_ender), 
    			new ItemStack(ItemHandler.dust_ender)});
    	this.addCombinerRecipe(new ItemStack(ItemHandler.dust_basic), new ItemStack(Items.REDSTONE), 40, new Object[] {
    			new ItemStack(Items.GLOWSTONE_DUST),
    			new ItemStack(Items.GLOWSTONE_DUST)
    	});
    	/*this.addCombinerRecipe(new ItemStack(ItemHandler.component_circuit_three_raw), new Object[]{
    			new ItemStack(ItemHandler.plate_ender), 
    			new ItemStack(ItemHandler.plate_ender), 
    			new ItemStack(ItemHandler.component_silicon_wafer_ender),
    			new ItemStack(ItemHandler.component_silicon_wafer_ender), 
    			new ItemStack(ItemHandler.component_silicon_wafer_ender), 
    			new ItemStack(ItemHandler.component_silicon_wafer_ender), 
    			new ItemStack(ItemHandler.component_circuit_one), 
    			new ItemStack(ItemHandler.component_circuit_one)});
    	this.addCombinerRecipe(new ItemStack(ItemHandler.component_silicon_wafer_basic, 2), new Object[]{
    			new ItemStack(ItemHandler.dust_basic), 
    			new ItemStack(ItemHandler.dust_basic), 
    			new ItemStack(ItemHandler.component_silicon_wafer),
    			new ItemStack(ItemHandler.component_silicon_wafer)});
    	this.addCombinerRecipe(new ItemStack(ItemHandler.dust_basic, 8), new Object[]{
    			new ItemStack(Items.REDSTONE), 
    			new ItemStack(Items.REDSTONE), 
    			new ItemStack(Items.REDSTONE),
    			new ItemStack(Items.REDSTONE), 
    			new ItemStack(Items.GLOWSTONE_DUST), 
    			new ItemStack(Items.GLOWSTONE_DUST), 
    			new ItemStack(Items.GLOWSTONE_DUST), 
    			new ItemStack(Items.GLOWSTONE_DUST)});*/
    }

    /**
     * Adds a shapeless crafting recipe to the the game.
     */
    public void addCombinerRecipe(ItemStack stack, ItemStack focus_stack, Integer process_time, Object... recipeComponents)
    {
        List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for (Object object : recipeComponents)
        {
            if (object instanceof ItemStack)
            {
                list.add(((ItemStack)object).copy());
            }
            else if (object instanceof Item)
            {
                list.add(new ItemStack((Item)object));
            }
            else
            {
                if (!(object instanceof Block))
                {
                    throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
                }

                list.add(new ItemStack((Block)object));
            }
        }

        this.recipes.add(new MachineCombinerRecipe(stack, list, focus_stack, process_time));
    }

    /**
     * Adds an ITRZRecipe to the list of crafting recipes.
     */
    public void addRecipe(ITRZRecipe recipe)
    {
        this.recipes.add(recipe);
    }

    /**
     * Retrieves an ItemStack that has multiple recipes for it.
     */
    public ItemStack findMatchingRecipe(List<ItemStack> list, World worldIn)
    {
        for (ITRZRecipe irecipe : this.recipes)
        {
            if (irecipe.matches(list, worldIn))
            {
                return irecipe.getCraftingResult(list);
            }
        }
        return ItemStack.EMPTY;
    }

	public ItemStack findFocusStack(List<ItemStack> list, World world) {
		for (ITRZRecipe irecipe : this.recipes) {
			if (irecipe.matches(list, world)) {
				return irecipe.getFocusStack(list);
			}
		}
		return ItemStack.EMPTY;
	}

    public NonNullList<ItemStack> getRemainingItems(List<ItemStack> list, World worldIn)
    {
        for (ITRZRecipe irecipe : this.recipes)
        {
            if (irecipe.matches(list, worldIn))
            {
                return irecipe.getRemainingItems(list);
            }
        }

        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(list.size(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            nonnulllist.set(i, list.get(i));
        }

        return nonnulllist;
    }

    /**
     * returns the List<> of all recipes
     */
    public List<ITRZRecipe> getRecipeList()
    {
        return this.recipes;
    }

	public Integer findProcessTime(List<ItemStack> list, World world) {
		for (ITRZRecipe irecipe : this.recipes) {
			if (irecipe.matches(list, world)) {
				return irecipe.getProcessTime(list);
			}
		}
		return Integer.valueOf(0);
	}

}