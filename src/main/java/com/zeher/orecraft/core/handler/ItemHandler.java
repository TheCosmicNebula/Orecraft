package com.zeher.orecraft.core.handler;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.core.reference.OrecraftReference;
import com.zeher.orecraft.storage.core.item.ItemBatteryDouble;
import com.zeher.orecraft.storage.core.item.ItemBatterySingle;
import com.zeher.orecraft.storage.core.item.ItemEnergizedCapacitor;
import com.zeher.orecraft.storage.core.item.ItemPoweredCapacitor;
import com.zeher.orecraft.tool.core.item.ToolDebug;
import com.zeher.zeherlib.core.item.ItemBase;
import com.zeher.zeherlib.core.item.ItemEffect;
import com.zeher.zeherlib.core.item.ItemUpgrade;
import com.zeher.zeherlib.core.item.ItemUpgradeFluid;
import com.zeher.zeherlib.tool.item.MachineWrench;
import com.zeher.zeherlib.tool.item.ModAxe;
import com.zeher.zeherlib.tool.item.ModPickaxe;
import com.zeher.zeherlib.tool.item.ModSpade;
import com.zeher.zeherlib.tool.item.ModSword;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Orecraft.MOD_ID)
public class ItemHandler {
	
	public static final Item ingot_copper = new ItemBase("ingot_copper", Orecraft.TAB_MATERIALS);
	public static final Item ingot_tin = new ItemBase("ingot_tin", Orecraft.TAB_MATERIALS);
	public static final Item ingot_silver = new ItemBase("ingot_silver", Orecraft.TAB_MATERIALS);
	public static final Item ingot_eutine = new ItemBase("ingot_eutine", Orecraft.TAB_MATERIALS);
	public static final Item ingot_mithril = new ItemBase("ingot_mithril", Orecraft.TAB_MATERIALS);
	public static final Item ingot_adamantite = new ItemBase("ingot_adamantite", Orecraft.TAB_MATERIALS);
	public static final Item ingot_rune = new ItemBase("ingot_rune", Orecraft.TAB_MATERIALS);
	public static final Item ingot_fractum = new ItemBase("ingot_fractum", Orecraft.TAB_MATERIALS);
	public static final Item ingot_fractum_unrefined = new ItemBase("ingot_fractum_unrefined", Orecraft.TAB_MATERIALS);
	public static final Item ingot_fractum_raw = new ItemBase("ingot_fractum_raw", Orecraft.TAB_MATERIALS);
	
	public static final Item ingot_silicon = new ItemBase("ingot_silicon", Orecraft.TAB_MATERIALS);
	public static final Item ingot_steel = new ItemBase("ingot_steel", Orecraft.TAB_MATERIALS);
	public static final Item ingot_steel_unrefined = new ItemBase("ingot_steel_unrefined", Orecraft.TAB_MATERIALS);
	public static final Item ingot_bronze = new ItemBase("ingot_bronze", Orecraft.TAB_MATERIALS);
	public static final Item ingot_brass = new ItemBase("ingot_brass", Orecraft.TAB_MATERIALS);
	
	public static final Item ingot_lapis = new ItemBase("ingot_lapis", Orecraft.TAB_MATERIALS);
	
	public static final Item gem_onyx = new ItemBase("gem_onyx", Orecraft.TAB_MATERIALS);
	public static final Item gem_satsum = new ItemBase("gem_satsum", Orecraft.TAB_MATERIALS);
	public static final Item gem_azurite = new ItemBase("gem_azurite", Orecraft.TAB_MATERIALS);
	public static final Item gem_ruby = new ItemBase("gem_ruby", Orecraft.TAB_MATERIALS);
	public static final Item gem_amythest = new ItemBase("gem_amythest", Orecraft.TAB_MATERIALS);
	
	public static final Item gem_yellowtopaz = new ItemBase("gem_yellowtopaz", Orecraft.TAB_MATERIALS);
	public static final Item gem_fireopal = new ItemBase("gem_fireopal", Orecraft.TAB_MATERIALS);
	public static final Item gem_turquoise = new ItemBase("gem_turquoise", Orecraft.TAB_MATERIALS);
	public static final Item gem_blackdiamond = new ItemBase("gem_blackdiamond", Orecraft.TAB_MATERIALS);
	
