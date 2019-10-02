package com.zeher.orecraft.machine.client.tesr;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.client.tesr.model.ModelBlockOreProcessor;
import com.zeher.orecraft.machine.core.block.BlockPoweredOreProcessor;
import com.zeher.orecraft.machine.core.recipe.OreProcessorCleaningRecipes;
import com.zeher.orecraft.machine.core.recipe.OreProcessorRefiningRecipes;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredOreProcessor;

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

public class RendererPoweredOreProcessor extends TileEntitySpecialRenderer<TileEntityPoweredOreProcessor> {

	@SideOnly(Side.CLIENT)
	private ModelBlockOreProcessor model;
	
	public RendererPoweredOreProcessor() {
		this.model = new ModelBlockOreProcessor();
	}
	
	private static final ResourceLocation texture_location = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "machine/powered/oreprocessor/ore_processor.png");
	
	@Override
	public void render(TileEntityPoweredOreProcessor tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		if (tileentity == null) {
			return;
		}
		
		IBlockState state = tileentity.getWorld().getBlockState(tileentity.getPos());
		BlockPoweredOreProcessor block = (BlockPoweredOreProcessor) state.getBlock();
		EnumFacing facing = state.getValue(block.FACING);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5F, y + 1.5F, z + 0.5F);
		GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		
		this.bindTexture(texture_location);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1, 1, 1, 1);
		
		if (facing.equals(EnumFacing.SOUTH)) {
			GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
		} else if (facing.equals(EnumFacing.WEST)) {
			GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
		} else if (facing.equals(EnumFacing.EAST)) {
			GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
		}
		
		this.model.renderBase(0.0625F);

		if (tileentity.getMode() == 0) {
			this.model.renderItemStand(0.0625F, false);
		} else {
			this.model.renderItemStand(0.0625F, true);
		}
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GlStateManager.popMatrix();
		
	
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		
		if (facing.equals(EnumFacing.NORTH)) {
			GlStateManager.translate(0.22F, 0.6F, 0.28F);
		} else if (facing.equals(EnumFacing.SOUTH)) {
			GlStateManager.translate(0.22F, 0.6F, 0.72F);
		} else if (facing.equals(EnumFacing.WEST)) {
			GlStateManager.translate(0.28F, 0.6F, 0.22F);
		} else {
			GlStateManager.translate(0.72F, 0.6F, 0.78F);
		}
		
		if (tileentity.canProcess() && tileentity.cook_time > 0 && tileentity.hasStored() && tileentity.getMode() == 0) {
			GlStateManager.rotate(-Minecraft.getSystemTime() / 720F * 120, 0F, 1F, 0F);
		}
		
		this.model.renderRightBrush(0.0625F);
		GlStateManager.popMatrix();
	
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		
		if (facing.equals(EnumFacing.NORTH)) {
			GlStateManager.translate(0.78F, 0.6F, 0.28F);
		} else if (facing.equals(EnumFacing.SOUTH)) {
			GlStateManager.translate(0.78F, 0.6F, 0.72F);
		} else if (facing.equals(EnumFacing.WEST)) {
			GlStateManager.translate(0.28F, 0.6F, 0.78F);
		} else {
			GlStateManager.translate(0.72F, 0.6F, 0.22F);
		}
		
		if (tileentity.canProcess() && tileentity.cook_time > 0 && tileentity.hasStored() && tileentity.getMode() == 0) {
			GlStateManager.rotate(Minecraft.getSystemTime() / 720F * 120, 0F, 1F, 0F);
		}
		
		this.model.renderLeftBrush(0.0625F);
		GlStateManager.popMatrix();
		
		if (tileentity.cook_time > (tileentity.getCookSpeed() - 10) && tileentity.getMode() == 0) {
			GlStateManager.pushMatrix();
			
			ItemStack result = OreProcessorCleaningRecipes.getInstance().getCleaningResult(tileentity.getStackInSlot(0));
			
			GlStateManager.translate(x, y - 0.35, z);
			
			if (facing.equals(EnumFacing.NORTH)) {
				GlStateManager.translate(0.5F, 1F, 0.25F);
			} else if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.translate(0.5F, 1F, 0.75F);
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.translate(0.25F, 1F, 0.5F);
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else {
				GlStateManager.translate(0.75F, 1F, 0.5F);
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			}
			
			if (tileentity.getStackInSlot(0).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.6F, 0.6F, 0.6F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.35F, 0.35F, 0.35F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(result, ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		} else if (tileentity.cook_time > (tileentity.getCookSpeed() - 10) && tileentity.getMode() == 1 && !tileentity.getStackInSlot(0).isEmpty()) {
			GlStateManager.pushMatrix();
			
			ItemStack result = OreProcessorRefiningRecipes.getInstance().getRefiningResult(tileentity.getStackInSlot(0));
			
			GlStateManager.translate(x, y - 0.35, z);
			
			if (facing.equals(EnumFacing.NORTH)) {
				GlStateManager.translate(0.5F, 1F, 0.25F);
			} else if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.translate(0.5F, 1F, 0.75F);
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.translate(0.25F, 1F, 0.5F);
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else {
				GlStateManager.translate(0.75F, 1F, 0.5F);
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			}
			
			if (tileentity.getStackInSlot(0).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.6F, 0.6F, 0.6F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.35F, 0.35F, 0.35F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(result, ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		} else if (!(tileentity.getStackInSlot(0).isEmpty())) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y - 0.35F, z);
			
			if (facing.equals(EnumFacing.NORTH)) {
				GlStateManager.translate(0.5F, 1F, 0.25F);
			} else if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.translate(0.5F, 1F, 0.75F);
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.translate(0.25F, 1F, 0.5F);
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else {
				GlStateManager.translate(0.75F, 1F, 0.5F);
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			}
			
			if (tileentity.getStackInSlot(0).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.6F, 0.6F, 0.6F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.35F, 0.35F, 0.35F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(0), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
		
		/**
		if (tileentity.cook_time > (tileentity.getCookSpeed() - 10)) {
			
			GlStateManager.pushMatrix();
			
			ItemStack result = CompressorRecipes.getInstance().getCompressingResult(tileentity.getStackInSlot(0));
			
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else  if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
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
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
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
		}*/
	}
}
