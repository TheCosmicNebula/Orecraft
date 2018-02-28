package com.zeher.orecraft.production.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.client.gui.GuiFluidButton;
import com.zeher.orecraft.core.handlers.NetworkHandler;
import com.zeher.orecraft.production.container.ContainerPeltierGeneratorBasic;
import com.zeher.orecraft.production.tileentity.TileEntityPeltierGeneratorBasic;

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
public class GuiPeltierGeneratorBasic extends GuiContainer {
	
	private GuiFluidButton tank_one_button;
	private GuiFluidButton tank_one_button_fluid;
	private GuiFluidButton tank_two_button;
	private GuiFluidButton tank_two_button_fluid;
	
	private static final ResourceLocation texture = new ResourceLocation("orecraft:textures/gui/production/generator/peltier/gui_peltier.png");
	private TileEntityPeltierGeneratorBasic inventory;

	public GuiPeltierGeneratorBasic(InventoryPlayer par1InventoryPlayer, TileEntityPeltierGeneratorBasic par2TileEntitygenerator) {
		super(new ContainerPeltierGeneratorBasic(par1InventoryPlayer, par2TileEntitygenerator));
		this.inventory = par2TileEntitygenerator;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		String s = this.inventory.hasCustomName() ? this.inventory.getName() : I18n.format(this.inventory.getName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) + 10, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		this.buttonList.clear();
		if(this.inventory.isFluidEmpty()){
			this.tank_one_button = this.addButton(new GuiFluidButton(0, k + 129, l + 51, 18, 18, 1, false));
		} else {
			this.tank_one_button_fluid = this.addButton(new GuiFluidButton(0, k + 129, l + 51, 18, 18, 4, true));
		}
		if(this.inventory.isFluidEmptyTwo()){
			this.tank_two_button = this.addButton(new GuiFluidButton(1, k + 107, l + 51, 18, 18, 1, false));	
		} else {
			this.tank_two_button_fluid = this.addButton(new GuiFluidButton(0, k + 107, l + 51, 18, 18, 4, true));
		}
		
		if (mouseX > k + 29 && mouseX < k + 47) {
			if (mouseY > l +7 && mouseY < l + 68) {
				if(this.inventory.isBurning()){
					String[] desc = { "Power: " + this.inventory.stored , "Producing: " + this.inventory.generation_rate };
					List temp = Arrays.asList(desc);
					this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
				} else {
					String[] desc = { "Power: " + this.inventory.stored};
					List temp = Arrays.asList(desc);
					this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
				}
			}
		}
		
		if (mouseX > k + 129 && mouseX < k + 146) {
			if (mouseY > l + 7 && mouseY < l + 46) {
				if(this.inventory.getTank().getFluidAmount() > 0){
					String[] desc_fill = { "Fluid: " + this.inventory.tank.getFluid().getLocalizedName() , "Amount: " + this.inventory.tank.getFluidAmount() + " mB"};
					List temp_fill = Arrays.asList(desc_fill);
					this.drawHoveringText(temp_fill, mouseX - k, mouseY - l, fontRendererObj);
				} else {
					String[] desc_empty = { "Empty" , "Amount: 0 mB"};
					List temp_empty = Arrays.asList(desc_empty);
					this.drawHoveringText(temp_empty, mouseX - k, mouseY - l, fontRendererObj);
				}
			}
		}
		
		if (mouseX > k + 107 && mouseX < k + 124) {
			if (mouseY > l + 7 && mouseY < l + 46) {
				if(!this.inventory.isFluidEmpty()){
					String[] desc_fill = { "Fluid: " + this.inventory.tank_two.getFluid().getLocalizedName() , "Amount: " + this.inventory.tank_two.getFluidAmount() + " mB"};
					List temp_fill = Arrays.asList(desc_fill);
					this.drawHoveringText(temp_fill, mouseX - k, mouseY - l, fontRendererObj);
				} else {
					String[] desc_empty = { "Empty" , "Amount: 0 mB"};
					List temp_empty = Arrays.asList(desc_empty);
					this.drawHoveringText(temp_empty, mouseX - k, mouseY - l, fontRendererObj);
				}
			}
		}
		
		if(mouseX > k + 107 && mouseX < k + 125){
			if(mouseY > l + 51 && mouseY < l + 69){
				if(!this.inventory.isFluidEmptyTwo()){
					if(this.isShiftKeyDown()){
						String[] desc = {"Empty above tank.", "Warning: Cannot be undone!"};
						List temp = Arrays.asList(desc);
						this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
					} else {
						String[] desc = {"Shift click to empty above tank."};
						List temp = Arrays.asList(desc);
						this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
					}
				}
			}
		}
		
		if(mouseX > k + 129 && mouseX < k + 147){
			if(mouseY > l + 51 && mouseY < l + 69){
				if(this.inventory.isFluidEmpty()){
					
				} else {
					if(this.isShiftKeyDown()){
						String[] desc = {"Empty above tank.", "Warning: Cannot be undone!"};
						List temp = Arrays.asList(desc);
						this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
					} else {
						String[] desc = {"Shift click to empty above tank."};
						List temp = Arrays.asList(desc);
						this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
					}
				}
			}
		}
	}
	
