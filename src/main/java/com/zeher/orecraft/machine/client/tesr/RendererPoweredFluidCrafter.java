package com.zeher.orecraft.machine.client.tesr;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.client.tesr.model.ModelBlockFluidCrafter;
import com.zeher.orecraft.machine.client.tesr.model.ModelBlockFluidInside;
import com.zeher.orecraft.machine.core.block.BlockPoweredFluidCrafter;
import com.zeher.orecraft.machine.core.block.BlockPoweredOreProcessor;
import com.zeher.orecraft.machine.core.recipe.FluidProcessorCraftRecipes;
import com.zeher.orecraft.machine.core.recipe.FluidProcessorMeltRecipes;
import com.zeher.orecraft.machine.core.recipe.OreProcessorCleaningRecipes;
import com.zeher.orecraft.machine.core.recipe.OreProcessorRefiningRecipes;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredCompressor;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredFluidCrafter;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RendererPoweredFluidCrafter extends TileEntitySpecialRenderer<TileEntityPoweredFluidCrafter> {

	@SideOnly(Side.CLIENT)
	private ModelBlockFluidCrafter model;

	@SideOnly(Side.CLIENT)
	public RendererPoweredFluidCrafter() {
		this.model = new ModelBlockFluidCrafter();
	}

	private static final ResourceLocation texture_location = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "machine/powered/fluidcrafter/fluid_crafter.png");
	
	@Override
	public void render(TileEntityPoweredFluidCrafter tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {

		IBlockState state = tileentity.getWorld().getBlockState(tileentity.getPos());
		BlockPoweredFluidCrafter block = (BlockPoweredFluidCrafter) state.getBlock();
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
			this.model.renderItemStand(0.0625F, true);
		} else {
			this.model.renderItemStand(0.0625F, false);
		}
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GlStateManager.popMatrix();
		
		if (tileentity.cook_time > (tileentity.getCookSpeed() - 10) && tileentity.getMode() == 1 && !tileentity.getStackInSlot(0).isEmpty()) {
			GlStateManager.pushMatrix();
			
			ItemStack result = FluidProcessorCraftRecipes.getInstance().getRecipeResult(tileentity.getStackInSlot(0));
			
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
	}

}
