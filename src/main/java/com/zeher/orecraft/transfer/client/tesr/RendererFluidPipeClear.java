package com.zeher.orecraft.transfer.client.tesr;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.transfer.client.tesr.model.ModelBlockFluidPipeClear;
import com.zeher.orecraft.transfer.core.tileentity.TileEntityEnergizedEnergyPipe;
import com.zeher.orecraft.transfer.core.tileentity.TileEntityFluidPipeClear;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RendererFluidPipeClear extends TileEntitySpecialRenderer<TileEntityFluidPipeClear> {

	@SideOnly(Side.CLIENT)
	private ModelBlockFluidPipeClear model;

	@SideOnly(Side.CLIENT)
	public RendererFluidPipeClear() {
		this.model = new ModelBlockFluidPipeClear();
	}

	private static final ResourceLocation texture = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "transfer/fluidpipe/fluidpipe_clear.png");
	
	@Override
	public void render(TileEntityFluidPipeClear tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		TileEntityFluidPipeClear cable = (TileEntityFluidPipeClear) tileentity;

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		
		this.bindTexture(texture);
		
		GL11.glPushMatrix();

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1, 1, 1, 1);
		
		this.model.render(cable, 0.0625F);
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
