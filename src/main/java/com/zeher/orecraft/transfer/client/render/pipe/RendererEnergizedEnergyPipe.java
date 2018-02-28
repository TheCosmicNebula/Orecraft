package com.zeher.orecraft.transfer.client.render.pipe;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.client.model.ModelBlockEnergyPipe;
import com.zeher.orecraft.transfer.tileentity.pipe.TileEntityEnergizedEnergyPipe;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RendererEnergizedEnergyPipe
  extends TileEntitySpecialRenderer
{
  
	@SideOnly(Side.CLIENT)
  private ModelBlockEnergyPipe model;
	
  @SideOnly(Side.CLIENT)
  public RendererEnergizedEnergyPipe()
  {
		this.model = new ModelBlockEnergyPipe();
  }
  
  private static final ResourceLocation texture = new ResourceLocation("orecraft:textures/render/transfer/energized/energypipe.png");
  
  public void renderAModel(TileEntity tileentity, double x, double y, double z)
  {
    TileEntityEnergizedEnergyPipe cable = (TileEntityEnergizedEnergyPipe)tileentity;
    
    GL11.glPushMatrix();
    GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
    bindTexture(texture);
    GL11.glPushMatrix();
    GL11.glDisable(3008);
    this.model.render(cable, 0.0625F);
    
    GL11.glEnable(3008);
    
    GL11.glPopMatrix();
    GL11.glPopMatrix();
  }
  
  public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks, int destroyStage)
  {
    renderAModel(tileentity, x, y, z);
  }

}
