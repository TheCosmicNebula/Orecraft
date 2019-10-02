package com.zeher.orecraft.core.creativetab;

import com.zeher.orecraft.core.handler.BlockHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabOrecraftEnergy extends CreativeTabs {
	
	public TabOrecraftEnergy(int par1, String par2str){
		super(par1, par2str);
	}


	public ItemStack getTabIconItem() {
		return new ItemStack(Item.getItemFromBlock(BlockHandler.REGISTRATION.STORAGE.BLOCK_POWERED_CAPACITOR));
	}

}