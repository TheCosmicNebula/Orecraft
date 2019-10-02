package com.zeher.orecraft.core.handler;

import com.zeher.orecraft.Orecraft;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Orecraft.MOD_ID)
public class SoundHandler {
	
	@EventBusSubscriber(modid = Orecraft.MOD_ID)
	public static class MACHINE {

		public static final SoundEvent GRINDER = new SoundEvent(new ResourceLocation(Orecraft.MOD_ID + ":" + "grinder")).setRegistryName("grinder");
		public static final SoundEvent CRUSHER = new SoundEvent(new ResourceLocation(Orecraft.MOD_ID + ":" + "crusher")).setRegistryName("crusher");
		
		public static final SoundEvent LASERHUM = new SoundEvent(new ResourceLocation(Orecraft.MOD_ID + ":" + "laserhum")).setRegistryName("laserhum");

		public static final SoundEvent COMPRESSOR = new SoundEvent(new ResourceLocation(Orecraft.MOD_ID + ":" + "compressor")).setRegistryName("compressor");
		public static final SoundEvent EXTRACTOR = new SoundEvent(new ResourceLocation(Orecraft.MOD_ID + ":" + "extractor")).setRegistryName("extractor");
		
		@SubscribeEvent
		public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
			event.getRegistry().registerAll(GRINDER, CRUSHER, LASERHUM, COMPRESSOR, EXTRACTOR);
			
		}
	}
}
