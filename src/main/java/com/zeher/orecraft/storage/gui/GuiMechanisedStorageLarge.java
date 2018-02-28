package com.zeher.orecraft.storage.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.*;
import scala.actors.threadpool.Arrays;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.storage.container.ContainerMechanisedStorageLarge;
import com.zeher.orecraft.storage.tileentity.TileEntityMechanisedStorageLarge;

@SideOnly(Side.CLIENT)
public class GuiMechanisedStorageLarge extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("orecraft:textures/gui/storage/mechanised/gui_mechanised_storage_large.png");
	private TileEntityMechanisedStorageLarge capacitorInventory;

	public GuiMechanisedStorageLarge(InventoryPlayer inventoryPlayer, TileEntityMechanisedStorageLarge tileEntitycapacitor) {
		super(new ContainerMechanisedStorageLarge(inventoryPlayer, tileEntitycapacitor));
		this.capacitorInventory = tileEntitycapacitor;
		this.xSize = 248;
		this.ySize = 238;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.capacitorInventory.hasCustomName() ? this.capacitorInventory.getName() : I18n.format(this.capacitorInventory.getName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
	
}
