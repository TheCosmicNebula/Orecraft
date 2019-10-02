package com.zeher.orecraft.core.handler;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.core.decor.block.BlockConnectedGlass;
import com.zeher.orecraft.core.decor.block.BlockPlanks;
import com.zeher.orecraft.core.decor.block.ItemBlockVariants;
import com.zeher.orecraft.core.decor.block.ModBlockLog;
import com.zeher.orecraft.machine.core.block.BlockCombinerCenter;
import com.zeher.orecraft.machine.core.block.BlockCombinerPedestal;
import com.zeher.orecraft.machine.core.block.BlockEnergizedCompressor;
import com.zeher.orecraft.machine.core.block.BlockEnergizedExtractor;
import com.zeher.orecraft.machine.core.block.BlockEnergizedFurnace;
import com.zeher.orecraft.machine.core.block.BlockEnergizedGrinder;
import com.zeher.orecraft.machine.core.block.BlockPoweredCharger;
import com.zeher.orecraft.machine.core.block.BlockPoweredCompressor;
import com.zeher.orecraft.machine.core.block.BlockPoweredExtractor;
import com.zeher.orecraft.machine.core.block.BlockPoweredFluidCrafter;
import com.zeher.orecraft.machine.core.block.BlockPoweredFurnace;
import com.zeher.orecraft.machine.core.block.BlockPoweredGrinder;
import com.zeher.orecraft.machine.core.block.BlockPoweredOreProcessor;
import com.zeher.orecraft.multiblock.core.block.BlockProcessingPlantBase;
import com.zeher.orecraft.production.core.block.BlockPoweredHeatedFluidGenerator;
import com.zeher.orecraft.production.core.block.BlockPoweredPeltierGenerator;
import com.zeher.orecraft.production.core.block.BlockPoweredSolarPanel;
import com.zeher.orecraft.production.core.block.BlockPoweredSolidFuelGenerator;
import com.zeher.orecraft.storage.core.block.BlockEnergizedCapacitor;
import com.zeher.orecraft.storage.core.block.BlockFluidTank;
import com.zeher.orecraft.storage.core.block.BlockMechanisedStorageLarge;
import com.zeher.orecraft.storage.core.block.BlockMechanisedStorageSmall;
import com.zeher.orecraft.storage.core.block.BlockPoweredCapacitor;
import com.zeher.orecraft.storage.core.block.ItemBlockEnergizedCapacitor;
import com.zeher.orecraft.storage.core.block.ItemBlockFluidTank;
import com.zeher.orecraft.storage.core.block.ItemBlockPoweredCapacitor;
import com.zeher.orecraft.transfer.core.block.BlockCreativeEnergyPipe;
import com.zeher.orecraft.transfer.core.block.BlockEnergizedEnergyPipe;
import com.zeher.orecraft.transfer.core.block.BlockFluidPipeClear;
import com.zeher.orecraft.transfer.core.block.BlockFluidPipeSolid;
import com.zeher.orecraft.transfer.core.block.BlockItemPipeClear;
import com.zeher.orecraft.transfer.core.block.BlockItemPipeSolid;
import com.zeher.orecraft.transfer.core.block.BlockPoweredEnergyPipe;
import com.zeher.zeherlib.api.interfaces.*;
import com.zeher.zeherlib.core.block.*;
import com.zeher.zeherlib.fluid.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
 
@Mod.EventBusSubscriber(modid = Orecraft.MOD_ID)
public class BlockHandler {
	
	@EventBusSubscriber(modid = Orecraft.MOD_ID)
	public static class REGISTRATION {
		
		//TODO STORAGE
		@EventBusSubscriber(modid = Orecraft.MOD_ID)
		public static class STORAGE {

			private static ItemBlock[] BLOCK_ITEMS;
			
