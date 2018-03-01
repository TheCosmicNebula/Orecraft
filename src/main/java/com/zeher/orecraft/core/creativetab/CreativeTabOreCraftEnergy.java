package com.zeher.orecraft.core.creativetab;

import com.zeher.orecraft.core.handlers.BlockHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabOreCraftEnergy extends CreativeTabs{
	
	public CreativeTabOreCraftEnergy(int par1, String par2str){
		super(par1, par2str);
	}


	public ItemStack getTabIconItem() {
		return new ItemStack(Item.getItemFromBlock(BlockHandler.block_powered_capacitor));
	}

}