package com.zeher.orecraft.production.client.tesr;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.*;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.production.client.tesr.model.ModelBlockPeltierGenerator;
import com.zeher.orecraft.production.client.tesr.model.ModelBlockSolidFuelGenerator;
import com.zeher.orecraft.production.core.block.BlockPoweredSolarPanel;
import com.zeher.orecraft.production.core.block.BlockPoweredSolidFuelGenerator;
import com.zeher.orecraft.production.core.tileentity.TileEntityPoweredHeatedFluidGenerator;
import com.zeher.orecraft.production.core.tileentity.TileEntityPoweredPeltierGenerator;
import com.zeher.orecraft.production.core.tileentity.TileEntityPoweredSolidFuelGenerator;
import com.zeher.orecraft.transfer.core.tileentity.TileEntityPoweredEnergyPipe;

@SideOnly(Side.CLIENT)
public class RendererPoweredSolidFuelGenerator extends TileEntitySpecialRenderer<TileEntityPoweredSolidFuelGenerator> {

	@SideOnly(Side.CLIENT)
	private ModelBlockSolidFuelGenerator model;

	@SideOnly(Side.CLIENT)
	public RendererPoweredSolidFuelGenerator() {
		this.model = new ModelBlockSolidFuelGenerator();
	}

	private static final ResourceLocation texture_location = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "production/powered/generator/solidfuel/solid_fuel.png");
	
	@Override
	public void render(TileEntityPoweredSolidFuelGenerator tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		IBlockState state = tileentity.getWorld().getBlockState(tileentity.getPos());
		BlockPoweredSolidFuelGenerator block = (BlockPoweredSolidFuelGenerator) state.getBlock();
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
		
		GlStateManager.pushMatrix();
		if (facing.equals(EnumFacing.NORTH)) {
			GlStateManager.translate(x + 0.69, y + 0.31, z + 0.2);
		} else if (facing.equals(EnumFacing.SOUTH)) {
			GlStateManager.translate(x + 0.31, y + 0.31, z + 0.2);
			GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		} else if (facing.equals(EnumFacing.WEST)) {
			GlStateManager.translate(x + 0.2, y + 0.31, z + 0.31);
			GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		} else if (facing.equals(EnumFacing.EAST)) {
			GlStateManager.translate(x + 0.8, y + 0.31, z + 0.69);
			GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		}
		if (tileentity.canProduce() && tileentity.getStored() < tileentity.getCapacity()) {
			GlStateManager.rotate(Minecraft.getSystemTime() / 720F * 120, 0.0F, 0.0F, 1.0F);
		}
		
		this.model.renderMotor(0.0625F);
		
		GlStateManager.popMatrix();
	}

}
