package com.zeher.orecraft.core.handler;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.machine.client.gui.*;
import com.zeher.orecraft.machine.core.container.*;
import com.zeher.orecraft.machine.core.tileentity.*;
import com.zeher.orecraft.production.client.gui.*;
import com.zeher.orecraft.production.container.*;
import com.zeher.orecraft.production.core.tileentity.*;
import com.zeher.orecraft.storage.client.gui.*;
import com.zeher.orecraft.storage.core.container.*;
import com.zeher.orecraft.storage.core.tileentity.*;
import com.zeher.orecraft.transfer.client.gui.*;
import com.zeher.orecraft.transfer.core.container.*;
import com.zeher.orecraft.transfer.core.tileentity.*;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiHandler implements IGuiHandler {
	
	public GuiHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(Orecraft.INSTANCE, this);
	}
	
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		
		if (!tile.equals(null)) {
			switch (ID) {
				case 0:
					return new ContainerPoweredCapacitor(player.inventory, (TileEntityPoweredCapacitor) tile);
				case 1:
					return new ContainerPoweredCapacitorDirection(player.inventory, (TileEntityPoweredCapacitor) tile);
				
				case 2:
					return new ContainerEnergizedCapacitor(player.inventory, (TileEntityEnergizedCapacitor) tile);
				case 3:
					return new ContainerEnergizedCapacitorDirection(player.inventory, (TileEntityEnergizedCapacitor) tile);
				
				case 8:
					return new ContainerPoweredFurnace(player.inventory, (TileEntityPoweredFurnace) tile);
				case 9:
					return new ContainerPoweredGrinder(player.inventory, (TileEntityPoweredGrinder) tile);
				case 10:
					return new ContainerPoweredCompressor(player.inventory, (TileEntityPoweredCompressor) tile);
				case 11:
					return new ContainerPoweredExtractor(player.inventory, (TileEntityPoweredExtractor) tile);
					
				case 15:
					return new ContainerEnergizedFurnace(player.inventory, (TileEntityEnergizedFurnace) tile);
				case 16:
					return new ContainerEnergizedCompressor(player.inventory, (TileEntityEnergizedCompressor) tile);
				case 17:
					return new ContainerEnergizedGrinder(player.inventory, (TileEntityEnergizedGrinder) tile);
				case 18:
					return new ContainerEnergizedExtractor(player.inventory, (TileEntityEnergizedExtractor) tile);
					
				case 23:
					return new ContainerPoweredSolidFuelGenerator(player.inventory, (TileEntityPoweredSolidFuelGenerator) tile);
				case 24:
					return new ContainerPoweredHeatedFluidGenerator(player.inventory, (TileEntityPoweredHeatedFluidGenerator) tile);
				case 25:
					return new ContainerPoweredPeltierGenerator(player.inventory, (TileEntityPoweredPeltierGenerator) tile);
				case 26:
					return new ContainerPoweredSolarPanel(player.inventory, (TileEntityPoweredSolarPanel) tile);
					
				case 37:
					return new ContainerMechanisedStorageSmall(player.inventory, (TileEntityMechanisedStorageSmall) tile);
				case 38:
					return new ContainerMechanisedStorageLarge(player.inventory, (TileEntityMechanisedStorageLarge) tile);
	
				case 41:
					return new ContainerPoweredCharger(player.inventory, (TileEntityPoweredCharger) tile);
				case 42:
					return new ContainerPoweredOreProcessor(player.inventory, (TileEntityPoweredOreProcessor) tile);
				case 43:
					return new ContainerPoweredFluidCrafter(player.inventory, (TileEntityPoweredFluidCrafter) tile);
					

				case 60:
					return new ContainerPoweredEnergyPipeDirection(player.inventory, (TileEntityPoweredEnergyPipe) tile);
			}
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	public Gui getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		
		switch (ID) {
			case 0:
				return new GuiPoweredCapacitor(player.inventory, (TileEntityPoweredCapacitor) tile);
			case 1:
				return new GuiPoweredCapacitorDirection(player.inventory, (TileEntityPoweredCapacitor) tile);
			
			case 2:
				return new GuiEnergizedCapacitor(player.inventory, (TileEntityEnergizedCapacitor) tile);
			case 3:
				return new GuiEnergizedCapacitorDirection(player.inventory, (TileEntityEnergizedCapacitor) tile);
			
			case 8:
				return new GuiPoweredFurnace(player.inventory, (TileEntityPoweredFurnace) tile);
			case 9:
				return new GuiPoweredGrinder(player.inventory, (TileEntityPoweredGrinder) tile);
			case 10:
				return new GuiPoweredCompressor(player.inventory, (TileEntityPoweredCompressor) tile);
			case 11:
				return new GuiPoweredExtractor(player.inventory, (TileEntityPoweredExtractor) tile);
				
			case 15:
				return new GuiEnergizedFurnace(player.inventory, (TileEntityEnergizedFurnace) tile);
			case 16:
				return new GuiEnergizedCompressor(player.inventory, (TileEntityEnergizedCompressor) tile);
			case 17:
				return new GuiEnergizedGrinder(player.inventory, (TileEntityEnergizedGrinder) tile);
			case 18:
				return new GuiEnergizedExtractor(player.inventory, (TileEntityEnergizedExtractor) tile);
			
			case 23:
				return new GuiPoweredSolidFuelGenerator(player.inventory, (TileEntityPoweredSolidFuelGenerator) tile);
			case 24:
				return new GuiPoweredHeatedFluidGenerator(player.inventory, (TileEntityPoweredHeatedFluidGenerator) tile);
			case 25:
				return new GuiPoweredPeltierGenerator(player.inventory, (TileEntityPoweredPeltierGenerator) tile);
			case 26:
				return new GuiPoweredSolarPanel(player.inventory, (TileEntityPoweredSolarPanel) tile);
				
			case 37:
				return new GuiMechanisedStorageSmall(player.inventory, (TileEntityMechanisedStorageSmall) tile);
			case 38:
				return new GuiMechanisedStorageLarge(player.inventory, (TileEntityMechanisedStorageLarge) tile);
	
			case 41:
				return new GuiPoweredCharger(player.inventory, (TileEntityPoweredCharger) tile);
			case 42:
				return new GuiPoweredOreProcessor(player.inventory, (TileEntityPoweredOreProcessor) tile);
			case 43:
				return new GuiPoweredFluidCrafter(player.inventory, (TileEntityPoweredFluidCrafter) tile);
				
	
			case 60:
				return new GuiPoweredEnergyPipeDirection(player.inventory, (TileEntityPoweredEnergyPipe) tile);
		}
		return null;
	}

}
