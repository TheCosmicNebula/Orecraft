package com.zeher.orecraft.storage.block;

import com.zeher.trzlib.api.TRZTextUtil;
import com.zeher.trzlib.storage.TRZFluidUtil;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockFluidTank extends ItemBlock {
	public ItemBlockFluidTank(Block block) {
		super(block);
		this.setMaxStackSize(1);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean isAdvanced) {
		if (stack.hasTagCompound()) {
			NBTTagCompound tag = stack.getTagCompound();
			FluidStack fluid = null;
			
			if (!TRZTextUtil.isShiftKeyDown()) {
				tooltip.add(TRZTextUtil.getInfoText("A block that stores fluids."));
				if (TRZTextUtil.displayShiftForDetail) {
					tooltip.add(TRZTextUtil.shiftForDetails());
				}
			}
			
			if (TRZTextUtil.isShiftKeyDown()) {
				if (tag.hasKey("Fluid")) {
					fluid = FluidStack.loadFluidStackFromNBT((NBTTagCompound) tag.getCompoundTag("Fluid"));
					tooltip.add(TRZTextUtil.getFlavorText("Fluid : ") + fluid.getLocalizedName());

					int amount = fluid != null ? fluid.amount : 0;
					tooltip.add(TRZTextUtil.getFlavorText("Amount: ") + amount + " / " + 16000 + " mB");
				} else {
					tooltip.add(TRZTextUtil.getFlavorText("Fluid : ") + "Empty");

					tooltip.add(TRZTextUtil.getFlavorText("Amount: ") + 0 + " / " + 16000 + " mB");
				}
			}
			
		} else if (!stack.hasTagCompound()) {
			NBTTagCompound tag1 = stack.getTagCompound();
			if (!TRZTextUtil.isShiftKeyDown()) {
				tooltip.add(TRZTextUtil.getInfoText("A block that stores fluids."));
				if (TRZTextUtil.displayShiftForDetail) {
					tooltip.add(TRZTextUtil.shiftForDetails());
				}
			}
			
			if (TRZTextUtil.isShiftKeyDown()) {
				tooltip.add(TRZTextUtil.getFlavorText("Fluid : ") + "Empty");

				tooltip.add(TRZTextUtil.getFlavorText("Amount: ") + 0 + " / " + 16000 + " mB");
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List itemList) {
		itemList.add(TRZFluidUtil.getTankStackFromData(this.block));
	}
}
