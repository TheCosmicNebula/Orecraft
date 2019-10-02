package com.zeher.orecraft.core.reference;

import com.zeher.orecraft.Orecraft;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class OrecraftReference {

	public static class RESOURCE {
		public static final String PRE = Orecraft.MOD_ID + ":";

		public static final String RESOURCE = PRE + "textures/";
		public static final String GUI = RESOURCE + "gui/";

		public static final String BLOCKS = PRE + "blocks/";
		public static final String ITEMS = RESOURCE + "items/";

		public static final String TESR = RESOURCE + "tesr/";
	}
	
	public static class TESR {
		public static class RESOURCE {
			public static final ResourceLocation TESR_FURNACE_INTERNALS = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "machine/furnace/internals.png");
			public static final ResourceLocation TESR_GRINDER_INTERNALS = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "machine/grinder/internals.png");
			public static final ResourceLocation TESR_COMPRESSOR_INTERNALS = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "machine/compressor/internals.png");
			public static final ResourceLocation TESR_EXTRACTOR_INTERNALS = new ResourceLocation(OrecraftReference.RESOURCE.TESR + "machine/extractor/internals.png");
			
		}
	}
	
	public static class GUI {
		public static class RESOURCE {
			public static final ResourceLocation GUI_CAPACITOR = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "storage/capacitor/gui_capacitor.png");
			
			public static final ResourceLocation GUI_FURNACE = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/furnace/gui_furnace.png");
			public static final ResourceLocation GUI_GRINDER = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/grinder/gui_grinder.png");
			public static final ResourceLocation GUI_COMPRESSOR = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/compressor/gui_compressor.png");
			public static final ResourceLocation GUI_EXTRACTOR = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/extractor/gui_extractor.png");
			
			public static final ResourceLocation GUI_CHARGER = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/charger/gui_charger.png");
			
			public static final ResourceLocation GUI_FURNACE_TWO = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/furnace/gui_furnace_two.png");
			public static final ResourceLocation GUI_GRINDER_TWO = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/grinder/gui_grinder_two.png");
			public static final ResourceLocation GUI_COMPRESSOR_TWO = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/compressor/gui_compressor_two.png");
			public static final ResourceLocation GUI_EXTRACTOR_TWO = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "machine/extractor/gui_extractor_two.png");
			
			public static final ResourceLocation GUI_PRODUCER_SCALED_ELEMENTS = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "production/scaled_elements.png");
		
			public static final ResourceLocation GUI_PRODUCER_SOLIDFUEL = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "production/generator/solidfuel/gui_solidfuel.png");
			public static final ResourceLocation GUI_PRODUCER_HEATEDFLUID = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "production/generator/heatedfluid/gui_heatedfluid.png");
			public static final ResourceLocation GUI_PRODUCER_PELTIER = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "production/generator/peltier/gui_peltier.png");
			public static final ResourceLocation GUI_PRODUCER_SOLARPANEL = new ResourceLocation(OrecraftReference.RESOURCE.GUI + "production/solarpanel/gui_solarpanel.png");
			
		}
	}

	public static class JEI {
		public static final String GRINDER_UID = Orecraft.MOD_ID + ":grinder";
		public static final String GRINDER_UNLOC = "jei.recipe.grinder";

		public static final String COMPRESSOR_UID = Orecraft.MOD_ID + ":compressor";
		public static final String COMPRESSOR_UNLOC = "jei.recipe.compressor";

		public static final String FURNACE_UID = Orecraft.MOD_ID + ":furnace";
		public static final String FURNACE_UNLOC = "jei.recipe.furnace";

		public static final String EXTRACTOR_UID = Orecraft.MOD_ID + ":extractor";
		public static final String EXTRACTOR_UNLOC = "jei.recipe.extractor";

		public static final String EXPERIENCE = "jei.experience";
	}

	public static class GUI_NAME {
		public static class MACHINE {
			public static class POWERED {
				public static final String FURNACE = "gui.machine.powered.furnace.name";
				public static final String GRINDER = "gui.machine.powered.grinder.name";
				public static final String COMPRESSOR = "gui.machine.powered.compressor.name";
				public static final String EXTRACTOR = "gui.machine.powered.extractor.name";

				public static final String CHARGER = "gui.machine.powered.charger.name";
				
				public static final String OREPROCESSOR = "gui.fluidmachine.powered.oreprocessor.name";
				public static final String FLUIDCRAFTER = "gui.fluidmachine.powered.fluidcrafter.name";
			}
			
			public static class ENERGIZED {
				public static final String FURNACE = "gui.machine.energized.furnace.name";
				public static final String GRINDER = "gui.machine.energized.grinder.name";
				public static final String COMPRESSOR = "gui.machine.energized.compressor.name";
				public static final String EXTRACTOR = "gui.machine.energized.extractor.name";
			}
		}
			
		public static class PRODUCER {
			public static class POWERED {
				public static final String SOLIDFUEL = "gui.production.powered.solidfuel.name";
				public static final String SOLARPANEL = "gui.production.powered.solarpanel.name";
				public static final String HEATEDFLUID = "gui.fluidproduction.powered.heatedfluid.name";
				public static final String PELTIER = "gui.fluidproduction.powered.peltier.name";
			}
			
			public static class ENERGIZED {
				
			}
		}
			
		public static class STORAGE {
			public static class POWERED {
				public static final String CAPACITOR = "gui.storage.powered.capacitor.name";
			}
			
			public static class ENERGIZED {
				public static final String CAPACITOR = "gui.storage.energized.capacitor.name";
			}
			
			public static final String MECHANISEDSTORAGESMALL = "gui.storage.mechanisedstoragesmall.name";
			public static final String MECHANISEDSTORAGELARGE = "gui.storage.mechanisedstoragelarge.name";
			
		}
	}

	public static class TOOL {
		public static final ToolMaterial TOOL_COPPER = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_copper", 1, 1250, 7.5F, 2.0F, 12);
		public static final ToolMaterial TOOL_SILVER = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_silver", 2, 1500, 10F, 2.0F, 15);
		public static final ToolMaterial TOOL_EUTINE = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_eutine", 2, 1750, 12.5F, 2.5F, 17);
		public static final ToolMaterial TOOL_MITHRIL = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_mithril", 2, 2500, 15.5F, 5.0F, 20);
		public static final ToolMaterial TOOL_ADAMANTITE = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_adamantite", 3, 3000, 17.5F, 7.5F, 23);
		public static final ToolMaterial TOOL_RUNE = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_rune", 3, 6500, 25.0F, 20.0F, 30);
		public static final ToolMaterial TOOL_FRACTUM = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_fractum", 4, 8000, 40F, 25.0F, 45);

		public static final ToolMaterial TOOL_STEEL = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_steel", 3, 3500, 16F, 3.5F, 10);
		public static final ToolMaterial TOOL_BRONZE = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_bronze", 3, 3000, 12.5F, 2.5F, 8);

		public static final ToolMaterial TOOL_GODONE = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_godone", 3, 250000, 64F, 64F, 64);
		public static final ToolMaterial TOOL_GODTWO = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_godtwo", 4, 500000, 76F, 76F, 76);
		public static final ToolMaterial TOOL_GODTHREE = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_three", 5, 1000000, 128F, 128F, 128);
		
		public static final ToolMaterial TOOL_ONYX = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_onyx", 2, 1000, 10F, 1.0F, 10);
		public static final ToolMaterial TOOL_SATSUM = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_satsum", 0, 500, 6F, 1F, 7);
		public static final ToolMaterial TOOL_AZURITE = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_azurite", 2, 1750, 15F, 2.0F, 15);
		public static final ToolMaterial TOOL_RUBY = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_ruby", 3, 2500, 17.5F, 17.5F, 17);
		public static final ToolMaterial TOOL_AMYTHEST = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_amythest", 3, 3500, 20.0F, 4.0F, 20);
		public static final ToolMaterial TOOL_YELLOWTOPAZ = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_yellowtopaz", 2, 2500, 10.5F, 5.0F, 15);
		public static final ToolMaterial TOOL_FIREOPAL = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_fireopal", 2, 3000, 15F, 7.5F, 20);
		public static final ToolMaterial TOOL_TURQUIOSE = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_turquiose", 3, 4000, 20F, 10F, 30);
		public static final ToolMaterial TOOL_BLACKDIAMOND = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_blackdiamond", 4, 7500, 30F, 20F, 45);

		public static final ToolMaterial TOOL_POWERED = EnumHelper.addToolMaterial(Orecraft.MOD_ID + ":" + "tool_powered", 3, 10, 40f, 25.0f, 0);
	}

	public static class ARMOUR {
		public static final ArmorMaterial ARMOUR_COPPER = EnumHelper.addArmorMaterial("armour_copper", "", 20, new int[] { 2, 4, 2, 1 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
		public static final ArmorMaterial ARMOUR_SILVER = EnumHelper.addArmorMaterial("armour_silver", "", 24, new int[] { 2, 4, 3, 1 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
		public static final ArmorMaterial ARMOUR_EUTINE = EnumHelper.addArmorMaterial("armour_eutine", "", 26, new int[] { 2, 4, 4, 1 }, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
		public static final ArmorMaterial ARMOUR_MITHRIL = EnumHelper.addArmorMaterial("armour_mithril", "", 34, new int[] { 3, 6, 4, 2 }, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
		public static final ArmorMaterial ARMOUR_ADAMANTITE = EnumHelper.addArmorMaterial("armour_adamantite", "", 38, new int[] { 3, 7, 5, 3 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
		public static final ArmorMaterial ARMOUR_RUNE = EnumHelper.addArmorMaterial("armour_rune", "", 42, new int[] { 3, 8, 6, 3 }, 30, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
		public static final ArmorMaterial ARMOUR_FRACTUM = EnumHelper.addArmorMaterial("armour_fractum", "", 60, new int[] { 3, 8, 6, 3 }, 45, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);

		public static final ArmorMaterial ARMOUR_STEEL = EnumHelper.addArmorMaterial("armour_steel", "", 40, new int[] { 3, 7, 4, 3 }, 22, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
		public static final ArmorMaterial ARMOUR_BRONZE = EnumHelper.addArmorMaterial("armour_bronze", "", 34, new int[] { 3, 6, 4, 3 }, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);

		public static final ArmorMaterial ARMOUR_GODONE = EnumHelper.addArmorMaterial("armour_godone", "", 128, new int[] { 3, 8, 6, 3 }, 128, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
		public static final ArmorMaterial ARMOUR_GODTWO = EnumHelper.addArmorMaterial("armour_godtwo", "", 176, new int[] { 3, 8, 6, 3 }, 176, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
		public static final ArmorMaterial ARMOUR_GODTHREE = EnumHelper.addArmorMaterial("armour_godthree", "", 256, new int[] { 10, 8, 6, 3 }, 256, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);

		public static final ArmorMaterial ARMOUR_ONYX = EnumHelper.addArmorMaterial("armour_onyx", "", 18, new int[] { 2, 3, 2, 1 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
		public static final ArmorMaterial ARMOUR_SATSUM = EnumHelper.addArmorMaterial("armour_satsum", "", 14, new int[] { 1, 2, 2, 1 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
		public static final ArmorMaterial ARMOUR_AZURITE = EnumHelper.addArmorMaterial("armour_azurite", "", 24, new int[] { 2, 3, 2, 2 }, 14, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
		public static final ArmorMaterial ARMOUR_RUBY = EnumHelper.addArmorMaterial("armour_ruby", "", 32, new int[] { 3, 5, 4, 3 }, 22, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
		public static final ArmorMaterial ARMOUR_AMYTHEST = EnumHelper.addArmorMaterial("armour_amythest", "", 38, new int[] { 3, 7, 5, 3 }, 26, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
		public static final ArmorMaterial ARMOUR_YELLOWTOPAZ = EnumHelper.addArmorMaterial("armour_yellowtopaz", "", 32, new int[] { 3, 5, 4, 3 }, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
		public static final ArmorMaterial ARMOUR_FIREOPAL = EnumHelper.addArmorMaterial("armour_fireopal", "", 32, new int[] { 3, 7, 4, 3 }, 23, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
		public static final ArmorMaterial ARMOUR_TURQUIOSE = EnumHelper.addArmorMaterial("armour_turquiose", "", 34, new int[] { 3, 7, 5, 3 }, 26, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
		public static final ArmorMaterial ARMOUR_BLACKDIAMOND = EnumHelper.addArmorMaterial("armour_blackdiamond", "", 36, new int[] { 3, 8, 6, 3 }, 28, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);

		public static final ArmorMaterial ARMOUR_CARBONFIBRE = EnumHelper.addArmorMaterial("armour_carbonfibre", "", 64, new int[] { 3, 8, 6, 3 }, 64, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0);
	}
}
