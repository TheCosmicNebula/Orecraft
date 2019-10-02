package com.zeher.orecraft.machine.client.tesr;

import com.zeher.orecraft.machine.core.tileentity.TileEntityCombinerCenter;
import com.zeher.orecraft.machine.core.tileentity.TileEntityCombinerPedestal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RendererCombinerPedestal extends TileEntitySpecialRenderer<TileEntityCombinerPedestal> {
	
	@Override
	public void render(TileEntityCombinerPedestal tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		if(tileentity == null) {
			return;
		}
		
		int slot = 0;

		TileEntityCombinerPedestal tile = (TileEntityCombinerPedestal) tileentity;

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.0F, (float) z + 0.5F);
		GlStateManager.rotate((float) Minecraft.getSystemTime() / 720.0F * 31.830988F, 0.0F, 1.0F, 0.0F);
		
		if ((tile.getStackInSlot(0).getItem() instanceof ItemBlock)) {
			GlStateManager.scale(0.8F, 0.8F, 0.8F);
		} else {
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
		}
		
		GlStateManager.pushAttrib();

		RenderHelper.disableStandardItemLighting();
		Minecraft.getMinecraft().getRenderItem().renderItem(tile.getStackInSlot(slot), ItemCameraTransforms.TransformType.FIXED);
		RenderHelper.enableStandardItemLighting();

		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
	}
}
