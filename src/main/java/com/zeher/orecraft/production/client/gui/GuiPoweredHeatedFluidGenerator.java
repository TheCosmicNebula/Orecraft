package com.zeher.orecraft.production.client.gui;

import com.zeher.orecraft.core.handler.NetworkHandler;
import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.production.container.ContainerPoweredHeatedFluidGenerator;
import com.zeher.orecraft.production.core.tileentity.TileEntityPoweredHeatedFluidGenerator;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.client.gui.button.GuiFluidButton;
import com.zeher.zeherlib.client.gui.util.ModGuiUtil;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPoweredHeatedFluidGenerator extends GuiContainer {
	
	private GuiFluidButton TANK_BUTTON;
	private TileEntityPoweredHeatedFluidGenerator INVENTORY;

	public GuiPoweredHeatedFluidGenerator(InventoryPlayer par1InventoryPlayer, TileEntityPoweredHeatedFluidGenerator par2TileEntitygenerator) {
		super(new ContainerPoweredHeatedFluidGenerator(par1InventoryPlayer, par2TileEntitygenerator));
		this.INVENTORY = par2TileEntitygenerator;
		this.ySize = 175;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.FONT.DRAW.drawCustomString(this.INVENTORY, this.fontRenderer, screen_coords, 7, 4);
		ModGuiUtil.FONT.DRAW.drawInventoryString(this.fontRenderer, screen_coords, 7, 83);
		
		this.buttonList.clear();
		this.TANK_BUTTON = this.addButton(ModGuiUtil.DRAW.createFluidButton(0, screen_coords, 125, 60, 18, this.INVENTORY.isFluidEmpty()));
		
		ModGuiUtil.DRAW.drawHoveringTextPowerProducer(this, screen_coords, 32, 16, mouse_x, mouse_y, this.INVENTORY.stored, this.INVENTORY.generation_rate, (this.INVENTORY.cook_time > 0));
		ModGuiUtil.DRAW.drawHoveringTextFluid(this, screen_coords, 125, 16, mouse_x, mouse_y, this.INVENTORY.getTank());
		ModGuiUtil.DRAW.drawHoveringTextEmptyFluidButton(this, screen_coords, 125, 60, mouse_x, mouse_y, (!this.INVENTORY.isFluidEmpty()));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int mouse_x, int mouse_y) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.DRAW.drawBackground(this, screen_coords, OrecraftReference.GUI.RESOURCE.GUI_PRODUCER_HEATEDFLUID);
		ModGuiUtil.DRAW.drawScaledElementUpExternal(this, screen_coords, 67, 38, 0, 0, 19, 19, this.INVENTORY.getCookProgressScaled(19), OrecraftReference.GUI.RESOURCE.GUI_PRODUCER_SCALED_ELEMENTS);
		ModGuiUtil.DRAW.drawFluidTank(this, screen_coords, 126, 17, this.INVENTORY.getTank(), this.INVENTORY.getFluidLevelScaled(38));
		ModGuiUtil.DRAW.drawPowerBar(this, screen_coords, 32, 15, this.INVENTORY.getPowerRemainingScaled(Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR[2]), Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR, this.INVENTORY.hasStored());
	}

	@Override
	protected void actionPerformed(GuiButton button) {		
		if(this.isShiftKeyDown()){
			if(button.equals(this.TANK_BUTTON)){
				NetworkHandler.sendHeatedFluidGeneratorPacket(INVENTORY.getPos(), "one", "empty", INVENTORY);
			}
		}
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}