package com.zeher.orecraft.machine.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.*;
import scala.actors.threadpool.Arrays;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.machine.container.ContainerEnergizedCompressor;
import com.zeher.orecraft.machine.tileentity.TileEntityEnergizedCompressor;

@SideOnly(Side.CLIENT)
public class GuiEnergizedCompressor extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("orecraft:textures/gui/machine/energized/compressor/gui_compressor.png");
	private TileEntityEnergizedCompressor inventory;

	public GuiEnergizedCompressor(InventoryPlayer par1InventoryPlayer,
			TileEntityEnergizedCompressor par2TileEntityCompressor) {
		super(new ContainerEnergizedCompressor(par1InventoryPlayer, par2TileEntityCompressor));
		this.inventory = par2TileEntityCompressor;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.inventory.hasCustomName() ? this.inventory.getName() : I18n.format(this.inventory.getName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 3, 4, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		// this.fontRendererObj.drawString("Speed:" + this.inventory.cookSpeed,
		// 100, 62, 6);
		// this.fontRendererObj.drawString("Power:" + this.inventory._power,
		// 100, 73, 6);

		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		if (mouseX > k + 29 && mouseX < k + 47) {
			if (mouseY > l + 6 && mouseY < l + 68) {
				String[] desc = { "Power:" + this.inventory._power };
				List temp = Arrays.asList(desc);
				this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
			}
		}
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		if (this.inventory.hasPower()) {
			int i1 = this.inventory.getPowerRemainingScaled(61);
			drawTexturedModalRect(k + 29, l + 68 - i1, 176, 92 - i1, 18, i1);
		}
		int i1 = this.inventory.getCookProgressScaled(24);
		drawTexturedModalRect(k + 83, l + 33, 176, 14, i1 + 1, 17);
	}
}
