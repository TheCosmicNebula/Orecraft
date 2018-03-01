package com.zeher.orecraft.core.handlers;

import com.zeher.orecraft.machine.client.render.*;
import com.zeher.orecraft.machine.tileentity.*;
import com.zeher.orecraft.production.client.render.*;
import com.zeher.orecraft.production.tileentity.*;
import com.zeher.orecraft.storage.tileentity.*;
import com.zeher.orecraft.transfer.client.render.pipe.*;
import com.zeher.orecraft.transfer.tileentity.pipe.*;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	
	public static void preInit(){
		GameRegistry.registerTileEntity(TileEntityPoweredCapacitor.class, "powered_capacitor_tile");	
		GameRegistry.registerTileEntity(TileEntityEnergizedCapacitor.class, "energized_capacitor_tile");	
		
		GameRegistry.registerTileEntity(TileEntityFluidTank.class, "fluidtank_tile");
		
		GameRegistry.registerTileEntity(TileEntityMechanisedStorageSmall.class, "mechanisedstorage_small_tile");
		GameRegistry.registerTileEntity(TileEntityMechanisedStorageLarge.class, "mechanisedstorage_large_tile");
		
		GameRegistry.registerTileEntity(TileEntityPoweredEnergyPipe.class, "powered_energypipe_tile");
		GameRegistry.registerTileEntity(TileEntityEnergizedEnergyPipe.class, "energized_energypipe_tile");
		
		GameRegistry.registerTileEntity(TileEntityItemPipeSolid.class, "itempipe_solid_tile");
		GameRegistry.registerTileEntity(TileEntityItemPipeClear.class, "itempipe_clear_tile");

		GameRegistry.registerTileEntity(TileEntityFluidPipeSolid.class, "fluidpipe_solid_tile");
		GameRegistry.registerTileEntity(TileEntityFluidPipeClear.class, "fluidpipe_clear_tile");
		
		GameRegistry.registerTileEntity(TileEntityPoweredFurnace.class, "powered_furnace_tile");
		
		GameRegistry.registerTileEntity(TileEntityPoweredGrinder.class, "powered_grinder_tile");
		
		GameRegistry.registerTileEntity(TileEntityPoweredCompressor.class, "powered_compressor_tile");
		
		GameRegistry.registerTileEntity(TileEntityEnergizedFurnace.class, "energized_furnace_tile");
		
		GameRegistry.registerTileEntity(TileEntityEnergizedCompressor.class, "energized_compressor_tile");
		
		GameRegistry.registerTileEntity(TileEntityEnergizedGrinder.class, "energized_grinder_tile");
		
		GameRegistry.registerTileEntity(TileEntityEnergizedCombiner.class, "energized_combiner_tile");
		
		GameRegistry.registerTileEntity(TileEntityPoweredCharger.class, "powered_charger_tile");
		
		GameRegistry.registerTileEntity(TileEntityPoweredOreProcessor.class, "powered_oreprocessor_tile");
		
		GameRegistry.registerTileEntity(TileEntityPoweredFluidCrafter.class, "powered_fluidcrafter_tile");
		
		GameRegistry.registerTileEntity(TileEntityCoalGeneratorBasic.class, "coalgenerator_tile");
		GameRegistry.registerTileEntity(TileEntityHeatedFluidGeneratorBasic.class, "heatedfluidgenerator_tile");
		GameRegistry.registerTileEntity(TileEntityPeltierGeneratorBasic.class, "peltiergenerator_tile");
		
		GameRegistry.registerTileEntity(TileEntityCombinerCenter.class, "combiner_center_tile");
		GameRegistry.registerTileEntity(TileEntityCombinerPedestal.class, "combiner_pedestal_tile");
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredEnergyPipe.class, new RendererPoweredEnergyPipe());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergizedEnergyPipe.class, new RendererEnergizedEnergyPipe());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemPipeSolid.class, new RendererItemPipeSolid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemPipeClear.class, new RendererItemPipeClear());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidPipeSolid.class, new RendererFluidPipeSolid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidPipeClear.class, new RendererFluidPipeClear());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeatedFluidGeneratorBasic.class, new RendererHeatedFluidGeneratorBasic());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPeltierGeneratorBasic.class, new RendererPeltierGeneratorBasic());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredOreProcessor.class, new RendererPoweredOreProcessor());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredFluidCrafter.class, new RendererPoweredFluidCrafter());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCombinerPedestal.class, new RendererCombinerPedestal());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCombinerCenter.class, new RendererCombinerCenter());
	}

}
