package com.zeher.orecraft.transfer.client.gui;

import java.io.IOException;

import com.zeher.orecraft.core.handler.NetworkHandler;
import com.zeher.orecraft.transfer.core.container.ContainerPoweredEnergyPipeDirection;
import com.zeher.orecraft.transfer.core.tileentity.TileEntityPoweredEnergyPipe;
import com.zeher.zeherlib.Reference;
import com.zeher.zeherlib.api.connection.EnumSide;
import com.zeher.zeherlib.api.util.GuiColours;
import com.zeher.zeherlib.client.gui.button.GuiEnergyButtonCustom;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;

public class GuiPoweredEnergyPipeDirection extends InventoryEffectRenderer {
	
	/**
	 * The order is D-U-N-S-W-E.
	 */
	private GuiEnergyButtonCustom[] buttons;
	private String[] display_names = new String[] { "", "", "", "", "", "" };
	
	private int screen = 0;

	private TileEntityPoweredEnergyPipe tile;
	
	public GuiPoweredEnergyPipeDirection(InventoryPlayer inventoryPlayer, TileEntityPoweredEnergyPipe tileEntity) {
		super(new ContainerPoweredEnergyPipeDirection(inventoryPlayer, tileEntity));
		this.tile = tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String s = "Energy Pipe";
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;	    
		this.fontRenderer.drawString("Energy Pipe Connections", 10, 4, 4210752);
		this.fontRenderer.drawString("Up", 14, 35, GuiColours.BLACK);
		this.fontRenderer.drawString("Down", 120, 35, GuiColours.BLACK);
	    
		this.fontRenderer.drawString("East", 9, 65, GuiColours.BLACK);
		this.fontRenderer.drawString("West", 121, 65, GuiColours.BLACK);
	    
		this.fontRenderer.drawString("North", 6, 95, GuiColours.BLACK);
		this.fontRenderer.drawString("South", 118, 95, GuiColours.BLACK);
	    
	    this.buttonList.clear();
	    
	    this.buttons = new GuiEnergyButtonCustom[] { 
	    		new GuiEnergyButtonCustom(1, k + 78, l + 14, 40, 20, display_names[0]), 
	    		new GuiEnergyButtonCustom(0, k + 32, l + 14, 40, 20, display_names[1]),
	    		new GuiEnergyButtonCustom(4, k + 32, l + 74, 40, 20, display_names[2]),
	    		new GuiEnergyButtonCustom(5, k + 78, l + 74, 40, 20, display_names[3]),
	    		new GuiEnergyButtonCustom(3, k + 78, l + 44, 40, 20, display_names[4]),
	    		new GuiEnergyButtonCustom(2, k + 32, l + 44, 40, 20, display_names[5])};
	    
	    this.addButton(buttons[0]);
	    this.addButton(buttons[1]);
	    this.addButton(buttons[2]);
	    this.addButton(buttons[3]);
	    this.addButton(buttons[4]);
	    this.addButton(buttons[5]);
	    
		this.updateSides();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		if(screen == 0){
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(Reference.GUI.RESOURCE.DIRECTION_TEXTURE);
			
			int k = (this.width - this.xSize) / 2;
			int l = (this.height - this.ySize) / 2;
			
			this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		} else {
			
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.equals(buttons[0])){
    		NetworkHandler.sendEnergyPipePacket(EnumFacing.DOWN, tile.getPos(), tile);
    	} else if(button.equals(buttons[1])){
    		NetworkHandler.sendEnergyPipePacket(EnumFacing.UP, tile.getPos(), tile);
    	} else if(button.equals(buttons[2])){
    		NetworkHandler.sendEnergyPipePacket(EnumFacing.NORTH, tile.getPos(), tile);
    	} else if(button.equals(buttons[3])){
    		NetworkHandler.sendEnergyPipePacket(EnumFacing.SOUTH, tile.getPos(), tile);
    	} else if(button.equals(buttons[4])){
    		NetworkHandler.sendEnergyPipePacket(EnumFacing.WEST, tile.getPos(), tile);
    	} else if(button.equals(buttons[5])){
    		NetworkHandler.sendEnergyPipePacket(EnumFacing.EAST, tile.getPos(), tile);
    	}
    }
	
	public void updateSides(){
		for (EnumFacing c : EnumFacing.VALUES) {
			EnumSide side = tile.getSide(c);
			
			display_names[c.getIndex()] = side.getGuiName();
			buttons[c.getIndex()].packedFGColour = side.getGuiColour();
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
