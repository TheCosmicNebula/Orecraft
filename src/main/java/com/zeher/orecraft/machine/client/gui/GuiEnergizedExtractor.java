package com.zeher.orecraft.machine.client.gui;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.core.container.ContainerEnergizedExtractor;
import com.zeher.orecraft.machine.core.tileentity.TileEntityEnergizedExtractor;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.client.gui.util.ModGuiUtil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnergizedExtractor extends GuiContainer {
	
	private TileEntityEnergizedExtractor INVENTORY;
	
	public GuiEnergizedExtractor(InventoryPlayer par1InventoryPlayer, TileEntityEnergizedExtractor par2TileEntityExtractor) {
		super(new ContainerEnergizedExtractor(par1InventoryPlayer, par2TileEntityExtractor));
		this.INVENTORY = par2TileEntityExtractor;
		this.xSize = 176;
		this.ySize = 174;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		String s = this.INVENTORY.hasCustomName() ? this.INVENTORY.getName() : I18n.format(this.INVENTORY.getName());
		this.fontRenderer.drawString(s, 43, 4, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 7, this.ySize - 96 + 4, 4210752);

		int screen_x = (this.width - this.xSize) / 2;
		int screen_y = (this.height - this.ySize) / 2;
		
		if (ModGuiUtil.IS_HOVERING.isHoveringPowerSmall(mouse_x, mouse_y, screen_x + 68, screen_y + 16)) {
			if (this.INVENTORY.getCookTime(0) > 0 || this.INVENTORY.getCookTime(1) > 0 || this.INVENTORY.canExtractOne() || this.INVENTORY.canExtractTwo() && this.INVENTORY.hasStored()) {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextRF(this.INVENTORY.getStored(), Reference.VALUE.MACHINE.machineDrainRate(this.INVENTORY.getCookSpeed())), mouse_x - screen_x, mouse_y - screen_y);
			} else {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextNo(this.INVENTORY.getStored()), mouse_x - screen_x, mouse_y - screen_y);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int mouse_x, int mouse_y) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(OrecraftReference.GUI.RESOURCE.GUI_EXTRACTOR_TWO);

		int screen_x = (this.width - this.xSize) / 2;
		int screen_y = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(screen_x, screen_y, 0, 0, this.xSize, this.ySize);
		
		int one = this.INVENTORY.getCookProgressScaledOne(16);
		this.drawTexturedModalRect(screen_x + 95, screen_y + 39, 176, 0, 16, one + 1);
		
		int two = this.INVENTORY.getCookProgressScaledTwo(16);
		this.drawTexturedModalRect(screen_x + 113, screen_y + 39, 176, 0, 16, two + 1);
		
		ModGuiUtil.DRAW.drawPowerBar(this, new int[] {screen_x, screen_y}, 68, 16, this.INVENTORY.getPowerRemainingScaled(Reference.GUI.ENERGY_BAR_INFO.ENERGIZED_BAR_SMALL[2]), Reference.GUI.ENERGY_BAR_INFO.ENERGIZED_BAR_SMALL, this.INVENTORY.hasStored());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}