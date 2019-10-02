package com.zeher.orecraft.machine.client.gui;

import com.zeher.orecraft.core.handler.NetworkHandler;
import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.core.container.ContainerPoweredOreProcessor;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredOreProcessor;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.util.TextUtil;
import com.zeher.zeherlib.client.gui.button.GuiFluidButton;
import com.zeher.zeherlib.client.gui.util.ModGuiUtil;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPoweredOreProcessor extends GuiContainer {
	
	private GuiFluidButton tank_button_empty;
	private GuiFluidButton tank_button_clear;
	
	private GuiFluidButton mode_button_clean;
	private GuiFluidButton mode_button_refine;
	
	private static final ResourceLocation texture = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/powered/oreprocessor/gui_oreprocessor.png");
	private TileEntityPoweredOreProcessor INVENTORY;

	public GuiPoweredOreProcessor(InventoryPlayer par1InventoryPlayer, TileEntityPoweredOreProcessor par2TileEntitygenerator) {
		super(new ContainerPoweredOreProcessor(par1InventoryPlayer, par2TileEntitygenerator));
		this.INVENTORY = par2TileEntitygenerator;
		
		this.xSize = 176;
		this.ySize = 178;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.FONT.DRAW.drawCustomString(this.INVENTORY, this.fontRenderer, screen_coords, 9, 4);
		ModGuiUtil.FONT.DRAW.drawInventoryString(this.fontRenderer, screen_coords, 7, (this.ySize / 2) - 3);
		
		this.buttonList.clear();
		
		if(INVENTORY.isFluidEmpty()){
			this.tank_button_empty = this.addButton(new GuiFluidButton(0, screen_coords[0] + 123, screen_coords[1] + 60, 18, 1, false));
		} else {
			if(this.INVENTORY.mode == 0){
				this.tank_button_clear = this.addButton(new GuiFluidButton(0, screen_coords[0] + 123, screen_coords[1] + 60, 18, 4, true));
			} else {
				this.tank_button_empty = this.addButton(new GuiFluidButton(0, screen_coords[0] + 123, screen_coords[1] + 60, 18, 1, false));
			}
		}
		
		switch (this.INVENTORY.getMode()) {
			case 0:
				this.mode_button_clean = this.addButton(new GuiFluidButton(1, screen_coords[0] + 62, screen_coords[1] + 38, 18, true));
				break;
			case 1:
				this.mode_button_refine = this.addButton(new GuiFluidButton(1, screen_coords[0] + 62, screen_coords[1] + 38, 18, 8, true));
				break;
		}
		
		this.drawHoveringText(mouse_x, mouse_y, screen_coords);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.equals(this.tank_button_clear)){
			if (this.isShiftKeyDown()){
				NetworkHandler.sendPoweredOreProcessorPacket(INVENTORY.getPos(), "one", "empty", INVENTORY);
			}
		} else if (button.equals(this.mode_button_clean)){
			NetworkHandler.sendPoweredOreProcessorPacket(INVENTORY.getPos(), "", "mode_set", 1, INVENTORY);
		} else if (button.equals(this.mode_button_refine)){
			NetworkHandler.sendPoweredOreProcessorPacket(INVENTORY.getPos(), "", "mode_set", 0, INVENTORY);
		}
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.DRAW.drawBackground(this, screen_coords, texture);

		ModGuiUtil.DRAW.drawScaledElementUpNestled(this, screen_coords, 82, 16, 176, 0, 10, 63, this.INVENTORY.getProcessProgressScaled(63));
		ModGuiUtil.DRAW.drawPowerBar(this, screen_coords, 34, 16, this.INVENTORY.getPowerRemainingScaled(Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR_SMALL[2]), Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR_SMALL, this.INVENTORY.hasStored());
		ModGuiUtil.DRAW.drawFluidTank(this, screen_coords, 124, 17, this.INVENTORY.getTank(), this.INVENTORY.getFluidLevelScaled(38));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
	
	public void drawHoveringText(int mouse_x, int mouse_y, int[] screen_coords) {
		if (ModGuiUtil.IS_HOVERING.isHoveringPowerSmall(mouse_x, mouse_y, screen_coords[0] + 33, screen_coords[1] + 16)) {
			if (this.INVENTORY.cook_time > 0 || this.INVENTORY.canProcess() && this.INVENTORY.hasStored()) {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextRF(this.INVENTORY.getStored(), Reference.VALUE.MACHINE.machineDrainRate(this.INVENTORY.getCookSpeed())), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
			}
			this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextNo(this.INVENTORY.getStored()), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
		}
		
		if(INVENTORY.mode == 0){
			if (ModGuiUtil.IS_HOVERING.isHovering(mouse_x, mouse_y, screen_coords[0] + 123, screen_coords[0] + 140, screen_coords[1] + 17, screen_coords[1] + 56)) {
				if (this.INVENTORY.getTank().getFluidAmount() > 0) {
					this.drawHoveringText(ModGuiUtil.TEXT_LIST.fluidText(this.INVENTORY.getTank().getFluid().getLocalizedName(), this.INVENTORY.getTank().getFluidAmount()), mouse_x  - screen_coords[0], mouse_y - screen_coords[1]);
				} else {
					this.drawHoveringText(ModGuiUtil.TEXT_LIST.fluidTextEmpty(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				}
			}
			
			if (ModGuiUtil.IS_HOVERING.isHovering(mouse_x, mouse_y, screen_coords[0] + 123, screen_coords[0] + 140, screen_coords[1] + 61, screen_coords[1] + 78)) {
				if (!this.INVENTORY.isFluidEmpty()) {
					if (this.isShiftKeyDown()) {
						this.drawHoveringText(ModGuiUtil.TEXT_LIST.emptyFluidTankDo(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
					} else {
						this.drawHoveringText(ModGuiUtil.TEXT_LIST.emptyFluidTank(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]); 
					}
				}
			}
		}
		
		if (ModGuiUtil.IS_HOVERING.isHovering(mouse_x, mouse_y, screen_coords[0] + 62, screen_coords[0] + 79, screen_coords[1] + 39, screen_coords[1] + 56)) {
			if (this.INVENTORY.getMode() == 0) {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.modeChange(TextUtil.TEAL, "Clean"), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
			}
			
			if (this.INVENTORY.getMode() == 1) {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.modeChange(TextUtil.ORANGE, "Refine"), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
			}
		}
	}
	
}