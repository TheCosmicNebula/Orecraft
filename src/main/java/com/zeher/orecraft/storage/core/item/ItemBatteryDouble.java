package com.zeher.orecraft.storage.core.item;

import java.util.List;

import javax.annotation.Nullable;

import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.util.TextUtil;
import com.zeher.zeherlib.storage.item.ItemStorage;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBatteryDouble extends ItemStorage {

	public static int maxPower;

	public ItemBatteryDouble(String name, int maxPower, CreativeTabs tab) {
		super(name, maxPower, tab);
		this.maxPower = maxPower;
		this.setMaxDamage(maxPower);
		this.setMaxStackSize(1);
		this.setCreativeTab(tab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (!TextUtil.isShiftKeyDown()) {
			tooltip.add(TextUtil.getInfoText(Reference.TOOLTIP.ENERGYITEM));
			if (TextUtil.displayShiftForDetail) {
				tooltip.add(TextUtil.shiftForDetails());
			}
			return;
		}
		if (TextUtil.isShiftKeyDown()) {
			tooltip.add(TextUtil.getFlavorText(Reference.TOOLTIP.CURRENT_POWER) + TextUtil.ORANGE + ((maxPower - stack.getItemDamage())) * 1000);
			tooltip.add(TextUtil.getFlavorText(Reference.TOOLTIP.MAX_POWER) + TextUtil.ORANGE + (this.maxPower * 1000));
		}
		return;
	}

}
