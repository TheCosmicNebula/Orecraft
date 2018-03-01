package com.zeher.orecraft.core.handlers;

import com.zeher.orecraft.OreCraft;
import com.zeher.orecraft.machine.block.BlockCombinerCenter;
import com.zeher.orecraft.machine.block.BlockCombinerPedestal;
import com.zeher.orecraft.machine.block.BlockEnergizedCombiner;
import com.zeher.orecraft.machine.block.BlockEnergizedCompressor;
import com.zeher.orecraft.machine.block.BlockEnergizedFurnace;
import com.zeher.orecraft.machine.block.BlockEnergizedGrinder;
import com.zeher.orecraft.machine.block.BlockPoweredCharger;
import com.zeher.orecraft.machine.block.BlockPoweredCompressor;
import com.zeher.orecraft.machine.block.BlockPoweredFluidCrafter;
import com.zeher.orecraft.machine.block.BlockPoweredFurnace;
import com.zeher.orecraft.machine.block.BlockPoweredGrinder;
import com.zeher.orecraft.machine.block.BlockPoweredOreProcessor;
import com.zeher.orecraft.multiblock.block.BlockProcessingPlantBase;
import com.zeher.orecraft.production.block.BlockCoalGeneratorBasic;
import com.zeher.orecraft.production.block.BlockHeatedFluidGeneratorBasic;
import com.zeher.orecraft.production.block.BlockPeltierGeneratorBasic;
import com.zeher.orecraft.storage.block.BlockEnergizedCapacitor;
import com.zeher.orecraft.storage.block.BlockFluidTank;
import com.zeher.orecraft.storage.block.BlockMechanisedStorageLarge;
import com.zeher.orecraft.storage.block.BlockMechanisedStorageSmall;
import com.zeher.orecraft.storage.block.BlockPoweredCapacitor;
import com.zeher.orecraft.storage.block.ItemBlockEnergizedCapacitor;
import com.zeher.orecraft.storage.block.ItemBlockFluidTank;
import com.zeher.orecraft.storage.block.ItemBlockPoweredCapacitor;
import com.zeher.orecraft.transfer.block.pipe.BlockEnergizedEnergyPipe;
import com.zeher.orecraft.transfer.block.pipe.BlockFluidPipeClear;
import com.zeher.orecraft.transfer.block.pipe.BlockFluidPipeSolid;
import com.zeher.orecraft.transfer.block.pipe.BlockItemPipeClear;
import com.zeher.orecraft.transfer.block.pipe.BlockItemPipeSolid;
import com.zeher.orecraft.transfer.block.pipe.BlockPoweredEnergyPipe;
import com.zeher.trzlib.api.TRZMeshDefinitionFix;
import com.zeher.trzlib.core.block.TRZBlock;
import com.zeher.trzlib.core.block.TRZBlockOreGem;
import com.zeher.trzlib.fluid.TRZBlockFluid;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(OreCraft.mod_id)
public class BlockHandler {
	
	public static Block block_ore_copper;
	public static Block block_copper;
	
	public static Block block_ore_tin;
	public static Block block_tin;
	
	public static Block block_ore_silver;
	public static Block block_silver;
	
	public static Block block_ore_eutine;
	public static Block block_eutine;
	
	public static Block block_ore_mithril;
	public static Block block_mithril;
	
	public static Block block_ore_adamantite;
	public static Block block_adamantite;
	
	public static Block block_ore_rune;
	public static Block block_rune;
	
	public static Block block_ore_fractum;
	public static Block block_fractum;
	
	public static Block block_ore_silicon;
	public static Block block_silicon;
	
	public static Block block_steel;
	public static Block block_bronze;
	public static Block block_brass;
	
	public static Block block_ore_onyx;
	public static Block block_onyx;
	
	public static Block block_ore_satsum;
	public static Block block_satsum;
	
	public static Block block_ore_azurite;
	public static Block block_azurite;

	public static Block block_ore_ruby;
	public static Block block_ruby;
	
	public static Block block_ore_amythest;
	public static Block block_amythest;
	
	public static Block block_ore_yellowtopaz;
	public static Block block_yellowtopaz;
	
	public static Block block_ore_fireopal;
	public static Block block_fireopal;
	
	public static Block block_ore_turquoise;
	public static Block block_turquoise;
	
