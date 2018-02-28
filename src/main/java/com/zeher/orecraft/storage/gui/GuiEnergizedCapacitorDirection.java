package com.zeher.orecraft.storage.gui;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.client.gui.GuiPowerButton;
import com.zeher.orecraft.core.handlers.NetworkHandler;
import com.zeher.orecraft.storage.container.ContainerEnergizedCapacitorDirection;
import com.zeher.orecraft.storage.tileentity.TileEntityEnergizedCapacitor;
import com.zeher.trzlib.api.TRZGuiColours;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiEnergizedCapacitorDirection extends GuiContainer {

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
	
	private World world;

	private TileEntityEnergizedCapacitor tile;
	private static final ResourceLocation texture = new ResourceLocation("orecraft:textures/gui/util/gui_direction.png");

	public GuiEnergizedCapacitorDirection(InventoryPlayer inventoryPlayer, TileEntityEnergizedCapacitor tileEntity, World world) {
		super(new ContainerEnergizedCapacitorDirection(inventoryPlayer, tileEntity));
		this.tile = tileEntity;
		this.world = world;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;	    
		this.fontRendererObj.drawString("Capacitor Connections", 10, 4, 4210752);
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
	
	protected void actionPerformed(GuiButton button)
    {
    	if(button == upBtn){
    		NetworkHandler.sendCapacitorPacket("up", tile.getPos(), tile);
    	}
    	
    	if(button == downBtn){
    		NetworkHandler.sendCapacitorPacket("down", tile.getPos(), tile);
    	}
        
    	if(button == eastBtn){
    		NetworkHandler.sendCapacitorPacket("east", tile.getPos(), tile);
    	}
    	
    	if(button == westBtn){
    		NetworkHandler.sendCapacitorPacket("west", tile.getPos(), tile);
    	}
    	
    	if(button == northBtn){
    		NetworkHandler.sendCapacitorPacket("north", tile.getPos(), tile);
    	}
    	
    	if(button == southBtn){
    		NetworkHandler.sendCapacitorPacket("south", tile.getPos(), tile);
    	}
    }
	
	public void updateSides(){
		if((tile.getSide("up") == 0) && !(tile.getSide("up") == 1) && !(tile.getSide("up") == 2)){
			up = "Input";
			this.upBtn.packedFGColour = TRZGuiColours.CYAN;
		}
		if((tile.getSide("up") == 1) && !(tile.getSide("up") == 2) && !(tile.getSide("up") == 0)){
			up = "None";
			this.upBtn.packedFGColour = TRZGuiColours.RED;
		}
		if((tile.getSide("up") == 2) && !(tile.getSide("up") == 1) && !(tile.getSide("up") == 0)){
			up = "Output";
			this.upBtn.packedFGColour = TRZGuiColours.GREEN;
		}
		
		if((tile.getSide("down") == 0) && !(tile.getSide("down") == 1) && !(tile.getSide("down") == 2)){
			down = "Input";
			this.downBtn.packedFGColour = TRZGuiColours.CYAN;
		}
		if((tile.getSide("down") == 1) && !(tile.getSide("down") == 2) && !(tile.getSide("down") == 0)){
			down = "None";
			this.downBtn.packedFGColour = TRZGuiColours.RED;
		}
		if((tile.getSide("down") == 2) && !(tile.getSide("down") == 1) && !(tile.getSide("down") == 0)){
			down = "Output";
			this.downBtn.packedFGColour = TRZGuiColours.GREEN;
		}
		
		if((tile.getSide("east") == 0) && !(tile.getSide("east") == 1) && !(tile.getSide("east") == 2)){
			east = "Input";
			this.eastBtn.packedFGColour = TRZGuiColours.CYAN;
		}
		if((tile.getSide("east") == 1) && !(tile.getSide("east") == 2) && !(tile.getSide("east") == 0)){
			east = "None";
			this.eastBtn.packedFGColour = TRZGuiColours.RED;
		}
		if((tile.getSide("east") == 2) && !(tile.getSide("east") == 1) && !(tile.getSide("east") == 0)){
			east = "Output";
			this.eastBtn.packedFGColour = TRZGuiColours.GREEN;
		}
		
		if((tile.getSide("west") == 0) && !(tile.getSide("west") == 1) && !(tile.getSide("west") == 2)){
			west = "Input";
			this.westBtn.packedFGColour = TRZGuiColours.CYAN;
		}
		if((tile.getSide("west") == 1) && !(tile.getSide("west") == 2) && !(tile.getSide("west") == 0)){
			west = "None";
			this.westBtn.packedFGColour = TRZGuiColours.RED;
		}
		if((tile.getSide("west") == 2) && !(tile.getSide("west") == 1) && !(tile.getSide("west") == 0)){
			west = "Output";
			this.westBtn.packedFGColour = TRZGuiColours.GREEN;
		}
		
		if((tile.getSide("north") == 0) && !(tile.getSide("north") == 1) && !(tile.getSide("north") == 2)){
			north = "Input";
			this.northBtn.packedFGColour = TRZGuiColours.CYAN;
		}
		if((tile.getSide("north") == 1) && !(tile.getSide("north") == 2) && !(tile.getSide("north") == 0)){
			north = "None";
			this.northBtn.packedFGColour = TRZGuiColours.RED;
		}
		if((tile.getSide("north") == 2) && !(tile.getSide("north") == 1) && !(tile.getSide("north") == 0)){
			north = "Output";
			this.northBtn.packedFGColour = TRZGuiColours.GREEN;
		}
		
		if((tile.getSide("south") == 0) && !(tile.getSide("south") == 1) && !(tile.getSide("south") == 2)){
			south = "Input";
			this.southBtn.packedFGColour = TRZGuiColours.CYAN;
		}
		if((tile.getSide("south") == 1) && !(tile.getSide("south") == 2) && !(tile.getSide("south") == 0)){
			south = "None";
			this.southBtn.packedFGColour = TRZGuiColours.RED;
		}
		if((tile.getSide("south") == 2) && !(tile.getSide("south") == 1) && !(tile.getSide("south") == 0)){
			south = "Output";
			this.southBtn.packedFGColour = TRZGuiColours.GREEN;
		}
	}
	
}
