package com.zeher.orecraft.network.proxy;

import com.zeher.orecraft.core.handlers.BlockHandler;
import com.zeher.orecraft.core.handlers.FluidHandler;
import com.zeher.orecraft.core.handlers.GuiHandler;
import com.zeher.orecraft.core.handlers.ItemHandler;
import com.zeher.orecraft.core.handlers.NetworkHandler;
import com.zeher.orecraft.core.handlers.RecipeHandler;
import com.zeher.orecraft.core.handlers.TileEntityHandler;
import com.zeher.orecraft.core.handlers.WorldGenerationHandler;
import com.zeher.trzlib.api.TRZIProxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy implements TRZIProxy {

	public void preInit() {
		ItemHandler.preInit();
		ItemHandler.register();

		FluidHandler.preInit();
		FluidHandler.register();

		BlockHandler.preInit();
		BlockHandler.register();

		RecipeHandler.registerRecipes();
		RecipeHandler.registerSmelting();
		RecipeHandler.registerGrinding();
		RecipeHandler.registerCompressing();
		RecipeHandler.registerExtracting();
		RecipeHandler.registerCleaning();
		RecipeHandler.registerRefining();
		RecipeHandler.registerFluidCraft();
		RecipeHandler.registerFluidMelt();

		TileEntityHandler.preInit();

		GameRegistry.registerWorldGenerator(new WorldGenerationHandler(), 0);

		NetworkHandler.preInit();
	}

	public void init() {
		new GuiHandler();
	}

	public void postInit() {
		// MinecraftForge.EVENT_BUS.register(new OreCraftHighlightEvent());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FMLInitializationEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverStarting(FMLServerStartingEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverStarted(FMLServerStartedEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverStopping(FMLServerStoppingEvent event) {
		// TODO Auto-generated method stub
		
	}
}
