package com.zeher.orecraft.client;

import com.zeher.orecraft.OreCraft;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class OreCraftClient extends OreCraft implements IResourceManagerReloadListener {

	public static OreCraftClient instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if(orecraft_system_message){
			System.out.println("[OreCraft/Client]: Pre-Initialization...");
		}
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		if(orecraft_system_message){
			System.out.println("[OreCraft/Client]: Initialization...");
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if(orecraft_system_message){
			System.out.println("[OreCraft/Client]: Post-Initialization...");
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager p_110549_1_) {}

}
