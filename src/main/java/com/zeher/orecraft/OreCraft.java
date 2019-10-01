package com.zeher.orecraft;

import java.util.ArrayList;
import java.util.List;

import com.zeher.orecraft.core.creativetab.TabOrecraft;
import com.zeher.orecraft.core.creativetab.TabOrecraftDecor;
import com.zeher.orecraft.core.creativetab.TabOrecraftEnergy;
import com.zeher.orecraft.core.creativetab.TabOrecraftMaterial;
import com.zeher.orecraft.core.creativetab.TabOrecraftTool;
import com.zeher.orecraft.core.handler.BlockHandler;
import com.zeher.orecraft.core.handler.FluidHandler;
import com.zeher.orecraft.core.handler.GuiHandler;
import com.zeher.orecraft.core.handler.NetworkHandler;
import com.zeher.orecraft.core.handler.RecipeHandler;
import com.zeher.orecraft.core.handler.TileEntityHandler;
import com.zeher.orecraft.core.handler.WorldGenHandler;
import com.zeher.orecraft.network.core.proxy.CommonProxy;
import com.zeher.zeherlib.ZeherLib;
import com.zeher.zeherlib.api.interfaces.IProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod.EventBusSubscriber
@Mod(modid = Orecraft.MOD_ID, name = Orecraft.MOD_NAME, version = Orecraft.MOD_VERSION, dependencies = Orecraft.MOD_DEPENDENCIES)
public class Orecraft {
	
	@SidedProxy(clientSide = "com.zeher.orecraft.network.core.proxy.ClientProxy", serverSide = "com.zeher.orecraft.network.core.proxy.ServerProxy")
	public static CommonProxy COMMON_PROXY;
	public static IProxy IPROXY;
	
	/**@Mod*/
	public static final String MOD_ID = "orecraft";
	public static final String MOD_NAME = "OreCraft";
	public static final String MOD_VERSION = "6.0.18-beta";
	public static final String MOD_VERSION_MAX = "6.1.0-beta";
	public static final String MOD_DEPENDENCIES = ZeherLib.VERSION_GROUP;
	
	public static final String VERSION_GROUP = "required-after:" + MOD_ID + "@[" + MOD_VERSION + "," + MOD_VERSION_MAX + "];";
	
	/**@CreativeTabs*/
	public static final CreativeTabs TAB_BLOCKS = new TabOrecraft(CreativeTabs.getNextID(), "tab_blocks");
	public static final CreativeTabs TAB_DECOR = new TabOrecraftDecor(CreativeTabs.getNextID(), "tab_decor");
	public static final CreativeTabs TAB_TOOLS = new TabOrecraftTool(CreativeTabs.getNextID(), "tab_tools");
	public static final CreativeTabs TAB_MATERIALS = new TabOrecraftMaterial(CreativeTabs.getNextID(), "tab_materials");
	public static final CreativeTabs TAB_ENERGY = new TabOrecraftEnergy(CreativeTabs.getNextID(), "tab_energy");
	
	@Instance(MOD_ID)
	public static Orecraft INSTANCE;
	
	public static SimpleNetworkWrapper NETWORK;
	public static GuiHandler GUI_HANDLER;
	
	public static final boolean ORECRAFT_SYSTEM_MESSAGE = false;
	
	public static final List<SoundEvent> SOUNDS = new ArrayList<>();
	
	public Orecraft(){
		FluidRegistry.enableUniversalBucket();
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		FluidHandler.preInit();
		BlockHandler.preInit();
		TileEntityHandler.preInit();
		WorldGenHandler.preInit();
		NetworkHandler.preInit();
		
		if(ORECRAFT_SYSTEM_MESSAGE){
			System.out.println("[OreCraft/CORE]: Pre-Initialization...");
		}
		
		COMMON_PROXY.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		COMMON_PROXY.init();
		RecipeHandler.init();
		
		GUI_HANDLER = new GuiHandler();
		
		if(ORECRAFT_SYSTEM_MESSAGE){
			System.out.println("[OreCraft/CORE]: Initialization...");
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		COMMON_PROXY.postInit();
		
		if(ORECRAFT_SYSTEM_MESSAGE){
			System.out.println("[OreCraft/CORE]: Post-Initialization...");
		}
	}
}