	public static Block block_ore_blackdiamond;
	public static Block block_blackdiamond;
	
	public static Block block_god_one;
	public static Block block_god_two;
	public static Block block_god_three;
	
	public static Block block_red;
	
	public static Block block_godone;
	public static Block block_godtwo;
	public static Block block_godthree;
	
	public static Block block_basalt_cobblestone;
	public static Block block_basalt;
	public static Block block_basalt_smooth;
	public static Block block_basalt_brick;
	
	public static Block block_marble_cobblestone;
	public static Block block_marble;
	public static Block block_marble_smooth;
	public static Block block_marble_brick;
	
	public static Block block_darkstone;
	public static Block block_darkstone_smooth;
	public static Block block_darkstone_brick;
	
	public static Block block_lightstone;
	public static Block block_lightstone_smooth;
	public static Block block_lightstone_brick;
	
	public static Block block_blackglass;
	
	public static Block block_reinforcedstone;
	public static Block block_reinforcedstone_brick;
	public static Block block_reinforcedstone_glass;
	
	public static Block block_witherinfusedstone;
	public static Block block_witherinfusedstone_brick;
	public static Block block_witherinfusedstone_glass;
	

	
	public static Block block_powered_frame;
	public static Block block_energized_frame;
	
	
	
	public static BlockPoweredCapacitor block_powered_capacitor;
	public static ItemBlockPoweredCapacitor itemblock_powered_capacitor;
	public static BlockEnergizedCapacitor block_energized_capacitor;
	public static ItemBlockEnergizedCapacitor itemblock_energized_capacitor;
	
	
	public static BlockFluidTank block_fluidtank;
	public static ItemBlockFluidTank itemblock_fluidtank;

	public static BlockMechanisedStorageSmall block_mechanised_storage_small;
	public static BlockMechanisedStorageLarge block_mechanised_storage_large;
	
	
	public static BlockPoweredEnergyPipe block_powered_energypipe;
	public static BlockEnergizedEnergyPipe block_energized_energypipe;
	
	public static BlockItemPipeSolid block_itempipe_solid;
	public static BlockItemPipeClear block_itempipe_clear;
	
	public static BlockFluidPipeSolid block_fluidpipe_solid;
	public static BlockFluidPipeClear block_fluidpipe_clear;
	

	
	public static BlockPoweredFurnace block_powered_furnace;
	public static BlockPoweredFurnace block_powered_furnace_on;
	
	public static BlockPoweredGrinder block_powered_grinder;
	public static BlockPoweredGrinder block_powered_grinder_on;
	
	public static BlockPoweredCompressor block_powered_compressor;
	public static BlockPoweredCompressor block_powered_compressor_on;
	
	public static BlockEnergizedFurnace block_energized_furnace;
	public static BlockEnergizedFurnace block_energized_furnace_on;
	
	public static BlockEnergizedCompressor block_energized_compressor;
	public static BlockEnergizedCompressor block_energized_compressor_on;
	
	public static BlockEnergizedGrinder block_energized_grinder;
	public static BlockEnergizedGrinder block_energized_grinder_on;
	
	public static BlockEnergizedCombiner block_energized_combiner;
	public static BlockEnergizedCombiner block_energized_combiner_on;
	
	public static BlockCombinerPedestal block_combiner_pedestal;
	public static BlockCombinerCenter block_combiner_center;
	
	public static BlockPoweredCharger block_powered_charger;
	public static BlockPoweredCharger block_powered_charger_on;
	
	public static BlockPoweredOreProcessor block_powered_oreprocessor;
	public static BlockPoweredOreProcessor block_powered_oreprocessor_on;
	
	public static BlockPoweredFluidCrafter block_powered_fluidcrafter;
	public static BlockPoweredFluidCrafter block_powered_fluidcrafter_on;
	
	
	public static BlockCoalGeneratorBasic block_coalgenerator;
	public static BlockCoalGeneratorBasic block_coalgenerator_on;
	
	public static BlockHeatedFluidGeneratorBasic block_heatedfluidgenerator;
	public static BlockHeatedFluidGeneratorBasic block_heatedfluidgenerator_on;