			public static final Block BLOCK_POWERED_CAPACITOR = new BlockPoweredCapacitor("block_powered_capacitor", Material.IRON, "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			public static final ItemBlock ITEMBLOCK_POWERED_CAPACITOR = new ItemBlockPoweredCapacitor(BLOCK_POWERED_CAPACITOR);
			public static final Block BLOCK_ENERGIZED_CAPACITOR = new BlockEnergizedCapacitor("block_energized_capacitor", Material.IRON, "pickaxe", 3, 8, 4, Orecraft.TAB_ENERGY);
			public static final ItemBlock ITEMBLOCK_ENERGIZED_CAPACITOR = new ItemBlockEnergizedCapacitor(BLOCK_ENERGIZED_CAPACITOR);

			public static final Block BLOCK_FLUIDTANK = new BlockFluidTank("block_fluidtank", Material.GLASS, "pickaxe", 2, 3, 10, Orecraft.TAB_ENERGY);
			public static final ItemBlock ITEMBLOCK_FLUIDTANK = new ItemBlockFluidTank(BLOCK_FLUIDTANK);

			public static final Block BLOCK_MECHANISEDSTORAGE_SMALL = new BlockMechanisedStorageSmall("block_mechanised_storage_small", Material.IRON, "pickaxe", 2, 8, 10, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_MECHANISEDSTORAGE_LARGE = new BlockMechanisedStorageLarge("block_mechanised_storage_large", Material.IRON, "pickaxe", 3, 10, 12, Orecraft.TAB_ENERGY);
			
			@SubscribeEvent
			public static void register(RegistryEvent.Register<Block> event) {
				registerStorageBlocks(event,
					BLOCK_MECHANISEDSTORAGE_SMALL,
					BLOCK_MECHANISEDSTORAGE_LARGE);
				
				event.getRegistry().register(BLOCK_POWERED_CAPACITOR);
				event.getRegistry().register(BLOCK_ENERGIZED_CAPACITOR);
				event.getRegistry().register(BLOCK_FLUIDTANK);
			}
			
			@SubscribeEvent
			public static void registerBlockModelLocations(final ModelRegistryEvent event) {
				registerModelLocations(event,
						BLOCK_POWERED_CAPACITOR, BLOCK_ENERGIZED_CAPACITOR,
						BLOCK_FLUIDTANK,
						
						BLOCK_MECHANISEDSTORAGE_SMALL,
						BLOCK_MECHANISEDSTORAGE_LARGE);
			}
			
			private static void registerStorageBlocks(RegistryEvent.Register<Block> event, Block... blocks) {
				BLOCK_ITEMS = new ItemBlock[blocks.length];
				event.getRegistry().registerAll(blocks);
				for (int i = 0; i < blocks.length; i++) {
					BLOCK_ITEMS[i] = new ItemBlock(blocks[i]);
					BLOCK_ITEMS[i].setRegistryName(blocks[i].getRegistryName());
				}
			}
			
			@SubscribeEvent
			public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
				event.getRegistry().register(ITEMBLOCK_POWERED_CAPACITOR.setRegistryName(BLOCK_POWERED_CAPACITOR.getRegistryName()));
				event.getRegistry().register(ITEMBLOCK_ENERGIZED_CAPACITOR.setRegistryName(BLOCK_ENERGIZED_CAPACITOR.getRegistryName()));
				event.getRegistry().register(ITEMBLOCK_FLUIDTANK.setRegistryName(BLOCK_FLUIDTANK.getRegistryName()));
				
				event.getRegistry().registerAll(BLOCK_ITEMS);
			}
		}
		
		//TODO PRODUCER
		@EventBusSubscriber(modid = Orecraft.MOD_ID)
		public static class PRODUCER {

			private static ItemBlock[] BLOCK_ITEMS;
			
			
			public static final Block BLOCK_POWERED_SOLIDFUEL_GENERATOR = new BlockPoweredSolidFuelGenerator("block_powered_solidfuel_generator", Material.IRON, "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_POWERED_SOLARPANEL = new BlockPoweredSolarPanel("block_powered_solarpanel", Material.IRON, "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			
			@SubscribeEvent
			public static void register(RegistryEvent.Register<Block> event) {
				registerProducerBlocks(event, 
					BLOCK_POWERED_SOLIDFUEL_GENERATOR,
					BLOCK_POWERED_SOLARPANEL);
			}
			
			@SubscribeEvent
			public static void registerBlockModelLocations(final ModelRegistryEvent event) {
				registerModelLocations(event,
						BLOCK_POWERED_SOLIDFUEL_GENERATOR,
						BLOCK_POWERED_SOLARPANEL);
			}
			
			private static void registerProducerBlocks(RegistryEvent.Register<Block> event, Block... blocks) {
				BLOCK_ITEMS = new ItemBlock[blocks.length];
				event.getRegistry().registerAll(blocks);
				for (int i = 0; i < blocks.length; i++) {
					BLOCK_ITEMS[i] = new ItemBlock(blocks[i]);
					BLOCK_ITEMS[i].setRegistryName(blocks[i].getRegistryName());
				}
			}
			
			@SubscribeEvent
			public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
				event.getRegistry().registerAll(BLOCK_ITEMS);
			}
		}
		
		//TODO FLUIDPRODUCER
		@EventBusSubscriber(modid = Orecraft.MOD_ID)
		public static class FLUIDPRODUCER {

			private static ItemBlock[] BLOCK_ITEMS;
			
			
			public static final Block BLOCK_POWERED_HEATEDFLUID_GENERATOR = new BlockPoweredHeatedFluidGenerator("block_powered_heatedfluid_generator", Material.IRON, "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_POWERED_PELTIER_GENERATOR = new BlockPoweredPeltierGenerator("block_powered_peltier_generator", Material.IRON, "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			
			@SubscribeEvent
			public static void register(RegistryEvent.Register<Block> event) {
				registerFluidProducerBlocks(event, 
					BLOCK_POWERED_HEATEDFLUID_GENERATOR,
					BLOCK_POWERED_PELTIER_GENERATOR);
			}
			
			@SubscribeEvent
			public static void registerBlockModelLocations(final ModelRegistryEvent event) {
				registerModelLocations(event,
						BLOCK_POWERED_HEATEDFLUID_GENERATOR,
						BLOCK_POWERED_PELTIER_GENERATOR);
			}
			
			private static void registerFluidProducerBlocks(RegistryEvent.Register<Block> event, Block... blocks) {
				BLOCK_ITEMS = new ItemBlock[blocks.length];
				event.getRegistry().registerAll(blocks);
				for (int i = 0; i < blocks.length; i++) {
					BLOCK_ITEMS[i] = new ItemBlock(blocks[i]);
					BLOCK_ITEMS[i].setRegistryName(blocks[i].getRegistryName());
				}
			}
			
			@SubscribeEvent
			public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
				event.getRegistry().registerAll(BLOCK_ITEMS);
			}
		}
		
