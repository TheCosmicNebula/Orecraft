package com.zeher.orecraft.core.handler;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.machine.client.tesr.*;
import com.zeher.orecraft.machine.client.tesr.internal.*;
import com.zeher.orecraft.machine.core.tileentity.*;
import com.zeher.orecraft.production.client.tesr.*;
import com.zeher.orecraft.production.core.tileentity.*;
import com.zeher.orecraft.storage.core.tileentity.*;
import com.zeher.orecraft.transfer.client.tesr.*;
import com.zeher.orecraft.transfer.core.tileentity.*;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityHandler {
	
	public static void preInit(){
		registerTileEntities();
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityPoweredCapacitor.class, Orecraft.MOD_ID + ":" + "powered_capacitor_tile");
		GameRegistry.registerTileEntity(TileEntityEnergizedCapacitor.class, Orecraft.MOD_ID + ":" + "energized_capacitor_tile");
		
		GameRegistry.registerTileEntity(TileEntityFluidTank.class, Orecraft.MOD_ID + ":" + "fluidtank_tile");
		
		GameRegistry.registerTileEntity(TileEntityMechanisedStorageSmall.class, Orecraft.MOD_ID + ":" + "mechanisedstorage_small_tile");
		GameRegistry.registerTileEntity(TileEntityMechanisedStorageLarge.class, Orecraft.MOD_ID + ":" + "mechanisedstorage_large_tile");
		
		GameRegistry.registerTileEntity(TileEntityPoweredFurnace.class, Orecraft.MOD_ID + ":" + "powered_furnace_tile");
		GameRegistry.registerTileEntity(TileEntityPoweredGrinder.class, Orecraft.MOD_ID + ":" + "powered_grinder_tile");
		GameRegistry.registerTileEntity(TileEntityPoweredCompressor.class, Orecraft.MOD_ID + ":" + "powered_compressor_tile");
		GameRegistry.registerTileEntity(TileEntityPoweredExtractor.class, Orecraft.MOD_ID + ":" + "powered_extractor_tile");
		GameRegistry.registerTileEntity(TileEntityPoweredCharger.class, Orecraft.MOD_ID + ":" + "powered_charger_tile");
		
		GameRegistry.registerTileEntity(TileEntityEnergizedFurnace.class, Orecraft.MOD_ID + ":" + "energized_furnace_tile");
		GameRegistry.registerTileEntity(TileEntityEnergizedCompressor.class, Orecraft.MOD_ID + ":" + "energized_compressor_tile");
		GameRegistry.registerTileEntity(TileEntityEnergizedGrinder.class, Orecraft.MOD_ID + ":" + "energized_grinder_tile");
		GameRegistry.registerTileEntity(TileEntityEnergizedExtractor.class, Orecraft.MOD_ID + ":" + "energized_extractor_tile");
		
		GameRegistry.registerTileEntity(TileEntityPoweredOreProcessor.class, Orecraft.MOD_ID + ":" + "powered_oreprocessor_tile");
		GameRegistry.registerTileEntity(TileEntityPoweredFluidCrafter.class, Orecraft.MOD_ID + ":" + "powered_fluidcrafter_tile");
		
		GameRegistry.registerTileEntity(TileEntityPoweredSolidFuelGenerator.class, Orecraft.MOD_ID + ":" + "coalgenerator_tile");
		GameRegistry.registerTileEntity(TileEntityPoweredHeatedFluidGenerator.class, Orecraft.MOD_ID + ":" + "heatedfluidgenerator_tile");
		GameRegistry.registerTileEntity(TileEntityPoweredPeltierGenerator.class, Orecraft.MOD_ID + ":" + "peltiergenerator_tile");
		GameRegistry.registerTileEntity(TileEntityPoweredSolarPanel.class, Orecraft.MOD_ID + ":" + "solarpanel_tile");

		GameRegistry.registerTileEntity(TileEntityCombinerCenter.class, Orecraft.MOD_ID + ":" + "combiner_center_tile");
		GameRegistry.registerTileEntity(TileEntityCombinerPedestal.class, Orecraft.MOD_ID + ":" + "combiner_pedestal_tile");
	
		GameRegistry.registerTileEntity(TileEntityPoweredEnergyPipe.class, Orecraft.MOD_ID + ":" + "powered_energypipe_tile");
		GameRegistry.registerTileEntity(TileEntityEnergizedEnergyPipe.class, Orecraft.MOD_ID + ":" + "energized_energypipe_tile");
		
		GameRegistry.registerTileEntity(TileEntityItemPipeSolid.class, Orecraft.MOD_ID + ":" + "itempipe_solid_tile");
		GameRegistry.registerTileEntity(TileEntityItemPipeClear.class, Orecraft.MOD_ID + ":" + "itempipe_clear_tile");

		GameRegistry.registerTileEntity(TileEntityFluidPipeSolid.class, Orecraft.MOD_ID + ":" + "fluidpipe_solid_tile");
		GameRegistry.registerTileEntity(TileEntityFluidPipeClear.class, Orecraft.MOD_ID + ":" + "fluidpipe_clear_tile");
		
		GameRegistry.registerTileEntity(TileEntityCreativeEnergyPipe.class, Orecraft.MOD_ID + ":" + "creative_energypipe_tile");
	}
	
	@SideOnly(Side.CLIENT)
	public static void clientPreInit() {
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredFurnace.class, new RendererPoweredFurnaceInternals());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredGrinder.class, new RendererPoweredGrinderInternals());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredExtractor.class, new RendererPoweredExtractorInternals());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredCompressor.class, new RendererPoweredCompressorInternals());
	    
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredCharger.class, new RendererPoweredCharger());
	    
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergizedFurnace.class, new RendererEnergizedFurnaceInternals());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergizedGrinder.class, new RendererEnergizedGrinderInternals());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergizedCompressor.class, new RendererEnergizedCompressorInternals());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergizedExtractor.class, new RendererEnergizedExtractorInternals());
	    
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredSolidFuelGenerator.class, new RendererPoweredSolidFuelGenerator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredHeatedFluidGenerator.class, new RendererPoweredHeatedFluidGenerator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredPeltierGenerator.class, new RendererPoweredPeltierGenerator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredSolarPanel.class, new RendererPoweredSolarPanel());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredOreProcessor.class, new RendererPoweredOreProcessor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredFluidCrafter.class, new RendererPoweredFluidCrafter());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCombinerPedestal.class, new RendererCombinerPedestal());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCombinerCenter.class, new RendererCombinerCenter());
	    
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPoweredEnergyPipe.class, new RendererPoweredEnergyPipe());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergizedEnergyPipe.class, new RendererEnergizedEnergyPipe());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemPipeSolid.class, new RendererItemPipeSolid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemPipeClear.class, new RendererItemPipeClear());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidPipeSolid.class, new RendererFluidPipeSolid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidPipeClear.class, new RendererFluidPipeClear());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCreativeEnergyPipe.class, new RendererCreativeEnergyPipe());
	}
	
}
