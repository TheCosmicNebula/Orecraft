package com.zeher.orecraft.core.creativetab;

import com.zeher.orecraft.OreCraft;
import com.zeher.orecraft.core.handlers.BlockHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.*;

public class CreativeTabOreCraft extends CreativeTabs{
	
	public CreativeTabOreCraft(int par1, String par2str){
		super(par1, par2str);
	}

	public ItemStack getTabIconItem() {
		return new ItemStack(Item.getItemFromBlock(BlockHandler.block_ore_copper));
	}

}