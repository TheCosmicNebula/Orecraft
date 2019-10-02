package com.zeher.orecraft.production.client.tesr;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.production.client.tesr.model.ModelBlockSolarPanel;
import com.zeher.orecraft.production.core.block.BlockPoweredSolarPanel;
import com.zeher.orecraft.production.core.tileentity.TileEntityPoweredSolarPanel;
import com.zeher.zeherlib.api.connection.EnumSide;
import com.zeher.zeherlib.transfer.IEnergyPipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RendererPoweredSolarPanel extends TileEntitySpecialRenderer<TileEntityPoweredSolarPanel> {

	@SideOnly(Side.CLIENT)
	private ModelBlockSolarPanel model;
	
	public RendererPoweredSolarPanel() {
		this.model = new ModelBlockSolarPanel();
	}
	
	private static final ResourceLocation texture_location = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "production/powered/solarpanel/solar_panel.png");
	
	@Override
	public void render(TileEntityPoweredSolarPanel tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		if (tileentity == null) {
			return;
		}
		
		IBlockState state = tileentity.getWorld().getBlockState(tileentity.getPos());
		BlockPoweredSolarPanel block = (BlockPoweredSolarPanel) state.getBlock();
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
		
		for (EnumFacing c : EnumFacing.VALUES) {
			World world = tileentity.getWorld();
			BlockPos pos = tileentity.getPos();
			TileEntity tile_in = world.getTileEntity(pos.offset(c));
			
			if (tile_in instanceof IEnergyPipe && !((IEnergyPipe)tile_in).getSide(c.getOpposite()).equals(EnumSide.NONE)) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(x + 0.5F, y + 1.5F, z + 0.5F);
				GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
				
				this.bindTexture(texture_location);
				
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glColor4f(1, 1, 1, 1);
				
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
				
				this.model.renderConnector(0.0625F, c);

				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_CULL_FACE);
				
				GlStateManager.popMatrix();
			}
			
			if (tile_in instanceof TileEntityPoweredSolarPanel) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(x + 0.5F, y + 1.5F, z + 0.5F);
				GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
				
				this.bindTexture(texture_location);
				
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glColor4f(1, 1, 1, 1);
				
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
				
				this.model.renderConnected(0.0625F, true, c);
				
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_CULL_FACE);
				
				GlStateManager.popMatrix();
			} else {
				GlStateManager.pushMatrix();
				GlStateManager.translate(x + 0.5F, y + 1.5F, z + 0.5F);
				GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
				
				this.bindTexture(texture_location);
				
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glColor4f(1, 1, 1, 1);
				
				GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
				this.model.renderConnected(0.0625F, false, c);
				
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_CULL_FACE);
				
				GlStateManager.popMatrix();
			}
		}
	}
}
