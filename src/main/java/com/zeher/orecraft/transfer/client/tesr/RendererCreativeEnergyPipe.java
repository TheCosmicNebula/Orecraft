package com.zeher.orecraft.transfer.client.tesr;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.transfer.client.tesr.model.ModelBlockEnergyPipe;
import com.zeher.orecraft.transfer.core.tileentity.TileEntityCreativeEnergyPipe;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RendererCreativeEnergyPipe extends TileEntitySpecialRenderer<TileEntityCreativeEnergyPipe> {

	@SideOnly(Side.CLIENT)
	private ModelBlockEnergyPipe model;

	@SideOnly(Side.CLIENT)
	public RendererCreativeEnergyPipe() {
		this.model = new ModelBlockEnergyPipe();
	}

	private static final ResourceLocation texture = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "transfer/energypipe/creative.png");
	
	@Override
	public void render(TileEntityCreativeEnergyPipe tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		TileEntityCreativeEnergyPipe cable = (TileEntityCreativeEnergyPipe) tileentity;

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		bindTexture(texture);
		GL11.glPushMatrix();
		GL11.glDisable(3008);
		this.model.render(cable, 0.0625F);

		GL11.glEnable(3008);

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