		//TODO MACHINE
		@EventBusSubscriber(modid = Orecraft.MOD_ID)
		public static class MACHINE {

			private static ItemBlock[] BLOCK_ITEMS;
			
			public static final Block BLOCK_POWERED_FRAME = new ModBlock("block_powered_frame", Material.IRON, "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_ENERGIZED_FRAME = new ModBlock("block_energized_frame", Material.IRON, "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);

			public static final Block BLOCK_POWERED_FURNACE = new BlockPoweredFurnace(Material.IRON, "block_powered_furnace","pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_POWERED_GRINDER = new BlockPoweredGrinder(Material.IRON, "block_powered_grinder", "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_POWERED_COMPRESSOR = new BlockPoweredCompressor(Material.IRON, "block_powered_compressor", "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_POWERED_EXTRACTOR = new BlockPoweredExtractor(Material.IRON, "block_powered_extractor", "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_POWERED_CHARGER = new BlockPoweredCharger(Material.IRON, "block_powered_charger", "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);

			public static final Block BLOCK_ENERGIZED_FURNACE = new BlockEnergizedFurnace(Material.IRON, "block_energized_furnace", "pickaxe", 3, 8, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_ENERGIZED_GRINDER = new BlockEnergizedGrinder(Material.IRON, "block_energized_grinder", "pickaxe", 3, 8, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_ENERGIZED_COMPRESSOR = new BlockEnergizedCompressor(Material.IRON, "block_energized_compressor", "pickaxe", 3, 8, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_ENERGIZED_EXTRACTOR = new BlockEnergizedExtractor(Material.IRON, "block_energized_extractor", "pickaxe", 3, 8, 4, Orecraft.TAB_ENERGY);
			
			public static final Block BLOCK_COMBINER_CENTER = new BlockCombinerCenter("block_combiner_center", Material.IRON, "pickaxe", 3, 8, 4, Orecraft.TAB_ENERGY, false);
			public static final Block BLOCK_COMBINER_PEDESTAL = new BlockCombinerPedestal("block_combiner_pedestal", Material.IRON, "pickaxe", 3, 8, 4, Orecraft.TAB_ENERGY, false);
			
			public static final Block BLOCK_PROCESSINGPLANT_BASE = new BlockProcessingPlantBase(Material.IRON, "block_processingplant_base", "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY, false);
			public static final Block BLOCK_PROCESSINGPLANT_BASE_MULTI = new BlockProcessingPlantBase(Material.IRON, "block_processingplant_base_multi", "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY, true);

			@SubscribeEvent
			public static void register(RegistryEvent.Register<Block> event) {
				registerMachineBlocks(event, 
					BLOCK_POWERED_FRAME, BLOCK_ENERGIZED_FRAME,
					
					BLOCK_POWERED_FURNACE, BLOCK_POWERED_GRINDER, BLOCK_POWERED_COMPRESSOR,
					BLOCK_POWERED_EXTRACTOR, BLOCK_POWERED_CHARGER,
					
					BLOCK_ENERGIZED_FURNACE, BLOCK_ENERGIZED_GRINDER, BLOCK_ENERGIZED_COMPRESSOR,
					BLOCK_ENERGIZED_EXTRACTOR,
					
					BLOCK_COMBINER_CENTER, BLOCK_COMBINER_PEDESTAL,
					
					BLOCK_PROCESSINGPLANT_BASE, BLOCK_PROCESSINGPLANT_BASE_MULTI);
			}
			
			@SubscribeEvent
			public static void registerBlockModelLocations(final ModelRegistryEvent event) {
				registerModelLocations(event,
						BLOCK_POWERED_FRAME, BLOCK_ENERGIZED_FRAME,
						
						BLOCK_POWERED_FURNACE, BLOCK_POWERED_GRINDER, BLOCK_POWERED_COMPRESSOR,
						BLOCK_POWERED_EXTRACTOR, BLOCK_POWERED_CHARGER,
						
						BLOCK_ENERGIZED_FURNACE, BLOCK_ENERGIZED_GRINDER, BLOCK_ENERGIZED_COMPRESSOR,
						BLOCK_ENERGIZED_EXTRACTOR,
						
						BLOCK_COMBINER_CENTER, BLOCK_COMBINER_PEDESTAL,
						
						BLOCK_PROCESSINGPLANT_BASE, BLOCK_PROCESSINGPLANT_BASE_MULTI);
			}
			
			private static void registerMachineBlocks(RegistryEvent.Register<Block> event, Block... blocks) {
				BLOCK_ITEMS = new ItemBlock[blocks.length];
				event.getRegistry().registerAll(blocks);
				for (int i = 0; i < blocks.length; i++) {
					BLOCK_ITEMS[i] = new ItemBlock(blocks[i]);
					BLOCK_ITEMS[i].setRegistryName(blocks[i].getRegistryName());
				}
			}
			
			@SubscribeEvent
			public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
				event.getRegistry().registerAll(BLOCK_ITEMS);
			}
		}
		
		//TODO FLUIDMACHINE
		@EventBusSubscriber(modid = Orecraft.MOD_ID)
		public static class FLUIDMACHINE {

			private static ItemBlock[] BLOCK_ITEMS;
			
			public static final Block BLOCK_POWERED_OREPROCESSOR = new BlockPoweredOreProcessor("block_powered_oreprocessor", Material.IRON, "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_POWERED_FLUIDCRAFTER = new BlockPoweredFluidCrafter("block_powered_fluidcrafter", Material.IRON, "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);

			@SubscribeEvent
			public static void register(RegistryEvent.Register<Block> event) {
				registerFluidMachineBlocks(event, 
						BLOCK_POWERED_OREPROCESSOR, BLOCK_POWERED_FLUIDCRAFTER);
			}
			
			@SubscribeEvent
			public static void registerBlockModelLocations(final ModelRegistryEvent event) {
				registerModelLocations(event,
						BLOCK_POWERED_OREPROCESSOR, BLOCK_POWERED_FLUIDCRAFTER);
			}
			
			private static void registerFluidMachineBlocks(RegistryEvent.Register<Block> event, Block... blocks) {
				BLOCK_ITEMS = new ItemBlock[blocks.length];
				event.getRegistry().registerAll(blocks);
				for (int i = 0; i < blocks.length; i++) {
					BLOCK_ITEMS[i] = new ItemBlock(blocks[i]);
					BLOCK_ITEMS[i].setRegistryName(blocks[i].getRegistryName());
				}
			}
			
			@SubscribeEvent
			public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
				event.getRegistry().registerAll(BLOCK_ITEMS);
			}
		}
		
		//TODO PIPE
		@EventBusSubscriber(modid = Orecraft.MOD_ID)
		public static class PIPE {

			private static ItemBlock[] BLOCK_ITEMS;
			
			
			public static final Block BLOCK_POWERED_ENERGYPIPE = new BlockPoweredEnergyPipe("block_powered_energypipe", Material.IRON, "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_ENERGIZED_ENERGYPIPE = new BlockEnergizedEnergyPipe("block_energized_energypipe", Material.IRON, "pickaxe", 2, 8, 4, Orecraft.TAB_ENERGY);

			public static final Block BLOCK_FLUIDPIPE_SOLID = new BlockFluidPipeSolid("block_fluidpipe_solid", Material.IRON, "pickaxe", 3, 8, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_FLUIDPIPE_CLEAR = new BlockFluidPipeClear("block_fluidpipe_clear", Material.IRON, "pickaxe", 3, 8, 4, Orecraft.TAB_ENERGY);
			
			public static final Block BLOCK_ITEMPIPE_SOLID = new BlockItemPipeSolid("block_itempipe_solid", Material.IRON, "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			public static final Block BLOCK_ITEMPIPE_CLEAR = new BlockItemPipeClear("block_itempipe_clear", Material.IRON, "pickaxe", 2, 5, 4, Orecraft.TAB_ENERGY);
			
			public static final Block BLOCK_CREATIVE_ENERGYPIPE = new BlockCreativeEnergyPipe("block_creative_energypipe", Material.IRON, "pickaxe", 2, 8, 4, Orecraft.TAB_ENERGY);
			
			@SubscribeEvent
			public static void register(RegistryEvent.Register<Block> event) {
				registerPipeBlocks(event, 
					BLOCK_POWERED_ENERGYPIPE, BLOCK_ENERGIZED_ENERGYPIPE,
					
					BLOCK_FLUIDPIPE_SOLID, BLOCK_FLUIDPIPE_CLEAR,
					
					BLOCK_ITEMPIPE_SOLID, BLOCK_ITEMPIPE_CLEAR,
					
					BLOCK_CREATIVE_ENERGYPIPE);
			}
			
			@SubscribeEvent
			public static void registerBlockModelLocations(final ModelRegistryEvent event) {
				registerModelLocations(event,
						BLOCK_POWERED_ENERGYPIPE, BLOCK_ENERGIZED_ENERGYPIPE,
						
						BLOCK_FLUIDPIPE_SOLID, BLOCK_FLUIDPIPE_CLEAR,
						
						BLOCK_ITEMPIPE_SOLID, BLOCK_ITEMPIPE_CLEAR,
						
						BLOCK_CREATIVE_ENERGYPIPE);
			}
			
			private static void registerPipeBlocks(RegistryEvent.Register<Block> event, Block... blocks) {
				BLOCK_ITEMS = new ItemBlock[blocks.length];
				event.getRegistry().registerAll(blocks);
				for (int i = 0; i < blocks.length; i++) {
					BLOCK_ITEMS[i] = new ItemBlock(blocks[i]);
					BLOCK_ITEMS[i].setRegistryName(blocks[i].getRegistryName());
				}
			}
			
			@SubscribeEvent
			public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
				event.getRegistry().registerAll(BLOCK_ITEMS);
			}
		}
		
		//TODO DECORATION
		@EventBusSubscriber(modid = Orecraft.MOD_ID)
		public static class DECORATION {

			private static ItemBlock[] BLOCK_ITEMS;
			
			public static final Block BLOCK_BASALTCOBBLESTONE = new ModBlock("block_basaltcobblestone", Material.ROCK, "pickaxe", 1, 2, 2, Orecraft.TAB_DECOR);
			public static final Block BLOCK_BASALT = new BlockWorldB("block_basalt", Material.ROCK, "pickaxe", 1, 2, 2, Orecraft.TAB_DECOR, BLOCK_BASALTCOBBLESTONE);
			public static final Block BLOCK_BASALTBRICK = new ModBlock("block_basaltbrick", Material.ROCK, "pickaxe", 1, 4, 4, Orecraft.TAB_DECOR);
			public static final Block BLOCK_BASALTSMOOTH = new ModBlock("block_basaltsmooth", Material.ROCK, "pickaxe", 1, 4, 4, Orecraft.TAB_DECOR);
			
			public static final Block BLOCK_MARBLECOBBLESTONE = new ModBlock("block_marblecobblestone", Material.ROCK, "pickaxe", 1, 2, 2, Orecraft.TAB_DECOR);
			public static final Block BLOCK_MARBLE = new BlockWorldB("block_marble", Material.ROCK, "pickaxe", 1, 2, 2, Orecraft.TAB_DECOR, BLOCK_MARBLECOBBLESTONE);
			public static final Block BLOCK_MARBLEBRICK = new ModBlock("block_marblebrick", Material.ROCK, "pickaxe", 1, 4, 4, Orecraft.TAB_DECOR);
			public static final Block BLOCK_MARBLESMOOTH = new ModBlock("block_marblesmooth", Material.ROCK, "pickaxe", 1, 4, 4, Orecraft.TAB_DECOR);
			
			public static final Block BLOCK_GLASS_BLACK = new BlockConnectedGlass("block_glass_black", "pickaxe", 3, 1, 1, Orecraft.TAB_DECOR);
			
			public static final Block BLOCK_REINFORCEDSTONE = new ModBlock("block_reinforcedstone", Material.ROCK, "pickaxe", 3, 20, 45, Orecraft.TAB_DECOR);
			public static final Block BLOCK_REINFORCEDBRICK = new ModBlock("block_reinforcedbrick", Material.ROCK, "pickaxe", 3, 20, 45, Orecraft.TAB_DECOR);
			public static final Block BLOCK_REINFORCEDGLASS = new BlockConnectedGlass("block_reinforcedglass", "pickaxe", 3, 20, 45, Orecraft.TAB_DECOR);
			public static final Block BLOCK_REINFORCED_ENCASED = new BlockConnected("block_reinforced_encased", Material.ROCK, "pickaxe", 3, 20, 45, Orecraft.TAB_DECOR);
			
			public static final Block BLOCK_WITHERSTONE = new ModBlock("block_witherstone", Material.ROCK, "pickaxe", 3, 40, 90, Orecraft.TAB_DECOR);
			public static final Block BLOCK_WITHERBRICK = new ModBlock("block_witherbrick", Material.ROCK, "pickaxe", 3, 40, 90, Orecraft.TAB_DECOR);
			public static final Block BLOCK_WITHERGLASS = new BlockConnectedGlass("block_witherglass", "pickaxe", 3, 40, 90, Orecraft.TAB_DECOR);
			
			public static final Block BLOCK_LOG = new ModBlockLog("block_log", "axe", 1, 4, 4, Orecraft.TAB_DECOR);
			public static final ItemBlock ITEMBLOCK_LOG = new ItemBlockVariants(BLOCK_LOG);
			//public static final Block block_leaves = new ModBlockLeaves();
			public static final Block BLOCK_PLANKS = new BlockPlanks("block_planks", "axe", 1, 4, 4, Orecraft.TAB_DECOR);
			public static final ItemBlock ITEMBLOCK_PLANKS = new ItemBlockVariants(BLOCK_PLANKS);
			
			@SubscribeEvent
			public static void register(RegistryEvent.Register<Block> event) {
				registerDecorationBlocks(event, 
					BLOCK_BASALTCOBBLESTONE, BLOCK_BASALT, BLOCK_BASALTBRICK, BLOCK_BASALTSMOOTH,
					
					BLOCK_MARBLECOBBLESTONE, BLOCK_MARBLE, BLOCK_MARBLEBRICK, BLOCK_MARBLESMOOTH,
					
					BLOCK_GLASS_BLACK,
					
					BLOCK_REINFORCEDSTONE, BLOCK_REINFORCEDBRICK, BLOCK_REINFORCEDGLASS, BLOCK_REINFORCED_ENCASED,
					
					BLOCK_WITHERSTONE, BLOCK_WITHERBRICK, BLOCK_WITHERGLASS );
				
				event.getRegistry().register(BLOCK_LOG);
				event.getRegistry().register(BLOCK_PLANKS);
			}
			
			@SubscribeEvent
			public static void registerBlockModelLocations(final ModelRegistryEvent event) {
				registerModelLocations(event,
						BLOCK_BASALTCOBBLESTONE, BLOCK_BASALT, BLOCK_BASALTBRICK, BLOCK_BASALTSMOOTH,
						
						BLOCK_MARBLECOBBLESTONE, BLOCK_MARBLE, BLOCK_MARBLEBRICK, BLOCK_MARBLESMOOTH,
						
						BLOCK_GLASS_BLACK,
						
						BLOCK_REINFORCEDSTONE, BLOCK_REINFORCEDBRICK, BLOCK_REINFORCEDGLASS, BLOCK_REINFORCED_ENCASED,

						BLOCK_WITHERSTONE, BLOCK_WITHERBRICK, BLOCK_WITHERGLASS );
			}
			
			private static void registerDecorationBlocks(RegistryEvent.Register<Block> event, Block... blocks) {
				BLOCK_ITEMS = new ItemBlock[blocks.length];
				event.getRegistry().registerAll(blocks);
				for (int i = 0; i < blocks.length; i++) {
					BLOCK_ITEMS[i] = new ItemBlock(blocks[i]);
					BLOCK_ITEMS[i].setRegistryName(blocks[i].getRegistryName());
				}
			}
			
			@SubscribeEvent
			public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
				event.getRegistry().register(ITEMBLOCK_LOG.setRegistryName(BLOCK_LOG.getRegistryName()));
				event.getRegistry().register(ITEMBLOCK_PLANKS.setRegistryName(BLOCK_PLANKS.getRegistryName()));
				
				event.getRegistry().registerAll(BLOCK_ITEMS);
			}
		}
		
		//TODO ORE
		@EventBusSubscriber(modid = Orecraft.MOD_ID)
		public static class ORE {
			
			private static ItemBlock[] BLOCK_ITEMS;
			
			public static final Block BLOCK_ORE_COPPER = new ModBlock("block_ore_copper", Material.ROCK, "pickaxe", 1, 3, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_ORE_TIN = new ModBlock("block_ore_tin", Material.ROCK, "pickaxe", 1, 3, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_ORE_SILVER = new ModBlock("block_ore_silver", Material.ROCK, "pickaxe", 2, 5, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_ORE_EUTINE = new ModBlock("block_ore_eutine", Material.ROCK, "pickaxe", 2, 5, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_ORE_MITHRIL = new ModBlock("block_ore_mithril", Material.ROCK, "pickaxe", 3, 7, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_ORE_ADAMANTITE = new ModBlock("block_ore_adamantite", Material.ROCK, "pickaxe", 3, 7, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_ORE_RUNE = new ModBlock("block_ore_rune", Material.ROCK, "pickaxe", 3, 7, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_ORE_FRACTUM = new ModBlock("block_ore_fractum", Material.ROCK, "pickaxe", 4, 9, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_ORE_SILICON = new ModBlock("block_ore_silicon", Material.ROCK, "pickaxe", 2, 4, 3, Orecraft.TAB_BLOCKS);
			
			public static final Block BLOCK_ORE_ONYX = new BlockGemedOre("block_ore_onyx", Material.ROCK, "pickaxe", 1, 3, 8, Orecraft.TAB_BLOCKS, ItemHandler.gem_onyx);
			public static final Block BLOCK_ORE_SATSUM = new BlockGemedOre("block_ore_satsum", Material.ROCK, "pickaxe", 1, 3, 8, Orecraft.TAB_BLOCKS, ItemHandler.gem_satsum);
			public static final Block BLOCK_ORE_AZURITE = new BlockGemedOre("block_ore_azurite", Material.ROCK, "pickaxe", 2, 5, 8, Orecraft.TAB_BLOCKS, ItemHandler.gem_azurite);
			public static final Block BLOCK_ORE_RUBY = new BlockGemedOre("block_ore_ruby", Material.ROCK, "pickaxe", 3, 7, 8, Orecraft.TAB_BLOCKS, ItemHandler.gem_ruby);
			public static final Block BLOCK_ORE_AMYTHEST = new BlockGemedOre("block_ore_amythest", Material.ROCK, "pickaxe", 3, 7, 8, Orecraft.TAB_BLOCKS, ItemHandler.gem_amythest);
			public static final Block BLOCK_ORE_YELLOWTOPAZ = new BlockGemedOre("block_ore_yellowtopaz", Material.ROCK, "pickaxe", 2, 5, 8, Orecraft.TAB_BLOCKS, ItemHandler.gem_yellowtopaz);
			public static final Block BLOCK_ORE_FIREOPAL = new BlockGemedOre("block_ore_fireopal", Material.ROCK, "pickaxe", 3, 7, 8, Orecraft.TAB_BLOCKS, ItemHandler.gem_fireopal);
			public static final Block BLOCK_ORE_TURQUOISE = new BlockGemedOre("block_ore_turquoise", Material.ROCK, "pickaxe", 3, 7, 8, Orecraft.TAB_BLOCKS, ItemHandler.gem_turquoise);
			public static final Block BLOCK_ORE_BLACKDIAMOND = new BlockGemedOre("block_ore_blackdiamond", Material.ROCK, "pickaxe", 4, 9, 8, Orecraft.TAB_BLOCKS, ItemHandler.gem_blackdiamond);
			
			@SubscribeEvent
			public static void register(RegistryEvent.Register<Block> event) {
				registerOreBlocks(event, 
					BLOCK_ORE_COPPER, BLOCK_ORE_TIN, BLOCK_ORE_SILVER, BLOCK_ORE_EUTINE,
					BLOCK_ORE_MITHRIL, BLOCK_ORE_ADAMANTITE, BLOCK_ORE_RUNE, 
					BLOCK_ORE_FRACTUM, BLOCK_ORE_SILICON,
					
					BLOCK_ORE_ONYX, BLOCK_ORE_SATSUM, BLOCK_ORE_AZURITE, BLOCK_ORE_RUBY,
					BLOCK_ORE_AMYTHEST, BLOCK_ORE_YELLOWTOPAZ, BLOCK_ORE_FIREOPAL, BLOCK_ORE_TURQUOISE, BLOCK_ORE_BLACKDIAMOND);
			}
			
			@SubscribeEvent
			public static void registerBlockModelLocations(final ModelRegistryEvent event) {
				registerModelLocations(event,
						BLOCK_ORE_COPPER, BLOCK_ORE_TIN, BLOCK_ORE_SILVER, BLOCK_ORE_EUTINE,
						BLOCK_ORE_MITHRIL, BLOCK_ORE_ADAMANTITE, BLOCK_ORE_RUNE, 
						BLOCK_ORE_FRACTUM, BLOCK_ORE_SILICON,
						
						BLOCK_ORE_ONYX, BLOCK_ORE_SATSUM, BLOCK_ORE_AZURITE, BLOCK_ORE_RUBY,
						BLOCK_ORE_AMYTHEST, BLOCK_ORE_YELLOWTOPAZ, BLOCK_ORE_FIREOPAL, BLOCK_ORE_TURQUOISE, BLOCK_ORE_BLACKDIAMOND);
			} 
			
			private static void registerOreBlocks(RegistryEvent.Register<Block> event, Block... blocks) {
				BLOCK_ITEMS = new ItemBlock[blocks.length];
				event.getRegistry().registerAll(blocks);
				for (int i = 0; i < blocks.length; i++) {
					BLOCK_ITEMS[i] = new ItemBlock(blocks[i]);
					BLOCK_ITEMS[i].setRegistryName(blocks[i].getRegistryName());
				}
			}
			
			@SubscribeEvent
			public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
				event.getRegistry().registerAll(BLOCK_ITEMS);
			}
		}
		
		//TODO OREBLOCKS
		@EventBusSubscriber(modid = Orecraft.MOD_ID)
		public static class OREBLOCKS {
			
			private static ItemBlock[] BLOCK_ITEMS;
			
			public static final Block BLOCK_COPPER = new ModBlock("block_copper", Material.IRON, "pickaxe", 1, 3, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_TIN = new ModBlock("block_tin", Material.IRON, "pickaxe", 1, 3, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_SILVER = new ModBlock("block_silver", Material.IRON, "pickaxe", 1, 5, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_EUTINE = new ModBlock("block_eutine", Material.IRON, "pickaxe", 1, 5, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_MITHRIL = new ModBlock("block_mithril", Material.IRON, "pickaxe", 3, 7, 10, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_ADAMANTITE = new ModBlock("block_adamantite", Material.IRON, "pickaxe", 3, 7, 10, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_RUNE = new ModBlock("block_rune", Material.IRON, "pickaxe", 3, 7, 12, Orecraft.TAB_BLOCKS);
			
			public static final Block BLOCK_FRACTUM = new ModBlock("block_fractum", Material.IRON, "pickaxe", 4, 9, 14, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_SILICON = new ModBlock("block_silicon", Material.IRON, "pickaxe", 2, 4, 5, Orecraft.TAB_BLOCKS);
			
			public static final Block BLOCK_STEEL = new ModBlock("block_steel", Material.IRON, "pickaxe", 2, 5, 10, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_BRONZE = new ModBlock("block_bronze", Material.IRON, "pickaxe", 2, 4, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_BRASS = new ModBlock("block_brass", Material.IRON, "pickaxe", 2, 4, 6, Orecraft.TAB_BLOCKS);

			public static final Block BLOCK_ONYX = new ModBlock("block_onyx", Material.IRON, "pickaxe", 1, 3, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_SATSUM = new ModBlock("block_satsum", Material.IRON, "pickaxe", 1, 3, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_AZURITE = new ModBlock("block_azurite", Material.IRON, "pickaxe", 2, 5, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_RUBY = new ModBlock("block_ruby", Material.IRON, "pickaxe", 3, 7, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_AMYTHEST = new ModBlock("block_amythest", Material.IRON, "pickaxe", 3, 7, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_YELLOWTOPAZ = new ModBlock("block_yellowtopaz", Material.IRON, "pickaxe", 2, 5, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_FIREOPAL = new ModBlock("block_fireopal", Material.IRON, "pickaxe", 3, 7, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_TURQUOISE = new ModBlock("block_turquoise", Material.IRON, "pickaxe", 3, 7, 8, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_BLACKDIAMOND = new ModBlock("block_blackdiamond", Material.IRON, "pickaxe", 4, 9, 8, Orecraft.TAB_BLOCKS);
			
			public static final Block BLOCK_GODONE = new ModBlock("block_godone", Material.IRON, "pickaxe", 4, 10, 12, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_GODTWO = new ModBlock("block_godtwo", Material.IRON, "pickaxe", 4, 10, 16, Orecraft.TAB_BLOCKS);
			public static final Block BLOCK_GODTHREE = new ModBlock("block_godthree", Material.IRON, "pickaxe", 4, 10, 20, Orecraft.TAB_BLOCKS);
			
			@SubscribeEvent
			public static void register(RegistryEvent.Register<Block> event) {
				registerOreBlockBlocks(event, 
					BLOCK_COPPER, BLOCK_TIN, BLOCK_SILVER, BLOCK_EUTINE,
					BLOCK_MITHRIL, BLOCK_ADAMANTITE, BLOCK_RUNE, 
					BLOCK_FRACTUM, BLOCK_SILICON,
					
					BLOCK_STEEL, BLOCK_BRONZE, BLOCK_BRASS,
					
					BLOCK_ONYX, BLOCK_SATSUM, BLOCK_AZURITE, BLOCK_RUBY,
					BLOCK_AMYTHEST, BLOCK_YELLOWTOPAZ, BLOCK_FIREOPAL, BLOCK_TURQUOISE, BLOCK_BLACKDIAMOND,
					
					BLOCK_GODONE, BLOCK_GODTWO, BLOCK_GODTHREE);
			}
			
			@SubscribeEvent
			public static void registerBlockModelLocations(final ModelRegistryEvent event) {
				registerModelLocations(event, 
						BLOCK_COPPER, BLOCK_TIN, BLOCK_SILVER, BLOCK_EUTINE,
						BLOCK_MITHRIL, BLOCK_ADAMANTITE, BLOCK_RUNE, 
						BLOCK_FRACTUM, BLOCK_SILICON,
						
						BLOCK_STEEL, BLOCK_BRONZE, BLOCK_BRASS,
						
						BLOCK_ONYX, BLOCK_SATSUM, BLOCK_AZURITE, BLOCK_RUBY,
						BLOCK_AMYTHEST, BLOCK_YELLOWTOPAZ, BLOCK_FIREOPAL, BLOCK_TURQUOISE, BLOCK_BLACKDIAMOND,
						
						BLOCK_GODONE, BLOCK_GODTWO, BLOCK_GODTHREE);
			}
			
			private static void registerOreBlockBlocks(RegistryEvent.Register<Block> event, Block... blocks) {
				BLOCK_ITEMS = new ItemBlock[blocks.length];
				event.getRegistry().registerAll(blocks);
				for (int i = 0; i < blocks.length; i++) {
					BLOCK_ITEMS[i] = new ItemBlock(blocks[i]);
					BLOCK_ITEMS[i].setRegistryName(blocks[i].getRegistryName());
				}
			}
			
			@SubscribeEvent
			public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
				event.getRegistry().registerAll(BLOCK_ITEMS);
			}
		}
		
		public static void registerFluidModelLocations(final ModelRegistryEvent event, ModBlockFluid... blocks) {
			for (int i = 0; i < blocks.length; i++) {
				registerFluidModelLocation(blocks[i]);
			}
		}
		
		public static void registerModelLocations(final ModelRegistryEvent event, Block... blocks) {
			for (int i = 0; i < blocks.length; i++) {
				registerBlockModelLocation(blocks[i]);
			}
		}
		
		@SideOnly(Side.CLIENT)
		public static void registerBlockModelLocation(Block block){
			Item item = Item.getItemFromBlock(block);
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
		
		@SubscribeEvent
		public static void registerVariants(final ModelRegistryEvent event) {
			for (int i = 0; i < BlockPlanks.EnumType.values().length; i++) {
				registerBlockModelLocationWithVariants(REGISTRATION.DECORATION.BLOCK_PLANKS, i, "block_planks_" + BlockPlanks.EnumType.values()[i].getName());
				registerBlockModelLocationWithVariants(REGISTRATION.DECORATION.BLOCK_LOG, i, "block_log_" + BlockPlanks.EnumType.values()[i].getName());
			}
		}
		
		@SideOnly(Side.CLIENT)
		public static void registerBlockModelLocationWithVariants(Block block, int meta, String filename) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(new ResourceLocation(Orecraft.MOD_ID, filename), "inventory"));
		}
	}
	
	public static ModBlockFluid block_fluid_coolant;
	public static ModBlockFluid block_fluid_energized_redstone;
	public static ModBlockFluid block_fluid_glowstoneinfused_magma;
	
	@SubscribeEvent
	public static void registerOtherBlocks(RegistryEvent.Register<Block> event) {
		
		event.getRegistry().register(block_fluid_coolant);
		event.getRegistry().register(block_fluid_energized_redstone);
		event.getRegistry().register(block_fluid_glowstoneinfused_magma);
	}
	
	@SubscribeEvent
	public static void registerModelLocations(final ModelRegistryEvent event) {

		registerFluidModelLocation(block_fluid_coolant);
		registerFluidModelLocation(block_fluid_energized_redstone);
		registerFluidModelLocation(block_fluid_glowstoneinfused_magma);		
		
		//registerBlockModelLocationWithVariants(block_planks, )
	}
	
	public static void preInit() {
		block_fluid_coolant = new ModBlockFluid(FluidRegistry.getFluid("coolant"), Material.WATER, "coolant");
		block_fluid_energized_redstone = new ModBlockFluid(FluidRegistry.getFluid("energized_redstone"), Material.WATER, "energized_redstone");
		block_fluid_glowstoneinfused_magma = new ModBlockFluid(FluidRegistry.getFluid("glowstoneinfused_magma"), Material.LAVA, "glowstoneinfused_magma");
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerFluidModelLocation(ModBlockFluid fluid_block){
		Item item = Item.getItemFromBlock(fluid_block);
		
		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(new ResourceLocation(Orecraft.MOD_ID, "fluid/fluid_" + fluid_block.getFluid().getName()), "normal");
		
		ModelLoader.setCustomMeshDefinition(item, stack -> modelResourceLocation);

		ModelLoader.setCustomStateMapper((Block) fluid_block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return modelResourceLocation;
			}
		});
	}
	
}