	public static final Item gem_godone = new ItemEffect("gem_godone", Orecraft.TAB_MATERIALS);
	public static final Item gem_godtwo = new ItemEffect("gem_godtwo", Orecraft.TAB_MATERIALS);
	public static final Item gem_godthree = new ItemEffect("gem_godthree", Orecraft.TAB_MATERIALS);
	
	public static final Item dust_copper = new ItemBase("dust_copper", Orecraft.TAB_MATERIALS);
	public static final Item dust_tin = new ItemBase("dust_tin", Orecraft.TAB_MATERIALS);
	public static final Item dust_silver = new ItemBase("dust_silver", Orecraft.TAB_MATERIALS);
	public static final Item dust_eutine = new ItemBase("dust_eutine", Orecraft.TAB_MATERIALS);
	public static final Item dust_mithril = new ItemBase("dust_mithril", Orecraft.TAB_MATERIALS);
	public static final Item dust_adamantite = new ItemBase("dust_adamantite", Orecraft.TAB_MATERIALS);
	public static final Item dust_rune = new ItemBase("dust_rune", Orecraft.TAB_MATERIALS);
	
	public static final Item dust_bronze = new ItemBase("dust_bronze", Orecraft.TAB_MATERIALS);
	public static final Item dust_steel = new ItemBase("dust_steel", Orecraft.TAB_MATERIALS);
	public static final Item dust_brass = new ItemBase("dust_brass", Orecraft.TAB_MATERIALS);
	public static final Item dust_carbon = new ItemBase("dust_carbon", Orecraft.TAB_MATERIALS);
	
	public static final Item dust_iron = new ItemBase("dust_iron", Orecraft.TAB_MATERIALS);
	public static final Item dust_gold = new ItemBase("dust_gold", Orecraft.TAB_MATERIALS);
	public static final Item dust_diamond = new ItemBase("dust_diamond", Orecraft.TAB_MATERIALS);
	
	public static final Item dust_iron_clean = new ItemBase("dust_iron_clean", Orecraft.TAB_MATERIALS);
	public static final Item dust_gold_clean = new ItemBase("dust_gold_clean", Orecraft.TAB_MATERIALS);
	
	public static final Item dust_basic = new ItemBase("dust_basic", Orecraft.TAB_MATERIALS);
	public static final Item dust_ender = new ItemBase("dust_ender", Orecraft.TAB_MATERIALS);
	
	public static final Item battery_small = new ItemBatterySingle("battery_small", 400, Orecraft.TAB_ENERGY);
	public static final Item battery_large = new ItemBatteryDouble("battery_large", 1200, Orecraft.TAB_ENERGY);
	
	public static final Item battery_powered = new ItemPoweredCapacitor("battery_powered", 4000, Orecraft.TAB_ENERGY);
	public static final Item battery_energized = new ItemEnergizedCapacitor("battery_energized", 12000, Orecraft.TAB_ENERGY);
	
	public static final Item component_circuit_one_raw = new ItemBase("component_circuit_one_raw", Orecraft.TAB_ENERGY);
	public static final Item component_circuit_one = new ItemBase("component_circuit_one", Orecraft.TAB_ENERGY);
	public static final Item component_circuit_three_raw = new ItemBase("component_circuit_three_raw", Orecraft.TAB_ENERGY);
	public static final Item component_circuit_three = new ItemBase("component_circuit_three", Orecraft.TAB_ENERGY);
	
	public static final Item component_silicon = new ItemBase("component_silicon", Orecraft.TAB_MATERIALS);
	public static final Item component_silicon_refined = new ItemBase("component_silicon_refined", Orecraft.TAB_MATERIALS);
	public static final Item component_silicon_wafer = new ItemBase("component_silicon_wafer", Orecraft.TAB_MATERIALS);
	public static final Item component_silicon_wafer_basic = new ItemBase("component_silicon_wafer_basic", Orecraft.TAB_MATERIALS);
	public static final Item component_silicon_wafer_ender = new ItemBase("component_silicon_wafer_ender", Orecraft.TAB_MATERIALS);
	
