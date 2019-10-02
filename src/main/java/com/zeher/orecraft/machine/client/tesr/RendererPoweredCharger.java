package com.zeher.orecraft.machine.client.tesr;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.client.tesr.model.ModelBlockCharger;
import com.zeher.orecraft.machine.core.block.BlockPoweredCharger;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredCharger;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RendererPoweredCharger extends TileEntitySpecialRenderer<TileEntityPoweredCharger> {

	@SideOnly(Side.CLIENT)
	private ModelBlockCharger model;
	
	public RendererPoweredCharger() {
		this.model = new ModelBlockCharger();
	}
	
	private static final ResourceLocation texture_location = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "machine/powered/charger/charger.png");
	
	@Override
	public void render(TileEntityPoweredCharger tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		if (tileentity == null) {
			return;
		}
		
		IBlockState state = tileentity.getWorld().getBlockState(tileentity.getPos());
		BlockPoweredCharger block = (BlockPoweredCharger) state.getBlock();
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
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GlStateManager.popMatrix();
		
		//Item Rendering
		
		if (!tileentity.getStackInSlot(1).isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			if (facing.equals(EnumFacing.NORTH)) {
				GlStateManager.translate(0.3125F, 0.0F, 0.3125F);
			} else if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.translate(-0.3125F, 0.0F, -0.3125F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.translate(-0.3125F, 0.0F, 0.3125F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.translate(0.3125F, 0.0F, -0.3125F);
			}
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			} 
			
			if (tileentity.getStackInSlot(1).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.4F, 0.4F, 0.4F);
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(1), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
		
		if (!tileentity.getStackInSlot(2).isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			if (facing.equals(EnumFacing.NORTH)) {
				GlStateManager.translate(0.0F, 0.0F, 0.3125F);
			} else if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.translate(0.0F, 0.0F, -0.3125F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.translate(-0.3125F, 0.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.translate(0.3125F, 0.0F, 0.0F);
			}
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			} 
			
			if (tileentity.getStackInSlot(2).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.4F, 0.4F, 0.4F);
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
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
		
		if (!tileentity.getStackInSlot(3).isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			if (facing.equals(EnumFacing.NORTH)) {
				GlStateManager.translate(-0.3125F, 0.0F, 0.3125F);
			} else if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.translate(0.3125F, 0.0F, -0.3125F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.translate(-0.3125F, 0.0F, -0.3125F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.translate(0.3125F, 0.0F, 0.3125F);
			}
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			} 
			
			if (tileentity.getStackInSlot(3).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.4F, 0.4F, 0.4F);
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(3), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
		
		if (!tileentity.getStackInSlot(4).isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			if (facing.equals(EnumFacing.NORTH)) {
				GlStateManager.translate(0.3125F, 0.0F, 0.0F);
			} else if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.translate(-0.3125F, 0.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.translate(0.0F, 0.0F, 0.3125F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.translate(0.0F, 0.0F, -0.3125F);
			}
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			} 
			
			if (tileentity.getStackInSlot(4).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.4F, 0.4F, 0.4F);
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(4), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
		
		if (!tileentity.getStackInSlot(5).isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			if (facing.equals(EnumFacing.NORTH)) {
				GlStateManager.translate(0.0F, 0.0F, 0.0F);
			} else if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.translate(0.0F, 0.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.translate(0.0F, 0.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.translate(0.0F, 0.0F, 0.0F);
			}
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			} 
			
			if (tileentity.getStackInSlot(5).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.4F, 0.4F, 0.4F);
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(5), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
		
		if (!tileentity.getStackInSlot(6).isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			if (facing.equals(EnumFacing.NORTH)) {
				GlStateManager.translate(-0.3125F, 0.0F, 0.0F);
			} else if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.translate(0.3125F, 0.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.translate(0.0F, 0.0F, -0.3125F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.translate(0.0F, 0.0F, 0.3125F);
			}
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			} 
			
			if (tileentity.getStackInSlot(6).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.4F, 0.4F, 0.4F);
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(6), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
		
		if (!tileentity.getStackInSlot(7).isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			if (facing.equals(EnumFacing.NORTH)) {
				GlStateManager.translate(0.3125F, 0.0F, -0.3125F);
			} else if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.translate(-0.3125F, 0.0F, 0.3125F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.translate(0.3125F, 0.0F, 0.3125F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.translate(-0.3125F, 0.0F, -0.3125F);
			}
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			} 
			
			if (tileentity.getStackInSlot(7).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.4F, 0.4F, 0.4F);
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(7), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
		
		if (!tileentity.getStackInSlot(8).isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			if (facing.equals(EnumFacing.NORTH)) {
				GlStateManager.translate(0.0F, 0.0F, -0.3125F);
			} else if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.translate(0.0F, 0.0F, 0.3125F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.translate(0.3125F, 0.0F, 0.F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.translate(-0.3125F, 0.0F, 0.0F);
			}
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			} 
			
			if (tileentity.getStackInSlot(8).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.4F, 0.4F, 0.4F);
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(8), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
		
		if (!tileentity.getStackInSlot(9).isEmpty()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			if (facing.equals(EnumFacing.NORTH)) {
				GlStateManager.translate(-0.3125F, 0.0F, -0.3125F);
			} else if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.translate(0.3125F, 0.0F, 0.3125F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.translate(0.3125F, 0.0F, -0.3125F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.translate(-0.3125F, 0.0F, 0.3125F);
			}
			
			if (facing.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			} else if (facing.equals(EnumFacing.EAST)) {
				GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			} 
			
			if (tileentity.getStackInSlot(9).getItem() instanceof ItemBlock) {
				GlStateManager.translate(0.0F, -0.05F, 0.0F);
				GlStateManager.scale(0.4F, 0.4F, 0.4F);
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
			} else {
				GlStateManager.translate(0.0F, -0.2F, 0.0F);
				GlStateManager.rotate(90, 1.0F, 0F, 0F);
				GlStateManager.scale(0.2F, 0.2F, 0.2F);
			}
			
			GlStateManager.pushAttrib();
			
			RenderHelper.disableStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItem(tileentity.getStackInSlot(9), ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.enableStandardItemLighting();
	
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();
		}
	}
}
