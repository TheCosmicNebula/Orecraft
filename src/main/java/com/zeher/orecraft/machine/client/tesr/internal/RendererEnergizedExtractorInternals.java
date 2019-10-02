package com.zeher.orecraft.machine.client.tesr.internal;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.client.tesr.model.internal.ModelBlockExtractorInternals;
import com.zeher.orecraft.machine.core.block.BlockEnergizedExtractor;
import com.zeher.orecraft.machine.core.recipe.ExtractorRecipes;
import com.zeher.orecraft.machine.core.tileentity.TileEntityEnergizedCompressor;
import com.zeher.orecraft.machine.core.tileentity.TileEntityEnergizedExtractor;

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

public class RendererEnergizedExtractorInternals extends TileEntitySpecialRenderer<TileEntityEnergizedExtractor> {

	@SideOnly(Side.CLIENT)
	private ModelBlockExtractorInternals model;
	
	public RendererEnergizedExtractorInternals() {
		this.model = new ModelBlockExtractorInternals();
	}
	
	@Override
	public void render(TileEntityEnergizedExtractor tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		if (tileentity == null) {
			return;
		}
		
		IBlockState state = tileentity.getWorld().getBlockState(tileentity.getPos());
		BlockEnergizedExtractor block = (BlockEnergizedExtractor) state.getBlock();
		EnumFacing facing = state.getValue(block.FACING);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5F, y + 1.5F, z + 0.5F);
		GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		
		this.bindTexture(OrecraftReference.TESR.RESOURCE.TESR_EXTRACTOR_INTERNALS);
		GL11.glColor4f(1F, 1F, 1F, 1F);

		GlStateManager.popMatrix();
		
		GlStateManager.pushMatrix();
		
		GlStateManager.translate(x + 0.5F, y + 1.5F, z + 0.5F);
		GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		
		if (tileentity.canExtractOne() && tileentity.getCookTime(0) > 0 && tileentity.hasStored() || tileentity.canExtractTwo() && tileentity.getCookTime(1) > 0 && tileentity.hasStored()) {
			GlStateManager.rotate(Minecraft.getSystemTime() / 720F * 120, 0.0F, 1.0F, 0.0F);
		}
		
		GlStateManager.rotate(45, 0.0F, 1.0F, 0.0F);
		this.model.renderArms(0.0625F);
		
		GlStateManager.popMatrix();
		
		if (tileentity.getCookTime(0) > (tileentity.getCookSpeed() - 10)) {
			
			GlStateManager.pushMatrix();
			
			ItemStack result = FurnaceRecipes.instance().getSmeltingResult(tileentity.getStackInSlot(0));
			
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else  if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			}
			
			if (result.getItem() instanceof ItemBlock) {
				GlStateManager.translate(+0.1F, -0.05F, 0.0F);
				GlStateManager.scale(0.3F, 0.3F, 0.3F);
			} else {
				GlStateManager.translate(+0.1F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
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
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			}
			
			if (tileentity.getStackInSlot(0).getItem() instanceof ItemBlock) {
				GlStateManager.translate(+0.1F, -0.05F, 0.0F);
				GlStateManager.scale(0.3F, 0.3F, 0.3F);
			} else {
				GlStateManager.translate(+0.1F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(0), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
		
		if (tileentity.getCookTime(1) > (tileentity.getCookSpeed() - 10)) {
			
			GlStateManager.pushMatrix();
			
			ItemStack result = FurnaceRecipes.instance().getSmeltingResult(tileentity.getStackInSlot(2));
			
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else  if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			}
			
			if (result.getItem() instanceof ItemBlock) {
				GlStateManager.translate(-0.1F, -0.05F, 0.0F);
				GlStateManager.scale(0.3F, 0.3F, 0.3F);
			} else {
				GlStateManager.translate(-0.1F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(result, ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
			
		} else if (!tileentity.getStackInSlot(2).isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			}
			
			if (tileentity.getStackInSlot(2).getItem() instanceof ItemBlock) {
				GlStateManager.translate(-0.1F, -0.05F, 0.0F);
				GlStateManager.scale(0.3F, 0.3F, 0.3F);
			} else {
				GlStateManager.translate(-0.1F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(2), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
	}
}
