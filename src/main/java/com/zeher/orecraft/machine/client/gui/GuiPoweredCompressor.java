package com.zeher.orecraft.machine.client.gui;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.core.container.ContainerPoweredCompressor;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredCompressor;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.client.gui.util.ModGuiUtil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPoweredCompressor extends GuiContainer {
	
	private TileEntityPoweredCompressor INVENTORY;
	
	public GuiPoweredCompressor(InventoryPlayer par1InventoryPlayer, TileEntityPoweredCompressor par2TileEntityCompressor) {
		super(new ContainerPoweredCompressor(par1InventoryPlayer, par2TileEntityCompressor));
		this.INVENTORY = par2TileEntityCompressor;
		
		this.xSize = 176;
		this.ySize = 177;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.FONT.DRAW.drawCustomString(this.INVENTORY, this.fontRenderer, screen_coords, 51, 4);
		ModGuiUtil.FONT.DRAW.drawInventoryString(this.fontRenderer, screen_coords, 7, (this.ySize / 2) - 3);
		
		if (ModGuiUtil.IS_HOVERING.isHoveringPowerSmall(mouse_x, mouse_y, screen_coords[0] + 76, screen_coords[1] + 17)) {
			if (this.INVENTORY.getCookTime(0) > 0 || this.INVENTORY.canCompress() && this.INVENTORY.hasStored()) {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextRF(this.INVENTORY.getStored(), Reference.VALUE.MACHINE.machineDrainRate(this.INVENTORY.getCookSpeed())), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
			} else {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextNo(this.INVENTORY.getStored()), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.DRAW.drawBackground(this, screen_coords, OrecraftReference.GUI.RESOURCE.GUI_COMPRESSOR);
		ModGuiUtil.DRAW.drawScaledElementDownNestled(this, screen_coords, 104, 39, 176, 0, 16, this.INVENTORY.getCookProgressScaled(16));
		ModGuiUtil.DRAW.drawPowerBar(this, screen_coords, 76, 16, this.INVENTORY.getPowerRemainingScaled(Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR_SMALL[2]), Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR_SMALL, this.INVENTORY.hasStored());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
	}
	
}