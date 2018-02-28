package com.zeher.orecraft.storage.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.storage.container.ContainerEnergizedCapacitor;
import com.zeher.orecraft.storage.tileentity.TileEntityEnergizedCapacitor;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

@SideOnly(Side.CLIENT)
public class GuiEnergizedCapacitor extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("orecraft:textures/gui/storage/capacitor/gui_energized_capacitor.png");
	private TileEntityEnergizedCapacitor capacitorInventory;

	public GuiEnergizedCapacitor(InventoryPlayer inventoryPlayer, TileEntityEnergizedCapacitor tileEntitycapacitor) {
		super(new ContainerEnergizedCapacitor(inventoryPlayer, tileEntitycapacitor));
		this.capacitorInventory = tileEntitycapacitor;
		this.xSize = 195;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.capacitorInventory.hasCustomName() ? this.capacitorInventory.getName()
				: I18n.format(this.capacitorInventory.getName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		if (mouseX > k + 29 && mouseX < k + 47) {
			if (mouseY > l + 6 && mouseY < l + 68) {
				String[] desc = { "Power:" + this.capacitorInventory.stored };
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
		if (this.capacitorInventory.stored > 0) {
			int i1 = this.capacitorInventory.getPowerReaminingScaled(62);
	        drawTexturedModalRect(k + 29, l + 68 - i1, 195, 61 - i1, 18, i1);
		}
	}
}
