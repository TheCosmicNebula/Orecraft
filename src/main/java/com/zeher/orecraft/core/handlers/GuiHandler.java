package com.zeher.orecraft.core.handlers;

import com.zeher.orecraft.OreCraft;
import com.zeher.orecraft.machine.container.ContainerEnergizedCombiner;
import com.zeher.orecraft.machine.container.ContainerEnergizedCompressor;
import com.zeher.orecraft.machine.container.ContainerEnergizedFurnace;
import com.zeher.orecraft.machine.container.ContainerEnergizedGrinder;
import com.zeher.orecraft.machine.container.ContainerPoweredCharger;
import com.zeher.orecraft.machine.container.ContainerPoweredCompressor;
import com.zeher.orecraft.machine.container.ContainerPoweredFluidCrafter;
import com.zeher.orecraft.machine.container.ContainerPoweredFurnace;
import com.zeher.orecraft.machine.container.ContainerPoweredGrinder;
import com.zeher.orecraft.machine.container.ContainerPoweredOreProcessor;
import com.zeher.orecraft.machine.gui.GuiEnergizedCombiner;
import com.zeher.orecraft.machine.gui.GuiEnergizedCompressor;
import com.zeher.orecraft.machine.gui.GuiEnergizedFurnace;
import com.zeher.orecraft.machine.gui.GuiEnergizedGrinder;
import com.zeher.orecraft.machine.gui.GuiPoweredCharger;
import com.zeher.orecraft.machine.gui.GuiPoweredCompressor;
import com.zeher.orecraft.machine.gui.GuiPoweredFluidCrafter;
import com.zeher.orecraft.machine.gui.GuiPoweredFurnace;
import com.zeher.orecraft.machine.gui.GuiPoweredGrinder;
import com.zeher.orecraft.machine.gui.GuiPoweredOreProcessor;
import com.zeher.orecraft.machine.tileentity.TileEntityEnergizedCombiner;
import com.zeher.orecraft.machine.tileentity.TileEntityEnergizedCompressor;
import com.zeher.orecraft.machine.tileentity.TileEntityEnergizedFurnace;
import com.zeher.orecraft.machine.tileentity.TileEntityEnergizedGrinder;
import com.zeher.orecraft.machine.tileentity.TileEntityPoweredCharger;
import com.zeher.orecraft.machine.tileentity.TileEntityPoweredCompressor;
import com.zeher.orecraft.machine.tileentity.TileEntityPoweredFluidCrafter;
import com.zeher.orecraft.machine.tileentity.TileEntityPoweredFurnace;
import com.zeher.orecraft.machine.tileentity.TileEntityPoweredGrinder;
import com.zeher.orecraft.machine.tileentity.TileEntityPoweredOreProcessor;
import com.zeher.orecraft.production.container.ContainerCoalGeneratorBasic;
import com.zeher.orecraft.production.container.ContainerHeatedFluidGeneratorBasic;
import com.zeher.orecraft.production.container.ContainerPeltierGeneratorBasic;
import com.zeher.orecraft.production.gui.GuiCoalGeneratorBasic;
import com.zeher.orecraft.production.gui.GuiHeatedFluidGeneratorBasic;
import com.zeher.orecraft.production.gui.GuiPeltierGeneratorBasic;
import com.zeher.orecraft.production.tileentity.TileEntityCoalGeneratorBasic;
import com.zeher.orecraft.production.tileentity.TileEntityHeatedFluidGeneratorBasic;
import com.zeher.orecraft.production.tileentity.TileEntityPeltierGeneratorBasic;
import com.zeher.orecraft.storage.container.ContainerEnergizedCapacitor;
import com.zeher.orecraft.storage.container.ContainerEnergizedCapacitorDirection;
import com.zeher.orecraft.storage.container.ContainerMechanisedStorageLarge;
import com.zeher.orecraft.storage.container.ContainerMechanisedStorageSmall;
import com.zeher.orecraft.storage.container.ContainerPoweredCapacitor;
import com.zeher.orecraft.storage.container.ContainerPoweredCapacitorDirection;
import com.zeher.orecraft.storage.gui.GuiEnergizedCapacitor;
import com.zeher.orecraft.storage.gui.GuiEnergizedCapacitorDirection;
import com.zeher.orecraft.storage.gui.GuiMechanisedStorageLarge;
import com.zeher.orecraft.storage.gui.GuiMechanisedStorageSmall;
import com.zeher.orecraft.storage.gui.GuiPoweredCapacitor;
import com.zeher.orecraft.storage.gui.GuiPoweredCapacitorDirection;
import com.zeher.orecraft.storage.tileentity.TileEntityEnergizedCapacitor;
import com.zeher.orecraft.storage.tileentity.TileEntityMechanisedStorageLarge;
import com.zeher.orecraft.storage.tileentity.TileEntityMechanisedStorageSmall;
import com.zeher.orecraft.storage.tileentity.TileEntityPoweredCapacitor;
import com.zeher.orecraft.transfer.container.pipe.ContainerPoweredEnergyPipeDirection;
import com.zeher.orecraft.transfer.gui.pipe.GuiPoweredEnergyPipeDirection;
import com.zeher.orecraft.transfer.tileentity.pipe.TileEntityPoweredEnergyPipe;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {

	public GuiHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(OreCraft.instance, this);
	}

	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		if (!tile.equals(null)) {
			switch (ID) {
			case 0:
				return new ContainerPoweredCapacitorDirection(player.inventory, (TileEntityPoweredCapacitor) tile);
			case 1:
				return new ContainerPoweredCapacitor(player.inventory, (TileEntityPoweredCapacitor) tile);
			
			case 2:
				return new ContainerEnergizedCapacitorDirection(player.inventory, (TileEntityEnergizedCapacitor) tile);
			case 3:
				return new ContainerEnergizedCapacitor(player.inventory, (TileEntityEnergizedCapacitor) tile);
			
			case 8:
				return new ContainerPoweredEnergyPipeDirection(player.inventory, (TileEntityPoweredEnergyPipe) tile);

			case 12:
				return new ContainerPoweredFurnace(player.inventory, (TileEntityPoweredFurnace) tile);

			case 16:
				return new ContainerPoweredGrinder(player.inventory, (TileEntityPoweredGrinder) tile);

			case 20:
				return new ContainerPoweredCompressor(player.inventory, (TileEntityPoweredCompressor) tile);

			case 29:
				return new ContainerEnergizedFurnace(player.inventory, (TileEntityEnergizedFurnace) tile);

			case 33:
				return new ContainerEnergizedCompressor(player.inventory, (TileEntityEnergizedCompressor) tile);

			case 37:
				return new ContainerEnergizedGrinder(player.inventory, (TileEntityEnergizedGrinder) tile);

			case 41:
				return new ContainerEnergizedCombiner(player.inventory, (TileEntityEnergizedCombiner) tile);

			case 44:
				return new ContainerCoalGeneratorBasic(player.inventory, (TileEntityCoalGeneratorBasic) tile);

			case 48:
				return new ContainerHeatedFluidGeneratorBasic(player.inventory, (TileEntityHeatedFluidGeneratorBasic) tile);

			case 52:
				return new ContainerPeltierGeneratorBasic(player.inventory, (TileEntityPeltierGeneratorBasic) tile);

			case 56:
				return new ContainerMechanisedStorageSmall(player.inventory, (TileEntityMechanisedStorageSmall) tile);
			case 58:
				return new ContainerMechanisedStorageLarge(player.inventory, (TileEntityMechanisedStorageLarge) tile);

			case 60:
				return new ContainerPoweredCharger(player.inventory, (TileEntityPoweredCharger) tile);

			case 61:
				return new ContainerPoweredOreProcessor(player.inventory, (TileEntityPoweredOreProcessor) tile);

			case 63:
				return new ContainerPoweredFluidCrafter(player.inventory, (TileEntityPoweredFluidCrafter) tile);
			}
		}
		return null;
	}

	public Gui getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		switch (ID) {

		case 0:
			return new GuiPoweredCapacitorDirection(player.inventory, (TileEntityPoweredCapacitor) tile, world);
		case 1:
			return new GuiPoweredCapacitor(player.inventory, (TileEntityPoweredCapacitor) tile);
		
		case 2:
			return new GuiEnergizedCapacitorDirection(player.inventory, (TileEntityEnergizedCapacitor) tile, world);
		case 3:
			return new GuiEnergizedCapacitor(player.inventory, (TileEntityEnergizedCapacitor) tile);

		case 8:
			return new GuiPoweredEnergyPipeDirection(player.inventory, (TileEntityPoweredEnergyPipe) tile);

		case 12:
			return new GuiPoweredFurnace(player.inventory, (TileEntityPoweredFurnace) tile);

		case 16:
			return new GuiPoweredGrinder(player.inventory, (TileEntityPoweredGrinder) tile);

		case 20:
			return new GuiPoweredCompressor(player.inventory, (TileEntityPoweredCompressor) tile);

		case 29:
			return new GuiEnergizedFurnace(player.inventory, (TileEntityEnergizedFurnace) tile);

		case 33:
			return new GuiEnergizedCompressor(player.inventory, (TileEntityEnergizedCompressor) tile);

		case 37:
			return new GuiEnergizedGrinder(player.inventory, (TileEntityEnergizedGrinder) tile);

		case 41:
			return new GuiEnergizedCombiner(player.inventory, (TileEntityEnergizedCombiner) tile);

		case 44:
			return new GuiCoalGeneratorBasic(player.inventory, (TileEntityCoalGeneratorBasic) tile);

		case 48:
			return new GuiHeatedFluidGeneratorBasic(player.inventory, (TileEntityHeatedFluidGeneratorBasic) tile);

		case 52:
			return new GuiPeltierGeneratorBasic(player.inventory, (TileEntityPeltierGeneratorBasic) tile);

		case 56:
			return new GuiMechanisedStorageSmall(player.inventory, (TileEntityMechanisedStorageSmall) tile);
		case 58:
			return new GuiMechanisedStorageLarge(player.inventory, (TileEntityMechanisedStorageLarge) tile);

		case 60:
			return new GuiPoweredCharger(player.inventory, (TileEntityPoweredCharger) tile);

		case 61:
			return new GuiPoweredOreProcessor(player.inventory, (TileEntityPoweredOreProcessor) tile);

		case 63:
			return new GuiPoweredFluidCrafter(player.inventory, (TileEntityPoweredFluidCrafter) tile);
		}
		return null;
	}

}
