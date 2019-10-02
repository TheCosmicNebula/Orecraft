package com.zeher.orecraft.machine.client.gui;

import com.zeher.orecraft.core.handler.NetworkHandler;
import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.machine.core.container.ContainerPoweredFluidCrafter;
import com.zeher.orecraft.machine.core.tileentity.TileEntityPoweredFluidCrafter;
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
public class GuiPoweredFluidCrafter extends GuiContainer {
	
	private GuiFluidButton tank_button_empty;
	private GuiFluidButton tank_button_clear;
	
	private GuiFluidButton mode_button_craft;
	private GuiFluidButton mode_button_melt;
	
	private static final ResourceLocation texture = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/powered/fluidcrafter/gui_fluidcrafter.png");
	private TileEntityPoweredFluidCrafter INVENTORY;

	public GuiPoweredFluidCrafter(InventoryPlayer par1InventoryPlayer, TileEntityPoweredFluidCrafter par2TileEntitygenerator) {
		super(new ContainerPoweredFluidCrafter(par1InventoryPlayer, par2TileEntitygenerator));
		this.INVENTORY = par2TileEntitygenerator;
		
		this.xSize = 176;
		this.ySize = 178;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.FONT.DRAW.drawCustomString(this.INVENTORY, this.fontRenderer, screen_coords, 8, 4);
		ModGuiUtil.FONT.DRAW.drawInventoryString(this.fontRenderer, screen_coords, 8, (this.ySize / 2) - 4);
		
		this.buttonList.clear();
		
		if(this.INVENTORY.isFluidEmpty()){
			this.tank_button_empty = this.addButton(new GuiFluidButton(0, screen_coords[0] + 125, screen_coords[1] + 60, 18, 1, false));
		} else {
			this.tank_button_clear = this.addButton(new GuiFluidButton(0, screen_coords[0] + 125, screen_coords[1] + 60, 18, 4, true));
		}
		
		switch(this.INVENTORY.getMode()) {
			case 0:
				this.mode_button_craft = this.addButton(new GuiFluidButton(1, screen_coords[0] + 67, screen_coords[1] + 16, 18, 8, true));
				break;
			case 1:
				this.mode_button_melt = this.addButton(new GuiFluidButton(1, screen_coords[0] + 67, screen_coords[1] + 16, 18, 7, true));
				break;
			default:
				break;
		}
		
		this.drawHoveringText(mouse_x, mouse_y, screen_coords);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.equals(this.tank_button_clear)) {
			if (this.isShiftKeyDown()) {
				NetworkHandler.sendPoweredFluidCrafterPacket(this.INVENTORY.getPos(), "one", "empty", this.INVENTORY);
			}
		} else if (button.equals(this.mode_button_craft)) {
			NetworkHandler.sendPoweredFluidCrafterPacket(this.INVENTORY.getPos(), "", "mode_set", 1, this.INVENTORY);
		} else if (button.equals(this.mode_button_melt)) {
			NetworkHandler.sendPoweredFluidCrafterPacket(this.INVENTORY.getPos(), "", "mode_set", 0, this.INVENTORY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		int[] screen_coords = new int[]{(this.width - this.xSize) / 2, (this.height - this.ySize) / 2};
		
		ModGuiUtil.DRAW.drawBackground(this, screen_coords, texture);
		
		switch(this.INVENTORY.getMode()) {
			case 0:
				ModGuiUtil.DRAW.drawStaticElement(this, screen_coords, 64, 60, 176, 0, 24, 16);
				ModGuiUtil.DRAW.drawScaledElementRightNestled(this, screen_coords, 64, 60, 176, 16, 16, this.INVENTORY.getProcessProgressScaled(24));
				ModGuiUtil.DRAW.drawSlot(this, screen_coords, 67, 38, Reference.GUI.GUI_SLOT_INFO.INPUT_SMALL);
				break;
			case 1:
				ModGuiUtil.DRAW.drawStaticElement(this, screen_coords, 64, 60, 176, 32, 24, 16);
				ModGuiUtil.DRAW.drawScaledElementRightNestled(this, screen_coords, 64, 60, 176, 48, 16, this.INVENTORY.getProcessProgressScaled(24));
				ModGuiUtil.DRAW.drawSlot(this, screen_coords, 67, 38, Reference.GUI.GUI_SLOT_INFO.OUTPUT_SMALL);
				break;
		}
		
		ModGuiUtil.DRAW.drawPowerBar(this, screen_coords, 32, 16, this.INVENTORY.getPowerRemainingScaled(Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR_SMALL[2]), Reference.GUI.ENERGY_BAR_INFO.POWERED_BAR_SMALL, this.INVENTORY.hasStored());
		ModGuiUtil.DRAW.drawFluidTank(this, screen_coords, 126, 17, this.INVENTORY.getTank(), this.INVENTORY.getFluidLevelScaled(38));
	}

	@Override
	public void drawScreen(int mouse_x, int mouse_y, float ticks) {
        this.drawDefaultBackground();
        super.drawScreen(mouse_x, mouse_y, ticks);
        this.renderHoveredToolTip(mouse_x, mouse_y);
    }
	
	protected void drawHoveringText(int mouse_x, int mouse_y, int[] screen_coords) {
		if (ModGuiUtil.IS_HOVERING.isHoveringPowerSmall(mouse_x, mouse_y, screen_coords[0] + 32, screen_coords[1] + 16)) {
			if (this.INVENTORY.cook_time > 0 || this.INVENTORY.canProcess() && this.INVENTORY.hasStored()) {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextRF(this.INVENTORY.getStored(), Reference.VALUE.MACHINE.machineDrainRate(this.INVENTORY.getCookSpeed())), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
			} else {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.storedTextNo(this.INVENTORY.getStored()), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
			}
		} else if (ModGuiUtil.IS_HOVERING.isHovering(mouse_x, mouse_y, screen_coords[0] + 125, screen_coords[0] + 142, screen_coords[1] + 16, screen_coords[1] + 55)) {
			if (this.INVENTORY.getTank().getFluidAmount() > 0) {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.fluidText(this.INVENTORY.getTank().getFluid().getLocalizedName(), this.INVENTORY.getTank().getFluidAmount()), mouse_x  - screen_coords[0], mouse_y - screen_coords[1]);
			} else {
				this.drawHoveringText(ModGuiUtil.TEXT_LIST.fluidTextEmpty(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
			}
		} else if (ModGuiUtil.IS_HOVERING.isHovering(mouse_x, mouse_y, screen_coords[0] + 125, screen_coords[0] + 142, screen_coords[1] + 60, screen_coords[1] + 77)) {
			if (!this.INVENTORY.isFluidEmpty()) {
				if (this.isShiftKeyDown()) {
					this.drawHoveringText(ModGuiUtil.TEXT_LIST.emptyFluidTankDo(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				} else {
					this.drawHoveringText(ModGuiUtil.TEXT_LIST.emptyFluidTank(), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
				}
			}
		} else if (ModGuiUtil.IS_HOVERING.isHovering(mouse_x, mouse_y, screen_coords[0] + 67, screen_coords[0] + 84, screen_coords[1] + 16, screen_coords[1] + 33)) {
			switch (this.INVENTORY.getMode()) {
				case 0:
					this.drawHoveringText(ModGuiUtil.TEXT_LIST.modeChange(TextUtil.ORANGE, "Melt"), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
					break;
				case 1:
					this.drawHoveringText(ModGuiUtil.TEXT_LIST.modeChange(TextUtil.TEAL, "Craft"), mouse_x - screen_coords[0], mouse_y - screen_coords[1]);
					break;
			}
		}
	}
}