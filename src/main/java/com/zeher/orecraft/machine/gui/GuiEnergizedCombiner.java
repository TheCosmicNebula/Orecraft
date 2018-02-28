package com.zeher.orecraft.machine.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.client.gui.GuiFluidButton;
import com.zeher.orecraft.core.handlers.NetworkHandler;
import com.zeher.orecraft.machine.container.ContainerEnergizedCombiner;
import com.zeher.orecraft.machine.tileentity.TileEntityEnergizedCombiner;
import com.zeher.trzlib.api.TRZTextUtil;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

@SideOnly(Side.CLIENT)
public class GuiEnergizedCombiner extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("orecraft:textures/gui/machine/energized/combiner/gui_combiner.png");
	
	private GuiFluidButton tank_button_empty;
	private GuiFluidButton tank_button_clear;
	
	private GuiFluidButton mode_button_craft;
	private GuiFluidButton mode_button_fluid;
	
	private TileEntityEnergizedCombiner inventory;

	public GuiEnergizedCombiner(InventoryPlayer par1InventoryPlayer, TileEntityEnergizedCombiner par2TileEntityCombiner) {
		super(new ContainerEnergizedCombiner(par1InventoryPlayer, par2TileEntityCombiner));
		this.inventory = par2TileEntityCombiner;
		this.xSize = 220;
		this.ySize = 177;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.inventory.hasCustomName() ? this.inventory.getName() : I18n.format(this.inventory.getName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) - 4, 4, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		this.buttonList.clear();
		if(this.inventory.isFluidEmpty()){
			this.tank_button_empty = this.addButton(new GuiFluidButton(0, k + 173, l + 60, 18, 18, 1, false));
		} else {
			this.tank_button_clear = this.addButton(new GuiFluidButton(0, k + 173, l + 60, 18, 18, 4, true));
		}
		
		if(this.inventory.mode == 0){
			this.mode_button_craft = this.addButton(new GuiFluidButton(1, k + 126, l + 59, 18, 18, 7, true));
		}
		if(this.inventory.mode == 1){
			this.mode_button_fluid = this.addButton(new GuiFluidButton(1, k + 126, l + 59, 18, 18, 9, true));
		}
		
		if (mouseX > k + 29 && mouseX < k + 47) {
			if (mouseY > l + 16 && mouseY < l + 78) {
				if(this.inventory.isBurning()){
					String[] desc = {TRZTextUtil.GREEN + "Power: " + TRZTextUtil.LIGHT_GRAY + this.inventory.stored , TRZTextUtil.RED +  "Using: " + this.inventory.input_rate };
					List temp = Arrays.asList(desc);
					this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
				} else {
					String[] desc = {TRZTextUtil.GREEN + "Power: " + TRZTextUtil.LIGHT_GRAY + this.inventory.stored};
					List temp = Arrays.asList(desc);
					this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
				}
			}
		}
		
		if (mouseX > k + 173 && mouseX < k + 190) {
			if (mouseY > l + 17 && mouseY < l + 56) {
				if(this.inventory.getTank().getFluidAmount() > 0){
					String[] desc_fill = {TRZTextUtil.TEAL + "Fluid: " + this.inventory.tank.getFluid().getLocalizedName() , TRZTextUtil.ORANGE + "Amount: " + this.inventory.tank.getFluidAmount() + " mB"};
					List temp_fill = Arrays.asList(desc_fill);
					this.drawHoveringText(temp_fill, mouseX - k, mouseY - l, fontRendererObj);
				} else {
					String[] desc_empty = {TRZTextUtil.TEAL + "Empty:" , TRZTextUtil.ORANGE + "Amount: 0 mB"};
					List temp_empty = Arrays.asList(desc_empty);
					this.drawHoveringText(temp_empty, mouseX - k, mouseY - l, fontRendererObj);
				}
			}
		}
		
		if(mouseX > k + 173 && mouseX < k + 191){
			if(mouseY > l + 60 && mouseY < l + 78){
				if(!this.inventory.isFluidEmpty()){
					if(this.isShiftKeyDown()){
						String[] desc = {TRZTextUtil.GREEN + "Empty above tank.", TRZTextUtil.RED + "Warning:" + TRZTextUtil.ORANGE + " Cannot be undone!"};
						List temp = Arrays.asList(desc);
						this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
					} else {
						String[] desc = {TRZTextUtil.GREEN + "Shift click " + TRZTextUtil.LIGHT_GRAY + "to empty above tank."};
						List temp = Arrays.asList(desc);
						this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
					
					}
				}
			}
		}
		
		if(mouseX > k + 126 && mouseX < k + 144){
			if(mouseY > l + 59 && mouseY < l + 77){
				if(this.inventory.getMode() == 0){
					String[] desc = {TRZTextUtil.GREEN + "Click to change machine mode.", TRZTextUtil.LIGHT_GRAY + "Current mode: " + TRZTextUtil.ORANGE + "Standard craft."};
					List temp = Arrays.asList(desc);
					this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
				} 
				if(this.inventory.getMode() == 1){
					String[] desc = {TRZTextUtil.GREEN + "Click to change machine mode.", TRZTextUtil.LIGHT_GRAY + "Current mode: " + TRZTextUtil.TEAL + "Fluid craft."};
					List temp = Arrays.asList(desc);
					this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);	
				}
			}
		}
	}
	
	protected void actionPerformed(GuiButton button)
    {
		if(this.isShiftKeyDown()){
			if(button.equals(this.tank_button_clear)){
				NetworkHandler.sendEnergizedCombinerPacket(inventory.getPos(), "one", "empty", inventory);
			}
		}
		
		if(button.equals(this.mode_button_craft)){
			NetworkHandler.sendEnergizedCombinerPacket(inventory.getPos(), "", "mode_set", 1, inventory);
		}
		
		if(button.equals(this.mode_button_fluid)){
			NetworkHandler.sendEnergizedCombinerPacket(inventory.getPos(), "", "mode_set", 0, inventory);
		}
    }

	protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		if (this.inventory.hasPower()) {
			int i1 = this.inventory.getPowerRemainingScaled(61);
			drawTexturedModalRect(k + 29, l + 78 - i1, 220, 62 - i1, 18, i1);
		}
		if(inventory.mode == 0){
			int i1 = this.inventory.getCookProgressScaled(20);
			drawTexturedModalRect(k + 125, l + 40, 220, 78, i1 + 1, 17);
		}
		if(inventory.mode == 1){
			int tex = this.inventory.getCookProgressScaled(20);
			drawTexturedModalRect(k+ 125, l + 40, 220, 62, tex + 1, 16);
		}
	}
}
