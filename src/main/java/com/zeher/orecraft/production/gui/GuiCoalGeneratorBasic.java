package com.zeher.orecraft.production.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.*;
import scala.actors.threadpool.Arrays;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.production.container.ContainerCoalGeneratorBasic;
import com.zeher.orecraft.production.tileentity.TileEntityCoalGeneratorBasic;

@SideOnly(Side.CLIENT)
public class GuiCoalGeneratorBasic extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("orecraft:textures/gui/production/generator/coal/gui_coal.png");
	private TileEntityCoalGeneratorBasic inventory;

	public GuiCoalGeneratorBasic(InventoryPlayer par1InventoryPlayer,
			TileEntityCoalGeneratorBasic par2TileEntitygenerator) {
		super(new ContainerCoalGeneratorBasic(par1InventoryPlayer, par2TileEntitygenerator));
		this.inventory = par2TileEntitygenerator;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.inventory.hasCustomName() ? this.inventory.getName() : I18n.format(this.inventory.getName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 3, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		this.fontRendererObj.drawString("Power:" + this.inventory.generation_time + "/" + this.inventory.generation_rate, 60, 73, 6);
		
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		if (mouseX > k + 29 && mouseX < k + 47) {
			if (mouseY > l + 6 && mouseY < l + 68) {
				if(this.inventory.isBurning()){
					String[] desc = { "Power: " + this.inventory.stored , "Producing: " + this.inventory.generation_rate };
					List temp = Arrays.asList(desc);
					this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
				} else {
					String[] desc = { "Power: " + this.inventory.stored};
					List temp = Arrays.asList(desc);
					this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
				}
			}
		}
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		if (this.inventory.hasStored()) {
			int i1 = this.inventory.getPowerRemainingScaled(62);
			drawTexturedModalRect(k + 29, l + 68 - i1, 176, 61 - i1, 18, i1);
		}
		int i1 = this.inventory.getCookProgressScaled(20);
		drawTexturedModalRect(k + 57, l + 29, 176, 62, i1 + 1, 20);
	}
}
