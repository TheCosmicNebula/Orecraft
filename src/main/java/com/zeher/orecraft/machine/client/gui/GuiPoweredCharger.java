package com.zeher.orecraft.machine.client.gui;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.core.container.ContainerPoweredCharger;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredCharger;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.client.gui.util.ModGuiUtil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPoweredCharger extends GuiContainer {
	
	private TileEntityPoweredCharger INVENTORY;

	public GuiPoweredCharger(InventoryPlayer par1InventoryPlayer, TileEntityPoweredCharger par2TileEntityCharger) {
		super(new ContainerPoweredCharger(par1InventoryPlayer, par2TileEntityCharger));
		this.INVENTORY = par2TileEntityCharger;
		
		this.xSize = 176;
		this.ySize = 177;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.FONT.DRAW.drawCustomString(this.INVENTORY, this.fontRenderer, screen_coords, 30, 4);
		ModGuiUtil.FONT.DRAW.drawInventoryString(this.fontRenderer, screen_coords, 7, (this.ySize / 2) - 3);
		
		if (ModGuiUtil.IS_HOVERING.isHoveringPowerSmall(mouse_x, mouse_y, screen_coords[0] + 55, screen_coords[1] + 17)) {
			this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextNo(this.INVENTORY.getStored()), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		ModGuiUtil.DRAW.drawBackground(this, screen_coords, OrecraftReference.GUI.RESOURCE.GUI_CHARGER);
		ModGuiUtil.DRAW.drawPowerBar(this, screen_coords, 55, 16, this.INVENTORY.getStoredRemainingScaled(Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR_SMALL[2]), Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR_SMALL, this.INVENTORY.hasStored());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
	
}