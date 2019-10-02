package com.zeher.orecraft.machine.client.gui;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.core.container.ContainerEnergizedGrinder;
import com.zeher.orecraft.machine.core.tileentity.TileEntityEnergizedGrinder;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.client.gui.util.ModGuiUtil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnergizedGrinder extends GuiContainer {
	
	private TileEntityEnergizedGrinder INVENTORY;

	public GuiEnergizedGrinder(InventoryPlayer par1InventoryPlayer, TileEntityEnergizedGrinder par2TileEntityGrinder) {
		super(new ContainerEnergizedGrinder(par1InventoryPlayer, par2TileEntityGrinder));
		this.INVENTORY = par2TileEntityGrinder;
		this.xSize = 176;
		this.ySize = 174;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.FONT.DRAW.drawCustomString(this.INVENTORY, this.fontRenderer, screen_coords, 43, 4);
		ModGuiUtil.FONT.DRAW.drawInventoryString(this.fontRenderer, screen_coords, 7, (this.ySize / 2) - 5);
		
		if (ModGuiUtil.IS_HOVERING.isHoveringPowerSmall(mouse_x, mouse_y, screen_coords[0] + 68, screen_coords[1] + 16)) {
			if (this.INVENTORY.getCookTime(0) > 0 || this.INVENTORY.getCookTime(1) > 0 || this.INVENTORY.canGrindOne() || this.INVENTORY.canGrindTwo() && this.INVENTORY.hasStored()) {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextRF(this.INVENTORY.getStored(), Reference.VALUE.MACHINE.machineDrainRate(this.INVENTORY.getCookSpeed())), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
			} else {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextNo(this.INVENTORY.getStored()), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int mouse_x, int mouse_y) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.DRAW.drawBackground(this, screen_coords, OrecraftReference.GUI.RESOURCE.GUI_GRINDER_TWO);
		ModGuiUtil.DRAW.drawScaledElementDownNestled(this, screen_coords, 95, 39, 176, 0, 16, this.INVENTORY.getCookProgressScaledOne(16));
		ModGuiUtil.DRAW.drawScaledElementDownNestled(this, screen_coords, 113, 39, 176, 0, 16, this.INVENTORY.getCookProgressScaledTwo(16));
		
		ModGuiUtil.DRAW.drawPowerBar(this, screen_coords, 68, 16, this.INVENTORY.getPowerRemainingScaled(Reference.GUI.ENERGY_BAR_INFO.ENERGIZED_BAR_SMALL[2]), Reference.GUI.ENERGY_BAR_INFO.ENERGIZED_BAR_SMALL, this.INVENTORY.hasStored());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
	
}