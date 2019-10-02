package com.zeher.orecraft.machine.client.tesr.internal;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.client.tesr.model.internal.ModelBlockFurnaceInternals;
import com.zeher.orecraft.machine.core.block.BlockPoweredFurnace;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredFluidCrafter;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredFurnace;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RendererPoweredFurnaceInternals extends TileEntitySpecialRenderer<TileEntityPoweredFurnace> {
	
	@SideOnly(Side.CLIENT)
	private ModelBlockFurnaceInternals model;
	
	public RendererPoweredFurnaceInternals() {
		this.model = new ModelBlockFurnaceInternals();
	}
	
	@Override
	public void render(TileEntityPoweredFurnace tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		if (tileentity == null) {
			return;
		}
		
		IBlockState state = tileentity.getWorld().getBlockState(tileentity.getPos());
		BlockPoweredFurnace block = (BlockPoweredFurnace) state.getBlock();
		EnumFacing enu = state.getValue(block.FACING);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
		GlStateManager.rotate(180F, 1.0F, 0.0F, 0.0F);
		this.bindTexture(OrecraftReference.TESR.RESOURCE.TESR_FURNACE_INTERNALS);

		GL11.glColor4f(1, 1, 1, 1);
		
		if (tileentity.canSmelt() && tileentity.getCookTime(0) > 0 && tileentity.hasStored()) {
			this.model.renderCoilsOn(0.0625F);
		} else {
			this.model.renderCoilsOff(0.0625F);
		}
		
		GlStateManager.popMatrix();
		
		if (tileentity.getCookTime(0) > (tileentity.getCookSpeed() - 10)) {
			
			GlStateManager.pushMatrix();
			
			ItemStack result = FurnaceRecipes.instance().getSmeltingResult(tileentity.getStackInSlot(0));
			
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			
			if (enu.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else  if (enu.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (enu.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			}
			
			if (result.getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.6F, 0.6F, 0.6F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.4F, 0.4F, 0.4F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(result, ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
			
		} else if (!tileentity.getStackInSlot(0).isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			
			if (enu.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (enu.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (enu.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			}
			
			if (tileentity.getStackInSlot(0).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.6F, 0.6F, 0.6F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.4F, 0.4F, 0.4F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(0), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
		
		
	}

}
