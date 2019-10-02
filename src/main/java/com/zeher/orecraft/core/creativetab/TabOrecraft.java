package com.zeher.orecraft.core.creativetab;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.core.handler.BlockHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.*;

public class TabOrecraft extends CreativeTabs {

	public TabOrecraft(int index, String label) {
		super(index, label);
	}

	public ItemStack getTabIconItem() {
		return new ItemStack(Item.getItemFromBlock(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_COPPER));
	}

}