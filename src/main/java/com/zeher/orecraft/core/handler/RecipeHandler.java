package com.zeher.orecraft.core.handler;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.machine.core.recipe.CombinerCraftingHandler;
import com.zeher.orecraft.storage.core.block.ItemBlockPoweredCapacitor;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeHandler {
	
	public static void init() {
		registerSmelting();
		registerGrinding();
		registerCompressing();
		registerExtracting();
		
		registerCleaning();
		registerRefining();
		
		registerFluidCraft();
		registerFluidMelt();
	}
	
	public static void registerShaped(){/*
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_copper), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.ingot_copper)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_tin), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.ingot_tin)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_silver), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.ingot_silver)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_eutine), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.ingot_eutine)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_mithril), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.ingot_mithril)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_adamantite), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.ingot_adamantite)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_rune), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.ingot_rune)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_fractum), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.ingot_fractum)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_silicon), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.ingot_silicon)});
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_steel), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.ingot_steel)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_bronze), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.ingot_bronze)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_brass), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.ingot_brass)});
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_onyx), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.gem_onyx)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_satsum), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.gem_satsum)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_azurite), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.gem_azurite)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_ruby), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.gem_ruby)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_amythest), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.gem_amythest)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_yellowtopaz), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.gem_yellowtopaz)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_fireopal), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.gem_fireopal)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_turquoise), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.gem_turquoise)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_blackdiamond), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.gem_blackdiamond)});
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_godone), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.gem_godone)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_godtwo), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.gem_godtwo)});
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_godthree), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.gem_godthree)});
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_silicon), new Object[]{"www", "www", "www", Character.valueOf('w'), new ItemStack(ItemHandler.component_silicon_refined)});
		
		*/
		//GameRegistry.addShapedRecipe(new ItemStack(Item.getItemFromBlock(BlockHandler.block_powered_capacitor), 1, Item.getItemFromBlock(BlockHandler.block_powered_capacitor).getMaxDamage()), new Object[]{"xxx", "xwx", "xxx", Character.valueOf('w'), new ItemStack(Items.APPLE)});
		
		
		/**@Electrical*//*
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.component_circuit_one_raw), new Object[]{"ccc", "sis", "rrr",
			Character.valueOf('c'), new ItemStack(ItemHandler.plate_iron),
			Character.valueOf('s'), new ItemStack(ItemHandler.component_silicon_refined),
			Character.valueOf('r'), new ItemStack(Items.REDSTONE),
			Character.valueOf('i'), new ItemStack(ItemHandler.plate_copper)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.component_circuit_one_raw), new Object[]{"rrr", "sis", "ccc",
			Character.valueOf('c'), new ItemStack(ItemHandler.plate_iron),
			Character.valueOf('s'), new ItemStack(ItemHandler.component_silicon_refined),
			Character.valueOf('r'), new ItemStack(Items.REDSTONE),
			Character.valueOf('i'), new ItemStack(ItemHandler.plate_copper)});
			
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.component_circuit_one_raw), new Object[]{"csr", "cir", "csr",
			Character.valueOf('c'), new ItemStack(ItemHandler.plate_iron),
			Character.valueOf('s'), new ItemStack(ItemHandler.component_silicon_refined),
			Character.valueOf('r'), new ItemStack(Items.REDSTONE),
			Character.valueOf('i'), new ItemStack(ItemHandler.plate_copper)});
			
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.component_circuit_one_raw), new Object[]{"csr", "cir", "csr",
			Character.valueOf('r'), new ItemStack(ItemHandler.plate_iron),
			Character.valueOf('s'), new ItemStack(ItemHandler.component_silicon_refined),
			Character.valueOf('c'), new ItemStack(Items.REDSTONE),
			Character.valueOf('i'), new ItemStack(ItemHandler.plate_copper)});
				
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.battery_powered, 1, ItemHandler.battery_powered.getMaxDamage()), new Object[]{ "ywy", "xdx", "awa",
			Character.valueOf('y'), new ItemStack(Items.REDSTONE),
			Character.valueOf('w'), new ItemStack(ItemHandler.plate_copper),
			Character.valueOf('d'), new ItemStack(Items.DIAMOND),
			Character.valueOf('x'), new ItemStack(ItemHandler.dust_basic),
			Character.valueOf('a'), new ItemStack(ItemHandler.component_circuit_one)});
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_powered_frame), new Object[]{"csc", "sis", "csc",
			Character.valueOf('c'), new ItemStack(ItemHandler.plate_tin),
			Character.valueOf('s'), new ItemStack(ItemHandler.plate_copper),
			Character.valueOf('i'), new ItemStack(ItemHandler.plate_steel)});
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_powered_grinder), new Object[]{"csc", "xix", "dwd",
			Character.valueOf('c'), new ItemStack(Items.FLINT),
			Character.valueOf('s'), new ItemStack(Items.REDSTONE),
			Character.valueOf('i'), new ItemStack(BlockHandler.block_powered_frame),
			Character.valueOf('d'), new ItemStack(ItemHandler.component_circuit_one),
			Character.valueOf('w'), new ItemStack(ItemHandler.component_silicon_wafer),
			Character.valueOf('x'), new ItemStack(ItemHandler.plate_iron)});

		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_powered_compressor), new Object[]{"ccc", "xix", "dwd",
			Character.valueOf('c'), new ItemStack(ItemHandler.plate_copper),
			Character.valueOf('i'), new ItemStack(BlockHandler.block_powered_frame),
			Character.valueOf('d'), new ItemStack(ItemHandler.component_circuit_one),
			Character.valueOf('r'), new ItemStack(Items.REDSTONE),
			Character.valueOf('w'), new ItemStack(ItemHandler.component_silicon_wafer),
			Character.valueOf('x'), new ItemStack(ItemHandler.plate_iron)});
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_powered_furnace), new Object[]{"scs", "xix", "dyd",
			Character.valueOf('c'), new ItemStack(ItemHandler.plate_copper),
			Character.valueOf('s'), new ItemStack(Items.REDSTONE),
			Character.valueOf('i'), new ItemStack(Blocks.FURNACE),
			Character.valueOf('y'), new ItemStack(BlockHandler.block_powered_frame),
			Character.valueOf('d'), new ItemStack(ItemHandler.component_circuit_one),
			Character.valueOf('x'), new ItemStack(ItemHandler.plate_iron)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.dust_basic, 4), new Object[]{"grg", "rsr", "grg",
			Character.valueOf('s'), new ItemStack(ItemHandler.component_silicon_refined),
			Character.valueOf('r'), new ItemStack(Items.REDSTONE),
			Character.valueOf('g'), new ItemStack(Items.GLOWSTONE_DUST)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.component_silicon_wafer, 2), new Object[]{"grg", "rsr", "grg",
			Character.valueOf('r'), new ItemStack(ItemHandler.component_silicon_refined),
			Character.valueOf('g'), new ItemStack(ItemHandler.dust_carbon),
			Character.valueOf('s'), new ItemStack(Items.REDSTONE)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.component_silicon_wafer, 2), new Object[]{"rgr", "gsg", "rgr",
			Character.valueOf('r'), new ItemStack(ItemHandler.component_silicon_refined),
			Character.valueOf('g'), new ItemStack(ItemHandler.dust_carbon),
			Character.valueOf('s'), new ItemStack(Items.REDSTONE)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.upgrade_speed), new Object[]{"rgr", "gsg", "rgr",
			Character.valueOf('r'), new ItemStack(ItemHandler.plate_steel),
			Character.valueOf('g'), new ItemStack(Items.GLOWSTONE_DUST),
			Character.valueOf('s'), new ItemStack(ItemHandler.component_silicon_wafer_basic)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.upgrade_speed), new Object[]{"rgr", "gsg", "rgr",
			Character.valueOf('g'), new ItemStack(ItemHandler.plate_steel),
			Character.valueOf('r'), new ItemStack(Items.GLOWSTONE_DUST),
			Character.valueOf('s'), new ItemStack(ItemHandler.component_silicon_wafer_basic)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.upgrade_capacity), new Object[]{"rgr", "gsg", "rgr",
			Character.valueOf('r'), new ItemStack(ItemHandler.plate_steel),
			Character.valueOf('g'), new ItemStack(ItemHandler.dust_basic),
			Character.valueOf('s'), new ItemStack(ItemHandler.component_silicon_wafer_basic)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.upgrade_capacity), new Object[]{"rgr", "gsg", "rgr",
			Character.valueOf('g'), new ItemStack(ItemHandler.plate_steel),
			Character.valueOf('r'), new ItemStack(ItemHandler.dust_basic),
			Character.valueOf('s'), new ItemStack(ItemHandler.component_silicon_wafer_basic)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.component_silicon_wafer_basic, 2), new Object[]{"rgr", "gsg", "rgr",
			Character.valueOf('r'), new ItemStack(ItemHandler.component_silicon_wafer),
			Character.valueOf('g'), new ItemStack(ItemHandler.dust_basic),
			Character.valueOf('s'), new ItemStack(ItemHandler.plate_steel)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.component_silicon_wafer_basic, 2), new Object[]{"rgr", "gsg", "rgr",
			Character.valueOf('g'), new ItemStack(ItemHandler.component_silicon_wafer),
			Character.valueOf('r'), new ItemStack(ItemHandler.dust_basic),
			Character.valueOf('s'), new ItemStack(ItemHandler.plate_steel)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.dust_ender, 2), new Object[]{"rgr", "gsg", "rgr",
			Character.valueOf('r'), new ItemStack(ItemHandler.dust_carbon),
			Character.valueOf('g'), new ItemStack(ItemHandler.dust_basic),
			Character.valueOf('s'), new ItemStack(Items.ENDER_EYE)});

		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.dust_ender, 2), new Object[]{"rgr", "gsg", "rgr",
			Character.valueOf('g'), new ItemStack(ItemHandler.dust_carbon),
			Character.valueOf('r'), new ItemStack(ItemHandler.dust_basic),
			Character.valueOf('s'), new ItemStack(Items.ENDER_EYE)});

		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_energized_frame), new Object[]{"cxc", "rir", "cxc",
			Character.valueOf('c'), new ItemStack(ItemHandler.plate_bronze),
			Character.valueOf('r'), new ItemStack(ItemHandler.component_circuit_one),
			Character.valueOf('x'), new ItemStack(ItemHandler.component_silicon_wafer_basic),
			Character.valueOf('i'), new ItemStack(BlockHandler.block_powered_frame)});
			
		GameRegistry.addShapedRecipe(new ItemStack(BlockHandler.block_energized_frame), new Object[]{"cxc", "rir", "cxc",
			Character.valueOf('c'), new ItemStack(ItemHandler.plate_bronze),
			Character.valueOf('x'), new ItemStack(ItemHandler.component_circuit_one),
			Character.valueOf('r'), new ItemStack(ItemHandler.component_silicon_wafer_basic),
			Character.valueOf('i'), new ItemStack(BlockHandler.block_powered_frame)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.ingot_steel_unrefined, 2), new Object[]{"cxc", "xix", "cxc",
			Character.valueOf('c'), new ItemStack(Items.IRON_INGOT),
			Character.valueOf('x'), new ItemStack(ItemHandler.dust_carbon)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.ingot_steel_unrefined, 2), new Object[]{"cxc", "xix", "cxc",
			Character.valueOf('x'), new ItemStack(Items.IRON_INGOT),
			Character.valueOf('c'), new ItemStack(ItemHandler.dust_carbon)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.dust_steel, 6), new Object[]{"cxc", "xix", "cxc",
			Character.valueOf('c'), new ItemStack(ItemHandler.dust_iron),
			Character.valueOf('x'), new ItemStack(ItemHandler.dust_carbon),
			Character.valueOf('i'), new ItemStack(ItemHandler.component_silicon_wafer)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.dust_steel, 6), new Object[]{"cxc", "xix", "cxc",
			Character.valueOf('c'), new ItemStack(ItemHandler.dust_iron),
			Character.valueOf('x'), new ItemStack(ItemHandler.dust_carbon),
			Character.valueOf('i'), new ItemStack(ItemHandler.component_silicon_wafer)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.tool_machinehammer), new Object[]{"wxx", "wix", "iww",
			Character.valueOf('x'), new ItemStack(Items.IRON_INGOT),
			Character.valueOf('i'), new ItemStack(ItemHandler.ingot_lapis)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.tool_machinewrench), new Object[]{"wxw", "wix", "iww",
			Character.valueOf('x'), new ItemStack(Items.IRON_INGOT),
			Character.valueOf('i'), new ItemStack(ItemHandler.ingot_lapis)});
		
		/**@Tools*//*
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.sword_copper), new Object[]{"xwx", "xwx", "xyx", 
				Character.valueOf('w'), new ItemStack(ItemHandler.ingot_copper), 
				Character.valueOf('y'), new ItemStack(Items.STICK)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.pickaxe_copper), new Object[]{"www", "xyx", "xyx", 
				Character.valueOf('w'), new ItemStack(ItemHandler.ingot_copper), 
				Character.valueOf('y'), new ItemStack(Items.STICK)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.axe_copper), new Object[]{"xww", "xyw", "xyx", 
				Character.valueOf('w'), new ItemStack(ItemHandler.ingot_copper), 
				Character.valueOf('y'), new ItemStack(Items.STICK)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.spade_copper), new Object[]{"xwx", "xyx", "xyx", 
				Character.valueOf('w'), new ItemStack(ItemHandler.ingot_copper),
				Character.valueOf('y'), new ItemStack(Items.STICK)});
		
		/**@Miscellaneous*//*
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.ingot_lapis, 4), new Object[]{"rgr", "gsg", "rgr", 
			Character.valueOf('g'), new ItemStack(Items.DYE, 1, 4),
			Character.valueOf('r'), new ItemStack(ItemHandler.ingot_copper)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.plate_carbon), new Object[]{"ww", "ww",
			Character.valueOf('w'), new ItemStack(ItemHandler.strip_carbon)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.strip_carbon, 2), new Object[]{"www", "wyw", "www",
			Character.valueOf('w'), new ItemStack(ItemHandler.strand_carbon)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.generic_rod_steel, 2), new Object[]{"wy", "wy", 
				Character.valueOf('y'), new ItemStack(ItemHandler.ingot_steel)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.generic_rod_mithril, 2), new Object[]{"wy", "wy",
				Character.valueOf('y'), new ItemStack(ItemHandler.ingot_mithril)});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemHandler.generic_rod_rune, 2), new Object[]{"y", "y", "y",
				Character.valueOf('y'), new ItemStack(ItemHandler.ingot_rune)});
		*/
	}
	
	public static void registerShapeless() {/*
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.ingot_copper, 9), new Object[]{ new ItemStack(BlockHandler.block_copper)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.ingot_silver, 9), new Object[]{ new ItemStack(BlockHandler.block_silver)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.ingot_tin, 9), new Object[]{ new ItemStack(BlockHandler.block_tin)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.ingot_eutine, 9), new Object[]{ new ItemStack(BlockHandler.block_eutine)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.ingot_mithril, 9), new Object[]{ new ItemStack(BlockHandler.block_mithril)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.ingot_adamantite, 9), new Object[]{ new ItemStack(BlockHandler.block_adamantite)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.ingot_rune, 9), new Object[]{ new ItemStack(BlockHandler.block_rune)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.ingot_fractum, 9), new Object[]{ new ItemStack(BlockHandler.block_fractum)});
		
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.ingot_steel, 9), new Object[]{ new ItemStack(BlockHandler.block_steel)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.ingot_bronze, 9), new Object[]{ new ItemStack(BlockHandler.block_bronze)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.ingot_brass, 9), new Object[]{ new ItemStack(BlockHandler.block_brass)});
		
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.gem_onyx, 9), new Object[]{ new ItemStack(BlockHandler.block_onyx)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.gem_satsum, 9), new Object[]{ new ItemStack(BlockHandler.block_satsum)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.gem_azurite, 9), new Object[]{ new ItemStack(BlockHandler.block_azurite)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.gem_ruby, 9), new Object[]{ new ItemStack(BlockHandler.block_ruby)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.gem_amythest, 9), new Object[]{ new ItemStack(BlockHandler.block_amythest)});
		
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.gem_yellowtopaz, 9), new Object[]{ new ItemStack(BlockHandler.block_yellowtopaz)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.gem_fireopal, 9), new Object[]{ new ItemStack(BlockHandler.block_fireopal)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.gem_turquoise, 9), new Object[]{ new ItemStack(BlockHandler.block_turquoise)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.gem_blackdiamond, 9), new Object[]{ new ItemStack(BlockHandler.block_blackdiamond)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.component_silicon_refined, 9), new Object[]{ new ItemStack(BlockHandler.block_silicon)});
		
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.gem_godone, 9), new Object[]{ new ItemStack(BlockHandler.block_godone)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.gem_godtwo, 9), new Object[]{ new ItemStack(BlockHandler.block_godtwo)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.gem_godthree, 9), new Object[]{ new ItemStack(BlockHandler.block_godthree)});
		
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.ingot_bronze, 4), new Object[]{new ItemStack(ItemHandler.ingot_copper), new ItemStack(ItemHandler.ingot_copper), new ItemStack(ItemHandler.ingot_copper), new ItemStack(ItemHandler.ingot_tin)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.dust_bronze, 4), new Object[]{new ItemStack(ItemHandler.dust_copper), new ItemStack(ItemHandler.dust_copper), new ItemStack(ItemHandler.dust_copper), new ItemStack(ItemHandler.dust_tin)});
		
		
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.plate_iron), new Object[]{new ItemStack(ItemHandler.tool_machinehammer, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.IRON_INGOT)});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemHandler.dust_carbon), new Object[]{new ItemStack(ItemHandler.tool_machinehammer, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.COAL)});
		*/
		
		//GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.energyCrystal_4, 1, OreCraftCore.energyCrystal_4.getMaxDamage()), new Object[]{ "ywy", "wdw", "ywy", Character.valueOf('y'), new ItemStack(Items.redstone), Character.valueOf('w'), new ItemStack(OreCraftCore.dustEnergy_4), Character.valueOf('d'), new ItemStack(OreCraftCore.energyCrystal_1, 1, OreCraftCore.energyCrystal_4.getMaxDamage())});
		
		/**
		//Silver
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.silverSword), new Object[]{"xwx", "xwx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Silver), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.silverPickaxe), new Object[]{"www", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Silver), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.silverAxe), new Object[]{"xww", "xyw", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Silver), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.silverSpade), new Object[]{"xwx", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Silver), Character.valueOf('y'), new ItemStack(Items.stick)});
		
		//Eutine
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.eutineSword), new Object[]{"xwx", "xwx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Eutine), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.eutinePickaxe), new Object[]{"www", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Eutine), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.eutineAxe), new Object[]{"xww", "xyw", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Eutine), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.eutineSpade), new Object[]{"xwx", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Eutine), Character.valueOf('y'), new ItemStack(Items.stick)});
		
		//Mithril
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.mithrilSword), new Object[]{"xwx", "xwx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Mithril), Character.valueOf('y'), new ItemStack(OreCraftCore.rodSteel)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.mithrilPickaxe), new Object[]{"www", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Mithril), Character.valueOf('y'), new ItemStack(OreCraftCore.rodSteel)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.mithrilAxe), new Object[]{"xww", "xyw", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Mithril), Character.valueOf('y'), new ItemStack(OreCraftCore.rodSteel)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.mithrilSpade), new Object[]{"xwx", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Mithril), Character.valueOf('y'), new ItemStack(OreCraftCore.rodSteel)});
		
		//Adamantite
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.adamantiteSword), new Object[]{"xwx", "xwx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Adamantite), Character.valueOf('y'), new ItemStack(OreCraftCore.rodSteel)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.adamantitePickaxe), new Object[]{"www", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Adamantite), Character.valueOf('y'), new ItemStack(OreCraftCore.rodSteel)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.adamantiteAxe), new Object[]{"xww", "xyw", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Adamantite), Character.valueOf('y'), new ItemStack(OreCraftCore.rodSteel)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.adamantiteSpade), new Object[]{"xwx", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Adamantite), Character.valueOf('y'), new ItemStack(OreCraftCore.rodSteel)});
		
		//Rune
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.runeSword), new Object[]{"xwx", "xwx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Rune), Character.valueOf('y'), new ItemStack(OreCraftCore.rodMithril)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.runePickaxe), new Object[]{"www", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Rune), Character.valueOf('y'), new ItemStack(OreCraftCore.rodMithril)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.runeAxe), new Object[]{"xww", "xyw", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Rune), Character.valueOf('y'), new ItemStack(OreCraftCore.rodMithril)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.runeSpade), new Object[]{"xwx", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Rune), Character.valueOf('y'), new ItemStack(OreCraftCore.rodMithril)});
		
		//Fractum
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.fractumSword), new Object[]{"xwx", "xwx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Fractum), Character.valueOf('y'), new ItemStack(OreCraftCore.rodMithril)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.fractumPickaxe), new Object[]{"www", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Fractum), Character.valueOf('y'), new ItemStack(OreCraftCore.rodMithril)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.fractumAxe), new Object[]{"xww", "xyw", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Fractum), Character.valueOf('y'), new ItemStack(OreCraftCore.rodMithril)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.fractumSpade), new Object[]{"xwx", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Fractum), Character.valueOf('y'), new ItemStack(OreCraftCore.rodMithril)});
		
		//GOD 1
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.godSword1), new Object[]{"xwx", "xwx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_God_1), Character.valueOf('y'), new ItemStack(OreCraftCore.rodRune)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.godPickaxe1), new Object[]{"www", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_God_1), Character.valueOf('y'), new ItemStack(OreCraftCore.rodRune)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.godAxe1), new Object[]{"xww", "xyw", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_God_1), Character.valueOf('y'), new ItemStack(OreCraftCore.rodRune)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.godSpade1), new Object[]{"xwx", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_God_1), Character.valueOf('y'), new ItemStack(OreCraftCore.rodRune)});
		
		//GOD 2
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.godSword2), new Object[]{"xwx", "xwx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_God_2), Character.valueOf('y'), new ItemStack(OreCraftCore.gem_God_1)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.godPickaxe2), new Object[]{"www", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_God_2), Character.valueOf('y'), new ItemStack(OreCraftCore.gem_God_1)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.godAxe2), new Object[]{"xww", "xyw", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_God_2), Character.valueOf('y'), new ItemStack(OreCraftCore.gem_God_1)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.godSpade2), new Object[]{"xwx", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_God_2), Character.valueOf('y'), new ItemStack(OreCraftCore.gem_God_1)});
		
		//GOD 2
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.godSword3), new Object[]{"xwx", "xwx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_God_3), Character.valueOf('y'), new ItemStack(OreCraftCore.gem_God_2)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.godPickaxe3), new Object[]{"www", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_God_3), Character.valueOf('y'), new ItemStack(OreCraftCore.gem_God_2)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.godAxe3), new Object[]{"xww", "xyw", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_God_3), Character.valueOf('y'), new ItemStack(OreCraftCore.gem_God_2)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.godSpade3), new Object[]{"xwx", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_God_3), Character.valueOf('y'), new ItemStack(OreCraftCore.gem_God_2)});
		
		//Steel
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.steelSword), new Object[]{"xwx", "xwx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Steel), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.steelPickaxe), new Object[]{"www", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Steel), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.steelAxe), new Object[]{"xww", "xyw", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Steel), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.steelSpade), new Object[]{"xwx", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Steel), Character.valueOf('y'), new ItemStack(Items.stick)});
		
		//Bronze
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.bronzeSword), new Object[]{"xwx", "xwx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Bronze), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.bronzePickaxe), new Object[]{"www", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Bronze), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.bronzeAxe), new Object[]{"xww", "xyw", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Bronze), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.bronzeSpade), new Object[]{"xwx", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Bronze), Character.valueOf('y'), new ItemStack(Items.stick)});
		
		//Bronze
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.onyxSword), new Object[]{"xwx", "xwx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_Onyx), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.onyxPickaxe), new Object[]{"www", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_Onyx), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.onyxAxe), new Object[]{"xww", "xyw", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_Onyx), Character.valueOf('y'), new ItemStack(Items.stick)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.onyxSpade), new Object[]{"xwx", "xyx", "xyx", Character.valueOf('w'), new ItemStack(OreCraftCore.gem_Onyx), Character.valueOf('y'), new ItemStack(Items.stick)});
		
		//Miscellaneous
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.rubber_insulation, 8), new Object[]{"ww", "ww", Character.valueOf('w'), new ItemStack(OreCraftCore.rubber)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.blockEnergyCable_basic_copper, 6), new Object[]{"wxw", "ece", "wxw", Character.valueOf('w'), new ItemStack(OreCraftCore.rubber_insulation), Character.valueOf('e'), new ItemStack(OreCraftCore.copperPlate), Character.valueOf('c'), new ItemStack(OreCraftCore.ingot_Copper)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.blockEnergyCable_basic_bronze, 6), new Object[]{"wxw", "ece", "wxw", Character.valueOf('w'), new ItemStack(OreCraftCore.rubber_insulation), Character.valueOf('e'), new ItemStack(OreCraftCore.bronzePlate), Character.valueOf('c'), new ItemStack(OreCraftCore.ingot_Bronze)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.blockEnergyCable_basic_steel, 6), new Object[]{"wxw", "ece", "wxw", Character.valueOf('w'), new ItemStack(OreCraftCore.rubber_insulation), Character.valueOf('e'), new ItemStack(OreCraftCore.steelPlate), Character.valueOf('c'), new ItemStack(OreCraftCore.ingot_Steel)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.blockEnergyCable_basic_iron, 6), new Object[]{"wxw", "ece", "wxw", Character.valueOf('w'), new ItemStack(OreCraftCore.rubber_insulation), Character.valueOf('e'), new ItemStack(OreCraftCore.ironPlate), Character.valueOf('c'), new ItemStack(Items.iron_ingot_)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.blockEnergyCable_basic_gold, 6), new Object[]{"wxw", "ece", "wxw", Character.valueOf('w'), new ItemStack(OreCraftCore.rubber_insulation), Character.valueOf('e'), new ItemStack(OreCraftCore.goldPlate), Character.valueOf('c'), new ItemStack(Items.gold_ingot_)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.blockEnergyCable_basic_tin, 6), new Object[]{"wxw", "ece", "wxw", Character.valueOf('w'), new ItemStack(OreCraftCore.rubber_insulation), Character.valueOf('e'), new ItemStack(OreCraftCore.tinPlate), Character.valueOf('c'), new ItemStack(OreCraftCore.ingot_Tin)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.blockEnergyCable_basic_silver, 6), new Object[]{"wxw", "ece", "wxw", Character.valueOf('w'), new ItemStack(OreCraftCore.rubber_insulation), Character.valueOf('e'), new ItemStack(OreCraftCore.silverPlate), Character.valueOf('c'), new ItemStack(OreCraftCore.ingot_Silver)});
		
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.blockEnergyCable_basic_glass, 6), new Object[]{"www", "ece", "www", Character.valueOf('w'), new ItemStack(OreCraftCore.glassPlate), Character.valueOf('e'), new ItemStack(OreCraftCore.diamondPlate), Character.valueOf('c'), new ItemStack(Items.diamond)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.blockEnergyCable_basic_blackglass, 6), new Object[]{"www", "ece", "www", Character.valueOf('w'), new ItemStack(OreCraftCore.blackGlassPlate), Character.valueOf('e'), new ItemStack(OreCraftCore.diamondPlate), Character.valueOf('c'), new ItemStack(Items.diamond)});
		
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.copperPlate), new Object[]{"ww", Character.valueOf('w'), new ItemStack(OreCraftCore.copperStrip)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.tinPlate), new Object[]{"ww", Character.valueOf('w'), new ItemStack(OreCraftCore.tinStrip)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.silverPlate), new Object[]{"ww", Character.valueOf('w'), new ItemStack(OreCraftCore.silverStrip)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.eutinePlate), new Object[]{"ww", Character.valueOf('w'), new ItemStack(OreCraftCore.eutineStrip)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.mithrilPlate), new Object[]{"ww", Character.valueOf('w'), new ItemStack(OreCraftCore.mithrilStrip)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.adamantitePlate), new Object[]{"ww", Character.valueOf('w'), new ItemStack(OreCraftCore.adamantiteStrip)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.runePlate), new Object[]{"ww", Character.valueOf('w'), new ItemStack(OreCraftCore.runeStrip)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.steelPlate), new Object[]{"ww", Character.valueOf('w'), new ItemStack(OreCraftCore.steelStrip)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.bronzePlate), new Object[]{"ww", Character.valueOf('w'), new ItemStack(OreCraftCore.bronzeStrip)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.ironPlate), new Object[]{"ww", Character.valueOf('w'), new ItemStack(OreCraftCore.ironStrip)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.goldPlate), new Object[]{"ww", Character.valueOf('w'), new ItemStack(OreCraftCore.goldStrip)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.diamondPlate), new Object[]{"ww", "ww", Character.valueOf('w'), new ItemStack(OreCraftCore.diamondStrip)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.glassPlate), new Object[]{"ww", "ww", Character.valueOf('w'), new ItemStack(OreCraftCore.glassStrip)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.blackGlassPlate), new Object[]{"ww", "ww", Character.valueOf('w'), new ItemStack(OreCraftCore.blackGlassStrip)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.carbonStrip), new Object[]{"ww", "ww", Character.valueOf('w'), new ItemStack(OreCraftCore.carbonStrand)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.carbonPlate), new Object[]{"ww", Character.valueOf('w'), new ItemStack(OreCraftCore.carbonStrip)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.reinforcedStone, 16), new Object[]{"wxw", "xyx", "wxw", Character.valueOf('w'), new ItemStack(Blocks.obsidian), Character.valueOf('x'), new ItemStack(Blocks.stone), Character.valueOf('y'), new ItemStack(OreCraftCore.steelPlate)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.reinforcedBrick, 4), new Object[]{"ww", "ww", Character.valueOf('w'), new ItemStack(OreCraftCore.reinforcedStone)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.reinforcedGlass, 8), new Object[]{"wxw", "xyx", "wxw", Character.valueOf('w'), new ItemStack(OreCraftCore.reinforcedStone), Character.valueOf('x'), new ItemStack(Blocks.glass)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.glassViewers, 4), new Object[]{"wxw", "xyx", "wxw", Character.valueOf('w'), new ItemStack(Items.dye), Character.valueOf('x'), new ItemStack(Blocks.glass)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.doorReinforced, 2), new Object[]{"ww", "ww", "ww", Character.valueOf('w'), new ItemStack(OreCraftCore.reinforcedStone)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.doorClear, 2), new Object[]{"ww", "ww", "ww", Character.valueOf('w'), new ItemStack(OreCraftCore.glassViewers)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.electricCircuit, 2), new Object[]{"ror", "xyx", "ror", Character.valueOf('r'), new ItemStack(Items.redstone), Character.valueOf('o'), new ItemStack(OreCraftCore.ingot_Tin), Character.valueOf('x'), new ItemStack(OreCraftCore.rubber_insulation), Character.valueOf('y'), new ItemStack(OreCraftCore.ingot_Copper)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.advancedElectricCircuit), new Object[]{"wor", "ded", "row", Character.valueOf('w'), new ItemStack(Items.dye, 1 , 4), Character.valueOf('o'), new ItemStack(OreCraftCore.ingot_Steel), Character.valueOf('r'), new ItemStack(Items.redstone), Character.valueOf('d'), new ItemStack(Items.diamond), Character.valueOf('e'), new ItemStack(OreCraftCore.electricCircuit)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.ingot_UnrefinedSteel, 2), new Object[]{"wow", "oeo", "wow", Character.valueOf('o'), new ItemStack(OreCraftCore.dustCarbon), Character.valueOf('e'), new ItemStack(Items.iron_ingot_)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.machineFrame_basic), new Object[]{"rtr", "tit", "rtr", Character.valueOf('r'), new ItemStack(Items.redstone), Character.valueOf('t'), new ItemStack(OreCraftCore.ingot_Tin), Character.valueOf('i'), new ItemStack(Blocks.iron_bars)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.machineFrame_ender), new Object[]{"utr", "tit", "rtu", Character.valueOf('r'), new ItemStack(Items.ender_pearl), Character.valueOf('t'), new ItemStack(OreCraftCore.ingot_Steel), Character.valueOf('i'), new ItemStack(OreCraftCore.machineFrame_basic), Character.valueOf('u'), new ItemStack(Items.ender_eye)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.grinderIdle_1), new Object[]{"ftf", "rir", "tet", Character.valueOf('r'), new ItemStack(Items.redstone), Character.valueOf('t'), new ItemStack(OreCraftCore.ingot_Tin), Character.valueOf('i'), new ItemStack(OreCraftCore.machineFrame_basic), Character.valueOf('f'), new ItemStack(Items.flint), Character.valueOf('e'), new ItemStack(OreCraftCore.electricCircuit)});
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.compressorIdle_1), new Object[]{"fef", "rir", "tet", Character.valueOf('r'), new ItemStack(Items.redstone), Character.valueOf('t'), new ItemStack(OreCraftCore.ingot_Tin), Character.valueOf('i'), new ItemStack(OreCraftCore.machineFrame_basic), Character.valueOf('f'), new ItemStack(Blocks.stone), Character.valueOf('e'), new ItemStack(OreCraftCore.electricCircuit)});
		
		GameRegistry.addShapedRecipe(new ItemStack(OreCraftCore.machineWrench), new Object[]{"wew", "ywy", "yxy", Character.valueOf('w'), new ItemStack(OreCraftCore.ingot_Tin), Character.valueOf('x'), new ItemStack(OreCraftCore.ingot_Copper)});
		*/
	}
	
	public static void registerSmelting(){
		GameRegistry.addSmelting(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_COPPER, new ItemStack(ItemHandler.ingot_copper), 1.0f);
		GameRegistry.addSmelting(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_TIN, new ItemStack(ItemHandler.ingot_tin), 1.0f);
		GameRegistry.addSmelting(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_SILVER, new ItemStack(ItemHandler.ingot_silver), 2.0f);
		
		GameRegistry.addSmelting(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_EUTINE, new ItemStack(ItemHandler.ingot_eutine), 2.0f);
		GameRegistry.addSmelting(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_MITHRIL, new ItemStack(ItemHandler.ingot_mithril), 3.0f);
		GameRegistry.addSmelting(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_ADAMANTITE, new ItemStack(ItemHandler.ingot_adamantite), 4.0f);
		GameRegistry.addSmelting(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_RUNE, new ItemStack(ItemHandler.ingot_rune), 5.0f);
		
		GameRegistry.addSmelting(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_SILICON, new ItemStack(ItemHandler.component_silicon_refined), 0.8f);
		
		GameRegistry.addSmelting(ItemHandler.dust_copper, new ItemStack(ItemHandler.ingot_copper), 0.05f);
		GameRegistry.addSmelting(ItemHandler.dust_tin, new ItemStack(ItemHandler.ingot_tin), 0.5f);
		GameRegistry.addSmelting(ItemHandler.dust_silver, new ItemStack(ItemHandler.ingot_silver), 0.5f);
		
		GameRegistry.addSmelting(ItemHandler.dust_eutine, new ItemStack(ItemHandler.ingot_eutine), 0.5f);
		GameRegistry.addSmelting(ItemHandler.dust_mithril, new ItemStack(ItemHandler.ingot_mithril), 0.5f);
		GameRegistry.addSmelting(ItemHandler.dust_adamantite, new ItemStack(ItemHandler.ingot_adamantite), 0.5f);
		GameRegistry.addSmelting(ItemHandler.dust_rune, new ItemStack(ItemHandler.ingot_rune), 0.5f);
		
		GameRegistry.addSmelting(ItemHandler.dust_bronze, new ItemStack(ItemHandler.ingot_bronze), 1.0f);
		
		GameRegistry.addSmelting(ItemHandler.ingot_steel_unrefined, new ItemStack(ItemHandler.ingot_steel), 1.0f);
		GameRegistry.addSmelting(ItemHandler.ingot_fractum_unrefined, new ItemStack(ItemHandler.ingot_fractum), 1.0f);
		
		GameRegistry.addSmelting(ItemHandler.dust_iron, new ItemStack(Items.IRON_INGOT), 1.0f);
		GameRegistry.addSmelting(ItemHandler.dust_gold, new ItemStack(Items.GOLD_INGOT), 1.0f);
		
		GameRegistry.addSmelting(new ItemStack(Items.DYE, 1, 0), new ItemStack(ItemHandler.generic_rubber), 1.0f);
		
		GameRegistry.addSmelting(new ItemStack(ItemHandler.component_silicon), new ItemStack(ItemHandler.component_silicon_refined), 0.5f);
		
		GameRegistry.addSmelting(new ItemStack(ItemHandler.component_circuit_one_raw), new ItemStack(ItemHandler.component_circuit_one), 1f);
		GameRegistry.addSmelting(new ItemStack(ItemHandler.component_circuit_three_raw), new ItemStack(ItemHandler.component_circuit_three), 1f);
		
 	}
	
	public static void registerGrinding(){
		RegistryHandler.addGrinding(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_COPPER, new ItemStack(ItemHandler.dust_copper, 2), 1.0f);
		RegistryHandler.addGrinding(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_TIN, new ItemStack(ItemHandler.dust_tin, 2),  1.0f);
		RegistryHandler.addGrinding(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_SILVER, new ItemStack(ItemHandler.dust_silver, 2), 1.0f);
		RegistryHandler.addGrinding(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_EUTINE, new ItemStack(ItemHandler.dust_eutine, 2), 1.0f);
		RegistryHandler.addGrinding(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_MITHRIL, new ItemStack(ItemHandler.dust_mithril, 2), 1.0f);
		RegistryHandler.addGrinding(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_ADAMANTITE, new ItemStack(ItemHandler.dust_adamantite, 2), 1.0f);
		RegistryHandler.addGrinding(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_RUNE, new ItemStack(ItemHandler.dust_rune, 2), 1.0f);
		
		RegistryHandler.addGrinding(BlockHandler.REGISTRATION.ORE.BLOCK_ORE_SILICON, new ItemStack(ItemHandler.component_silicon, 4), 1.5f);
		
		RegistryHandler.addGrinding(Blocks.IRON_ORE, new ItemStack(ItemHandler.dust_iron, 2), 1.0f);
		RegistryHandler.addGrinding(Blocks.GOLD_ORE, new ItemStack(ItemHandler.dust_gold, 2), 1.0f);
		RegistryHandler.addGrinding(Blocks.COAL_ORE, new ItemStack(ItemHandler.dust_carbon, 4), 1.0f);
		
		RegistryHandler.addGrinding(Blocks.COBBLESTONE, new ItemStack(Blocks.GRAVEL, 2), 1.0f);
		RegistryHandler.addGrinding(Blocks.GRAVEL, new ItemStack(Blocks.SAND, 2), 1.0f);
		
		RegistryHandler.addGrinding(ItemHandler.ingot_copper, new ItemStack(ItemHandler.dust_copper), 0.5f);
		RegistryHandler.addGrinding(ItemHandler.ingot_tin, new ItemStack(ItemHandler.dust_tin), 0.5f);
		RegistryHandler.addGrinding(ItemHandler.ingot_silver, new ItemStack(ItemHandler.dust_silver), 1.0f);
		RegistryHandler.addGrinding(ItemHandler.ingot_eutine, new ItemStack(ItemHandler.dust_eutine), 0.5f);
		RegistryHandler.addGrinding(ItemHandler.ingot_mithril, new ItemStack(ItemHandler.dust_mithril), 0.5f);
		RegistryHandler.addGrinding(ItemHandler.ingot_adamantite, new ItemStack(ItemHandler.dust_adamantite), 0.5f);
		RegistryHandler.addGrinding(ItemHandler.ingot_rune, new ItemStack(ItemHandler.dust_rune), 0.5f);
		RegistryHandler.addGrinding(ItemHandler.ingot_bronze, new ItemStack(ItemHandler.dust_bronze), 0.5f);
		
		RegistryHandler.addGrinding(Items.COAL, new ItemStack(ItemHandler.dust_carbon, 2), 1.0f);
		RegistryHandler.addGrinding(Items.DIAMOND, new ItemStack(ItemHandler.dust_diamond), 1.0f);
	}
	
	public static void registerCompressing(){
		RegistryHandler.addCompressing(ItemHandler.ingot_copper, new ItemStack(ItemHandler.plate_copper), 1.0f);
		RegistryHandler.addCompressing(ItemHandler.ingot_tin, new ItemStack(ItemHandler.plate_tin), 1.0f);
		RegistryHandler.addCompressing(ItemHandler.ingot_silver, new ItemStack(ItemHandler.plate_silver), 1.0f);
		
		RegistryHandler.addCompressing(ItemHandler.ingot_eutine, new ItemStack(ItemHandler.plate_eutine), 1.0f);
		RegistryHandler.addCompressing(ItemHandler.ingot_mithril, new ItemStack(ItemHandler.plate_mithril), 1.0f);
		RegistryHandler.addCompressing(ItemHandler.ingot_adamantite, new ItemStack(ItemHandler.plate_adamantite), 1.0f);
		RegistryHandler.addCompressing(ItemHandler.ingot_rune, new ItemStack(ItemHandler.plate_rune), 1.0f);
		
		RegistryHandler.addCompressing(ItemHandler.ingot_bronze, new ItemStack(ItemHandler.plate_bronze), 1.0f);
		RegistryHandler.addCompressing(ItemHandler.ingot_brass, new ItemStack(ItemHandler.plate_brass), 1.0f);
		RegistryHandler.addCompressing(ItemHandler.ingot_steel, new ItemStack(ItemHandler.plate_steel), 1.0f);
		RegistryHandler.addCompressing(ItemHandler.dust_carbon, new ItemStack(ItemHandler.strand_carbon, 2), 1.0f);
		
		RegistryHandler.addCompressing(Items.IRON_INGOT, new ItemStack(ItemHandler.plate_iron), 1.0f);
		RegistryHandler.addCompressing(Items.GOLD_INGOT, new ItemStack(ItemHandler.plate_gold), 1.0f);
		RegistryHandler.addCompressing(Items.DIAMOND, new ItemStack(ItemHandler.plate_diamond), 1.5f);
		
		RegistryHandler.addCompressing(Blocks.GLASS, new ItemStack(ItemHandler.plate_glass), 1.0f);
	}
	
	public static void registerExtracting(){
		RegistryHandler.addExtracting(Blocks.COAL_BLOCK, new ItemStack(Blocks.DIAMOND_BLOCK), 1.0F);
	}
	
	public static void registerCleaning(){
		RegistryHandler.addProcessorClean(ItemHandler.dust_iron, new ItemStack(ItemHandler.dust_iron_clean), 1.0f);
		RegistryHandler.addProcessorClean(ItemHandler.dust_gold, new ItemStack(ItemHandler.dust_gold_clean), 1.0f);
		
	}
	
	public static void registerRefining(){
		RegistryHandler.addProcessorRefine(ItemHandler.dust_iron_clean, new ItemStack(Items.IRON_INGOT), 1.0f);
		RegistryHandler.addProcessorRefine(ItemHandler.dust_gold_clean, new ItemStack(Items.GOLD_INGOT), 1.0f);
		
	}
	
	public static void registerFluidCraft(){
		
	}
	
	public static void registerFluidMelt(){
		RegistryHandler.addFluidMeltRecipe(Blocks.DIRT, new FluidStack(FluidRegistry.WATER, 1000));
		RegistryHandler.addFluidMeltRecipe(Items.REDSTONE, new FluidStack(FluidRegistry.getFluid("energized_redstone"), 500));
	}
	
}
