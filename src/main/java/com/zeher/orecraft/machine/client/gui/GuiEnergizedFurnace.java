package com.zeher.orecraft.machine.client.gui;

import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.core.container.ContainerEnergizedFurnace;
import com.zeher.orecraft.machine.core.tileentity.TileEntityEnergizedFurnace;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.client.gui.util.ModGuiUtil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnergizedFurnace extends GuiContainer {

	private TileEntityEnergizedFurnace INVENTORY;

	public GuiEnergizedFurnace(InventoryPlayer par1InventoryPlayer, TileEntityEnergizedFurnace par2TileEntityFurnace) {
		super(new ContainerEnergizedFurnace(par1InventoryPlayer, par2TileEntityFurnace));
		this.INVENTORY = par2TileEntityFurnace;
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
			if (this.INVENTORY.getCookTime(0) > 0 || this.INVENTORY.getCookTime(1) > 0 || this.INVENTORY.canSmeltOne() || this.INVENTORY.canSmeltTwo() && this.INVENTORY.hasStored()) {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextRF(this.INVENTORY.getStored(), Reference.VALUE.MACHINE.machineDrainRate(this.INVENTORY.getCookSpeed())), mouse_x - screen_x, mouse_y - screen_y);
			} else {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextNo(this.INVENTORY.getStored()), mouse_x - screen_x, mouse_y - screen_y);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int mouse_x, int mouse_y) {
		this.mc.getTextureManager().bindTexture(OrecraftReference.GUI.RESOURCE.GUI_FURNACE_TWO);

		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
	
		ModGuiUtil.DRAW.drawBackground(this, screen_coords, OrecraftReference.GUI.RESOURCE.GUI_FURNACE_TWO);
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
