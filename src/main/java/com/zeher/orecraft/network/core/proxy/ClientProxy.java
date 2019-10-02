package com.zeher.orecraft.network.core.proxy;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.core.handler.*;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {
	
	@Override
	@SideOnly(Side.CLIENT)
	public void preInit() {
		TileEntityHandler.clientPreInit();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void init() {
		//OreCraft.orecraft_event_hub = new OreCraftEventHandler();
		//MinecraftForge.EVENT_BUS.register(OreCraft.orecraft_event_hub);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void postInit() { }
	
}
