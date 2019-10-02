package com.zeher.orecraft.core.handler;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.machine.client.baked.model.BakedFluidCrafterModel;
import com.zeher.orecraft.machine.client.baked.model.BakedHeatedFluidGeneratorModel;
import com.zeher.orecraft.machine.client.baked.model.BakedOreProcessorModel;
import com.zeher.orecraft.machine.client.baked.model.BakedPeltierGeneratorFluidModel;
import com.zeher.orecraft.machine.client.baked.model.BakedPeltierGeneratorModel;
import com.zeher.orecraft.storage.client.baked.model.BakedFluidTankFluidModel;
import com.zeher.orecraft.storage.client.baked.model.BakedFluidTankModel;
import com.zeher.orecraft.transfer.client.baked.model.BakedFluidPipeClearFluidModel;
import com.zeher.orecraft.transfer.client.baked.model.BakedFluidPipeClearModel;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Orecraft.MOD_ID)
public class BakedHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelBakeEvent(ModelBakeEvent event) {
		if (Orecraft.ORECRAFT_SYSTEM_MESSAGE) {
			System.out.println("[OreCraft/Client]: Registering fluids for render...");
			for (Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
				Fluid fluid_ = entry.getValue();
				String fluid_name = fluid_.getName();
				System.out.println("[OreCraft/Client]: Fluid: " + fluid_name + " has been registered.");
			}
		}
		
		bakeFluidTankModel(event);
		bakeFluidPipeModel(event);
		
		bakeHeatedFluidGenModel(event, "powered", "");
		bakeHeatedFluidGenModel(event, "powered", "_on");
		
		bakePeltierGenModel(event, "powered", "");
		bakePeltierGenModel(event, "powered", "_on");
		
