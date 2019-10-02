package com.zeher.orecraft.production.client.gui;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.production.container.ContainerPoweredSolarPanel;
import com.zeher.orecraft.production.core.tileentity.TileEntityPoweredSolarPanel;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.client.gui.util.ModGuiUtil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPoweredSolarPanel extends GuiContainer {
	
	private TileEntityPoweredSolarPanel INVENTORY;

	public GuiPoweredSolarPanel(InventoryPlayer par1InventoryPlayer, TileEntityPoweredSolarPanel par2TileEntityCharger) {
		super(new ContainerPoweredSolarPanel(par1InventoryPlayer, par2TileEntityCharger));
		this.INVENTORY = par2TileEntityCharger;
		this.xSize = 176;
		this.ySize = 177;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.FONT.DRAW.drawCustomString(this.INVENTORY, this.fontRenderer, screen_coords, 51, 4);
		ModGuiUtil.FONT.DRAW.drawInventoryString(this.fontRenderer, screen_coords, 7, 83);
		
		ModGuiUtil.DRAW.drawHoveringTextPowerProducer(this, screen_coords, 76, 17, mouse_x, mouse_y, this.INVENTORY.stored, this.INVENTORY.generation_rate, this.INVENTORY.canProduce());
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partial_ticks, int mouse_x, int mouse_y) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.DRAW.drawBackground(this, screen_coords, OrecraftReference.GUI.RESOURCE.GUI_PRODUCER_SOLARPANEL);
		ModGuiUtil.DRAW.drawStaticElementToggled(this, screen_coords, 104, 38, 176, 0, 18, 18, this.INVENTORY.canProduce());
		ModGuiUtil.DRAW.drawPowerBar(this, screen_coords, 76, 15, this.INVENTORY.getStoredRemainingScaled(Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR[2]), Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR, this.INVENTORY.hasStored());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}