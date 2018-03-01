package com.zeher.orecraft.core.creativetab;

import com.zeher.orecraft.core.handlers.ItemHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabOreCraftTools extends CreativeTabs{
	
	public CreativeTabOreCraftTools(int par1, String par2str){
		super(par1, par2str);
	}

	public ItemStack getTabIconItem() {
		return new ItemStack(ItemHandler.tool_machinewrench);
	}
}