package com.zeher.orecraft.transfer.client.tesr;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.transfer.client.tesr.model.ModelBlockEnergyPipe;
import com.zeher.orecraft.transfer.core.tileentity.TileEntityItemPipeSolid;
import com.zeher.orecraft.transfer.core.tileentity.TileEntityPoweredEnergyPipe;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RendererPoweredEnergyPipe extends TileEntitySpecialRenderer<TileEntityPoweredEnergyPipe> {

	@SideOnly(Side.CLIENT)
	private ModelBlockEnergyPipe model;

	@SideOnly(Side.CLIENT)
	public RendererPoweredEnergyPipe() {
		this.model = new ModelBlockEnergyPipe();
	}

	private static final ResourceLocation texture = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "transfer/energypipe/powered.png");
	
	@Override
	public void render(TileEntityPoweredEnergyPipe tileentity, double x, double y, double z, float partialTicks, int destroyStage, float a) {
		TileEntityPoweredEnergyPipe cable = (TileEntityPoweredEnergyPipe) tileentity;

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