	protected void actionPerformed(GuiButton button)
    {
		if(this.isShiftKeyDown()){
			if(!this.inventory.isFluidEmpty()){
				if(button.equals(this.tank_one_button_fluid)){
					NetworkHandler.sendPeltierGeneratorPacket(inventory.getPos(), "one", "empty", inventory);
				}
			}
			if(!this.inventory.isFluidEmptyTwo()){
				if(button.equals(this.tank_two_button_fluid)){
					NetworkHandler.sendPeltierGeneratorPacket(inventory.getPos(), "two", "empty", inventory);
				}
			}
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
			drawTexturedModalRect(k + 29, l + 68 - i1, 176, 61 - i1, 18, i1);
		}
		int i1 = this.inventory.getCookProgressScaled(21);
		drawTexturedModalRect(k + 57, l + 29, 176, 62, i1 + 1, 20);
		
		if(this.inventory.getTank().getFluidAmount() > 0){
			Fluid fluid = inventory.getTank().getFluid().getFluid();
			TextureAtlasSprite fluid_texture = mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
			mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			
			int i2 = this.inventory.getFluidLevelScaled(38);
			
			if(i2 <= 30){
				drawTexturedModalRect(k + 130, l + 46 - i2, fluid_texture, 16, i2);
			}
			if(i2 > 30 && i2 <= 45){
				drawTexturedModalRect(k + 130, l + 46 - i2, fluid_texture, 16, i2 / 2 + 1);
				drawTexturedModalRect(k + 130, l + 46 - i2/2, fluid_texture, 16, i2 / 2);
			
			}
			if(i2 > 45){
				drawTexturedModalRect(k + 130, l + 46 - i2, fluid_texture, 16, i2 / 2);
				drawTexturedModalRect(k + 130, l + 46 - i2/2, fluid_texture, 16, i2 / 2);
			}
		}
		if(this.inventory.getTankTwo().getFluidAmount() > 0){
			Fluid fluid_two = inventory.getTankTwo().getFluid().getFluid();
			TextureAtlasSprite fluid_texture_two = mc.getTextureMapBlocks().getTextureExtry(fluid_two.getStill().toString());
			mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			
			
			
			int i3 = this.inventory.getFluidLevelScaledTwo(38);
			
			if(i3 <= 30){
				drawTexturedModalRect(k + 108, l + 46 - i3, fluid_texture_two, 16, i3);
			}
			if(i3 > 30 && i3 <= 45){
				drawTexturedModalRect(k + 108, l + 46 - i3, fluid_texture_two, 16, i3 / 2 + 1);
				drawTexturedModalRect(k + 108, l + 46 - i3/2, fluid_texture_two, 16, i3 / 2);
			
			}
			if(i3 > 45){
				drawTexturedModalRect(k + 108, l + 46 - i3, fluid_texture_two, 16, i3 / 2);
				drawTexturedModalRect(k + 108, l + 46 - i3/2, fluid_texture_two, 16, i3 / 2);
			}
		}
		
	}
	
}
