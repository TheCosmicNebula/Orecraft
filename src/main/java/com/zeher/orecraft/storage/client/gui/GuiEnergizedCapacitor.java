package com.zeher.orecraft.storage.client.gui;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.storage.core.container.ContainerEnergizedCapacitor;
import com.zeher.orecraft.storage.core.tileentity.TileEntityEnergizedCapacitor;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.client.gui.util.ModGuiUtil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnergizedCapacitor extends GuiContainer {
	
	private TileEntityEnergizedCapacitor INVENTORY;

	public GuiEnergizedCapacitor(InventoryPlayer inventoryPlayer, TileEntityEnergizedCapacitor tileEntitycapacitor) {
		super(new ContainerEnergizedCapacitor(inventoryPlayer, tileEntitycapacitor));
		this.INVENTORY = tileEntitycapacitor;
		this.xSize = 176;
		this.ySize = 176;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		String s = this.INVENTORY.hasCustomName() ? this.INVENTORY.getName() : I18n.format(this.INVENTORY.getName());
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2 - 24, 5, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 7, this.ySize - 94 + 2, 4210752);
		
		int screen_x = (this.width - this.xSize) / 2;
		int screen_y = (this.height - this.ySize) / 2;
		
		if (ModGuiUtil.IS_HOVERING.isHoveringPower(mouse_x, mouse_y, screen_x + 18, screen_y + 18)) {
			this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextNo(this.INVENTORY.getStored()), mouse_x - screen_x, mouse_y - screen_y);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		this.mc.getTextureManager().bindTexture(OrecraftReference.GUI.RESOURCE.GUI_CAPACITOR);
		
		int screen_x = (this.width - this.xSize) / 2;
		int screen_y = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(screen_x, screen_y, 0, 0, this.xSize, this.ySize);
		
		if (this.INVENTORY.hasStored()) {
			this.mc.getTextureManager().bindTexture(Reference.GUI.RESOURCE.ENERGY_BAR_TEXTURE);
			
			int[] bar_location = Reference.GUI.ENERGY_BAR_INFO.ENERGIZED_BAR;
			
			int i2 = this.INVENTORY.getPowerRemainingScaled(bar_location[2]);
			this.drawTexturedModalRect(screen_x + 17, screen_y + 78 - i2, bar_location[0], bar_location[1] - i2, bar_location[3], i2);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
