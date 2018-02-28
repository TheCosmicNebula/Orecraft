package com.zeher.orecraft.machine.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.client.gui.GuiFluidButton;
import com.zeher.orecraft.core.handlers.NetworkHandler;
import com.zeher.orecraft.machine.container.ContainerPoweredOreProcessor;
import com.zeher.orecraft.machine.tileentity.TileEntityPoweredOreProcessor;
import com.zeher.trzlib.api.TRZTextUtil;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

@SideOnly(Side.CLIENT)
public class GuiPoweredOreProcessor extends GuiContainer {
	
	private GuiFluidButton tank_button_empty;
	private GuiFluidButton tank_button_clear;
	
	private GuiFluidButton mode_button_clean;
	private GuiFluidButton mode_button_refine;
	
	private static final ResourceLocation texture = new ResourceLocation("orecraft:textures/gui/machine/powered/oreprocessor/gui_oreprocessor.png");
	private TileEntityPoweredOreProcessor inventory;

	public GuiPoweredOreProcessor(InventoryPlayer par1InventoryPlayer, TileEntityPoweredOreProcessor par2TileEntitygenerator) {
		super(new ContainerPoweredOreProcessor(par1InventoryPlayer, par2TileEntitygenerator));
		this.inventory = par2TileEntitygenerator;
		this.xSize = 190;
		this.ySize = 166;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.inventory.hasCustomName() ? this.inventory.getName() : I18n.format(this.inventory.getName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		this.buttonList.clear();
		if(inventory.isFluidEmpty()){
			this.tank_button_empty = this.addButton(new GuiFluidButton(0, k + 143, l + 51, 18, 18, 1, false));
		} else {
			if(this.inventory.mode == 0){
				this.tank_button_clear = this.addButton(new GuiFluidButton(0, k + 143, l + 51, 18, 18, 4, true));
			} else {
				this.tank_button_empty = this.addButton(new GuiFluidButton(0, k + 143, l + 51, 18, 18, 1, false));
			}
		}
		
		if(this.inventory.getMode() == 0){
			this.mode_button_clean = this.addButton(new GuiFluidButton(1, k + 90, l + 43, 18, 18, 9, true));
		}
		if(this.inventory.getMode() == 1){
			this.mode_button_refine = this.addButton(new GuiFluidButton(1, k + 90, l + 43, 18, 18, 8, true));
		}
		
		if (mouseX > k + 29 && mouseX < k + 47) {
			if (mouseY > l + 6 && mouseY < l + 68) {
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
		
		if(inventory.mode == 0){
			if (mouseX > k + 144 && mouseX < k + 159) {
				if (mouseY > l + 7 && mouseY < l + 46) {
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
			
			if(mouseX > k + 143 && mouseX < k + 160){
				if(mouseY > l + 51 && mouseY < l + 69){
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
		}
		
		if(mouseX > k + 90 && mouseX < k + 108){
			if(mouseY > l + 43 && mouseY < l + 61){
				if(this.inventory.getMode() == 0){
					String[] desc = {TRZTextUtil.GREEN + "Click to change machine mode.", TRZTextUtil.LIGHT_GRAY + "Current mode: " + TRZTextUtil.TEAL + "Clean."};
					List temp = Arrays.asList(desc);
					this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
				} 
				if(this.inventory.getMode() == 1){
					String[] desc = {TRZTextUtil.GREEN + "Click to change machine mode.", TRZTextUtil.LIGHT_GRAY + "Current mode: " + TRZTextUtil.ORANGE + "Refine."};
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
				NetworkHandler.sendPoweredOreProcessorPacket(inventory.getPos(), "one", "empty", inventory);
			}
		}
		
		if(button.equals(this.mode_button_clean)){
			NetworkHandler.sendPoweredOreProcessorPacket(inventory.getPos(), "", "mode_set", 1, inventory);
		}
		
		if(button.equals(this.mode_button_refine)){
			NetworkHandler.sendPoweredOreProcessorPacket(inventory.getPos(), "", "mode_set", 0, inventory);
		}
    }
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		if (this.inventory.hasStored()) {
			int i1 = this.inventory.getPowerRemainingScaled(62);
			drawTexturedModalRect(k + 29, l + 68 - i1, 190, 61 - i1, 18, i1);
		}
		int i1 = this.inventory.getProcessProgressScaled(60);
		drawTexturedModalRect(k + 73, l + 67 - i1, 190, 122 - i1, 10, i1);
		
		if(this.inventory.mode == 1){
			drawTexturedModalRect(k + 144, l + 7, 210, 0, 18, 40);
		}
		
		if(this.inventory.getTank().getFluidAmount() > 0){
			if(this.inventory.mode == 0){
				Fluid fluid = inventory.getTank().getFluid().getFluid();
				TextureAtlasSprite fluid_texture = mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
				mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				
				int i2 = this.inventory.getFluidLevelScaled(38);
				
				if(i2 <= 30){
					drawTexturedModalRect(k + 144, l + 46 - i2, fluid_texture, 16, i2);
				}
				if(i2 > 30 && i2 <= 45){
					drawTexturedModalRect(k + 144, l + 46 - i2, fluid_texture, 16, i2 / 2 + 1);
					drawTexturedModalRect(k + 144, l + 46 - i2/2, fluid_texture, 16, i2 / 2);
				
				}
				if(i2 > 45){
					drawTexturedModalRect(k + 144, l + 46 - i2, fluid_texture, 16, i2 / 2);
					drawTexturedModalRect(k + 144, l + 46 - i2/2, fluid_texture, 16, i2 / 2);
				}
			}
		}
		
	}
	
}
