package com.zeher.orecraft.core.creativetab;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.core.handler.ItemHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabOrecraftMaterial extends CreativeTabs {
	
	public TabOrecraftMaterial(int par1, String par2str){
		super(par1, par2str);
	}

	public ItemStack getTabIconItem() {
		return new ItemStack(ItemHandler.ingot_copper);
	}

}