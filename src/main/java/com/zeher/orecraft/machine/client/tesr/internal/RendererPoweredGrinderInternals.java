package com.zeher.orecraft.machine.client.tesr.internal;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.client.tesr.model.internal.ModelBlockGrinderInternals;
import com.zeher.orecraft.machine.core.block.BlockPoweredGrinder;
import com.zeher.orecraft.machine.core.recipe.GrinderRecipes;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredGrinder;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RendererPoweredGrinderInternals extends TileEntitySpecialRenderer<TileEntityPoweredGrinder>{

	@SideOnly(Side.CLIENT)
	private ModelBlockGrinderInternals model;
	
	public RendererPoweredGrinderInternals() {
		this.model = new ModelBlockGrinderInternals();
	}
	
	@Override
	public void render(TileEntityPoweredGrinder tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		if (tileentity == null) {
			return;
		}
		
		IBlockState block = tileentity.getWorld().getBlockState(tileentity.getPos());
		BlockPoweredGrinder block_ = (BlockPoweredGrinder) block.getBlock();
		EnumFacing enu = block.getValue(block_.FACING);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
		GlStateManager.rotate(180F, 1.0F, 0.0F, 0.0F);
		this.bindTexture(OrecraftReference.TESR.RESOURCE.TESR_GRINDER_INTERNALS);
		
		GL11.glColor4f(1, 1, 1, 1);
		
		if (enu.equals(EnumFacing.SOUTH)) {
			GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
		} else if (enu.equals(EnumFacing.WEST)) {
			GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
		} else if (enu.equals(EnumFacing.EAST)) {
			GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
		}
		
		GlStateManager.popMatrix();
		
		
		GlStateManager.pushMatrix();
		
		if (enu.equals(EnumFacing.NORTH)) {
			GlStateManager.translate(x + 0.785, y + 0.62, z + 0.5);
		}
		
		if (enu.equals(EnumFacing.SOUTH)) {
			GlStateManager.translate(x + 0.785, y + 0.62, z + 0.5);
			GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		}
		
		if (enu.equals(EnumFacing.WEST)) {
			GlStateManager.translate(x + 0.5, y + 0.62, z + 0.215);
			GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		}
		
		if (enu.equals(EnumFacing.EAST)) {
			GlStateManager.translate(x + 0.5, y + 0.62, z + 0.785);
			GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		}
		
		if (tileentity.canGrind() && tileentity.getCookTime(0) > 0 && tileentity.hasStored()) {
			GlStateManager.rotate(Minecraft.getSystemTime() / 720F * 120, 0F, 0F, 1F);
		}
		
		this.model.renderLeftTeeth(0.0625F);
		
		GlStateManager.popMatrix();
		
		
		GlStateManager.pushMatrix();
		
		if (enu.equals(EnumFacing.NORTH)) {
			GlStateManager.translate(x + 0.215, y + 0.62, z + 0.5);
		} else  if (enu.equals(EnumFacing.SOUTH)) {
			GlStateManager.translate(x + 0.215, y + 0.62, z + 0.5);
			GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);

		} else if (enu.equals(EnumFacing.WEST)) {
			GlStateManager.translate(x + 0.5, y + 0.62, z + 0.785);
			GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		} else if (enu.equals(EnumFacing.EAST)) {
			GlStateManager.translate(x + 0.5, y + 0.62, z + 0.215);
			GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		}
		
		GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		
		
		if (tileentity.canGrind() && tileentity.getCookTime(0) > 0 && tileentity.hasStored()) {
			GlStateManager.rotate(Minecraft.getSystemTime() / 720F * 120, 0F, 0F, 1F);
		}
		
		this.model.renderRightTeeth(0.0625F);
		
		GlStateManager.popMatrix();
		
		
		GlStateManager.pushMatrix();

		if (enu.equals(EnumFacing.NORTH)) {
			GlStateManager.translate(x + 0.5, y + 0.62, z + 0.785);
			GlStateManager.rotate(180, 0.0F, 0.0F, 1F);

		} else if (enu.equals(EnumFacing.SOUTH)) {
			GlStateManager.translate(x + 0.5, y + 0.62, z + 0.215);
		} else if (enu.equals(EnumFacing.WEST)) {
			GlStateManager.translate(x + 0.785, y + 0.62, z + 0.5);
			GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
		} else if (enu.equals(EnumFacing.EAST)) {
			GlStateManager.translate(x + 0.215, y + 0.62, z + 0.5);
			GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		}
		
		GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);

		
		if (tileentity.canGrind() && tileentity.getCookTime(0) > 0 && tileentity.hasStored()) {
			GlStateManager.rotate(Minecraft.getSystemTime() / 720F * 120, 0F, 0F, 1F);
		}
		
		this.model.renderLeftTeeth(0.0625F);
		
		GlStateManager.popMatrix();
		
		if (tileentity.getCookTime(0) > (tileentity.getCookSpeed() - 10) && !tileentity.getStackInSlot(0).isEmpty()) {
			
			ItemStack result = GrinderRecipes.getInstance().getGrindingResult(tileentity.getStackInSlot(0));
			
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 0.5, y + 0.65, z + 0.5);
			
			if (enu.equals(EnumFacing.SOUTH)) {
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			} else if (enu.equals(EnumFacing.WEST)) {
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
			}
			
			if (enu.equals(EnumFacing.WEST)) {
				GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			}
			
			if (enu.equals(EnumFacing.EAST)) {
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
