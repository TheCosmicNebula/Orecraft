package com.zeher.orecraft.machine.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.zeher.orecraft.machine.container.ContainerPoweredCharger;
import com.zeher.orecraft.machine.tileentity.TileEntityPoweredCharger;
import com.zeher.trzlib.api.TRZTextUtil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

@SideOnly(Side.CLIENT)
public class GuiPoweredCharger extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation( "orecraft:textures/gui/machine/powered/charger/gui_charger.png");
	private TileEntityPoweredCharger inventory;

	public GuiPoweredCharger(InventoryPlayer par1InventoryPlayer,
			TileEntityPoweredCharger par2TileEntityCharger) {
		super(new ContainerPoweredCharger(par1InventoryPlayer, par2TileEntityCharger));
		this.inventory = par2TileEntityCharger;
		this.xSize = 195;
		this.ySize = 168;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.inventory.hasCustomName() ? this.inventory.getName() : I18n.format(this.inventory.getName());
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s), 3, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		
		if (mouseX > k + 29 && mouseX < k + 47) {
			if (mouseY > l + 11 && mouseY < l + 72) {
				String[] desc = {TRZTextUtil.GREEN + "Power: " + TRZTextUtil.LIGHT_GRAY + this.inventory.stored};
				List temp = Arrays.asList(desc);
				this.drawHoveringText(temp, mouseX - k, mouseY - l, fontRendererObj);
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
			int i1 = this.inventory.getStoredRemainingScaled(62);
			drawTexturedModalRect(k + 29, l + 72 - i1, 195, 61 - i1, 18, i1);
		}
	}
}