	public static BlockPeltierGeneratorBasic block_peltiergenerator;
	public static BlockPeltierGeneratorBasic block_peltiergenerator_on;
	
	
	public static BlockProcessingPlantBase block_processingplant_base;
	public static BlockProcessingPlantBase block_processingplant_base_multi;
	
	
	public static TRZBlockFluid block_fluid_coolant;
	public static TRZBlockFluid block_fluid_energized_redstone;
	public static TRZBlockFluid block_fluid_glowstoneinfused_magma;
	
	
	public static void preInit(){
		
		block_ore_copper = new TRZBlock("block_ore_copper", Material.ROCK, "pickaxe", 1, 3, 8, OreCraft.tab_orecraft);
		block_copper = new TRZBlock("block_copper", Material.IRON, "pickaxe", 1, 3, 8, OreCraft.tab_orecraft);
		
		block_ore_tin = new TRZBlock("block_ore_tin", Material.ROCK, "pickaxe", 1, 3, 8, OreCraft.tab_orecraft);
		block_tin = new TRZBlock("block_tin", Material.IRON, "pickaxe", 1, 3, 8, OreCraft.tab_orecraft);
		
		block_ore_silver = new TRZBlock("block_ore_silver", Material.ROCK, "pickaxe", 2, 5, 8, OreCraft.tab_orecraft);
		block_silver = new TRZBlock("block_silver", Material.IRON, "pickaxe", 1, 5, 8, OreCraft.tab_orecraft);
		
		block_ore_eutine = new TRZBlock("block_ore_eutine", Material.ROCK, "pickaxe", 2, 5, 8, OreCraft.tab_orecraft);
		block_eutine = new TRZBlock("block_eutine", Material.IRON, "pickaxe", 1, 5, 8, OreCraft.tab_orecraft);
		
		block_ore_mithril = new TRZBlock("block_ore_mithril", Material.ROCK, "pickaxe", 3, 7, 8, OreCraft.tab_orecraft);
		block_mithril = new TRZBlock("block_mithril", Material.IRON, "pickaxe", 3, 7, 10, OreCraft.tab_orecraft);
		
		block_ore_adamantite = new TRZBlock("block_ore_adamantite", Material.ROCK, "pickaxe", 3, 7, 8, OreCraft.tab_orecraft);
		block_adamantite = new TRZBlock("block_adamantite", Material.IRON, "pickaxe", 3, 7, 10, OreCraft.tab_orecraft);
		
		block_ore_rune = new TRZBlock("block_ore_rune", Material.ROCK, "pickaxe", 3, 7, 8, OreCraft.tab_orecraft);
		block_rune = new TRZBlock("block_rune", Material.IRON, "pickaxe", 3, 7, 12, OreCraft.tab_orecraft);
		
		block_ore_fractum = new TRZBlock("block_ore_fractum", Material.ROCK, "pickaxe", 4, 9, 8, OreCraft.tab_orecraft);
		block_fractum = new TRZBlock("block_fractum", Material.IRON, "pickaxe", 4, 9, 14, OreCraft.tab_orecraft);
		
		block_ore_silicon = new TRZBlock("block_ore_silicon", Material.ROCK, "pickaxe", 2, 4, 3, OreCraft.tab_orecraft);
		block_silicon = new TRZBlock("block_silicon", Material.IRON, "pickaxe", 2, 4, 5, OreCraft.tab_orecraft);
		
		block_steel = new TRZBlock("block_steel", Material.IRON, "pickaxe", 2, 5, 10, OreCraft.tab_orecraft);
		block_bronze = new TRZBlock("block_bronze", Material.IRON, "pickaxe", 2, 4, 8, OreCraft.tab_orecraft);
		block_brass = new TRZBlock("block_brass", Material.IRON, "pickaxe", 2, 4, 6, OreCraft.tab_orecraft);
		
		
		block_ore_onyx = new TRZBlockOreGem("block_ore_onyx", ItemHandler.gem_onyx, "pickaxe", 1, 3, OreCraft.tab_orecraft);
		block_onyx = new TRZBlock("block_onyx", Material.IRON, "pickaxe", 1, 3, 8, OreCraft.tab_orecraft);
		
		block_ore_satsum = new TRZBlockOreGem("block_ore_satsum", ItemHandler.gem_satsum, "pickaxe", 1, 3, OreCraft.tab_orecraft);
		block_satsum = new TRZBlock("block_satsum", Material.IRON, "pickaxe", 1, 3, 8, OreCraft.tab_orecraft);
		
		block_ore_azurite = new TRZBlockOreGem("block_ore_azurite", ItemHandler.gem_azurite, "pickaxe", 2, 5, OreCraft.tab_orecraft);
		block_azurite = new TRZBlock("block_azurite", Material.IRON, "pickaxe", 2, 5, 8, OreCraft.tab_orecraft);
		
		block_ore_ruby = new TRZBlockOreGem("block_ore_ruby", ItemHandler.gem_ruby, "pickaxe", 3, 7, OreCraft.tab_orecraft);
		block_ruby = new TRZBlock("block_ruby", Material.IRON, "pickaxe", 3, 7, 8, OreCraft.tab_orecraft);
		
		block_ore_amythest = new TRZBlockOreGem("block_ore_amythest", ItemHandler.gem_amythest, "pickaxe", 3, 7, OreCraft.tab_orecraft);
		block_amythest = new TRZBlock("block_amythest", Material.IRON, "pickaxe", 3, 7, 8, OreCraft.tab_orecraft);
		
		block_ore_yellowtopaz = new TRZBlockOreGem("block_ore_yellowtopaz", ItemHandler.gem_yellowtopaz, "pickaxe", 2, 5, OreCraft.tab_orecraft);
		block_yellowtopaz = new TRZBlock("block_yellowtopaz", Material.IRON, "pickaxe", 2, 5, 8, OreCraft.tab_orecraft);
		
		block_ore_fireopal = new TRZBlockOreGem("block_ore_fireopal", ItemHandler.gem_fireopal, "pickaxe", 3, 7, OreCraft.tab_orecraft);
		block_fireopal = new TRZBlock("block_fireopal", Material.IRON, "pickaxe", 3, 7, 8, OreCraft.tab_orecraft);
		
		block_ore_turquoise = new TRZBlockOreGem("block_ore_turquoise", ItemHandler.gem_turquoise, "pickaxe", 3, 7, OreCraft.tab_orecraft);
		block_turquoise = new TRZBlock("block_turquoise", Material.IRON, "pickaxe", 3, 7, 8, OreCraft.tab_orecraft);
		
		block_ore_blackdiamond = new TRZBlockOreGem("block_ore_blackdiamond", ItemHandler.gem_blackdiamond, "pickaxe", 4, 9, OreCraft.tab_orecraft);
		block_blackdiamond = new TRZBlock("block_blackdiamond", Material.IRON, "pickaxe", 4, 9, 8, OreCraft.tab_orecraft);
		
		block_red = new TRZBlock("block_red", Material.IRON, "pickaxe", 3, 4, 8, OreCraft.tab_orecraft);
		
		block_godone = new TRZBlock("block_godone", Material.IRON, "pickaxe", 4, 10, 12, OreCraft.tab_orecraft);
		block_godtwo = new TRZBlock("block_godtwo", Material.IRON, "pickaxe", 4, 10, 16, OreCraft.tab_orecraft);
		block_godthree = new TRZBlock("block_godthree", Material.IRON, "pickaxe", 4, 10, 20, OreCraft.tab_orecraft);
		
		
		block_powered_frame = new TRZBlock("block_powered_frame", Material.IRON, "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy);
		block_energized_frame = new TRZBlock("block_energized_frame", Material.IRON, "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy);
		

		
		block_powered_capacitor = new BlockPoweredCapacitor("block_powered_capacitor", Material.IRON, "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy);
		itemblock_powered_capacitor = new ItemBlockPoweredCapacitor(block_powered_capacitor);
		block_energized_capacitor = new BlockEnergizedCapacitor("block_energized_capacitor", Material.IRON, "pickaxe", 3, 8, 4, OreCraft.tab_orecraft_energy);
		itemblock_energized_capacitor = new ItemBlockEnergizedCapacitor(block_energized_capacitor);
		
		block_fluidtank = new BlockFluidTank("block_fluidtank", Material.GLASS, "pickaxe", 2, 3, 10, OreCraft.tab_orecraft_energy);
		itemblock_fluidtank = new ItemBlockFluidTank(block_fluidtank);
		
		block_mechanised_storage_small = new BlockMechanisedStorageSmall("block_mechanised_storage_small", Material.IRON, "pickaxe", 2, 8, 10, OreCraft.tab_orecraft_energy);
		block_mechanised_storage_large = new BlockMechanisedStorageLarge("block_mechanised_storage_large", Material.IRON, "pickaxe", 3, 10, 12, OreCraft.tab_orecraft_energy);
		
		
		
		block_powered_energypipe = new BlockPoweredEnergyPipe("block_powered_energypipe", Material.IRON, "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy);
		block_energized_energypipe = new BlockEnergizedEnergyPipe("block_energized_energypipe", Material.IRON, "pickaxe", 2, 8, 4, OreCraft.tab_orecraft_energy);
		
		block_itempipe_solid = new BlockItemPipeSolid("block_itempipe_solid", Material.IRON, "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy);
		block_itempipe_clear = new BlockItemPipeClear("block_itempipe_clear", Material.IRON, "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy);
		
		block_fluidpipe_solid = new BlockFluidPipeSolid("block_fluidpipe_solid", Material.IRON, "pickaxe", 3, 8, 4, OreCraft.tab_orecraft_energy);
		block_fluidpipe_clear = new BlockFluidPipeClear("block_fluidpipe_clear", Material.IRON, "pickaxe", 3, 8, 4, OreCraft.tab_orecraft_energy);
		
		
		
		block_powered_furnace = new BlockPoweredFurnace(Material.IRON, "block_powered_furnace","pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy, false);
		block_powered_furnace_on = new BlockPoweredFurnace(Material.IRON, "block_powered_furnace_on", "pickaxe", 2, 5, 4, null, true);
		
		block_powered_grinder = new BlockPoweredGrinder(Material.IRON, "block_powered_grinder", "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy, false);
		block_powered_grinder_on = new BlockPoweredGrinder(Material.IRON, "block_powered_grinder_on", "pickaxe", 2, 5, 4, null, true);
		
		block_powered_compressor = new BlockPoweredCompressor(Material.IRON, "block_powered_compressor", "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy, false);
		block_powered_compressor_on = new BlockPoweredCompressor(Material.IRON, "block_powered_compressor_on", "pickaxe", 2, 5, 4, null, true);
		
		block_energized_furnace = new BlockEnergizedFurnace(Material.IRON, "block_energized_furnace", "pickaxe", 3, 8, 4, OreCraft.tab_orecraft_energy, false);
		block_energized_furnace_on = new BlockEnergizedFurnace(Material.IRON, "block_energized_furnace_on", "pickaxe", 3, 8, 4, null, true);
		
		block_energized_grinder = new BlockEnergizedGrinder(Material.IRON, "block_energized_grinder", "pickaxe", 3, 8, 4, OreCraft.tab_orecraft_energy, false);
		block_energized_grinder_on = new BlockEnergizedGrinder(Material.IRON, "block_energized_grinder_on", "pickaxe", 3, 8, 4, null, true);
		
		block_energized_compressor = new BlockEnergizedCompressor(Material.IRON, "block_energized_compressor", "pickaxe", 3, 8, 4, OreCraft.tab_orecraft_energy, false);
		block_energized_compressor_on = new BlockEnergizedCompressor(Material.IRON, "block_energized_compressor_on", "pickaxe", 3, 8, 4, null, true);
		
		block_energized_combiner = new BlockEnergizedCombiner(Material.IRON, "block_energized_combiner", "pickaxe", 3, 8, 4, OreCraft.tab_orecraft_energy, false);
		block_energized_combiner_on = new BlockEnergizedCombiner(Material.IRON, "block_energized_combiner_on", "pickaxe", 3, 8, 4, null, true);
		
		block_powered_charger = new BlockPoweredCharger(Material.IRON, "block_powered_charger", "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy, false);
		block_powered_charger_on = new BlockPoweredCharger(Material.IRON, "block_powered_charger_on", "pickaxe", 2, 5, 4, null, true);
		
		block_powered_oreprocessor = new BlockPoweredOreProcessor("block_powered_oreprocessor", Material.IRON, "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy, false);
		block_powered_oreprocessor_on = new BlockPoweredOreProcessor("block_powered_oreprocessor_on", Material.IRON, "pickaxe", 2, 5, 4, null, true);
		
		block_powered_fluidcrafter = new BlockPoweredFluidCrafter("block_powered_fluidcrafter", Material.IRON, "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy, false);
		block_powered_fluidcrafter_on = new BlockPoweredFluidCrafter("block_powered_fluidcrafter_on", Material.IRON, "pickaxe", 2, 5, 4, null, true);
		
		block_combiner_center = new BlockCombinerCenter("block_combiner_center", Material.IRON, "pickaxe", 3, 8, 4, OreCraft.tab_orecraft_energy, false);
		block_combiner_pedestal = new BlockCombinerPedestal("block_combiner_pedestal", Material.IRON, "pickaxe", 3, 8, 4, OreCraft.tab_orecraft_energy, false);
		
		
		block_coalgenerator = new BlockCoalGeneratorBasic("block_coalgenerator", Material.IRON, "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy, false);
		block_coalgenerator_on = new BlockCoalGeneratorBasic("block_coalgenerator_on", Material.IRON, "pickaxe", 2, 5, 4, null, true);
		
		block_heatedfluidgenerator = new BlockHeatedFluidGeneratorBasic("block_heatedfluidgenerator", Material.IRON, "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy, false);
		block_heatedfluidgenerator_on = new BlockHeatedFluidGeneratorBasic("block_heatedfluidgenerator_on", Material.IRON, "pickaxe", 2, 5, 4, null, true);
		
		block_peltiergenerator = new BlockPeltierGeneratorBasic("block_peltiergenerator", Material.IRON, "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy, false);
		block_peltiergenerator_on = new BlockPeltierGeneratorBasic("block_peltiergenerator_on", Material.IRON, "pickaxe", 2, 5, 4, null, true);
		
		block_processingplant_base = new BlockProcessingPlantBase(Material.IRON, "block_processingplant_base", "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy, false);
		block_processingplant_base_multi = new BlockProcessingPlantBase(Material.IRON, "block_processingplant_base_multi", "pickaxe", 2, 5, 4, OreCraft.tab_orecraft_energy, true);
		
		
		block_fluid_coolant = new TRZBlockFluid(FluidRegistry.getFluid("coolant"), Material.WATER, "coolant");
		block_fluid_energized_redstone = new TRZBlockFluid(FluidRegistry.getFluid("energized_redstone"), Material.WATER, "energized_redstone");
		block_fluid_glowstoneinfused_magma = new TRZBlockFluid(FluidRegistry.getFluid("glowstoneinfused_magma"), Material.LAVA, "glowstoneinfused_magma");
		
	}
	 
	public static void register(){
		registerBlock(block_ore_copper);
		registerBlock(block_copper);
		
		registerBlock(block_ore_tin);
		registerBlock(block_tin);
		
		registerBlock(block_ore_silver);
		registerBlock(block_silver);
		
		registerBlock(block_ore_eutine);
		registerBlock(block_eutine);
		
		registerBlock(block_ore_mithril);
		registerBlock(block_mithril);
		
		registerBlock(block_ore_adamantite);
		registerBlock(block_adamantite);
		
		registerBlock(block_ore_rune);
		registerBlock(block_rune);
		
		registerBlock(block_ore_fractum);
		registerBlock(block_fractum);
		
		registerBlock(block_ore_silicon);
		registerBlock(block_silicon);

		registerBlock(block_steel);
		registerBlock(block_bronze);
		registerBlock(block_brass);
		
		registerBlock(block_ore_onyx);
		registerBlock(block_onyx);
		
		registerBlock(block_ore_satsum);
		registerBlock(block_satsum);
		
		registerBlock(block_ore_azurite);
		registerBlock(block_azurite);
		
		registerBlock(block_ore_ruby);
		registerBlock(block_ruby);
		
		registerBlock(block_ore_amythest);
		registerBlock(block_amythest);
		
		registerBlock(block_ore_yellowtopaz);
		registerBlock(block_yellowtopaz);
		
		registerBlock(block_ore_fireopal);
		registerBlock(block_fireopal);
		
		registerBlock(block_ore_turquoise);
		registerBlock(block_turquoise);
		
		registerBlock(block_ore_blackdiamond);
		registerBlock(block_blackdiamond);
		
		registerBlock(block_red);
		
		registerBlock(block_godone);
		registerBlock(block_godtwo);
		registerBlock(block_godthree);
		
		
		
		registerBlock(block_powered_frame);
		registerBlock(block_energized_frame);
		
		
		
		registerBlock(block_powered_capacitor, itemblock_powered_capacitor);
		registerBlock(block_energized_capacitor, itemblock_energized_capacitor);
		
		registerBlock(block_fluidtank, itemblock_fluidtank);
		
		registerBlock(block_mechanised_storage_small);
		registerBlock(block_mechanised_storage_large);
		
		
		
		registerBlock(block_powered_energypipe);
		registerBlock(block_energized_energypipe);
		
		registerBlock(block_itempipe_solid);
		registerBlock(block_itempipe_clear);
		
		registerBlock(block_fluidpipe_solid);
		registerBlock(block_fluidpipe_clear);
		
		
		
		registerBlock(block_powered_furnace);
		registerBlock(block_powered_furnace_on);
		
		registerBlock(block_powered_grinder);
		registerBlock(block_powered_grinder_on);
		
		registerBlock(block_powered_compressor);
		registerBlock(block_powered_compressor_on);
		
		registerBlock(block_energized_furnace);
		registerBlock(block_energized_furnace_on);

		registerBlock(block_energized_grinder);
		registerBlock(block_energized_grinder_on);
		
		registerBlock(block_energized_compressor);
		registerBlock(block_energized_compressor_on);
		
		registerBlock(block_energized_combiner);
		registerBlock(block_energized_combiner_on);
		
		registerBlock(block_powered_charger);
		registerBlock(block_powered_charger_on);
		
		registerBlock(block_powered_oreprocessor);
		registerBlock(block_powered_oreprocessor_on);
		
		registerBlock(block_powered_fluidcrafter);
		registerBlock(block_powered_fluidcrafter_on);
		
		registerBlock(block_combiner_center);
		registerBlock(block_combiner_pedestal);
		
		registerBlock(block_coalgenerator);
		registerBlock(block_coalgenerator_on);
		
		registerBlock(block_heatedfluidgenerator);
		registerBlock(block_heatedfluidgenerator_on);

		registerBlock(block_peltiergenerator);
		registerBlock(block_peltiergenerator_on);
		
		registerBlock(block_processingplant_base);
		registerBlock(block_processingplant_base_multi);
		
		
		registerFluidBlock(block_fluid_coolant);
		registerFluidBlock(block_fluid_energized_redstone);
		registerFluidBlock(block_fluid_glowstoneinfused_magma);
		
	}
	 
	public static void registerModelLocations(){
		registerBlockModelLocation(block_ore_copper);
		registerBlockModelLocation(block_copper);
		
		registerBlockModelLocation(block_ore_tin);
		registerBlockModelLocation(block_tin);
		
		registerBlockModelLocation(block_ore_silver);
		registerBlockModelLocation(block_silver);
		
		registerBlockModelLocation(block_ore_eutine);
		registerBlockModelLocation(block_eutine);
		
		registerBlockModelLocation(block_ore_mithril);
		registerBlockModelLocation(block_mithril);
		
		registerBlockModelLocation(block_ore_adamantite);
		registerBlockModelLocation(block_adamantite);
		
		registerBlockModelLocation(block_ore_rune);
		registerBlockModelLocation(block_rune);
		
		registerBlockModelLocation(block_ore_fractum);
		registerBlockModelLocation(block_fractum);
		
		registerBlockModelLocation(block_ore_silicon);
		registerBlockModelLocation(block_silicon);
		
		registerBlockModelLocation(block_steel);
		registerBlockModelLocation(block_bronze);
		registerBlockModelLocation(block_brass);
		
		
		registerBlockModelLocation(block_ore_onyx);
		registerBlockModelLocation(block_onyx);
		
		registerBlockModelLocation(block_ore_satsum);
		registerBlockModelLocation(block_satsum);
		
		registerBlockModelLocation(block_ore_azurite);
		registerBlockModelLocation(block_azurite);
		
		registerBlockModelLocation(block_ore_ruby);
		registerBlockModelLocation(block_ruby);
		
		registerBlockModelLocation(block_ore_amythest);
		registerBlockModelLocation(block_amythest);
		
		registerBlockModelLocation(block_ore_yellowtopaz);
		registerBlockModelLocation(block_yellowtopaz);
		
		registerBlockModelLocation(block_ore_fireopal);
		registerBlockModelLocation(block_fireopal);
		
		registerBlockModelLocation(block_ore_turquoise);
		registerBlockModelLocation(block_turquoise);
		
		registerBlockModelLocation(block_ore_blackdiamond);
		registerBlockModelLocation(block_blackdiamond);
		
		registerBlockModelLocation(block_red);
		
		registerBlockModelLocation(block_godone);
		registerBlockModelLocation(block_godtwo);
		registerBlockModelLocation(block_godthree);
		
		
		
		registerBlockModelLocation(block_powered_frame);
		registerBlockModelLocation(block_energized_frame);

		
		
		registerBlockModelLocation(block_powered_capacitor);
		registerBlockModelLocation(block_energized_capacitor);
		
		registerBlockModelLocation(block_fluidtank);

		registerBlockModelLocation(block_mechanised_storage_small);
		registerBlockModelLocation(block_mechanised_storage_large);
		
		
		
		registerBlockModelLocation(block_powered_energypipe);
		registerBlockModelLocation(block_energized_energypipe);
		
		registerBlockModelLocation(block_itempipe_solid);
		registerBlockModelLocation(block_itempipe_clear);
		
		registerBlockModelLocation(block_fluidpipe_solid);
		registerBlockModelLocation(block_fluidpipe_clear);
		
		
		
		registerBlockModelLocation(block_powered_furnace);
		registerBlockModelLocation(block_powered_furnace_on);
		
		registerBlockModelLocation(block_powered_grinder);
		registerBlockModelLocation(block_powered_grinder_on);
		
		registerBlockModelLocation(block_powered_compressor);
		registerBlockModelLocation(block_powered_compressor_on);
		
		registerBlockModelLocation(block_energized_furnace);
		registerBlockModelLocation(block_energized_furnace_on);

		registerBlockModelLocation(block_energized_grinder);
		registerBlockModelLocation(block_energized_grinder_on);
		
		registerBlockModelLocation(block_energized_compressor);
		registerBlockModelLocation(block_energized_compressor_on);
		
		registerBlockModelLocation(block_energized_combiner);
		registerBlockModelLocation(block_energized_combiner_on);
		
		registerBlockModelLocation(block_powered_charger);
		registerBlockModelLocation(block_powered_charger_on);
		
		registerBlockModelLocation(block_powered_oreprocessor);
		registerBlockModelLocation(block_powered_oreprocessor_on);
		
		registerBlockModelLocation(block_powered_fluidcrafter);
		registerBlockModelLocation(block_powered_fluidcrafter_on);
		
		registerBlockModelLocation(block_combiner_center);
		registerBlockModelLocation(block_combiner_pedestal);
		
		registerBlockModelLocation(block_coalgenerator);
		registerBlockModelLocation(block_coalgenerator_on);
		
		registerBlockModelLocation(block_heatedfluidgenerator);
		registerBlockModelLocation(block_heatedfluidgenerator_on);

		registerBlockModelLocation(block_peltiergenerator);
		registerBlockModelLocation(block_peltiergenerator_on);
		
		registerBlockModelLocation(block_processingplant_base);
		registerBlockModelLocation(block_processingplant_base_multi);
		
	}
	 
	public static void registerBlock(Block block){
		ItemBlock item = new ItemBlock(block);
		GameRegistry.register(block);
		GameRegistry.register(item, block.getRegistryName());
		
	}
	
	public static void registerBlock(Block block, ItemBlock item){
		GameRegistry.register(block);
		GameRegistry.register(item, block.getRegistryName());
	}
	
	public static void registerFluidBlock(TRZBlockFluid block){
		GameRegistry.register(block);
		registerFluidModelLocation(block);
	}
	
	public static void registerBlockModelLocation(Block block){
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	public static void registerFluidModelLocation(TRZBlockFluid fluid_block){
		Item item = Item.getItemFromBlock(fluid_block);
		
		ModelBakery.registerItemVariants(item);

		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(OreCraft.mod_id + ":" +  "block_fluids", fluid_block.getFluid().getName());
		
		ModelLoader.setCustomMeshDefinition(item, TRZMeshDefinitionFix.create(stack -> modelResourceLocation));

		ModelLoader.setCustomStateMapper((Block) fluid_block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return modelResourceLocation;
			}
		});
		
		GameRegistry.register(item);
	}
	
}
