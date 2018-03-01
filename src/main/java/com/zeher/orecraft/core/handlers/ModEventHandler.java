package com.zeher.orecraft.core.handlers;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.zeher.orecraft.OreCraft;
import com.zeher.orecraft.client.model.baked.BakedFluidPipeClearFluidModel;
import com.zeher.orecraft.client.model.baked.BakedFluidPipeClearModel;
import com.zeher.orecraft.client.model.baked.BakedFluidTankFluidModel;
import com.zeher.orecraft.client.model.baked.BakedFluidTankModel;
import com.zeher.orecraft.client.model.baked.BakedUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class ModEventHandler {

	@SubscribeEvent
	public void onModelBakeEvent(ModelBakeEvent event) {
		if (OreCraft.orecraft_system_message) {
			System.out.println("[OreCraft/Client]: Registering fluids for render...");
			for (Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
				Fluid fluid_ = entry.getValue();
				String fluid_name = fluid_.getName();
				System.out.println("[OreCraft/Client]: Fluid: " + fluid_name + " has been registered.");
			}
		}

		BakedUtils.bakeTankModel(event, "");
		BakedUtils.bakeFluidPipeModel(event);
		
		BakedUtils.bakeHeatedFluidGenModel(event, "");
		BakedUtils.bakeHeatedFluidGenModel(event, "_on");
		BakedUtils.bakePeltierGenModel(event, "");
		BakedUtils.bakePeltierGenModel(event, "_on");
		
		BakedUtils.bakeOreProcessorModel(event, "powered", "");
		BakedUtils.bakeOreProcessorModel(event, "powered", "_on");
		
		BakedUtils.bakeFluidCrafterModel(event, "powered", "");
		BakedUtils.bakeFluidCrafterModel(event, "powered", "_on");
	}

}
