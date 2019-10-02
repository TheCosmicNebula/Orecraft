package com.zeher.orecraft.core.creativetab;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.core.handler.BlockHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.*;

public class TabOrecraftDecor extends CreativeTabs {

	public TabOrecraftDecor(int par1, String par2str) {
		super(par1, par2str);
	}

	public ItemStack getTabIconItem() {
		return new ItemStack(Item.getItemFromBlock(BlockHandler.REGISTRATION.DECORATION.BLOCK_GLASS_BLACK));
	}

}