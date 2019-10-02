package com.zeher.orecraft.storage.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.*;
import scala.actors.threadpool.Arrays;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.storage.core.container.ContainerMechanisedStorageSmall;
import com.zeher.orecraft.storage.core.tileentity.TileEntityMechanisedStorageSmall;

@SideOnly(Side.CLIENT)
public class GuiMechanisedStorageSmall extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "storage/mechanised/gui_mechanised_storage_small.png");
	private TileEntityMechanisedStorageSmall capacitorInventory;

	public GuiMechanisedStorageSmall(InventoryPlayer inventoryPlayer, TileEntityMechanisedStorageSmall tileEntitycapacitor) {
		super(new ContainerMechanisedStorageSmall(inventoryPlayer, tileEntitycapacitor));
		this.capacitorInventory = tileEntitycapacitor;
		this.xSize = 194;
		this.ySize = 202;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.capacitorInventory.hasCustomName() ? this.capacitorInventory.getName() : I18n.format(this.capacitorInventory.getName());
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
