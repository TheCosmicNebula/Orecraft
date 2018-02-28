package com.zeher.orecraft.transfer.gui.pipe;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.client.gui.GuiPowerButton;
import com.zeher.orecraft.transfer.container.pipe.ContainerPoweredEnergyPipeDirection;
import com.zeher.orecraft.transfer.tileentity.pipe.TileEntityPoweredEnergyPipe;
import com.zeher.trzlib.api.TRZGuiColours;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPoweredEnergyPipeDirection extends InventoryEffectRenderer {

	private GuiPowerButton northBtn;
	private GuiPowerButton southBtn;
	private GuiPowerButton eastBtn;
	private GuiPowerButton westBtn;
	private GuiPowerButton upBtn;
	private GuiPowerButton downBtn;
	
	private String north = "";
	private String south = "";
	private String east = "";
	private String west = "";
	private String up = "";
	private String down = "";
	
	private int screen = 0;

	private TileEntityPoweredEnergyPipe tile;
	private static final ResourceLocation texture = new ResourceLocation("orecraft:textures/gui/util/gui_direction.png");

	public GuiPoweredEnergyPipeDirection(InventoryPlayer inventoryPlayer, TileEntityPoweredEnergyPipe tileEntity) {
		super(new ContainerPoweredEnergyPipeDirection(inventoryPlayer, tileEntity));
		this.tile = tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String s = "Energy Pipe";
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;	    
		this.fontRendererObj.drawString("Energy Pipe Connections", 10, 4, 4210752);
		this.fontRendererObj.drawString("Up", 14, 35, TRZGuiColours.BLACK);
		this.fontRendererObj.drawString("Down", 120, 35, TRZGuiColours.BLACK);
	    
		this.fontRendererObj.drawString("East", 9, 65, TRZGuiColours.BLACK);
		this.fontRendererObj.drawString("West", 121, 65, TRZGuiColours.BLACK);
	    
		this.fontRendererObj.drawString("North", 6, 95, TRZGuiColours.BLACK);
		this.fontRendererObj.drawString("South", 118, 95, TRZGuiColours.BLACK);
	    
	    this.buttonList.clear();
		this.upBtn = this.addButton(new GuiPowerButton(0, k + 32, l + 14, 40, 20, up));
		this.downBtn = this.addButton(new GuiPowerButton(0, k + 78, l + 14, 40, 20, down));
		
		this.eastBtn = this.addButton(new GuiPowerButton(0, k + 32, l + 44, 40, 20, east));
		this.westBtn = this.addButton(new GuiPowerButton(0, k + 78, l + 44, 40, 20, west));
		
		this.northBtn = this.addButton(new GuiPowerButton(0, k + 32, l + 74, 40, 20, north));
		this.southBtn = this.addButton(new GuiPowerButton(0, k + 78, l + 74, 40, 20, south));
		
		updateSides();
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		if(screen == 0){
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(texture);
			int k = (this.width - this.xSize) / 2;
			int l = (this.height - this.ySize) / 2;
			drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		} else {
			
		}
	}

	protected void actionPerformed(GuiButton button) throws IOException
    {
    	if(button == upBtn){
    		int i = tile.getSide("up");
			i = i+1;
			if(i > 2){
				i = 0;
			}
			tile.setSide("up", i);
    	}
    	
    	if(button == downBtn){
    		int i = tile.getSide("down");
			i = i+1;
			if(i > 2){
				i = 0;
			}
			tile.setSide("down", i);
    	}
        
    	if(button == eastBtn){
    		int i = tile.getSide("east");
			i = i+1;
			if(i > 2){
				i = 0;
			}
			tile.setSide("east", i);
    	}
    	
    	if(button == westBtn){
    		int i = tile.getSide("west");
			i = i+1;
			if(i > 2){
				i = 0;
			}
			tile.setSide("west", i);
    	}
    	
    	if(button == northBtn){
    		int i = tile.getSide("north");
			i = i+1;
			if(i > 2){
				i = 0;
			}
			tile.setSide("north", i);
    	}
    	
    	if(button == southBtn){
    		int i = tile.getSide("south");
			i = i+1;
			if(i > 2){
				i = 0;
			}
			tile.setSide("south", i);
    	}
    }
	
	public void updateSides(){
		if((tile.getSide("up") == 0) && !(tile.getSide("up") == 1) && !(tile.getSide("up") == 2)){
			up = "Normal";
			this.upBtn.packedFGColour = 0x2ECCFA;
		}
		if((tile.getSide("up") == 1) && !(tile.getSide("up") == 2) && !(tile.getSide("up") == 0)){
			up = "None";
			this.upBtn.packedFGColour = 0xFF0000;
		}
		if((tile.getSide("up") == 2) && !(tile.getSide("up") == 1) && !(tile.getSide("up") == 0)){
			up = "Forced";
			this.upBtn.packedFGColour = 0x00FF00;
		}
		
		if((tile.getSide("down") == 0) && !(tile.getSide("down") == 1) && !(tile.getSide("down") == 2)){
			down = "Normal";
			this.downBtn.packedFGColour = 0x2ECCFA;
		}
		if((tile.getSide("down") == 1) && !(tile.getSide("down") == 2) && !(tile.getSide("down") == 0)){
			down = "None";
			this.downBtn.packedFGColour = 0xFF0000;
		}
		if((tile.getSide("down") == 2) && !(tile.getSide("down") == 1) && !(tile.getSide("down") == 0)){
			down = "Forced";
			this.downBtn.packedFGColour = 0x00FF00;
		}
		
		if((tile.getSide("east") == 0) && !(tile.getSide("east") == 1) && !(tile.getSide("east") == 2)){
			east = "Normal";
			this.eastBtn.packedFGColour = 0x2ECCFA;
		}
		if((tile.getSide("east") == 1) && !(tile.getSide("east") == 2) && !(tile.getSide("east") == 0)){
			east = "None";
			this.eastBtn.packedFGColour = 0xFF0000;
		}
		if((tile.getSide("east") == 2) && !(tile.getSide("east") == 1) && !(tile.getSide("east") == 0)){
			east = "Forced";
			this.eastBtn.packedFGColour = 0x00FF00;
		}
		
		if((tile.getSide("west") == 0) && !(tile.getSide("west") == 1) && !(tile.getSide("west") == 2)){
			west = "Normal";
			this.westBtn.packedFGColour = 0x2ECCFA;
		}
		if((tile.getSide("west") == 1) && !(tile.getSide("west") == 2) && !(tile.getSide("west") == 0)){
			west = "None";
			this.westBtn.packedFGColour = 0xFF0000;
		}
		if((tile.getSide("west") == 2) && !(tile.getSide("west") == 1) && !(tile.getSide("west") == 0)){
			west = "Forced";
			this.westBtn.packedFGColour = 0x00FF00;
		}
		
		if((tile.getSide("north") == 0) && !(tile.getSide("north") == 1) && !(tile.getSide("north") == 2)){
			north = "Normal";
			this.northBtn.packedFGColour = 0x2ECCFA;
		}
		if((tile.getSide("north") == 1) && !(tile.getSide("north") == 2) && !(tile.getSide("north") == 0)){
			north = "None";
			this.northBtn.packedFGColour = 0xFF0000;
		}
		if((tile.getSide("north") == 2) && !(tile.getSide("north") == 1) && !(tile.getSide("north") == 0)){
			north = "Forced";
			this.northBtn.packedFGColour = 0x00FF00;
		}
		
		if((tile.getSide("south") == 0) && !(tile.getSide("south") == 1) && !(tile.getSide("south") == 2)){
			south = "Normal";
			this.southBtn.packedFGColour = 0x2ECCFA;
		}
		if((tile.getSide("south") == 1) && !(tile.getSide("south") == 2) && !(tile.getSide("south") == 0)){
			south = "None";
			this.southBtn.packedFGColour = 0xFF0000;
		}
		if((tile.getSide("south") == 2) && !(tile.getSide("south") == 1) && !(tile.getSide("south") == 0)){
			south = "Forced";
			this.southBtn.packedFGColour = 0x00FF00;
		}
	}
	
}
