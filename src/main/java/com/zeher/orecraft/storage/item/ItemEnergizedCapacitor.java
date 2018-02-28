package com.zeher.orecraft.storage.item;

import java.util.List;

import com.zeher.trzlib.api.TRZTextUtil;
import com.zeher.trzlib.storage.item.TRZItemStorage;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEnergizedCapacitor extends TRZItemStorage {

	public static int maxPower;

	public ItemEnergizedCapacitor(String name, int maxPower, CreativeTabs tab) {
		super(name, maxPower, tab);
		this.maxPower = maxPower;
		this.setMaxDamage(maxPower);
		this.setMaxStackSize(1);
		this.setCreativeTab(tab);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool) {
		super.addInformation(item, player, list, bool);
		if (!TRZTextUtil.isShiftKeyDown()) {
			list.add(TRZTextUtil.getInfoText("An item that holds energy."));
			if (TRZTextUtil.displayShiftForDetail) {
				list.add(TRZTextUtil.shiftForDetails());
			}
			return;
		}
		if (TRZTextUtil.isShiftKeyDown()) {
			list.add(TRZTextUtil.getFlavorText("Current Power: ") + TRZTextUtil.ORANGE
					+ ((maxPower - item.getItemDamage())) * 10000);
			list.add(TRZTextUtil.getFlavorText("Max Power: ") + TRZTextUtil.ORANGE + (this.maxPower * 10000));
		}
		return;
	}

}
