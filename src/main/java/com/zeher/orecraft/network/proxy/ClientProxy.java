package com.zeher.orecraft.network.proxy;

import com.zeher.orecraft.OreCraft;
import com.zeher.orecraft.core.handlers.BlockHandler;
import com.zeher.orecraft.core.handlers.ItemHandler;
import com.zeher.orecraft.core.handlers.ModEventHandler;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void init() {
		ItemHandler.registerModelLocations();
		BlockHandler.registerModelLocations();
		
		OreCraft.orecraft_event_hub = new ModEventHandler();
		MinecraftForge.EVENT_BUS.register(OreCraft.orecraft_event_hub);

	}
	
}
