package com.zeher.orecraft.core.decor.block;

import com.zeher.zeherlib.api.interfaces.IMetaName;

import net.minecraft.block.BlockLog;
import net.minecraft.item.ItemStack;

public class BlockLogMod extends BlockLog implements IMetaName {

	@Override
	public String getSpecialName(ItemStack stack) {
		return null;
	}

}