	public static final Item upgrade_base = new ItemUpgrade("upgrade_base", Orecraft.TAB_ENERGY, 64);
	public static final Item upgrade_speed = new ItemUpgrade("upgrade_speed", Orecraft.TAB_ENERGY, 8);
	public static final Item upgrade_capacity = new ItemUpgrade("upgrade_capacity", Orecraft.TAB_ENERGY, 8);
	public static final Item upgrade_efficiency = new ItemUpgrade("upgrade_efficiency", Orecraft.TAB_ENERGY, 8);
	
	public static final Item upgrade_charge = new ItemUpgrade("upgrade_charge", Orecraft.TAB_ENERGY, 8);
	public static final Item upgrade_generator = new ItemUpgrade("upgrade_generator", Orecraft.TAB_ENERGY, 8);
	public static final Item upgrade_solar = new ItemUpgrade("upgrade_solar", Orecraft.TAB_ENERGY, 8);
	
	public static final Item upgrade_fluid_speed = new ItemUpgradeFluid("upgrade_fluid_speed", Orecraft.TAB_ENERGY, 8);
	public static final Item upgrade_fluid_capacity = new ItemUpgradeFluid("upgrade_fluid_capacity", Orecraft.TAB_ENERGY, 8);
	public static final Item upgrade_fluid_efficiency = new ItemUpgradeFluid("upgrade_fluid_efficiency", Orecraft.TAB_ENERGY, 8);
	
	public static final Item upgrade_transformer = new ItemUpgrade("upgrade_transformer", Orecraft.TAB_ENERGY, 8);
	
	public static final Item plate_copper = new ItemBase("plate_copper", Orecraft.TAB_MATERIALS);
	public static final Item plate_tin = new ItemBase("plate_tin", Orecraft.TAB_MATERIALS);
	public static final Item plate_silver = new ItemBase("plate_silver", Orecraft.TAB_MATERIALS);
	public static final Item plate_eutine = new ItemBase("plate_eutine", Orecraft.TAB_MATERIALS);
	public static final Item plate_mithril = new ItemBase("plate_mithril", Orecraft.TAB_MATERIALS);
	public static final Item plate_adamantite = new ItemBase("plate_adamantite", Orecraft.TAB_MATERIALS);
	public static final Item plate_rune = new ItemBase("plate_rune", Orecraft.TAB_MATERIALS);
	
	public static final Item plate_bronze = new ItemBase("plate_bronze", Orecraft.TAB_MATERIALS);
	public static final Item plate_brass = new ItemBase("plate_brass", Orecraft.TAB_MATERIALS);
	public static final Item plate_steel = new ItemBase("plate_steel", Orecraft.TAB_MATERIALS);
	public static final Item plate_carbon = new ItemBase("plate_carbon", Orecraft.TAB_MATERIALS);
	
	public static final Item plate_iron = new ItemBase("plate_iron", Orecraft.TAB_MATERIALS);
	public static final Item plate_gold = new ItemBase("plate_gold", Orecraft.TAB_MATERIALS);
	public static final Item plate_diamond = new ItemBase("plate_diamond", Orecraft.TAB_MATERIALS);
	
	public static final Item plate_glass = new ItemBase("plate_glass", Orecraft.TAB_MATERIALS);
	public static final Item plate_blackglass = new ItemBase("plate_blackglass", Orecraft.TAB_MATERIALS);
	public static final Item plate_reinforced = new ItemBase("plate_reinforced", Orecraft.TAB_MATERIALS);
	public static final Item plate_witherreinforced = new ItemBase("plate_witherreinforced", Orecraft.TAB_MATERIALS);
	public static final Item plate_ender = new ItemBase("plate_ender", Orecraft.TAB_MATERIALS);
	
	public static final Item strip_carbon = new ItemBase("strip_carbon", Orecraft.TAB_MATERIALS);
	public static final Item strand_carbon = new ItemBase("strand_carbon", Orecraft.TAB_MATERIALS);
	
	public static final Item generic_rubber = new ItemBase("generic_rubber", Orecraft.TAB_MATERIALS);
	public static final Item generic_rubber_insulation = new ItemBase("generic_rubber_insulation", Orecraft.TAB_MATERIALS);
	public static final Item generic_rod_steel = new ItemBase("generic_rod_steel", Orecraft.TAB_MATERIALS);
	public static final Item generic_rod_mithril = new ItemBase("generic_rod_mithril", Orecraft.TAB_MATERIALS);
	public static final Item generic_rod_rune = new ItemBase("generic_rod_rune", Orecraft.TAB_MATERIALS);
	public static final Item generic_rod_tool = new ItemBase("generic_rod_tool", Orecraft.TAB_TOOLS);
	
