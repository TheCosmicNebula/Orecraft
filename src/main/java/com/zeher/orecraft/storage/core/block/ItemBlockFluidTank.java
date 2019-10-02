package com.zeher.orecraft.storage.core.block;

import java.util.List;

import javax.annotation.Nullable;

import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.util.TextUtil;
import com.zeher.zeherlib.fluid.ModFluidUtil;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockFluidTank extends ItemBlock {
	public ItemBlockFluidTank(Block block) {
		super(block);
		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (stack.hasTagCompound()) {
			NBTTagCompound tag = stack.getTagCompound();
			FluidStack fluid = null;
			
			if (!TextUtil.isShiftKeyDown()) {
				tooltip.add(TextUtil.getInfoText(Reference.TOOLTIP.FLUIDITEM));
				if (TextUtil.displayShiftForDetail) {
					tooltip.add(TextUtil.shiftForDetails());
				}
			}
			
			if (TextUtil.isShiftKeyDown()) {
				if (tag.hasKey("Fluid")) {
					fluid = FluidStack.loadFluidStackFromNBT((NBTTagCompound) tag.getCompoundTag("Fluid"));
					tooltip.add(TextUtil.getFlavorText("Fluid : ") + fluid.getLocalizedName());

					int amount = fluid != null ? fluid.amount : 0;
					tooltip.add(TextUtil.getFlavorText("Amount: ") + amount + " / " + 16000 + " mB");
				} else {
					tooltip.add(TextUtil.getFlavorText("Fluid : ") + "Empty");

					tooltip.add(TextUtil.getFlavorText("Amount: ") + 0 + " / " + 16000 + " mB");
				}
			}
			
		} else if (!stack.hasTagCompound()) {
			if (!TextUtil.isShiftKeyDown()) {
				tooltip.add(TextUtil.getInfoText(Reference.TOOLTIP.FLUIDITEM));
				if (TextUtil.displayShiftForDetail) {
					tooltip.add(TextUtil.shiftForDetails());
				}
			}
			
			if (TextUtil.isShiftKeyDown()) {
				tooltip.add(TextUtil.getFlavorText("Fluid : ") + "Empty");

				tooltip.add(TextUtil.getFlavorText("Amount: ") + 0 + " / " + 16000 + " mB");
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List itemList) {
		itemList.add(ModFluidUtil.getTankStackFromData(this.block));
	}
}