		bakeOreProcessorModel(event, "powered");
		bakeFluidCrafterModel(event, "powered");
	}
	
	public static void bakeFluidTankModel(ModelBakeEvent event) {
		Fluid fluid;
		IBakedModel[] bakedFluidModels;

		for (Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
			fluid = entry.getValue();

			bakedFluidModels = new IBakedModel[BakedFluidTankModel.FLUID_LEVELS];

			for (int x = 0; x < BakedFluidTankModel.FLUID_LEVELS; x++) {
				bakedFluidModels[x] = new BakedFluidTankFluidModel(fluid, x + 1);
			}

			BakedFluidTankModel.FLUID_MODELS.put(entry.getKey(), bakedFluidModels);
		}

		RegistrySimple<ModelResourceLocation, IBakedModel> registry = (RegistrySimple) event.getModelRegistry();
		ArrayList<ModelResourceLocation> modelLocations = Lists.newArrayList();

		String modelPath = "block_fluidtank";

		for (ModelResourceLocation modelLoc : registry.getKeys()) {
			if (modelLoc.getResourceDomain().equals(Orecraft.MOD_ID) && modelLoc.getResourcePath().equals(modelPath) && !modelLoc.getVariant().equals("inventory")) {
				modelLocations.add(modelLoc);
			}
		}

		IBakedModel registeredModel;
		IBakedModel replacementModel;

		for (ModelResourceLocation loc : modelLocations) {
			registeredModel = event.getModelRegistry().getObject(loc);
			replacementModel = new BakedFluidTankModel(registeredModel);
			event.getModelRegistry().putObject(loc, replacementModel);
		}
	}

	public static void bakeHeatedFluidGenModel(ModelBakeEvent event, String type, String tank_name) {
		Fluid fluid;
		IBakedModel[] bakedFluidModels;

		for (Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
			fluid = entry.getValue();

			bakedFluidModels = new IBakedModel[BakedHeatedFluidGeneratorModel.FLUID_LEVELS];

			for (int x = 0; x < BakedHeatedFluidGeneratorModel.FLUID_LEVELS; x++) {
				bakedFluidModels[x] = new BakedFluidTankFluidModel(fluid, x + 1);
			}

			BakedHeatedFluidGeneratorModel.FLUID_MODELS.put(entry.getKey(), bakedFluidModels);
		}

		RegistrySimple<ModelResourceLocation, IBakedModel> registry = (RegistrySimple) event.getModelRegistry();
		ArrayList<ModelResourceLocation> modelLocations = Lists.newArrayList();

		String modelPath = "block_" + type + "_heatedfluid_generator" + tank_name;

		for (ModelResourceLocation modelLoc : registry.getKeys()) {
			if (modelLoc.getResourceDomain().equals(Orecraft.MOD_ID) && modelLoc.getResourcePath().equals(modelPath) && !modelLoc.getVariant().equals("inventory")) {
				modelLocations.add(modelLoc);
			}
		}

		IBakedModel registeredModel;
		IBakedModel replacementModel;

		for (ModelResourceLocation loc : modelLocations) {
			registeredModel = event.getModelRegistry().getObject(loc);
			replacementModel = new BakedHeatedFluidGeneratorModel(registeredModel);
			event.getModelRegistry().putObject(loc, replacementModel);
		}
	}

	public static void bakePeltierGenModel(ModelBakeEvent event, String type, String tank_name) {
		Fluid fluid;
		IBakedModel[] bakedFluidModels;

		for (Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
			fluid = entry.getValue();

			bakedFluidModels = new IBakedModel[BakedPeltierGeneratorModel.FLUID_LEVELS];

			for (int x = 0; x < BakedPeltierGeneratorModel.FLUID_LEVELS; x++) {
				bakedFluidModels[x] = new BakedPeltierGeneratorFluidModel(fluid, x + 1);
			}

			BakedPeltierGeneratorModel.FLUID_MODELS.put(entry.getKey(), bakedFluidModels);
		}

		RegistrySimple<ModelResourceLocation, IBakedModel> registry = (RegistrySimple) event.getModelRegistry();
		ArrayList<ModelResourceLocation> modelLocations = Lists.newArrayList();

		String modelPath = "block_" + type + "_peltier_generator" + tank_name;

		for (ModelResourceLocation modelLoc : registry.getKeys()) {
			if (modelLoc.getResourceDomain().equals(Orecraft.MOD_ID) && modelLoc.getResourcePath().equals(modelPath) && !modelLoc.getVariant().equals("inventory")) {
				modelLocations.add(modelLoc);
			}
		}

		IBakedModel registeredModel;
		IBakedModel replacementModel;

		for (ModelResourceLocation loc : modelLocations) {
			registeredModel = event.getModelRegistry().getObject(loc);
			replacementModel = new BakedPeltierGeneratorModel(registeredModel);
			event.getModelRegistry().putObject(loc, replacementModel);
		}
	}

	public static void bakeOreProcessorModel(ModelBakeEvent event, String type) {
		Fluid fluid;
		IBakedModel[] bakedFluidModels;

		for (Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
			fluid = entry.getValue();

			bakedFluidModels = new IBakedModel[BakedOreProcessorModel.FLUID_LEVELS];

			for (int x = 0; x < BakedOreProcessorModel.FLUID_LEVELS; x++) {
				bakedFluidModels[x] = new BakedFluidTankFluidModel(fluid, x + 1);
			}

			BakedOreProcessorModel.FLUID_MODELS.put(entry.getKey(), bakedFluidModels);
		}

		RegistrySimple<ModelResourceLocation, IBakedModel> registry = (RegistrySimple) event.getModelRegistry();
		ArrayList<ModelResourceLocation> modelLocations = Lists.newArrayList();

		String modelPath = "block_" + type + "_oreprocessor";

		for (ModelResourceLocation modelLoc : registry.getKeys()) {
			if (modelLoc.getResourceDomain().equals(Orecraft.MOD_ID) && modelLoc.getResourcePath().equals(modelPath) && !modelLoc.getVariant().equals("inventory")) {
				modelLocations.add(modelLoc);
			}
		}

		IBakedModel registeredModel;
		IBakedModel replacementModel;

		for (ModelResourceLocation loc : modelLocations) {
			registeredModel = event.getModelRegistry().getObject(loc);
			replacementModel = new BakedOreProcessorModel(registeredModel);
			event.getModelRegistry().putObject(loc, replacementModel);
		}
	}

	public static void bakeFluidCrafterModel(ModelBakeEvent event, String type) {
		Fluid fluid;
		IBakedModel[] bakedFluidModels;

		for (Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
			fluid = entry.getValue();

			bakedFluidModels = new IBakedModel[BakedFluidCrafterModel.FLUID_LEVELS];

			for (int x = 0; x < BakedFluidCrafterModel.FLUID_LEVELS; x++) {
				bakedFluidModels[x] = new BakedFluidTankFluidModel(fluid, x + 1);
			}

			BakedFluidCrafterModel.FLUID_MODELS.put(entry.getKey(), bakedFluidModels);
		}

		RegistrySimple<ModelResourceLocation, IBakedModel> registry = (RegistrySimple) event.getModelRegistry();
		ArrayList<ModelResourceLocation> modelLocations = Lists.newArrayList();

		String modelPath = "block_" + type + "_fluidcrafter";

		for (ModelResourceLocation modelLoc : registry.getKeys()) {
			if (modelLoc.getResourceDomain().equals(Orecraft.MOD_ID) && modelLoc.getResourcePath().equals(modelPath) && !modelLoc.getVariant().equals("inventory")) {
				modelLocations.add(modelLoc);
			}
		}

		IBakedModel registeredModel;
		IBakedModel replacementModel;

		for (ModelResourceLocation loc : modelLocations) {
			registeredModel = event.getModelRegistry().getObject(loc);
			replacementModel = new BakedFluidCrafterModel(registeredModel);
			event.getModelRegistry().putObject(loc, replacementModel);
		}
	}

	public static void bakeFluidPipeModel(ModelBakeEvent event) {
		Fluid fluid;
		IBakedModel[] bakedFluidModels;

		for (Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
			fluid = entry.getValue();

			bakedFluidModels = new IBakedModel[BakedFluidPipeClearModel.FLUID_LEVELS];

			for (int x = 0; x < BakedFluidPipeClearModel.FLUID_LEVELS; x++) {
				bakedFluidModels[x] = new BakedFluidPipeClearFluidModel(fluid, x);
			}

			BakedFluidPipeClearModel.FLUID_MODELS.put(entry.getKey(), bakedFluidModels);
		}

		RegistrySimple<ModelResourceLocation, IBakedModel> registry = (RegistrySimple) event.getModelRegistry();
		ArrayList<ModelResourceLocation> modelLocations = Lists.newArrayList();

		String modelPath = "block_fluidpipe_clear";

		for (ModelResourceLocation modelLoc : registry.getKeys()) {
			if (modelLoc.getResourceDomain().equals(Orecraft.MOD_ID) && modelLoc.getResourcePath().equals(modelPath) && !modelLoc.getVariant().equals("inventory")) {
				modelLocations.add(modelLoc);
			}
		}

		IBakedModel registeredModel;
		IBakedModel replacementModel;

		for (ModelResourceLocation loc : modelLocations) {
			registeredModel = event.getModelRegistry().getObject(loc);
			replacementModel = new BakedFluidPipeClearModel(registeredModel);
			event.getModelRegistry().putObject(loc, replacementModel);
		}
	}
}