	public static final Item tool_machinewrench = new MachineWrench("tool_machinewrench", Orecraft.TAB_TOOLS);
	public static final Item tool_machinehammer = new MachineWrench("tool_machinehammer", Orecraft.TAB_TOOLS);
	
	public static final Item tool_debug = new ToolDebug("tool_debug", null, 1);
	
	public static final ItemPickaxe pickaxe_copper = new ModPickaxe(OrecraftReference.TOOL.TOOL_COPPER, Orecraft.TAB_TOOLS, "pickaxe_copper", false);
	public static final ItemSword sword_copper = new ModSword(OrecraftReference.TOOL.TOOL_COPPER, Orecraft.TAB_TOOLS, "sword_copper", false);
	public static final ItemAxe axe_copper = new ModAxe(OrecraftReference.TOOL.TOOL_COPPER, Orecraft.TAB_TOOLS, "axe_copper", false);
	public static final ItemSpade spade_copper = new ModSpade(OrecraftReference.TOOL.TOOL_COPPER, Orecraft.TAB_TOOLS, "spade_copper", false);
	
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ingot_copper,
				ingot_tin, ingot_silver, ingot_eutine, ingot_mithril, ingot_adamantite, ingot_rune, ingot_fractum,
				ingot_fractum_unrefined, ingot_fractum_raw, ingot_silicon, ingot_steel, ingot_steel_unrefined,
				ingot_bronze, ingot_brass, ingot_lapis,
				gem_onyx, gem_satsum, gem_azurite, gem_ruby, gem_amythest, 
				gem_yellowtopaz, gem_fireopal, gem_turquoise, gem_blackdiamond,
				gem_godone, gem_godtwo, gem_godthree,
				dust_copper, dust_tin, dust_silver, dust_eutine, dust_mithril, dust_adamantite, dust_rune,
				dust_bronze, dust_steel, dust_brass, dust_carbon,
				dust_iron, dust_gold, dust_diamond,
				dust_iron_clean, dust_gold_clean,
				dust_basic, dust_ender,
				battery_small, battery_large,
				battery_powered, battery_energized,
				component_circuit_one_raw, component_circuit_one, component_circuit_three_raw, component_circuit_three,
				component_silicon, component_silicon_refined, component_silicon_wafer, component_silicon_wafer_basic,
				component_silicon_wafer_ender,
				upgrade_base, upgrade_speed, upgrade_capacity, upgrade_efficiency,
				upgrade_charge, upgrade_generator, upgrade_solar,
				upgrade_fluid_speed, upgrade_fluid_capacity, upgrade_fluid_efficiency,
				upgrade_transformer,
				plate_copper, plate_tin, plate_silver, plate_eutine, plate_mithril, plate_adamantite, plate_rune,
				plate_bronze, plate_brass, plate_steel, plate_carbon,
				plate_iron, plate_gold, plate_diamond,
				plate_glass, plate_blackglass, plate_reinforced, plate_witherreinforced, plate_ender,
				strip_carbon, strand_carbon,
				generic_rubber, generic_rubber_insulation, generic_rod_steel, generic_rod_mithril, generic_rod_rune, generic_rod_tool,
				tool_machinewrench, tool_machinehammer, tool_debug,
				pickaxe_copper, sword_copper, axe_copper, spade_copper);
	}
	
	@SubscribeEvent
	public static void registerModelLocations(final ModelRegistryEvent event) {
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
		
		registerItemModelLocation(battery_small);
		registerItemModelLocation(battery_large);
		
		registerItemModelLocation(battery_powered);
		registerItemModelLocation(battery_energized);
		
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
		
		registerItemModelLocation(upgrade_transformer);
		
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
		
		registerItemModelLocation(pickaxe_copper);
		registerItemModelLocation(sword_copper);
		registerItemModelLocation(axe_copper);
		registerItemModelLocation(spade_copper);
	}
	
	public static void registerItemModelLocation(Item item){
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
}