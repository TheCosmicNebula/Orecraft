package com.zeher.orecraft.core.handlers;

import com.zeher.orecraft.OreCraft;
import com.zeher.orecraft.storage.item.ItemBatteryDouble;
import com.zeher.orecraft.storage.item.ItemBatterySingle;
import com.zeher.orecraft.storage.item.ItemPoweredCapacitor;
import com.zeher.orecraft.tool.item.ToolDebug;
import com.zeher.trzlib.core.item.TRZItemBase;
import com.zeher.trzlib.core.item.TRZItemEffect;
import com.zeher.trzlib.tool.item.TRZItemMachineWrench;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemHandler {
	
	public static Item ingot_copper;
	public static Item ingot_tin;
	public static Item ingot_silver;
	public static Item ingot_eutine;
	public static Item ingot_mithril;
	public static Item ingot_adamantite;
	public static Item ingot_rune;
	
	public static Item ingot_fractum;
	public static Item ingot_fractum_unrefined;
	public static Item ingot_fractum_raw;
	
	public static Item ingot_silicon;
	public static Item ingot_steel;
	public static Item ingot_steel_unrefined;
	public static Item ingot_bronze;
	public static Item ingot_brass;
	
	public static Item ingot_lapis;
	
	/**Crystals*/
	public static Item gem_onyx;
	public static Item gem_satsum;
	public static Item gem_azurite;
	public static Item gem_ruby;
	public static Item gem_amythest;
	public static Item gem_yellowtopaz;
	public static Item gem_fireopal;
	public static Item gem_turquoise;
	public static Item gem_blackdiamond;
	
	public static Item gem_red;
	public static Item gem_godone;
	public static Item gem_godtwo;
	public static Item gem_godthree;
	
	/**Dust*/
	public static Item dust_copper;
	public static Item dust_tin;
	public static Item dust_silver;
	public static Item dust_eutine;
	public static Item dust_mithril;
	public static Item dust_adamantite;
	public static Item dust_rune;
	
	public static Item dust_bronze;
	public static Item dust_steel;
	public static Item dust_brass;
	public static Item dust_carbon;
	
	public static Item dust_iron;
	public static Item dust_gold;
	public static Item dust_diamond;
	
	public static Item dust_iron_clean;
	public static Item dust_gold_clean;
	
	public static Item dust_basic;
	public static Item dust_ender;
	
	/**Batteries*/
	public static ItemBatterySingle battery_single;
	public static ItemBatteryDouble battery_double;
	public static ItemPoweredCapacitor battery_basic;
	
	/**Electric Components*/
	public static Item component_circuit_one_raw;
	public static Item component_circuit_one;
	public static Item component_circuit_three_raw;
	public static Item component_circuit_three;
	
	public static Item component_silicon;
	public static Item component_silicon_refined;
	public static Item component_silicon_wafer;
	public static Item component_silicon_wafer_basic;
	public static Item component_silicon_wafer_ender;
	
	/**Machine Upgrades*/
	public static Item upgrade_base;
	public static Item upgrade_speed;
	public static Item upgrade_capacity;
	public static Item upgrade_efficiency;
	public static Item upgrade_charge;
	
	public static Item upgrade_generator;
	public static Item upgrade_solar;
	
	public static Item upgrade_fluid_speed;
	public static Item upgrade_fluid_capacity;
	public static Item upgrade_fluid_efficiency;
	
	/**Plate*/
	public static Item plate_copper;
	public static Item plate_tin;
	public static Item plate_silver;
	public static Item plate_eutine;
	public static Item plate_mithril;
	public static Item plate_adamantite;
	public static Item plate_rune;

	public static Item plate_bronze;
	public static Item plate_brass;
	public static Item plate_steel;
	public static Item plate_carbon;
	
	public static Item plate_iron;
	public static Item plate_gold;
	public static Item plate_diamond;
	
	public static Item plate_glass;
	public static Item plate_blackglass;
	public static Item plate_reinforced;
	public static Item plate_witherreinforced;
	public static Item plate_ender;
	
	/**Strand*/
	public static Item strand_carbon;
	public static Item strip_carbon;
	
	/**Generic*/
	public static Item generic_rubber;
	public static Item generic_rubber_insulation;
	public static Item generic_rod_steel;
	public static Item generic_rod_mithril;
	public static Item generic_rod_rune;
	public static Item generic_rod_tool;
	
	/**Tool*/
	public static TRZItemMachineWrench tool_machinewrench;
	public static TRZItemMachineWrench tool_machinehammer;
	public static ToolDebug tool_debug;
	
	public static void preInit(){
		ingot_copper = new TRZItemBase("ingot_copper", OreCraft.tab_orecraft_materials);
		ingot_tin = new TRZItemBase("ingot_tin", OreCraft.tab_orecraft_materials);
		ingot_silver = new TRZItemBase("ingot_silver", OreCraft.tab_orecraft_materials);
		ingot_eutine = new TRZItemBase("ingot_eutine", OreCraft.tab_orecraft_materials);
		ingot_mithril = new TRZItemBase("ingot_mithril", OreCraft.tab_orecraft_materials);
		ingot_adamantite = new TRZItemBase("ingot_adamantite", OreCraft.tab_orecraft_materials);
		ingot_rune = new TRZItemBase("ingot_rune", OreCraft.tab_orecraft_materials);
		ingot_fractum = new TRZItemBase("ingot_fractum", OreCraft.tab_orecraft_materials);
		ingot_fractum_unrefined = new TRZItemBase("ingot_fractum_unrefined", OreCraft.tab_orecraft_materials);
		ingot_fractum_raw = new TRZItemBase("ingot_fractum_raw", OreCraft.tab_orecraft_materials);
		
		ingot_silicon = new TRZItemBase("ingot_silicon", OreCraft.tab_orecraft_materials);
		ingot_steel = new TRZItemBase("ingot_steel", OreCraft.tab_orecraft_materials);
		ingot_steel_unrefined = new TRZItemBase("ingot_steel_unrefined", OreCraft.tab_orecraft_materials);
		ingot_bronze = new TRZItemBase("ingot_bronze", OreCraft.tab_orecraft_materials);
		ingot_brass = new TRZItemBase("ingot_brass", OreCraft.tab_orecraft_materials);
		
		ingot_lapis = new TRZItemBase("ingot_lapis", OreCraft.tab_orecraft_materials);
		
		gem_onyx = new TRZItemBase("gem_onyx", OreCraft.tab_orecraft_materials);
		gem_satsum = new TRZItemBase("gem_satsum", OreCraft.tab_orecraft_materials);
		gem_azurite = new TRZItemBase("gem_azurite", OreCraft.tab_orecraft_materials);
		gem_ruby = new TRZItemBase("gem_ruby", OreCraft.tab_orecraft_materials);
		gem_amythest = new TRZItemBase("gem_amythest", OreCraft.tab_orecraft_materials);
		gem_yellowtopaz = new TRZItemBase("gem_yellowtopaz", OreCraft.tab_orecraft_materials);
		gem_fireopal = new TRZItemBase("gem_fireopal", OreCraft.tab_orecraft_materials);
		gem_turquoise = new TRZItemBase("gem_turquoise", OreCraft.tab_orecraft_materials);
		gem_blackdiamond = new TRZItemBase("gem_blackdiamond", OreCraft.tab_orecraft_materials);
		
		gem_red = new TRZItemBase("gem_red", OreCraft.tab_orecraft_materials);
		gem_godone = new TRZItemEffect("gem_godone", OreCraft.tab_orecraft_materials);
		gem_godtwo = new TRZItemEffect("gem_godtwo", OreCraft.tab_orecraft_materials);
		gem_godthree = new TRZItemEffect("gem_godthree", OreCraft.tab_orecraft_materials);
		
		dust_copper = new TRZItemBase("dust_copper", OreCraft.tab_orecraft_materials);
		dust_tin = new TRZItemBase("dust_tin", OreCraft.tab_orecraft_materials);
		dust_silver = new TRZItemBase("dust_silver", OreCraft.tab_orecraft_materials);
		dust_eutine = new TRZItemBase("dust_eutine", OreCraft.tab_orecraft_materials);
		dust_mithril = new TRZItemBase("dust_mithril", OreCraft.tab_orecraft_materials);
		dust_adamantite = new TRZItemBase("dust_adamantite", OreCraft.tab_orecraft_materials);
		dust_rune = new TRZItemBase("dust_rune", OreCraft.tab_orecraft_materials);
		
		dust_bronze = new TRZItemBase("dust_bronze", OreCraft.tab_orecraft_materials);
		dust_steel = new TRZItemBase("dust_steel", OreCraft.tab_orecraft_materials);
		dust_brass = new TRZItemBase("dust_brass", OreCraft.tab_orecraft_materials);
		dust_carbon = new TRZItemBase("dust_carbon", OreCraft.tab_orecraft_materials);
		
		dust_iron = new TRZItemBase("dust_iron", OreCraft.tab_orecraft_materials);
		dust_gold = new TRZItemBase("dust_gold", OreCraft.tab_orecraft_materials);
		dust_diamond = new TRZItemBase("dust_diamond", OreCraft.tab_orecraft_materials);
		
		dust_iron_clean = new TRZItemBase("dust_iron_clean", OreCraft.tab_orecraft_materials);
		dust_gold_clean = new TRZItemBase("dust_gold_clean", OreCraft.tab_orecraft_materials);
		
		dust_basic = new TRZItemBase("dust_basic", OreCraft.tab_orecraft_materials);
		dust_ender = new TRZItemBase("dust_ender", OreCraft.tab_orecraft_materials);
		
		battery_single = new ItemBatterySingle("battery_single", 40, OreCraft.tab_orecraft_energy);
		battery_double = new ItemBatteryDouble("battery_double", 120, OreCraft.tab_orecraft_energy);
		
		battery_basic = new ItemPoweredCapacitor("battery_basic", 400, OreCraft.tab_orecraft_energy);
		
		component_circuit_one_raw = new TRZItemBase("component_circuit_one_raw", OreCraft.tab_orecraft_energy);
		component_circuit_one = new TRZItemBase("component_circuit_one", OreCraft.tab_orecraft_energy);
		component_circuit_three_raw = new TRZItemBase("component_circuit_three_raw", OreCraft.tab_orecraft_energy);
		component_circuit_three = new TRZItemBase("component_circuit_three", OreCraft.tab_orecraft_energy);
		
		component_silicon = new TRZItemBase("component_silicon", OreCraft.tab_orecraft_materials);
		component_silicon_refined = new TRZItemBase("component_silicon_refined", OreCraft.tab_orecraft_materials);
		component_silicon_wafer = new TRZItemBase("component_silicon_wafer", OreCraft.tab_orecraft_materials);
		component_silicon_wafer_basic = new TRZItemBase("component_silicon_wafer_basic", OreCraft.tab_orecraft_materials);
		component_silicon_wafer_ender = new TRZItemBase("component_silicon_wafer_ender", OreCraft.tab_orecraft_materials);
		
		upgrade_base = new TRZItemBase("upgrade_base", OreCraft.tab_orecraft_energy, 64);
		upgrade_speed = new TRZItemBase("upgrade_speed", OreCraft.tab_orecraft_energy, 8);
		upgrade_capacity = new TRZItemBase("upgrade_capacity", OreCraft.tab_orecraft_energy, 8);
		upgrade_efficiency = new TRZItemBase("upgrade_efficiency", OreCraft.tab_orecraft_energy, 8);
		
		upgrade_charge = new TRZItemBase("upgrade_charge", OreCraft.tab_orecraft_energy, 8);
		upgrade_generator = new TRZItemBase("upgrade_generator", OreCraft.tab_orecraft_energy, 8);
		upgrade_solar = new TRZItemBase("upgrade_solar", OreCraft.tab_orecraft_energy, 8);
		
		upgrade_fluid_speed = new TRZItemBase("upgrade_fluid_speed", OreCraft.tab_orecraft_energy, 8);
		upgrade_fluid_capacity = new TRZItemBase("upgrade_fluid_capacity", OreCraft.tab_orecraft_energy, 8);
		upgrade_fluid_efficiency = new TRZItemBase("upgrade_fluid_efficiency", OreCraft.tab_orecraft_energy, 8);
		
		plate_copper = new TRZItemBase("plate_copper", OreCraft.tab_orecraft_materials);
		plate_tin = new TRZItemBase("plate_tin", OreCraft.tab_orecraft_materials);
		plate_silver = new TRZItemBase("plate_silver", OreCraft.tab_orecraft_materials);
		plate_eutine = new TRZItemBase("plate_eutine", OreCraft.tab_orecraft_materials);
		plate_mithril = new TRZItemBase("plate_mithril", OreCraft.tab_orecraft_materials);
		plate_adamantite = new TRZItemBase("plate_adamantite", OreCraft.tab_orecraft_materials);
		plate_rune = new TRZItemBase("plate_rune", OreCraft.tab_orecraft_materials);
		
		plate_bronze = new TRZItemBase("plate_bronze", OreCraft.tab_orecraft_materials);
		plate_brass = new TRZItemBase("plate_brass", OreCraft.tab_orecraft_materials);
		plate_steel = new TRZItemBase("plate_steel", OreCraft.tab_orecraft_materials);
		plate_carbon = new TRZItemBase("plate_carbon", OreCraft.tab_orecraft_materials);
		
		plate_iron = new TRZItemBase("plate_iron", OreCraft.tab_orecraft_materials);
		plate_gold = new TRZItemBase("plate_gold", OreCraft.tab_orecraft_materials);
		plate_diamond = new TRZItemBase("plate_diamond", OreCraft.tab_orecraft_materials);
		
		plate_glass = new TRZItemBase("plate_glass", OreCraft.tab_orecraft_materials);
		plate_blackglass = new TRZItemBase("plate_blackglass", OreCraft.tab_orecraft_materials);
		plate_reinforced = new TRZItemBase("plate_reinforced", OreCraft.tab_orecraft_materials);
		plate_witherreinforced = new TRZItemBase("plate_witherreinforced", OreCraft.tab_orecraft_materials);
		plate_ender = new TRZItemBase("plate_ender", OreCraft.tab_orecraft_materials);
		
		strip_carbon = new TRZItemBase("strip_carbon", OreCraft.tab_orecraft_materials);
		strand_carbon = new TRZItemBase("strand_carbon", OreCraft.tab_orecraft_materials);
		
		generic_rubber = new TRZItemBase("generic_rubber", OreCraft.tab_orecraft_materials);
		generic_rubber_insulation = new TRZItemBase("generic_rubber_insulation", OreCraft.tab_orecraft_materials);
		generic_rod_steel = new TRZItemBase("generic_rod_steel", OreCraft.tab_orecraft_materials);
		generic_rod_mithril = new TRZItemBase("generic_rod_mithril", OreCraft.tab_orecraft_materials);
		generic_rod_rune = new TRZItemBase("generic_rod_rune", OreCraft.tab_orecraft_materials);
		generic_rod_tool = new TRZItemBase("generic_rod_tool", OreCraft.tab_orecraft_tools);
		
		
		tool_machinewrench = new TRZItemMachineWrench("tool_machinewrench", OreCraft.tab_orecraft_tools);
		tool_machinehammer = new TRZItemMachineWrench("tool_machinehammer", OreCraft.tab_orecraft_tools);
		
		tool_debug = new ToolDebug("tool_debug", OreCraft.tab_orecraft_tools, 1);
		
		UniversalBucket bucket_coolant = new UniversalBucket();
	}
	 
	public static void register(){
		GameRegistry.register(ingot_copper);
		GameRegistry.register(ingot_tin);
		GameRegistry.register(ingot_silver);
		GameRegistry.register(ingot_eutine);
		GameRegistry.register(ingot_mithril);
		GameRegistry.register(ingot_adamantite);
		GameRegistry.register(ingot_rune);
		GameRegistry.register(ingot_fractum);
		GameRegistry.register(ingot_fractum_unrefined);
		GameRegistry.register(ingot_fractum_raw);
		GameRegistry.register(ingot_silicon);
		GameRegistry.register(ingot_steel);
		GameRegistry.register(ingot_steel_unrefined);
		GameRegistry.register(ingot_bronze);
		GameRegistry.register(ingot_brass);
		
		GameRegistry.register(ingot_lapis);
		
		GameRegistry.register(gem_onyx);
		GameRegistry.register(gem_satsum);
		GameRegistry.register(gem_azurite);
		GameRegistry.register(gem_ruby);
		GameRegistry.register(gem_amythest);
		GameRegistry.register(gem_yellowtopaz);
		GameRegistry.register(gem_fireopal);
		GameRegistry.register(gem_turquoise);
		GameRegistry.register(gem_blackdiamond);
		GameRegistry.register(gem_red);
		
		GameRegistry.register(gem_godone);
		GameRegistry.register(gem_godtwo);
		GameRegistry.register(gem_godthree);
		
		GameRegistry.register(dust_copper);
		GameRegistry.register(dust_tin);
		GameRegistry.register(dust_silver);
		GameRegistry.register(dust_eutine);
		GameRegistry.register(dust_mithril);
		GameRegistry.register(dust_adamantite);
		GameRegistry.register(dust_rune);
		
		GameRegistry.register(dust_bronze);
		GameRegistry.register(dust_steel);
		GameRegistry.register(dust_brass);
		GameRegistry.register(dust_carbon);
		
		GameRegistry.register(dust_iron);
		GameRegistry.register(dust_gold);
		GameRegistry.register(dust_diamond);
		
		GameRegistry.register(dust_iron_clean);
		GameRegistry.register(dust_gold_clean);
		
		GameRegistry.register(dust_basic);
		GameRegistry.register(dust_ender);
		
		GameRegistry.register(battery_single);
		GameRegistry.register(battery_double);
		
		GameRegistry.register(battery_basic);
		
		GameRegistry.register(component_circuit_one_raw);
		GameRegistry.register(component_circuit_one);
		GameRegistry.register(component_circuit_three_raw);
		GameRegistry.register(component_circuit_three);
		
		GameRegistry.register(component_silicon);
		GameRegistry.register(component_silicon_refined);
		GameRegistry.register(component_silicon_wafer);
		GameRegistry.register(component_silicon_wafer_basic);
		GameRegistry.register(component_silicon_wafer_ender);
		
		GameRegistry.register(upgrade_base);
		GameRegistry.register(upgrade_speed);
		GameRegistry.register(upgrade_capacity);
		GameRegistry.register(upgrade_efficiency);
		
		GameRegistry.register(upgrade_charge);
		GameRegistry.register(upgrade_generator);
		GameRegistry.register(upgrade_solar);
		
		GameRegistry.register(upgrade_fluid_speed);
		GameRegistry.register(upgrade_fluid_capacity);
		GameRegistry.register(upgrade_fluid_efficiency);
		
		GameRegistry.register(plate_copper);
		GameRegistry.register(plate_tin);
		GameRegistry.register(plate_silver);
		GameRegistry.register(plate_eutine);
		GameRegistry.register(plate_mithril);
		GameRegistry.register(plate_adamantite);
		GameRegistry.register(plate_rune);
		
		GameRegistry.register(plate_bronze);
		GameRegistry.register(plate_brass);
		GameRegistry.register(plate_steel);
		GameRegistry.register(plate_carbon);
		
		GameRegistry.register(plate_iron);
		GameRegistry.register(plate_gold);
		GameRegistry.register(plate_diamond);
		
		GameRegistry.register(plate_glass);
		GameRegistry.register(plate_blackglass);
		GameRegistry.register(plate_reinforced);
		GameRegistry.register(plate_witherreinforced);
		GameRegistry.register(plate_ender);
		
		GameRegistry.register(strip_carbon);
		GameRegistry.register(strand_carbon);
		
		GameRegistry.register(generic_rubber);
		GameRegistry.register(generic_rubber_insulation);
		GameRegistry.register(generic_rod_steel);
		GameRegistry.register(generic_rod_mithril);
		GameRegistry.register(generic_rod_rune);
		GameRegistry.register(generic_rod_tool);
		
		GameRegistry.register(tool_machinewrench);
		GameRegistry.register(tool_machinehammer);
		
		GameRegistry.register(tool_debug);
	}
	 
	public static void registerModelLocations(){
		registerItemModelLocation(ingot_copper);
		registerItemModelLocation(ingot_tin);
		registerItemModelLocation(ingot_silver);
		registerItemModelLocation(ingot_eutine);
		registerItemModelLocation(ingot_mithril);
		registerItemModelLocation(ingot_adamantite);
		registerItemModelLocation(ingot_rune);
		registerItemModelLocation(ingot_fractum);
		registerItemModelLocation(ingot_fractum_unrefined);
		registerItemModelLocation(ingot_fractum_raw);
		registerItemModelLocation(ingot_silicon);
		registerItemModelLocation(ingot_steel);
		registerItemModelLocation(ingot_steel_unrefined);
		registerItemModelLocation(ingot_bronze);
		registerItemModelLocation(ingot_brass);
		
		registerItemModelLocation(ingot_lapis);
		
		registerItemModelLocation(gem_onyx);
		registerItemModelLocation(gem_satsum);
		registerItemModelLocation(gem_azurite);
		registerItemModelLocation(gem_ruby);
		registerItemModelLocation(gem_amythest);
		registerItemModelLocation(gem_yellowtopaz);
		registerItemModelLocation(gem_fireopal);
		registerItemModelLocation(gem_turquoise);
		registerItemModelLocation(gem_blackdiamond);
		registerItemModelLocation(gem_red);
		registerItemModelLocation(gem_godone);
		registerItemModelLocation(gem_godtwo);
		registerItemModelLocation(gem_godthree);
		
		registerItemModelLocation(dust_copper);
		registerItemModelLocation(dust_tin);
		registerItemModelLocation(dust_silver);
		registerItemModelLocation(dust_eutine);
		registerItemModelLocation(dust_mithril);
		registerItemModelLocation(dust_adamantite);
		registerItemModelLocation(dust_rune);
		
		registerItemModelLocation(dust_bronze);
		registerItemModelLocation(dust_steel);
		registerItemModelLocation(dust_brass);
		registerItemModelLocation(dust_carbon);
		
		registerItemModelLocation(dust_iron);
		registerItemModelLocation(dust_gold);
		registerItemModelLocation(dust_diamond);
		
		registerItemModelLocation(dust_iron_clean);
		registerItemModelLocation(dust_gold_clean);
		
		registerItemModelLocation(dust_basic);
		registerItemModelLocation(dust_ender);
		
		registerItemModelLocation(battery_single);
		registerItemModelLocation(battery_double);
		
		registerItemModelLocation(battery_basic);
		
		registerItemModelLocation(component_circuit_one_raw);
		registerItemModelLocation(component_circuit_one);
		registerItemModelLocation(component_circuit_three_raw);
		registerItemModelLocation(component_circuit_three);
		
		registerItemModelLocation(component_silicon);
		registerItemModelLocation(component_silicon_refined);
		registerItemModelLocation(component_silicon_wafer);
		registerItemModelLocation(component_silicon_wafer_basic);
		registerItemModelLocation(component_silicon_wafer_ender);
		
		registerItemModelLocation(upgrade_base);
		registerItemModelLocation(upgrade_speed);
		registerItemModelLocation(upgrade_capacity);
		registerItemModelLocation(upgrade_efficiency);
		
		registerItemModelLocation(upgrade_charge);
		registerItemModelLocation(upgrade_generator);
		registerItemModelLocation(upgrade_solar);
		
		registerItemModelLocation(upgrade_fluid_speed);
		registerItemModelLocation(upgrade_fluid_capacity);
		registerItemModelLocation(upgrade_fluid_efficiency);
		
		registerItemModelLocation(plate_copper);
		registerItemModelLocation(plate_tin);
		registerItemModelLocation(plate_silver);
		registerItemModelLocation(plate_eutine);
		registerItemModelLocation(plate_mithril);
		registerItemModelLocation(plate_adamantite);
		registerItemModelLocation(plate_rune);
		
		registerItemModelLocation(plate_bronze);
		registerItemModelLocation(plate_brass);
		registerItemModelLocation(plate_steel);
		registerItemModelLocation(plate_carbon);
		
		registerItemModelLocation(plate_iron);
		registerItemModelLocation(plate_gold);
		registerItemModelLocation(plate_diamond);
		
		registerItemModelLocation(plate_glass);
		registerItemModelLocation(plate_blackglass);
		registerItemModelLocation(plate_reinforced);
		registerItemModelLocation(plate_witherreinforced);
		registerItemModelLocation(plate_ender);
		
		registerItemModelLocation(strip_carbon);
		registerItemModelLocation(strand_carbon);
		
		registerItemModelLocation(generic_rubber);
		registerItemModelLocation(generic_rubber_insulation);
		registerItemModelLocation(generic_rod_steel);
		registerItemModelLocation(generic_rod_mithril);
		registerItemModelLocation(generic_rod_rune);
		registerItemModelLocation(generic_rod_tool);
		
		registerItemModelLocation(tool_machinewrench);
		registerItemModelLocation(tool_machinehammer);
		
		registerItemModelLocation(tool_debug);
		
	}
	 
	public static void registerItemModelLocation(Item item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}