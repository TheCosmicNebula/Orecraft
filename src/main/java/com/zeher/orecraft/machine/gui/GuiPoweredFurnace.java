package com.zeher.orecraft.machine.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.*;
import scala.actors.threadpool.Arrays;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.machine.container.ContainerPoweredFurnace;
import com.zeher.orecraft.machine.tileentity.TileEntityPoweredFurnace;

@SideOnly(Side.CLIENT)
public class GuiPoweredFurnace extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("orecraft:textures/gui/machine/powered/furnace/gui_furnace.png");
	private TileEntityPoweredFurnace inventory;

	public GuiPoweredFurnace(InventoryPlayer par1InventoryPlayer,
			TileEntityPoweredFurnace par2TileEntityFurnace) {
		super(new ContainerPoweredFurnace(par1InventoryPlayer, par2TileEntityFurnace));
		this.inventory = par2TileEntityFurnace;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.inventory.hasCustomName() ? this.inventory.getName() : I18n.format(this.inventory.getName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 3, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		// this.fontRendererObj.drawString("Power:" + this.inventory._power, 60,
		// 73, 6);

		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;

		if (mouseX > k + 29 && mouseX < k + 47) {
			if (mouseY > l + 6 && mouseY < l + 68) {
				String[] desc = { "Power:" + this.inventory.stored };
				List temp = Arrays.asList(desc);
				this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
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
			drawTexturedModalRect(k + 29, l + 68 - i1, 176, 92 - i1, 18, i1);
		}
		int i1 = this.inventory.getCookProgressScaled(24);
		drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 17);
	}
}
