package com.zeher.orecraft.production.client.gui;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.production.container.ContainerPoweredSolidFuelGenerator;
import com.zeher.orecraft.production.core.tileentity.TileEntityPoweredSolidFuelGenerator;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.client.gui.util.ModGuiUtil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPoweredSolidFuelGenerator extends GuiContainer {
	
	private TileEntityPoweredSolidFuelGenerator INVENTORY;

	public GuiPoweredSolidFuelGenerator(InventoryPlayer par1InventoryPlayer, TileEntityPoweredSolidFuelGenerator par2TileEntitygenerator) {
		super(new ContainerPoweredSolidFuelGenerator(par1InventoryPlayer, par2TileEntitygenerator));
		this.INVENTORY = par2TileEntitygenerator;
		this.xSize = 176;
		this.ySize = 177;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.FONT.DRAW.drawCustomString(this.INVENTORY, this.fontRenderer, screen_coords, 47, 4);
		ModGuiUtil.FONT.DRAW.drawInventoryString(this.fontRenderer, screen_coords, 7, 83);
		
		ModGuiUtil.DRAW.drawHoveringTextPowerProducer(this, screen_coords, 72, 16, mouse_x, mouse_y, this.INVENTORY.stored, this.INVENTORY.generation_rate, (this.INVENTORY.cook_time > 0));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.DRAW.drawBackground(this, screen_coords, OrecraftReference.GUI.RESOURCE.GUI_PRODUCER_SOLIDFUEL);
		ModGuiUtil.DRAW.drawScaledElementUpExternal(this, screen_coords, 103, 53, 0, 0, 19, 19, this.INVENTORY.getCookProgressScaled(19), OrecraftReference.GUI.RESOURCE.GUI_PRODUCER_SCALED_ELEMENTS);
		ModGuiUtil.DRAW.drawPowerBar(this, screen_coords, 72, 15, this.INVENTORY.getPowerRemainingScaled(Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR[2]), Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR, this.INVENTORY.hasStored());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}