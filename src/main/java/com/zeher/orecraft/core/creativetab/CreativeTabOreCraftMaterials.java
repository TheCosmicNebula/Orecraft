package com.zeher.orecraft.core.creativetab;

import com.zeher.orecraft.OreCraft;
import com.zeher.orecraft.core.handlers.ItemHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabOreCraftMaterials extends CreativeTabs{
	
	public CreativeTabOreCraftMaterials(int par1, String par2str){
		super(par1, par2str);
	}

	public ItemStack getTabIconItem() {
		return new ItemStack(ItemHandler.ingot_copper);
	}

}