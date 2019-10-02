package com.zeher.orecraft.transfer.client.tesr;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.transfer.client.tesr.model.ModelBlockFluidPipe;
import com.zeher.orecraft.transfer.core.tileentity.TileEntityFluidPipeClear;
import com.zeher.orecraft.transfer.core.tileentity.TileEntityFluidPipeSolid;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RendererFluidPipeSolid extends TileEntitySpecialRenderer<TileEntityFluidPipeSolid> {

	@SideOnly(Side.CLIENT)
	private ModelBlockFluidPipe model;

	@SideOnly(Side.CLIENT)
	public RendererFluidPipeSolid() {
		this.model = new ModelBlockFluidPipe();
	}

	private static final ResourceLocation texture = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "transfer/fluidpipe/fluidpipe_solid.png");
	
	@Override
	public void render(TileEntityFluidPipeSolid tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		TileEntityFluidPipeSolid cable = (TileEntityFluidPipeSolid) tileentity;

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		
		this.bindTexture(texture);
		
		GL11.glPushMatrix();
		GL11.glDisable(3008);
		
		this.model.render(cable, 0.0625F);

		GL11.glEnable(3008);

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
