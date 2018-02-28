package com.zeher.orecraft;

import com.zeher.orecraft.core.creativetab.*;
import com.zeher.orecraft.core.handlers.*;
import com.zeher.orecraft.network.proxy.ClientProxy;
import com.zeher.orecraft.network.proxy.CommonProxy;
import com.zeher.trzlib.TRZLib;
import com.zeher.trzlib.api.TRZIProxy;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = OreCraft.mod_id, name = OreCraft.mod_name, version = OreCraft.mod_version, dependencies = OreCraft.mod_dependencies)
public class OreCraft{
	
	@SidedProxy(clientSide = "com.zeher.orecraft.network.proxy.ClientProxy", serverSide = "com.zeher.orecraft.network.proxy.ServerProxy")
	public static CommonProxy common_proxy;
	public static TRZIProxy iproxy;
	
	/**Main Mod Class Info*/
	public static final String mod_id = "orecraft";
	public static final String mod_name = "OreCraft";
	public static final String mod_version = "3.0.8b";
	public static final String mod_version_max = "3.1.0b";
	public static final String mod_dependencies = TRZLib.version_group;
	
	public static final String version_group = "required-after:" + mod_id + "@[" + mod_version + "," + mod_version_max + "];";

	/**Creative Tabs*/
	public static CreativeTabs tab_orecraft = new CreativeTabOreCraft(CreativeTabs.getNextID(), "tab_orecraft");
	public static CreativeTabs tab_orecraft_tools = new CreativeTabOreCraftTools(CreativeTabs.getNextID(), "tab_orecraft_tools");
	public static CreativeTabs tab_orecraft_materials = new CreativeTabOreCraftMaterials(CreativeTabs.getNextID(), "tab_orecraft_materials");
	public static CreativeTabs tab_orecraft_energy = new CreativeTabOreCraftEnergy(CreativeTabs.getNextID(), "tab_orecraft_energy");
	
	@Instance(mod_id)
	public static OreCraft instance;
	public static SimpleNetworkWrapper network;
	public static ModEventHandler orecraft_event_hub;
	public static GuiHandler gui_handler;
	
	public static boolean orecraft_system_message = false;
	
	public OreCraft(){
		FluidRegistry.enableUniversalBucket();
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		common_proxy.preInit();
		
		if(orecraft_system_message){
			System.out.println("[OreCraft/Core]: Pre-Initialization...");
		}
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		common_proxy.init();
		
		gui_handler = new GuiHandler();
		
		if(orecraft_system_message){
			System.out.println("[OreCraft/Core]: Initialization...");
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		common_proxy.postInit();
		
		if(orecraft_system_message){
			System.out.println("[OreCraft/Core]: Post-Initialization...");
		}
	}
}
